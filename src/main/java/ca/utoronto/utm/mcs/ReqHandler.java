package ca.utoronto.utm.mcs;
import com.sun.net.httpserver.HttpHandler;
import javax.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.OutputStream;
import java.util.List;
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
            if (uriParts.length == 4 && uriParts[0].equals("") && uriParts[1].equals("api") && uriParts[2].equals("v1")) {
	            switch (exchange.getRequestMethod()) {
	                case "GET":
                    	if (uriParts[3].equals("getActor")) {
                    		this.getActor(exchange);
                    		return;
                    	} else if (uriParts[3].equals("getMovie")) {
                    		this.getMovie(exchange);
                    		return;
                    	} else if (uriParts[3].equals("hasRelationship")) {
                    		this.hasRelationship(exchange);
                    		return;
                    	} else if (uriParts[3].equals("computeBaconNumber")) {
                    		this.computeBaconNumber(exchange);
                    		return;
                    	} else if (uriParts[3].equals("computeBaconPath")) {
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
    /**
 	GET /api/v1/getActor
	@param none
	@param_body {"actorId": String}
	@resp_body {"actorId": String, "name": String, "movies": [String]}
	@return 200 OK (success), 400 BAD REQUEST (req body is improperly formatted OR missing req info), 404 NOT FOUND (when the actor DNE for the req), 500 INTERNAL SERVER ERROR [failure (i.e. Java exception thrown)]
	Check if an actor exists in the db.
	Edge case: return an empty list of movies for the response if the actor exists but didn't act in any movies (ex. {"actorId": "nm1001234", "name": "Sharlto Copley", "movies": []})
	Try: `curl -v -X GET -d '{"actorId": "1"}' http://localhost:8081/api/v1/getActor`
    */
    public void getActor(HttpExchange exchange) throws IOException, JSONException {
    	try {
    		// check for 400:
    		String reqBody = Utils.convert(exchange.getRequestBody());
    		JSONObject deserReqBody = new JSONObject(reqBody);
    		String reqActorId;
    		if (deserReqBody.length() == 1 && deserReqBody.has("actorId")) {
    			reqActorId = deserReqBody.getString("actorId");
    		} else {
                exchange.sendResponseHeaders(400, -1); // -1 indicates this response has no body
                return;
            }
    		// check for 200, 404:
    		try {
    			String queryResult = this.neodao.getActor(reqActorId);
    			JSONObject deserResBody = new JSONObject(queryResult);
        		String resMoviesStr;
        		if (deserResBody.length() == 3 && deserResBody.has("a.actorId") && deserResBody.has("a.name") && deserResBody.has("collect(m.name)")) {
        			resMoviesStr = deserResBody.getString("collect(m.name)");
                    exchange.sendResponseHeaders(200, resMoviesStr.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(resMoviesStr.getBytes());
                    os.close();
        		} else {
                    exchange.sendResponseHeaders(500, -1);
                    return;
                }
            } catch (Exception e) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }
        } catch (Exception e) {
        	exchange.sendResponseHeaders(500, -1);
            return;
        }
    }
    /**
	GET /api/v1/getMovie
	@param none
	@param_body {"movieId": String}
	@resp_body {"movieId": String, "name": String, "actors": [String]}
	@return 200 OK (success), 400 BAD REQUEST (req body is improperly formatted OR missing req info), 404 NOT FOUND (when the movie DNE for the req), 500 INTERNAL SERVER ERROR [failure (i.e. Java exception thrown)]
	Check if a movie exists in the db.
	Edge case: return an empty list of actors for the response if the movie exists but no one acted in it (ex. {"movieId": "nm1001234", "name": "Chappie", "actors": []})
	Try: `curl -v -X GET -d '{"movieId": "3"}' http://localhost:8081/api/v1/getMovie`
    */
    public void getMovie(HttpExchange exchange) throws IOException, JSONException {
    	try {
    		// check for 400:
    		String reqBody = Utils.convert(exchange.getRequestBody());
    		JSONObject deserReqBody = new JSONObject(reqBody);
    		String reqMovieId;
    		if (deserReqBody.length() == 1 && deserReqBody.has("movieId")) {
    			reqMovieId = deserReqBody.getString("movieId");
    		} else {
                exchange.sendResponseHeaders(400, -1); // -1 indicates this response has no body
                return;
            }
    		// check for 200, 404:
    		try {
    			String queryResult = this.neodao.getMovie(reqMovieId);
    			JSONObject deserResBody = new JSONObject(queryResult);
        		String resActorsStr;
        		if (deserResBody.length() == 3 && deserResBody.has("m.movieId") && deserResBody.has("m.name") && deserResBody.has("collect(a.name)")) {
        			resActorsStr = deserResBody.getString("collect(a.name)");
                    exchange.sendResponseHeaders(200, resActorsStr.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(resActorsStr.getBytes());
                    os.close();
        		} else {
                    exchange.sendResponseHeaders(500, -1);
                    return;
                }
            } catch (Exception e) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }
        } catch (Exception e) {
        	exchange.sendResponseHeaders(500, -1);
            return;
        }
    }
    /**
	GET /api/v1/hasRelationship
	@param none
	@param_body {"movieId": String, "actorId": String}
	@resp_body {"movieId": String, "actorId": String, "hasRelationship": Boolean}
	@return 200 OK (success), 400 BAD REQUEST (req body is improperly formatted OR missing req info), 404 NOT FOUND (when the relationship DNE for the req), 500 INTERNAL SERVER ERROR [failure (i.e. Java exception thrown)]
	Check if there exists a relationship between an actor and a movie.
	Edge case: none
	Try: `curl -v -X GET -d '{"movieId": "1", "actorId": "1"}' http://localhost:8081/api/v1/hasRelationship`
	Try: `curl -v -X GET -d '{"movieId": "3", "actorId": "1"}' http://localhost:8081/api/v1/hasRelationship`
    */
	public void hasRelationship(HttpExchange exchange) throws IOException, JSONException {
		try {
    		// check for 400:
    		String reqBody = Utils.convert(exchange.getRequestBody());
    		JSONObject deserReqBody = new JSONObject(reqBody);
    		String reqMovieId, reqActorId;
    		if (deserReqBody.length() == 2 && deserReqBody.has("movieId") && deserReqBody.has("actorId")) {
    			reqMovieId = deserReqBody.getString("movieId");
    			reqActorId = deserReqBody.getString("actorId");
    		} else {
                exchange.sendResponseHeaders(400, -1); // -1 indicates this response has no body
                return;
            }
    		// check for 200, 404:
    		try {
    			String queryResult = this.neodao.hasRelationship(reqMovieId, reqActorId);
    			JSONObject deserResBody = new JSONObject(queryResult);
        		String resHasRelationshipStr;
        		if (deserResBody.length() == 3 && deserResBody.has("m.movieId") && deserResBody.has("a.actorId") && deserResBody.has("EXISTS((m)<-[:ACTED_IN]-(a))")) {
        			resHasRelationshipStr = deserResBody.getString("EXISTS((m)<-[:ACTED_IN]-(a))");
                    exchange.sendResponseHeaders(200, resHasRelationshipStr.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(resHasRelationshipStr.getBytes());
                    os.close();
        		} else {
                    exchange.sendResponseHeaders(500, -1);
                    return;
                }
            } catch (Exception e) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }
        } catch (Exception e) {
        	exchange.sendResponseHeaders(500, -1);
            return;
        }
	}
    /**
	GET /api/v1/computeBaconNumber
	@param none
	@param_body {"actorId": String}
	@resp_body {"baconNumber": Integer}
	@return 200 OK (success), 400 BAD REQUEST (req body is improperly formatted OR missing req info), 404 NOT FOUND (when the actor DNE for the req OR there DNE a path from them to Kevin Bacon), 500 INTERNAL SERVER ERROR [failure (i.e. Java exception thrown)]
	Check the bacon number of an actor. Note that Kevin Bacon has a bacon number of 0.
	Edge case: none
	Try: `curl -v -X GET -d '{"actorId": "1"}' http://localhost:8081/api/v1/computeBaconNumber`
    */
	public void computeBaconNumber(HttpExchange exchange) throws IOException, JSONException {
		try {
    		// check for 400:
    		String reqBody = Utils.convert(exchange.getRequestBody());
    		JSONObject deserReqBody = new JSONObject(reqBody);
    		String reqActorId;
    		if (deserReqBody.length() == 1 && deserReqBody.has("actorId")) {
    			reqActorId = deserReqBody.getString("actorId");
    		} else {
                exchange.sendResponseHeaders(400, -1); // -1 indicates this response has no body
                return;
            }
    		// check for 200, 404:
    		try {
    			String queryResult = this.neodao.computeBaconNumber(reqActorId);
    			JSONObject deserResBody = new JSONObject(queryResult);
        		String baconNumberStr;
        		if (deserResBody.length() == 1 && deserResBody.has("length(p)/2")) {
        			baconNumberStr = deserResBody.getString("length(p)/2");
                    exchange.sendResponseHeaders(200, baconNumberStr.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(baconNumberStr.getBytes());
                    os.close();
        		} else {
                    exchange.sendResponseHeaders(500, -1);
                    return;
                }
            } catch (Exception e) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }
        } catch (Exception e) {
        	exchange.sendResponseHeaders(500, -1);
            return;
        }
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
		try {
    		// check for 400:
    		String reqBody = Utils.convert(exchange.getRequestBody());
    		JSONObject deserReqBody = new JSONObject(reqBody);
    		String reqActorId;
    		if (deserReqBody.length() == 1 && deserReqBody.has("actorId")) {
    			reqActorId = deserReqBody.getString("actorId");
    		} else {
                exchange.sendResponseHeaders(400, -1); // -1 indicates this response has no body
                return;
            }
    		// check for 200, 404:
    		try {
    			List<String> queryResult = this.neodao.computeBaconPath(reqActorId);
    			String queryResultStr = queryResult.toString();
    			JSONArray r = new JSONArray(queryResult);
    			JSONObject o = new JSONObject();
    			o.put("baconPath", r);
                exchange.sendResponseHeaders(200, queryResultStr.length());
                OutputStream os = exchange.getResponseBody();
                os.write(queryResultStr.getBytes());
                os.close();
            } catch (Exception e) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }
        } catch (Exception e) {
        	exchange.sendResponseHeaders(500, -1);
            return;
        }
	}
	
	
	
}


