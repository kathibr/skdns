package de.dhbw.wwi11sca.skdns.server;

import java.net.UnknownHostException;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import de.dhbw.wwi11sca.skdns.client.unternehmen.CompanyService;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;

@SuppressWarnings("serial")
public class CompanyServiceImpl extends RemoteServiceServlet implements
		CompanyService {

	public List<Company> getCompany() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<Company> dbCompanies = ds.createQuery(Company.class).asList();
		return dbCompanies;
	}

	@Override
	public OwnCompany getOwnCompany() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<OwnCompany> dbOwnCompany = ds.createQuery(OwnCompany.class)
				.asList();
		OwnCompany singleUN = dbOwnCompany.get(0);

		return singleUN;
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

	@Override
	public void addOwnCompany(OwnCompany ownCompany) {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		// ds.createQuery(EigenesUnternehmen.class);
		Query<OwnCompany> q = ds.createQuery(OwnCompany.class);
		ds.delete(q);
		ds.save(ownCompany);
		// PersistenceManager.getDatastore().save(student);

	}

}
