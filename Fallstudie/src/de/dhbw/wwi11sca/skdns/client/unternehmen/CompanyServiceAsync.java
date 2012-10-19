package de.dhbw.wwi11sca.skdns.client.unternehmen;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;

public interface CompanyServiceAsync {

	void getCompany(AsyncCallback<List<Company>> callback);

	void getOwnCompany(AsyncCallback<OwnCompany> callback);

	void addOwnCompany(OwnCompany ownCompany, AsyncCallback<Void> callback);
}
