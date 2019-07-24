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
    public Sensor(Map<String, String> environment) {
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
    public void test_get_all_sensors() {
        get_all_sensors();
    }
    public Response get_all_sensors() {
        System.out.println("Starting get_all_sensors...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/sensors");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_sensor() {
        get_sensor();
    }
    public Response get_sensor() {
        return get_sensor(environment.get("zone_id"));
    }
    public Response get_sensor(String sensorId) {
        System.out.println("Starting get_sensor...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("zone_id", sensorId);

        Response response = request.get("/panels/{account_number}/devices/sensors/{zone_id}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_sensor_property() {
        get_sensor_property();
    }
    public Response get_sensor_property() {
        String defaultProperty = "sensor_name";
        return get_sensor_property(defaultProperty);
    }
    public Response get_sensor_property(String property) {
        System.out.println("Starting get_sensor_property...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("zone_id", Integer.parseInt(environment.get("srf_sensor_id")));
        request.pathParam("property_name", property);

        Response response = request.get("/panels/{account_number}/devices/sensors/{zone_id}/{property_name}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //POST
    public void test_learn_sensor() {
        learn_sensor();
    }
    public Response learn_sensor() {
        System.out.println("Starting learn_sensor...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.post("/panels/{account_number}/devices/sensors/learn");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_learn_sensor_poll_result() {
        learn_sensor_poll_result();
    }
    public Response learn_sensor_poll_result() {
        System.out.println("Starting learn_sensor_poll_result...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/sensors/learn");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //POST
    public void test_add_sensor() {
        add_sensor();
    }
    public Response add_sensor() {
        //Default values
        Integer zoneId = Integer.parseInt(environment.get("srf_sensor_id"));
        Integer partitionId = 0;
        String sensorId = "A12343";
        String sensorType = "SMOKE_HEAT";
        Integer sensorGroup = 26;
        String sensorSignalSource = "ge_319_sline";
        String zoneLoop = "loop_1";
        return add_sensor(zoneId, partitionId, sensorId, null, sensorType,
                   sensorGroup, sensorSignalSource, zoneLoop);
    }
    public Response add_sensor(Integer zoneId, Integer partitionId, String sensorId,
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

        return response;
    }

//    @Test //POST
//    public void test_sensor_test() {
//        sensor_test();
//    }
//    public Response sensor_test() {
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
//
//        return response;
//    }

    @Test //PUT
    public void test_update_sensor() {
        update_sensor();
    }
    public Response update_sensor() {
        String propertyName = "sensor_name";
        Object propertyValue = "Smoke Detector Test";
        return update_sensor(propertyName, propertyValue);
    }
    public Response update_sensor(String propertyName, Object propertyValue) {
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

        return response;
    }

    @Test //DELETE
    // DANGER DON'T DELETE AN IMPORTANT SENSOR
    public void test_delete_sensor() {
        delete_sensor();
    }
    public Response delete_sensor() {
        return delete_sensor(Integer.parseInt(environment.get("srf_sensor_id")));
    }
    public Response delete_sensor(int sensorId) {
        System.out.println("Starting delete_sensor...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("zone_id", sensorId);

        Response response = request.delete("/panels/{account_number}/devices/sensors/{zone_id}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_srf_sensors() {
        srf_sensors();
    }
    public Response srf_sensors() {
        System.out.println("Starting srf_sensors...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/sensors/srf");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_powerg_sensors() {
        powerg_sensors();
    }
    public Response powerg_sensors() {
        System.out.println("Starting powerg_sensors...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/devices/sensors/powerg");

        System.out.println(response.body().asString());

        return response;
    }

}
