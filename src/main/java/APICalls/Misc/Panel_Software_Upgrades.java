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

    @BeforeTest
    public void init() {
        environment = APIEnvironment.getEnvironment();
    }

    @Test //POST
    public void single_updgrade() {
        System.out.println("Starting single_updgrade...");
        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.post("/panels/{account_number}/upgrades");

        System.out.println(response.body().asString());
    }

}
