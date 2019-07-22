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
    public void set_learn_mode_on_off() {
        Boolean defaultIsOn = true;
        set_learn_mode_on_off(defaultIsOn);
    }
    public void set_learn_mode_on_off(Boolean isOn) {
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
    }

    @Test //POST
    public void set_delete_mode_on_off() {
        Boolean defaultIsOn = true;
        set_delete_mode_on_off(defaultIsOn);
    }
    public void set_delete_mode_on_off(Boolean isOn) {
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
    }

    @Test //GET
    public void get_all_home_automation_devices() {
        System.out.println("Starting get_all_home_automation_devices...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/homeautomation");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_home_automation_device() {
        Integer defaultDeviceId = 2;
        get_home_automation_device(defaultDeviceId);
    }
    public void get_home_automation_device(Integer deviceId) {
        System.out.println("Starting get_home_automation_device...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);

        Response response = request.get("/panels/{account_number}/devices/homeautomation/{device_id}");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_home_automation_device_property() {
        String defaultDeviceId = environment.get("device_id_thermo");
        String defaultPropertyName = "battery_status";
        get_home_automation_device_property(defaultDeviceId, defaultPropertyName);
    }
    public void get_home_automation_device_property(String deviceId, String propertyName) {
        System.out.println("Starting get_home_automation_device_property...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);
        request.pathParam("property_name", propertyName);

        Response response = request.get("/panels/{account_number}/devices/homeautomation/{device_id}/{property_name}");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void update_home_automation_device() {
        String defaultDeviceId = environment.get("device_id_thermo");
        String defaultDevicePropertyName = "mode";
        Object defaultDevicePropertyValue = "cool";
        update_home_automation_device(defaultDeviceId, defaultDevicePropertyName, defaultDevicePropertyValue);
    }
    public void update_home_automation_device(String deviceId, String propertyName, Object propertyValue) {
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
    }

    //The rest of these tests are undocumented
    //UNDOCUMENTED
    @Test //GET
    public void get_all_devices_of_type() {
        //can be 'thermostats' 'doorlocks' 'lights' 'dimmers' or 'smartsockets'
        String defaultType = "thermostats";
        get_all_devices_of_type(defaultType);
    }
    public void get_all_devices_of_type(String type) {
        System.out.println("Starting get_all_devices_of_type...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("type", type);

        Response response = request.get("/panels/{account_number}/devices/{type}");

        System.out.println(response.body().asString());
    }
    //UNDOCUMENTED
    @Test //GET
    public void get_device_of_type() {
        String defaultType = "thermostats";
        String defaultDeviceId = environment.get("device_id_thermo");
        get_device_of_type(defaultType, defaultDeviceId);
    }
    public void get_device_of_type(String type, String deviceId) {
        System.out.println("Starting get_device_of_type...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("type", type);
        request.pathParam("device_id", deviceId);

        Response response = request.get("/panels/{account_number}/devices/{type}/{device_id}");

        System.out.println(response.body().asString());
    }
    //UNDOCUMENTED
    @Test //POST
    public void execute_raw_comands() {
        String defaultDeviceId = environment.get("device_id_lock");
        String defaultCommand = "620001";
        execute_raw_commands(defaultDeviceId, defaultCommand);
    }
    public void execute_raw_commands(String deviceId, String command) {
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
    }
}
