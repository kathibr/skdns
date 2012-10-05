package de.dhbw.wwi11sca.skdns.shared;

import java.io.Serializable;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username  = new String();
	private String kennwort = new String();
	
	public User(){}
	public User(String username, String kennwort){
		this.username = username;
		this.kennwort = kennwort;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getKennwort() {
		return kennwort;
	}
	public void setKennwort(String kennwort) {
		this.kennwort = kennwort;
	}

//	public User(String kennwort, String username){
//		this.kennwort = kennwort;
//		this.username = username;
//	}
}
