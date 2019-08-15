package APICalls;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

public class FullTests {

    public FullTests() { super(); }

    @Test
    public void test_login_and_refresh() {
        Authentication authentication = new Authentication();
        Response response = authentication.generate_access_token();
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Login Successful");
        try { sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        response = authentication.refresh_access_token();
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Refresh Successful");
    }

    @Test
    public void is_panel_connected() {
        IQ_Panel iq_panel = new IQ_Panel();
        Response response = iq_panel.get_iqpanel_information();
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Status Code is 200");
        Assert.assertEquals(response.jsonPath().get("connection_status"), "connected");
        System.out.println("Panel is connected");
    }

    @Test
    public void is_account_active() {
        Account account = new Account();
        Response response = account.get_account_information();
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Status Code is 200");
        Assert.assertEquals(response.jsonPath().get("account_status"), "active");
        System.out.println("Account is active");
    }

//    @Test
//    public void is_account_active_and_panel_connected() {
//        Account account = new Account();
//        IQ_Panel iq_panel = new IQ_Panel();
//        Response responseA = account.get_account_information();
//        Response responseP = iq_panel.get_iqpanel_information();
//        Assert.assertEquals(responseA.getStatusCode(), 200);
//        Assert.assertEquals(responseA.jsonPath().get("account_status"), "active");
//        System.out.println("Account is active");
//        Assert.assertEquals(responseA.jsonPath().get("connection_status"), "connected");
//        Assert.assertEquals(responseP.getStatusCode(), 200);
//        Assert.assertEquals(responseP.jsonPath().get("connection_status"), "connected");
//        System.out.println("Panel is connected");
//    }

    @Test
    //Panel should start disarmed
    public void check_and_change_arm_status() {
        IQ_Panel iq_panel = new IQ_Panel();
        Response response = iq_panel.get_iqpanels_partition_arm_status();
        Assert.assertEquals(response.getStatusCode(), 200);
        String armStatus = response.jsonPath().get("arming_status");
        System.out.println("Got arming status. Arming status is " + armStatus);
        if (!armStatus.equals("disarm")) {
            System.out.println("Panel should start disarmed");
            Assert.assertEquals("disarm", armStatus);
        }
        try { sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        response = iq_panel.set_iqpanels_partition_arm_status("arm_stay");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().get("request_status"), true);
        System.out.println("Set arming status to arm_stay");
        try { sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        response = iq_panel.get_iqpanels_partition_arm_status();
        Assert.assertEquals(response.getStatusCode(), 200);
        armStatus = response.jsonPath().get("arming_status");
        System.out.println("Got arming status. Arming status is " + armStatus);
        Assert.assertEquals("arm_stay", response.jsonPath().get("arming_status"));
    }

    @Test
    public void add_update_delete_sensor() {
        Sensor sensor = new Sensor();
        Response response = sensor.add_sensor();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().get("request_status"), true);
        try { sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        response = sensor.get_sensor(sensor.environment.get("srf_sensor_id"));
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().get("sensor_id"), "A12343");
        try { sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        response = sensor.get_sensor_property("sensor_name");
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Got sensor name: " + response.jsonPath().get("sensor_name"));
        try { sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        response = sensor.update_sensor("sensor_name", "Jeffrey");
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Set sensor name to Jeffrey");
        try { sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        response = sensor.get_sensor_property("sensor_name");
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Got sensor name: " + response.jsonPath().get("sensor_name"));
        Assert.assertEquals("Jeffrey", response.jsonPath().get("sensor_name"));
        try { sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        response = sensor.delete_sensor(Integer.parseInt(
                sensor.environment.get("srf_sensor_id")));
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("Deleted sensor");
    }

    

}