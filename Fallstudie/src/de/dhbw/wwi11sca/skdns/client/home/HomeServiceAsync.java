package de.dhbw.wwi11sca.skdns.client.home;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;

public interface HomeServiceAsync {

	void getUnternehmen(AsyncCallback<List<Unternehmen>> callback);
	void loginServer(String name, AsyncCallback<String> callback);

}
