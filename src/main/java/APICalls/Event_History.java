package APICalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Event_History {

    public Map<String, String> environment;

    public Event_History() {
        init();
    }

    @BeforeTest
    public void init() {
        environment = APIEnvironment.getEnvironment();
    }

    @Test //GET
    public void get_panel_events() {
        System.out.println("Starting get_panel_events...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/panels/{account_number}/events");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_panel_arming_events() {
        System.out.println("Starting get_panel_arming_events...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/panels/{account_number}/events/arming");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_panel_alarm_events() {
        System.out.println("Starting get_panel_alarm_events...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/panels/{account_number}/events/alarms");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_all_sensor_events() {
        System.out.println("Starting get_all_sensor_events...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/panels/{account_number}/events/sensors");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_sensor_events() {
        System.out.println("Starting get_sensor_events...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("zone_id", environment.get("zone_id"));

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/panels/{account_number}/events/sensors/{zone_id}");

        System.out.println(response.body().asString());
    }


    @Test //GET
    public void get_all_home_automation_device_events() {
        System.out.println("Starting get_all_home_automation_device_events...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/panels/{account_number}/events/homeautomation");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_home_automation_device_events() {
        String defaultDeviceId = environment.get("device_id_thermo");
        get_home_automation_device_events(defaultDeviceId);
    }
    public void get_home_automation_device_events(String deviceId) {
        System.out.println("Starting get_home_automation_device_events...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/panels/{account_number}/events/homeautomation/{device_id}");

        System.out.println(response.body().asString());
    }
}