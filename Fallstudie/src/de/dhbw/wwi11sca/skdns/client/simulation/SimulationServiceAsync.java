package de.dhbw.wwi11sca.skdns.client.simulation;

import java.util.List;

import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimulationServiceAsync {

	void getCompany(AsyncCallback<EigenesUnternehmen> callback);

	void createSimulationCallback(SimulationVersion version,
			AsyncCallback<SimulationVersion> callback);

}
