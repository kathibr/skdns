package de.dhbw.wwi11sca.skdns.client.login;


import java.net.URLClassLoader;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Image;
import de.dhbw.wwi11sca.skdns.shared.*;
import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation;


public class LoginSimulation  implements EntryPoint{
	
	public static User user1;
	String version = new String ("0.00.1");
	AbsolutePanel absolutePanelLogin = new AbsolutePanel();
	TextBox textBoxUsername = new TextBox();
	PasswordTextBox textBoxKennwort = new PasswordTextBox();
	Button ButtonLogin = new Button("Login");
	Label LabelAnmeldeFehlschlag = new Label(" ");
	boolean AnmeldeBestaetigung;
	Label labelSimulationsVersion = new Label(" ");
	Image imageLoginLogo = new Image();
	HomeSimulation home = new HomeSimulation();
		/**
		 * Create a remote service proxy to talk to the server-side Greeting service.
		 */
		private final LoginServiceAsync LoginService = GWT.create(LoginService.class);

		/**
		 * This is the entry point method.
		 */
		public void onModuleLoad() {
			// allgemeine Panels
			RootPanel rootPanel = RootPanel.get();
			rootPanel.setSize("1024", "768");
			rootPanel.add(absolutePanelLogin, 182, 169);
			absolutePanelLogin.setSize("655px", "395px");
			
			// TextBox für den Usernamen
			textBoxUsername.setText("Username");
			absolutePanelLogin.add(textBoxUsername, 187, 188);
			textBoxUsername.setSize("257px", "18px");
				// ClickHandler löscht den Inhalt der Textbox, damit der User seine Daten eingeben kann 
			textBoxUsername.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					textBoxUsername.setText(" ");
				}
			});
			
			// TextBox für das Kennwort			
			textBoxKennwort.setText("Kennwort");
			absolutePanelLogin.add(textBoxKennwort, 187, 224);
			textBoxKennwort.setSize("257px", "18px");
				// ClickHandler löscht den Inhalt der Textbox, damit der User seine Daten eingeben kann 
			textBoxKennwort.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					textBoxKennwort.setText("");
				}
			});
			
			// Button für die Bestätigung der Daten
			ButtonLogin.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					
	
					User user1 = new User();
					user1.setKennwort(textBoxKennwort.getText());
					user1.setUsername(textBoxUsername.getText());
					
					LoginService.checkLogin(user1, new CheckLoginCallback());
					// Kontrolle, ob die Daten in der DB vorhanden sind
					// setze AnmeldeBestaetigung = true oder false
//					AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
//						
//							@Override
//							public void onFailure (Throwable caught){
//								// TODO: on Failure
//								System.out.println(caught.getMessage());
//							}
//							@Override
//							public void onSuccess(Boolean result){
//								AnmeldeBestaetigung = result;	
//								if (AnmeldeBestaetigung == true)
//								{
//									// In die Startoberfläche wechseln
//									LabelAnmeldeFehlschlag.setText("Daten korrekt.");
//									RootPanel.get().clear();
//									HomeSimulation home = new HomeSimulation();
//									home.onModuleLoad();
//															
//								}
//								else
//								{
//									LabelAnmeldeFehlschlag.setText("Username oder Passwort falsch/ unbekannt.");							
//								}			
//							}						
//					};
//					LoginService.getKennDaten(callback);
					
					
				}
			});
			absolutePanelLogin.add(ButtonLogin, 354, 260);
			ButtonLogin.setSize("100px", "35px");
			
			// Label, dass dem User meldet, dass seine Daten nicht vorhanden sind.
			LabelAnmeldeFehlschlag.setStyleName("gwt-PasswortUnbekannt");
			absolutePanelLogin.add(LabelAnmeldeFehlschlag, 222, 159);
			LabelAnmeldeFehlschlag.setSize("219px", "18px");
		
			// Label, dass dem User Informationen über die Version gibt
			labelSimulationsVersion.setStyleName("gwt-Informationslabel");
			absolutePanelLogin.add(labelSimulationsVersion, 10, 373);
			labelSimulationsVersion.setText("Version " + version);
			imageLoginLogo.setUrl("fallstudie/gwt/clean/images/Logo.JPG");
			
			absolutePanelLogin.add(imageLoginLogo, 175, 46);
			imageLoginLogo.setSize("292px", "89px");
			
			Button btnNeuerBenutzer = new Button("Neuer Benutzer");
			absolutePanelLogin.add(btnNeuerBenutzer, 520, 355);
			btnNeuerBenutzer.setSize("125px", "30px");
			
		}
	    public class CheckLoginCallback implements AsyncCallback<java.lang.Void> {

	        @Override
	        public void onFailure(Throwable caught) {
	        	LabelAnmeldeFehlschlag.setText("Username oder Passwort falsch/ unbekannt.");
	        	
	        }

	        @Override
	        public void onSuccess(Void result) {
	        	LabelAnmeldeFehlschlag.setText("Daten korrekt.");
				RootPanel.get().clear();
				HomeSimulation home = new HomeSimulation();
				home.onModuleLoad();
	      
	        }
	    }
}


