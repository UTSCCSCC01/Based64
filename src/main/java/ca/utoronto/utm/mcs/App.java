package ca.utoronto.utm.mcs;
import io.github.cdimascio.dotenv.Dotenv;
public class App {
    public static void main(String[] args) {
    	ServerComponent component = DaggerServerComponent.create();
	    Server s = component.buildServer();
	    s.setupServer();
        // This code is used to get the neo4j address, you must use this so that we can mark :)
        Dotenv dotenv = Dotenv.load();
        String addr = dotenv.get("NEO4J_ADDR");
        System.out.println(addr);
    }
} 