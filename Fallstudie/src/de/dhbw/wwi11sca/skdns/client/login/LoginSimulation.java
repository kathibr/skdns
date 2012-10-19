package de.dhbw.wwi11sca.skdns.client.login;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 *         Die LoginSimulation enthält das Frontend des Logins.
 * 
 */
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Image;
import de.dhbw.wwi11sca.skdns.shared.User;
import de.dhbw.wwi11sca.skdns.client.admin.AdminSimulation;
import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation;

public class LoginSimulation implements EntryPoint {

	private final LoginServiceAsync LoginService = GWT
			.create(LoginService.class);

	// Panel
	private AbsolutePanel panelLogin = new AbsolutePanel();

	// Widgets
	Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");
	private TextBox textBoxUsername = new TextBox();
	private PasswordTextBox textBoxPassword = new PasswordTextBox();
	private Button btLogin = new Button("Login");
	private Button btForgotPassword = new Button("Passwort vergessen?");

	public static User userOnline;
	private Label lbInfo = new Label();
	String admin = new String("admin");

	public void onModuleLoad() {

		// RootPanel: root
		RootPanel root = RootPanel.get();
		root.setSize("1024px", "768px");

		// AbsolutePanel: panelLogin
		root.get().add(panelLogin, 0, 0);
		panelLogin.setSize("1024px", "768px");
		lbInfo.setSize("310px", "12px");

		// Firmenlogo: logo
		panelLogin.add(logo, 205, 70);
		logo.setSize("360px", "110px");

		// TextBox für den Usernamen: textBoxUsername
		panelLogin.add(textBoxUsername, 236, 228);
		textBoxUsername.setSize("300px", "24px");
		textBoxUsername.setText("Username");

		// TextBox für das Kennwort: textBoxPassword
		panelLogin.add(textBoxPassword, 236, 283);
		textBoxPassword.setText("Kennwort");
		textBoxPassword.setSize("300px", "24px");

		// Informationsfeld: lfInfo
		panelLogin.add(lbInfo, 236, 404);

		// Buttons

		// Button vergessenes Passwort: btForgotPassword
		panelLogin.add(btForgotPassword, 397, 353);
		btForgotPassword.setSize("149px", "30px");

		// Button einloggen: btLogin
		panelLogin.add(btLogin, 236, 353);
		btLogin.setSize("100px", "30px");

		// Eventhandler

		// Eventhandler Login
		btLogin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				userOnline = new User();
				userOnline.setPassword(textBoxPassword.getText());
				userOnline.setUsername(textBoxUsername.getText());
				// Überprüfen, ob es sich bei dem einloggenden User um den admin
				// handelt
				// TODO funktioniert noch nicht
				if (userOnline.getUsername().equals(admin)) {
					LoginService.checkAdmin(userOnline,
							new CheckAdminCallback());
				} else {
					LoginService.checkLogin(userOnline,
							new CheckLoginCallback());
				}
			}
		});

		// Eventhandler vergessenes Passwort
		btForgotPassword.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				userOnline = new User();
				userOnline.setUsername(textBoxUsername.getText());
				LoginService.forgotPassword(userOnline,
						new ForgotPasswordCallback());
			}
		});

		// Eventhandler Password TextBox: löscht den Textboxinhalt, damit der
		// User Daten eingeben kann
		textBoxPassword.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBoxPassword.setText("");
			}

		});

		// Eventhandler Username TextBox: löscht den Textboxinhalt, damit der
		// User Daten eingeben kann
		textBoxUsername.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBoxUsername.setText("");
			}
		});
	}

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die
	 * Logindaten mit den Daten der DB vergleicht.
	 * 
	 */
	public class CheckLoginCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {
			lbInfo.setText("Username oder Passwort falsch/ unbekannt.");

		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get().clear();
			HomeSimulation home = new HomeSimulation();
			home.onModuleLoad();

		}
	}

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher
	 * speichert, dass der User sein Passwort vergessen hat
	 * 
	 */
	public class ForgotPasswordCallback implements
			AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {
			lbInfo.setText("Derzeit liegt leider ein Systemfehler vor. Versuchen Sie es später erneut.");

		}

		@Override
		public void onSuccess(Void result) {

			lbInfo.setText("Der Admin wurde informiert.");

		}
	}

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher das
	 * Adminpasswort mit der DB vergleicht.
	 * 
	 */
	public class CheckAdminCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {
			lbInfo.setText("Adminpasswort falsch.");

		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get().clear();
			AdminSimulation admin = new AdminSimulation();
			admin.onModuleLoad();

		}
	}
}
