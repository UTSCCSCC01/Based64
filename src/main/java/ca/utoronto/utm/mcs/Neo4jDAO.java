package ca.utoronto.utm.mcs;
import javax.inject.Inject;
import org.neo4j.driver.Record;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONObject;
import org.neo4j.driver.*;
import java.io.IOException;
import org.json.JSONException;
import java.util.Collections;

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
                String query = "CREATE (m: Movie {name: '%s', movieId:'%s'})".formatted(name, movieId);
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
	            String query = "CREATE (m: Actor {name: '%s', actorId:'%s'})".formatted(name, actorId);
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
                else if (checkRelation(movieId, actorId)){
                    return 400;
                }else{
                    String query = "MATCH (a: Actor), (m: Movie) WHERE a.actorId = '%s' AND m.movieId = '%s' CREATE (a)-[:ACTED_IN]->(m)".formatted(actorId, movieId);
                    tx.run(query);
                    tx.commit();
                    return 200;
                }

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
	                query = "MATCH (a: Actor) WHERE a.actorId = '%s' RETURN a".formatted(id);
	            }else{
	                query = "MATCH (m: Movie) WHERE m.movieId = '%s' RETURN m".formatted(id);
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
    private Boolean checkRelation(String movieId, String actorId) {
        try(Session session = driver.session()){
            try(Transaction tx = session.beginTransaction()){
                String query = "RETURN EXISTS((:Actor{actorId:'%s'})-[:ACTED_IN]-(:Movie{movieId:'%s'}))".formatted(actorId, movieId);
                Result result = tx.run(query);
                Value val1 = result.next().values().get(0);
                Boolean val = val1.asBoolean();
                tx.commit();
                return val;
            }
            catch(Exception e1){
                e1.printStackTrace();
                return false;
            }
        }
    }

    // TODO (CRUD operations, where the following function is an example of the format):
    public String getActor(String reqActorId) {
    	String query;
    	query = "MATCH (a {actorId:\"%s\"})-[ai:ACTED_IN]->(m) RETURN a.actorId, a.name, collect(m.movieId);";
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
        query = "MATCH (m {movieId:\"%s\"})<-[ai:ACTED_IN]-(a) RETURN m.movieId, m.name, collect(a.actorId);";
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
    public String computeBaconNumber(String reqActorId) throws JSONException {
    	String query1;
        query1 = "MATCH (a {name: 'Kevin Bacon'}) RETURN a.actorId;";
        Result result1 = this.session.run(query1);
        List<JSONObject> resultAsJsonObjects1 = new ArrayList<JSONObject>();
        Record record1;
        JSONObject recordJson1;
        while (result1.hasNext()) {
            record1 = result1.next();
            recordJson1 = new JSONObject(record1.asMap());
            resultAsJsonObjects1.add(recordJson1);
        }    
        JSONObject kbIdJson = resultAsJsonObjects1.get(0);
        String kbIdJsonVal = kbIdJson.getString("a.actorId");
        if (kbIdJsonVal.equals(reqActorId)) {
        	JSONObject j = new JSONObject();
        	j.put("length(p)/2", "0");
        	return j.toString();
        } else {
        	String query2;
            query2 = "MATCH (a1:Actor {name: 'Kevin Bacon'}), (a2:Actor {actorId: \"%s\"}), p = shortestPath((a1)-[:ACTED_IN*]-(a2)) RETURN length(p)/2";
            query2 = String.format(query2, reqActorId);
            Result result2 = this.session.run(query2);
            List<String> resultAsJsonStrings2 = new ArrayList<String>();
            Record record2;
            JSONObject recordJson2;
            String recordJsonString2;
            while (result2.hasNext()) {
                record2 = result2.next();
                recordJson2 = new JSONObject(record2.asMap());
                recordJsonString2 = recordJson2.toString();
                resultAsJsonStrings2.add(recordJsonString2);
            }
            return resultAsJsonStrings2.get(0);
        }
    }
    public List<String> computeBaconPath(String reqActorId) throws JSONException {
    	String query1;
        query1 = "MATCH (a {name: 'Kevin Bacon'}) RETURN a.actorId;";
        Result result1 = this.session.run(query1);
        List<JSONObject> resultAsJsonObjects1 = new ArrayList<JSONObject>();
        Record record1;
        JSONObject recordJson1;
        while (result1.hasNext()) {
            record1 = result1.next();
            recordJson1 = new JSONObject(record1.asMap());
            resultAsJsonObjects1.add(recordJson1);
        }
        JSONObject kbIdJson = resultAsJsonObjects1.get(0);
        String kbIdJsonVal = kbIdJson.getString("a.actorId");
        if (kbIdJsonVal.equals(reqActorId)) {
        	List<String> r1 = new ArrayList<String>();
        	r1.add(kbIdJsonVal);
        	return r1;
        } else {
	    	String query2;
	        query2 = "MATCH (a1:Actor {name: 'Kevin Bacon'}), (a2:Actor {actorId: \"%s\"}), p = shortestPath((a1)-[:ACTED_IN*]-(a2)) RETURN nodes(p)";
	        query2 = String.format(query2, reqActorId);
	        Result result2 = this.session.run(query2);
	        List<Record> resultAsRecords2 = new ArrayList<Record>();
	        Record record2;
	        while (result2.hasNext()) {
	            record2 = result2.next();
	            resultAsRecords2.add(record2);
	        }
	        Record shortestPathRecord2 = resultAsRecords2.get(0);
	        List<Value> shortestPathValueAsListInList2 = shortestPathRecord2.values();
	        Value shortestPathValueAsList2 = shortestPathValueAsListInList2.get(0);
	        List<String> r2 = new ArrayList<String>();
	        for (int i = 0; i < shortestPathValueAsList2.size(); i++) {
	        	if ((i % 2) == 0) {
	        		r2.add(shortestPathValueAsList2.get(i).get("actorId").toString());
	        	} else {
	        		r2.add(shortestPathValueAsList2.get(i).get("movieId").toString());
	        	}
	        }
	        Collections.reverse(r2);
	        return r2;
    	}



    }
    
    
    
}


