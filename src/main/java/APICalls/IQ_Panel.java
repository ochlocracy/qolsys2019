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

    @BeforeTest
    public void init() {
        environment = APIEnvironment.getEnvironment();
    }

    @Test
    public void get_all_iqpanels() {
        System.out.println("Starting get_all_iq_panels...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/panels");

        System.out.println(response.body().asString());
    }

    @Test
    public void get_iqpanel_information() {
        System.out.println("Starting get_iqpanel_information...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}");

        System.out.println(response.body().asString());
    }

    @Test
    public void get_iqpanels_property() {
        String defaultProperty = "connection_status";
        get_iqpanels_property(defaultProperty);
    }
    public void get_iqpanels_property(String propertyName) {

        System.out.println("Starting get_iqpanels_property("+propertyName+")...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("property_name", propertyName);

        Response response = request.get("/panels/{account_number}/{property_name}");

        System.out.println(response.body().asString());
    }

    @Test
    public void get_iqpanels_arm_status() {
        System.out.println("Starting get_iqpanels_arm_status...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/status");

        System.out.println(response.body().asString());
    }

    @Test
    public void get_iqpanels_partition_arm_status() {
        System.out.println("Starting get_iqpanels_partition_arm_status...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("partition_id", environment.get("partition_id"));

        Response response = request.get("/panels/{account_number}/status/{partition_id}");

        System.out.println(response.body().asString());
    }

    @Test
    public void set_iqpanels_partition_arm_status() {
        String defaultArmStatus = "arm_stay";
        set_iqpanels_partition_arm_status(defaultArmStatus);
    }
    public void set_iqpanels_partition_arm_status(String newArmStatus) {
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
    }

    @Test
    public void get_all_iqpanel_settings() {
        System.out.println("Starting get_all_iqpanel_settings...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/settings");

        System.out.println(response.body().asString());
    }

    @Test
    public void get_iqpanels_setting() {
        String defaultSetting = "font_size";
        get_iqpanels_setting(defaultSetting);
    }
    public void get_iqpanels_setting(String settingName) {

        System.out.println("Starting get_iqpanels_property("+settingName+")...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("setting_name", settingName);

        Response response = request.get("/panels/{account_number}/settings/{setting_name}");

        System.out.println(response.body().asString());
    }

    @Test
    public void update_iqpanel_setting() {
        String defaultSettingName = "alarm_photos";
        Object defaultSettingValue = true;
        update_iqpanel_setting(defaultSettingName, defaultSettingValue);
    }
    public void update_iqpanel_setting(String settingName, Object settingValue) {

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
    }
}
