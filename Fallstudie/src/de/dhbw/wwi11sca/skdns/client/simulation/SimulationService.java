package de.dhbw.wwi11sca.skdns.client.simulation;

import java.util.List;

import de.dhbw.wwi11sca.skdns.shared.*;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("simulation")
public interface SimulationService extends RemoteService{

	List<Unternehmen> getUnternehmen();
	
	EigenesUnternehmen getEigenesUnternehmen();

	Simulationsversion getVersion();

	
}
