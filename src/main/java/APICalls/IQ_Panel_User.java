package APICalls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class IQ_Panel_User {

    public Map<String, String> environment;

    public IQ_Panel_User() {
        init();
    }

    @BeforeTest
    public void init() {
        environment = APIEnvironment.getEnvironment();
    }

    @Test //GET
    public void get_all_users() {
        System.out.println("Starting get_all_users...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/panels/{account_number}/users");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_user() {
        System.out.println("Starting get_users...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("user_id", environment.get("user_id"));

        Response response = request.get("/panels/{account_number}/users/{user_id}");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_user_property() {
        String defaultProperty = "user_name";
        get_user_property(defaultProperty);
    }
    public void get_user_property(String propertyName) {

        System.out.println("Starting get_user_property("+propertyName+")...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("user_id", environment.get("user_id"));
        request.pathParam("property_name", propertyName);

        Response response = request.get("/panels/{account_number}/users/{user_id}/{property_name}");

        System.out.println(response.body().asString());
    }

//    @Test //POST
//    //DANGER (maybe)
//    public void create_user() {
//        Integer newUserId = 1;
//        int[] newPartitionId = {};
//        String newUserCode = "";
//        create_user(newUserId, newPartitionId, newUserCode);
//    }
//    public void create_user(Integer userId, int[] partitionId, String userCode) {
//        System.out.println("Starting create_user...");
//
//        RestAssured.baseURI = environment.get("domain_url");
//        RequestSpecification request = RestAssured.given();
//        request.header("Content-Type", "application/json");
//
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("user_id", userId);
//        requestBody.put("partition_id", userCode);
//
//        request.pathParam("account_number", environment.get("account_number"));
//
//        request.body(requestBody.toString());
//        Response response = request.post("/panels/{account_number}/users");
//
//        System.out.println(response.body().asString());
//    }

    @Test //PUT
    public void update_user() {
        String defaultProperty = "user_name";
        Object propertyValue = "EvanII";
        update_user(defaultProperty, propertyValue);
    }
    public void update_user(String propertyName, Object propertyValue) {
        System.out.println("Starting update_user("+propertyName+", "+propertyValue+")...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "Application/Json");

        JSONObject requestBody = new JSONObject();
        requestBody.put(propertyName, propertyValue);

        request.body(requestBody.toString());

        request.pathParam("account_number", environment.get("account_number"));
        request.pathParam("user_id", environment.get("user_id"));

        Response response = request.put("/panels/{account_number}/users/{user_id}");

        System.out.println(response.body().asString());
    }


}
