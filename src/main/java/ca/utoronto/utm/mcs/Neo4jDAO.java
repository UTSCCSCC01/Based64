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
	/*
	private final Session session;
    private final Driver driver;
    public Neo4jDAO(String uriDb, String username, String password) {
        this.driver = GraphDatabase.driver(uriDb, AuthTokens.basic(username, password));
        this.session = this.driver.session();
    }
    */
	private final Driver driver;
	private final Session session;
    @Inject
    public Neo4jDAO(Driver driver) {
        this.driver = driver;
        this.session = this.driver.session();
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


