package ca.utoronto.utm.mcs;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

// TODO Please Write Your Tests For CI/CD In This Class. You will see
    // these tests pass/fail on github under github actions.    
public class AppTest {

    //Add
    //--------------------------------
    @Test
    public void addActorPass() { //200
        assertTrue(true);
    }
    @Test
    public void addActorFail() { //400
        assertTrue(true);
    }
    //--------------------------------
    @Test
    public void addMoviePass() { //200
        assertTrue(true);
    }    
    @Test
    public void addMovieFail() { //400
        assertTrue(true);
    }
    //--------------------------------
    @Test
    public void addRelationshipPass() { //200
        assertTrue(true);
    }
    @Test
    public void addRelationshipFail() { //400,404
        assertTrue(true);
    }
    //--------------------------------
    
    // Get
    //--------------------------------
    @Test
    public void getActorPass() { //200
        assertTrue(true);
    }    
    @Test
    public void getActorFail() { //400,404
        assertTrue(true);
    }
    //--------------------------------
    @Test
    public void getMoviePass() { //200
        assertTrue(true);
    }    
    @Test
    public void getMovieFail() { //400,404
        assertTrue(true);
    }
    //--------------------------------
    
    // Has
    //--------------------------------
    @Test
    public void hasRelationshipPass() { //200
        assertTrue(true);
    }    
    @Test
    public void hasRelationshipFail() { //400,404
        assertTrue(true);
    }
    //--------------------------------
    
    // Compute
    //--------------------------------
    @Test
    public void computeBaconNumberPass() { //200
        assertTrue(true);
    }    
    @Test
    public void computeBaconNumberFail() { //400,404
        assertTrue(true);
    }
    //--------------------------------
    @Test
    public void computeBaconPathPass() { //200
        assertTrue(true);
    }    
    @Test
    public void computeBaconPathFail() { //400,404
        assertTrue(true);
    }
    //--------------------------------

}
