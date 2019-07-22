package APICalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.auth.AUTH;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Authentication {

    private Map<String, String> environment;

    public Authentication() {
        init();
    }
    public Authentication(Map<String, String> environment) {
        init(environment);
    }

    @BeforeTest
    public void init() {
        init(APIEnvironment.getEnvironment());
    }
    public void init(Map<String, String> environment) {
        this.environment = environment;
    }

    @Test //POST
    public void generate_access_token() {
        System.out.println("Starting generate_access_token...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("username", environment.get("username"));
        requestBody.put("password", environment.get("password"));

        request.body(requestBody.toString());
        Response response = request.post("/auth/token");

        System.out.println(response.body().asString());

//        System.out.println("Original: " + environment.get("refresh_token"));
        JsonPath pathEvaluator = response.jsonPath();
        environment.put("refresh_token", pathEvaluator.get("refresh_token").toString());
//        System.out.println("New: " + environment.get("refresh_token"));
    }

    @Test //POST
    public void refresh_access_token() {
        System.out.println("Starting refresh_access_token...");
        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("refresh_token", environment.get("refresh_token"));

        request.body(requestBody.toString());
        Response response = request.post("/auth/token/refresh");

        System.out.println(response.body().asString());

        JsonPath pathEvaluator = response.jsonPath();
        environment.put("refresh_token", pathEvaluator.get("refresh_token").toString());
    }

}
