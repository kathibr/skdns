package de.dhbw.wwi11sca.skdns.shared;

import java.io.Serializable;

public class Maschinen implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private int kapazitaet;
	private int nutzungsDauer;
	private int noetigeMitarbeiter;
	private double buchwert;

	
	public Maschinen(){}
	public Maschinen(int kapazitaet, int nutzungsDauer, int noetigeMitarbeiter, double buchwert){
		this.kapazitaet = kapazitaet;
		this.nutzungsDauer = nutzungsDauer;
		this.noetigeMitarbeiter = noetigeMitarbeiter;
		this.buchwert = buchwert;
	}
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
