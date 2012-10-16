package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die LoginServiceImpl ist die Serverklasse für die LoginSimulation. 
 * Sie greift auf die Datenbank zu.
 *
 */
import java.net.UnknownHostException;
import java.util.List;
import de.dhbw.wwi11sca.skdns.client.DelistedException;
import de.dhbw.wwi11sca.skdns.client.login.LoginService;
import de.dhbw.wwi11sca.skdns.shared.User;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	boolean kontrolle = false;
	private String username;
	private String kennwort;
	private static final long serialVersionUID = -179774088492873807L;
	User userForgottenPassword;

	@Override
	public void checkLogin(User user) throws DelistedException {
		username = (String) user.getUsername().trim();
		kennwort = (String) user.getKennwort().trim();

		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");

		List<User> dbUser = ds.createQuery(User.class)
				.filter("username =", username).asList();
		User first = dbUser.get(0);

		String dbUsername = (String) first.getUsername();
		String dbKennwort = (String) first.getKennwort();

		if ((username.equals(dbUsername)) && (kennwort.equals(dbKennwort))) {
			AdminServiceImpl.getAdmin().setLoginCount(1);
			// success
		} else {
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

	@Override
	public void forgotPassword(User user) throws DelistedException {
//		userForgottenPassword = new User();
//		userForgottenPassword.setUsername(user.getUsername().trim());
//		userForgottenPassword.setForgottenPasswort(true);

		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");

//		List<User> dbUser = ds.createQuery(User.class)
//				.filter("username =", username).asList();

//		User first = dbUser.get(0);

//		userForgottenPassword.setMail(first.getMail());
//		userForgottenPassword.setKennwort(first.getKennwort());

		//ds.save(userForgottenPassword);

		Query<User> updateQuery = ds.createQuery(User.class).filter("username =", user.getUsername());
			
		UpdateOperations<User> ops;
		ops = ds.createUpdateOperations(User.class).set("forgottenPassword", "true");
		
		ds.update(updateQuery, ops);
		// TODO: Daten aus DB löschen
		// ds.delete(dbUser.get(0));

		// success

	}

	@Override
	public void checkAdmin(User admin) throws DelistedException {
		username = (String) admin.getUsername().trim();
		kennwort = (String) admin.getKennwort().trim();

		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");

		List<User> dbUser = ds.createQuery(User.class)
				.filter("username =", username).asList();
		User first = dbUser.get(0);

		String dbKennwort = (String) first.getKennwort();

		if (kennwort.equals(dbKennwort)) {
			// success
		} else {
			throw new DelistedException("ERR");
		}

	}
}
