package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * In der MarketSimulation werden alle Berechnungen für die Marktsimulation ausgeführt.
 *
 */
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.Machines;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;

public class MarketSimulation {

	OwnCompany ownCompany;
	Company company1;
	Company company2;
	Company company3;

	// Sonderfall 1
	double freePersonal = 0;
	int necessaryPersonal = 0;
	String necessaryPersonalInfo;

	// Schritt 2
	int random = 0;
	int topLineMarket = 0;
	int topLineMarketArithmetic = 0;
	final static double increaseRandom[] = { 1.01, 1.01, 1.02, 1.03, 1.04,
			1.02, 1.02, 1.01, 1.00, 0.99, 0.98, 1.01, 1.05, 1.02, 0.99, 1.03 };

	// Schritt 3
	final static double marketingRandom[] = { 0.00, 0.00, 0.00, 0.00, 0.05,
			0.05, 0.05, 0.05, 0.05, 0.05, 0.1, 0.1, 0.1, 0.15, 0.15, 0.2 };

	// Schritt 5
	int gewinn = 0;
	int amortization = 0;
	String unusedMachineCapacityInfo;

	public SimulationVersion simulate(SimulationVersion version) {

		ownCompany = version.getOwnCompany();
		company1 = version.getCompany1();
		company2 = version.getCompany2();
		company3 = version.getCompany3();

		// TODO: wenn eine company nicht gefüllt ist werte mit 0 füllen

		// Sonderfall 1
		// Überprüft, ob genügend Personal für den Einsatz aller Maschinen
		// vorhanden ist
		specialCaseOne(version);

		// Schritt 1
		// Berechnung des Gesamtumsatz des Marktes
		calculateTopLineMarket(topLineMarketArithmetic,
				ownCompany.getTopLine(), company1.getTopLine(),
				company2.getTopLine(), company3.getTopLine());

		// Schritt 2
		// Ermittlung des Marktwachstums und erneute Berechnung des Gesamtumsatz
		// des Markets
		increaseRandom(1, 16, random);
		topLineMarket = (int) Math.ceil(increaseRandom[random] * topLineMarket);

		// Schritt 3
		// Ermittlung des Umsatz aller Unternehmen
		calculateTopLineOwnCompany(version.getMarketing(),
				ownCompany.getTopLine(), ownCompany.getProduct().getPrice(),
				version.getPrice());

		calculateTopLineCompanies(company1.getTopLine());
		calculateTopLineCompanies(company2.getTopLine());
		calculateTopLineCompanies(company3.getTopLine());

		// Schritt 4
		// Ermittlung des Marktgesamtumsatzes anhand der in Schritt 3
		// ermittelten Unternehmensumsätze
		calculateTopLineMarket(topLineMarket, ownCompany.getTopLine(),
				company1.getTopLine(), company2.getTopLine(),
				company3.getTopLine());

		// Ermittlung der Unternehmensmarktanteile
		calculateMarketShare(topLineMarketArithmetic, ownCompany.getTopLine(),
				ownCompany.getMarketShare());
		calculateMarketShare(topLineMarketArithmetic, company1.getTopLine(),
				company1.getMarketShare());
		calculateMarketShare(topLineMarketArithmetic, company2.getTopLine(),
				company2.getMarketShare());
		calculateMarketShare(topLineMarketArithmetic, company3.getTopLine(),
				company3.getMarketShare());

		// Schritt 5
		// Ermittlung der Unternehmensabsatzmengen
		calculateSalesVolume(topLineMarket, ownCompany.getMarketShare(),
				ownCompany.getProduct().getPrice(), ownCompany.getProduct()
						.getSalesVolume());
		calculateSalesVolume(topLineMarket, company1.getMarketShare(), company1
				.getProduct().getPrice(), company1.getProduct()
				.getSalesVolume());
		calculateSalesVolume(topLineMarket, company2.getMarketShare(), company2
				.getProduct().getPrice(), company2.getProduct()
				.getSalesVolume());
		calculateSalesVolume(topLineMarket, company3.getMarketShare(), company3
				.getProduct().getPrice(), company3.getProduct()
				.getSalesVolume());

		// Überprüfung, ob der Markt wächst oder sinkt
		if (random > 1) {
			// der Markt wächst == 1
			version.setMarketIncrease(1);
		} else if (random < 1) {
			// der Markt sinkt == -1
			version.setMarketIncrease(-1);
		} else {
			// der Markt verändert sich nicht == 0
			version.setMarketIncrease(0);
		} // Ende if-else

		// Ermittlung der Maschinenabschreibungen
		getAmortization(amortization, version.getMachineValue(),
				ownCompany.getMachines());

		// Ermittlung des Gewinns des eigenen Unternehmens
		gewinn = (int) Math.ceil(ownCompany.getTopLine()
				- ownCompany.getFixedCosts() - ownCompany.getSalaryStaff()
				* ownCompany.getNumberOfStaff() - version.getMarketing()
				- ownCompany.getVariableCosts() - amortization);

		ownCompany.setAmount(gewinn);

		// Sonderfall 2
		// Ermittelt, ob die Kapazitäten der Maschinen ausgelastet sind
		specialCaseTwo(version);

		return version;
	} // Ende method simulate

	public void specialCaseOne(SimulationVersion version) {
		freePersonal = ownCompany.getNumberOfStaff()
				* 0.7
				- (ownCompany.getMachines().getStaff() + version
						.getMachineStaff());

		if (freePersonal < 0) {
			necessaryPersonal = (int) Math
					.ceil((version.getMachineStaff() + ownCompany.getMachines()
							.getStaff()) / 0.7 - ownCompany.getNumberOfStaff());

			necessaryPersonalInfo = new String(
					"Für die neue Maschine ist nicht genügend Personal vorhanden und kann somit nicht in Betrieb genommen werden. Es fehlen mindestens "
							+ necessaryPersonal + " Mitarbeiter.");
		} // Ende if-Statement

	} // Ende method specialCaseOne

	public void specialCaseTwo(SimulationVersion version) {
		if (ownCompany.getProduct().getSalesVolume() < ownCompany.getMachines()
				.getCapacity()) {
			unusedMachineCapacityInfo = new String(
					"Die Kapazitäten sind nicht voll ausgelastet. Möchten Sie noch eine Maschine kaufen? Sie könnten dadurch Ihr Betriebergebnis steigern!");
			double MarketSharesCompanies = company1.getMarketShare()
					+ company2.getMarketShare() + company3.getMarketShare();
			calculateAdditionalVolume(MarketSharesCompanies, company1);
			calculateAdditionalVolume(MarketSharesCompanies, company2);
			calculateAdditionalVolume(MarketSharesCompanies, company3);
		} // Ende if-Statement
	} // Ende method specialCaseTwo

	private Company calculateAdditionalVolume(double marketSharesCompanies,
			Company company) {
		double distributionKey = company.getMarketShare()
				/ marketSharesCompanies;

		double salesVolume = (ownCompany.getProduct().getSalesVolume() - ownCompany
				.getMachines().getCapacity())
				* ownCompany.getProduct().getPrice()
				* distributionKey
				+ company.getProduct().getSalesVolume();

		company.setTopLine((int) (salesVolume * company.getProduct().getPrice()));

		calculateMarketShare(topLineMarketArithmetic, company.getTopLine(),
				company.getMarketShare());
		return company;

	} // Ende method calculateAdditionalVolume

	public static int calculateTopLineMarket(int topLineMarket,
			int tLOwnCompany, int tLCompany1, int tLCompany2, int tLCompany3) {

		return topLineMarket = tLOwnCompany + tLCompany1 + tLCompany2
				+ tLCompany3;
	} // Ende method calculateTopLineMarket

	public static int increaseRandom(int low, int high, int random) {
		return random = (int) Math.round(Math.random() * (high - low) + low);

	} // Ende method increaseRandom

	public static int calculateTopLineCompanies(int topLine) {

		// Marketing berechnen
		int random = (int) Math.random() * 15 + 1;
		double marketing = marketingRandom[random];

		// Preisfaktor berechnen
		double priceFactor = Math.random() * (1.01 - 0.99) + 0.99;

		// Umsatz berechnen
		topLine = (int) Math.ceil(topLine * marketing * priceFactor);
		return topLine;
	} // Ende method calculateTopLineCompanies

	public static int calculateTopLineOwnCompany(int marketingInput,
			int topLine, double priceOld, double priceNew) {

		double marketing = marketingInput / topLine;

		if (marketing > 0.2) {
			marketing = 0.2;
		} // Ende if-Statement
		double priceFactor = priceOld / priceNew;

		topLine = (int) (Math.ceil(topLine * marketing * priceFactor));
		return topLine;
	} // Ende method calculateTopLineOwnCompany

	public static double calculateMarketShare(int topLine, int topLineCompany,
			double marketShare) {
		return marketShare = (marketShare + (topLineCompany / topLine * 100)) * 0.5;
	} // Ende method calculateMarketShare

	public static int calculateSalesVolume(int topLineMarket,
			double marketShare, double price, int salesVolume) {
		int realTopLine = (int) (topLineMarket * marketShare / 100);

		return salesVolume = (int) Math.ceil(realTopLine / price);
	} // Ende method calculateSalesVolume

	private static int getAmortization(int amortization, int machineValue,
			Machines machine) {

		return amortization = (int) Math.ceil(machineValue / 10
				+ machine.getAccountingValue() / machine.getServiceLife());
	} // Ende method getAmortization
} // Ende class MarketSimulation
