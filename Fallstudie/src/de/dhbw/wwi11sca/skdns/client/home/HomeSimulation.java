package de.dhbw.wwi11sca.skdns.client.home;
/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 *         Die HomeSimulation enthält das Frontend des Homescreens des
 *         jeweiligen Users.
 * 
 */
import java.util.List;

import de.dhbw.wwi11sca.skdns.shared.Unternehmen;
import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import de.dhbw.wwi11sca.skdns.client.simulation.Simulation;
import de.dhbw.wwi11sca.skdns.client.unternehmen.UnternehmenSimulation;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;


public class HomeSimulation implements EntryPoint {

	// Panels
	AbsolutePanel panelHome = new AbsolutePanel();

	// Widgets
	CellTable<Unternehmen> tableCompanies = new CellTable<Unternehmen>();
	CellTable<EigenesUnternehmen> tableOwnCompany = new CellTable<EigenesUnternehmen>();
	Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");

	Button btCompaniesChange = new Button("Unternehmen bearbeiten");
	Button btSimulation = new Button("Simulation starten");
	Button btLogout = new Button("Logout");

	Label lbOwnCompany = new Label();
	Label lbCompanies = new Label("Konkurrenz:");
	Label lbLogout = new Label("Sie wurden erfolgreich ausgeloggt.");

	List<Unternehmen> dbCompany;
	List<Unternehmen> companyList;
	List<EigenesUnternehmen> ownCompanyList;

	private HomeServiceAsync service = GWT.create(HomeService.class);

	@Override
	public void onModuleLoad() {

		// RootPanel: root
		RootPanel root = RootPanel.get();
		root.setSize("1024px", "768px");

		// AbsolutePanel: absolutePanelHome
		root.add(panelHome, 0, 0);
		panelHome.setSize("1024px", "768px");

		// Firmenlogo: logo
		panelHome.add(logo, 333, 96);
		logo.setSize("359px", "93px");

		// Informationsfelder

		// Label eigenes Unternehmen: labelEigenesUN
		lbOwnCompany.setStyleName("gwt-Home-Label");
		panelHome.add(lbOwnCompany, 10, 236);
		lbOwnCompany.setSize("480px", "24px");

		// Label andere Unternehmen: labelAndereUN
		lbCompanies.setStyleName("gwt-Home-Label");
		panelHome.add(lbCompanies, 518, 236);

		// Buttons

		// Button Unternehmen bearbeiten: btUNBearbeiten
		panelHome.add(btCompaniesChange, 267, 533);
		btCompaniesChange.setSize("200px", "35px");

		// Button Simulation starten: btSimulation
		panelHome.add(btSimulation, 569, 533);
		btSimulation.setSize("200px", "35px");

		// Button Logout> byLogout
		panelHome.add(btLogout, 914, 10);
		btLogout.setSize("100px", "35px");

		// Eventhandler

		// Eventhandler Unternehmen bearbeiten
		btCompaniesChange.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				RootPanel.get().clear();
				UnternehmenSimulation unternehmen = new UnternehmenSimulation();
				unternehmen.onModuleLoad();
			}
		});

		// Eventhandler Simualation starten
		btSimulation.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				RootPanel.get().clear();
				Simulation simulation = new Simulation();
				simulation.onModuleLoad();
			}
		});

		// Eventhandler ausloggen
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(lbLogout);
			}
		});
		tableOwnCompany.setPageSize(1);

		// Darstellung der vorhandenen Unternehmensdaten

		// CellTable für eigenes Unternehmen
		panelHome.add(tableOwnCompany, 10, 266);
		tableOwnCompany.setSize("480px", "200px");

		// Unternehmensdaten des eigenen Unternehmens befüllen: Umsatz
		TextColumn<EigenesUnternehmen> umsatzEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(final EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getUmsatz()).toString();
			}
		};
		// Unternehmensdaten des eigenen Unternehmens befüllen: Gewinn
		TextColumn<EigenesUnternehmen> gewinnEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(final EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getGewinn()).toString();
			}
		};
		// Unternehmensdaten des eigenen Unternehmens befüllen: Marktanteil
		TextColumn<EigenesUnternehmen> marktAnteilEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(final EigenesUnternehmen unternehmen) {
				return new Double(unternehmen.getMarktAnteil()).toString();
			}
		};
		// Unternehmensdaten des eigenen Unternehmens befüllen:
		// Produktabsatzmenge
		TextColumn<EigenesUnternehmen> produktMengeEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(final EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getProdukt().getMenge())
						.toString();
			}
		};
		// Unternehmensdaten des eigenen Unternehmens befüllen: Produktpreis
		TextColumn<EigenesUnternehmen> produktPreisEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(final EigenesUnternehmen unternehmen) {
				return new Double(unternehmen.getProdukt().getPreis())
						.toString();
			}
		};

		// Unternehmensdaten des eigenen Unternehmens anzeigen lassen
		tableOwnCompany.addColumn(umsatzEColumn, "Umsatz");
		tableOwnCompany.addColumn(gewinnEColumn, "Gewinn");
		tableOwnCompany.addColumn(marktAnteilEColumn, "Marktanteil");
		tableOwnCompany.addColumn(produktPreisEColumn, "Produktpreis");
		tableOwnCompany.addColumn(produktMengeEColumn, "Absatzmenge");

		// CellTable für Konkurrenzunternehmen
		panelHome.add(tableCompanies, 518, 266);
		tableCompanies.setSize("480px", "200px");

		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Umsatz
		TextColumn<Unternehmen> umsatzColumn = new TextColumn<Unternehmen>() {
			@Override
			public String getValue(final Unternehmen unternehmen) {
				return new Integer(unternehmen.getUmsatz()).toString();
			}
		};
		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Gewinn
		TextColumn<Unternehmen> gewinnColumn = new TextColumn<Unternehmen>() {
			@Override
			public String getValue(final Unternehmen unternehmen) {
				return new Integer(unternehmen.getGewinn()).toString();
			}
		};
		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Marktanteil
		TextColumn<Unternehmen> marktAnteilColumn = new TextColumn<Unternehmen>() {
			@Override
			public String getValue(final Unternehmen unternehmen) {
				return new Double(unternehmen.getMarktAnteil()).toString();
			}
		};
		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Produktmenge
		TextColumn<Unternehmen> produktMengeColumn = new TextColumn<Unternehmen>() {
			@Override
			public String getValue(final Unternehmen unternehmen) {
				return new Integer(unternehmen.getProdukt().getMenge())
						.toString();
			}
		};
		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Produktpreis
		TextColumn<Unternehmen> produktPreisColumn = new TextColumn<Unternehmen>() {
			@Override
			public String getValue(final Unternehmen unternehmen) {
				return new Double(unternehmen.getProdukt().getPreis())
						.toString();
			}
		};

		// Unternehmensdaten der Konkurrenzunternehmen anzeigen lassen
		tableCompanies.addColumn(umsatzColumn, "Umsatz");
		tableCompanies.addColumn(gewinnColumn, "Gewinn");
		tableCompanies.addColumn(marktAnteilColumn, "Marktanteil");
		tableCompanies.addColumn(produktPreisColumn, "Produktpreis");
		tableCompanies.addColumn(produktMengeColumn, "Absatzmenge");

		// Calls

		// Eigenes Unternehmen
		service.getEigenesUnternehmen(new GetEigenesUnternehmenCallback());
		// Konkurrenzunternehmen
		service.getUnternehmen(new GetUnternehmenCallback());

	}

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die Daten
	 * der Konkurrenzunternehmen aus der Datenbank zurückgibt
	 * 
	 */
	public class GetUnternehmenCallback implements
			AsyncCallback<List<Unternehmen>> {

		@Override
		public void onFailure(final Throwable caught) {
		}

		@Override
		public final void onSuccess(final List<Unternehmen> result) {

			ListDataProvider<Unternehmen> dataProvider = new ListDataProvider<Unternehmen>();
			// Connect the table to the data provider.
			dataProvider.addDataDisplay(tableCompanies);

			// Add the data to the data provider, which automatically pushes it
			// to the widget.
			companyList = dataProvider.getList();

			for (Unternehmen unternehmen : result) {
				companyList.add(unternehmen);
			}

		}
	}

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die Daten
	 * des eigenen Unternehmen aus der Datenbank zurückgibt
	 * 
	 */
	public class GetEigenesUnternehmenCallback implements
			AsyncCallback<EigenesUnternehmen> {

		@Override
		public void onFailure(final Throwable caught) {
		}

		@Override
		public final void onSuccess(EigenesUnternehmen result) {

			ListDataProvider<EigenesUnternehmen> dataEProvider = new ListDataProvider<EigenesUnternehmen>();
			// Connect the table to the data provider.
			dataEProvider.addDataDisplay(tableOwnCompany);

			// Add the data to the data provider, which automatically pushes it
			// to the widget.
			ownCompanyList = dataEProvider.getList();
			ownCompanyList.add(result);
			lbOwnCompany.setText(result.getFirma());

		}
	}

}
