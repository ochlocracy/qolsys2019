package APICalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Sensor {

    public Map<String, String> environment;

    public Sensor() {
        init();
    }

    @BeforeTest
    public void init() {
        environment = APIEnvironment.getEnvironment();
    }

    @Test //GET
    public void get_all_sensors() {
        System.out.println("Starting get_all_sensors...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/sensors");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_sensor() {
        System.out.println("Starting get_sensor...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("zone_id", environment.get("zone_id"));

        Response response = request.get("/panels/{account_number}/devices/sensors/{zone_id}");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_sensor_property() {
        String defaultProperty = "sensor_name";
        get_sensor_property(defaultProperty);
    }
    public void get_sensor_property(String property) {
        System.out.println("Starting get_sensor_property...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("zone_id", Integer.parseInt(environment.get("srf_sensor_id")));
        request.pathParam("property_name", property);

        Response response = request.get("/panels/{account_number}/devices/sensors/{zone_id}/{property_name}");

        System.out.println(response.body().asString());
    }

    @Test //POST
    public void learn_sensor() {
        System.out.println("Starting learn_sensor...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.post("/panels/{account_number}/devices/sensors/learn");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void learn_sensor_poll_result() {
        System.out.println("Starting learn_sensor_poll_result...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/sensors/learn");

        System.out.println(response.body().asString());
    }

    @Test //POST
    public void add_sensor() {
        //Default values
        Integer zoneId = Integer.parseInt(environment.get("srf_sensor_id"));
        Integer partitionId = 0;
        String sensorId = "A12343";
        String sensorType = "SMOKE_HEAT";
        Integer sensorGroup = 26;
        String sensorSignalSource = "ge_319_sline";
        String zoneLoop = "loop_1";
        add_sensor(zoneId, partitionId, sensorId, null, sensorType,
                   sensorGroup, sensorSignalSource, zoneLoop);
    }
    public void add_sensor(Integer zoneId, Integer partitionId, String sensorId,
                           String serialNumber, String sensorType, Integer sensorGroup,
                           String sensorSignalSource, String zoneLoop) {
        System.out.println("Starting add_sensor...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        JSONObject requestBody = new JSONObject();
        requestBody.put("zone_id", zoneId);
        requestBody.put("partition_id", partitionId);
        requestBody.put("sensor_id", sensorId);
        requestBody.put("serial_number", serialNumber);
        requestBody.put("sensor_type", sensorType);
        requestBody.put("sensor_group", sensorGroup);
        requestBody.put("sensor_signal_source", sensorSignalSource);
        requestBody.put("zone_loop", zoneLoop);

        request.body(requestBody.toString());

        Response response = request.post("/panels/{account_number}/devices/sensors");

        System.out.println(response.body().asString());
    }

//    @Test //POST
//    public void sensor_test() {
//        System.out.println("Starting sensor_test...");
//
//        RestAssured.baseURI = environment.get("domain_url");
//        RequestSpecification request = RestAssured.given();
//        request.header("Authorization", "Bearer " + environment.get("access_token"));
//        request.header("Content-Type", "application/json");
//
//        request.pathParam("account_number", environment.get("account_number"));
//        request.pathParam("zone_id", Integer.parseInt(environment.get("srf_sensor_id")));
//
//        Response response = request.post("/panels/{account_number}/devices/sensors/{zone_id/test}");
//
//        System.out.println(response.body().asString());
//    }

    @Test //PUT
    public void update_sensor() {
        String propertyName = "sensor_name";
        Object propertyValue = "Smoke Detector 69";
        update_sensor(propertyName, propertyValue);
    }
    public void update_sensor(String propertyName, Object propertyValue) {
        System.out.println("Starting update_sensor...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("zone_id", Integer.parseInt(environment.get("srf_sensor_id")));

        JSONObject requestBody = new JSONObject();
        requestBody.put(propertyName, propertyValue);

        request.body(requestBody.toString());

        Response response = request.put("/panels/{account_number}/devices/sensors/{zone_id}");

        System.out.println(response.body().asString());
    }

//    @Test //DELETE
//    // DANGER DON'T DELETE AN IMPORTANT SENSOR
//    public void delete_sensor() {
//        System.out.println("Starting delete_sensor...");
//
//        RestAssured.baseURI = environment.get("domain_url");
//        RequestSpecification request = RestAssured.given();
//        request.header("Authorization", "Bearer " + environment.get("access_token"));
//
//        request.pathParam("account_number", environment.get("account_number"));
//        request.pathParam("zone_id", Integer.parseInt(environment.get("srf_sensor_id")));
//
//        Response response = request.get("/panels/{account_number}/devices/sensors/{zone_id}");
//
//        System.out.println(response.body().asString());
//    }

    @Test //GET
    public void srf_sensors() {
        System.out.println("Starting srf_sensors...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/sensors/srf");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void powerg_sensors() {
        System.out.println("Starting powerg_sensors...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/sensors/powerg");

        System.out.println(response.body().asString());
    }

}
