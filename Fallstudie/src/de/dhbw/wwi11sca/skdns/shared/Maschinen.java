package de.dhbw.wwi11sca.skdns.shared;

import java.io.Serializable;

public class Maschinen implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int kapazitaet;
	private int nutzungsDauer;
	private int noetigeMitarbeiter;
	private double buchwert;

	
	// Getter-Setter-Methoden
	public int getKapazitaet() {
		return kapazitaet;
	}
	public int getNutzungsDauer() {
		return nutzungsDauer;
	}
	public int getNoetigeMitarbeiter() {
		return noetigeMitarbeiter;
	}
	public double getBuchwert() {
		return buchwert;
	}
	public void setBuchwert(double buchwert) {
		this.buchwert = buchwert;
	}
	

}
