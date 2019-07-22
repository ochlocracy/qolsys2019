package APICalls.Misc;

import APICalls.APIEnvironment;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;


public class Release_Version {

    public Map<String, String> environment;

    public Release_Version() {
        init();
    }
    public Release_Version(Map<String, String> environment) {
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
    public void get_iqcloud_version() {
        System.out.println("Starting get_iqcloud_version...");
        RestAssured.baseURI = environment.get("domain_url");
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + environment.get("access_token"));

        Response response = request.get("/version");

        System.out.println(response.body().asString());
    }
}
