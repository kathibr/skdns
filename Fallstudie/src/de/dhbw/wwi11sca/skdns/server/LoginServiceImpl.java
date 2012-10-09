package de.dhbw.wwi11sca.skdns.server;

import java.net.UnknownHostException;
import java.util.List;
import de.dhbw.wwi11sca.skdns.client.DelistedException;
import de.dhbw.wwi11sca.skdns.client.login.LoginService;
import de.dhbw.wwi11sca.skdns.shared.User;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

//@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
	
	boolean kontrolle = false;
	private String username;
	private String kennwort;
	private static final long serialVersionUID = -179774088492873807L;
	
	@Override
	public void checkLogin(User user) throws DelistedException {
		username = (String) user.getUsername().trim();
		kennwort = (String) user.getKennwort().trim();
 		
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		
		List<User> dbUser =  ds.createQuery(User.class).filter("username =", username).asList();
		User first = dbUser.get(0);
			   
		String dbUsername = (String) first.getUsername();
		String dbKennwort = (String) first.getKennwort();
			   
		if((username.equals(dbUsername))&&(kennwort.equals(dbKennwort))){
		 //success
		}else{
			throw new DelistedException("ERR"); 
		}
	}

	@Override
	public String loginServer(String name) throws IllegalArgumentException {
		return null;
	}
	private static Mongo getMongo() {
        Mongo m = null;
	    try {
            m = new Mongo("localhost", 27017);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return m;
	}
}

