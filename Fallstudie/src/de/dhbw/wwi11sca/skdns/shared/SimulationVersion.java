package de.dhbw.wwi11sca.skdns.shared;

import java.io.Serializable;

public class SimulationVersion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int personal;
	private int machineValue;
	private int machineCapacity;
	private int machineStaff;
	private int marketing;
	private int price;
	private int marketIncrease;

	private int simulationYear;
	private int version;

	private EigenesUnternehmen ownCompany;
	private Unternehmen company1;
	private Unternehmen company2;
	private Unternehmen company3;

	// Konstruktor
	public SimulationVersion() {

	}

	public SimulationVersion(int simulationYear, int simulationVersion) {
		this.setSimulationYear(simulationYear);
		this.setVersion(simulationVersion);

	}

	// Getter-Methoden

	public int getPersonal() {
		return personal;
	}

	public void setPersonal(int personal) {
		this.personal = personal;
	}

	public int getMachineValue() {
		return machineValue;
	}

	public void setMachineValue(int machineValue) {
		this.machineValue = machineValue;
	}

	public int getMachineCapacity() {
		return machineCapacity;
	}

	public void setMachineCapacity(int machineCapacity) {
		this.machineCapacity = machineCapacity;
	}

	public int getMachineStaff() {
		return machineStaff;
	}

	public void setMachineStaff(int machineStaff) {
		this.machineStaff = machineStaff;
	}

	public int getMarketing() {
		return marketing;
	}

	public void setMarketing(int marketing) {
		this.marketing = marketing;
	}

	public int getSimulationYear() {
		return simulationYear;
	}

	public void setSimulationYear(int simulationYear) {
		this.simulationYear = simulationYear;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public EigenesUnternehmen getOwnCompany() {
		return ownCompany;
	}

	public void setOwnCompany(EigenesUnternehmen ownCompany) {
		this.ownCompany = ownCompany;
	}

	public Unternehmen getCompany1() {
		return company1;
	}

	public void setCompany1(Unternehmen company1) {
		this.company1 = company1;
	}

	public Unternehmen getCompany2() {
		return company2;
	}

	public void setCompany2(Unternehmen company2) {
		this.company2 = company2;
	}

	public Unternehmen getCompany3() {
		return company3;
	}

	public void setCompany3(Unternehmen company3) {
		this.company3 = company3;
	}

	public int getMarketIncrease() {
		return marketIncrease;
	}

	public void setMarketIncrease(int marketIncrease) {
		this.marketIncrease = marketIncrease;
	}

}
