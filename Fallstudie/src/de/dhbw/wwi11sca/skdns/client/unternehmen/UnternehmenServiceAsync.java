package de.dhbw.wwi11sca.skdns.client.unternehmen;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;

public interface UnternehmenServiceAsync {
	
	void getUnternehmen(AsyncCallback<List<Unternehmen>> callback);
	void getEigenesUnternehmen(AsyncCallback<EigenesUnternehmen> callback);
	void addEigenesUN(EigenesUnternehmen eigenesUN, AsyncCallback<Void> callback);
}
