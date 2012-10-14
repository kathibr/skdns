package de.dhbw.wwi11sca.skdns.client.admin;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.dhbw.wwi11sca.skdns.client.admin.AdminSimulation.SaveUserCallback;
import de.dhbw.wwi11sca.skdns.shared.Admin;
import de.dhbw.wwi11sca.skdns.shared.User;

public interface AdminServiceAsync {

	void getUser(AsyncCallback<List<User>> callback);

	void saveUser(User newUser, AsyncCallback<Void> callback);

	void deleteUser(String deleteUser, AsyncCallback<Void> callback);

	void getStats(AsyncCallback<Admin> callback);
}
