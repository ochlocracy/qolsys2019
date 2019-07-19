package APICalls.Misc;

import APICalls.APIEnvironment;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Analytics {

    public Map<String, String> environment;

    public Analytics() {
        init();
    }

    @BeforeTest
    public void init() {
        environment = APIEnvironment.getEnvironment();
    }

    @Test //GET
    public void get_panel_analytics() {
        System.out.println("Starting get_panel_analytics...");
        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        Response response = request.get("/analytics/panels");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_account_analytics() {
        System.out.println("Starting get_account_analytics...");
        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        Response response = request.get("/analytics/accounts");

        System.out.println(response.body().asString());
    }
}
