package de.dhbw.wwi11sca.skdns.server;

import java.net.UnknownHostException;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import de.dhbw.wwi11sca.skdns.client.company.CompanyService;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;

@SuppressWarnings("serial")
public class CompanyServiceImpl extends RemoteServiceServlet implements
		CompanyService {

	public List<Company> getCompany() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<Company> dbCompanies = ds.createQuery(Company.class).filter("userID = ", LoginServiceImpl.getUserID()).asList();
		// sucht alle Unternehmen raus, die nicht die UserID aus
		// LoginServiceImpl haben und l�scht sie aus der Liste
//		for (Company company : dbCompanies) {
//			if (company.getUserID() != LoginServiceImpl.getUserID()) {
//				dbCompanies.remove(company);
//			}
//		}
		return dbCompanies;
	} // Ende method getCompany

	public OwnCompany getOwnCompany() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<OwnCompany> dbOwnCompany = ds.createQuery(OwnCompany.class).filter("userID = ", LoginServiceImpl.getUserID())
				.asList();
		// sucht alle Unternehmen raus, die nicht die UserID aus
		// LoginServiceImpl haben und l�scht sie aus der Liste
//		for (Company company : dbOwnCompany) {
//			if (company.getUserID() != LoginServiceImpl.getUserID()) {
//				dbOwnCompany.remove(company);
//			}
//		}
		OwnCompany singleUN = dbOwnCompany.get(0);

		return singleUN;
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

	public void addOwnCompany(OwnCompany ownCompany) {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		Query<OwnCompany> updateQuery = ds.createQuery(OwnCompany.class).field("userID").equal(LoginServiceImpl.getUserID());
		// ds.createQuery(EigenesUnternehmen.class);
		UpdateOperations<OwnCompany> ops;
		ops = ds.createUpdateOperations(OwnCompany.class)
				.set("tradeName", ownCompany.getTradeName())
				.set("topLine", ownCompany.getTopLine())
				.set("marketShare", ownCompany.getMarketShare())
				.set("fixedCosts", ownCompany.getFixedCosts())
				.set("numberOfStaff",ownCompany.getNumberOfStaff())
				.set("salaryStaff",ownCompany.getSalaryStaff())
				.set("product", ownCompany.getProduct());
								
		ds.update(updateQuery,ops);
//		Query<OwnCompany> q = ds.createQuery(OwnCompany.class);
//		ds.delete(q);
//		ds.save(ownCompany);
		// PersistenceManager.getDatastore().save(student);

	} // Ende method addOwnCompany

} // Ende class CompanyServiceImpl
