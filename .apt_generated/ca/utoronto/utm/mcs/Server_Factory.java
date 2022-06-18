package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpServer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class Server_Factory implements Factory<Server> {
  private final Provider<HttpServer> serverProvider;

  public Server_Factory(Provider<HttpServer> serverProvider) {
    this.serverProvider = serverProvider;
  }

  @Override
  public Server get() {
    return newInstance(serverProvider.get());
  }

  public static Server_Factory create(Provider<HttpServer> serverProvider) {
    return new Server_Factory(serverProvider);
  }

  public static Server newInstance(HttpServer server) {
    return new Server(server);
  }
}
