package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die HomeServiceImpl ist die Serverklasse für die HomeSimulation. 
 * Sie greift auf die Datenbank zu.
 *
 */

import java.net.UnknownHostException;
import java.util.List;
import de.dhbw.wwi11sca.skdns.client.home.HomeService;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;

@SuppressWarnings("serial")
public class HomeServiceImpl extends RemoteServiceServlet implements
		HomeService {

	@Override
	public String loginServer(String name) throws IllegalArgumentException {
		return null;
	} // Ende method loginServer

	@Override
	public List<Company> getCompany() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<Company> dbCompany = ds.createQuery(Company.class).asList();
		// sucht alle Unternehmen raus, die nicht die UserID aus
		// LoginServiceImpl haben und löscht sie aus der Liste
		for (Company company : dbCompany) {
			if (company.getUserID() != LoginServiceImpl.getUserID()) {
				dbCompany.remove(company);
			} // Ende if-Statement
		} // Ende for-Schleife
		return dbCompany;
	} // Ende method getCompany

	@Override
	public OwnCompany getOwnCompany() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<OwnCompany> dbOwnCompany = ds.createQuery(OwnCompany.class)
				.asList();
		// sucht alle Unternehmen raus, die nicht die UserID aus
		// LoginServiceImpl haben und löscht sie aus der Liste
		for (OwnCompany company : dbOwnCompany) {
			if (company.getUserID() != LoginServiceImpl.getUserID()) {
				dbOwnCompany.remove(company);
			} // Ende if-Statement
		} // Ende for-Schleife
		OwnCompany single = dbOwnCompany.get(0);

		return single;
	} // Ende method getOwnCompany

	private static Mongo getMongo() {
		Mongo m = null;
		try {
			m = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return m;
	} // Ende method getMongo

} // Ende class HomeServiceImpl
