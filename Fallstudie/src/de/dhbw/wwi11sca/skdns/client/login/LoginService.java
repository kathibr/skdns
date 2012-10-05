package de.dhbw.wwi11sca.skdns.client.login;


import java.util.List;

import de.dhbw.wwi11sca.skdns.shared.Unternehmen;
import de.dhbw.wwi11sca.skdns.shared.User;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.dhbw.wwi11sca.skdns.client.DelistedException;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	
	String loginServer(String name) throws IllegalArgumentException;
	
	public boolean getKennDaten();

	void checkLogin(User user) throws DelistedException;

		
}
