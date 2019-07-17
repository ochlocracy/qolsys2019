package SanityTests;

import PanelPages.ContactUs;
import PanelPages.HomePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigProps;
import utils.RetryAnalizer;
import utils.SensorsActivity;
import utils.Setup;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class PreUpdateSensors extends Setup {

    public PreUpdateSensors() throws Exception {
        SensorsActivity.init();
    }

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver();
        preconditions();
    }

    @Test(priority = 1)
    public void addSensors() throws IOException, InterruptedException {

        addPrimaryCall(3, 10, 6619296, 1); // 65 00 A0 //dw's
        addPrimaryCall(4, 12, 6619297, 1); // 65 00 A1
        addPrimaryCall(5, 13, 6619298, 1); // 65 00 A2
        addPrimaryCall(6, 14, 6619299, 1); // 65 00 A3
        addPrimaryCall(7, 16, 6619300, 1); // 65 00 A4
        addPrimaryCall(8, 8, 6619301, 1); // 65 00 A5
        addPrimaryCall(9, 9, 6619302, 1); // 65 00 A6
        addPrimaryCall(10, 25, 6619303, 1); // 65 00 A7
        Thread.sleep(1000);
        addPrimaryCall(11, 15, 5570628, 2); // 55 00 44 //motion's
        addPrimaryCall(12, 17, 5570629, 2); // 55 00 45
        addPrimaryCall(13, 20, 5570630, 2); // 55 00 46
        addPrimaryCall(14, 25, 5570631, 2); // 55 00 47
        addPrimaryCall(15, 35, 5570632, 2); // 55 00 48
        Thread.sleep(1000);
        addPrimaryCall(16, 26, 6750242, 5); // 67 00 22 //smoke
        addPrimaryCall(17, 34, 7667882, 6); // 75 00 AA //co
        addPrimaryCall(18, 13, 6750361, 19); // 67 00 99 //glass
        addPrimaryCall(19, 17, 6750355, 19); // 67 00 93 //glass
        Thread.sleep(1000);
        addPrimaryCall(20, 10, 6488238, 16); // 63 00 AE //tilt
        addPrimaryCall(21, 12, 6488239, 16); // 63 00 AF //tilt
        addPrimaryCall(22, 25, 6488224, 16); // 63 00 A0 //tilt
        addPrimaryCall(23, 13, 6684828, 107); // 66 00 9C //shock
        addPrimaryCall(24, 17, 6684829, 107); // 66 00 9D //shock
        addPrimaryCall(25, 52, 7536801, 17); // 73 00 A1 //freeze
        Thread.sleep(1000);
        addPrimaryCall(26, 26, 7667810, 111); // 75 00 62 //heat
        addPrimaryCall(27, 38, 7672224, 22); // 75 11 A0 //water
        addPrimaryCall(28, 1, 6619386, 102); // 65 00 FA //keyfob
        addPrimaryCall(29, 6, 6619387, 102); // 65 00 FB //keyfob
        addPrimaryCall(30, 4, 6619388, 102); // 65 00 FC //keyfob
        addPrimaryCall(31, 0, 8716538, 104); // 85 00 FA //keypad
        addPrimaryCall(32, 2, 8716539, 104); // 85 00 FB //keypad
        Thread.sleep(1000);
        addPrimaryCall(33, 25, 6405546, 109); // 61 BD AA //doorbell
        addPrimaryCall(34, 6, 6361649, 21); // 61 12 31 // medical
        addPrimaryCall(35, 0, 6361650, 21); // 61 12 32 //medical
    }


    @Test(priority =2)
    public void sensorsCheck() throws Exception {
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);

        String element_id = "com.qolsys:id/t3_home_tv_SensorName";
        String alarm = "com.qolsys:id/txt_title";

        System.out.println("Open-Close contact sensors");
        sensor_activity_verification("65 00 0A", 2, element_id, "Door-Window 3");
        sensor_activity_verification("65 00 1A", 2, element_id, "Door-Window 4");
        sensor_activity_verification("65 00 2A", 2, element_id, "Door-Window 5");
        sensor_activity_verification("65 00 3A", 2, element_id, "Door-Window 6");
        sensor_activity_verification("65 00 4A", 2, element_id, "Door-Window 7");
        sensor_activity_verification("65 00 5A", 2, alarm, "AL ARM");
        enterDefaultUserCode();
        Thread.sleep(2000);
        sensor_activity_verification("65 00 6A", 14, alarm, "AL ARM");
        enterDefaultUserCode();
        Thread.sleep(2000);
        sensor_activity_verification("65 00 7A", 2, element_id, "Door-Window 10");

        System.out.println("Activate motion sensors");
        primaryCall("55 00 44", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        primaryCall("55 00 54", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        primaryCall("55 00 64", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        primaryCall("55 00 74", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        primaryCall("55 00 84", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);

        System.out.println("Activate smoke sensors");
        primaryCall("67 00 22", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        alarm_verification();
        Thread.sleep(2000);
        primaryCall("67 00 22", SensorsActivity.RESTORE);

        System.out.println("Activate CO sensors");
        primaryCall("75 00 AA", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        alarm_verification();
        Thread.sleep(2000);

        System.out.println("Activate glassbreak sensors");
        primaryCall("67 00 99", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        primaryCall("67 00 99", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        primaryCall("67 00 39", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        primaryCall("67 00 39", SensorsActivity.RESTORE);
        Thread.sleep(1000);

        System.out.println("Open/close tilt sensors");
        sensor_activity_verification("63 00 EA", 2, element_id, "Tilt 20");
        sensor_activity_verification("63 00 FA", 2, element_id, "Tilt 21");
        sensor_activity_verification("63 00 0A", 2, element_id, "Tilt 22");

        System.out.println("Activate IQShock sensors");
        primaryCall("66 00 C9", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        primaryCall("66 00 C9", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        primaryCall("66 00 D9", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        primaryCall("66 00 D9", SensorsActivity.RESTORE);
        Thread.sleep(1000);

        System.out.println("Activate freeze sensors");
        primaryCall("73 00 1A", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        primaryCall("73 00 1A", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        alarm_verification();
        Thread.sleep(1000);

        System.out.println("Activate heat sensors");
        primaryCall("75 00 26", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        alarm_verification();
        Thread.sleep(1000);
        primaryCall("75 00 26", SensorsActivity.RESTORE);

        System.out.println("Activate water sensors");
        primaryCall("75 11 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        alarm_verification();
        Thread.sleep(1000);
        primaryCall("75 11 0A", SensorsActivity.RESTORE);

        System.out.println("Activate keyfob sensors");
        primaryCall("65 00 AF", SensorsActivity.OPEN);
        Thread.sleep(1000);
        alarm_verification();
        Thread.sleep(1000);
        primaryCall("65 00 AF", SensorsActivity.RESTORE);

        primaryCall("65 00 BF", SensorsActivity.OPEN);
        Thread.sleep(1000);
        alarm_verification();
        Thread.sleep(1000);
        primaryCall("65 00 BF", SensorsActivity.RESTORE);

        primaryCall("65 00 CF", SensorsActivity.OPEN);
        Thread.sleep(1000);
        alarm_verification();
        Thread.sleep(1000);
        primaryCall("65 00 CF", SensorsActivity.RESTORE);

        System.out.println("Activate keypad sensors");
        primaryCall("85 00 AF", SensorsActivity.OPEN);
        Thread.sleep(1000);
        alarm_verification();
        Thread.sleep(1000);
        primaryCall("85 00 AF", SensorsActivity.RESTORE);

        primaryCall("85 00 BF", "04 04");
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifySystemState("ARMED AWAY");
        primaryCall("85 00 BF", "08 01");
        Thread.sleep(1000);

        System.out.println("Activate medical pendant sensors");
        primaryCall("61 12 13", "03 01");
        Thread.sleep(2000);
        alarm_verification();
        Thread.sleep(1000);

        primaryCall("61 12 23", "03 01");
        Thread.sleep(1000);
        alarm_verification();
        Thread.sleep(1000);
        primaryCall("61 12 23", "04 01");

        System.out.println("Activate doorbell sensors");
        primaryCall("61 BD AA", SensorsActivity.OPEN);
        Thread.sleep(1000);
        primaryCall("61 BD AA", SensorsActivity.CLOSE);

        contact.acknowledge_all_alerts();
        swipeLeft();
        Thread.sleep(1000);
    }

    @AfterClass
    public void tearDown() throws Exception {
        quitDriver();
    }

    public void sensor_activity_verification(String DLID, int sleep_time, String element, String name) throws Exception {
        primaryCall(DLID, SensorsActivity.OPEN);
        TimeUnit.SECONDS.sleep(sleep_time);
        elementVerification(element, name);
        primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(2000);
    }
    public void alarm_verification(){
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        try{
            if(home.ALARM.isDisplayed()){
                System.out.println("Pass: Alarm is displayed!");
                enterDefaultUserCode();
            }else{
                System.out.println("FAIL: No ALARM event!");
                System.exit(0);
            }
        }catch (NoSuchElementException e){}
    }
}
