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

    @BeforeTest
    public void init() {
        environment = APIEnvironment.getEnvironment();
    }

    @Test //GET
    public void get_all_accounts() {
        System.out.println("Starting get_all_accounts...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.param("page", 1);
        request.param("pageSize", 50);

        Response response = request.get("/accounts");

        System.out.println(response.body().asString());
    }

    @Test //GET
    public void get_account_information() {
        System.out.println("Starting get_account_information...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        Response response = request.get("/accounts/{account_number}");

        System.out.println(response.body().asString());
    }

    @Test //POST
    public void create_account() {
        environment.put("serial_number", "QV015164200952AD");
        create_account( environment.get("account_number"),
                        environment.get("serial_number"),
                        Integer.parseInt(environment.get("time_zone")));
    }
    public void create_account(String accountNumber, String serialNumber, Integer timezone) {
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
    }

    @Test //PUT
    public void update_account() {
        String defaultPropertyName = "first_name";
        Object defaultPropertyValue = "EvanII";
        update_account(defaultPropertyName, defaultPropertyValue);
    }
    public void update_account(String propertyName, Object propertyValue) {
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
    }

    @Test //PUT
    public void update_account_timezone() {
        Integer defaultNewTimezone = Integer.parseInt(environment.get("time_zone"));
        update_account_timezone(defaultNewTimezone);
    }
    public void update_account_timezone(Integer newTimeZone) {
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
    }

    @Test //PUT
    public void update_account_status() {
        String newStatus = "active";
        update_account_status(newStatus);
    }
    public void update_account_status(String newAccountStatus) {
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
    }

//    @Test //DELETE
//    //DANGER, DON'T DELETE AN IMPORTANT ACCOUNT
//    public void delete_account() {
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
//    }
}
