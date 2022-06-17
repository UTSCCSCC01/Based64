package ca.utoronto.utm.mcs;
import org.neo4j.driver.*;
// NOTE: all your db transactions or queries should go in this class
public class Neo4jDAO {
	private final Session session;
    private final Driver driver;

    public Neo4jDAO(String uriDb, String username, String password) {
        this.driver = GraphDatabase.driver(uriDb, AuthTokens.basic(username, password));
        this.session = this.driver.session();
    }

    // TODO (CRUD operations, where the following function is an example of the format):
    public void insertItem(String name) {
        String query;
        query = "CREATE (n:pokemon {name: \"%s\"})";
        query = String.format(name);
        this.session.run(query);
        return;
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
    
    /** Add Actor
     * This endpoint is to add an actor node into the database
     * @param name
     * @param actorId
     * @return  - 200 OK - For a successful add
                - 400 BAD REQUEST - If the request body is improperly formatted or 
                  missing required information
                - 500 INTERNAL SERVER ERROR - If save or add was unsuccessful 
                  (Java Exception Thrown)
     */
    public int addActor(String name, String actorId){

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
    
    /** Add Relationship
     * This endpoint is to add an ACTED_IN relationship between an actor 
     * and a movie in the database
     * @param actorId
     * @param movieId
     * @return  - 200 OK - For a successful add
                - 400 BAD REQUEST - If the request body is improperly formatted or 
                  missing required information
                - 404 NOT FOUND - If the actor or movie does not exist when adding the 
                  relationship
                - 500 INTERNAL SERVER ERROR - If save or add was unsuccessful 
                  (Java Exception Thrown)
     */
    public int addRelationship(String actorId, String movieId){

        // try () {
            // if (actor or movie not present)
                // return 404 on fail
            // else ()
                // if (fail)
                    // return 400
                // else
                    // return 200 on pass
        // }
        // catch(Exception e){
            // catch error
                // return 500 on INTERNAL SERVER ERROR
        // }
        return 1;

    }



}


