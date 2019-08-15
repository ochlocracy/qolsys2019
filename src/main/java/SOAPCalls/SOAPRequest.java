package SOAPCalls;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class SOAPRequest {

    private String username;
    private String password;
    public String serviceName;
    public String baseURI;
    private XMLBlock envelope;
    private XMLBlock header;
    private XMLBlock body;
    public XMLBlock action;

    public SOAPRequest() { this(null, null, null, null);}
    public SOAPRequest(String username, String password) {
        this(null, null, username, password);
    }
    public SOAPRequest(String baseURI, String serviceName, String username, String password) {
        super();
        this.username = username;
        this.password = password;
        this.serviceName = serviceName;
        this.baseURI = baseURI;
        action = new XMLBlock(this.serviceName, null,
                "http://www.alarm.com/WebServices");
    }

    private void initBlocks() {
        envelope = new XMLBlock("Envelope", null,
                "http://schemas.xmlsoap.org/soap/envelope/");
        header = new XMLBlock("Header");
        XMLBlock authentication = new XMLBlock("Authentication", null,
                "http://www.alarm.com/WebServices");
        authentication.addChild(new XMLBlock("User", username));
        authentication.addChild(new XMLBlock("Password", password));
        body = new XMLBlock("Body");
        action.setTitle(serviceName);
        header.addChild(authentication);
        body.addChild(action);
        envelope.addChild(header);
        envelope.addChild(body);

    }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    public RequestSpecification getRequest() {
        RestAssured.baseURI = "https://alarmadmin.alarm.com/webservices/CustomerManagement.asmx?WSDL";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "text/xml");
        initBlocks();
        request.body(envelope.toString());
        System.out.println(envelope.toString());
        return request;
    }
}