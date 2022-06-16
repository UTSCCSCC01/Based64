package ca.utoronto.utm.mcs;

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
  private final Provider<Config> configProvider;

  private final Provider<ReqHandler> reqHandlerProvider;

  public Server_Factory(Provider<Config> configProvider, Provider<ReqHandler> reqHandlerProvider) {
    this.configProvider = configProvider;
    this.reqHandlerProvider = reqHandlerProvider;
  }

  @Override
  public Server get() {
    return newInstance(configProvider.get(), reqHandlerProvider.get());
  }

  public static Server_Factory create(Provider<Config> configProvider,
      Provider<ReqHandler> reqHandlerProvider) {
    return new Server_Factory(configProvider, reqHandlerProvider);
  }

  public static Server newInstance(Config config, ReqHandler reqHandler) {
    return new Server(config, reqHandler);
  }
}
