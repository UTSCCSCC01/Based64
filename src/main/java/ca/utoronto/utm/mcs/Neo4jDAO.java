package ca.utoronto.utm.mcs;
import org.neo4j.driver.*;
// NOTE: all your db transactions or queries should go in this class
public class Neo4jDAO {
    private final Driver driver;
    // Constructor
    public Neo4jDAO(String uriDb, String username, String password) {
        this.driver = GraphDatabase.driver(uriDb, AuthTokens.basic(username, password));   
    }


    // _______________________________________________
    // Put Requests
    // _______________________________________________

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

        try (Session session = this.driver.session()) {
            try(Transaction tx = session.beginTransaction()){
                if (checkDatabase(movieId, 1 ) == 1){
                    return 400;
                }
                String query = "CREATE (m: Movie {name: '%s', id:'%s'})".formatted(name, movieId);
                tx.run(query);
                tx.commit();
                return 200;
                
            }catch(Exception e1){
                e1.printStackTrace();
                return 500;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return 500;
        }
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

        try (Session session = this.driver.session()) {
            try(Transaction tx = session.beginTransaction()){
                if (checkDatabase(actorId, 0 ) == 1){
                    return 400;
                }
                String query = "CREATE (m: Actor {name: '%s', id:'%s'})".formatted(name, actorId);
                tx.run(query);
                tx.commit();
                return 200;
                
            }catch(Exception e1){
                e1.printStackTrace();
                return 500;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return 500;
        }
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
    // TODO relationship already exists return 400
    public int addRelationship(String actorId, String movieId){

        try (Session session = this.driver.session()){
            try(Transaction tx = session.beginTransaction()){
                if (checkDatabase(actorId, 0 ) == 0 || checkDatabase(movieId, 1 ) == 0){
                    return 404;
                }
                String query = "MATCH (a: Actor), (m: Movie) WHERE a.id = %s AND m.id = '%s' CREATE (a)-[:ACTED_IN]->(m)".formatted(actorId, movieId);
                tx.run(query);
                tx.commit();
                return 200;
                
            }catch(Exception e1){
                e1.printStackTrace();
                return 500;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return 500;
        }

    }
    // ________________________________________________ 
    // Put Helper functions
    // ________________________________________________ 
    private int checkDatabase(String id, int actorOrMovie ){
        // Actor => actorOrMovie = 0
        // Movie => actorOrMovie = 1
        try(Session session = driver.session()){
            try(Transaction tx = session.beginTransaction()){
                String query;
                if (actorOrMovie == 0){
                    query = "MATCH (a: Actor) WHERE a.id = '%s' RETURN a".formatted(id);
                }else{
                    query = "MATCH (m: Movie) WHERE m.id = '%s' RETURN a".formatted(id);
                }
                boolean x = tx.run(query).hasNext();
                tx.commit();

                if (x)
                    return 0;   // if exists
                return 1;       // if DNE
                
            }
            catch(Exception e1){
                e1.printStackTrace();
                return 500;
            }

        }
        catch(Exception e){
            e.printStackTrace();
            return 500;
        }

    }
    





    // Get Requests

    



}


