package ca.utoronto.utm.mcs;
import javax.inject.Inject;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.util.List;
import java.util.Map;
import java.io.OutputStream;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.neo4j.driver.util.Pair;
import org.neo4j.driver.*;
import java.io.IOException;
import org.json.JSONException;
// NOTE: all your db transactions or queries should go in this class
public class Neo4jDAO {
    private final Driver driver;
	private final Session session;
    @Inject
    public Neo4jDAO(Driver driver) {
        this.driver = driver;
        this.session = this.driver.session();
    }

    // _______________________________________________
    // Put Requests
    // _______________________________________________

    /** Add Movie
     * This endpoint is to add a movie node into the database
     * @param movieId
     * @param name
     * @return  - 200 OK - For a successful add
                - 400 BAD REQUEST - If the request body is improperly formatted or 
                  missing required information
                - 500 INTERNAL SERVER ERROR - If save or add was unsuccessful 
                  (Java Exception Thrown)
     */
    public int addMovie(String name, String movieId){

        try (Session session = this.driver.session()) {
            try(Transaction tx = session.beginTransaction()){
                if (checkDatabase(movieId, 1 ) == 0){
                    return 400;
                }
                String query = "CREATE (m: Movie {name: '%s', id:'%s'})".formatted(name, movieId);
                tx.run(query);
                tx.commit();
                return 200;
                
            }catch(Exception e1){
                e1.printStackTrace();
                return 500;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return 500;
        }
	}
	/** Add Actor
	 * This endpoint is to add an actor node into the database
	 * @param name
	 * @param actorId
	 * @return  - 200 OK - For a successful add
	            - 400 BAD REQUEST - If the request body is improperly formatted or 
	              missing required information
	            - 500 INTERNAL SERVER ERROR - If save or add was unsuccessful 
	              (Java Exception Thrown)
	 */
	public int addActor(String name, String actorId){
	
	    try (Session session = this.driver.session()) {
	        try(Transaction tx = session.beginTransaction()){
	            if (checkDatabase(actorId, 0 ) == 0){
	                return 400;
	            }
	            String query = "CREATE (m: Actor {name: '%s', id:'%s'})".formatted(name, actorId);
	            tx.run(query);
	            tx.commit();
	            return 200;
	            
	        }catch(Exception e1){
	            e1.printStackTrace();
	            return 500;
	        }
	    }
	    catch(Exception e){
	        e.printStackTrace();
	        return 500;
	    }
	}
	
	/** Add Relationship
	 * This endpoint is to add an ACTED_IN relationship between an actor 
	 * and a movie in the database
	 * @param actorId
	 * @param movieId
	 * @return  - 200 OK - For a successful add
	            - 400 BAD REQUEST - If the request body is improperly formatted or 
	              missing required information
	            - 404 NOT FOUND - If the actor or movie does not exist when adding the 
	              relationship
	            - 500 INTERNAL SERVER ERROR - If save or add was unsuccessful 
	              (Java Exception Thrown)
	 */
	// TODO relationship already exists return 400
	public int addRelationship(String actorId, String movieId){
	
	    try (Session session = this.driver.session()){
	        try(Transaction tx = session.beginTransaction()){
	            if (checkDatabase(actorId, 0 ) == 1 || checkDatabase(movieId, 1 ) == 1){
	                return 404;
	            }
//                if (hasRelationship(movieId, actorId)){
//
//                }
	            String query = "MATCH (a: Actor), (m: Movie) WHERE a.id = '%s' AND m.id = '%s' CREATE (a)-[:ACTED_IN]->(m)".formatted(actorId, movieId);
                tx.run(query);
	            tx.commit();
	            return 200;
	            
	        }catch(Exception e1){
	            e1.printStackTrace();
	            return 500;
	        }
	    }
	    catch(Exception e){
	        e.printStackTrace();
	        return 500;
	    }
	
	}
	// ________________________________________________ 
	// Put Helper functions
	// ________________________________________________ 
	private int checkDatabase(String id, int actorOrMovie ){
	    // Actor => actorOrMovie = 0
	    // Movie => actorOrMovie = 1
	    try(Session session = driver.session()){
	        try(Transaction tx = session.beginTransaction()){
	            String query;
	            if (actorOrMovie == 0){
	                query = "MATCH (a: Actor) WHERE a.id = '%s' RETURN a".formatted(id);
	            }else{
	                query = "MATCH (m: Movie) WHERE m.id = '%s' RETURN m".formatted(id);
	            }
	            boolean x = tx.run(query).hasNext();
	            tx.commit();
	
	            if (x)
	                return 0;   // if exists
	            return 1;       // if DNE
	            
	        }
	        catch(Exception e1){
	            e1.printStackTrace();
	            return 500;
	        }
	
	    }
	    catch(Exception e){
	        e.printStackTrace();
	        return 500;
	    }
	
	}
    
    // TODO (CRUD operations, where the following function is an example of the format):
    public String getActor(String reqActorId) {
    	String query;
    	query = "MATCH (a {actorId:\"%s\"})-[ai:ACTED_IN]->(m) RETURN a.actorId, a.name, collect(m.name);";
        query = String.format(query, reqActorId);
        Result result = this.session.run(query);
        List<String> resultAsJsonStrings = new ArrayList<String>();
        Record record;
        JSONObject recordJson;
        String recordJsonString;
        while (result.hasNext()) {
            record = result.next();
            recordJson = new JSONObject(record.asMap());
            recordJsonString = recordJson.toString();
            resultAsJsonStrings.add(recordJsonString);
        }
        return resultAsJsonStrings.get(0);
    }
    public String getMovie(String reqMovieId) {
    	String query;
        query = "MATCH (m {movieId:\"%s\"})<-[ai:ACTED_IN]-(a) RETURN m.movieId, m.name, collect(a.name);";
        query = String.format(query, reqMovieId);
        Result result = this.session.run(query);
        List<String> resultAsJsonStrings = new ArrayList<String>();
        Record record;
        JSONObject recordJson;
        String recordJsonString;
        while (result.hasNext()) {
            record = result.next();
            recordJson = new JSONObject(record.asMap());
            recordJsonString = recordJson.toString();
            resultAsJsonStrings.add(recordJsonString);
        }    
        return resultAsJsonStrings.get(0);
    }
    public String hasRelationship(String reqMovieId, String reqActorId) {
    	String query;
        query = "MATCH (m {movieId: \"%s\"}), (a {actorId: \"%s\"}) RETURN m.movieId, a.actorId, EXISTS((m)<-[:ACTED_IN]-(a));";
        query = String.format(query, reqMovieId, reqActorId);
        Result result = this.session.run(query);
        List<String> resultAsJsonStrings = new ArrayList<String>();
        Record record;
        JSONObject recordJson;
        String recordJsonString;
        while (result.hasNext()) {
            record = result.next();
            recordJson = new JSONObject(record.asMap());
            recordJsonString = recordJson.toString();
            resultAsJsonStrings.add(recordJsonString);
        }    
        return resultAsJsonStrings.get(0);
    }
    public String computeBaconNumber(String reqActorId) {
    	String query;
        query = "MATCH (a1:Actor {name: 'Kevin Bacon'}), (a2:Actor {actorId: \"%s\"}), p = shortestPath((a1)-[:ACTED_IN*]-(a2)) RETURN length(p)/2";
        query = String.format(query, reqActorId);
        Result result = this.session.run(query);
        List<String> resultAsJsonStrings = new ArrayList<String>();
        Record record;
        JSONObject recordJson;
        String recordJsonString;
        while (result.hasNext()) {
            record = result.next();
            recordJson = new JSONObject(record.asMap());
            recordJsonString = recordJson.toString();
            resultAsJsonStrings.add(recordJsonString);
        }    
        return resultAsJsonStrings.get(0);
    }
    public List<String> computeBaconPath(String reqActorId) {
    	String query;
        query = "MATCH (a1:Actor {name: 'Kevin Bacon'}), (a2:Actor {actorId: \"%s\"}), p = shortestPath((a1)-[:ACTED_IN*]-(a2)) RETURN nodes(p)";
        query = String.format(query, reqActorId);
        Result result = this.session.run(query);
        List<Record> resultAsRecords = new ArrayList<Record>();
        Record record;
        while (result.hasNext()) {
            record = result.next();
            resultAsRecords.add(record);
        }
        Record shortestPathRecord = resultAsRecords.get(0);
        List<Value> shortestPathValueAsListInList = shortestPathRecord.values();
        Value shortestPathValueAsList = shortestPathValueAsListInList.get(0);
        List<String> r = new ArrayList<String>();
        for (int i = 0; i < shortestPathValueAsList.size(); i++) {
        	if ((i % 2) == 0) {
        		r.add(shortestPathValueAsList.get(i).get("actorId").toString());
        	} else {
        		r.add(shortestPathValueAsList.get(i).get("movieId").toString());
        	}
        }
        return r;
    }
    
    
    
}


