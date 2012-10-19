package de.dhbw.wwi11sca.skdns.client.company;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 *         Die CompanySimulation enthält das Frontend, auf welchem Unternehmen angelegt und verändert werden können.
 * 
 */
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
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.Machines;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;

import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

public class CompanySimulation implements EntryPoint {

	// Allgemeine Panel
	AbsolutePanel absolutePanelCreate = new AbsolutePanel();
	TabLayoutPanel tabPanelCreateCompanies = new TabLayoutPanel(1.5, Unit.EM);

	// Widgets
	Label lbHome = new Label("Home");
	Label lbCreate = new Label("> Unternehmen anlegen");
	Label lbLogout = new Label("Sie wurden erfolgreich ausgeloggt.");
	Button btLogout = new Button("Logout");

	AbsolutePanel absolutePanelOwnCompany = new AbsolutePanel();
	Label lbTradeName = new Label("Firma:");
	Label lbTopLineOwnCompany = new Label("Umsatz:");
	Label lbMarketShareOwnCompany = new Label("Marktanteil:");
	Label lbFixedCosts = new Label("Fixkosten:");
	Label lbProductPriceOwnCompany = new Label("Produktpreis:");
	Label lbSalesVolume = new Label("Absatzmenge:");
	Label lbNumberOfStaff = new Label("Anzahl Mitarbeiter:");
	Label lbSalaryOfStaff = new Label("durch. Mitarbeitergehalt:");
	TextBox textBoxTradeName = new TextBox();
	IntegerBox integerBoxTopLineOwnCompany = new IntegerBox();
	DoubleBox doubleBoxMarketShareOwnCompany = new DoubleBox();
	DoubleBox doubleBoxFixedCosts = new DoubleBox();
	DoubleBox doubleBoxProductPriceOwnCompany = new DoubleBox();
	IntegerBox integerBoxSalesVolume = new IntegerBox();
	IntegerBox integerBoxNumberOfStaff = new IntegerBox();
	IntegerBox integerBoxSalaryOfStaff = new IntegerBox();
	Button btDeleteOwnCompany = new Button("L\u00F6schen");
	Button btSaveOwnCompany = new Button("\u00DCbernehmen");
	CellTable<Machines> cellTableMachines;
	List<Machines> machinesOwnCompany;

	AbsolutePanel absolutePanelCompany1 = new AbsolutePanel();
	Label lbTopLineCompany1 = new Label("Umsatz:");
	Label lbMarketShareCompany1 = new Label("Marktanteil:");
	Label lbProductPriceCompany1 = new Label("Produktpreis:");
	Label lbSalesVolumeCompany1 = new Label("Absatzmenge:");
	IntegerBox integerBoxTopLineCompany1 = new IntegerBox();
	DoubleBox doubleBoxMarketShareCompany1 = new DoubleBox();
	DoubleBox doubleBoxProductPriceCompany1 = new DoubleBox();
	IntegerBox integerBoxSalesVolumeCompany1 = new IntegerBox();
	Button btDeleteCompany1 = new Button("L\u00F6schen");
	Button btSaveCompany1 = new Button("\u00DCbernehmen");

	AbsolutePanel absolutePanelCompany2 = new AbsolutePanel();
	Label lbTopLineCompany2 = new Label("Umsatz:");
	Label lbMarketShareCompany2 = new Label("Marktanteil:");
	Label lbProductPriceCompany2 = new Label("Produktpreis:");
	Label lbSalesVolumeCompany2 = new Label("Absatzmenge:");
	IntegerBox integerBoxTopLineCompany2 = new IntegerBox();
	DoubleBox doubleBoxMarketShareCompany2 = new DoubleBox();
	DoubleBox doubleBoxProductPriceCompany2 = new DoubleBox();
	IntegerBox integerBoxSalesVolumeCompany2 = new IntegerBox();
	Button btDeleteCompany2 = new Button("L\u00F6schen");
	Button btSaveCompany2 = new Button("\u00DCbernehmen");

	AbsolutePanel absolutePanelCompany3 = new AbsolutePanel();
	Label lbTopLineCompany3 = new Label("Umsatz:");
	Label lbMarketShareCompany3 = new Label("Marktanteil:");
	Label lbProductPriceCompany3 = new Label("Produktpreis:");
	Label lbSalesVolumeCompany3 = new Label("Absatzmenge:");
	IntegerBox integerBoxTopLineCompany3 = new IntegerBox();
	DoubleBox doubleBoxMarketShareCompany3 = new DoubleBox();
	DoubleBox doubleBoxProductPriceCompany3 = new DoubleBox();
	IntegerBox integerBoxSalesVolumeCompany3 = new IntegerBox();
	Button btDeleteCompany3 = new Button("L\u00F6schen");
	Button btSaveCompany3 = new Button("\u00DCbernehmen");

	OwnCompany ownCom;
	Company[] company = new Company[3];
	OwnCompany ownCompany = new OwnCompany();

	private CompanyServiceAsync service = GWT.create(CompanyService.class);

	@Override
	public void onModuleLoad() {

		// AbsolutePanel : absolutePanelCreate
		RootPanel.get().add(absolutePanelCreate, 0, 0);
		absolutePanelCreate.setSize("1024px", "768px");

		// Informationsfelder

		// Label zurück zur Home : lbHome
		absolutePanelCreate.add(lbHome, 30, 30);
		lbHome.setStyleName("gwt-Home-Label");
		// Label Unternehmen anlegen: lbCreate
		absolutePanelCreate.add(lbCreate, 80, 30);

		// Buttons
		absolutePanelCreate.add(btLogout, 914, 10);
		btLogout.setSize("100px", "35px");

		// TabPanel, auf dem die Unternehmen angezeigt werden :
		// tabPanelCreateCompanies
		absolutePanelCreate.add(tabPanelCreateCompanies, 90, 78);
		tabPanelCreateCompanies.setSize("844px", "605px");

		// AbsolutePanels für die Unternehmen anbringen
		// Tab für das eigene Unternehmen
		addTabPanelOwnCompany();
		// Tab für das erste Unternehmen
		addTabPanelCompanyOne();
		// Tab für das zweite Unternehmen
		addTabPanelCompanyTwo();
		// tab für das dritte Unternehmen
		addTabPanelCompanyThree();

		// Eventhandler
		lbHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				HomeSimulation home = new HomeSimulation();
				home.onModuleLoad();
			}
		}); // Ende lbHome

		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(lbLogout);
			}
		}); // Ende btLogout

		// Asynchroner Call: Falls Daten vorhanden sind, aus der Datenbank
		// auslesen, ansonsten Felder im TabPanelUnternehmenAnlegen frei lassen
		// service.getEigenesUnternehmen(new GetEigenesUnternehmenCallback());

		startCall();

	} // Ende onModuleLoad

	private void addTabPanelOwnCompany() {

		// Tab für das eigene Unternehmen anbringen
		absolutePanelOwnCompany.setSize("100%", "596px");
		tabPanelCreateCompanies.add(absolutePanelOwnCompany,
				"Eigenes Unternehmen", false);

		// Buttons
		// Unternehmensdaten löschen
		absolutePanelOwnCompany.add(btDeleteOwnCompany, 720, 551);
		btDeleteOwnCompany.setSize("100px", "35px");
		// Unternehmensdaten speichern
		absolutePanelOwnCompany.add(btSaveOwnCompany, 614, 551);
		btSaveOwnCompany.setSize("100px", "35px");

		// Eventhandler
		// Unternehmen löschen
		btDeleteOwnCompany.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBoxTradeName.setText(null);
				integerBoxTopLineOwnCompany.setValue(null);
				doubleBoxMarketShareOwnCompany.setValue(null);
				doubleBoxProductPriceOwnCompany.setValue(null);
				integerBoxSalesVolume.setValue(null);
				integerBoxNumberOfStaff.setValue(null);
				integerBoxSalaryOfStaff.setValue(null);
				// TODO Maschinen in Oberfläche löschen
				// TODO EigenesUnternehmen aus DB löschen
			}
		}); // Ende btDeleteOwnCompany
		// Unternehmen speichern
		btSaveOwnCompany.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				if (Pattern
						.matches(
								"[\u00C4\u00DC\u00D6A-Z][a-z\u00E4\u00FC\u00F6\u00C4\u00DC\u00DF\u00D6A-Z\\s]*",
								textBoxTradeName.getText())) {

					ownCom.setTradeName(textBoxTradeName.getText());

					service.addOwnCompany(ownCom, new AddOwnCompanyCallback());

				} else {
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");
				} // Ende if-else

			}
		}); // Ende btSaveOwnCompany

		// Elemente
		// Firma
		absolutePanelOwnCompany.add(lbTradeName, 41, 48);
		absolutePanelOwnCompany.add(textBoxTradeName, 136, 36);
		// Umsatz
		absolutePanelOwnCompany.add(lbTopLineOwnCompany, 41, 105);
		absolutePanelOwnCompany.add(integerBoxTopLineOwnCompany, 136, 93);
		integerBoxTopLineOwnCompany.setSize("162px", "24px");
		// Marktanteil
		absolutePanelOwnCompany.add(lbMarketShareOwnCompany, 41, 159);
		absolutePanelOwnCompany.add(doubleBoxMarketShareOwnCompany, 136, 147);
		doubleBoxMarketShareOwnCompany.setSize("161px", "24px");
		// Fixkosten
		absolutePanelOwnCompany.add(lbFixedCosts, 41, 211);
		absolutePanelOwnCompany.add(doubleBoxFixedCosts, 136, 199);
		doubleBoxFixedCosts.setSize("161px", "24px");
		// Produktpreis
		absolutePanelOwnCompany.add(lbProductPriceOwnCompany, 432, 159);
		absolutePanelOwnCompany.add(doubleBoxProductPriceOwnCompany, 577, 147);
		doubleBoxProductPriceOwnCompany.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelOwnCompany.add(lbSalesVolume, 432, 211);
		absolutePanelOwnCompany.add(integerBoxSalesVolume, 577, 199);
		integerBoxSalesVolume.setSize("161px", "24px");
		// Anzahl Mitarbeiter
		absolutePanelOwnCompany.add(lbNumberOfStaff, 432, 48);
		absolutePanelOwnCompany.add(integerBoxNumberOfStaff, 577, 36);
		integerBoxNumberOfStaff.setSize("161px", "24px");
		// durchschnittliches Mitarbeitergehalt
		absolutePanelOwnCompany.add(lbSalaryOfStaff, 432, 105);
		absolutePanelOwnCompany.add(integerBoxSalaryOfStaff, 577, 93);
		integerBoxSalaryOfStaff.setSize("161px", "24px");
		// Maschinen
		cellTableMachines = new CellTable<Machines>();
		absolutePanelOwnCompany.add(cellTableMachines, 88, 277);
		cellTableMachines.setSize("656px", "100px");

		TextColumn<Machines> serviceLifeColumn = new TextColumn<Machines>() {
			@Override
			public String getValue(Machines machine) {
				return new Integer(machine.getServiceLife()).toString();
			}
		}; // Ende serviceLifeColumn
		TextColumn<Machines> capacityColumn = new TextColumn<Machines>() {
			@Override
			public String getValue(Machines machine) {
				return new Integer(machine.getCapacity()).toString();
			}
		}; // Ende capacityColumn
		TextColumn<Machines> accountingValueColumn = new TextColumn<Machines>() {
			@Override
			public String getValue(Machines machine) {
				return new Double(machine.getAccountingValue()).toString();
			}
		}; // Ende accountingValueColumn
		TextColumn<Machines> StaffColumn = new TextColumn<Machines>() {
			@Override
			public String getValue(Machines machine) {
				// TODO Tabellenfeld Notwendige Mitarbeiter füllen
				return new Integer(machine.getStaff()).toString();
			}
		}; // Ende StaffColumn
		cellTableMachines.addColumn(serviceLifeColumn,
				"bisherige Nutzungsdauer");
		cellTableMachines.addColumn(capacityColumn, "Kapazit\u00e4t");
		cellTableMachines.addColumn(accountingValueColumn, "Preis");
		cellTableMachines.addColumn(StaffColumn, "Notwendige Mitarbeiter");
	} // Ende method addTabPanelOwnCompany

	private void addTabPanelCompanyOne() {
		// Tab für Unternehmen 1 anbringen
		absolutePanelCompany1.setSize("100%", "596");
		tabPanelCreateCompanies.add(absolutePanelCompany1, "Unternehmen 1",
				false);

		// Buttons
		absolutePanelCompany1.add(btDeleteCompany1, 416, 173);
		btDeleteCompany1.setSize("100px", "35px");

		absolutePanelCompany1.add(btSaveCompany1, 233, 173);
		btSaveCompany1.setSize("100px", "35px");

		// Eventhandler
		// Unternehmen löschen
		btDeleteCompany1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 1 aus DB löschen
				integerBoxTopLineCompany1.setValue(null);
				doubleBoxMarketShareCompany1.setValue(null);
				doubleBoxProductPriceCompany1.setValue(null);
				integerBoxSalesVolumeCompany1.setValue(null);
			}
		}); // Ende btDeleteCompany1
		// Unternehmen speichern
		btSaveCompany1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 1 in DB übernehmen
				company[0] = new Company();
				company[0].setTopLine(integerBoxTopLineCompany1.getValue());
				company[0].setMarketShare(doubleBoxMarketShareCompany1
						.getValue());
				company[0].getProduct().setPrice(
						doubleBoxProductPriceCompany1.getValue());
				company[0].getProduct().setSalesVolume(
						integerBoxSalesVolumeCompany1.getValue());
			}
		}); // Ende btSaveCompany1

		// Elemente
		// Umsatz
		absolutePanelCompany1.add(lbTopLineCompany1, 41, 48);
		absolutePanelCompany1.add(integerBoxTopLineCompany1, 136, 36);
		integerBoxTopLineCompany1.setSize("161", "24");
		// Marktanteil
		absolutePanelCompany1.add(lbMarketShareCompany1, 432, 48);
		absolutePanelCompany1.add(doubleBoxMarketShareCompany1, 577, 36);
		doubleBoxMarketShareCompany1.setSize("161", "24");
		// Produktpreis
		absolutePanelCompany1.add(lbProductPriceCompany1, 41, 104);
		absolutePanelCompany1.add(doubleBoxProductPriceCompany1, 136, 92);
		doubleBoxProductPriceCompany1.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelCompany1.add(lbSalesVolumeCompany1, 432, 104);
		absolutePanelCompany1.add(integerBoxSalesVolumeCompany1, 577, 92);
		integerBoxSalesVolumeCompany1.setSize("161px", "24px");

	} // Ende method addTabPanelCompanyOne

	private void addTabPanelCompanyTwo() {
		// Tab für Unternehmen 2 anbringen
		absolutePanelCompany2.setSize("100%", "596");
		tabPanelCreateCompanies.add(absolutePanelCompany2, "Unternehmen 2",
				false);
		// Buttons
		absolutePanelCompany2.add(btDeleteCompany2, 416, 173);
		btDeleteCompany2.setSize("100px", "35px");

		absolutePanelCompany2.add(btSaveCompany2, 233, 173);
		btSaveCompany2.setSize("100px", "35px");
		// Eventhandler
		// Unternehmen löschen
		btDeleteCompany2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 2 aus DB löschen
				integerBoxTopLineCompany2.setValue(null);
				doubleBoxMarketShareCompany2.setValue(null);
				doubleBoxProductPriceCompany2.setValue(null);
				integerBoxSalesVolumeCompany2.setValue(null);
			}
		}); // Ende btDeleteCompany2
		// Unternehmen speichern
		btSaveCompany2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 2 in DB übernehmen
				company[1] = new Company();
				company[1].setTopLine(integerBoxTopLineCompany2.getValue());
				company[1].setMarketShare(doubleBoxMarketShareCompany2
						.getValue());
				company[1].getProduct().setPrice(
						doubleBoxProductPriceCompany2.getValue());
				company[1].getProduct().setSalesVolume(
						integerBoxSalesVolumeCompany2.getValue());
			}
		}); // Ende btSaveCompany2

		// Elemente
		// Umsatz
		absolutePanelCompany2.add(lbTopLineCompany2, 41, 48);
		absolutePanelCompany2.add(integerBoxTopLineCompany2, 136, 36);
		integerBoxTopLineCompany2.setSize("161", "24");
		// Marktanteil
		absolutePanelCompany2.add(lbMarketShareCompany2, 432, 48);
		absolutePanelCompany2.add(doubleBoxMarketShareCompany2, 577, 36);
		doubleBoxMarketShareCompany2.setSize("161", "24");
		// Produktpreis
		absolutePanelCompany2.add(lbProductPriceCompany2, 41, 104);
		absolutePanelCompany2.add(doubleBoxProductPriceCompany2, 136, 92);
		doubleBoxProductPriceCompany2.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelCompany2.add(lbSalesVolumeCompany2, 432, 104);
		absolutePanelCompany2.add(integerBoxSalesVolumeCompany2, 577, 92);
		integerBoxSalesVolumeCompany2.setSize("161px", "24px");
	} // Ende method addTabPanelCompany2

	private void addTabPanelCompanyThree() {
		// Tab für Unternehmen 3 anbringen
		absolutePanelCompany3.setSize("100%", "596");
		tabPanelCreateCompanies.add(absolutePanelCompany3, "Unternehmen 3",
				false);

		// Buttons
		absolutePanelCompany3.add(btDeleteCompany3, 416, 173);
		btDeleteCompany3.setSize("100px", "35px");
		absolutePanelCompany3.add(btSaveCompany3, 233, 173);
		btSaveCompany3.setSize("100px", "35px");
		// Eventhandler
		// Unternehmen löschen
		btDeleteCompany3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 3 aus DB löschen
				integerBoxTopLineCompany3.setValue(null);
				doubleBoxMarketShareCompany3.setValue(null);
				doubleBoxProductPriceCompany3.setValue(null);
				integerBoxSalesVolumeCompany3.setValue(null);
			}
		}); // Ende btDeleteCompany3
		// Unternehmen speichern
		btSaveCompany3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 3 in DB übernehmen
				company[2] = new Company();
				company[2].setTopLine(integerBoxTopLineCompany3.getValue());
				company[2].setMarketShare(doubleBoxMarketShareCompany3
						.getValue());
				company[2].getProduct().setPrice(
						doubleBoxProductPriceCompany3.getValue());
				company[2].getProduct().setSalesVolume(
						integerBoxSalesVolumeCompany3.getValue());
			}
		}); // Ende btSaveCompany3

		// Elemente
		// Umsatz
		absolutePanelCompany3.add(lbTopLineCompany3, 41, 48);
		absolutePanelCompany3.add(integerBoxTopLineCompany3, 136, 36);
		integerBoxTopLineCompany3.setSize("161", "24");
		// Marktanteil
		absolutePanelCompany3.add(lbMarketShareCompany3, 432, 48);
		absolutePanelCompany3.add(doubleBoxMarketShareCompany3, 577, 36);
		doubleBoxMarketShareCompany3.setSize("161", "24");
		// Produktpreis
		absolutePanelCompany3.add(lbProductPriceCompany3, 41, 104);
		absolutePanelCompany3.add(doubleBoxProductPriceCompany3, 136, 92);
		doubleBoxProductPriceCompany3.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelCompany3.add(lbSalesVolumeCompany3, 432, 104);
		absolutePanelCompany3.add(integerBoxSalesVolumeCompany3, 577, 92);
		integerBoxSalesVolumeCompany3.setSize("161px", "24px");
	} // Ende method addTabPanelCompanyThree

	private void startCall() {
		service.getOwnCompany(new GetOwnCompanyCallback());

	} // Ende method startCall

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher ein
	 * angelegtes eigenes Unternehmen speichert
	 * 
	 */
	public class AddOwnCompanyCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende onFailure

		@Override
		public void onSuccess(Void result) {
			// jetzt wissen wir, das alles geklappt hat, auf dem Server.
			// Wir fÃ¼gen den neuen Wert der Liste hinzu
			// students.addItem(newName.getText());
			//
			// // und leeren das Eingabefeld.
			// newName.setText("");

			Window.alert("Das eigene Unternehmen wurde aktualisiert");
		} // Ende method onSuccess
	} // Ende class AddOwnCompanyCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher ein
	 * bereits angelegtes eigenes Unternehmen aus der Datenbank zurückgibt
	 * 
	 */
	public class GetOwnCompanyCallback implements AsyncCallback<OwnCompany> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende onFailure

		@Override
		public void onSuccess(OwnCompany result) {

			ownCom = new OwnCompany();
			ownCom = result;

			ListDataProvider<Machines> dataOwnProvider = new ListDataProvider<Machines>();
			// Connect the table to the data provider.
			dataOwnProvider.addDataDisplay(cellTableMachines);

			// Add the data to the data provider, which automatically pushes it
			// to the widget.
			machinesOwnCompany = dataOwnProvider.getList();
			machinesOwnCompany.add(result.getMachines());

			textBoxTradeName.setText(result.getTradeName());
			integerBoxTopLineOwnCompany.setValue(result.getTopLine());
			doubleBoxMarketShareOwnCompany.setValue(result.getMarketShare());
			doubleBoxFixedCosts.setValue(result.getFixedCosts());
			doubleBoxProductPriceOwnCompany.setValue(result.getProduct()
					.getPrice());
			integerBoxSalesVolume
					.setValue(result.getProduct().getSalesVolume());
			integerBoxNumberOfStaff.setValue(result.getNumberOfStaff());
			integerBoxSalaryOfStaff.setValue(result.getSalaryStaff());

		} // Ende method onSuccess
	} // Ende class GetOwnCompanyCallback

} // Ende class CompanySimulation