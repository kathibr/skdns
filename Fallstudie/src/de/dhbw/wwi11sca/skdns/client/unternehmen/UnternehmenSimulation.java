package de.dhbw.wwi11sca.skdns.client.unternehmen;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation;
import de.dhbw.wwi11sca.skdns.client.simulation.SimulationService;
import de.dhbw.wwi11sca.skdns.client.simulation.SimulationServiceAsync;
import de.dhbw.wwi11sca.skdns.shared.*;

import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;

public class UnternehmenSimulation implements EntryPoint {

	Button btnLogout = new Button("Logout");
	AbsolutePanel absolutePanelAnlegen = new AbsolutePanel();
	Label labelAusgeloggt = new Label("Sie wurden erfolgreich ausgeloggt.");
	Label labelHome = new Label("Home");
	Label lblAnlegen = new Label(">  Unternehmen anlegen");
	AbsolutePanel absolutePanelEigenesUnternehmen = new AbsolutePanel();
	AbsolutePanel absolutePanelUnternehmenEins = new AbsolutePanel();
	AbsolutePanel absolutePanelUnternehmenZwei = new AbsolutePanel();
	AbsolutePanel absolutePanelUnternehmenDrei = new AbsolutePanel();
	TabPanel tabPanelUnternehmenAnlegen = new TabPanel();
	CellTable<Maschinen> cellTableMaschinen = new CellTable<Maschinen>();
	Label labelFirma = new Label("Firma:");
	TextBox textBoxFirma = new TextBox();
	Label labelUmsatzEigenesUnternehmen = new Label("Umsatz:");
	Label labelMarktanteilEigenesUnternehmen = new Label("Marktanteil:");
	Label labelFixkosten = new Label("Fixkosten:");
	Label labelProduktPreisEigenesUnternehmen = new Label("Produktpreis:");
	Label labelAbsatzmengeEigenesUnternehmen = new Label("Absatzmenge:");
	IntegerBox integerBoxUmsatzEigenesUnternehmen = new IntegerBox();
	DoubleBox doubleBoxMarktanteilEigenesUnternehmen = new DoubleBox();
	IntegerBox integerBoxFixkosten = new IntegerBox();
	DoubleBox doubleBoxProduktpreisEigenesUnternehmen = new DoubleBox();
	IntegerBox integerBoxAbsatzEigenesUnternehmen = new IntegerBox();
	Label lblAnzahlMitarbeiter = new Label("Anzahl Mitarbeiter:");
	IntegerBox integerBoxAnzahlMitarbeiter = new IntegerBox();
	Label lblDurchMitarbeitergehalt = new Label("durch. Mitarbeitergehalt:");
	IntegerBox integerBoxGehaltMitarbeiter = new IntegerBox();
	Button buttonLoeschenEigenesUnternehmen = new Button("L\u00F6schen");
	Button buttonUebernehmenEigenesUnternehmen = new Button("\u00DCbernehmen");
	Button buttonLoeschenUNEins = new Button("L\u00F6schen");
	Button buttonUebernehmenUNEins = new Button("\u00DCbernehmen");
	Label labelUmsatzUNEins = new Label("Umsatz:");
	IntegerBox integerBoxUmsatzUNEins = new IntegerBox();
	Label labelMarkanteilUNEins = new Label("Marktanteil:");
	DoubleBox doubleBoxMarktanteilUNEins = new DoubleBox();
	Label labelProduktpreisUNEins = new Label("Produktpreis:");
	DoubleBox doubleBoxProduktpreisUNEins = new DoubleBox();
	Label labelAbsatzmengeUNEins = new Label("Absatzmenge:");
	IntegerBox integerBoxAbsatzUNEins = new IntegerBox();
	Button buttonLoeschenUNZwei = new Button("L\u00F6schen");
	Button buttonUebernehmenUNZwei = new Button("\u00DCbernehmen");
	Label labelUmsatzUNZwei = new Label("Umsatz:");
	IntegerBox integerBoxUmsatzUNZwei = new IntegerBox();
	Label labelMarkanteilUNZwei = new Label("Marktanteil:");
	DoubleBox doubleBoxMarktanteilUNZwei = new DoubleBox();
	Label labelProduktpreisUNZwei = new Label("Produktpreis:");
	DoubleBox doubleBoxProduktpreisUNZwei = new DoubleBox();
	Label labelAbsatzmengeUNZwei = new Label("Absatzmenge:");
	IntegerBox integerBoxAbsatzUNZwei = new IntegerBox();
	Button buttonLoeschenUNDrei = new Button("L\u00F6schen");
	Button buttonUebernehmenUNDrei = new Button("\u00DCbernehmen");
	Label labelUmsatzUNDrei = new Label("Umsatz:");
	IntegerBox integerBoxUmsatzUNDrei = new IntegerBox();
	Label labelMarkanteilUNDrei = new Label("Marktanteil:");
	DoubleBox doubleBoxMarktanteilUNDrei = new DoubleBox();
	Label labelProduktpreisUNDrei = new Label("Produktpreis:");
	DoubleBox doubleBoxProduktpreisUNDrei = new DoubleBox();
	Label labelAbsatzmengeUNDrei = new Label("Absatzmenge:");
	IntegerBox integerBoxAbsatzUNDrei = new IntegerBox();
	Unternehmen[] unternehmen = new Unternehmen[3];
	EigenesUnternehmen eigenesUnternehmen = new EigenesUnternehmen();

	private SimulationServiceAsync service = GWT
			.create(SimulationService.class);

	@Override
	public void onModuleLoad() {
		// allgemeine Panels
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");
		rootPanel.add(absolutePanelAnlegen, 0, 0);
		absolutePanelAnlegen.setSize("1024", "768");
		// Asynchroner Call: Falls Daten vorhanden sind, aus der Datenbank
		// auslesen, ansonsten Felder im TabPanelUnternehmenAnlegen frei lassen
		call();

		// Label, dass zeigt, in welcher Sicht der User sich befindet
		absolutePanelAnlegen.add(labelHome, 30, 30);
		labelHome.setStyleName("gwt-Home-Label");
		absolutePanelAnlegen.add(lblAnlegen, 70, 30);

		// TabPanel, auf dem die Unternehmen angezeigt werden
		absolutePanelAnlegen.add(tabPanelUnternehmenAnlegen, 90, 78);
		tabPanelUnternehmenAnlegen.setSize("844px", "605px");
		// Tab für das eigene Unternehmen
		tabPanelUnternehmenAnlegen.add(absolutePanelEigenesUnternehmen,
				"Eigenes Unternehmen", true);
		absolutePanelEigenesUnternehmen.setSize("100%", "596px");
		// Firma
		absolutePanelEigenesUnternehmen.add(labelFirma, 41, 48);
		absolutePanelEigenesUnternehmen.add(textBoxFirma, 136, 36);
		// Umsatz
		absolutePanelEigenesUnternehmen.add(labelUmsatzEigenesUnternehmen, 41,
				105);
		absolutePanelEigenesUnternehmen.add(integerBoxUmsatzEigenesUnternehmen,
				136, 93);
		integerBoxUmsatzEigenesUnternehmen.setSize("162px", "24px");
		// Marktanteil
		absolutePanelEigenesUnternehmen.add(labelMarktanteilEigenesUnternehmen,
				41, 159);
		absolutePanelEigenesUnternehmen.add(
				doubleBoxMarktanteilEigenesUnternehmen, 136, 147);
		doubleBoxMarktanteilEigenesUnternehmen.setSize("161px", "24px");
		// Fixkosten
		absolutePanelEigenesUnternehmen.add(labelFixkosten, 41, 211);
		absolutePanelEigenesUnternehmen.add(integerBoxFixkosten, 136, 199);
		integerBoxFixkosten.setSize("161px", "24px");
		// Produktpreis
		absolutePanelEigenesUnternehmen.add(
				labelProduktPreisEigenesUnternehmen, 432, 159);
		absolutePanelEigenesUnternehmen.add(
				doubleBoxProduktpreisEigenesUnternehmen, 577, 147);
		doubleBoxProduktpreisEigenesUnternehmen.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelEigenesUnternehmen.add(labelAbsatzmengeEigenesUnternehmen,
				432, 211);
		absolutePanelEigenesUnternehmen.add(integerBoxAbsatzEigenesUnternehmen,
				577, 199);
		integerBoxAbsatzEigenesUnternehmen.setSize("161px", "24px");
		// Anzahl Mitarbeiter
		absolutePanelEigenesUnternehmen.add(lblAnzahlMitarbeiter, 432, 48);
		absolutePanelEigenesUnternehmen.add(integerBoxAnzahlMitarbeiter, 577,
				36);
		integerBoxAnzahlMitarbeiter.setSize("161px", "24px");
		// durchschnittliches Mitarbeitergehalt
		absolutePanelEigenesUnternehmen
				.add(lblDurchMitarbeitergehalt, 432, 105);
		absolutePanelEigenesUnternehmen.add(integerBoxGehaltMitarbeiter, 577,
				93);
		integerBoxGehaltMitarbeiter.setSize("161px", "24px");

		// Maschinen
		absolutePanelEigenesUnternehmen.add(cellTableMaschinen, 88, 277);
		cellTableMaschinen.setSize("656px", "194px");
		// Attribut bisherige Dauer, in der die Maschine genutzt wurde
		TextColumn<Maschinen> nutzungsdauer = new TextColumn<Maschinen>() {
			@Override
			public String getValue(Maschinen object) {
				// TODO Tabellenfeld bisherige Nutzungsdauer füllen
				String nutzungsdauer = new String();
				return nutzungsdauer;
			}
		};
		cellTableMaschinen.addColumn(nutzungsdauer, "bisherige Nutzungsdauer");
		// Attribut Maschinenkapazität
		TextColumn<Maschinen> kapazitaet = new TextColumn<Maschinen>() {
			@Override
			public String getValue(Maschinen object) {
				// TODO Tabellenfeld Kapazität füllen
				String kapazitaet = new String();
				return kapazitaet;
			}
		};
		cellTableMaschinen.addColumn(kapazitaet, "Kapazität");
		// Attribut gezahlter Maschinenpreis
		TextColumn<Maschinen> preis = new TextColumn<Maschinen>() {
			@Override
			public String getValue(Maschinen object) {
				// TODO Tabellenfeld Preis füllen
				String preis = new String();
				return preis;
			}
		};
		cellTableMaschinen.addColumn(preis, "Preis");
		// Attribut Anzahl der bedienenden Mitarbeiter
		TextColumn<Maschinen> bediener = new TextColumn<Maschinen>() {
			@Override
			public String getValue(Maschinen object) {
				// TODO Tabellenfeld Notwendige Mitarbeiter füllen
				String bediener = new String();
				return bediener;
			}
		};
		cellTableMaschinen.addColumn(bediener, "Notwendige Mitarbeiter");

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
			}
		});

		// Unternehmensdaen des eigenen Unternehmen in der Datenbank speichern
		absolutePanelEigenesUnternehmen.add(
				buttonUebernehmenEigenesUnternehmen, 614, 551);
		buttonUebernehmenEigenesUnternehmen.setSize("100px", "35px");
		buttonUebernehmenEigenesUnternehmen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Daten Eigenes Unternehmen in DB speichern
			}
		});

		// Tab für das erste Unternehmen
		tabPanelUnternehmenAnlegen.add(absolutePanelUnternehmenEins,
				"Unternehmen 1", false);
		absolutePanelUnternehmenEins.setSize("100%", "596");
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
		tabPanelUnternehmenAnlegen.add(absolutePanelUnternehmenZwei,
				"Unternehmen 2", false);
		absolutePanelUnternehmenZwei.setSize("100%", "596");
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
		tabPanelUnternehmenAnlegen.add(absolutePanelUnternehmenDrei,
				"Unternehmen 3", false);
		absolutePanelUnternehmenDrei.setSize("100%", "596");
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

		// Button, um auf die Home-Seite zurückzukehren
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
	}

	private void call() {
		
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
				eigenesUnternehmen.setFixkosten(result.getFixkosten());
				eigenesUnternehmen.setGewinn(result.getGewinn());
				eigenesUnternehmen.setMarktAnteil(result.getMarktAnteil());
				eigenesUnternehmen.setMaschinen(result.getMaschinen());
				eigenesUnternehmen.setMitarbeiterGehalt(result
						.getMitarbeiterGehalt());
				eigenesUnternehmen.setNachfrageTendenz(result
						.getNachfrageTendenz());
				eigenesUnternehmen.setProdukt(result.getProdukt());
				eigenesUnternehmen.setUmsatz(result.getUmsatz());
				eigenesUnternehmen.setFirma(result.getFirma());

				if (eigenesUnternehmen != null) {
					textBoxFirma.setText(eigenesUnternehmen.getFirma());
					integerBoxUmsatzEigenesUnternehmen
							.setValue(eigenesUnternehmen.getUmsatz());
					doubleBoxMarktanteilEigenesUnternehmen
							.setValue(eigenesUnternehmen.getMarktAnteil());
					doubleBoxProduktpreisEigenesUnternehmen
							.setValue(eigenesUnternehmen.getProdukt()
									.getPreis());
					integerBoxAbsatzEigenesUnternehmen
							.setValue(eigenesUnternehmen.getProdukt()
									.getMenge());
					integerBoxAnzahlMitarbeiter.setValue(eigenesUnternehmen
							.getMitarbeiterAnzahl());
					integerBoxGehaltMitarbeiter.setValue(eigenesUnternehmen
							.getMitarbeiterGehalt());
					// TODO Maschinen in cellTableMaschinen übernehmen
				}

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
				// Unternehmen aus der DB übernehmen
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
				// Übernehmen der Daten in die Felder
				unternehmenBefuellen();

			}
		};
		service.getUnternehmen(callback);

	}

	private void unternehmenBefuellen() {

		// Falls Daten in der Datenbank vorhanden sind, werden sie in
		// den Boxen angezeigt
		if (unternehmen[0] != null) {
			integerBoxUmsatzUNEins.setValue(unternehmen[0].getUmsatz());
			doubleBoxMarktanteilUNEins
					.setValue(unternehmen[0].getMarktAnteil());
			doubleBoxProduktpreisUNEins.setValue(unternehmen[0].getProdukt()
					.getPreis());
			integerBoxAbsatzUNEins.setValue(unternehmen[0].getProdukt()
					.getMenge());
		}
		if (unternehmen[1] != null) {
			integerBoxUmsatzUNZwei.setValue(unternehmen[1].getUmsatz());
			doubleBoxMarktanteilUNZwei
					.setValue(unternehmen[1].getMarktAnteil());
			doubleBoxProduktpreisUNZwei.setValue(unternehmen[1].getProdukt()
					.getPreis());
			integerBoxAbsatzUNZwei.setValue(unternehmen[1].getProdukt()
					.getMenge());
		}
		if (unternehmen[2] != null) {
			integerBoxUmsatzUNDrei.setValue(unternehmen[2].getUmsatz());
			doubleBoxMarktanteilUNDrei
					.setValue(unternehmen[2].getMarktAnteil());
			doubleBoxProduktpreisUNDrei.setValue(unternehmen[2].getProdukt()
					.getPreis());
			integerBoxAbsatzUNDrei.setValue(unternehmen[2].getProdukt()
					.getMenge());
		}
	}
}
