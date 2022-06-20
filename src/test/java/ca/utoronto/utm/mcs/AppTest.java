package ca.utoronto.utm.mcs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {

    @BeforeAll
    private static void setServer(){

        ServerComponent component = DaggerServerComponent.create();
        Server s = component.buildServer();
        s.setupServer();
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
        JSONObject rq = new JSONObject();
        rq.put("name", "Kevin Bacon");
        rq.put("actorId", "21");
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
    @Order(2)
    public void addActorFail() throws JSONException, IOException, InterruptedException { //400
        JSONObject rq = new JSONObject();
        rq.put("name", "Kevin Bacon");
        rq.put("actorId", "21");
        HttpResponse<String> confirmRes = sendRequest("addActor", "PUT", rq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }
    //--------------------------------
    @Test
    @Order(3)
    public void addMoviePass() throws JSONException, IOException, InterruptedException { //200
        JSONObject rq = new JSONObject();
        rq.put("name", "Pirates");
        rq.put("movieId", "22");
        HttpResponse<String> confirmRes = sendRequest("addMovie", "PUT", rq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
    }    
    @Test
    @Order(4)
    public void addMovieFail() throws JSONException, IOException, InterruptedException { //400
        JSONObject rq = new JSONObject();
        rq.put("name", "Pirates");
        rq.put("movieId", "22");
        HttpResponse<String> confirmRes = sendRequest("addMovie", "PUT", rq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }
    //--------------------------------
    @Test
    @Order(5)
    public void addRelationshipPass() throws JSONException, IOException, InterruptedException { //200
        JSONObject rq = new JSONObject();
        rq.put("actorId", "21");
        rq.put("movieId", "22");
        HttpResponse<String> confirmRes = sendRequest("addRelationship", "PUT", rq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());

    }
    @Test
    @Order(6)
    public void addRelationshipFail() throws JSONException, IOException, InterruptedException { //404
        JSONObject rq = new JSONObject();
        rq.put("actorId", "211");
        rq.put("movieId", "22");
        HttpResponse<String> confirmRes = sendRequest("addRelationship", "PUT", rq.toString());
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, confirmRes.statusCode());
    }
    @Test
    @Order(7)
    public void addRelationshipFail400() throws JSONException, IOException, InterruptedException { //400
        JSONObject rq = new JSONObject();
        rq.put("actod", "21");
        rq.put("movieId", "10");
        HttpResponse<String> confirmRes = sendRequest("addRelationship", "PUT", rq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }
    //--------------------------------
    // Get
    //--------------------------------
    @Test
    @Order(8)
    public void getActorPass() throws JSONException, IOException, InterruptedException { //200
        JSONObject rq = new JSONObject();
        rq.put("actorId", "21");
        HttpResponse<String> confirmRes = sendRequest("getActor", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
        assertEquals("{\"movies\":[\"22\"],\"actorId\":\"21\",\"name\":\"Kevin Bacon\"}", confirmRes.body());
    }
    @Test
    @Order(9)
    public void getActorFail() throws JSONException, IOException, InterruptedException { //404
        JSONObject rq = new JSONObject();
        rq.put("actorId", "122231");
        HttpResponse<String> confirmRes = sendRequest("getActor", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, confirmRes.statusCode());
//        assertEquals("{\"movies\":[\"32\"],\"actorId\":\"21\",\"name\":\"Jack\"}", confirmRes.body());
    }
    @Test
    @Order(10)
    public void getActorFail400() throws JSONException, IOException, InterruptedException { //400
        JSONObject rq = new JSONObject();
        rq.put("actr", "122231");
        HttpResponse<String> confirmRes = sendRequest("getActor", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
//        assertEquals("{\"movies\":[\"32\"],\"actorId\":\"21\",\"name\":\"Jack\"}", confirmRes.body());
    }
    //--------------------------------
    @Test
    @Order(11)
    public void getMoviePass() throws JSONException, IOException, InterruptedException { //200
        JSONObject rq = new JSONObject();
        rq.put("movieId", "22");
        HttpResponse<String> confirmRes = sendRequest("getMovie", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
        assertEquals("{\"actors\":[\"21\"],\"name\":\"Pirates\",\"movieId\":\"22\"}", confirmRes.body());
    }
    @Test
    @Order(12)
    public void getMovieFail() throws JSONException, IOException, InterruptedException { //404
        JSONObject rq = new JSONObject();
        rq.put("movieId", "122231");
        HttpResponse<String> confirmRes = sendRequest("getMovie", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, confirmRes.statusCode());
    }
    @Test
    @Order(13)
    public void getMovieFail400() throws JSONException, IOException, InterruptedException { //400
        JSONObject rq = new JSONObject();
        rq.put("movId", "122231");
        HttpResponse<String> confirmRes = sendRequest("getMovie", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }
    //--------------------------------
    @Test
    @Order(14)
    public void hasRelationshipPass() throws JSONException, IOException, InterruptedException { //200
        JSONObject rq = new JSONObject();
        rq.put("actorId", "21");
        rq.put("movieId", "22");
        HttpResponse<String> confirmRes = sendRequest("hasRelationship", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
        assertEquals("{\"actorId\":\"21\",\"movieId\":\"22\",\"hasRelationship\":true}", confirmRes.body());
    }
    @Test
    @Order(15)
    public void hasRelationshipFail() throws JSONException, IOException, InterruptedException { //404
        JSONObject rq = new JSONObject();
        rq.put("actorId", "22311");
        rq.put("movieId", "3432");
        HttpResponse<String> confirmRes = sendRequest("hasRelationship", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, confirmRes.statusCode());
//        assertEquals("{\"actorId\":\"21\",\"movieId\":\"32\",\"hasRelationship\":true}", confirmRes.body());
    }
    @Test
    @Order(16)
    public void hasRelationshipFail400() throws JSONException, IOException, InterruptedException { //400
        JSONObject rq = new JSONObject();
        rq.put("acorId", "22311");
        rq.put("moeId", "3432");
        HttpResponse<String> confirmRes = sendRequest("hasRelationship", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
//        assertEquals("{\"actorId\":\"21\",\"movieId\":\"32\",\"hasRelationship\":true}", confirmRes.body());
    }
    //--------------------------------
    // Compute
    //--------------------------------
    @Test
    @Order(17)
    public void computeBaconNumberPass() throws JSONException, IOException, InterruptedException { //200
        JSONObject rq = new JSONObject();
        rq.put("actorId", "21");
        HttpResponse<String> confirmRes = sendRequest("computeBaconNumber", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
        assertEquals("{\"baconNumber\":0}", confirmRes.body());
    }
    @Test
    @Order(18)
    public void computeBaconNumberFail() throws JSONException, IOException, InterruptedException { //404
        JSONObject rq = new JSONObject();
        rq.put("actorId", "212");
        HttpResponse<String> confirmRes = sendRequest("computeBaconNumber", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, confirmRes.statusCode());
//        assertEquals("{\"baconNumber\":0}", confirmRes.body());
    }
    @Test
    @Order(19)
    public void computeBaconNumberFail400() throws JSONException, IOException, InterruptedException { //400
        JSONObject rq = new JSONObject();
        rq.put("actId", "212");
        HttpResponse<String> confirmRes = sendRequest("computeBaconNumber", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
//        assertEquals("{\"baconNumber\":0}", confirmRes.body());
    }
    //--------------------------------
    @Test
    @Order(20)
    public void computeBaconPathPass() throws JSONException, IOException, InterruptedException { //200
        JSONObject rq = new JSONObject();
        rq.put("actorId", "21");
        HttpResponse<String> confirmRes = sendRequest("computeBaconPath", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
        assertEquals("{\"baconPath\":[\"21\"]}", confirmRes.body());
    }
    @Test
    @Order(21)
    public void computeBaconPathFail() throws JSONException, IOException, InterruptedException { //404
        JSONObject rq = new JSONObject();
        rq.put("actorId", "212");
        HttpResponse<String> confirmRes = sendRequest("computeBaconPath", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, confirmRes.statusCode());
//        assertEquals("{\"baconPath\":[\"21\"]}", confirmRes.body());
    }
    @Test
    @Order(22)
    public void computeBaconPathFail400() throws JSONException, IOException, InterruptedException { //404
        JSONObject rq = new JSONObject();
        rq.put("acrId", "212");
        HttpResponse<String> confirmRes = sendRequest("computeBaconPath", "GET", rq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
//        assertEquals("{\"baconPath\":[\"21\"]}", confirmRes.body());
    }
    //--------------------------------

}
