package de.dhbw.wwi11sca.skdns.shared;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Diese Objektklasse enthält alle wichtigen Daten eines Users.
 * 
 */
import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username = new String();
	private String kennwort = new String();
	private String mail = new String();
	private boolean forgottenPasswort = false;

	public User() {
	}

	public User(String username, String kennwort, String mail) {
		this.username = username;
		this.kennwort = kennwort;
		this.mail = mail;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isForgottenPasswort() {
		return forgottenPasswort;
	}

	public void setForgottenPasswort(boolean forgottenPasswort) {
		this.forgottenPasswort = forgottenPasswort;
	}

}
