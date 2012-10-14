package de.dhbw.wwi11sca.skdns.shared;

public class Admin {

	private int loginCount;
	private int existingUserCount;

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount += loginCount;
	}

	public int getExistingUserCount() {
		return existingUserCount;
	}

	public void setExistingUserCount(int existingUserCount) {
		this.existingUserCount = existingUserCount;
	}

}
