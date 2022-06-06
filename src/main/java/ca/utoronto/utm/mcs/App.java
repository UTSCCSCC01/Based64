package ca.utoronto.utm.mcs;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class App
{
    static int PORT = 8080;

    public static void main(String[] args) throws IOException
    {
        // Creating Server Context Here (There Should Only Be One Context):
    	HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);
        server.createContext("/api", new ReqHandler());
        server.start();
        System.out.printf("Server started on port %d\n", PORT);

        
        
        // TODO (only 1 left for App.java):
        // This code is used to get the neo4j address, you must use this so that we can mark :)
        Dotenv dotenv = Dotenv.load();
        String addr = dotenv.get("NEO4J_ADDR");
        System.out.println(addr);
        
        
        
    }
}
