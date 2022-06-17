package ca.utoronto.utm.mcs;
import com.sun.net.httpserver.HttpServer;
import javax.inject.Inject;
public class Server {
	/*
	private Config config;
	private ReqHandler reqHandler;
	private HttpServer server;
	@Inject
	public Server(Config config, ReqHandler reqHandler) {
		this.config = config;
		this.reqHandler = reqHandler;
		this.server = null;
	}
	public void setupServer() {
		try {
			this.server = HttpServer.create(new InetSocketAddress(this.config.hostname, this.config.port), this.config.backlog);
			this.server.createContext(this.config.contextPath, this.reqHandler);
			this.server.start();
			System.out.printf("Server started on port %d\n", this.config.port);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	*/
	private HttpServer server;
	@Inject
	public Server(HttpServer server) {
		this.server = server;
	}
	public void setupServer() {
		this.server.start();
		System.out.printf("Server started on port %d\n", this.server.getAddress().getPort());
	}
}


