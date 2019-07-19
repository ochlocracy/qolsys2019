package APICalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Panel_Alarms {

    public Map<String, String> environment;

    public Panel_Alarms() {
        init();
    }

    @BeforeTest
    public void init() {
        environment = APIEnvironment.getEnvironment();
    }

    @Test //GET
    public void get_all_iqpanel_alarms() {
        System.out.println("Starting get_all_panel_alarms...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        Response response = request.get("/alarms");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_iqpanels_alarms() {
        System.out.println("Starting get_iqpanels_alarms...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/alarms");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_iqpanels_partition_alarms() {
        System.out.println("Starting get_iqpanels_partition_alarms...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("partition_id", environment.get("partition_id"));

        Response response = request.get("/panels/{account_number}/alarms/{partition_id}");

        System.out.println(response.body().asString());
    }

    @Test //PUT
    public void cancel_iqpanels_partition_active_alarms() {
        String defaultUserCode = "1234";
        cancel_iqpanels_partition_active_alarms(defaultUserCode);
    }
    public void cancel_iqpanels_partition_active_alarms(String userCode) {
        System.out.println("Starting cancel_iqpanels_partition_active_alarms...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("partition_id", environment.get("partition_id"));

        JSONObject requestBody = new JSONObject();
        requestBody.put("user_code", userCode);

        request.body(requestBody.toString());

        Response response = request.put("/panels/{account_number}/alarms/{partition_id}/cancel");

        System.out.println(response.body().asString());
    }
}
