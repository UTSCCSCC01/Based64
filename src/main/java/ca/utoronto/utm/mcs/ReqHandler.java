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
            switch (exchange.getRequestMethod()) {
                case "GET":
                    this.handleGet(exchange);
                    break;
                case "POST":
                    this.handlePost(exchange);
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
    public void handlePost(HttpExchange exchange) throws IOException, JSONException {
    	// TODO
    }
    
}


