package de.dhbw.wwi11sca.skdns.server;


import java.util.ArrayList;
import java.util.List;

import de.dhbw.wwi11sca.skdns.client.home.HomeService;
import de.dhbw.wwi11sca.skdns.shared.Produkt;
import de.dhbw.wwi11sca.skdns.shared.Unternehmen;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class HomeServiceImpl extends RemoteServiceServlet implements
		HomeService {

	@Override
	public String loginServer(String name) throws IllegalArgumentException {
		return null;
	}

	@Override
	public List<Unternehmen> getUnternehmen() {
		// TODO Daten aus der DB holen und an die GUI übergeben
		// Daten: 4 Unternehmen, Felder: umsatz; gewinn; marktAnteil; nachfrageTendenz; produkt;
		
		return null;
	}

	

	
}
