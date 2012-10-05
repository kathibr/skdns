package de.dhbw.wwi11sca.skdns.client.login;

import de.dhbw.wwi11sca.skdns.shared.User;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface LoginServiceAsync {

	void getKennDaten(AsyncCallback<Boolean> callback);

	
	void loginServer(String name, AsyncCallback<String> callback);
	void checkLogin(User user, AsyncCallback<Void> callback);

}
