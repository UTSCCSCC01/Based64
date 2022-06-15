package ca.utoronto.utm.mcs;
import org.neo4j.driver.*;
// NOTE: all your db transactions or queries should go in this class
public class Neo4jDAO {
	private final Session session;
    private final Driver driver;
    public Neo4jDAO(String uriDb, String username, String password) {
        this.driver = GraphDatabase.driver(uriDb, AuthTokens.basic(username, password));
        this.session = this.driver.session();
    }

    // TODO (CRUD operations, where the following function is an example of the format):
    public void insertItem(String name) {
        String query;
        query = "CREATE (n:pokemon {name: \"%s\"})";
        query = String.format(name);
        this.session.run(query);
        return;
    }
    
}


