package SOAPCalls;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class CustomerManagement {

    private static final String baseURI = "https://alarmadmin.alarm.com/webservices/CustomerManagement.asmx?WSDL";

    @Test
    public void test_run_system_check() { run_system_check(); }
    public Response run_system_check() {
        String defualtCustomerId = "5434143";
        String defaultCategory = "Panel";
        return run_system_check(defualtCustomerId, defaultCategory);
    }
    public Response run_system_check(String customerId, String category) {
        System.out.println("Starting run_system_check...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "RunSystemCheck";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        soapRequest.action.addChild(new XMLBlock("customerId", customerId));
        soapRequest.action.addChild(new XMLBlock("category", category));

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_email_welcome_letter() { email_welcome_letter(); }
    public Response email_welcome_letter() {
        String defaultCustomerId = "3";
        return email_welcome_letter(defaultCustomerId);
    }
    public Response email_welcome_letter(String customerId) {
        System.out.println("Starting email_welcome_letter...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "EmailWelcomeLetter";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        soapRequest.action.addChild(new XMLBlock("customerId", customerId));

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_get_uploaded_panel_settings() { get_uploaded_panel_settings(); }
    public Response get_uploaded_panel_settings() {
        String defaultCustomerId = "5434143";
        return get_uploaded_panel_settings(defaultCustomerId);
    }
    public Response get_uploaded_panel_settings(String customerId) {
        System.out.println("Starting get_uploaded_panel_settings...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "GetUploadedPanelSettings";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        soapRequest.action.addChild(new XMLBlock("customerId", customerId));

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_add_user_code() { add_user_code(); }
    public Response add_user_code() {
        String defaultCustomerId = "5434143";
        return add_user_code(defaultCustomerId);
    }
    public Response add_user_code(String customerId) {
        System.out.println("Starting add_user_code...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "AddUserCode";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        soapRequest.action.addChild(new XMLBlock("CustomerId", customerId));
        soapRequest.action.addChild(new XMLBlock("UserFirstName", "Kobe"));
        soapRequest.action.addChild(new XMLBlock("UserLastName", "Bryant"));
        soapRequest.action.addChild(new XMLBlock("NewUserCode", "6789"));

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

}
