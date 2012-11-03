package de.dhbw.wwi11sca.skdns.client.company;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 *         Die CompanySimulation enthält das Frontend, auf welchem Unternehmen angelegt und verändert werden können.
 * 
 */
import java.util.List;
import com.google.gwt.regexp.shared.RegExp;
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
import de.dhbw.wwi11sca.skdns.client.login.LoginSimulation;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.Machines;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
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
	Button btRelogin = new Button("erneuter Login?");

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
	TextBox textBoxTopLineOwnCompany = new TextBox();
	TextBox textBoxMarketShareOwnCompany = new TextBox();
	TextBox textBoxFixedCosts = new TextBox();
	TextBox textBoxProductPriceOwnCompany = new TextBox();
	TextBox textBoxSalesVolume = new TextBox();
	TextBox textBoxNumberOfStaff = new TextBox();
	TextBox textBoxSalaryOfStaff = new TextBox();
	Button btDeleteOwnCompany = new Button("L\u00F6schen");
	Button btSaveOwnCompany = new Button("\u00DCbernehmen");
	CellTable<Machines> cellTableMachines;
	List<Machines> machinesOwnCompany;

	AbsolutePanel absolutePanelCompany1 = new AbsolutePanel();
	Label lbTopLineCompany1 = new Label("Umsatz:");
	Label lbMarketShareCompany1 = new Label("Marktanteil:");
	Label lbProductPriceCompany1 = new Label("Produktpreis:");
	Label lbSalesVolumeCompany1 = new Label("Absatzmenge:");
	TextBox textBoxTopLineCompany1 = new TextBox();
	TextBox textBoxMarketShareCompany1 = new TextBox();
	TextBox textBoxProductPriceCompany1 = new TextBox();
	TextBox textBoxSalesVolumeCompany1 = new TextBox();
	Button btDeleteCompany1 = new Button("L\u00F6schen");
	Button btSaveCompany1 = new Button("\u00DCbernehmen");

	AbsolutePanel absolutePanelCompany2 = new AbsolutePanel();
	Label lbTopLineCompany2 = new Label("Umsatz:");
	Label lbMarketShareCompany2 = new Label("Marktanteil:");
	Label lbProductPriceCompany2 = new Label("Produktpreis:");
	Label lbSalesVolumeCompany2 = new Label("Absatzmenge:");
	TextBox textBoxTopLineCompany2 = new TextBox();
	TextBox textBoxMarketShareCompany2 = new TextBox();
	TextBox textBoxProductPriceCompany2 = new TextBox();
	TextBox textBoxSalesVolumeCompany2 = new TextBox();
	Button btDeleteCompany2 = new Button("L\u00F6schen");
	Button btSaveCompany2 = new Button("\u00DCbernehmen");

	AbsolutePanel absolutePanelCompany3 = new AbsolutePanel();
	Label lbTopLineCompany3 = new Label("Umsatz:");
	Label lbMarketShareCompany3 = new Label("Marktanteil:");
	Label lbProductPriceCompany3 = new Label("Produktpreis:");
	Label lbSalesVolumeCompany3 = new Label("Absatzmenge:");
	TextBox textBoxTopLineCompany3 = new TextBox();
	TextBox textBoxMarketShareCompany3 = new TextBox();
	TextBox textBoxProductPriceCompany3 = new TextBox();
	TextBox textBoxSalesVolumeCompany3 = new TextBox();
	Button btDeleteCompany3 = new Button("L\u00F6schen");
	Button btSaveCompany3 = new Button("\u00DCbernehmen");
	
		OwnCompany ownCom;
	Company[] company = new Company[3];
	List<Company> companies;
	
	OwnCompany ownCompany = new OwnCompany();
	RegExp expTradeName = RegExp.compile("^([\u00C4\u00DC\u00D6A-Z])[0-9a-z\u00E4\u00FC\u00F6\u00C4\u00DC\u00DF\u00D6A-Z\\s]*");
	RegExp expInteger = RegExp.compile("^([0-9])[0-9]*");
	RegExp expDouble = RegExp.compile("^([1-9]\\d*|0)(\\.\\d)?$");
	
	
	private CompanyServiceAsync service = GWT.create(CompanyService.class);

	@Override
	public void onModuleLoad() {

		// AbsolutePanel : absolutePanelCreate
		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(absolutePanelCreate, 0, 0);
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

		// Eventhandler
		lbHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
		
				if( ( ownCom.getMarketShare() + companies.get(0).getMarketShare() + companies.get(1).getMarketShare() + companies.get(2).getMarketShare()) == 100
						){
					
					RootPanel.get().clear();
					HomeSimulation home = new HomeSimulation();
					home.onModuleLoad();
						
						
					
				}else{
					Window.alert("Der Marktanteil aller Unternehmen muss zusammen 100 betragen!");
				}
				
				
			}
		}); // Ende lbHome

		// Eventhandler ausloggen
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(btRelogin);
				RootPanel.get().add(lbLogout);
				btRelogin.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						
						if( ( ownCom.getMarketShare() + companies.get(0).getMarketShare() + companies.get(1).getMarketShare() + companies.get(2).getMarketShare()) == 100
								){
							

							RootPanel.get().clear();
							LoginSimulation login = new LoginSimulation();
							login.onModuleLoad();
								
								
							
						}else{
							Window.alert("Der Marktanteil aller Unternehmen muss zusammen 100 betragen!");
						}
						
						
					}
				}); // btLogout
			}
		});
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
				return new Integer(machine.getStaff()).toString();
			}
		}; // Ende StaffColumn
		cellTableMachines.addColumn(serviceLifeColumn,
				"bisherige Nutzungsdauer");
		cellTableMachines.addColumn(capacityColumn, "Kapazit\u00e4t");
		cellTableMachines.addColumn(accountingValueColumn, "Preis");
		cellTableMachines.addColumn(StaffColumn, "Notwendige Mitarbeiter");

		// AbsolutePanels für die Unternehmen anbringen
		// Tab für das eigene Unternehmen
		addTabPanelOwnCompany();
		// Tab für das erste Unternehmen
		addTabPanelCompanyOne();
		// Tab für das zweite Unternehmen
		addTabPanelCompanyTwo();
		// tab für das dritte Unternehmen
		addTabPanelCompanyThree();

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
				// TODO: Meldung: Sind sie sich sicher
				textBoxTradeName.setText(null);
				textBoxTopLineOwnCompany.setValue(null);
				textBoxMarketShareOwnCompany.setValue(null);
				textBoxProductPriceOwnCompany.setValue(null);
				textBoxSalesVolume.setValue(null);
				textBoxNumberOfStaff.setValue(null);
				textBoxSalaryOfStaff.setValue(null);
				// TODO Maschinen in Oberfläche löschen
				// TODO EigenesUnternehmen aus DB löschen
			}
		}); // Ende btDeleteOwnCompany
		// Unternehmen speichern
		btSaveOwnCompany.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
						
				if(	
						expTradeName.test(textBoxTradeName.getText())
						&& expInteger.test(textBoxTopLineOwnCompany.getText())
						&& expDouble.test(textBoxMarketShareOwnCompany.getText())
						&& expDouble.test(textBoxFixedCosts.getText())
						&& expInteger.test(textBoxNumberOfStaff.getText())
						&& expInteger.test(textBoxSalaryOfStaff.getText())
						&& expDouble.test(textBoxProductPriceOwnCompany.getText())
						&& expInteger.test(textBoxSalesVolume.getText())
			
						){
					ownCom.setTradeName(textBoxTradeName.getText());
					ownCom.setTopLine(new Integer(textBoxTopLineOwnCompany
							.getText()));
					ownCom.setMarketShare(new Double(
							textBoxMarketShareOwnCompany.getText()));
					ownCom.setFixedCosts(new Double(textBoxFixedCosts
							.getText()));
					ownCom.setNumberOfStaff(new Integer(textBoxNumberOfStaff
							.getText()));
					ownCom.setSalaryStaff(new Integer(textBoxSalaryOfStaff
							.getText()));
					ownCom.getProduct().setPrice(
							new Double(textBoxProductPriceOwnCompany
									.getText()));
					ownCom.getProduct().setSalesVolume(
							new Integer(textBoxSalesVolume.getText()));

					service.addOwnCompany(ownCom, new AddOwnCompanyCallback());
					
				}else{
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");
					
				}
				/*if ((Pattern
						.matches(
								"[\u00C4\u00DC\u00D6A-Z][0-9a-z\u00E4\u00FC\u00F6\u00C4\u00DC\u00DF\u00D6A-Z\\s]*",
								textBoxTradeName.getText()))
						&& (Pattern.matches("[0-9][0-9]*",
								textBoxTopLineOwnCompany.getText()))
						&& (Pattern.matches("^([1-9]\\d*|0)(\\.\\d)?$",textBoxMarketShareOwnCompany.getText())) //double
						&& (Pattern.matches("^([1-9]\\d*|0)(\\.\\d)?$",textBoxFixedCosts.getText())) //double
						&& (Pattern.matches("[0-9][0-9]*",textBoxNumberOfStaff.getText()))
						&& (Pattern.matches("[0-9][0-9]*",textBoxSalaryOfStaff.getText()))
						&& (Pattern.matches("^([1-9]\\d*|0)(\\.\\d)?$",textBoxProductPriceOwnCompany.getText()))//double
						&& (Pattern.matches("[0-9][0-9]*",textBoxSalesVolume.getText()))	
					)
				{
					ownCom.setTradeName(textBoxTradeName.getText());
					ownCom.setTopLine(new Integer(textBoxTopLineOwnCompany
							.getText()));
					ownCom.setMarketShare(new Double(
							textBoxMarketShareOwnCompany.getText()));
					ownCom.setFixedCosts(new Double(textBoxFixedCosts
							.getText()));
					ownCom.setNumberOfStaff(new Integer(textBoxNumberOfStaff
							.getText()));
					ownCom.setSalaryStaff(new Integer(textBoxSalaryOfStaff
							.getText()));
					ownCom.getProduct().setPrice(
							new Double(textBoxProductPriceOwnCompany
									.getText()));
					ownCom.getProduct().setSalesVolume(
							new Integer(textBoxSalesVolume.getText()));

					service.addOwnCompany(ownCom, new AddOwnCompanyCallback());
				} else {
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");
				} // Ende if-else */

			}
		}); // Ende btSaveOwnCompany

		// Elemente
		// Firma
		absolutePanelOwnCompany.add(lbTradeName, 41, 48);
		absolutePanelOwnCompany.add(textBoxTradeName, 136, 36);
		// Umsatz
		absolutePanelOwnCompany.add(lbTopLineOwnCompany, 41, 105);
		absolutePanelOwnCompany.add(textBoxTopLineOwnCompany, 136, 93);
		textBoxTopLineOwnCompany.setSize("162px", "24px");
		// Marktanteil
		absolutePanelOwnCompany.add(lbMarketShareOwnCompany, 41, 159);
		absolutePanelOwnCompany.add(textBoxMarketShareOwnCompany, 136, 147);
		textBoxMarketShareOwnCompany.setSize("161px", "24px");
		// Fixkosten
		absolutePanelOwnCompany.add(lbFixedCosts, 41, 211);
		absolutePanelOwnCompany.add(textBoxFixedCosts, 136, 199);
		textBoxFixedCosts.setSize("161px", "24px");
		// Produktpreis
		absolutePanelOwnCompany.add(lbProductPriceOwnCompany, 432, 159);
		absolutePanelOwnCompany.add(textBoxProductPriceOwnCompany, 577, 147);
		textBoxProductPriceOwnCompany.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelOwnCompany.add(lbSalesVolume, 432, 211);
		absolutePanelOwnCompany.add(textBoxSalesVolume, 577, 199);
		textBoxSalesVolume.setSize("161px", "24px");
		// Anzahl Mitarbeiter
		absolutePanelOwnCompany.add(lbNumberOfStaff, 432, 48);
		absolutePanelOwnCompany.add(textBoxNumberOfStaff, 577, 36);
		textBoxNumberOfStaff.setSize("161px", "24px");
		// durchschnittliches Mitarbeitergehalt
		absolutePanelOwnCompany.add(lbSalaryOfStaff, 432, 105);
		absolutePanelOwnCompany.add(textBoxSalaryOfStaff, 577, 93);
		textBoxSalaryOfStaff.setSize("161px", "24px");
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
				textBoxTopLineCompany1.setValue(null);
				textBoxMarketShareCompany1.setValue(null);
				textBoxProductPriceCompany1.setValue(null);
				textBoxSalesVolumeCompany1.setValue(null);
			}
		}); // Ende btDeleteCompany1
		// Unternehmen speichern
		btSaveCompany1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 1 in DB übernehmen
				
				
				
								
				if(	
						expInteger.test(textBoxTopLineCompany1.getText())
						&& expDouble.test(textBoxMarketShareCompany1.getText())
						&& expDouble.test(textBoxProductPriceCompany1.getText())
						&& expInteger.test(textBoxSalesVolumeCompany1.getText())
			
						)
				{
				
					//company[0] = new Company();
					
					companies.get(0).setTopLine(new Integer(textBoxTopLineCompany1
									.getText()));
					companies.get(0).setMarketShare(new Double(
									textBoxMarketShareCompany1.getText()));
					companies.get(0).getProduct().setPrice(new Double(textBoxProductPriceCompany1
							.getText()));
					companies.get(0).getProduct().setSalesVolume(new Integer(textBoxSalesVolumeCompany1.getText()));
					
					
					/* company[0].setTopLine(new Integer(textBoxTopLineCompany1
					*		.getText()));
					* company[0].setMarketShare(new Double(
					*		textBoxMarketShareCompany1.getText()));
					company[0].getProduct().setPrice(
							new Double(textBoxProductPriceCompany1
									.getText()));
					company[0].getProduct().setSalesVolume(
							new Integer(textBoxSalesVolumeCompany1.getText()));*/

					service.addCompany(companies.get(0), new AddCompanyCallback());
				} else {
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");
				} // Ende if-else
				
		
			}
		}); // Ende btSaveCompany1

		// Elemente
		// Umsatz
		absolutePanelCompany1.add(lbTopLineCompany1, 41, 48);
		absolutePanelCompany1.add(textBoxTopLineCompany1, 136, 36);
		textBoxTopLineCompany1.setSize("161", "24");
		// Marktanteil
		absolutePanelCompany1.add(lbMarketShareCompany1, 432, 48);
		absolutePanelCompany1.add(textBoxMarketShareCompany1, 577, 36);
		textBoxMarketShareCompany1.setSize("161", "24");
		// Produktpreis
		absolutePanelCompany1.add(lbProductPriceCompany1, 41, 104);
		absolutePanelCompany1.add(textBoxProductPriceCompany1, 136, 92);
		textBoxProductPriceCompany1.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelCompany1.add(lbSalesVolumeCompany1, 432, 104);
		absolutePanelCompany1.add(textBoxSalesVolumeCompany1, 577, 92);
		textBoxSalesVolumeCompany1.setSize("161px", "24px");

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
				textBoxTopLineCompany2.setValue(null);
				textBoxMarketShareCompany2.setValue(null);
				textBoxProductPriceCompany2.setValue(null);
				textBoxSalesVolumeCompany2.setValue(null);
			}
		}); // Ende btDeleteCompany2
		// Unternehmen speichern
		btSaveCompany2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 2 in DB übernehmen
				if(	
						expInteger.test(textBoxTopLineCompany2.getText())
						&& expDouble.test(textBoxMarketShareCompany2.getText())
						&& expDouble.test(textBoxProductPriceCompany2.getText())
						&& expInteger.test(textBoxSalesVolumeCompany2.getText())
			
						)
				{
					//company[0] = new Company();
					
					companies.get(1).setTopLine(new Integer(textBoxTopLineCompany2
									.getText()));
					companies.get(1).setMarketShare(new Double(
									textBoxMarketShareCompany2.getText()));
					companies.get(1).getProduct().setPrice(new Double(textBoxProductPriceCompany2
							.getText()));
					companies.get(1).getProduct().setSalesVolume(new Integer(textBoxSalesVolumeCompany2.getText()));
					
					
					/* company[0].setTopLine(new Integer(textBoxTopLineCompany1
					*		.getText()));
					* company[0].setMarketShare(new Double(
					*		textBoxMarketShareCompany1.getText()));
					company[0].getProduct().setPrice(
							new Double(textBoxProductPriceCompany1
									.getText()));
					company[0].getProduct().setSalesVolume(
							new Integer(textBoxSalesVolumeCompany1.getText()));*/

					service.addCompany(companies.get(1), new AddCompanyCallback());
				} else {
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");
				} // Ende if-else
			}
		}); // Ende btSaveCompany2

		// Elemente
		// Umsatz
		absolutePanelCompany2.add(lbTopLineCompany2, 41, 48);
		absolutePanelCompany2.add(textBoxTopLineCompany2, 136, 36);
		textBoxTopLineCompany2.setSize("161", "24");
		// Marktanteil
		absolutePanelCompany2.add(lbMarketShareCompany2, 432, 48);
		absolutePanelCompany2.add(textBoxMarketShareCompany2, 577, 36);
		textBoxMarketShareCompany2.setSize("161", "24");
		// Produktpreis
		absolutePanelCompany2.add(lbProductPriceCompany2, 41, 104);
		absolutePanelCompany2.add(textBoxProductPriceCompany2, 136, 92);
		textBoxProductPriceCompany2.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelCompany2.add(lbSalesVolumeCompany2, 432, 104);
		absolutePanelCompany2.add(textBoxSalesVolumeCompany2, 577, 92);
		textBoxSalesVolumeCompany2.setSize("161px", "24px");
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
				textBoxTopLineCompany3.setValue(null);
				textBoxMarketShareCompany3.setValue(null);
				textBoxProductPriceCompany3.setValue(null);
				textBoxSalesVolumeCompany3.setValue(null);
			}
		}); // Ende btDeleteCompany3
		// Unternehmen speichern
		btSaveCompany3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 3 in DB übernehmen
				if(	
						expInteger.test(textBoxTopLineCompany3.getText())
						&& expDouble.test(textBoxMarketShareCompany3.getText())
						&& expDouble.test(textBoxProductPriceCompany3.getText())
						&& expInteger.test(textBoxSalesVolumeCompany3.getText())
			
						)
				{
					//company[0] = new Company();
					
					companies.get(2).setTopLine(new Integer(textBoxTopLineCompany3
									.getText()));
					companies.get(2).setMarketShare(new Double(
									textBoxMarketShareCompany3.getText()));
					companies.get(2).getProduct().setPrice(new Double(textBoxProductPriceCompany3
							.getText()));
					companies.get(2).getProduct().setSalesVolume(new Integer(textBoxSalesVolumeCompany3.getText()));
					
					
					/* company[0].setTopLine(new Integer(textBoxTopLineCompany1
					*		.getText()));
					* company[0].setMarketShare(new Double(
					*		textBoxMarketShareCompany1.getText()));
					company[0].getProduct().setPrice(
							new Double(textBoxProductPriceCompany1
									.getText()));
					company[0].getProduct().setSalesVolume(
							new Integer(textBoxSalesVolumeCompany1.getText()));*/

					service.addCompany(companies.get(2), new AddCompanyCallback());
				} else {
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");
				} // Ende if-else
			}
		}); // Ende btSaveCompany3

		// Elemente
		// Umsatz
		absolutePanelCompany3.add(lbTopLineCompany3, 41, 48);
		absolutePanelCompany3.add(textBoxTopLineCompany3, 136, 36);
		textBoxTopLineCompany3.setSize("161", "24");
		// Marktanteil
		absolutePanelCompany3.add(lbMarketShareCompany3, 432, 48);
		absolutePanelCompany3.add(textBoxMarketShareCompany3, 577, 36);
		textBoxMarketShareCompany3.setSize("161", "24");
		// Produktpreis
		absolutePanelCompany3.add(lbProductPriceCompany3, 41, 104);
		absolutePanelCompany3.add(textBoxProductPriceCompany3, 136, 92);
		textBoxProductPriceCompany3.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelCompany3.add(lbSalesVolumeCompany3, 432, 104);
		absolutePanelCompany3.add(textBoxSalesVolumeCompany3, 577, 92);
		textBoxSalesVolumeCompany3.setSize("161px", "24px");
	} // Ende method addTabPanelCompanyThree

	private void startCall() {
		service.getOwnCompany(new GetOwnCompanyCallback());
		service.getCompany(new GetCompanyCallback());
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
				Window.alert("Das eigene Unternehmen wurde aktualisiert");
		} // Ende method onSuccess
	} // Ende class AddOwnCompanyCallback
	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher ein
	 * angelegtes Unternehmen speichert
	 * 
	 */
	public class AddCompanyCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende onFailure

		@Override
		public void onSuccess(Void result) {
			Window.alert("Das Unternehmen wurde aktualisiert");
		} // Ende method onSuccess
	} // Ende class AddCompanyCallback
	
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
			textBoxTopLineOwnCompany
					.setText(new Integer(result.getTopLine()).toString());
			textBoxMarketShareOwnCompany.setText(new Double(result.getMarketShare()).toString());
			textBoxFixedCosts.setText(new Double(result.getFixedCosts()).toString());
			textBoxProductPriceOwnCompany.setText(new Double(result.getProduct()
					.getPrice()).toString());
			textBoxSalesVolume.setText(new Integer(result.getProduct()
					.getSalesVolume()).toString());
			textBoxNumberOfStaff.setText(new Integer(result
					.getNumberOfStaff()).toString());
			textBoxSalaryOfStaff
					.setText(new Integer(result.getSalaryStaff()).toString());

		} // Ende method onSuccess
	} // Ende class GetOwnCompanyCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher bereits
	 * angelegte Unternehmen aus der Datenbank zurückgibt
	 * 
	 */
	public class GetCompanyCallback implements AsyncCallback<List<Company>> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende onFailure

		@Override
		public void onSuccess(List<Company> result) {
			companies = result;
			textBoxTopLineCompany1.setText(new Integer(result.get(0).getTopLine()).toString());
			textBoxMarketShareCompany1.setValue(new Double(result.get(0)
					.getMarketShare()).toString());
			textBoxProductPriceCompany1.setValue(new Double(result.get(0).getProduct()
					.getPrice()).toString());
			textBoxSalesVolumeCompany1.setValue(new Integer(result.get(0).getProduct()
					.getSalesVolume()).toString());

			textBoxTopLineCompany2.setValue(new Integer(result.get(1).getTopLine()).toString());
			textBoxMarketShareCompany2.setValue(new Double(result.get(1)
					.getMarketShare()).toString());
			textBoxProductPriceCompany2.setValue(new Double(result.get(1).getProduct()
					.getPrice()).toString());
			textBoxSalesVolumeCompany2.setValue(new Integer(result.get(1).getProduct()
					.getSalesVolume()).toString());

			textBoxTopLineCompany3.setValue(new Integer(result.get(2).getTopLine()).toString());
			textBoxMarketShareCompany3.setValue(new Double(result.get(2)
					.getMarketShare()).toString());
			textBoxProductPriceCompany3.setValue(new Double(result.get(2).getProduct()
					.getPrice()).toString());
			textBoxSalesVolumeCompany3.setValue(new Integer(result.get(2).getProduct()
					.getSalesVolume()).toString());

		} // Ende method onSuccess
	} // Ende class GetOwnCompanyCallback
} // Ende class CompanySimulation