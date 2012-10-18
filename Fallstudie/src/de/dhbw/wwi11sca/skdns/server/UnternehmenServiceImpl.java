package de.dhbw.wwi11sca.skdns.server;

import java.net.UnknownHostException;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import de.dhbw.wwi11sca.skdns.client.unternehmen.UnternehmenService;
import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;


@SuppressWarnings("serial")
public class UnternehmenServiceImpl extends RemoteServiceServlet implements
		UnternehmenService {

	@Override
	public List<Unternehmen> getUnternehmen() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<Unternehmen> dbUN =  ds.createQuery(Unternehmen.class).asList();
		return dbUN;
	}
	@Override
	public EigenesUnternehmen getEigenesUnternehmen() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<EigenesUnternehmen> dbEUN =  ds.createQuery(EigenesUnternehmen.class).asList();		
		EigenesUnternehmen singleUN = dbEUN.get(0);
		
//		Query<EigenesUnternehmen> dsd = ds.createQuery(EigenesUnternehmen.class).filter(user + "un1", Object);
				
		
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
	public void addEigenesUN(EigenesUnternehmen eigenesUN) {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
	//	ds.createQuery(EigenesUnternehmen.class);
		Query<EigenesUnternehmen> q = ds.createQuery(EigenesUnternehmen.class);
		ds.delete(q);
		ds.save(eigenesUN);
		 //PersistenceManager.getDatastore().save(student);
		
	}
}
