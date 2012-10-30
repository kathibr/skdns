package de.dhbw.wwi11sca.skdns.client.simulation;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation;
import de.dhbw.wwi11sca.skdns.client.login.LoginSimulation;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Simulation implements EntryPoint {

	// Panels
	AbsolutePanel absolutePanelSimulation = new AbsolutePanel();
	AbsolutePanel absolutePanelInvestments = new AbsolutePanel();

	// Widgets
	Label lbHome = new Label("Home");
	Label lbSimulation = new Label(">  Simulation");
	Label lbLogout = new Label("Sie wurden erfolgreich ausgeloggt.");
	Button btRelogin = new Button("erneuter Login?");

	Button btSimulation = new Button("Simulation starten");
	Button btNextYear = new Button("Folgejahr");
	Button btLogout = new Button("Logout");

	Label lbInvestments = new Label("Investitionen:");
	Label lbMarketing = new Label("Marketing:");
	Label lbMachine = new Label("Maschinen:");
	Label lbMachineValue = new Label("Wert:");
	Label lbUsedPersonal = new Label("nötiges Personal:");
	Label lbMachineCapacity = new Label("Kapazität:");
	Label lbPersonal = new Label("Personal:");
	Label lbPrice = new Label("Produktpreis:");

	IntegerBox integerBoxMarketing = new IntegerBox();
	IntegerBox integerBoxMachineValue = new IntegerBox();
	IntegerBox integerBoxCapacity = new IntegerBox();
	IntegerBox integerBoxMachineStaff = new IntegerBox();
	IntegerBox integerBoxPersonal = new IntegerBox();
	IntegerBox integerBoxPrice = new IntegerBox();

	ScrollPanel scrollPanelYears = new ScrollPanel();
	TabLayoutPanel tabPanelYears = new TabLayoutPanel(1.5, Unit.EM);
	CellTable<Company> tableCompanies = new CellTable<Company>();
	List<Company> companyList;
	AbsolutePanel[] absolutePanelYear = new AbsolutePanel[1000];

	AbsolutePanel absolutePanel = new AbsolutePanel();
	VerticalPanel verticalPanelInput = new VerticalPanel();
	Label lbResults = new Label("Ihre Eingaben:");
	Label lbInvestMarketing = new Label();
	Label lbInvestPersonal = new Label();
	Label lbInvestPrice = new Label();
	Label lbInvestMachineValue = new Label();
	Label lbInvestMachinesCapacity = new Label();
	Label lbInvestMachinePersonal = new Label();

	int stackYear = 0;
	int simulationYear = 1;
	int simulationVersion = 1;
	int deleteCounter = 0;

	private SimulationServiceAsync service = GWT
			.create(SimulationService.class);

	public void onModuleLoad() {
		// RootPanel : root
		RootPanel root = RootPanel.get();
		root.setSize("1024", "768");

		// Absolute Panel: absolutePanelSimulation
		root.add(absolutePanelSimulation, 0, 0);
		absolutePanelSimulation.setSize("1024px", "768px");

		// Informationsfelder

		// Label zurück zur Home: lbHome
		absolutePanelSimulation.add(lbHome, 30, 30);
		lbHome.setStyleName("gwt-Home-Label");
		// Label Simulation : lbSimulation
		absolutePanelSimulation.add(lbSimulation, 84, 34);

		// ScrollPanel, auf dem der TabPanel angebracht wird
		absolutePanelSimulation.add(scrollPanelYears, 60, 401);
		scrollPanelYears.setSize("894px", "300px");

		// TabPanel, auf dem die Ergebnisse der Simulation angezeigt werden
		scrollPanelYears.add(tabPanelYears);
		tabPanelYears.setSize("100%", "300px");

		// Darstellung der Unternehmen
		summaryCompanies();

		// Investitionen
		loadInvestment();

		// Buttons

		// Simulation starten : buttonSimulation
		absolutePanelSimulation.add(btSimulation, 795, 274);
		btSimulation.setSize("127px", "35px");

		// Simulation für das Folgejahr starten : buttonFolgejahr
		absolutePanelSimulation.add(btNextYear, 795, 334);
		btNextYear.setSize("127px", "35px");
		btNextYear.setEnabled(false);

		// Logout : btLogout
		absolutePanelSimulation.add(btLogout, 914, 10);
		btLogout.setSize("100px", "35px");

		// Eventhandler

		// Simulation starten
		btSimulation.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// eine neue Simulationsversion des laufenden Jahres wird
				// angebracht
				absolutePanelYear[stackYear] = new AbsolutePanel();
				absolutePanelYear[stackYear].setSize("100%", "250px");

				SimulationVersion version = new SimulationVersion(
						simulationYear, simulationVersion);

				if (integerBoxMarketing.getValue() != null)
					version.setMarketing(integerBoxMarketing.getValue());
				if (integerBoxMachineValue.getValue() != null)
					version.setMachineValue(integerBoxMachineValue.getValue());
				if (integerBoxCapacity.getValue() != null)
					version.setMachineCapacity(integerBoxCapacity.getValue());
				if (integerBoxMachineStaff.getValue() != null)
					version.setMachineStaff(integerBoxMachineStaff.getValue());
				if (integerBoxPersonal.getValue() != null)
					version.setPersonal(integerBoxPersonal.getValue());
				if (integerBoxPrice.getValue() != null)
					version.setPrice(integerBoxPrice.getValue());

				deleteValueInvestments();

				service.createSimulationCallback(version,
						new CreateSimulationCallback());

				stackYear++;
				simulationVersion++;
				btNextYear.setEnabled(true);
			}
		}); // Ende btSimulation

		// Simulation Folgejahr starten
		btNextYear.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Alle Versionen (außer der letzten) des letzten Jahres werden
				// aus der Ansicht gelöscht:
				for (int i = deleteCounter; i < stackYear - 1; i++) {
					tabPanelYears.remove(absolutePanelYear[i]);
				}

				deleteCounter = stackYear;

				simulationYear++;
				simulationVersion = 1;

				// eine Simulationsversion des nächsten Jahres wird angebracht
				absolutePanelYear[stackYear] = new AbsolutePanel();
				absolutePanelYear[stackYear].setSize("100%", "250px");

				SimulationVersion version = new SimulationVersion(
						simulationYear, simulationVersion);

				if (integerBoxMarketing.getValue() != null)
					version.setMarketing(integerBoxMarketing.getValue());
				if (integerBoxMachineValue.getValue() != null)
					version.setMachineValue(integerBoxMachineValue.getValue());
				if (integerBoxCapacity.getValue() != null)
					version.setMachineCapacity(integerBoxCapacity.getValue());
				if (integerBoxMachineStaff.getValue() != null)
					version.setMachineStaff(integerBoxMachineStaff.getValue());
				if (integerBoxPersonal.getValue() != null)
					version.setPersonal(integerBoxPersonal.getValue());
				if (integerBoxPrice.getValue() != null)
					version.setPrice(integerBoxPrice.getValue());

				// gefüllte Investitionsfelder werden geleert:
				deleteValueInvestments();

				tabPanelYears
						.add(absolutePanelYear[stackYear], "Jahr "
								+ simulationYear + " (" + simulationVersion
								+ ")", true);

				stackYear++;
				simulationVersion++;
				service.createSimulationCallback(version,
						new CreateSimulationCallback());

			}
		}); // Ende btNextYear

		// zur Home zurückkehren
		lbHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				HomeSimulation home = new HomeSimulation();
				home.onModuleLoad();
			}
		}); // Ende lbHome

		// Logout
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(btRelogin);
				RootPanel.get().add(lbLogout);
				btRelogin.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						RootPanel.get().clear();
						LoginSimulation login = new LoginSimulation();
						login.onModuleLoad();
					}
				}); // btLogout
			}
		}); // btLogout

	} // Ende onModuleLoad()

	private void createResults(int stackYear) {
		tabPanelYears.add(absolutePanelYear[stackYear - 1], "Jahr "
				+ simulationYear + " (" + simulationVersion + ")", true);

		absolutePanelYear[stackYear - 1].add(lbResults, 10, 10);
		absolutePanelYear[stackYear - 1].add(verticalPanelInput, 10, 34);
		verticalPanelInput.setSize("210px", "236px");
		verticalPanelInput.add(lbInvestMarketing);
		verticalPanelInput.add(lbInvestPersonal);
		verticalPanelInput.add(lbInvestPrice);
		verticalPanelInput.add(lbInvestMachineValue);
		verticalPanelInput.add(lbInvestMachinesCapacity);
		verticalPanelInput.add(lbInvestMachinePersonal);

	}

	private void deleteValueInvestments() {
		integerBoxMarketing.setValue(null);
		integerBoxMachineValue.setValue(null);
		integerBoxCapacity.setValue(null);
		integerBoxMachineStaff.setValue(null);
		integerBoxPersonal.setValue(null);
		integerBoxPrice.setValue(null);

	} // Ende method deleteValueInvestments

	public void summaryCompanies() {

		tableCompanies = new CellTable<Company>();
		absolutePanelSimulation.add(tableCompanies, 60, 79);
		tableCompanies.setSize("894px", "156px");

		TextColumn<Company> tradeNameColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new String(company.getTradeName()).toString();
			}
		};
		TextColumn<Company> umsatzColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new Integer(company.getTopLine()).toString();
			}
		};
		TextColumn<Company> gewinnColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new Integer(company.getAmount()).toString();
			}
		};
		TextColumn<Company> marktAnteilColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new Double(company.getMarketShare()).toString();
			}
		};
		TextColumn<Company> produktMengeColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new Integer(company.getProduct().getSalesVolume())
						.toString();
			}
		};
		TextColumn<Company> produktPreisColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new Double(company.getProduct().getPrice()).toString();
			}
		};

		tableCompanies.addColumn(tradeNameColumn, "Firma");
		tableCompanies.addColumn(umsatzColumn, "Umsatz");
		tableCompanies.addColumn(gewinnColumn, "Gewinn");
		tableCompanies.addColumn(marktAnteilColumn, "Marktanteil");
		tableCompanies.addColumn(produktPreisColumn, "Produktpreis");
		tableCompanies.addColumn(produktMengeColumn, "Absatzmenge");

		service.getCompany(new GetCompanyCallback());

	} // Ende method summaryCompanies

	private void loadInvestment() {
		// Panel, um die Investitionen zu tätigen : absolutePanelSimulation
		absolutePanelSimulation.add(absolutePanelInvestments, 84, 248);
		absolutePanelInvestments.setSize("650px", "133px");

		// Labels

		lbInvestments.setStyleName("gwt-UnternehmenLabel");
		absolutePanelInvestments.add(lbInvestments, 10, 10);
		lbInvestments.setSize("282px", "18px");
		// Marketing
		absolutePanelInvestments.add(lbMarketing, 20, 45);
		// Maschinen
		absolutePanelInvestments.add(lbMachine, 14, 73);
		// Maschinen Wert der Maschinen
		absolutePanelInvestments.add(lbMachineValue, 21, 103);
		lbMachineValue.setSize("60px", "18px");
		// Maschinen nötiges Personal
		absolutePanelInvestments.add(lbUsedPersonal, 410, 103);
		// Maschinen Kapazität
		absolutePanelInvestments.add(lbMachineCapacity, 239, 103);
		// Personal
		absolutePanelInvestments.add(lbPersonal, 239, 45);
		// Produktpreis
		absolutePanelInvestments.add(lbPrice, 410, 47);
		lbPrice.setSize("101px", "18px");
		lbPrice.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		// IntegerBoxen
		// Marketing
		absolutePanelInvestments.add(integerBoxMarketing, 86, 34);
		integerBoxMarketing.setSize("94px", "25px");
		// Personal
		absolutePanelInvestments.add(integerBoxPersonal, 300, 34);
		integerBoxPersonal.setSize("94px", "25px");
		// Produktpreis
		absolutePanelInvestments.add(integerBoxPrice, 520, 34);
		integerBoxPrice.setSize("94px", "25px");
		// Maschinenwert
		absolutePanelInvestments.add(integerBoxMachineValue, 86, 90);
		integerBoxMachineValue.setSize("94px", "25px");
		// Maschinenkapazität
		absolutePanelInvestments.add(integerBoxCapacity, 300, 90);
		integerBoxCapacity.setSize("94px", "25px");
		// Maschinen Anzahl der notwendigen Mitarbeiter
		absolutePanelInvestments.add(integerBoxMachineStaff, 520, 90);
		integerBoxMachineStaff.setSize("94px", "25px");

	} // Ende method loadInvestment

	/**
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher angelegte
	 * Unternehmen zurückgibt
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
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die
	 * Berechnungen der Marktsimulation durchführt und eine SimulationVersion
	 * zurückgibt
	 * 
	 */
	public class CreateSimulationCallback implements
			AsyncCallback<SimulationVersion> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende method onFailure

		@Override
		public void onSuccess(SimulationVersion result) {
			// TODO AbsolutePanel im TabPanel mit Marktanteilstorte etc.
			// befüllen

			// Eingegebene Investitionen werden angezeigt
			lbInvestMarketing.setText("Marketing: " + result.getMarketing());
			lbInvestPersonal.setText("Personal: " + result.getPersonal());
			lbInvestPrice.setText("Produktpreis: " + result.getPrice());
			lbInvestMachineValue.setText("Maschinenwert: "
					+ result.getMachineValue());
			lbInvestMachinesCapacity.setText("Maschinenkapazität: "
					+ result.getMachineCapacity());
			lbInvestMachinePersonal.setText("Maschinenpersonal: "
					+ result.getMachineStaff());

			// Alle Elemente auf der Oberfläche anbringen
			createResults(stackYear);
		} // Ende method onSuccess

	} // Ende class CreateSimulationCallback
} // Ende class Simulation