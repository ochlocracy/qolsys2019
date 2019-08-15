package APICalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class IQ_Panel {

    public Map<String, String> environment;

    public IQ_Panel() {
        init();
    }
    public IQ_Panel(Map<String, String> environment) {
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
    public void test_get_all_iqpanels() {
        get_all_iqpanels();
    }
    public Response get_all_iqpanels() {
        System.out.println("Starting get_all_iq_panels...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/panels");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_iqpanel_information() {
        get_iqpanel_information();
    }
    public Response get_iqpanel_information() {
        System.out.println("Starting get_iqpanel_information...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_iqpanels_property() {
        get_iqpanels_property();
    }
    public Response get_iqpanels_property() {
        String defaultProperty = "connection_status";
        return get_iqpanels_property(defaultProperty);
    }
    public Response get_iqpanels_property(String propertyName) {

        System.out.println("Starting get_iqpanels_property("+propertyName+")...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("property_name", propertyName);

        Response response = request.get("/panels/{account_number}/{property_name}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_iqpanels_arm_status() {
        Response result = get_iqpanels_arm_status();
        System.out.println();
        System.out.println("Panel is in " + result.jsonPath().get("arming_status"));
    }
    public Response get_iqpanels_arm_status() {
        System.out.println("Starting get_iqpanels_arm_status...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/status");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_iqpanels_partition_arm_status() {
        Response result = get_iqpanels_partition_arm_status();
        System.out.println();
        System.out.println("Panel is in " + result.jsonPath().get("arming_status"));
    }
    public Response get_iqpanels_partition_arm_status() {
        System.out.println("Starting get_iqpanels_partition_arm_status...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("partition_id", environment.get("partition_id"));

        Response response = request.get("/panels/{account_number}/status/{partition_id}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //PUT
    public void test_set_iqpanels_partition_arm_status() {
        set_iqpanels_partition_arm_status();
    }
    public Response set_iqpanels_partition_arm_status() {
        String defaultArmStatus = "arm_stay";
        return set_iqpanels_partition_arm_status(defaultArmStatus);
    }
    public Response set_iqpanels_partition_arm_status(String newArmStatus) {
        System.out.println("Starting set_iqpanels_partition_arm_status("+newArmStatus+")...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "Application/Json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("arming_status", newArmStatus);
        request.body(requestBody.toString());

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("partition_id", environment.get("partition_id"));

        Response response = request.put("/panels/{account_number}/status/{partition_id}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_all_iqpanel_settings() {
        get_all_iqpanel_settings();
    }
    public Response get_all_iqpanel_settings() {
        System.out.println("Starting get_all_iqpanel_settings...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/settings");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_iqpanels_setting() { get_iqpanels_setting(); }
    public Response get_iqpanels_setting() {
        String defaultSetting = "font_size";
        return get_iqpanels_setting(defaultSetting);
    }
    public Response get_iqpanels_setting(String settingName) {

        System.out.println("Starting get_iqpanels_property("+settingName+")...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("setting_name", settingName);

        Response response = request.get("/panels/{account_number}/settings/{setting_name}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //PUT
    public void test_update_iqpanels_setting() {
        update_iqpanel_setting();
    }
    public Response update_iqpanel_setting() {
        String defaultSettingName = "font_size";
        Object defaultSettingValue = "large";
        return update_iqpanel_setting(defaultSettingName, defaultSettingValue);
    }
    public Response update_iqpanel_setting(String settingName, Object settingValue) {

        System.out.println("Starting set_iqpanels_property("+settingName+", "+settingValue+")...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "Application/Json");

        JSONObject requestBody = new JSONObject();
        requestBody.put(settingName, settingValue);

        request.body(requestBody.toString());

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.put("/panels/{account_number}/settings");

        System.out.println(response.body().asString());

        return response;
    }
}
