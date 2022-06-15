package ca.utoronto.utm.mcs;
import dagger.Module;
import dagger.Provides;
@Module
public class ServerModule {	
	@Provides
	public Config provideConfig() {
		return new Config(8080, "0.0.0.0", 0, "/api");
	}
	@Provides
	public ReqHandler provideReqHandler() {
		//return new ReqHandler();
		ReqHandlerComponent component = DaggerReqHandlerComponent.create();
		ReqHandler rh = component.buildHandler();
	    return rh;
	}
}


