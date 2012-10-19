package de.dhbw.wwi11sca.skdns.shared;

import java.io.Serializable;

public class Machines implements Serializable {

	private static final long serialVersionUID = 1L;
	private int capacity;
	private int serviceLife;
	private int staff;
	private double accoutingValue;

	public Machines() {
	}

	public Machines(int capacity, int serviceLife, int staff,
			double accountingValue) {
		this.setCapacity(capacity);
		this.setServiceLife(serviceLife);
		this.setStaff(staff);
		this.setAccoutingValue(accountingValue);
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getServiceLife() {
		return serviceLife;
	}

	public void setServiceLife(int serviceLife) {
		this.serviceLife = serviceLife;
	}

	public double getAccoutingValue() {
		return accoutingValue;
	}

	public void setAccoutingValue(double accoutingValue) {
		this.accoutingValue = accoutingValue;
	}

	public int getStaff() {
		return staff;
	}

	public void setStaff(int staff) {
		this.staff = staff;
	}

}
