package de.dhbw.wwi11sca.skdns.shared;

import java.io.Serializable;

public class Simulationsversion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int personal;
	private int maschineWert;
	private int maschineKapazitaet;
	private int maschineMitarbeiter;	
	private int marketing;
	
	private int simulationsJahr;
	private int version;
	private Unternehmen unternehmen;
	private int gewinn;
	private int umsatz;
	private double marktAnteil;
	private double nachfrageTendenz;
	
	// Konstruktor
	public Simulationsversion(){
		
	}
	public Simulationsversion(int simulationsjahr, int version){
		this.setSimulationsJahr(simulationsjahr);
		this.setVersion(version);
		
	}
	
	// Getter-Methoden

	public int getPersonal() {
		return personal;
	}
	public void setPersonal(int personal) {
		this.personal = personal;
	}
	public int getMaschineWert() {
		return maschineWert;
	}
	public void setMaschineWert(int maschineWert) {
		this.maschineWert = maschineWert;
	}
	public int getMaschineKapazitaet() {
		return maschineKapazitaet;
	}
	public void setMaschineKapazitaet(int maschineKapazitaet) {
		this.maschineKapazitaet = maschineKapazitaet;
	}
	public int getMaschineMitarbeiter() {
		return maschineMitarbeiter;
	}
	public void setMaschineMitarbeiter(int maschineMitarbeiter) {
		this.maschineMitarbeiter = maschineMitarbeiter;
	}

	public int getMarketing() {
		return marketing;
	}

	public void setMarketing(int marketing) {
		this.marketing = marketing;
	}

	public int getSimulationsJahr() {
		return simulationsJahr;
	}

	public void setSimulationsJahr(int simulationsJahr) {
		this.simulationsJahr = simulationsJahr;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Unternehmen getUnternehmen() {
		return unternehmen;
	}

	public void setUnternehmen(Unternehmen unternehmen) {
		this.unternehmen = unternehmen;
	}

	public int getGewinn() {
		return gewinn;
	}

	public void setGewinn(int gewinn) {
		this.gewinn = gewinn;
	}

	public int getUmsatz() {
		return umsatz;
	}

	public void setUmsatz(int umsatz) {
		this.umsatz = umsatz;
	}

	public double getMarktAnteil() {
		return marktAnteil;
	}

	public void setMarktAnteil(double marktAnteil) {
		this.marktAnteil = marktAnteil;
	}

	public double getNachfrageTendenz() {
		return nachfrageTendenz;
	}

	public void setNachfrageTendenz(double nachfrageTendenz) {
		this.nachfrageTendenz = nachfrageTendenz;
	}	
}
