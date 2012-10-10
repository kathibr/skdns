package de.dhbw.wwi11sca.skdns.shared;

import java.io.Serializable;

public class Unternehmen implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private int umsatz;
	private int gewinn;
	private double marktAnteil;
	private String nachfrageTendenz;
	private Produkt produkt;
	 
	public Unternehmen(){}
	public Unternehmen(String name,int umsatz, int gewinn, double marktAnteil, String nachfrageTendenz, Produkt produkt){
		this.name=name;
		this.umsatz=umsatz;
		this.gewinn=gewinn;
		this.marktAnteil=marktAnteil;
		this.nachfrageTendenz=nachfrageTendenz;
		this.produkt=produkt;
	}
	public Unternehmen(String name,int umsatz, int gewinn, double marktAnteil, String nachfrageTendenz){
		this.name=name;
		this.umsatz=umsatz;
		this.gewinn=gewinn;
		this.marktAnteil=marktAnteil;
		this.nachfrageTendenz=nachfrageTendenz;
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getGewinn() {
		return gewinn;
	}
	public void setGewinn(int gewinn) {
		this.gewinn = gewinn;
	}
	public String getNachfrageTendenz() {
		return nachfrageTendenz;
	}
	public void setNachfrageTendenz(String nachfrageTendenz) {
		this.nachfrageTendenz = nachfrageTendenz;
	}
	public Produkt getProdukt() {
		return produkt;
	}
	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	}
	
	

}
