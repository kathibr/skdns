package de.dhbw.wwi11sca.skdns.client.unternehmen;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;

@RemoteServiceRelativePath("unternehmen")
public interface UnternehmenService extends RemoteService  {

	public List<Unternehmen> getUnternehmen();
	public EigenesUnternehmen getEigenesUnternehmen();	

}
