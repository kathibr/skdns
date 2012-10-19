package de.dhbw.wwi11sca.skdns.client.simulation;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;

@RemoteServiceRelativePath("simulation")
public interface SimulationService extends RemoteService {

	SimulationVersion createSimulationCallback(SimulationVersion version);

	OwnCompany getCompany();

}
