package de.dhbw.wwi11sca.skdns.client.simulation;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import de.dhbw.wwi11sca.skdns.client.home.*;
import de.dhbw.wwi11sca.skdns.shared.*;

public class Simulation implements EntryPoint{
	
	AbsolutePanel absolutePanelSimulation = new AbsolutePanel();
	Label labelAusgeloggt = new Label("Sie wurden erfolgreich ausgeloggt.");
	Label labelHome = new Label("Home");
	Label lblSimulation = new Label(">  Simulation");
	Button btnLogout = new Button("Logout");
	HorizontalPanel horizontalPanel = new HorizontalPanel();
	AbsolutePanel absolutePanelEigenesUN = new AbsolutePanel();
	AbsolutePanel [] absolutePanelUnternehmen = new AbsolutePanel[3];	
	AbsolutePanel[] absolutePanelJahre = new AbsolutePanel[100];
	Label labelEigenesUnternehmen = new Label("Eigenes Unternehmen");
	Label labelUmsatz = new Label("Umsatz:");
	Label labelGewinn = new Label("Gewinn:");
	Label labelMarktanteil = new Label("Marktanteil:");
	Label labelNachfrageTendenz = new Label("Nachfragetendenz:");
	Label labelUmsatzEUN = new Label("0.00\u20AC");
	Label labelGewinnEUN = new Label("0.00\u20AC");
	Label labelMarktanteilEUN = new Label("0%");
	Label labelNachfrageEUN = new Label("steigend");
	Label [] labelUnternehmen = new Label[3];
	Label [] labelUmsatzUnternehmen = new Label  [3];
	Label [] labelUmsatzUnternehmenWert = new Label [3];
	Label [] labelGewinnUnternehmen = new Label[3];
	Label [] labelGewinnUnternehmenWert = new Label[3];
	Label [] labelMarktanteilUnternehmen = new Label[3];
	Label [] labelMarktanteilUnternehmenWert = new Label[3];
	Label [] labelNachfrageTendenzUnternehmen = new Label [3];
	Label [] labelNachfrageTendenzUnternehmenWert = new Label [3];
	AbsolutePanel absolutePanelInvestitionen = new AbsolutePanel();
	Label labelInvestitionen = new Label("Investitionen Eigenes Unternehmen");
	IntegerBox integerBoxMarketing = new IntegerBox();
	IntegerBox integerBoxMaschinenWert = new IntegerBox();
	IntegerBox integerBoxPersonal = new IntegerBox();
	Label labelMarketing = new Label("Marketing:");
	Label labelMaschinen = new Label("Maschinen:");
	Label labelPersonal = new Label("Personal:");
	Button buttonSimulation = new Button("Simulation starten");
	Button buttonFolgejahr = new Button("Folgejahr");
	AbsolutePanel absolutePanelJahrEins = new AbsolutePanel();
	TabPanel tabPanelJahre = new TabPanel();
	int jahr = 2;
	int anzahlUnternehmen;
	int version = 1;
	int simulationsJahr = 2012;
	EigenesUnternehmen eigenesUN = new EigenesUnternehmen();
	Unternehmen [] unternehmen = new Unternehmen[3];
	Label labelAnzahlMitarbeiter = new Label("Anzahl Mitarbeiter:");
	Label labelKapazitaet = new Label("Maschinenkapazitaet:");
	Label labelAnzahlMitarbeiterWert = new Label("0");
	Label labelKapazitaetWert = new Label("0");
	Label labelMaschinenWert = new Label("Wert:");
	IntegerBox integerBoxKapazitaet = new IntegerBox();
	Label labelMaschinenKapazitaet = new Label("Kapazität:");
	Label lblNtigeMitarbeiter = new Label("nötige Mitarbeiter:");
	IntegerBox integerBoxMaschinenMitarbeiter = new IntegerBox();
	
	private SimulationServiceAsync service = GWT.create(SimulationService.class);
	
	@Override
	public void onModuleLoad() {
		// allgemeine Panels
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");
		rootPanel.add(absolutePanelSimulation, 0, 10);
		absolutePanelSimulation.setSize("1056px", "768px");
		
		unternehmenAktualisieren();
		eigenesUnternehmenAktualisieren();
	
		// Panel, auf dem die Unternehmeninformationen angezeigt werden
				absolutePanelSimulation.add(horizontalPanel, 60, 77);
				horizontalPanel.setSize("894px", "183px");
					// Eigenes Unternehmen		
					horizontalPanel.add(absolutePanelEigenesUN);
					absolutePanelEigenesUN.setHeight("184px");
						// Label
						labelEigenesUnternehmen.setStyleName("gwt-UnternehmenLabel");
						labelEigenesUnternehmen.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
						absolutePanelEigenesUN.add(labelEigenesUnternehmen, 0, 0);
						labelEigenesUnternehmen.setSize("224px", "18px");		
						absolutePanelEigenesUN.add(labelUmsatz, 10, 24);		
						absolutePanelEigenesUN.add(labelGewinn, 10, 48);		
						absolutePanelEigenesUN.add(labelMarktanteil, 10, 72);
						absolutePanelEigenesUN.add(labelNachfrageTendenz, 10, 96);
						absolutePanelEigenesUN.add(labelUmsatzEUN, 142, 24);		
						labelUmsatzEUN.setSize("61px", "18px");
						absolutePanelEigenesUN.add(labelGewinnEUN, 142, 48);
						labelGewinnEUN.setSize("61px", "18px");
						absolutePanelEigenesUN.add(labelMarktanteilEUN, 142, 72);
						labelMarktanteilEUN.setSize("61px", "18px");
						absolutePanelEigenesUN.add(labelNachfrageEUN, 142, 96);
						labelNachfrageEUN.setSize("61px", "18px");
						absolutePanelEigenesUN.add(labelAnzahlMitarbeiter, 10, 120);
						labelAnzahlMitarbeiter.setSize("109px", "18px");
						absolutePanelEigenesUN.add(labelKapazitaet, 10, 144);
						absolutePanelEigenesUN.add(labelAnzahlMitarbeiterWert, 142, 120);
						labelAnzahlMitarbeiterWert.setSize("61px", "18px");
						absolutePanelEigenesUN.add(labelKapazitaetWert, 142, 144);
						labelKapazitaetWert.setSize("61px", "18px");
					// weitere Unternehmen
						// TODO: absolutePanelUnternehmen.length durch anzahlUnternehmen ersetzen, wenn db amgebunden
					for (int i=0; i< absolutePanelUnternehmen.length; i++){
						int j = i+1;
						// neues Panel erzeugen 
						absolutePanelUnternehmen[i] = new AbsolutePanel();
						horizontalPanel.add(absolutePanelUnternehmen[i]);
						absolutePanelUnternehmen[i].setHeight("159px");
						// "Überschrift" anbringen
						labelUnternehmen[i] = new Label("Unternehmen " + j);
						labelUnternehmen[i].setStyleName("gwt-UnternehmenLabel");
						labelUnternehmen[i].setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
						absolutePanelUnternehmen[i].add(labelUnternehmen[i],0,0);				
						labelUnternehmen[i].setSize("224px", "18px");
						// neue Labels erzeugen
						labelUmsatzUnternehmen[i] = new Label("Umsatz:");
						labelGewinnUnternehmen[i] = new Label("Gewinn: ");
						labelMarktanteilUnternehmen[i] = new Label("Marktanteil:");
						labelNachfrageTendenzUnternehmen[i] = new Label("Nachfragetendenz:");				
						// Labels auf dem Panel anbringen
						absolutePanelUnternehmen[i].add(labelUmsatzUnternehmen[i], 10, 24);		
						absolutePanelUnternehmen[i].add(labelGewinnUnternehmen[i], 10, 48);		
						absolutePanelUnternehmen[i].add(labelMarktanteilUnternehmen[i], 10, 72);
						absolutePanelUnternehmen[i].add(labelNachfrageTendenzUnternehmen[i], 10, 96);				
						absolutePanelUnternehmen[i].add(labelUmsatzUnternehmenWert[i], 129, 24);		
						absolutePanelUnternehmen[i].add(labelGewinnUnternehmenWert[i], 129, 48);
						labelGewinnUnternehmenWert[i].setSize("32px", "18px");		
						absolutePanelUnternehmen[i].add(labelMarktanteilUnternehmenWert[i], 129, 72);
						labelMarktanteilUnternehmenWert[i].setSize("32px", "18px");		
						absolutePanelUnternehmen[i].add(labelNachfrageTendenzUnternehmenWert[i], 129, 96);
						labelNachfrageTendenzUnternehmenWert[i].setSize("49px", "18px");				
					}
				
		// Panel, um die Investitionen zu tätigen		
		absolutePanelSimulation.add(absolutePanelInvestitionen, 84, 298);
		absolutePanelInvestitionen.setSize("700px", "151px");
		
		labelInvestitionen.setStyleName("gwt-UnternehmenLabel");
		absolutePanelInvestitionen.add(labelInvestitionen, 10, 10);
		labelInvestitionen.setSize("282px", "18px");
		
		// Marketing Investitionen
		absolutePanelInvestitionen.add(labelMarketing, 20, 45);
		absolutePanelInvestitionen.add(integerBoxMarketing, 86, 34);
		integerBoxMarketing.setSize("94px", "25px");
		
		// Maschinen Investitionen
		absolutePanelInvestitionen.add(labelMaschinen, 14, 83);
		absolutePanelInvestitionen.add(integerBoxMaschinenWert, 86, 110);
		integerBoxMaschinenWert.setSize("94px", "25px");
		absolutePanelInvestitionen.add(labelMaschinenWert, 37, 123);
		labelMaschinenWert.setSize("60px", "18px");		
		absolutePanelInvestitionen.add(integerBoxKapazitaet, 300, 110);
		integerBoxKapazitaet.setSize("94px", "25px");		
		absolutePanelInvestitionen.add(labelMaschinenKapazitaet, 236, 123);		
		absolutePanelInvestitionen.add(integerBoxMaschinenMitarbeiter, 520, 110);
		integerBoxMaschinenMitarbeiter.setSize("94px", "25px");		
		absolutePanelInvestitionen.add(lblNtigeMitarbeiter, 410, 123);
		
		// Personal Investitionen
		absolutePanelInvestitionen.add(labelPersonal, 239, 45);
		absolutePanelInvestitionen.add(integerBoxPersonal, 300, 34);
		integerBoxPersonal.setSize("94px", "25px");		
		
		
		
		// Button, um die Simulation mit den getätigten Investitionen zu starten	
		absolutePanelSimulation.add(buttonSimulation, 844, 310);
		buttonSimulation.setSize("127px", "35px");
		buttonSimulation.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Daten aus den IntegerBoxen ablegen
				Simulationsversion simulation = new Simulationsversion(simulationsJahr, version);
				simulation.setMaschineKapazitaet(integerBoxKapazitaet.getValue());
				simulation.setMaschineMitarbeiter(integerBoxMaschinenMitarbeiter.getValue());
				simulation.setPersonal(integerBoxMaschinenWert.getValue());
				simulation.setPersonal(integerBoxPersonal.getValue());
				simulation.setMarketing(integerBoxMarketing.getValue());				
				version++;
				
				AsyncCallback<Simulationsversion> callback = new AsyncCallback<Simulationsversion>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO onFailure
						
					}

					@Override
					public void onSuccess(Simulationsversion result) {
						Simulationsversion simulationsErgebnis = new Simulationsversion(result.getSimulationsJahr(), result.getVersion());
						simulationsErgebnis.setUnternehmen(result.getUnternehmen());
						simulationsErgebnis.setGewinn(result.getGewinn());
						simulationsErgebnis.setUmsatz(result.getUmsatz());
						simulationsErgebnis.setMarktAnteil(result.getMarktAnteil());
						simulationsErgebnis.setNachfrageTendenz(result.getNachfrageTendenz());

						// TODO: Simulationsergebnisse anzeigen
					}

					

					
					
				};
				
				service.getVersion(callback);
				// TODO Simulation starten
			}
		});
		
		// Button, um einen weiteren Tab für ein weiteres Jahr zu tätigen	
		absolutePanelSimulation.add(buttonFolgejahr, 844, 368);
		buttonFolgejahr.setSize("127px", "35px");
		buttonFolgejahr.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Simulationsjahr erhöhen
				simulationsJahr++;
				// weiteren Tab hinzufügen			
				absolutePanelJahre[jahr-2] = new AbsolutePanel();
				tabPanelJahre.add(absolutePanelJahre[jahr-2], "Jahr" + jahr , false);
				absolutePanelJahre[jahr-2].setSize("100%", "238");
				// IntegerBoxen leeren
				integerBoxMarketing.setValue(null);
				integerBoxMaschinenWert.setValue(null);
				integerBoxPersonal.setValue(null);
				integerBoxMaschinenMitarbeiter.setValue(null);
				integerBoxKapazitaet.setValue(null);
				jahr++;				
				
			}
		});
		
			
		// Label, dass zeigt, in welcher Sicht der User sich befindet
				absolutePanelSimulation.add(labelHome, 30, 30);
				labelHome.setStyleName("gwt-Home-Label");
				absolutePanelSimulation.add(lblSimulation, 70, 30);
				
				
				// Button, um auf die Home-Seite zurückzukehren
				labelHome.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						RootPanel.get().clear();
						HomeSimulation home = new HomeSimulation();
						home.onModuleLoad();
					}
				});
				
				// Logout-Button
				absolutePanelSimulation.add(btnLogout, 948, 10);
				btnLogout.setSize("100px", "35px");
				btnLogout.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						// TODO: Daten an DB übergeben
						
						RootPanel.get().clear();
						RootPanel.get().add(labelAusgeloggt);
					}
				});
				
				
				// TabPanel, auf dem die Ergebnisse der Simulation angezeigt werden
				absolutePanelSimulation.add(tabPanelJahre, 60, 484);
				tabPanelJahre.setSize("894px", "268px");
				// erstes Jahr				
				tabPanelJahre.add(absolutePanelJahrEins, "Jahr 1", false);
				absolutePanelJahrEins.setSize("100%", "238");
				
				
				
	}
	
	public void unternehmenAktualisieren(){
				
		AsyncCallback<List<Unternehmen>> callback = new AsyncCallback<List<Unternehmen>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO on Failure
				
			}

			@Override
			public void onSuccess(List<Unternehmen> result) {
				// Unternehmen aus der DB übernehmen
				anzahlUnternehmen = result.size();
				for (int i=0; i< result.size(); i++){
					unternehmen[i] = new Unternehmen();
					unternehmen[i].setGewinn(result.get(i).getGewinn());
					unternehmen[i].setMarktAnteil(result.get(i).getMarktAnteil());
					unternehmen[i].setNachfrageTendenz(result.get(i).getNachfrageTendenz());
					unternehmen[i].setProdukt(result.get(i).getProdukt());
					unternehmen[i].setUmsatz(result.get(i).getUmsatz());
				}
				
			}

			
		};
		
		service.getUnternehmen(callback);
		
		//Werte aus der DB übernehmen
		// TODO: absolutePanelUnternehmen.length durch anzahlUnternehmen ersetzen, wenn db amgebunden
		for (int i=0; i< absolutePanelUnternehmen.length ; i++){
		
			labelUmsatzUnternehmenWert[i] = new Label(unternehmen[i].getUmsatz() + " €");
			labelGewinnUnternehmenWert[i] = new Label(unternehmen[i].getGewinn() + " €");
			labelMarktanteilUnternehmenWert[i] = new Label(unternehmen[i].getMarktAnteil() + " %");
			labelNachfrageTendenzUnternehmenWert[i] = new Label(unternehmen[i].getNachfrageTendenz() + "");
		}
		
		
	}

	public void eigenesUnternehmenAktualisieren(){
		AsyncCallback<EigenesUnternehmen> callback = new AsyncCallback<EigenesUnternehmen>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO on Failure
				
			}

			@Override
			public void onSuccess(EigenesUnternehmen result) {
				// Daten des eigenen Unternehmens 
				eigenesUN.setFixkosten(result.getFixkosten());
				eigenesUN.setGewinn(result.getGewinn());
				eigenesUN.setMarktAnteil(result.getMarktAnteil());
				eigenesUN.setMaschinen(result.getMaschinen());
				eigenesUN.setMitarbeiterGehalt(result.getMitarbeiterGehalt());
				eigenesUN.setNachfrageTendenz(result.getNachfrageTendenz());
				eigenesUN.setProdukt(result.getProdukt());
				eigenesUN.setUmsatz(result.getUmsatz());
			}
		};
		
		service.getEigenesUnternehmen(callback);
		
		labelUmsatzEUN.setText(eigenesUN.getUmsatz() + " €");
		labelGewinnEUN.setText(eigenesUN.getGewinn() + " €");
		labelMarktanteilEUN.setText(eigenesUN.getMarktAnteil() + " %");
		labelNachfrageEUN.setText(eigenesUN.getNachfrageTendenz() + "");
		labelAnzahlMitarbeiterWert.setText(eigenesUN.getMitarbeiterAnzahl() + "");
		labelKapazitaetWert.setText(eigenesUN.getMaschinen().getKapazitaet() + "");
		
	}

}
