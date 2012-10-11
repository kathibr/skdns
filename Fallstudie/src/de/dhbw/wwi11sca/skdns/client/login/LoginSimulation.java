package de.dhbw.wwi11sca.skdns.client.login;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Image;
import de.dhbw.wwi11sca.skdns.shared.*;
import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;

public class LoginSimulation  implements EntryPoint{
		
//	AbsolutePanel absolutePanelLogin = new AbsolutePanel();
//	TextBox textBoxUsername = new TextBox();
//	PasswordTextBox textBoxKennwort = new PasswordTextBox();
//	Button ButtonLogin = new Button("Login");
//	Label LabelAnmeldeFehlschlag = new Label("");
//	Label labelSimulationsVersion = new Label("");
//	Image imageLoginLogo = new Image();
//	HomeSimulation home = new HomeSimulation();
//	public static User user1;
//	String version = new String ("0.00.1");
//	boolean AnmeldeBestaetigung;
	private final LoginServiceAsync LoginService = GWT.create(LoginService.class);
	//Panel
	private AbsolutePanel panelLogin;
	private TabLayoutPanel tabLayoutPanel;
	private VerticalPanel loginPanel;
	private VerticalPanel forgotPwdPanel;
	private VerticalPanel adminPanel;
	
	private TextBox textBoxUsername;
	private PasswordTextBox textBoxKennwort;
	private Button btLogin;
	
	private TextBox tBUser;
	private TextBox tBEmail;
	private Button btSend;
	
	private TextBox tBAdmin;
	private TextBox tbpwd;
	private Button btAdminLogin;
	
	public static User userOnline;
	private Label lbLoginFail;
	private Label adminLoginFail;
	private String version;
	
	public void onModuleLoad() {
			
		panelLogin = new AbsolutePanel();
		panelLogin.setSize("624px", "382px");
			
		tabLayoutPanel = new TabLayoutPanel(1.5, Unit.EM);
		tabLayoutPanel.setSize("360px", "216px");
		
		loginPanel = new VerticalPanel();
		loginPanel.setSize("350px", "182px");		
		
		forgotPwdPanel = new VerticalPanel();
		forgotPwdPanel.setSize("350px", "182px");	
		
		adminPanel = new VerticalPanel();
		adminPanel.setSize("350px", "182px");	
			
		tabLayoutPanel.add(loginPanel, "Login", true);
		tabLayoutPanel.add(forgotPwdPanel, "Passwort vergessen", false);
		tabLayoutPanel.add(adminPanel, "Admin", false);
		
		panelLogin.add(tabLayoutPanel,0,110);
		RootPanel.get().add(panelLogin, 0, 0);	
			
		Image image = new Image("fallstudie/gwt/clean/images/logo.png");
		panelLogin.add(image, 0, 0);
		image.setSize("624px", "110px");
		
		//Login Panel	
		
			// TextBox für den Usernamen
			textBoxUsername = new TextBox();
			loginPanel.add(textBoxUsername);
			textBoxUsername.setSize("300px", "30px");
			textBoxUsername.setText("Username");
			
			// TextBox für das Kennwort		
			textBoxKennwort = new PasswordTextBox();
			loginPanel.add(textBoxKennwort);
			textBoxKennwort.setText("Kennwort");
			textBoxKennwort.setSize("300px", "30px");
			
			//Login Fail
//			lbLoginFail.setStyleName("gwt-PasswortUnbekannt");
			lbLoginFail = new Label();
			loginPanel.add(lbLoginFail);
			lbLoginFail.setSize("300px", "12px");
			
			//Button Bestätigung
			btLogin = new Button("Login");
			loginPanel.add(btLogin);
			btLogin.setSize("100px", "30px");
			
			
			btLogin.addClickHandler(new ClickHandler() 
			{
				public void onClick(ClickEvent event) {
					userOnline = new User();
					userOnline.setKennwort(textBoxKennwort.getText());
					userOnline.setUsername(textBoxUsername.getText());
					LoginService.checkLogin(userOnline, new CheckLoginCallback());	
				}
			});
			textBoxKennwort.addClickHandler(new ClickHandler() //löscht den Inhalt der Textbox, damit der User seine Daten eingeben kann 
			{
				public void onClick(ClickEvent event) {
					textBoxKennwort.setText("");
				}
			});
			textBoxUsername.addClickHandler(new ClickHandler() //löscht den Inhalt der Textbox, damit der User seine Daten eingeben kann 
			{
				public void onClick(ClickEvent event) {
					textBoxUsername.setText("");
				}
			});
			
			
		//ForgotPwd Panel	
			
			tBUser = new TextBox();
			forgotPwdPanel.add(tBUser);
			tBUser.setSize("300px", "30px");
			tBUser.setText("Username");
			
			tBEmail = new TextBox();
			forgotPwdPanel.add(tBEmail);
			tBEmail.setSize("300px", "30px");
			tBEmail.setText("E-Mail");
			
			btSend = new Button("Senden");
			forgotPwdPanel.add(btSend);
			btSend.setSize("100px", "30px");
								
			btSend.addClickHandler(new ClickHandler() 
			{
				public void onClick(ClickEvent event) {
					//ToDo
				}
			});
			tBUser.addClickHandler(new ClickHandler() //löscht den Inhalt der Textbox, damit der User seine Daten eingeben kann 
			{
				public void onClick(ClickEvent event) {
					tBUser.setText("");
				}
			});
			tBEmail.addClickHandler(new ClickHandler() //löscht den Inhalt der Textbox, damit der User seine Daten eingeben kann 
			{
				public void onClick(ClickEvent event) {
					tBEmail.setText("");
				}
			});
			
		//Admin Panel	
			
		// TextBox für den Usernamen
		tBAdmin = new TextBox();
		adminPanel.add(tBAdmin);
		tBAdmin.setSize("300px", "30px");
		tBAdmin.setText("Admin Name");
		
		// TextBox für das Kennwort		
		tbpwd = new PasswordTextBox();
		adminPanel.add(tbpwd);
		tbpwd.setText("Kennwort");
		tbpwd.setSize("300px", "30px");
			
		//Login Fail
//			lbLoginFail.setStyleName("gwt-PasswortUnbekannt");
		adminLoginFail = new Label();
		adminPanel.add(adminLoginFail);
		adminLoginFail.setSize("300px", "12px");
			
			//Button Bestätigung
		btAdminLogin = new Button("Login");
		adminPanel.add(btAdminLogin);
		btAdminLogin.setSize("100px", "30px");
		
			
		btLogin.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) {
				//TODO
			}
		});
			textBoxKennwort.addClickHandler(new ClickHandler() //löscht den Inhalt der Textbox, damit der User seine Daten eingeben kann 
			{
				public void onClick(ClickEvent event) {
					tbpwd.setText("");
				}
			});
			textBoxUsername.addClickHandler(new ClickHandler() //löscht den Inhalt der Textbox, damit der User seine Daten eingeben kann 
			{
				public void onClick(ClickEvent event) {
					tBAdmin.setText("");
				}
			});

		
	}	
			

			// Label, dass dem User Informationen über die Version gibt
//			labelSimulationsVersion.setStyleName("gwt-Informationslabel");
//			absolutePanelLogin.add(labelSimulationsVersion, 10, 373);
//			labelSimulationsVersion.setText("Version " + version);
//			imageLoginLogo.setUrl("fallstudie/gwt/clean/images/Logo.JPG");

	    public class CheckLoginCallback implements AsyncCallback<java.lang.Void> {

	        @Override
	        public void onFailure(Throwable caught) {
	        	lbLoginFail.setText("Username oder Passwort falsch/ unbekannt.");
	        	
	        }

	        @Override
	        public void onSuccess(Void result) {
	        	
				RootPanel.get().clear();
				HomeSimulation home = new HomeSimulation();
				home.onModuleLoad();
	      
	        }
	    }
}


