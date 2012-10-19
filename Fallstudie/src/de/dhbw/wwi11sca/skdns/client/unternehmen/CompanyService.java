package de.dhbw.wwi11sca.skdns.client.unternehmen;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;

@RemoteServiceRelativePath("company")
public interface CompanyService extends RemoteService {

	public List<Company> getCompany();

	public OwnCompany getOwnCompany();

	void addOwnCompany(OwnCompany ownCompany);
}
