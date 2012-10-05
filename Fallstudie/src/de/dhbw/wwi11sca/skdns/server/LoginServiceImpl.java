package de.dhbw.wwi11sca.skdns.server;


import java.net.UnknownHostException;
import java.util.List;

import de.dhbw.wwi11sca.skdns.client.DelistedException;
import de.dhbw.wwi11sca.skdns.client.login.LoginSimulation;
import de.dhbw.wwi11sca.skdns.client.login.LoginService;
import de.dhbw.wwi11sca.skdns.shared.User;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {
	
	boolean kontrolle = false;
	private String username;
	private String kennwort;
	private static final long serialVersionUID = -179774088492873807L;
	public boolean getKennDaten(){
		

//		
//		
		return true;
		
	}
	
	   @Override
	    public void checkLogin(User user) throws DelistedException {
	        // in die Datenbank schreiben
//		   if (){
		//	   throw new DelistedException("ERR");
//		   }
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
//			   
//			   
//			   if(username != dbUsername){
//				   throw new DelistedException("ERR"); //ToDo: No Username
//			   } else {
//				   if(kennwort!= dbKennwort){
//					   throw new DelistedException("ERR"); //ToDo Wrong Pwd
//				   }
//			   }
			   
			   
			   
			   
//				DB db = getMongo().getDB("skdns");
//				DBCollection collection = db.getCollection("login");
				
//				BasicDBObject searchQuery = new BasicDBObject();
//				searchQuery.put("name", username);
//				searchQuery.put("password", kennwort);
				
//				DBCursor cursor = collection.find(searchQuery);
				
				
//				BasicDBObject field = new BasicDBObject();
//				field = (BasicDBObject) cursor.
				
	
//				String dbusername = (String) field.get("name");
//				String dbkennwort = (String) field.get("password");
				
			
							
			
	    
	    }

	@Override
	public String loginServer(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
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

