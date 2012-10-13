package de.dhbw.wwi11sca.skdns.client.home;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * HomeServiceAsync ist ein Interface für die Kommunikation der HomeSimulation mit der HomeServiceImpl im Server.
 *
 */
import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;

public interface HomeServiceAsync {

	void getUnternehmen(AsyncCallback<List<Unternehmen>> callback);

	void getEigenesUnternehmen(AsyncCallback<EigenesUnternehmen> callback);

	void loginServer(String name, AsyncCallback<String> callback);

}
