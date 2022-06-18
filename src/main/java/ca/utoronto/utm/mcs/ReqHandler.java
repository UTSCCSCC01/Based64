package ca.utoronto.utm.mcs;
import com.sun.net.httpserver.HttpHandler;
import javax.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class ReqHandler implements HttpHandler {
	private Neo4jDAO neodao;
	
    @Inject
    public ReqHandler(Neo4jDAO neodao) {
        this.neodao = neodao;
    }
	
    // TODO (finish the class below to cover all cases):
    @Override
    public void handle(HttpExchange exchange) throws IOException {
    	try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    this.handleGet(exchange);
                    break;
                case "PUT":
                    this.handlePut(exchange);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // TODO (the following description + function is an example of the format):
    /**
     * GET /api/:pid
     * @param {String} pid     String representing a unique pokemon id
     * @return 200, 400, 404, 500
     * Returns a Pokemon with the specified pokemon id, if it exists.
     */
    public void handleGet(HttpExchange exchange) throws IOException, JSONException {
    	// TODO
    }
    public void handlePut(HttpExchange exchange) throws IOException, JSONException {
        try{
            // Add actor
            if (exchange.getRequestURI().toString().equals("/api/v1/addActor")){
                String body = Utils.convert(exchange.getRequestBody());
                try {
                    JSONObject deserialized = new JSONObject(body);
                    String name, actorId;

                    if(!deserialized.has("name") || !deserialized.has("actorId") ){
                        exchange.sendResponseHeaders(400, -1);
                    }else{
                        name = deserialized.getString("name");
                        actorId = deserialized.getString("name");
                        int rcode = neodao.addActor(name, actorId);
                        exchange.sendResponseHeaders(rcode, -1);
                    }

                }catch(JSONException e1){
                    exchange.sendResponseHeaders(400, -1);
                }


            }
            // Add movie
            else if (exchange.getRequestURI().toString().equals("/api/v1/addMovie")){
                String body = Utils.convert(exchange.getRequestBody());
                try {
                    JSONObject deserialized = new JSONObject(body);
                    String name, movieId;

                    if(!deserialized.has("name") || !deserialized.has("movieID") ){
                        exchange.sendResponseHeaders(400, -1);
                    }else{
                        name = deserialized.getString("name");
                        movieId = deserialized.getString("movieId");
                        int rcode = neodao.addMovie(name, movieId);
                        exchange.sendResponseHeaders(rcode, -1);
                    }

                }catch(JSONException e1){
                    exchange.sendResponseHeaders(400, -1);
                }

            }
            // Add Relationship
            else if(exchange.getRequestURI().toString().equals("/api/v1/addRelationship")){
                String body = Utils.convert(exchange.getRequestBody());
                try {
                    JSONObject deserialized = new JSONObject(body);
                    String actorId, movieId;

                    if(!deserialized.has("actorId") || !deserialized.has("movieID") ){
                        exchange.sendResponseHeaders(400, -1);
                    }else{
                        actorId = deserialized.getString("actorId");
                        movieId = deserialized.getString("movieId");
                        int rcode = neodao.addRelationship(actorId, movieId);
                        exchange.sendResponseHeaders(rcode, -1);
                    }

                }catch(JSONException e1){
                    exchange.sendResponseHeaders(400, -1);
                }
            }
            else{
                exchange.sendResponseHeaders(404, -1);
            }
        } catch (Exception e){
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
        }


    }
    
}


