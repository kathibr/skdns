package de.dhbw.wwi11sca.skdns.client.simulation;

import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimulationServiceAsync {

	void getCompany(AsyncCallback<OwnCompany> callback);

	void createSimulationCallback(SimulationVersion version,
			AsyncCallback<SimulationVersion> callback);

}
