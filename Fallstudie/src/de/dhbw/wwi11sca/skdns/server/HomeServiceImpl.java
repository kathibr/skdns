package de.dhbw.wwi11sca.skdns.server;


import java.net.UnknownHostException;
import java.util.List;
import de.dhbw.wwi11sca.skdns.client.home.HomeService;
import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;
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
	public List<Unternehmen> getUnternehmen() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<Unternehmen> dbUN =  ds.createQuery(Unternehmen.class).asList();
		return dbUN;
	}
	@Override
	public List<EigenesUnternehmen> getEigenesUnternehmen() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<EigenesUnternehmen> dbEUN =  ds.createQuery(EigenesUnternehmen.class).asList();
		return dbEUN;
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
