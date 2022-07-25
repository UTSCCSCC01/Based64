package ca.utoronto.utm.mcs;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.AuthTokens;
import dagger.Module;
import dagger.Provides;
@Module
public class ReqHandlerModule {
	Dotenv dotenv = Dotenv.load();
	String addr = dotenv.get("NEO4J_ADDR");
	String uriDb = "bolt://" + addr + ":7687";
	
// 	private String uriDb = "bolt://localhost:7687";
	private String username = "neo4j";
	private String password = "123456";
	@Provides
	public Neo4jDAO provideNeo4jDAO() {
		return new Neo4jDAO(GraphDatabase.driver(uriDb, AuthTokens.basic(username, password)));
	}
	/*
	@Provides
	public Driver provideDriver() {
		return GraphDatabase.driver(uriDb, AuthTokens.basic(username, password));
	}
	*/
}
// module for server like components


