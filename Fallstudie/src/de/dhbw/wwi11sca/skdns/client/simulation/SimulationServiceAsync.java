package de.dhbw.wwi11sca.skdns.client.simulation;

import java.util.List;

import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.Simulationsversion;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimulationServiceAsync {

	void getUnternehmen(AsyncCallback<List<Unternehmen>> callback);

	void getEigenesUnternehmen(AsyncCallback<EigenesUnternehmen> callback);

	void getVersion(AsyncCallback<Simulationsversion> callback);
	
	

}
