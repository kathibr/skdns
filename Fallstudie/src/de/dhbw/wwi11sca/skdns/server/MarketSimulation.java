package de.dhbw.wwi11sca.skdns.server;

import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.Maschinen;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;

public class MarketSimulation {

	EigenesUnternehmen ownCompany;
	Unternehmen company1;
	Unternehmen company2;
	Unternehmen company3;

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

		specialCaseOne(version);

		// Schritt 1

		calculateTopLineMarket(topLineMarketArithmetic, ownCompany.getUmsatz(),
				company1.getUmsatz(), company2.getUmsatz(),
				company3.getUmsatz());

		// Schritt 2

		increaseRandom(1, 16, random);
		topLineMarket = (int) Math.ceil(increaseRandom[random] * topLineMarket);

		// Schritt 3

		calculateTopLineOwnCompany(version.getMarketing(),
				ownCompany.getUmsatz(), ownCompany.getProdukt().getPreis(),
				version.getPrice());

		calculateTopLineCompanies(company1.getUmsatz());
		calculateTopLineCompanies(company2.getUmsatz());
		calculateTopLineCompanies(company3.getUmsatz());

		// Schritt 4

		calculateTopLineMarket(topLineMarket, ownCompany.getUmsatz(),
				company1.getUmsatz(), company2.getUmsatz(),
				company3.getUmsatz());

		calculateMarketShare(topLineMarketArithmetic, ownCompany.getUmsatz(),
				ownCompany.getMarktAnteil());
		calculateMarketShare(topLineMarketArithmetic, company1.getUmsatz(),
				company1.getMarktAnteil());
		calculateMarketShare(topLineMarketArithmetic, company2.getUmsatz(),
				company2.getMarktAnteil());
		calculateMarketShare(topLineMarketArithmetic, company3.getUmsatz(),
				company3.getMarktAnteil());

		// Schritt 5

		calculateSalesVolume(topLineMarket, ownCompany.getMarktAnteil(),
				ownCompany.getProdukt().getPreis(), ownCompany.getProdukt()
						.getMenge());
		calculateSalesVolume(topLineMarket, company1.getMarktAnteil(), company1
				.getProdukt().getPreis(), company1.getProdukt().getMenge());
		calculateSalesVolume(topLineMarket, company2.getMarktAnteil(), company2
				.getProdukt().getPreis(), company2.getProdukt().getMenge());
		calculateSalesVolume(topLineMarket, company3.getMarktAnteil(), company3
				.getProdukt().getPreis(), company3.getProdukt().getMenge());

		if (random > 1) {
			version.setMarketIncrease(1);
		} else if (random < 1) {
			version.setMarketIncrease(-1);
		} else {
			version.setMarketIncrease(0);
		}

		getAmortization(amortization, version.getMachineValue(),
				ownCompany.getMaschinen());

		gewinn = (int) Math.ceil(ownCompany.getUmsatz()
				- ownCompany.getFixkosten() - ownCompany.getMitarbeiterGehalt()
				* ownCompany.getMitarbeiterAnzahl() - version.getMarketing()
				- ownCompany.getVariableKosten() - amortization);

		ownCompany.setGewinn(gewinn);

		// Sonderfall 2
		
		specialCaseTwo(version);

		return version;
	}

	public void specialCaseOne(SimulationVersion version) {
		freePersonal = ownCompany.getMitarbeiterAnzahl()
				* 0.7
				- (ownCompany.getMaschinen().getNoetigeMitarbeiter() + version
						.getMachineStaff());

		if (freePersonal < 0) {
			necessaryPersonal = (int) Math
					.ceil((version.getMachineStaff() + ownCompany
							.getMaschinen().getNoetigeMitarbeiter())
							/ 0.7
							- ownCompany.getMitarbeiterAnzahl());

			necessaryPersonalInfo = new String(
					"Für die neue Maschine ist nicht genügend Personal vorhanden und kann somit nicht in Betrieb genommen werden. Es fehlen mindestens "
							+ necessaryPersonal + " Mitarbeiter.");
		}

	}

	public void specialCaseTwo(SimulationVersion version) {
		if (ownCompany.getProdukt().getMenge() < ownCompany.getMaschinen()
				.getKapazitaet()) {
			unusedMachineCapacityInfo = new String(
					"Die Kapazitäten sind nicht voll ausgelastet. Möchten Sie noch eine Maschine kaufen? Sie könnten dadurch Ihr Betriebergebnis steigern!");
			double MarketSharesCompanies = company1.getMarktAnteil()
					+ company2.getMarktAnteil() + company3.getMarktAnteil();
			calculateAdditionalVolume(MarketSharesCompanies, company1);
			calculateAdditionalVolume(MarketSharesCompanies, company2);
			calculateAdditionalVolume(MarketSharesCompanies, company3);
		}
	}

	private Unternehmen calculateAdditionalVolume(double marketSharesCompanies,
			Unternehmen company) {
		double distributionKey = company.getMarktAnteil()
				/ marketSharesCompanies;

		double salesVolume = (ownCompany.getProdukt().getMenge() - ownCompany
				.getMaschinen().getKapazitaet())
				* ownCompany.getProdukt().getPreis() * distributionKey
				+ company.getProdukt().getMenge();
		
		company.setUmsatz((int) (salesVolume * company.getProdukt().getPreis()));
		
		calculateMarketShare(topLineMarketArithmetic, company.getUmsatz(),
				company.getMarktAnteil());
		return company;

	}

	public static int calculateTopLineMarket(int topLineMarket,
			int tLOwnCompany, int tLCompany1, int tLCompany2, int tLCompany3) {
		
		return topLineMarket = tLOwnCompany + tLCompany1 + tLCompany2
				+ tLCompany3;
	}

	public static int increaseRandom(int low, int high, int random) {
		return random = (int) Math.round(Math.random() * (high - low) + low);

	}

	public static int calculateTopLineCompanies(int topLine) {

		// Marketing berechnen
		int random = (int) Math.random() * 15 + 1;
		double marketing = marketingRandom[random];

		// Preisfaktor berechnen
		double priceFactor = Math.random() * (1.01 - 0.99) + 0.99;

		// Umsatz berechnen
		topLine = (int) Math.ceil(topLine * marketing * priceFactor);
		return topLine;
	}

	public static int calculateTopLineOwnCompany(int marketingInput,
			int topLine, double priceOld, double priceNew) {
		
		double marketing = marketingInput / topLine;

		if (marketing > 0.2) {
			marketing = 0.2;
		}
		double priceFactor = priceOld / priceNew;

		topLine = (int) (Math.ceil(topLine * marketing * priceFactor));
		return topLine;
	}

	public static double calculateMarketShare(int topLine, int topLineCompany,
			double marketShare) {
		return marketShare = (marketShare + (topLineCompany / topLine * 100)) * 0.5;
	}

	public static int calculateSalesVolume(int topLineMarket,
			double marketShare, double price, int salesVolume) {
		int realTopLine = (int) (topLineMarket * marketShare / 100);

		return salesVolume = (int) Math.ceil(realTopLine / price);
	}

	private static int getAmortization(int amortization, int machineValue,
			Maschinen machine) {

		return amortization = (int) Math.ceil(machineValue / 10
				+ machine.getBuchwert() / machine.getNutzungsDauer());
	}
}
