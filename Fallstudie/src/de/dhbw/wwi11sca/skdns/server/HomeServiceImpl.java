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
	}

	@Override
	public List<Company> getCompany() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<Company> dbCompany = ds.createQuery(Company.class).asList();
		return dbCompany;
	}

	@Override
	public OwnCompany getOwnCompany() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<OwnCompany> dbOwnCompany = ds.createQuery(OwnCompany.class).asList();
		OwnCompany single = dbOwnCompany.get(0);
		return single;
	}

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
	}

}
