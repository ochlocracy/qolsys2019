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


public class Timezone {

    public Map<String, String> environment;

    public Timezone() {
        init();
    }
    public Timezone(Map<String, String> environment) {
        init(environment);
    }

    @BeforeTest
    public void init() {
        init(APIEnvironment.getEnvironment());
    }
    public void init(Map<String, String> environment) {
        this.environment = environment;
    }

    @Test //GET
    public void get_all_timezones() {
        System.out.println("Starting get_sensor_events...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        Response response = request.get("/timezones");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_timezone_information() {
        System.out.println("Starting get_timezone_information...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("timezone_id", environment.get("time_zone"));

        Response response = request.get("/timezones/{timezone_id}");

        System.out.println(response.body().asString());
    }
}
