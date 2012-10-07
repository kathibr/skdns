package de.dhbw.wwi11sca.skdns.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.dhbw.wwi11sca.skdns.client.unternehmen.UnternehmenService;
import de.dhbw.wwi11sca.skdns.shared.EigenesUnternehmen;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;

@SuppressWarnings("serial")
public class UnternehmenServiceImpl extends RemoteServiceServlet implements
		UnternehmenService {

	@Override
	public List<Unternehmen> getUnternehmen() {
		// TODO Unternehmen aus der Datenbank holen
		return null;
	}

	@Override
	public EigenesUnternehmen getEigenesUnternehmen() {
		// TODO Eigenes Unternehmen aus der Datenbank holen
		return null;
	}

}
