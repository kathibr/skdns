package de.dhbw.wwi11sca.skdns.client.simulation;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * SimulationService ist ein Interface für die Kommunikation der Simulation mit der SimulationServiceImpl im Server.
 *
 */
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;

@RemoteServiceRelativePath("simulation")
public interface SimulationService extends RemoteService {

	SimulationVersion createSimulationCallback(SimulationVersion version);

	OwnCompany getCompany();

}
