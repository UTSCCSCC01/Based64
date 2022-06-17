package ca.utoronto.utm.mcs;
import com.sun.net.httpserver.HttpHandler;
import javax.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import org.json.JSONException;
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
    		String uri = exchange.getRequestURI().toString();
            String[] uriParts = uri.split("/");
            if (uriParts.length == 3 && uriParts[0] == "api" && uriParts[1] == "v1") {
	            switch (exchange.getRequestMethod()) {
	                case "GET":
                    	if (uriParts[2] == "getActor") {
                    		this.getActor(exchange);
                    		return;
                    	} else if (uriParts[2] == "getMovie") {
                    		this.getMovie(exchange);
                    		return;
                    	} else if (uriParts[2] == "hasRelationship") {
                    		this.hasRelationship(exchange);
                    		return;
                    	} else if (uriParts[2] == "computeBaconNumber") {
                    		this.computeBaconNumber(exchange);
                    		return;
                    	} else if (uriParts[2] == "computeBaconPath") {
                    		this.computeBaconPath(exchange);
                    		return;
                    	}
	                case "POST":
	                    //this.handlePost(exchange);
	                default:
	                	exchange.sendResponseHeaders(500, -1);
	                    return;
	            }
            } else {
            	exchange.sendResponseHeaders(500, -1);
                return;
            }
        } catch (Exception e) {
        	exchange.sendResponseHeaders(500, -1);
            e.printStackTrace();
        }
    }
    /** Add Movie
     * This endpoint is to add a movie node into the database
     * @param movieId
     * @param name
     * @return  - 200 OK - For a successful add
                - 400 BAD REQUEST - If the request body is improperly formatted or 
                  missing required information
                - 500 INTERNAL SERVER ERROR - If save or add was unsuccessful 
                  (Java Exception Thrown)
     */
    public int addMovie(String movieId, String name){
        // try () {
            // if ()
                // return 400 on fail
            // else (pass)
                // return 200 on pass
        // }
        // catch(Exception e){
            // catch error
                // return 500 on INTERNAL SERVER ERROR
        // }
        return 1;
    }
    /**
 	GET /api/v1/getActor
	@param none
	@param_body {"actorId": String}
	@resp_body {"actorId": String, "name": String, "movies": [String]}
	@return 200 OK (success), 400 BAD REQUEST (req body is improperly formatted OR missing req info), 404 NOT FOUND (when the actor DNE for the req), 500 INTERNAL SERVER ERROR [failure (i.e. Java exception thrown)]
	Check if an actor exists in the db.
	Edge case: return an empty list of movies for the response if the actor exists but didn't act in any movies (ex. {"actorId": "nm1001234", "name": "Sharlto Copley", "movies": []})
    */
    public void getActor(HttpExchange exchange) throws IOException, JSONException {
    	// TODO
    	// ex. this.neodao.getActor(@param_body) // where cypher query is done
    }
    /**
	GET /api/v1/getMovie
	@param none
	@param_body {"movieId": String}
	@resp_body {"movieId": String, "name": String, "actors": [String]}
	@return 200 OK (success), 400 BAD REQUEST (req body is improperly formatted OR missing req info), 404 NOT FOUND (when the movie DNE for the req), 500 INTERNAL SERVER ERROR [failure (i.e. Java exception thrown)]
	Check if a movie exists in the db.
	Edge case: return an empty list of actors for the response if the movie exists but no one acted in it (ex. {"movieId": "nm1001234", "name": "Chappie", "actors": []})
    */
    public void getMovie(HttpExchange exchange) throws IOException, JSONException {
    	// TODO
    }
    /**
	GET /api/v1/hasRelationship
	@param none
	@param_body {"movieId": String, "actorId": String}
	@resp_body {"movieId": String, "actorId": String, "hasRelationship": Boolean}
	@return 200 OK (success), 400 BAD REQUEST (req body is improperly formatted OR missing req info), 404 NOT FOUND (when the relationship DNE for the req), 500 INTERNAL SERVER ERROR [failure (i.e. Java exception thrown)]
	Check if there exists a relationship between an actor and a movie.
	Edge case: none
    */
	public void hasRelationship(HttpExchange exchange) throws IOException, JSONException {
		// TODO
	}
    /**
	GET /api/v1/computeBaconNumber
	@param none
	@param_body {"actorId": String}
	@resp_body {"baconNumber": Integer}
	@return 200 OK (success), 400 BAD REQUEST (req body is improperly formatted OR missing req info), 404 NOT FOUND (when the actor DNE for the req OR there DNE a path from them to Kevin Bacon), 500 INTERNAL SERVER ERROR [failure (i.e. Java exception thrown)]
	Check the bacon number of an actor. Note that Kevin Bacon has a bacon number of 0.
	Edge case: none
    */
	public void computeBaconNumber(HttpExchange exchange) throws IOException, JSONException {
		// TODO
	}
    /**
	GET /api/v1/computeBaconPath
	@param none
	@param_body {"actorId": String}
	@resp_body {"baconPath": ["actorId": String, "movieId": String, "actorId": String, "movieId": String, ...]} (i.e. the "baconPath" is a list of interchanging actors and movies beginning with the inputted "actorId" and ending with Kevin Bacon's "actorId")
	@return 200 OK (success), 400 BAD REQUEST (req body is improperly formatted OR missing req info), 404 NOT FOUND (when the actor DNE for the req OR there DNE a path from them to Kevin Bacon), 500 INTERNAL SERVER ERROR [failure (i.e. Java exception thrown)]
	Return the shortest bacon path in order from the actor given to Kevin Bacon.
	Edge cases:
	1) for an actor that's acted in a movie but doesn't have a path to Kevin Bacon, a 404 NOT FOUND should be returned
	2) for an actor with multiple baconPaths with the same baconNumbers, just return 1 of the baconPaths
	3) Kevin Bacon's baconPath should be a list with just his actorId in it
    */
	public void computeBaconPath(HttpExchange exchange) throws IOException, JSONException {
		// TODO
	}
	
	
	
}


