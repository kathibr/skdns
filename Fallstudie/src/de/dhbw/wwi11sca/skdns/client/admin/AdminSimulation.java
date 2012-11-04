package de.dhbw.wwi11sca.skdns.client.admin;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 *         Die AdminSimulation enthält das Frontend der Adminfunktionen.
 * 
 */
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import de.dhbw.wwi11sca.skdns.client.login.LoginSimulation;
import de.dhbw.wwi11sca.skdns.shared.Admin;
import de.dhbw.wwi11sca.skdns.shared.User;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class AdminSimulation implements EntryPoint {

	// Panels
	AbsolutePanel panelAdmin = new AbsolutePanel();

	// Widgets
	CellTable<User> cellTableUser = new CellTable<User>();
	List<User> userList;
	Button btSave = new Button("Refresh");

	Button btLogout = new Button("Logout");
	Label lbLogout = new Label("Sie wurden erfolgreich ausgeloggt.");
	Button btRelogin = new Button("erneuter Login?");

	Label lbExistingUsers = new Label("aktuell angelegte User:");
	Label lbLoginCounter = new Label("0");
	Label lbLogins = new Label("bisherige Anmeldungen (gesamt):");
	Label lbExistingUserCounter = new Label("0");

	Label lbCreateNewUser = new Label("neuen User anlegen:");
	TextBox textBoxUsername = new TextBox();
	TextBox textBoxMail = new TextBox();
	TextBox textBoxPassword = new TextBox();
	Button btCreateUser = new Button("neuen User anlegen");
	User newUser;

	TextBox textBoxUsernameDelete = new TextBox();
	Label lbDeleteUser = new Label("User l\u00F6schen:");
	Button btDelete = new Button("L\u00F6schen");
	String deleteUser;

	Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");

	String changeInfo;
	private AdminServiceAsync service = GWT.create(AdminService.class);

	@Override
	public void onModuleLoad() {
		// RootPanel : root
		RootPanel root = RootPanel.get();
		root.setSize("1024px", "768px");

		// AbsolutePanel : panelAdmin
		root.add(panelAdmin, -10, 0);
		panelAdmin.setSize("1024px", "768px");

		// Firmenlogo: logo
		panelAdmin.add(logo, 30, 30);
		logo.setSize("339px", "100px");

		// Buttons
		panelAdmin.add(btLogout, 933, 10);
		panelAdmin.add(btSave, 309, 594);
		panelAdmin.add(btDelete, 788, 512);
		panelAdmin.add(btCreateUser, 788, 372);

		btDelete.setSize("173px", "30px");
		btLogout.setSize("81px", "30px");
		btCreateUser.setSize("173px", "30px");

		// Statistiken
		panelAdmin.add(lbExistingUsers, 525, 64);
		panelAdmin.add(lbExistingUserCounter, 788, 64);
		lbExistingUserCounter.setSize("44px", "18px");

		panelAdmin.add(lbLogins, 525, 88);
		panelAdmin.add(lbLoginCounter, 788, 88);
		lbLoginCounter.setSize("47px", "18px");

		// neuen User anlegen
		panelAdmin.add(lbCreateNewUser, 559, 246);

		;
		panelAdmin.add(textBoxUsername, 559, 292);
		panelAdmin.add(textBoxPassword, 559, 328);
		panelAdmin.add(textBoxMail, 559, 368);

		textBoxUsername.setSize("159px", "18px");

		textBoxUsername.setText("Username");
		textBoxPassword.setText("Kennwort");
		textBoxMail.setText("E-Mail");

		// User löschen
		panelAdmin.add(lbDeleteUser, 559, 464);
		panelAdmin.add(textBoxUsernameDelete, 559, 508);

		textBoxUsernameDelete.setText("Username");

		// Usertabelle
		getUserTable();

		// Calls
		service.getUser(new GetUserCallback());
		service.getStats(new GetStatsCallback());

		// Eventhandler

		btSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				service.getUser(new GetUserCallback());

			}
		}); // btSave

		btDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// User löschen, dessen Name angezeigt wurde
				deleteUser = textBoxUsernameDelete.getText().trim();
				if((deleteUser.toLowerCase()).equals("admin")){
					Window.alert("Sie können den Admin nicht löschen!");
				}else{
					service.deleteUser(deleteUser, new DeleteUserCallback());
				}
				
			}
		}); // Ende btDelete

		btCreateUser.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// Daten aus Textboxen übernehmen und neuen User erzeugen
				newUser = new User();
				newUser.setUsername(textBoxUsername.getText());
				newUser.setPassword(textBoxPassword.getText());
				newUser.setMail(textBoxMail.getText());
				service.saveUser(newUser, new SaveUserCallback());
			}
		}); // Ende btCreateUser

		// Eventhandler ausloggen
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(btRelogin);
				RootPanel.get().add(lbLogout);
				btRelogin.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						RootPanel.get().clear();
						LoginSimulation login = new LoginSimulation();
						login.onModuleLoad();
					}
				}); // btLogout
			}
		});

		// Eventhandler Username TextBox: löscht den Textboxinhalt, damit der
		// Admin Daten eingeben kann
		textBoxUsername.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBoxUsername.setText("");
			}
		}); // Ende textBoxUsername

		// Eventhandler Mail TextBox: löscht den Textboxinhalt, damit der
		// Admin Daten eingeben kann
		textBoxMail.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBoxMail.setText("");
			}
		}); // Ende textBoxUsername

		// Eventhandler Password TextBox: löscht den Textboxinhalt, damit der
		// Admin Daten eingeben kann
		textBoxPassword.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBoxPassword.setText("");
			}
		}); // Ende textBoxUsername

		// Eventhandler User löschen TextBox: löscht den Textboxinhalt, damit
		// der
		// Admin Daten eingeben kann
		textBoxUsernameDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBoxUsernameDelete.setText("");
			}
		}); // Ende textBoxUsername

	} // Ende onModuleLoad

	private void getUserTable() {
		// die User werden in eine Tabelle geladen und angezeigt
		cellTableUser.setPageSize(30);
		cellTableUser.setSize("489px", "407px");
		panelAdmin.add(cellTableUser, 30, 181);

		// Usertabelle mit DB-Usern befüllen: Username
		TextColumn<User> UsernameColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return new String(user.getUsername());
			}

		}; // Ende UsernameColumn

		// Usertabelle mit DB-Usern befüllen: Kennwort
		final TextInputCell passwordCell = new TextInputCell();
		Column<User, String> PasswordColumn = new Column<User, String>(
				passwordCell) {
			@Override
			public String getValue(User object) {
				return object.getPassword();
			}
		}; // Ende PasswordColumn

		// Usertabelle mit DB-Usern befüllen: E-Mail
		TextColumn<User> EMailColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return new String(user.getMail());
			}

		}; // Ende EMailColumn

		List<String> pwdCell = new ArrayList<String>();
		pwdCell.add("true");
		pwdCell.add("false");
		SelectionCell forgottenPasswordCell = new SelectionCell(pwdCell);
		Column<User, String> forgottenPasswordColumn = new Column<User, String>(
				forgottenPasswordCell) {

			@Override
			public String getValue(User object) {
				return new String(
						new Boolean(object.isForgottenPassword()).toString());
			}

		};

		// DB-Userdaten anzeigen lassen
		cellTableUser.addColumn(UsernameColumn, "Username");
		cellTableUser.addColumn(PasswordColumn, "Kennwort");
		cellTableUser.addColumn(EMailColumn, "Email");
		cellTableUser.addColumn(forgottenPasswordColumn, "Passwort vergessen");

		PasswordColumn.setFieldUpdater(new FieldUpdater<User, String>() {
			@Override
			public void update(int index, User object, String value) {
				Window.alert("\u00C4nderung erfolgreich.");
				((User) object).setPassword((String) value);
				service.updateTable((User) object, new UpdateTableCallback());
				cellTableUser.redraw();
			}
		});
		forgottenPasswordColumn
				.setFieldUpdater(new FieldUpdater<User, String>() {
					@Override
					public void update(int index, User object, String value) {
						Window.alert("\u00C4nderung erfolgreich.");
						((User) object)
								.setForgottenPassword(new Boolean(value));
						service.updateTable((User) object,
								new UpdateTableCallback());
						cellTableUser.redraw();
					}
				});

	} // Ende method getUserTable

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher den neu
	 * erzeugten User in der db speichert
	 * 
	 */
	public class UpdateTableCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {

		} // Ende onFailure

		@Override
		public void onSuccess(Void result) {
			// TODO: User ist gespeichert worden uns soll nun in der
			// cellTableUser auch für den Anwender angezeigt werden
			// Daten wurden durch Textboxen im Eventhandler btCreaseUser.onClick
			// in newUser gespeichert
			// und mit service.saveUser(newUser, new SaveUserCallback())
			// gestartet
			// wurde newUser in der DB gespeichert, soll dem Admin ein Label
			// angezeigt werden, dass das speichern erfolgreich war

		} // Ende method onSuccess
	} // Ende class SaveUserCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die
	 * Userdaten aus der Datenbank zurückgibt, deren Passwort geändert werden
	 * müssen
	 * 
	 */
	public class GetUserCallback implements AsyncCallback<List<User>> {

		@Override
		public void onFailure(Throwable caught) {

		} // Ende onFailure

		@Override
		public final void onSuccess(List<User> result) {
			ListDataProvider<User> dataProvider = new ListDataProvider<User>();
			// Connect the table to the data provider.
			dataProvider.addDataDisplay(cellTableUser);

			// Add the data to the data provider, which automatically pushes it
			// to the widget.
			userList = dataProvider.getList();

			for (User user : result) {
				userList.add(user);
			} // Ende for-Schleife

		} // Ende method onSuccess

	} // Ende class GetChangeUserCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher den neu
	 * erzeugten User in der db speichert
	 * 
	 */
	public class SaveUserCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {

		} // Ende onFailure

		@Override
		public void onSuccess(Void result) {
			// TODO: User ist gespeichert worden uns soll nun in der
			// cellTableUser auch für den Anwender angezeigt werden
			// Daten wurden durch Textboxen im Eventhandler btCreaseUser.onClick
			// in newUser gespeichert
			// und mit service.saveUser(newUser, new SaveUserCallback())
			// gestartet
			// wurde newUser in der DB gespeichert, soll dem Admin ein Label
			// angezeigt werden, dass das speichern erfolgreich war

		} // Ende method onSuccess
	} // Ende class SaveUserCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher einen
	 * gewählten User aus der DB löscht
	 * 
	 */
	public class DeleteUserCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {

		} // Ende method onFailure

		@Override
		public void onSuccess(Void result) {
			// TODO User soll aus der db gelöscht werden
			// der Username wurde durch die textBoxUsernameDelete im String
			// deleteUser gespeichert
			// dann service.deleteUser(deleteUser, new DeleteUserCallback())
			// ist der zu löschende User aus der DB entfernt worden soll dem
			// User ein Label ausgegeben werden
			// auf dem angegeben wurde, dass der User nun nicht mehr vorhanden
			// ist
			Window.alert("User wurde entfernt.");
		} // Ende method onSuccess
	} // Ende class DeleteUserCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die
	 * Anzahl der existierenden User und der bisher insgesamt getätigten Logins
	 * zurückgibt
	 * 
	 */
	public class GetStatsCallback implements AsyncCallback<Admin> {

		@Override
		public void onFailure(Throwable caught) {

		} // Ende method onFailure

		@Override
		public void onSuccess(Admin result) {
			lbLoginCounter.setText(result.getLoginCount() + "");
			lbExistingUserCounter.setText(result.getExistingUserCount() + "");

		} // Ende method onSuccess

	} // Ende class GetStatsCallback
} // Ende class AdminSimulation
