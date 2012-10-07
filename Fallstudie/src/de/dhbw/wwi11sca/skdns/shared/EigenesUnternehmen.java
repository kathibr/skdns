package de.dhbw.wwi11sca.skdns.shared;

import java.io.Serializable;

public class EigenesUnternehmen extends Unternehmen implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double fixkosten;
	private Maschinen maschinen;
	private int mitarbeiterGehalt;	
	private int mitarbeiterAnzahl;
	
	// Getter-Setter-Methoden
	public double getFixkosten() {
		return fixkosten;
	}
	public void setFixkosten(double fixkosten) {
		this.fixkosten = fixkosten;
	}
	public Maschinen getMaschinen() {
		return maschinen;
	}
	public void setMaschinen(Maschinen maschinen) {
		this.maschinen = maschinen;
	}
	public int getMitarbeiterGehalt() {
		return mitarbeiterGehalt;
	}
	public void setMitarbeiterGehalt(int mitarbeiterGehalt) {
		this.mitarbeiterGehalt = mitarbeiterGehalt;
	}
	public int getMitarbeiterAnzahl() {
		return mitarbeiterAnzahl;
	}
	public void setMitarbeiterAnzahl(int mitarbeiterAnzahl) {
		this.mitarbeiterAnzahl = mitarbeiterAnzahl;
	}
	

}
