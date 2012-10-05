package de.dhbw.wwi11sca.skdns.client.home;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.dhbw.wwi11sca.skdns.shared.*;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("home")
public interface HomeService extends RemoteService {
	
	String loginServer(String name) throws IllegalArgumentException;

	public List<Unternehmen> getUnternehmen();

	
	
	
		
}
