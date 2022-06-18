package ca.utoronto.utm.mcs;
import javax.inject.Inject;
import org.neo4j.driver.Record;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;
import org.neo4j.driver.util.Pair;
import org.neo4j.driver.*;
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
    	// TODO:
    	String query;
        query = "...;"; // TODO: the query should check if the given actor acted in the given movie (node label should be r)
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
    
    
    
}


