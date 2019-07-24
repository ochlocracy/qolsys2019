package APICalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Home_Automation_Devices {

    public Map<String, String> environment;

    public Home_Automation_Devices() {
        init();
    }
    public Home_Automation_Devices(Map<String, String> environment) {
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
    public void test_set_learn_mode_on_off() {
        set_learn_mode_on_off();
    }
    public Response set_learn_mode_on_off() {
        Boolean defaultIsOn = true;
        return set_learn_mode_on_off(defaultIsOn);
    }
    public Response set_learn_mode_on_off(Boolean isOn) {
        System.out.println("Starting set_learn_mode_on_off...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        JSONObject requestBody = new JSONObject();
        requestBody.put("learn_enabled", isOn);

        request.body(requestBody.toString());

        Response response = request.post("/panels/{account_number}/devices/homeautomation/learn");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //POST
    public void test_set_delete_mode_on_off() {
        set_delete_mode_on_off();
    }
    public Response set_delete_mode_on_off() {
        Boolean defaultIsOn = true;
        return set_delete_mode_on_off(defaultIsOn);
    }
    public Response set_delete_mode_on_off(Boolean isOn) {
        System.out.println("Starting set_delete_mode_on_off...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        JSONObject requestBody = new JSONObject();
        requestBody.put("delete_enabled", isOn);

        request.body(requestBody.toString());

        Response response = request.post("/panels/{account_number}/devices/homeautomation/clear");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_all_home_automation_devices() {
        get_all_home_automation_devices();
    }
    public Response get_all_home_automation_devices() {
        System.out.println("Starting get_all_home_automation_devices...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/homeautomation");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_home_automation_device() {
        get_home_automation_device();
    }
    public Response get_home_automation_device() {
        Integer defaultDeviceId = 2;
        return get_home_automation_device(defaultDeviceId);
    }
    public Response get_home_automation_device(Integer deviceId) {
        System.out.println("Starting get_home_automation_device...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);

        Response response = request.get("/panels/{account_number}/devices/homeautomation/{device_id}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_home_automation_device_property() {
        get_home_automation_device_property();
    }
    public Response get_home_automation_device_property() {
        String defaultDeviceId = environment.get("device_id_thermo");
        String defaultPropertyName = "battery_status";
        return get_home_automation_device_property(defaultDeviceId, defaultPropertyName);
    }
    public Response get_home_automation_device_property(String deviceId, String propertyName) {
        System.out.println("Starting get_home_automation_device_property...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);
        request.pathParam("property_name", propertyName);

        Response response = request.get("/panels/{account_number}/devices/homeautomation/{device_id}/{property_name}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_update_home_automation_device() {
        update_home_automation_device();
    }
    public Response update_home_automation_device() {
        String defaultDeviceId = environment.get("device_id_thermo");
        String defaultDevicePropertyName = "mode";
        Object defaultDevicePropertyValue = "cool";
        return update_home_automation_device(defaultDeviceId, defaultDevicePropertyName, defaultDevicePropertyValue);
    }
    public Response update_home_automation_device(String deviceId, String propertyName, Object propertyValue) {
        System.out.println("Starting set_delete_mode_on_off...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);

        JSONObject requestBody = new JSONObject();
        requestBody.put(propertyName, propertyValue);

        request.body(requestBody.toString());

        Response response = request.put("/panels/{account_number}/devices/homeautomation/{device_id}");

        System.out.println(response.body().asString());

        return response;
    }

    //The rest of these tests are undocumented
    //UNDOCUMENTED
    @Test //GET
    public void test_get_all_devices_of_type() {
        get_all_devices_of_type();
    }
    public Response get_all_devices_of_type() {
        //can be 'thermostats' 'doorlocks' 'lights' 'dimmers' or 'smartsockets'
        String defaultType = "thermostats";
        return get_all_devices_of_type(defaultType);
    }
    public Response get_all_devices_of_type(String type) {
        System.out.println("Starting get_all_devices_of_type...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("type", type);

        Response response = request.get("/panels/{account_number}/devices/{type}");

        System.out.println(response.body().asString());

        return response;
    }
    //UNDOCUMENTED
    @Test //GET
    public void test_get_device_of_type() {
        get_device_of_type();
    }
    public Response get_device_of_type() {
        String defaultType = "thermostats";
        String defaultDeviceId = environment.get("device_id_thermo");
        return get_device_of_type(defaultType, defaultDeviceId);
    }
    public Response get_device_of_type(String type, String deviceId) {
        System.out.println("Starting get_device_of_type...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("type", type);
        request.pathParam("device_id", deviceId);

        Response response = request.get("/panels/{account_number}/devices/{type}/{device_id}");

        System.out.println(response.body().asString());

        return response;
    }
    //UNDOCUMENTED
    @Test //POST
    public void test_execute_raw_commands() {
        execute_raw_comands();
    }
    public Response execute_raw_comands() {
        String defaultDeviceId = environment.get("device_id_lock");
        String defaultCommand = "620001";
        return execute_raw_commands(defaultDeviceId, defaultCommand);
    }
    public Response execute_raw_commands(String deviceId, String command) {
        System.out.println("Starting execute_raw_commands...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);

        JSONObject requestBody = new JSONObject();
        requestBody.put("command", command);

        request.body(requestBody.toString());

        Response response = request.post("/panels/{account_number}/devices/homeautomation/{device_id}/commands");

        System.out.println(response.body().asString());

        return response;
    }
}
