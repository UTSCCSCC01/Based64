package ca.utoronto.utm.mcs;
import dagger.Module;
import dagger.Provides;
@Module
public class ReqHandlerModule {
	@Provides
	public Neo4jDAO provideNeo4jDAO() {
		return new Neo4jDAO("bolt://localhost:7687", "neo4j", "123456");
	}
}


