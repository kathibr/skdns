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
	public OwnCompany getCompany() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<OwnCompany> dbOwnCompany = ds.createQuery(
				OwnCompany.class).asList();
		OwnCompany single = dbOwnCompany.get(0);
		return single;
	}

	@Override
	public SimulationVersion createSimulationCallback(SimulationVersion version) {
		// TODO Simulationsberechnung
		// eine SimulationVersion wurde erzeugt und mit den Feldern
		// simulationYear, version, personal,
		// machineValue, machineCapacity, machineStaff, marketing, price
		// gefüllt worden

		// nun sollen passend dazu die Felder ownCompany, company1, company2,
		// company3 gefüllt werden, indem die passenden Daten dazu aus der DB
		// geholt werden

		// berechnungen ausführen lassen: MarketSimulation(version);

		// nach der Berechnung soll eine SimulationVersion mit den gefüllten
		// Feldern
		// simulationYear (wie aus GUI vorgegeben), version (wie aus GUI
		// vorgegeben),
		// company, profit, topLine, marketShare, trendOfRequest, ownCompany, company1, company2, company3
		// zurückgegeben werden

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
