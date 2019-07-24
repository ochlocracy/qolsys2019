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


public class Panel_Software_Upgrades {

    public Map<String, String> environment;

    public Panel_Software_Upgrades() {
        init();
    }
    public Panel_Software_Upgrades(Map<String, String> environment) {
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
    public void test_single_upgrade() {
        single_upgrade();
    }
    public Response single_upgrade() {
        System.out.println("Starting single_updgrade...");
        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.post("/panels/{account_number}/upgrades");

        System.out.println(response.body().asString());

        return response;
    }

}
