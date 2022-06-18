package ca.utoronto.utm.mcs;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ReqHandlerModule_ProvideNeo4jDAOFactory implements Factory<Neo4jDAO> {
  private final ReqHandlerModule module;

  public ReqHandlerModule_ProvideNeo4jDAOFactory(ReqHandlerModule module) {
    this.module = module;
  }

  @Override
  public Neo4jDAO get() {
    return provideNeo4jDAO(module);
  }

  public static ReqHandlerModule_ProvideNeo4jDAOFactory create(ReqHandlerModule module) {
    return new ReqHandlerModule_ProvideNeo4jDAOFactory(module);
  }

  public static Neo4jDAO provideNeo4jDAO(ReqHandlerModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideNeo4jDAO());
  }
}
