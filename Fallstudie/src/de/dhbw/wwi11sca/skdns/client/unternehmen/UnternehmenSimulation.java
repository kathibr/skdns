package de.dhbw.wwi11sca.skdns.client.unternehmen;

import java.util.List;
import java.util.regex.Pattern;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TextBox;
import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation;
import de.dhbw.wwi11sca.skdns.shared.*;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

public class UnternehmenSimulation implements EntryPoint {
		
	EigenesUnternehmen eigenesUN;
	Unternehmen[] unternehmen = new Unternehmen[3];
	EigenesUnternehmen eigenesUnternehmen = new EigenesUnternehmen();
		
	//Allgemeine Panel
	AbsolutePanel absolutePanelAnlegen;
	TabLayoutPanel tabPanelUnternehmenAnlegen;
	AbsolutePanel absolutePanelEigenesUnternehmen;
	AbsolutePanel absolutePanelUnternehmenEins = new AbsolutePanel();
	AbsolutePanel absolutePanelUnternehmenZwei = new AbsolutePanel();
	AbsolutePanel absolutePanelUnternehmenDrei = new AbsolutePanel();
	Label labelAusgeloggt = new Label("Sie wurden erfolgreich ausgeloggt.");

	//Allgemeines Panel: Elemente
	Label labelHome = new Label("Home");
	Label lblAnlegen = new Label("> Unternehmen anlegen");
	Button btnLogout = new Button("Logout");
	
	//Eigenes Unternehmen: Elemente
	Label labelFirma = new Label("Firma:");
	TextBox textBoxFirma = new TextBox();
	Label labelUmsatzEigenesUnternehmen = new Label("Umsatz:");
	IntegerBox integerBoxUmsatzEigenesUnternehmen = new IntegerBox();
	Label labelMarktanteilEigenesUnternehmen = new Label("Marktanteil:");
	DoubleBox doubleBoxMarktanteilEigenesUnternehmen = new DoubleBox();
	Label labelFixkosten = new Label("Fixkosten:");
	DoubleBox doubleBoxFixkosten = new DoubleBox();
	Label labelProduktPreisEigenesUnternehmen = new Label("Produktpreis:");
	DoubleBox doubleBoxProduktpreisEigenesUnternehmen = new DoubleBox();
	Label labelAbsatzmengeEigenesUnternehmen = new Label("Absatzmenge:");
	IntegerBox integerBoxAbsatzEigenesUnternehmen = new IntegerBox();
	Label lblAnzahlMitarbeiter = new Label("Anzahl Mitarbeiter:");
	IntegerBox integerBoxAnzahlMitarbeiter = new IntegerBox();
	Label lblDurchMitarbeitergehalt = new Label("durch. Mitarbeitergehalt:");
	IntegerBox integerBoxGehaltMitarbeiter = new IntegerBox();
	
	Button buttonLoeschenEigenesUnternehmen = new Button("L\u00F6schen");
	Button buttonUebernehmenEigenesUnternehmen = new Button("\u00DCbernehmen");
	
	CellTable<Maschinen> cellTableMaschinen;
	List<Maschinen> maschinenEUN;
	
	//Unternehmen 1: Elemente
	Label labelUmsatzUNEins = new Label("Umsatz:");
	IntegerBox integerBoxUmsatzUNEins = new IntegerBox();
	Label labelMarkanteilUNEins = new Label("Marktanteil:");
	DoubleBox doubleBoxMarktanteilUNEins = new DoubleBox();
	Label labelProduktpreisUNEins = new Label("Produktpreis:");
	DoubleBox doubleBoxProduktpreisUNEins = new DoubleBox();
	Label labelAbsatzmengeUNEins = new Label("Absatzmenge:");
	IntegerBox integerBoxAbsatzUNEins = new IntegerBox();
	
	Button buttonLoeschenUNEins = new Button("L\u00F6schen");
	Button buttonUebernehmenUNEins = new Button("\u00DCbernehmen");
	//Unternehmen 2: Elemente
	Label labelUmsatzUNZwei = new Label("Umsatz:");
	IntegerBox integerBoxUmsatzUNZwei = new IntegerBox();
	Label labelMarkanteilUNZwei = new Label("Marktanteil:");
	DoubleBox doubleBoxMarktanteilUNZwei = new DoubleBox();
	Label labelProduktpreisUNZwei = new Label("Produktpreis:");
	DoubleBox doubleBoxProduktpreisUNZwei = new DoubleBox();
	Label labelAbsatzmengeUNZwei = new Label("Absatzmenge:");
	IntegerBox integerBoxAbsatzUNZwei = new IntegerBox();
	
	Button buttonLoeschenUNZwei = new Button("L\u00F6schen");
	Button buttonUebernehmenUNZwei = new Button("\u00DCbernehmen");
	
	//Unternehmen 3: Elemente
	Label labelUmsatzUNDrei = new Label("Umsatz:");
	IntegerBox integerBoxUmsatzUNDrei = new IntegerBox();
	Label labelMarkanteilUNDrei = new Label("Marktanteil:");
	DoubleBox doubleBoxMarktanteilUNDrei = new DoubleBox();
	Label labelProduktpreisUNDrei = new Label("Produktpreis:");
	DoubleBox doubleBoxProduktpreisUNDrei = new DoubleBox();
	Label labelAbsatzmengeUNDrei = new Label("Absatzmenge:");
	IntegerBox integerBoxAbsatzUNDrei = new IntegerBox();
	
	Button buttonLoeschenUNDrei = new Button("L\u00F6schen");
	Button buttonUebernehmenUNDrei = new Button("\u00DCbernehmen");
	
	private UnternehmenServiceAsync service = GWT.create(UnternehmenService.class);

	@Override
	public void onModuleLoad() {
		
		// Allgemeine Panel
		//RootPanel rootPanel = RootPanel.get();
		//rootPanel.setSize("1024", "768");
		absolutePanelAnlegen = new AbsolutePanel();
		RootPanel.get().add(absolutePanelAnlegen, 0, 0);
		absolutePanelAnlegen.setSize("1024px", "768px");
		
		// TabPanel, auf dem die Unternehmen angezeigt werden
		tabPanelUnternehmenAnlegen = new TabLayoutPanel(1.5, Unit.EM);
		tabPanelUnternehmenAnlegen.setSize("844px", "605px");
		
		absolutePanelEigenesUnternehmen = new AbsolutePanel();
		absolutePanelEigenesUnternehmen.setSize("800", "596px");
		absolutePanelUnternehmenEins.setSize("100%", "596");
		absolutePanelUnternehmenZwei.setSize("100%", "596");
		absolutePanelUnternehmenDrei.setSize("100%", "596");
		
		tabPanelUnternehmenAnlegen.add(absolutePanelEigenesUnternehmen,	"Eigenes Unternehmen", false);
		tabPanelUnternehmenAnlegen.add(absolutePanelUnternehmenEins, "Unternehmen 1", false);
		tabPanelUnternehmenAnlegen.add(absolutePanelUnternehmenZwei, "Unternehmen 2", false);
		tabPanelUnternehmenAnlegen.add(absolutePanelUnternehmenDrei, "Unternehmen 3", false);
				
		absolutePanelAnlegen.add(tabPanelUnternehmenAnlegen, 90, 78);
							
		//Allgemeines Panel: Elemente
		absolutePanelAnlegen.add(labelHome, 30, 30);
		labelHome.setStyleName("gwt-Home-Label");
		absolutePanelAnlegen.add(lblAnlegen, 80, 30);
		
		labelHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				HomeSimulation home = new HomeSimulation();
				home.onModuleLoad();
			}
		});
																											
		// Logout-Button
		absolutePanelAnlegen.add(btnLogout, 914, 10);
		btnLogout.setSize("100px", "35px");
		btnLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			// TODO: Daten an DB übergeben?
				RootPanel.get().clear();
				RootPanel.get().add(labelAusgeloggt);
			}
		});
		
		// Tab für das eigene Unternehmen

		addElementeEUN();		
				
		// Unternehmensdaten des eigenen Unternehmen Löschen
		absolutePanelEigenesUnternehmen.add(buttonLoeschenEigenesUnternehmen,
				720, 551);
		buttonLoeschenEigenesUnternehmen.setSize("100px", "35px");
		buttonLoeschenEigenesUnternehmen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBoxFirma.setText(null);
				integerBoxUmsatzEigenesUnternehmen.setValue(null);
				doubleBoxMarktanteilEigenesUnternehmen.setValue(null);
				doubleBoxProduktpreisEigenesUnternehmen.setValue(null);
				integerBoxAbsatzEigenesUnternehmen.setValue(null);
				integerBoxAnzahlMitarbeiter.setValue(null);
				integerBoxGehaltMitarbeiter.setValue(null);
				// TODO Maschinen in Oberfläche löschen
				// TODO EigenesUnternehmen aus DB löschen
			}
		});
								
		// Unternehmensdaten des eigenen Unternehmen in der Datenbank speichern
		absolutePanelEigenesUnternehmen.add(buttonUebernehmenEigenesUnternehmen, 614, 551);
		buttonUebernehmenEigenesUnternehmen.setSize("100px", "35px");
		buttonUebernehmenEigenesUnternehmen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
									
				if(Pattern.matches("[\u00C4\u00DC\u00D6A-Z][a-z\u00E4\u00FC\u00F6\u00C4\u00DC\u00DF\u00D6A-Z\\s]*", textBoxFirma.getText())){
					
					eigenesUN.setFirma(textBoxFirma.getText());
					
					service.addEigenesUN(eigenesUN, new AddEigenesUNCallback());
					
					
				} else {
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");
				}
				
				
			}
		});
										
		// Tab für das erste Unternehmen
				
		addElemente1UN();						
					
		// Button, um die Daten von Unternehmen 1 in DB zu übernehmen
		absolutePanelUnternehmenEins.add(buttonUebernehmenUNEins, 233, 173);
		buttonUebernehmenUNEins.setSize("100px", "35px");
		buttonUebernehmenUNEins.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 1 in DB übernehmen
				unternehmen[0] = new Unternehmen();
				unternehmen[0].setUmsatz(integerBoxUmsatzUNEins.getValue());
				unternehmen[0].setMarktAnteil(doubleBoxMarktanteilUNEins
						.getValue());
				unternehmen[0].getProdukt().setPreis(
						doubleBoxProduktpreisUNEins.getValue());
				unternehmen[0].getProdukt().setMenge(
						integerBoxAbsatzUNEins.getValue());
			}
		});
														
		// Button, um die Daten von Unternehmen 1 zu löschen
		absolutePanelUnternehmenEins.add(buttonLoeschenUNEins, 416, 173);
		buttonLoeschenUNEins.setSize("100px", "35px");
		buttonLoeschenUNEins.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 1 aus DB löschen
				integerBoxUmsatzUNEins.setValue(null);
				doubleBoxMarktanteilUNEins.setValue(null);
				doubleBoxProduktpreisUNEins.setValue(null);
				integerBoxAbsatzUNEins.setValue(null);
			}
		});
																
		// Tab für das zweite Unternehmen

		addElemente2UN();
																		
		// Button, um die Daten von Unternehmen 2 in DB zu übernehmen
		absolutePanelUnternehmenZwei.add(buttonUebernehmenUNZwei, 233, 173);
		buttonUebernehmenUNZwei.setSize("100px", "35px");
		buttonUebernehmenUNZwei.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 2 in DB übernehmen
				unternehmen[1] = new Unternehmen();
				unternehmen[1].setUmsatz(integerBoxUmsatzUNZwei.getValue());
				unternehmen[1].setMarktAnteil(doubleBoxMarktanteilUNZwei
						.getValue());
				unternehmen[1].getProdukt().setPreis(
						doubleBoxProduktpreisUNZwei.getValue());
				unternehmen[1].getProdukt().setMenge(
						integerBoxAbsatzUNZwei.getValue());
			}
		});
			
		// Button, um die Daten von Unternehmen 2 zu löschen
		absolutePanelUnternehmenZwei.add(buttonLoeschenUNZwei, 416, 173);
		buttonLoeschenUNZwei.setSize("100px", "35px");
		buttonLoeschenUNZwei.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 2 aus DB löschen
				integerBoxUmsatzUNZwei.setValue(null);
				doubleBoxMarktanteilUNZwei.setValue(null);
				doubleBoxProduktpreisUNZwei.setValue(null);
				integerBoxAbsatzUNZwei.setValue(null);
			}
		});
			// tab für das dritte Unternehmen
			addElemente3UN();
		
		// Button, um die Daten von Unternehmen 2 in DB zu übernehmen
		absolutePanelUnternehmenDrei.add(buttonUebernehmenUNDrei, 233, 173);
		buttonUebernehmenUNDrei.setSize("100px", "35px");
		buttonUebernehmenUNDrei.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 3 in DB übernehmen
				unternehmen[2] = new Unternehmen();
				unternehmen[2].setUmsatz(integerBoxUmsatzUNDrei.getValue());
				unternehmen[2].setMarktAnteil(doubleBoxMarktanteilUNDrei
						.getValue());
				unternehmen[2].getProdukt().setPreis(
						doubleBoxProduktpreisUNDrei.getValue());
				unternehmen[2].getProdukt().setMenge(
						integerBoxAbsatzUNDrei.getValue());
			}
		});
					
		// Button, um die Daten von Unternehmen 2 zu löschen
		absolutePanelUnternehmenDrei.add(buttonLoeschenUNDrei, 416, 173);
		buttonLoeschenUNDrei.setSize("100px", "35px");
		buttonLoeschenUNDrei.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 3 aus DB löschen
				integerBoxUmsatzUNDrei.setValue(null);
				doubleBoxMarktanteilUNDrei.setValue(null);
				doubleBoxProduktpreisUNDrei.setValue(null);
				integerBoxAbsatzUNDrei.setValue(null);
			}
		});
																					
		// Asynchroner Call: Falls Daten vorhanden sind, aus der Datenbank
		// auslesen, ansonsten Felder im TabPanelUnternehmenAnlegen frei lassen
//		service.getEigenesUnternehmen(new GetEigenesUnternehmenCallback());
		
		startCall();
							
							
	}

	private void addElementeEUN(){
		// Firma
		absolutePanelEigenesUnternehmen.add(labelFirma, 41, 48);
		absolutePanelEigenesUnternehmen.add(textBoxFirma, 136, 36);
		// Umsatz
		absolutePanelEigenesUnternehmen.add(labelUmsatzEigenesUnternehmen, 41,105);
		absolutePanelEigenesUnternehmen.add(integerBoxUmsatzEigenesUnternehmen, 136, 93);
		integerBoxUmsatzEigenesUnternehmen.setSize("162px", "24px");
		// Marktanteil
		absolutePanelEigenesUnternehmen.add(labelMarktanteilEigenesUnternehmen,41, 159);
		absolutePanelEigenesUnternehmen.add(doubleBoxMarktanteilEigenesUnternehmen, 136, 147);
		doubleBoxMarktanteilEigenesUnternehmen.setSize("161px", "24px");
		// Fixkosten
		absolutePanelEigenesUnternehmen.add(labelFixkosten, 41, 211);
		absolutePanelEigenesUnternehmen.add(doubleBoxFixkosten, 136, 199);
		doubleBoxFixkosten.setSize("161px", "24px");
		// Produktpreis
		absolutePanelEigenesUnternehmen.add(labelProduktPreisEigenesUnternehmen, 432, 159);
		absolutePanelEigenesUnternehmen.add(doubleBoxProduktpreisEigenesUnternehmen, 577, 147);
		doubleBoxProduktpreisEigenesUnternehmen.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelEigenesUnternehmen.add(labelAbsatzmengeEigenesUnternehmen,432, 211);
		absolutePanelEigenesUnternehmen.add(integerBoxAbsatzEigenesUnternehmen,577, 199);
		integerBoxAbsatzEigenesUnternehmen.setSize("161px", "24px");
		// Anzahl Mitarbeiter
		absolutePanelEigenesUnternehmen.add(lblAnzahlMitarbeiter, 432, 48);
		absolutePanelEigenesUnternehmen.add(integerBoxAnzahlMitarbeiter, 577,36);
		integerBoxAnzahlMitarbeiter.setSize("161px", "24px");
		// durchschnittliches Mitarbeitergehalt
		absolutePanelEigenesUnternehmen.add(lblDurchMitarbeitergehalt, 432, 105);
		absolutePanelEigenesUnternehmen.add(integerBoxGehaltMitarbeiter, 577,93);
		integerBoxGehaltMitarbeiter.setSize("161px", "24px");
		
		// Maschinen
		cellTableMaschinen = new CellTable<Maschinen>();
		absolutePanelEigenesUnternehmen.add(cellTableMaschinen, 88, 277);
		cellTableMaschinen.setSize("656px", "100px");
		
		TextColumn<Maschinen> nutzungsdauerColumn = new TextColumn<Maschinen>() {
			@Override
			public String getValue(Maschinen maschinen) {
				return new Integer(maschinen.getNutzungsDauer()).toString();
			}
		};
		TextColumn<Maschinen> kapazitaetColumn = new TextColumn<Maschinen>() {
			@Override
			public String getValue(Maschinen  maschinen) {
				return new Integer(maschinen.getKapazitaet()).toString();
			}
		};
		TextColumn<Maschinen> buchwertColumn = new TextColumn<Maschinen>() {
			@Override
			public String getValue(Maschinen  maschinen) {
				return new Double(maschinen.getBuchwert()).toString();
			}
		};
		TextColumn<Maschinen> mitarbeiterColumn = new TextColumn<Maschinen>() {
			@Override
			public String getValue(Maschinen  maschinen) {
				// TODO Tabellenfeld Notwendige Mitarbeiter füllen
				return new Integer(maschinen.getNoetigeMitarbeiter()).toString();
			}
		};
		cellTableMaschinen.addColumn(nutzungsdauerColumn, "bisherige Nutzungsdauer");	
		cellTableMaschinen.addColumn(kapazitaetColumn, "Kapazit\u00e4t");
		cellTableMaschinen.addColumn(buchwertColumn, "Preis");
		cellTableMaschinen.addColumn(mitarbeiterColumn, "Notwendige Mitarbeiter");
	}
	private void addElemente1UN(){
		// Umsatz
		absolutePanelUnternehmenEins.add(labelUmsatzUNEins, 41, 48);
		absolutePanelUnternehmenEins.add(integerBoxUmsatzUNEins, 136, 36);
		integerBoxUmsatzUNEins.setSize("161", "24");
		// Marktanteil
		absolutePanelUnternehmenEins.add(labelMarkanteilUNEins, 432, 48);
		absolutePanelUnternehmenEins.add(doubleBoxMarktanteilUNEins, 577, 36);
		doubleBoxMarktanteilUNEins.setSize("161", "24");
		// Produktpreis
		absolutePanelUnternehmenEins.add(labelProduktpreisUNEins, 41, 104);
		absolutePanelUnternehmenEins.add(doubleBoxProduktpreisUNEins, 136, 92);
		doubleBoxProduktpreisUNEins.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelUnternehmenEins.add(labelAbsatzmengeUNEins, 432, 104);
		absolutePanelUnternehmenEins.add(integerBoxAbsatzUNEins, 577, 92);
		integerBoxAbsatzUNEins.setSize("161px", "24px");
				
	}
	private void addElemente2UN(){
		// Umsatz
		absolutePanelUnternehmenZwei.add(labelUmsatzUNZwei, 41, 48);
		absolutePanelUnternehmenZwei.add(integerBoxUmsatzUNZwei, 136, 36);
		integerBoxUmsatzUNZwei.setSize("161", "24");
		// Marktanteil
		absolutePanelUnternehmenZwei.add(labelMarkanteilUNZwei, 432, 48);
		absolutePanelUnternehmenZwei.add(doubleBoxMarktanteilUNZwei, 577, 36);
		doubleBoxMarktanteilUNZwei.setSize("161", "24");
		// Produktpreis
		absolutePanelUnternehmenZwei.add(labelProduktpreisUNZwei, 41, 104);
		absolutePanelUnternehmenZwei.add(doubleBoxProduktpreisUNZwei, 136, 92);
		doubleBoxProduktpreisUNZwei.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelUnternehmenZwei.add(labelAbsatzmengeUNZwei, 432, 104);
		absolutePanelUnternehmenZwei.add(integerBoxAbsatzUNZwei, 577, 92);
		integerBoxAbsatzUNZwei.setSize("161px", "24px");
	}
	private void addElemente3UN(){
		// Umsatz
		absolutePanelUnternehmenDrei.add(labelUmsatzUNDrei, 41, 48);
		absolutePanelUnternehmenDrei.add(integerBoxUmsatzUNDrei, 136, 36);
		integerBoxUmsatzUNDrei.setSize("161", "24");
		// Marktanteil
		absolutePanelUnternehmenDrei.add(labelMarkanteilUNDrei, 432, 48);
		absolutePanelUnternehmenDrei.add(doubleBoxMarktanteilUNDrei, 577, 36);
		doubleBoxMarktanteilUNDrei.setSize("161", "24");
		// Produktpreis
		absolutePanelUnternehmenDrei.add(labelProduktpreisUNDrei, 41, 104);
		absolutePanelUnternehmenDrei.add(doubleBoxProduktpreisUNDrei, 136, 92);
		doubleBoxProduktpreisUNDrei.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelUnternehmenDrei.add(labelAbsatzmengeUNDrei, 432, 104);
		absolutePanelUnternehmenDrei.add(integerBoxAbsatzUNDrei, 577, 92);
		integerBoxAbsatzUNDrei.setSize("161px", "24px");
	}
	
	
    public class AddEigenesUNCallback implements AsyncCallback<java.lang.Void> {

        @Override
        public void onFailure(Throwable caught) {
        }

        @Override
        public void onSuccess(Void result) {
            // jetzt wissen wir, das alles geklappt hat, auf dem Server.
            // Wir fÃ¼gen den neuen Wert der Liste hinzu
//            students.addItem(newName.getText());
//            
//            // und leeren das Eingabefeld.
//            newName.setText("");
        	
        	Window.alert("Das eigene Unternehmen wurde aktualisiert");
        }
    }
	
	
	
	public class GetEigenesUnternehmenCallback implements AsyncCallback<EigenesUnternehmen> {
		
        @Override
        public void onFailure(Throwable caught) { }

        @Override
        public void onSuccess(EigenesUnternehmen result) {
         	    	
        	eigenesUN = new EigenesUnternehmen();
        	eigenesUN =	result;
        	
        	ListDataProvider<Maschinen> dataEProvider = new ListDataProvider<Maschinen>();
    	    // Connect the table to the data provider.
    	    dataEProvider.addDataDisplay(cellTableMaschinen);
    			
    	   // Add the data to the data provider, which automatically pushes it to the widget.
    	    maschinenEUN = dataEProvider.getList();       	 	    
        	maschinenEUN.add(result.getMaschinen());
        	       	
        	
	       	textBoxFirma.setText(result.getFirma());
	       	integerBoxUmsatzEigenesUnternehmen.setValue(result.getUmsatz());
	       	doubleBoxMarktanteilEigenesUnternehmen.setValue(result.getMarktAnteil());
	       	doubleBoxFixkosten.setValue(result.getFixkosten());
	       	doubleBoxProduktpreisEigenesUnternehmen.setValue(result.getProdukt().getPreis());
	       	integerBoxAbsatzEigenesUnternehmen.setValue(result.getProdukt().getMenge());
	       	integerBoxAnzahlMitarbeiter.setValue(result.getMitarbeiterAnzahl()); 
	       	integerBoxGehaltMitarbeiter.setValue(result.getMitarbeiterGehalt());
        	
        	
     
        }
    }
	private void startCall() {
		service.getEigenesUnternehmen(new GetEigenesUnternehmenCallback());
		
	}
}
//		
//		// Asynchroner Service, der die Daten des eigenen Unternehmens aus der
//		// Datenbank holt
//		AsyncCallback<EigenesUnternehmen> callback2 = new AsyncCallback<EigenesUnternehmen>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO on Failure
//
//			}
//
//			@Override
//			public void onSuccess(EigenesUnternehmen result) {
//				// Daten des eigenen Unternehmens
//				eigenesUnternehmen.setFixkosten(result.getFixkosten());
//				eigenesUnternehmen.setGewinn(result.getGewinn());
//				eigenesUnternehmen.setMarktAnteil(result.getMarktAnteil());
//				eigenesUnternehmen.setMaschinen(result.getMaschinen());
//				eigenesUnternehmen.setMitarbeiterGehalt(result
//						.getMitarbeiterGehalt());
//				eigenesUnternehmen.setNachfrageTendenz(result
//						.getNachfrageTendenz());
//				eigenesUnternehmen.setProdukt(result.getProdukt());
//				eigenesUnternehmen.setUmsatz(result.getUmsatz());
//				eigenesUnternehmen.setFirma(result.getFirma());
//
//				if (eigenesUnternehmen != null) {
//					textBoxFirma.setText(eigenesUnternehmen.getFirma());
//					integerBoxUmsatzEigenesUnternehmen
//							.setValue(eigenesUnternehmen.getUmsatz());
//					doubleBoxMarktanteilEigenesUnternehmen
//							.setValue(eigenesUnternehmen.getMarktAnteil());
//					doubleBoxProduktpreisEigenesUnternehmen
//							.setValue(eigenesUnternehmen.getProdukt()
//									.getPreis());
//					integerBoxAbsatzEigenesUnternehmen
//							.setValue(eigenesUnternehmen.getProdukt()
//									.getMenge());
//					integerBoxAnzahlMitarbeiter.setValue(eigenesUnternehmen
//							.getMitarbeiterAnzahl());
//					integerBoxGehaltMitarbeiter.setValue(eigenesUnternehmen
//							.getMitarbeiterGehalt());
//					// TODO Maschinen in cellTableMaschinen übernehmen
//				}
//
//			}
//		};

//		service.getEigenesUnternehmen(callback2);
//		// Asynchroner Service, der die Daten der Konkurrenzunternehmen aus der
//		// Datenbank holt
//		AsyncCallback<List<Unternehmen>> callback = new AsyncCallback<List<Unternehmen>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO on Failure
//
//			}
//
//			@Override
//			public void onSuccess(List<Unternehmen> result) {
//				// Unternehmen aus der DB übernehmen
//				for (int i = 0; i < result.size(); i++) {
//					unternehmen[i] = new Unternehmen();
//					unternehmen[i].setGewinn(result.get(i).getGewinn());
//					unternehmen[i].setMarktAnteil(result.get(i)
//							.getMarktAnteil());
//					unternehmen[i].setNachfrageTendenz(result.get(i)
//							.getNachfrageTendenz());
//					unternehmen[i].setProdukt(result.get(i).getProdukt());
//					unternehmen[i].setUmsatz(result.get(i).getUmsatz());
//				}
//				// Übernehmen der Daten in die Felder
//				unternehmenBefuellen();
//
//			}
//		};
//		service.getUnternehmen(callback);

//	}

//	private void unternehmenBefuellen() {
//
//		// Falls Daten in der Datenbank vorhanden sind, werden sie in
//		// den Boxen angezeigt
//		if (unternehmen[0] != null) {
//			integerBoxUmsatzUNEins.setValue(unternehmen[0].getUmsatz());
//			doubleBoxMarktanteilUNEins
//					.setValue(unternehmen[0].getMarktAnteil());
//			doubleBoxProduktpreisUNEins.setValue(unternehmen[0].getProdukt()
//					.getPreis());
//			integerBoxAbsatzUNEins.setValue(unternehmen[0].getProdukt()
//					.getMenge());
//		}
//		if (unternehmen[1] != null) {
//			integerBoxUmsatzUNZwei.setValue(unternehmen[1].getUmsatz());
//			doubleBoxMarktanteilUNZwei
//					.setValue(unternehmen[1].getMarktAnteil());
//			doubleBoxProduktpreisUNZwei.setValue(unternehmen[1].getProdukt()
//					.getPreis());
//			integerBoxAbsatzUNZwei.setValue(unternehmen[1].getProdukt()
//					.getMenge());
//		}
//		if (unternehmen[2] != null) {
//			integerBoxUmsatzUNDrei.setValue(unternehmen[2].getUmsatz());
//			doubleBoxMarktanteilUNDrei
//					.setValue(unternehmen[2].getMarktAnteil());
//			doubleBoxProduktpreisUNDrei.setValue(unternehmen[2].getProdukt()
//					.getPreis());
//			integerBoxAbsatzUNDrei.setValue(unternehmen[2].getProdukt()
//					.getMenge());
//		}
//	}

