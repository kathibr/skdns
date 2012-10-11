package de.dhbw.wwi11sca.skdns.server;



import java.net.UnknownHostException;
import java.util.List;
import de.dhbw.wwi11sca.skdns.client.simulation.SimulationService;
import de.dhbw.wwi11sca.skdns.shared.*;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SimulationServiceImpl extends RemoteServiceServlet implements
		SimulationService {

	@Override
	public List<Unternehmen> getUnternehmen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EigenesUnternehmen getEigenesUnternehmen() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<EigenesUnternehmen> dbEUN =  ds.createQuery(EigenesUnternehmen.class).asList();
		EigenesUnternehmen single = dbEUN.get(0);
		return single;
	}

	@Override
	public Simulationsversion getVersion() {
		// TODO Auto-generated method stub
		return null;
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

