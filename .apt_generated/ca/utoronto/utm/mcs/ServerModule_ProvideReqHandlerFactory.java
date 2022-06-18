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
public final class ServerModule_ProvideReqHandlerFactory implements Factory<ReqHandler> {
  private final ServerModule module;

  public ServerModule_ProvideReqHandlerFactory(ServerModule module) {
    this.module = module;
  }

  @Override
  public ReqHandler get() {
    return provideReqHandler(module);
  }

  public static ServerModule_ProvideReqHandlerFactory create(ServerModule module) {
    return new ServerModule_ProvideReqHandlerFactory(module);
  }

  public static ReqHandler provideReqHandler(ServerModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideReqHandler());
  }
}
