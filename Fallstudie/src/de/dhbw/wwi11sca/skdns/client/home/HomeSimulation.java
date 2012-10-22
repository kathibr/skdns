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
import de.dhbw.wwi11sca.skdns.client.company.CompanySimulation;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

public class HomeSimulation implements EntryPoint {

	// Panels
	private AbsolutePanel panelHome = new AbsolutePanel();

	// Widgets
	private CellTable<Company> tableCompanies = new CellTable<Company>();
	private CellTable<OwnCompany> tableOwnCompany = new CellTable<OwnCompany>();
	private Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");

	private Button btCompaniesChange = new Button("Unternehmen bearbeiten");
	private Button btSimulation = new Button("Simulation starten");
	private Button btLogout = new Button("Logout");

	private Label lbOwnCompany = new Label();
	private Label lbCompanies = new Label("Konkurrenz:");
	private Label lbLogout = new Label("Sie wurden erfolgreich ausgeloggt.");

	private List<Company> dbCompany;
	private List<Company> companyList;
	private List<OwnCompany> ownCompanyList;

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
				CompanySimulation company = new CompanySimulation();
				company.onModuleLoad();
			}
		}); // Ende btCompaniesChange

		// Eventhandler Simualation starten
		btSimulation.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				RootPanel.get().clear();
				Simulation simulation = new Simulation();
				simulation.onModuleLoad();
			}
		}); // Ende btSimulation

		// Eventhandler ausloggen
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(lbLogout);
			}
		}); // Ende btLogout
		tableOwnCompany.setPageSize(1);

		// Darstellung der vorhandenen Unternehmensdaten

		// CellTable für eigenes Unternehmen
		panelHome.add(tableOwnCompany, 10, 266);
		tableOwnCompany.setSize("480px", "200px");

		// Unternehmensdaten des eigenen Unternehmens befüllen: Umsatz
		TextColumn<OwnCompany> topLineOwnColumn = new TextColumn<OwnCompany>() {
			@Override
			public String getValue(OwnCompany company) {
				return new Integer(company.getTopLine()).toString();
			}
		}; // Ende topLineOwnColumn
			// Unternehmensdaten des eigenen Unternehmens befüllen: Gewinn
		TextColumn<OwnCompany> amountOwnColumn = new TextColumn<OwnCompany>() {
			@Override
			public String getValue(final OwnCompany company) {
				return new Integer(company.getAmount()).toString();
			}
		}; // Ende amountOwnColumn
			// Unternehmensdaten des eigenen Unternehmens befüllen: Marktanteil
		TextColumn<OwnCompany> marketShareOwnColumn = new TextColumn<OwnCompany>() {
			@Override
			public String getValue(final OwnCompany company) {
				return new Double(company.getMarketShare()).toString();
			}
		}; // Ende marketShareOwnColumn
			// Unternehmensdaten des eigenen Unternehmens befüllen:
			// Produktabsatzmenge
		TextColumn<OwnCompany> salesVolumeOwnColumn = new TextColumn<OwnCompany>() {
			@Override
			public String getValue(final OwnCompany company) {
				return new Integer(company.getProduct().getSalesVolume())
						.toString();
			}
		}; // Ende salesVolumeOwnColumn
			// Unternehmensdaten des eigenen Unternehmens befüllen: Produktpreis
		TextColumn<OwnCompany> productPriceOwnColumn = new TextColumn<OwnCompany>() {
			@Override
			public String getValue(final OwnCompany company) {
				return new Double(company.getProduct().getPrice()).toString();
			}
		}; // Ende productPriceOwnColumn

		// Unternehmensdaten des eigenen Unternehmens anzeigen lassen
		tableOwnCompany.addColumn(topLineOwnColumn, "Umsatz");
		tableOwnCompany.addColumn(amountOwnColumn, "Gewinn");
		tableOwnCompany.addColumn(marketShareOwnColumn, "Marktanteil");
		tableOwnCompany.addColumn(productPriceOwnColumn, "Produktpreis");
		tableOwnCompany.addColumn(salesVolumeOwnColumn, "Absatzmenge");

		// CellTable für Konkurrenzunternehmen
		panelHome.add(tableCompanies, 518, 266);
		tableCompanies.setSize("480px", "200px");

		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Umsatz
		TextColumn<Company> topLineColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new Integer(company.getTopLine()).toString();
			}
		}; // Ende topLineColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen: Gewinn
		TextColumn<Company> amountColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new Integer(company.getAmount()).toString();
			}
		}; // Ende amountColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen: Marktanteil
		TextColumn<Company> marketShareColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				return new Double(company.getMarketShare()).toString();
			}
		}; // Ende marketShareColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen:
			// Produktmenge
		TextColumn<Company> salesVolumeColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				return new Integer(company.getProduct().getSalesVolume()).toString();
			}
		}; // Ende salesVolumeColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen:
			// Produktpreis
		TextColumn<Company> productPriceColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				return new Double(company.getProduct().getPrice()).toString();
			}
		}; // Ende productPriceColumn

		// Unternehmensdaten der Konkurrenzunternehmen anzeigen lassen
		tableCompanies.addColumn(topLineColumn, "Umsatz");
		tableCompanies.addColumn(amountColumn, "Gewinn");
		tableCompanies.addColumn(marketShareColumn, "Marktanteil");
		tableCompanies.addColumn(productPriceColumn, "Produktpreis");
		tableCompanies.addColumn(salesVolumeColumn, "Absatzmenge");

		// Calls

		// Eigenes Unternehmen
		service.getOwnCompany(new GetOwnCompanyCallback());
		// Konkurrenzunternehmen
		service.getCompany(new GetCompanyCallback());

	} // Ende method onModuleLoad

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die Daten
	 * der Konkurrenzunternehmen aus der Datenbank zurückgibt
	 * 
	 */
	public class GetCompanyCallback implements AsyncCallback<List<Company>> {
		@Override
		public void onFailure(final Throwable caught) {
		} // Ende method onFailure
		@Override
		public final void onSuccess(List<Company> result) {

			ListDataProvider<Company> dataProvider = new ListDataProvider<Company>();
			// Connect the table to the data provider.
			dataProvider.addDataDisplay(tableCompanies);

			// Add the data to the data provider, which automatically pushes it
			// to the widget.
			companyList = dataProvider.getList();

			for (Company company : result) {
				companyList.add(company);
			} // Ende for-Schleife

		} // Ende method onSuccess
	} // Ende class GetCompanyCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die Daten
	 * des eigenen Unternehmen aus der Datenbank zurückgibt
	 * 
	 */
	public class GetOwnCompanyCallback implements AsyncCallback<OwnCompany> {

		@Override
		public void onFailure(final Throwable caught) {
		} // Ende method onFailure

		@Override
		public final void onSuccess(OwnCompany result) {

			ListDataProvider<OwnCompany> dataOwnProvider = new ListDataProvider<OwnCompany>();
			// Connect the table to the data provider.
			dataOwnProvider.addDataDisplay(tableOwnCompany);

			// Add the data to the data provider, which automatically pushes it
			// to the widget.
			ownCompanyList = dataOwnProvider.getList();
			ownCompanyList.add(result);
			lbOwnCompany.setText(result.getTradeName());

		} // Ende method onSuccess
	} // Ende class GetOwnCompanyCallback

} // Ende class HomeSimulation
