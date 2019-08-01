package SOAPCalls;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class SOAPTest {

    @Test
    public void sendRequest() {
        System.out.println("Starting SOAP sendRequest...");

        RestAssured.baseURI = "https://alarmadmin.alarm.com/webservices/CustomerManagement.asmx?WSDL";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "text/xml");

        String myEnvelope =
        "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"> \n" +
            "<Header>\n" +
                "<Authentication xmlns=\"http://www.alarm.com/WebServices\">\n" +
                    "<User>\n" + "qautomation\n" + "</User>\n" +
                    "<Password>Qolsys123</Password>\n" +
                    "<TwoFactorDeviceId>[string?]</TwoFactorDeviceId>\n" +
                "</Authentication>\n" +
            "</Header>\n" +
            "<Body>\n" +
                "<RunSystemCheck xmlns=\"http://www.alarm.com/WebServices\">\n" +
                    "<customerId>3</customerId>\n" +
                    "<category>Panel</category>\n" +
                "</RunSystemCheck>\n" +
            "</Body>\n" +
        "</Envelope>";

        request.body(myEnvelope);

        Response response = request.post();

        String rString = response.andReturn().asString();
        String prettyXML = XmlPath.with(rString).prettyPrint();
        System.out.println(prettyXML);
    }

}
