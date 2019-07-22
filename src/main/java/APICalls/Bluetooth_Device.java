package APICalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Bluetooth_Device {

    public Map<String, String> environment;

    public Bluetooth_Device() {
        init();
    }
    public Bluetooth_Device(Map<String, String> environment) {
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
    public void get_all_bluetooth_devices() {
        System.out.println("Starting get_all_bluetooth_devices...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/bluetooth");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_bluetooth_device() {
        String defaultDeviceId = "1";
        get_bluetooth_device(defaultDeviceId);
    }
    public void get_bluetooth_device(String deviceId) {
        System.out.println("Starting get_bluetooth_device...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);

        Response response = request.get("/panels/{account_number}/devices/bluetooth/{device_id}");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_bluetooth_property() {
        String defaultDeviceId = "1";
        String defaultPropertyName = "device_name";
        get_bluetooth_property(defaultDeviceId, defaultPropertyName);
    }
    public void get_bluetooth_property(String deviceId, String propertyName) {
        System.out.println("Starting get_bluetooth_property...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);
        request.pathParam("property_name", propertyName);

        Response response = request.get("/panels/{account_number}/devices/bluetooth/{device_id}/{property_name}");

        System.out.println(response.body().asString());
    }
}
