package APICalls.Misc;

import APICalls.APIEnvironment;
import com.sun.org.apache.regexp.internal.RE;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Panel_Acceptance_Test {

    public Map<String, String> environment;

    public Panel_Acceptance_Test() {
        init();
    }
    public Panel_Acceptance_Test(Map<String, String> environment) {
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
    public void test_ac_power_test_on_off() {
        ac_power_test_on_off();
    }
    public Response ac_power_test_on_off() {
        System.out.println("Starting ac_power_test_on_off...");
        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.post("/panels/{account_number}/test/acpower");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //POST
    public void test_connection_test_online_offline() {
        connection_test_online_offline();
    }
    public Response connection_test_online_offline() {
        System.out.println("Starting connection_test_online_offline...");
        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.post("/panels/{account_number}/test/connection");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //POST
    public void test_tamper_status() {
        tamper_status();
    }
    public Response tamper_status() {
        System.out.println("Starting tamper_status...");
        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.post("/panels/{account_number}/test/tamper");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //POST
    public void test_alarm_status() {
        alarm_status();
    }
    public Response alarm_status() {
        System.out.println("Starting alarm_status...");
        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.post("/panels/{account_number}/test/alarm");

        System.out.println(response.body().asString());

        return response;
    }
}