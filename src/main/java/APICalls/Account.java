package APICalls;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Account {

    public Map<String, String> environment;

    public Account() {
        init();
    }
    public Account(Map<String, String> environment) {
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
    public void test_get_all_accounts() {
        get_all_accounts();
    }
    public Response get_all_accounts() {
        System.out.println("Starting get_all_accounts...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/accounts");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //GET
    public void test_get_account_information() {
        get_account_information();
    }
    public Response get_account_information() {
        System.out.println("Starting get_account_information...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/accounts/{account_number}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //POST
    public void test_create_account() {
        create_account();
    }
    public Response create_account() {
        environment.put("serial_number", "QV015164200952AD");
        return create_account( environment.get("account_number"),
                        environment.get("serial_number"),
                        Integer.parseInt(environment.get("time_zone")));
    }
    public Response create_account(String accountNumber, String serialNumber, Integer timezone) {
        System.out.println("Starting create_account...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("account_number", accountNumber);
        requestBody.put("serial_number", serialNumber);
        requestBody.put("time_zone", timezone);

        request.body(requestBody.toString());

        Response response = request.post("/accounts");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //PUT
    public void test_update_account() {
        update_account();
    }
    public Response update_account() {
        String defaultPropertyName = "first_name";
        Object defaultPropertyValue = "EvanII";
        return update_account(defaultPropertyName, defaultPropertyValue);
    }
    public Response update_account(String propertyName, Object propertyValue) {
        System.out.println("Starting update_account...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        JSONObject requestBody = new JSONObject();
        requestBody.put(propertyName, propertyValue);
        request.body(requestBody.toString());

        Response response = request.put("/accounts/{account_number}");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //PUT
    public void test_update_account_timezone() {
        update_account_timezone();
    }
    public Response update_account_timezone() {
        Integer defaultNewTimezone = Integer.parseInt(environment.get("time_zone"));
        return update_account_timezone(defaultNewTimezone);
    }
    public Response update_account_timezone(Integer newTimeZone) {
        System.out.println("Starting update_account_timezone...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");


        request.pathParam("account_number", environment.get("account_number"));

        JSONObject requestBody = new JSONObject();
        requestBody.put("timezone_id", newTimeZone);
        request.body(requestBody.toString());

        Response response = request.put("/accounts/{account_number}/timezone");

        System.out.println(response.body().asString());

        return response;
    }

    @Test //PUT
    public void test_update_account_status() {
        update_account_status();
    }
    public Response update_account_status() {
        String newStatus = "active";
        return update_account_status(newStatus);
    }
    public Response update_account_status(String newAccountStatus) {
        System.out.println("Starting update_account_status...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");


        request.pathParam("account_number", environment.get("account_number"));

        JSONObject requestBody = new JSONObject();
        requestBody.put("account_status", newAccountStatus);
        request.body(requestBody.toString());

        Response response = request.put("/accounts/{account_number}/status");

        System.out.println(response.body().asString());

        return response;
    }

//    @Test //DELETE
//    //DANGER, DON'T DELETE AN IMPORTANT ACCOUNT
//    public void test_delete_account() {
//        delete_account();
//    }
//    public Response delete_account() {
//        System.out.println("Starting delete_account...");
//
//        RestAssured.baseURI = environment.get("domain_url");
//        RequestSpecification request = RestAssured.given();
//
//        request.header("Authorization", "Bearer " + environment.get("access_token"));
//
//        String accountNumber = "";
//        request.pathParam("account_number", accountNumber);
//
//        Response response = request.put("/accounts/{account_number}/status");
//
//        System.out.println(response.body().asString());
//
//        return response;
//    }
}
