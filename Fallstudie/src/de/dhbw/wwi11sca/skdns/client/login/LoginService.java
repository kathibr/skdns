package de.dhbw.wwi11sca.skdns.client.login;


import de.dhbw.wwi11sca.skdns.shared.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.dhbw.wwi11sca.skdns.client.DelistedException;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	String loginServer(String name) throws IllegalArgumentException;
	void checkLogin(User user) throws DelistedException;
}
