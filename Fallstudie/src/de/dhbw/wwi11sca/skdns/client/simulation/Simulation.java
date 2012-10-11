package de.dhbw.wwi11sca.skdns.client.simulation;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.dhbw.wwi11sca.skdns.client.home.*;
import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation.GetEigenesUnternehmenCallback;
import de.dhbw.wwi11sca.skdns.shared.*;

public class Simulation implements EntryPoint {

	AbsolutePanel absolutePanelSimulation = new AbsolutePanel();
	Label labelAusgeloggt = new Label("Sie wurden erfolgreich ausgeloggt.");
	Label labelHome = new Label("Home");
	Label lblSimulation = new Label(">  Simulation");
	Button btnLogout = new Button("Logout");
	HorizontalPanel horizontalPanel = new HorizontalPanel();
	AbsolutePanel absolutePanelEigenesUN = new AbsolutePanel();
	AbsolutePanel[] absolutePanelUnternehmen = new AbsolutePanel[3];
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
	Label[] labelUnternehmen = new Label[3];
	Label[] labelUmsatzUnternehmen = new Label[3];
	Label[] labelUmsatzUnternehmenWert = new Label[3];
	Label[] labelGewinnUnternehmen = new Label[3];
	Label[] labelGewinnUnternehmenWert = new Label[3];
	Label[] labelMarktanteilUnternehmen = new Label[3];
	Label[] labelMarktanteilUnternehmenWert = new Label[3];
	Label[] labelNachfrageTendenzUnternehmen = new Label[3];
	Label[] labelNachfrageTendenzUnternehmenWert = new Label[3];
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
	Unternehmen[] unternehmen = new Unternehmen[3];
	Label labelAnzahlMitarbeiter = new Label("Anzahl Mitarbeiter:");
	Label labelKapazitaet = new Label("Maschinenkapazitaet:");
	Label labelAnzahlMitarbeiterWert = new Label("0");
	Label labelKapazitaetWert = new Label("0");
	Label labelMaschinenWert = new Label("Wert:");
	IntegerBox integerBoxKapazitaet = new IntegerBox();
	Label labelMaschinenKapazitaet = new Label("Kapazit�t:");
	Label lblNtigeMitarbeiter = new Label("n�tige Mitarbeiter:");
	IntegerBox integerBoxMaschinenMitarbeiter = new IntegerBox();
	
	CellTable<EigenesUnternehmen> tableEigenesUnternehmen;
	List<EigenesUnternehmen> unEList;
	private SimulationServiceAsync service = GWT.create(SimulationService.class);

	@Override
	public void onModuleLoad() {
		// allgemeine Panels
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");
		rootPanel.add(absolutePanelSimulation, 0, 0);
		absolutePanelSimulation.setSize("844px", "768px");

		unternehmenAktualisieren();

		// Panel, um die Investitionen zu t�tigen
		absolutePanelSimulation.add(absolutePanelInvestitionen, 84, 276);
		absolutePanelInvestitionen.setSize("700px", "151px");

		labelInvestitionen.setStyleName("gwt-UnternehmenLabel");
		absolutePanelInvestitionen.add(labelInvestitionen, 10, 10);
		labelInvestitionen.setSize("282px", "18px");

		uebersicht();
		
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
		absolutePanelInvestitionen
				.add(integerBoxMaschinenMitarbeiter, 520, 110);
		integerBoxMaschinenMitarbeiter.setSize("94px", "25px");
		absolutePanelInvestitionen.add(lblNtigeMitarbeiter, 410, 123);

		// Personal Investitionen
		absolutePanelInvestitionen.add(labelPersonal, 239, 45);
		absolutePanelInvestitionen.add(integerBoxPersonal, 300, 34);
		integerBoxPersonal.setSize("94px", "25px");

		// Button, um die Simulation mit den get�tigten Investitionen zu starten
		absolutePanelSimulation.add(buttonSimulation, 844, 310);
		buttonSimulation.setSize("127px", "35px");
		buttonSimulation.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Daten aus den IntegerBoxen ablegen
				Simulationsversion simulation = new Simulationsversion(
						simulationsJahr, version);
				simulation.setMaschineKapazitaet(integerBoxKapazitaet
						.getValue());
				simulation
						.setMaschineMitarbeiter(integerBoxMaschinenMitarbeiter
								.getValue());
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
						Simulationsversion simulationsErgebnis = new Simulationsversion(
								result.getSimulationsJahr(), result
										.getVersion());
						simulationsErgebnis.setGewinn(result.getGewinn());
						simulationsErgebnis.setUmsatz(result.getUmsatz());
						simulationsErgebnis.setMarktAnteil(result
								.getMarktAnteil());
						simulationsErgebnis.setNachfrageTendenz(result
								.getNachfrageTendenz());

						// TODO: Simulationsergebnisse anzeigen
					}

				};

				service.getVersion(callback);
				// TODO Simulation starten
			}
		});

		// Button, um einen weiteren Tab f�r ein weiteres Jahr zu t�tigen
		absolutePanelSimulation.add(buttonFolgejahr, 844, 368);
		buttonFolgejahr.setSize("127px", "35px");
		buttonFolgejahr.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Simulationsjahr erh�hen
				simulationsJahr++;
				// weiteren Tab hinzuf�gen
				absolutePanelJahre[jahr - 2] = new AbsolutePanel();
				tabPanelJahre.add(absolutePanelJahre[jahr - 2], "Jahr" + jahr,
						false);
				absolutePanelJahre[jahr - 2].setSize("100%", "238");
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

		// Button, um auf die Home-Seite zur�ckzukehren
		labelHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				HomeSimulation home = new HomeSimulation();
				home.onModuleLoad();
			}
		});

		// Logout-Button
		absolutePanelSimulation.add(btnLogout, 914, 10);
		btnLogout.setSize("100px", "35px");
		btnLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Daten an DB �bergeben

				RootPanel.get().clear();
				RootPanel.get().add(labelAusgeloggt);
			}
		});

		// TabPanel, auf dem die Ergebnisse der Simulation angezeigt werden
		absolutePanelSimulation.add(tabPanelJahre, 60, 458);
		tabPanelJahre.setSize("894px", "268px");
		// erstes Jahr
		tabPanelJahre.add(absolutePanelJahrEins, "Jahr 1", false);
		absolutePanelJahrEins.setSize("100%", "238");

	}

	
	public class GetEigenesUnternehmenCallback implements AsyncCallback<EigenesUnternehmen> {
		
        @Override
        public void onFailure(Throwable caught) { }

        @Override
        public void onSuccess(EigenesUnternehmen result) {
        	
    		ListDataProvider<EigenesUnternehmen> dataEProvider = new ListDataProvider<EigenesUnternehmen>();
    	    // Connect the table to the data provider.
    	    dataEProvider.addDataDisplay(tableEigenesUnternehmen);
    			
    	   // Add the data to the data provider, which automatically pushes it to the widget.
    	    unEList = dataEProvider.getList();
        	unEList.add(result);
        	
     
        }
    }
	
	
	
	public void uebersicht(){
		tableEigenesUnternehmen = new CellTable<EigenesUnternehmen>();
		absolutePanelSimulation.add(tableEigenesUnternehmen, 0, 144);
		tableEigenesUnternehmen.setSize("500px", "100px");
		
		TextColumn<EigenesUnternehmen> firmaEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return unternehmen.getFirma();
			}			
		};
		TextColumn<EigenesUnternehmen> umsatzEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getUmsatz()).toString();
			}			
		};
		TextColumn<EigenesUnternehmen> gewinnEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getGewinn()).toString();
			}			
		};
		TextColumn<EigenesUnternehmen> marktAnteilEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Double(unternehmen.getMarktAnteil()).toString();
			}			
		};
		TextColumn<EigenesUnternehmen> produktMengeEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getProdukt().getMenge()).toString();
			}			
		};
		TextColumn<EigenesUnternehmen> produktPreisEColumn = new TextColumn<EigenesUnternehmen>(){
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Double(unternehmen.getProdukt().getPreis()).toString();
			}			
		};

		tableEigenesUnternehmen.addColumn(firmaEColumn, "Firma");
		tableEigenesUnternehmen.addColumn(umsatzEColumn, "Umsatz");
		tableEigenesUnternehmen.addColumn(gewinnEColumn, "Gewinn");
		tableEigenesUnternehmen.addColumn(marktAnteilEColumn, "Marktanteil");
		tableEigenesUnternehmen.addColumn(produktPreisEColumn, "Produktpreis");
		tableEigenesUnternehmen.addColumn(produktMengeEColumn, "Absatzmenge");
		
		service.getEigenesUnternehmen(new GetEigenesUnternehmenCallback());
		
		
		
	}
	public void unternehmenAktualisieren() {
		// Asynchroner Service, der die Daten des eigenen Unternehmens aus der
		// Datenbank holt
		AsyncCallback<EigenesUnternehmen> callback2 = new AsyncCallback<EigenesUnternehmen>() {

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

				labelUmsatzEUN.setText(eigenesUN.getUmsatz() + " �");
				labelGewinnEUN.setText(eigenesUN.getGewinn() + " �");
				labelMarktanteilEUN.setText(eigenesUN.getMarktAnteil() + " %");
				labelNachfrageEUN.setText(eigenesUN.getNachfrageTendenz() + "");
				labelAnzahlMitarbeiterWert.setText(eigenesUN
						.getMitarbeiterAnzahl() + "");
				labelKapazitaetWert.setText(eigenesUN.getMaschinen()
						.getKapazitaet() + "");
			}
		};

		service.getEigenesUnternehmen(callback2);
		// Asynchroner Service, der die Daten der Konkurrenzunternehmen aus der
		// Datenbank holt
		AsyncCallback<List<Unternehmen>> callback = new AsyncCallback<List<Unternehmen>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO on Failure

			}

			@Override
			public void onSuccess(List<Unternehmen> result) {
				// Unternehmen aus der DB �bernehmen
				anzahlUnternehmen = result.size();
				for (int i = 0; i < result.size(); i++) {
					unternehmen[i] = new Unternehmen();
					unternehmen[i].setGewinn(result.get(i).getGewinn());
					unternehmen[i].setMarktAnteil(result.get(i)
							.getMarktAnteil());
					unternehmen[i].setNachfrageTendenz(result.get(i)
							.getNachfrageTendenz());
					unternehmen[i].setProdukt(result.get(i).getProdukt());
					unternehmen[i].setUmsatz(result.get(i).getUmsatz());

				}
				befuellen(anzahlUnternehmen);
			}

		};

		service.getUnternehmen(callback);

	}

	// befuellt die auf der Oberfl�che angezeigten Labels mit den aus der
	// Datenbank gelieferten Daten
	public void befuellen(int anzahlUnternehmen) {
		// Panel, auf dem die Unternehmeninformationen angezeigt werden
		absolutePanelSimulation.add(horizontalPanel, 60, 77);
		horizontalPanel.setSize("894px", "183px");
		// Eigenes Unternehmen
		horizontalPanel.add(absolutePanelEigenesUN);
		absolutePanelEigenesUN.setHeight("184px");
		// Label
		labelEigenesUnternehmen.setStyleName("gwt-UnternehmenLabel");
		labelEigenesUnternehmen
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		absolutePanelEigenesUN.add(labelEigenesUnternehmen, 0, 0);
		labelEigenesUnternehmen.setSize("224px", "18px");
		// Umsatz
		absolutePanelEigenesUN.add(labelUmsatz, 10, 24);
		absolutePanelEigenesUN.add(labelUmsatzEUN, 142, 24);
		labelUmsatzEUN.setSize("61px", "18px");
		// Gewinn
		absolutePanelEigenesUN.add(labelGewinn, 10, 48);
		absolutePanelEigenesUN.add(labelGewinnEUN, 142, 48);
		labelGewinnEUN.setSize("61px", "18px");
		// Marktanteil
		absolutePanelEigenesUN.add(labelMarktanteil, 10, 72);
		absolutePanelEigenesUN.add(labelMarktanteilEUN, 142, 72);
		labelMarktanteilEUN.setSize("61px", "18px");
		// Nachfragetendenz
		absolutePanelEigenesUN.add(labelNachfrageTendenz, 10, 96);
		absolutePanelEigenesUN.add(labelNachfrageEUN, 142, 96);
		labelNachfrageEUN.setSize("61px", "18px");
		// Anzahl der Mitarbeiter
		absolutePanelEigenesUN.add(labelAnzahlMitarbeiter, 10, 120);
		labelAnzahlMitarbeiter.setSize("109px", "18px");
		absolutePanelEigenesUN.add(labelAnzahlMitarbeiterWert, 142, 120);
		labelAnzahlMitarbeiterWert.setSize("61px", "18px");
		// Maschinenkapazitaet
		absolutePanelEigenesUN.add(labelKapazitaet, 10, 144);
		absolutePanelEigenesUN.add(labelKapazitaetWert, 142, 144);
		labelKapazitaetWert.setSize("61px", "18px");

		// weitere Unternehmen
		for (int i = 0; i < anzahlUnternehmen; i++) {
			int j = i + 1;
			// neues Panel erzeugen
			absolutePanelUnternehmen[i] = new AbsolutePanel();
			horizontalPanel.add(absolutePanelUnternehmen[i]);
			absolutePanelUnternehmen[i].setHeight("159px");
			// "�berschrift" anbringen
			labelUnternehmen[i] = new Label("Unternehmen " + j);
			labelUnternehmen[i].setStyleName("gwt-UnternehmenLabel");
			labelUnternehmen[i]
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			absolutePanelUnternehmen[i].add(labelUnternehmen[i], 0, 0);
			labelUnternehmen[i].setSize("224px", "18px");
			// neue Labels erzeugen und auf dem Panel anbringen
			// Umsatz
			labelUmsatzUnternehmen[i] = new Label("Umsatz:");
			absolutePanelUnternehmen[i].add(labelUmsatzUnternehmen[i], 10, 24);
			labelUmsatzUnternehmenWert[i] = new Label(
					unternehmen[i].getUmsatz() + " �");
			absolutePanelUnternehmen[i].add(labelUmsatzUnternehmenWert[i], 129,
					24);
			// Gewinn
			labelGewinnUnternehmen[i] = new Label("Gewinn: ");
			absolutePanelUnternehmen[i].add(labelGewinnUnternehmen[i], 10, 48);
			labelGewinnUnternehmenWert[i] = new Label(
					unternehmen[i].getGewinn() + " �");
			absolutePanelUnternehmen[i].add(labelGewinnUnternehmenWert[i], 129,
					48);
			labelGewinnUnternehmenWert[i].setSize("32px", "18px");
			// Marktanteil
			labelMarktanteilUnternehmen[i] = new Label("Marktanteil:");
			absolutePanelUnternehmen[i].add(labelMarktanteilUnternehmen[i], 10,
					72);
			labelMarktanteilUnternehmenWert[i] = new Label(
					unternehmen[i].getMarktAnteil() + " %");
			absolutePanelUnternehmen[i].add(labelMarktanteilUnternehmenWert[i],
					129, 72);
			labelMarktanteilUnternehmenWert[i].setSize("49px", "18px");
			// Nachfragetendenz
			labelNachfrageTendenzUnternehmen[i] = new Label("Nachfragetendenz:");
			absolutePanelUnternehmen[i].add(
					labelNachfrageTendenzUnternehmen[i], 10, 96);
			labelNachfrageTendenzUnternehmenWert[i] = new Label(
					unternehmen[i].getNachfrageTendenz());
			absolutePanelUnternehmen[i].add(
					labelNachfrageTendenzUnternehmenWert[i], 129, 96);
			labelNachfrageTendenzUnternehmenWert[i].setSize("49px", "18px");

		}
	}

}
