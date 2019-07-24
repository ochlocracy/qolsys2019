package APICalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class IQ_Remote {

    public Map<String, String> environment;

    public IQ_Remote() {
        init();
    }
    public IQ_Remote(Map<String, String> environment) {
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
    public void test_get_all_iq_remotes() {
        get_all_iq_remotes();
    }
    public Response get_all_iq_remotes() {
        System.out.println("Starting get_all_iq_remotes...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/iqremote");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_iq_remote() {
        get_iq_remote();
    }
    public Response get_iq_remote() {
        String defaultDeviceId = "1";
        return get_iq_remote(defaultDeviceId);
    }
    public Response get_iq_remote(String deviceId) {
        System.out.println("Starting get_iqremote_device...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);

        Response response = request.get("/panels/{account_number}/devices/iqremote/{device_id}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_iqremote_property() {
        get_iqremote_property();
    }
    public Response get_iqremote_property() {
        String defaultDeviceId = "1";
        String defaultPropertyName = "device_name";
        return get_iqremote_property(defaultDeviceId, defaultPropertyName);
    }
    public Response get_iqremote_property(String deviceId, String propertyName) {
        System.out.println("Starting get_iqremote_property...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("device_id", deviceId);
        request.pathParam("property_name", propertyName);

        Response response = request.get("/panels/{account_number}/devices/iqremote/{device_id}/{property_name}");

        System.out.println(response.body().asString());

        return response;
    }
}
