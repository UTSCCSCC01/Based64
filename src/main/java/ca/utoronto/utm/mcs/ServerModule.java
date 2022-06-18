package ca.utoronto.utm.mcs;
import dagger.Module;
import dagger.Provides;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;
@Module
public class ServerModule {
	/*
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
	*/
	@Provides
	public HttpServer provideServer() {
		final int port = 8080;
		final String hostname = "0.0.0.0";
		final int backlog = 0;
		final String contextPath = "/api";
		// ReqHandlerComponent reqHandlerComponent = DaggerReqHandlerComponent.create();
		ReqHandler reqHandler = reqHandlerComponent.buildHandler();
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(hostname, port), backlog);
			server.createContext(contextPath, reqHandler);
			return server;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
// module for client like components


