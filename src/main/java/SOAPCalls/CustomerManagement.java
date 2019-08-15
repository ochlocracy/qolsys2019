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
        String defaultCustomerId = "5434143";
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

        XMLBlock input = new XMLBlock("input");
        input.addChild(new XMLBlock("CustomerId", customerId));
        input.addChild(new XMLBlock("UserFirstName", "Tim"));
        input.addChild(new XMLBlock("UserLastName", "Duncan"));
        input.addChild(new XMLBlock("NewUserCode", "2014"));
        input.addChild(new XMLBlock("VoiceAccess", "false"));
        input.addChild(new XMLBlock("PartitionFlags", "1"));
        soapRequest.action.addChild(input);

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_edit_user_code() { edit_user_code(); }
    public Response edit_user_code() {
        String defaultCustomerId = "5434143";
        return edit_user_code(defaultCustomerId);
    }
    public Response edit_user_code(String customerId) {
        System.out.println("Starting edit_user_code...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "AddUserCode";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        XMLBlock input = new XMLBlock("input");
        input.addChild(new XMLBlock("CustomerId", customerId));
        input.addChild(new XMLBlock("PanelUserId", "1"));
        input.addChild(new XMLBlock("UserFirstName", "LeBron"));
        input.addChild(new XMLBlock("UserLastName", "James"));
        input.addChild(new XMLBlock("NewUserCode", "9876"));
        soapRequest.action.addChild(input);

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_generate_login_token() { generate_login_token(); }
    public Response generate_login_token() {
        System.out.println("Starting generate_login_token...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "GenerateLoginToken";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        soapRequest.action.addChild(new XMLBlock("loginName", "qautomation"));

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_get_user_codes() { get_user_codes(); }
    public Response get_user_codes() {
        System.out.println("Starting generate_login_token...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "GetUserCodes";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        soapRequest.action.addChild(new XMLBlock("customerId", "5434143"));

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_add_sensor_device() { add_sensor_device(); }
    public Response add_sensor_device() {
        System.out.println("Starting add_sensor_device...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "AddSensorDevice";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        XMLBlock input = new XMLBlock("input");
        input.addChild(new XMLBlock("CustomerId", "5434143"));
        input.addChild(new XMLBlock("SensorId", "66"));
        input.addChild(new XMLBlock("GroupId", "1"));
        input.addChild(new XMLBlock("SensorTypeId", "2"));
        input.addChild(new XMLBlock("SensorName", "Jeff"));
        input.addChild(new XMLBlock("DLCode", "111111"));

        soapRequest.action.addChild(input);

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_add_video_device() { add_video_device(); }
    public Response add_video_device() {
        System.out.println("Starting add_video_device...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "AddVideoDevice";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        XMLBlock input = new XMLBlock("input");
        input.addChild(new XMLBlock("CustomerId", "5434143"));
        input.addChild(new XMLBlock("VideoDeviceMacAddress", "915375621863"));

        soapRequest.action.addChild(input);

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_delete_sensor_device() { delete_sensor_device(); }
    public Response delete_sensor_device() {
        System.out.println("Starting delete_sensor_device...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "DeleteSensorDevice";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        XMLBlock input = new XMLBlock("input");
        input.addChild(new XMLBlock("CustomerId", "5434143"));
        input.addChild(new XMLBlock("SensorId", "66"));

        soapRequest.action.addChild(input);

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_delete_video_device() { delete_video_device(); }
    public Response delete_video_device() {
        System.out.println("Starting delete_video_device...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "DeleteVideoDevice";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        XMLBlock input = new XMLBlock("input");
        input.addChild(new XMLBlock("CustomerId", "5434143"));
        input.addChild(new XMLBlock("VideoDeviceMacAddress", "915375621863"));

        soapRequest.action.addChild(input);

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_request_zwave_equipment_list() { request_zwave_equipment_list(); }
    public Response request_zwave_equipment_list() {
        System.out.println("Starting request_zwave_equipment_list...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "RequestZWaveEquipmentList";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        soapRequest.action.addChild(new XMLBlock("customerId", "5434143"));

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_set_wifi() { set_wifi(); }
    public Response set_wifi() {
        System.out.println("Starting set_wifi...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "SetWifi";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        XMLBlock input = new XMLBlock("input");
        input.addChild(new XMLBlock("CustomerId", "5434143"));
        input.addChild(new XMLBlock("DeviceId", "12"));
        input.addChild(new XMLBlock("SSID", "Qolsys-playground"));
        input.addChild(new XMLBlock("Password", "QOLSYS-router-access"));
        //None or Wep or Wpa or Wpa2 or Wpa2Enterprise or Wps or WpaWPa2 or Unknown
        input.addChild(new XMLBlock("EncryptionType", "Unknown"));
        //None or Tkip or Aes or Open or Shared or AmbiguousOpenShared or Ccmp or TkipCcmp or WepAlgorithm or TkipAes or Unknown
        input.addChild(new XMLBlock("EncryptionAlgorithm", "Unknown"));


        soapRequest.action.addChild(input);

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }

    @Test
    public void test_update_device_name() { update_device_name(); }
    public Response update_device_name() {
        System.out.println("Starting update_device_name...");

        SOAPRequest soapRequest = new SOAPRequest();
        soapRequest.baseURI = baseURI;
        soapRequest.serviceName = "UpdateDeviceName";
        soapRequest.setUsername("qautomation");
        soapRequest.setPassword("Qolsys123");

        XMLBlock input = new XMLBlock("input");
        input.addChild(new XMLBlock("CustomerId", "5434143"));
        input.addChild(new XMLBlock("DeviceId", "66"));
        input.addChild(new XMLBlock("NewDeviceName", "Geoff"));

        soapRequest.action.addChild(input);

        RequestSpecification request = soapRequest.getRequest();

        Response response = request.post();

        String rString = response.andReturn().asString();
        XmlPath.with(rString).prettyPrint();

        return response;
    }
}
