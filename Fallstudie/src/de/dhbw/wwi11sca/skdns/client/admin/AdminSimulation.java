package de.dhbw.wwi11sca.skdns.client.admin;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

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
	Button btSave = new Button("ge�nderte Daten speichern");

	Button btLogout = new Button("Logout");
	Label lbLogout = new Label("Sie wurden erfolgreich ausgeloggt.");

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

		// User l�schen
		panelAdmin.add(lbDeleteUser, 559, 464);
		panelAdmin.add(textBoxUsernameDelete, 559, 508);

		textBoxUsernameDelete.setText("Username");

		// Usertabelle
		getUserTable();

		// Calls
		service.getUser(new GetChangeUserCallback());
		service.getStats(new GetStatsCallback());

		// Eventhandler

		btSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO ge�nderte User speichern Passw�rter oder Email o.�.
				// Daten sollen direkt in der cellTableUser bearbeitet werden
				// k�nnen
				// btSave soll feststellen, ob ein Eintrag in der cellTableUser
				// anders ist, als der entsprechende Eintrag
				// in der List userList
				// wenn Daten unterschiedlich sind, sollen die ge�nderten Daten
				// an den Server �bergeben werden
				// und in der DB gespeichert werden
				// wenn das Passwort ge�ndert wurde, soll das Userfeld
				// forgottenPassword = false gesetzt werden

				// Dem Admin wird �bergeben, dass die Daten ge�ndert wurden und
				// er dem entsprechenden User
				// eine Email schreiben soll
			}
		});

		btDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// User l�schen, dessen Name angezeigt wurde
				deleteUser = textBoxUsernameDelete.getText();
				service.deleteUser(deleteUser, new DeleteUserCallback());

			}
		});

		btCreateUser.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// Daten aus Textboxen �bernehmen und neuen User erzeugen
				newUser = new User();
				newUser.setUsername(textBoxUsername.getText());
				newUser.setKennwort(textBoxPassword.getText());
				newUser.setMail(textBoxMail.getText());
				service.saveUser(newUser, new SaveUserCallback());
			}
		});

		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(lbLogout);
			}
		});

	}

	private void getUserTable() {
		// die User werden in eine Tabelle geladen und angezeigt
		cellTableUser.setPageSize(30);
		cellTableUser.setSize("489px", "407px");
		panelAdmin.add(cellTableUser, 30, 181);

		// Usertabelle mit DB-Usern bef�llen: Username
		TextColumn<User> UsernameColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return new String(user.getUsername());
			}

		};

		// Usertabelle mit DB-Usern bef�llen: Kennwort
		TextColumn<User> PasswordColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return new String(user.getKennwort());
			}

		};
		// Usertabelle mit DB-Usern bef�llen: E-Mail
		TextColumn<User> EMailColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return new String(user.getMail());
			}

		};

		// DB-Userdaten anzeigen lassen
		cellTableUser.addColumn(UsernameColumn, "Username");
		cellTableUser.addColumn(PasswordColumn, "Kennwort");
		cellTableUser.addColumn(EMailColumn, "Email");

	}

	/**
	 * 
	 * Klasse, die f�r den Asynchronen Callback zust�ndig ist, welcher die
	 * Userdaten aus der Datenbank zur�ckgibt, deren Passwort ge�ndert werden
	 * muss
	 * 
	 */
	public class GetChangeUserCallback implements AsyncCallback<List<User>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(List<User> result) {
			ListDataProvider<User> dataProvider = new ListDataProvider<User>();
			// Connect the table to the data provider.
			dataProvider.addDataDisplay(cellTableUser);

			// Add the data to the data provider, which automatically pushes it
			// to the widget.
			userList = dataProvider.getList();

			for (User user : result) {
				userList.add(user);
			}
		}

	}

	/**
	 * 
	 * Klasse, die f�r den Asynchronen Callback zust�ndig ist, welcher den neu
	 * erzeugten User in der db speichert
	 * 
	 */
	public class SaveUserCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Void result) {
			// TODO: User ist gespeichert worden uns soll nun in der
			// cellTableUser auch f�r den Anwender angezeigt werden
			// Daten wurden durch Textboxen im Eventhandler btCreaseUser.onClick
			// in newUser gespeichert
			// und mit service.saveUser(newUser, new SaveUserCallback())
			// gestartet
			// wurde newUser in der DB gespeichert, soll dem Admin ein Label
			// angezeigt werden, dass das speichern erfolgreich war

		}
	}

	/**
	 * 
	 * Klasse, die f�r den Asynchronen Callback zust�ndig ist, welcher einen
	 * gew�hlten User aus der DB l�scht
	 * 
	 */
	public class DeleteUserCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Void result) {
			// TODO User soll aus der db gel�scht werden
			// der Username wurde durch die textBoxUsernameDelete im String
			// deleteUser gespeichert
			// dann service.deleteUser(deleteUser, new DeleteUserCallback())
			// ist der zu l�schende User aus der DB entfernt worden soll dem
			// User ein Label ausgegeben werden
			// auf dem angegeben wurde, dass der User nun nicht mehr vorhanden
			// ist
		}
	}

	/**
	 * 
	 * Klasse, die f�r den Asynchronen Callback zust�ndig ist, welcher die Anzahl der existierenden User und der bisher insgesamt get�tigten Logins zur�ckgibt
	 * 
	 */
	public class GetStatsCallback implements AsyncCallback<Admin> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Admin result) {
			lbLoginCounter.setText(result.getLoginCount() + "");
			lbExistingUserCounter.setText(result.getExistingUserCount() + "");

		}

	}
}