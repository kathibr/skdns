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
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;
import de.dhbw.wwi11sca.skdns.shared.User;

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

		List<Company> dbCompany = ds.createQuery(Company.class)
				.filter("userID =", LoginServiceImpl.getUserID()).asList();

		List<OwnCompany> dbOwnCompany = ds.createQuery(OwnCompany.class)
				.filter("userID = ", LoginServiceImpl.getUserID()).asList();

		// Eigenes Unternehmen aus der Datenbank laden und am Anfang der Liste
		// in die Liste aufnehmen
		OwnCompany single = dbOwnCompany.get(0);

		dbCompany.add(0, single);

		return dbCompany;
	} // Ende method getCompany

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

	@Override
	public void deleteVersions() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<SimulationVersion> versions = ds
				.createQuery(SimulationVersion.class)
				.filter("userID =", LoginServiceImpl.getUserID()).asList();
		
		
		if(versions.size() > 3)
			{
			ds.delete(ds.createQuery(SimulationVersion.class).filter("userID = ",
					LoginServiceImpl.getUserID()));
			versions.get(versions.size()-1);
			for (int i = versions.size(); i > versions.size() - 3; i--) {
				ds.save(versions.get(i));
			}
			}
		

	}

} // Ende class HomeServiceImpl
