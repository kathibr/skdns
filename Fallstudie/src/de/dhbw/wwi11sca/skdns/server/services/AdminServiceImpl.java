package de.dhbw.wwi11sca.skdns.server.services;

import java.net.UnknownHostException;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import de.dhbw.wwi11sca.skdns.client.admin.AdminService;
import de.dhbw.wwi11sca.skdns.shared.Admin;
import de.dhbw.wwi11sca.skdns.shared.User;

public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1889396499053755175L;
	private static Admin admin = new Admin();

	@Override
	public List<User> getUser() {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		List<User> dbUser = ds.createQuery(User.class).asList();
		return dbUser;
	} // Enge methode getUser

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
	} // Ende method getMongo

	@Override
	public void saveUser(User newUser) {
		// TODO User in DB speichern
		// User newUser aus AdminSimulation holen und in der db speichern
		// anschließend true zurückgeben, damit dem Admin angezeigt werden kann,
		// dass das Erzeugen erfolgreich war
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		ds.save(newUser);
		
	} // Ende method saveUser

	@Override
	public void deleteUser(String deleteUser) {
		// TODO Auto-generated method stub
		
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
//		Query<User> deleteQuery = ds.createQuery(User.class).filter("userID =", deleteUser);
		ds.delete(ds.createQuery(User.class).filter("username = ", deleteUser));

	} // Ende method deleteUser

	@Override
	public Admin getStats() {
		// TODO DB-User Inhalte zählen
		// alle in der Datenbank vorhandenen User (außer Admin) sollen gezählt
		// werden und in
		// admin.setExistingUserCount(existingUserCount) gespeichert werden
		// damit die Statistiken ausgegeben werden können
		return getAdmin();
	} // Ende method getStats

	public static Admin getAdmin() {
		return admin;
	} // Ende method getAdmin

	public void setAdmin(Admin admin) {
		AdminServiceImpl.admin = admin;
	} // Ende method setAdmin

	@Override
	public void updateTable(User user) {
		Datastore ds = new Morphia().createDatastore(getMongo(), "skdns");
		Query<User> updateQuery = ds.createQuery(User.class).field("userID").equal(user.getUserID());
		// ds.createQuery(EigenesUnternehmen.class);
		UpdateOperations<User> ops;
		ops = ds.createUpdateOperations(User.class)
				.set("password", user.getPassword())
				.set("forgottenPassword", user.isForgottenPassword());
								
		ds.update(updateQuery,ops);
	}

} // Ende class AdminServiceImpl
