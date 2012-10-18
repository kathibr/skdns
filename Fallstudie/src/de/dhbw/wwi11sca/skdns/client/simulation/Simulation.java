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
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation;
import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;

public class Simulation implements EntryPoint {

	// Panels
	AbsolutePanel absolutePanelSimulation = new AbsolutePanel();
	AbsolutePanel absolutePanelInvest = new AbsolutePanel();

	// Widgets
	Label lbHome = new Label("Home");
	Label lbSimulation = new Label(">  Simulation");
	Label lbLogout = new Label("Sie wurden erfolgreich ausgeloggt.");

	Button btSimulation = new Button("Simulation starten");
	Button btNextYear = new Button("Folgejahr");
	Button btLogout = new Button("Logout");

	Label lbInvest = new Label("Investitionen:");
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
	TabPanel tabPanelYears = new TabPanel();
	CellTable<EigenesUnternehmen> tableOwnCompany;
	List<EigenesUnternehmen> companyList;
	AbsolutePanel[] absolutePanelYear = new AbsolutePanel[1000];

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
		tabPanelYears.setSize("100%", "100%");

		// Darstellung der Unternehmen
		uebersicht();

		// Investitionen
		investitionLaden();

		// Buttons

		// Simulation starten : buttonSimulation
		absolutePanelSimulation.add(btSimulation, 795, 274);
		btSimulation.setSize("127px", "35px");

		// Simulation für das Folgejahr starten : buttonFolgejahr
		absolutePanelSimulation.add(btNextYear, 795, 334);
		btNextYear.setSize("127px", "35px");

		// Logout : btLogout
		absolutePanelSimulation.add(btLogout, 914, 10);
		btLogout.setSize("100px", "35px");

		// Eventhandler

		// Simulation starten
		btSimulation.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
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

				deleteValueInvest();

				tabPanelYears
						.add(absolutePanelYear[stackYear], "Jahr "
								+ simulationYear + " (" + simulationVersion
								+ ")", true);

				stackYear++;
				simulationVersion++;
				// service.createSimulationCallback(version,
				// new CreateSimulationCallback());
			}
		});

		// Simulation Folgejahr starten
		btNextYear.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Kommentieren
				for (int i = deleteCounter; i < stackYear - 1; i++) {
					tabPanelYears.remove(absolutePanelYear[i]);
				}

				deleteCounter = stackYear;
				// TODO Ende
				simulationYear++;
				simulationVersion = 1;

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

				deleteValueInvest();

				tabPanelYears
						.add(absolutePanelYear[stackYear], "Jahr "
								+ simulationYear + " (" + simulationVersion
								+ ")", true);

				stackYear++;
				simulationVersion++;
				// service.createSimulationCallback(version,
				// new CreateSimulationCallback());

			}
		});

		// zur Home zurückkehren
		lbHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				HomeSimulation home = new HomeSimulation();
				home.onModuleLoad();
			}
		});

		// Logout
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(lbLogout);
			}
		});

	}

	private void deleteValueInvest() {
		integerBoxMarketing.setValue(null);
		integerBoxMachineValue.setValue(null);
		integerBoxCapacity.setValue(null);
		integerBoxMachineStaff.setValue(null);
		integerBoxPersonal.setValue(null);
		integerBoxPrice.setValue(null);

	}

	public void uebersicht() {
		tableOwnCompany = new CellTable<EigenesUnternehmen>();
		absolutePanelSimulation.add(tableOwnCompany, 60, 79);
		tableOwnCompany.setSize("894px", "135px");

		TextColumn<EigenesUnternehmen> firmaEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return unternehmen.getFirma();
			}
		};
		TextColumn<EigenesUnternehmen> umsatzEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getUmsatz()).toString();
			}
		};
		TextColumn<EigenesUnternehmen> gewinnEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getGewinn()).toString();
			}
		};
		TextColumn<EigenesUnternehmen> marktAnteilEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Double(unternehmen.getMarktAnteil()).toString();
			}
		};
		TextColumn<EigenesUnternehmen> produktMengeEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Integer(unternehmen.getProdukt().getMenge())
						.toString();
			}
		};
		TextColumn<EigenesUnternehmen> produktPreisEColumn = new TextColumn<EigenesUnternehmen>() {
			@Override
			public String getValue(EigenesUnternehmen unternehmen) {
				return new Double(unternehmen.getProdukt().getPreis())
						.toString();
			}
		};

		tableOwnCompany.addColumn(firmaEColumn, "Firma");
		tableOwnCompany.addColumn(umsatzEColumn, "Umsatz");
		tableOwnCompany.addColumn(gewinnEColumn, "Gewinn");
		tableOwnCompany.addColumn(marktAnteilEColumn, "Marktanteil");
		tableOwnCompany.addColumn(produktPreisEColumn, "Produktpreis");
		tableOwnCompany.addColumn(produktMengeEColumn, "Absatzmenge");

		service.getCompany(new GetCompanyCallback());

	}

	private void investitionLaden() {
		// Panel, um die Investitionen zu tätigen : absolutePanelSimulation
		absolutePanelSimulation.add(absolutePanelInvest, 84, 248);
		absolutePanelInvest.setSize("650px", "133px");

		// Labels

		lbInvest.setStyleName("gwt-UnternehmenLabel");
		absolutePanelInvest.add(lbInvest, 10, 10);
		lbInvest.setSize("282px", "18px");
		// Marketing
		absolutePanelInvest.add(lbMarketing, 20, 45);
		// Maschinen
		absolutePanelInvest.add(lbMachine, 14, 73);
		// Maschinen Wert der Maschinen
		absolutePanelInvest.add(lbMachineValue, 21, 103);
		lbMachineValue.setSize("60px", "18px");
		// Maschinen nötiges Personal
		absolutePanelInvest.add(lbUsedPersonal, 410, 103);
		// Maschinen Kapazität
		absolutePanelInvest.add(lbMachineCapacity, 239, 103);
		// Personal
		absolutePanelInvest.add(lbPersonal, 239, 45);
		// Produktpreis
		absolutePanelInvest.add(lbPrice, 410, 47);
		lbPrice.setSize("101px", "18px");
		lbPrice.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		// IntegerBoxen
		// Marketing
		absolutePanelInvest.add(integerBoxMarketing, 86, 34);
		integerBoxMarketing.setSize("94px", "25px");
		// Maschinenwert
		absolutePanelInvest.add(integerBoxMachineValue, 86, 90);
		integerBoxMachineValue.setSize("94px", "25px");
		// Maschinenkapazität
		absolutePanelInvest.add(integerBoxCapacity, 300, 90);
		integerBoxCapacity.setSize("94px", "25px");
		// Maschinen Anzahl der notwendigen Mitarbeiter
		absolutePanelInvest.add(integerBoxMachineStaff, 520, 90);
		integerBoxMachineStaff.setSize("94px", "25px");
		// Personal
		absolutePanelInvest.add(integerBoxPersonal, 300, 34);
		integerBoxPersonal.setSize("94px", "25px");
		// Produktpreis
		absolutePanelInvest.add(integerBoxPrice, 520, 34);
		integerBoxPrice.setSize("94px", "25px");

	}

	public class GetCompanyCallback implements
			AsyncCallback<EigenesUnternehmen> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(EigenesUnternehmen result) {

			ListDataProvider<EigenesUnternehmen> dataEProvider = new ListDataProvider<EigenesUnternehmen>();
			// Connect the table to the data provider.
			dataEProvider.addDataDisplay(tableOwnCompany);

			// Add the data to the data provider, which automatically pushes it
			// to the widget.
			companyList = dataEProvider.getList();
			companyList.add(result);

		}
	}

	public class CreateSimulationCallback implements
			AsyncCallback<SimulationVersion> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(SimulationVersion result) {
			// TODO AbsolutePanel im TabPanel mit Marktanteilstorte etc.
			// befüllen

		}

	}
}
