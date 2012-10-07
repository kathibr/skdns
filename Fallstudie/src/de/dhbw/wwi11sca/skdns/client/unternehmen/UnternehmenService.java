package de.dhbw.wwi11sca.skdns.client.unternehmen;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;

@RemoteServiceRelativePath("unternehmen")
public interface UnternehmenService {

	List<Unternehmen> getUnternehmen();

	EigenesUnternehmen getEigenesUnternehmen();

}
