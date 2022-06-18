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
public final class ServerModule_ProvideConfigFactory implements Factory<Config> {
  private final ServerModule module;

  public ServerModule_ProvideConfigFactory(ServerModule module) {
    this.module = module;
  }

  @Override
  public Config get() {
    return provideConfig(module);
  }

  public static ServerModule_ProvideConfigFactory create(ServerModule module) {
    return new ServerModule_ProvideConfigFactory(module);
  }

  public static Config provideConfig(ServerModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideConfig());
  }
}
