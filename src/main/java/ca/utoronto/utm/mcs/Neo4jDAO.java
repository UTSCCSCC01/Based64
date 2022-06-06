package ca.utoronto.utm.mcs;

import org.neo4j.driver.*;

// All your database transactions or queries should 
// go in this class
public class Neo4jDAO {

	private final Session session;
    private final Driver driver;

    private final String uriDb = "bolt://localhost:7687";
    private final String username = "neo4j";
    private final String password = "123456";
	
    public Neo4jDAO() {
        this.driver = GraphDatabase.driver(this.uriDb, AuthTokens.basic(this.username, this.password));
        this.session = this.driver.session();
    }
    
    // TODO (the following function is an example of the format):
    
    public void insertItem(String name) {
        String query;
        query = "CREATE (n:pokemon {name: \"%s\"})";
        query = String.format(name);
        this.session.run(query);
        return;
    }
	
}
