package APICalls;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Account {

    private Map<String, String> environment;

    @BeforeTest
    public void init() {
        environment = APIEnvironment.getEnvironment();
    }

    @Test
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

    @Test
    public void get_account_information() {
        System.out.println("Starting get_account_information...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));

        request.pathParam("account_number", environment.get("account_number"));

        request.param("access_token", environment.get("access_token"));

        Response response = request.get("/accounts/{account_number}");

        System.out.println(response.body().asString());
    }

    @Test
    public void create_account() {
        System.out.println("Starting create_account...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        //To make a new account put in unique values, don't take from the environment
        environment.put("serial_number", "QV015164200952AD");

        JSONObject requestBody = new JSONObject();
        requestBody.put("account_number", environment.get("account_number"));
        requestBody.put("serial_number", environment.get("serial_number"));
        requestBody.put("time_zone", Integer.parseInt(environment.get("time_zone")));

        request.body(requestBody.toString());

        Response response = request.post("/accounts");

        System.out.println(response.body().asString());
    }

    @Test
    public void update_account() {
        System.out.println("Starting update_account...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");

        request.pathParam("account_number", environment.get("account_number"));

        JSONObject requestBody = new JSONObject();
        //insert property name and value of choice
        String propertyName = "first_name", propertyValue = "Evan2";
        requestBody.put(propertyName, propertyValue);
        request.body(requestBody.toString());

        Response response = request.put("/accounts/{account_number}");

        System.out.println(response.body().asString());
    }

    @Test
    public void update_account_timezone() {
        System.out.println("Starting update_account_timezone...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");


        request.pathParam("account_number", environment.get("account_number"));

        JSONObject requestBody = new JSONObject();
        //to change timezone, make this a new unique number
        Integer newTimeZone = Integer.parseInt(environment.get("time_zone"));
        requestBody.put("timezone_id", newTimeZone);
        request.body(requestBody.toString());

        Response response = request.put("/accounts/{account_number}/timezone");

        System.out.println(response.body().asString());
    }

    @Test
    public void update_account_status() {
        System.out.println("Starting update_account_status...");

        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + environment.get("access_token"));
        request.header("Content-Type", "application/json");


        request.pathParam("account_number", environment.get("account_number"));

        JSONObject requestBody = new JSONObject();
        //to change status make pick a new status, either active, maintenance, unlink, or revive
        String newAccountStatus = "maintenance";
        requestBody.put("account_status", newAccountStatus);
        request.body(requestBody.toString());

        Response response = request.put("/accounts/{account_number}/status");

        System.out.println(response.body().asString());
    }

//    @Test
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
