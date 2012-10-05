package de.dhbw.wwi11sca.skdns.server;



import java.util.List;
import de.dhbw.wwi11sca.skdns.client.simulation.SimulationService;
import de.dhbw.wwi11sca.skdns.shared.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Simulationsversion getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	
	}

