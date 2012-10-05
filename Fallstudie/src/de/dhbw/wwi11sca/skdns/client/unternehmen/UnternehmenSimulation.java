package de.dhbw.wwi11sca.skdns.client.unternehmen;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;

public class UnternehmenSimulation implements EntryPoint{
	
	Button btnLogout = new Button("Logout");
	AbsolutePanel absolutePanel = new AbsolutePanel();
	
	@Override
	public void onModuleLoad() {
		// allgemeine Panels
		System.out.println("Zugang");
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");		
		rootPanel.add(absolutePanel, 0, 0);
		absolutePanel.setSize("1024", "768");
		
		// Logout-Button		
		absolutePanel.add(btnLogout, 914, 10);
		btnLogout.setSize("100px", "35px");
		
		System.out.println("Button");
		
		TabPanel tabPanel = new TabPanel();
		absolutePanel.add(tabPanel, 71, 88);
		tabPanel.setSize("878px", "557px");
		
		System.out.println("Tab");
		
		AbsolutePanel absolutePanelEigenesUN = new AbsolutePanel();
		tabPanel.add(absolutePanelEigenesUN, "Eigenes Unternehmen", false);
		absolutePanelEigenesUN.setSize("100%", "549px");
		
		Label lblFirma = new Label("Firma:");
		absolutePanelEigenesUN.add(lblFirma, 50, 50);
		
		TextBox textBox = new TextBox();
		absolutePanelEigenesUN.add(textBox, 148, 38);
		
		Label lblNewLabel = new Label("Umsatz:");
		absolutePanelEigenesUN.add(lblNewLabel, 50, 93);
		
		Label lblMarktanteil = new Label("Marktanteil:");
		absolutePanelEigenesUN.add(lblMarktanteil, 50, 141);
		
		Label lblFixkosten = new Label("Fixkosten:");
		absolutePanelEigenesUN.add(lblFixkosten, 50, 191);
		
		Label lblVariableKosten = new Label("variable Kosten:");
		absolutePanelEigenesUN.add(lblVariableKosten, 50, 238);
		
		TextBox textBox_1 = new TextBox();
		absolutePanelEigenesUN.add(textBox_1, 148, 81);
		textBox_1.setSize("157px", "18px");
		
		TextBox textBox_2 = new TextBox();
		absolutePanelEigenesUN.add(textBox_2, 148, 129);
		textBox_2.setSize("157px", "18px");
		
		TextBox textBox_3 = new TextBox();
		absolutePanelEigenesUN.add(textBox_3, 148, 179);
		textBox_3.setSize("157px", "18px");
		
		TextBox textBox_4 = new TextBox();
		absolutePanelEigenesUN.add(textBox_4, 148, 226);
		textBox_4.setSize("157px", "18px");
		
		Label lblNewLabel_1 = new Label("Marketing Ma\u00DFnahmen:");
		absolutePanelEigenesUN.add(lblNewLabel_1, 421, 50);
		
		Label lblMitarbeiter = new Label("Mitarbeiter:");
		absolutePanelEigenesUN.add(lblMitarbeiter, 421, 93);
		
		TextBox textBox_5 = new TextBox();
		absolutePanelEigenesUN.add(textBox_5, 584, 38);
		textBox_5.setSize("157", "18");
		
		TextBox textBox_6 = new TextBox();
		absolutePanelEigenesUN.add(textBox_6, 584, 81);
		textBox_6.setSize("157px", "18px");
		
		Label lblMaschinen = new Label("Maschinen:");
		absolutePanelEigenesUN.add(lblMaschinen, 50, 291);
		
		Label lblNr = new Label("Nr.");
		absolutePanelEigenesUN.add(lblNr, 82, 315);
		
		Label lblAnschaffungsdatum = new Label("Anschaffungsdatum");
		absolutePanelEigenesUN.add(lblAnschaffungsdatum, 123, 315);
		
		Label lblProduktionsleistung = new Label("Produktionsleistung");
		absolutePanelEigenesUN.add(lblProduktionsleistung, 278, 315);
		
		Label lblPreis = new Label("Preis");
		absolutePanelEigenesUN.add(lblPreis, 430, 315);
		
		Label lblBediener = new Label("Bediener");
		absolutePanelEigenesUN.add(lblBediener, 504, 315);
		
		Button btnLschen = new Button("L\u00F6schen");
		absolutePanelEigenesUN.add(btnLschen, 754, 504);
		btnLschen.setSize("100px", "35px");
		
		Button btnAnnehmen = new Button("Annehmen");
		absolutePanelEigenesUN.add(btnAnnehmen, 630, 504);
		btnAnnehmen.setSize("100px", "35px");
		
		AbsolutePanel absolutePanelUN1 = new AbsolutePanel();
		tabPanel.add(absolutePanelUN1, "Unternehmen 1", false);
		absolutePanelUN1.setSize("100%", "549");
		
		AbsolutePanel absolutePanelUN2 = new AbsolutePanel();
		tabPanel.add(absolutePanelUN2, "Unternehmen 2", false);
		absolutePanelUN2.setSize("100%", "549");
		
		AbsolutePanel absolutePanelUN3 = new AbsolutePanel();
		tabPanel.add(absolutePanelUN3, "Unternehmen 3", false);
		absolutePanelUN3.setSize("100%", "549");
		btnLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: ausloggen
			}
		});
		
		
		
		
	}
}
