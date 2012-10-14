package de.dhbw.wwi11sca.skdns.client.admin;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.dhbw.wwi11sca.skdns.shared.Admin;
import de.dhbw.wwi11sca.skdns.shared.User;


@RemoteServiceRelativePath("admin")
public interface AdminService extends RemoteService{

	public List<User> getUser();

	void saveUser(User newUser);

	void deleteUser(String deleteUser);

	Admin getStats();

}
