package APICalls;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Authentication {

    private Map<String, String> environment;

    @BeforeTest
    public void init() {
        environment = APIEnvironment.getEnvironment();
    }

    @Test
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

        //edit refresh token
    }

    @Test
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

        //edit refresh token
    }

}
