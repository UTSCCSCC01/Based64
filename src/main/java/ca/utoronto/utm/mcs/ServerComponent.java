package ca.utoronto.utm.mcs;
import javax.inject.Singleton;
import dagger.Component;
@Singleton
@Component(modules = ServerModule.class)
public interface ServerComponent {
	public Server buildServer();
}


