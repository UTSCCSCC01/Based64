package ca.utoronto.utm.mcs;
public class Config {
	public int port;
	public String hostname;
	public int backlog;
	public String contextPath;
    public Config(int port, String hostname, int backlog, String contextPath) {
    	this.port = port;
    	this.hostname = hostname;
    	this.backlog = backlog;
    	this.contextPath = contextPath;
    }
}


