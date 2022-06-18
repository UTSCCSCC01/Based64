package ca.utoronto.utm.mcs;
import org.neo4j.driver.*;
import javax.inject.Inject;
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
    @Inject
    public Neo4jDAO(Driver driver) {
        this.driver = driver;
    }


}


