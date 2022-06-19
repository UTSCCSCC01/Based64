package ca.utoronto.utm.mcs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

// TODO Please Write Your Tests For CI/CD In This Class. You will see
    // these tests pass/fail on github under github actions.    
public class AppTest {

    @BeforeAll
    private static void setServer(){
        // TODO
    }

    final static String API_URL = "http://localhost:8080";
    final static String Path = "/api/v1";
    private static HttpResponse<String> sendRequest(String endpoint, String method, String reqBody) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + Path + "/"+ endpoint))
//                .version(HttpClient.Version.HTTP_1_1)
                .method(method, HttpRequest.BodyPublishers.ofString(reqBody))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    //Add
    //--------------------------------
    @Test
    @Order(1)
    public void addActorPass() throws JSONException, IOException, InterruptedException { //200
//        URL url = new URL(API_URL+"/api/v1/addActor");
        JSONObject rq = new JSONObject();
        rq.put("name", "Jack");
        rq.put("actorId", "20");
        HttpResponse<String> confirmRes = sendRequest("addActor", "PUT", rq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
//        assertEquals("2\n", confirmRes.body());
//        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//        httpURLConnection.setDoOutput(true);
//        httpURLConnection.setRequestMethod("PUT");
//        httpURLConnection.setRequestProperty("Content-Type", "application/json");
//        OutputStream os = httpURLConnection.getOutputStream();
//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os, StandardCharsets.UTF_8);
//        outputStreamWriter.write(rq.toString());
//        outputStreamWriter.close();
//        os.close();
//        OutputStreamWriter out = new OutputStreamWriter(
//                httpURLConnection.getOutputStream());
//        out.write("Resource content");
//        out.close();
//        httpURLConnection.getInputStream();
//        httpURLConnection.connect();
//        assertEquals(200, httpURLConnection.getResponseCode());
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
