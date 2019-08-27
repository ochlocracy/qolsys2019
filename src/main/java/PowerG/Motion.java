package PowerG;

import ADC.ADC;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ServiceCalls.PanelInfo_ServiceCalls;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* Number of test cases: 30;  Estimate run time: 66 min*/

public class Motion extends Setup {

    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    ADC adc = new ADC();
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    private String Armed_Stay = "//*[contains(text(), 'Armed Stay')]";
    private String Armed_Away = "//*[contains(text(), 'Armed Away')]";

    public Motion() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/MotionSensor.html");
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/MotionSensor.html", false);
        log = report.startTest(test_case_name);
    }

    public void ADC_verification(String string, String string1) throws InterruptedException, IOException {
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("History")).click();
        Thread.sleep(7000);
        String[] message = {string, string1};
        adc.driver1.navigate().refresh();
        Thread.sleep(7000);
        for (int i = 0; i < message.length; i++) {
            adc.driver1.navigate().refresh();
            try {
                WebElement history_message = adc.driver1.findElement(By.xpath(message[i]));
                Assert.assertTrue(history_message.isDisplayed());
                {
                    System.out.println("Pass: message is displayed " + history_message.getText());
                }
            } catch (Exception e) {
                System.out.println("***No such element found!***");
            } finally {
            }
            Thread.sleep(7000);
        }
    }
    public void ArmStay_Activate(int type, int id, int time, String element_to_verify, String element_to_verify2, String state) throws Exception {
        ARM_STAY();
        TimeUnit.SECONDS.sleep(time);
        pgprimaryCall(type, id, state);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifySystemState("ARMED STAY");
        Thread.sleep(1000);
        DISARM();
        Thread.sleep(2000);
        ADC_verification(element_to_verify, element_to_verify2);
    }
    public void ArmAway_Activate(int type, int id, int time, String element_to_verify, String element_to_verify2, String state) throws Exception {
        ARM_AWAY(time);
        pgprimaryCall(type, id, state);
        TimeUnit.SECONDS.sleep(15);
        verifySystemState("ARMED AWAY");
        Thread.sleep(1000);
        DISARM();
        Thread.sleep(2000);
        ADC_verification(element_to_verify, element_to_verify2);
    }
    public void ArmAway_Activate_Alarm(int type, int id, int time, String element_to_verify, String element_to_verify2, String state) throws Exception {
        ARM_AWAY(time);
        Thread.sleep(1000);
        pgprimaryCall(type, id, state);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        ADC_verification(element_to_verify, element_to_verify2);
    }
    public void ArmStay_Activate_Alarm(int type, int id, int time, String element_to_verify, String element_to_verify2, String state) throws Exception {
        ARM_STAY();
        TimeUnit.SECONDS.sleep(time);
        pgprimaryCall(type, id, state);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        ADC_verification(element_to_verify, element_to_verify2);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver();
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longEntryDelay);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longExitDelay);
        servcall.set_ARM_STAY_NO_DELAY_disable();
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    /*** ARM STAY ***/
    // run time 50 min 15 sec

    @Test
    public void ArmStayDelay_15() throws Exception {
        create_report("Arm Stay Motion sensor group15");
        log.log(LogStatus.INFO, ("ArmStay during delay Activate motion sensor group15"));
        ArmStay_Activate(120, 1411,15/2, "//*[contains(text(), 'Activated')]", Armed_Stay, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Stay, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Stay, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 1)
    public void ArmStay_15() throws Exception {
        add_to_report("Arm Stay Motion sensor group15");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group15"));
        ArmStay_Activate_Alarm(120, 1411, 15, "//*[contains(text(), 'Motion 120-1411 (Sensor 9) Pending Alarm')]","//*[contains(text(), 'Delayed alarm')]" , PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 2)
    public void ArmStay_15_Tamper() throws Exception {
        add_to_report("Arm Stay Motion sensor group15 tamper");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group15 tamper"));
        ArmStay_Activate_Alarm(120, 1411, 15, "//*[contains(text(), 'Motion 120-1411 (Sensor 9) Tamper')]", "//*[contains(text(), 'Sensor 9 Tamper**')]", PGSensorsActivity.TAMPER);
        pgprimaryCall(120, 1411, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 3)
    public void ArmStayDelay_17() throws Exception {
        add_to_report("Arm Stay Motion sensor group17");
        log.log(LogStatus.INFO, ("ArmStay during delay Activate motion sensor group17"));
        ArmStay_Activate(123, 1441, 15 / 2, "//*[contains(text(), 'Activated')]", Armed_Stay, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 4)
    public void ArmStay_17() throws Exception {
        add_to_report("Arm Stay Motion sensor group17");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group17"));
        ArmStay_Activate(123, 1441, 15, "//*[contains(text(), 'Activated')]", Armed_Stay, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 5)
    public void ArmStay_17_Tamper() throws Exception {
        add_to_report("Arm Stay Motion sensor group17 tamper");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group17 tamper"));
        ArmStay_Activate(123, 1441, 15, "//*[contains(text(), 'Sensor 10 Tamper**')]", Armed_Stay, PGSensorsActivity.TAMPER);
        pgprimaryCall(123, 1441, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 6)
    public void ArmStayDelay_20() throws Exception {
        add_to_report("Arm Stay Motion sensor group20");
        log.log(LogStatus.INFO, ("ArmStay during delay Activate motion sensor group20"));
        ArmStay_Activate(122, 1423,15/2, "//*[contains(text(), 'Activated')]", Armed_Stay, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 7)
    public void ArmStay_20() throws Exception {
        add_to_report("Arm Stay Motion sensor group20");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group20"));
        ArmStay_Activate(122, 1423, 15, "//*[contains(text(), 'Motion 122-1423 (Sensor 11) Activated')]",Armed_Stay, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 8)
    public void ArmStay_20_Tamper() throws Exception {
        add_to_report("Arm Stay Motion sensor group20 tamper");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group20 tamper"));
        ArmStay_Activate(122, 1423, 15, "//*[contains(text(), 'Motion 122-1423')]", "//*[contains(text(), 'Sensor 11 Tamper**')]", PGSensorsActivity.TAMPER);
        pgprimaryCall(122, 1423, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is Arm Stay mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is Arm Stay mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 9)
    public void ArmStayDelay_25() throws Exception {
        add_to_report("Arm Stay Motion sensor group25");
        log.log(LogStatus.INFO, ("ArmStay during delay Activate motion sensor group25"));
        ArmStay_Activate(120, 1445, 15 / 2, "//*[contains(text(), 'Activated')]", Armed_Stay, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 10)
    public void ArmStay_25() throws Exception {
        add_to_report("Arm Stay Motion sensor group25");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group25"));
        ArmStay_Activate(120, 1445, 15, "//*[contains(text(), 'Motion 120-1445')]", Armed_Stay, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 11)
    public void ArmStay_25_Tamper() throws Exception {
        add_to_report("Arm Stay Motion sensor group25 tamper");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group25 tamper"));
        ArmStay_Activate(120, 1445, 15, "//*[contains(text(), 'Sensor 12 Tamper**')]", Armed_Stay, PGSensorsActivity.TAMPER);
        pgprimaryCall(120, 1445, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Stay, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Stay, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 12)
    public void ArmStayDelay_35() throws Exception {
        add_to_report("Arm Stay Motion sensor group35");
        log.log(LogStatus.INFO, ("ArmStay during delay Activate motion sensor group35"));
        ArmStay_Activate(123, 1446, 15/2, "//*[contains(text(), 'Motion 123-1446')]",Armed_Stay, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 13)
    public void ArmStay_35() throws Exception {
        add_to_report("Arm Stay Motion sensor group35");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group35"));
        ArmStay_Activate_Alarm(123, 1446, 15, "//*[contains(text(), 'Entry delay on sensor 13')]", "//*[contains(text(), 'Pending Alarm')]", PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 14)
    public void ArmStay_35_Tamper() throws Exception {
        add_to_report("Arm Stay Motion sensor group35 tamper");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group35 tamper"));
        ArmStay_Activate_Alarm(123, 1446, 15, "//*[contains(text(), 'Sensor 13 Tamper**')]", "//*[contains(text(), 'Pending Alarm')]", PGSensorsActivity.TAMPER);
        pgprimaryCall(123, 1446, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    /*** ARM AWAY ***/

    @Test(priority = 15)
    public void ArmAwayDelay_15() throws Exception {
        servcall.set_AUTO_STAY(0);
        add_to_report("Arm Away Motion sensor group15");
        log.log(LogStatus.INFO, ("ArmAway during delay Activate motion sensor group15"));
        ArmAway_Activate(120, 1411,15/2, "//*[contains(text(), 'Activated')]", Armed_Away, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Away, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Away, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 16)
    public void ArmAway_15() throws Exception {
        servcall.set_AUTO_STAY(0);
        add_to_report("Arm Away Motion sensor group15");
        log.log(LogStatus.INFO, ("ArmAway Activate motion sensor group15"));
        ArmAway_Activate_Alarm(120, 1411, 15, "//*[contains(text(), 'Motion 120-1411 (Sensor 9) Pending Alarm')]","//*[contains(text(), 'Delayed alarm')]" , PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 17)
    public void ArmAway_15_Tamper() throws Exception {
        servcall.set_AUTO_STAY(0);
        add_to_report("Arm Away Motion sensor group15 tamper");
        log.log(LogStatus.INFO, ("ArmAway Activate motion sensor group15 tamper"));
        ArmAway_Activate_Alarm(120, 1411, 15, "//*[contains(text(), 'Motion 120-1411')]", "//*[contains(text(), 'Sensor 9 Tamper**')]", PGSensorsActivity.TAMPER);
        pgprimaryCall(120, 1411, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 18)
    public void ArmAwayDelay_17() throws Exception {
        add_to_report("Arm Away Motion sensor group17");
        log.log(LogStatus.INFO, ("ArmAway during delay Activate motion sensor group17"));
        ArmAway_Activate(123, 1441,15/2, "//*[contains(text(), 'Activated')]", Armed_Away, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Away, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Away, events are displayed correctly on the ADC dealer website" + "\n__________________________");

    }
    @Test(priority = 19)
    public void ArmAway_17() throws Exception {
        add_to_report("Arm Away Motion sensor group17");
        log.log(LogStatus.INFO, ("ArmAway Activate motion sensor group17"));
        ArmAway_Activate_Alarm(123, 1441, 15, "//*[contains(text(), 'Pending Alarm')]","//*[contains(text(), 'Delayed alarm')]" , PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");

    }
    @Test(priority = 20)
    public void ArmAway_17_Tamper() throws Exception {
        add_to_report("Arm Away Motion sensor group17 tamper");
        log.log(LogStatus.INFO, ("ArmAway Activate motion sensor group17 tamper"));
        ArmAway_Activate_Alarm(123, 1441, 15, "//*[contains(text(), 'Motion 123-1441')]", "//*[contains(text(), 'Sensor 10 Tamper**')]", PGSensorsActivity.TAMPER);
        pgprimaryCall(123, 1441, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 21)
    public void ArmAwayDelay_20() throws Exception {
        add_to_report("Arm Away Motion sensor group20");
        log.log(LogStatus.INFO, ("ArmAway during delay Activate motion sensor group20"));
        ArmAway_Activate(122, 1423,15/2, "//*[contains(text(), 'Activated')]", Armed_Away, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Away mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Away mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 22)
    public void ArmAway_20() throws Exception {
        add_to_report("Arm Away Motion sensor group20");
        log.log(LogStatus.INFO, ("ArmAway Activate motion sensor group20"));
        ArmAway_Activate_Alarm(122, 1423, 15, "//*[contains(text(), 'Pending Alarm')]","//*[contains(text(), 'Delayed alarm')]", PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 23)
    public void ArmAway_20_Tamper() throws Exception {
        add_to_report("Arm Away Motion sensor group20 tamper");
        log.log(LogStatus.INFO, ("ArmAway Activate motion sensor group20 tamper"));
        ArmAway_Activate_Alarm(122, 1423, 15, "//*[contains(text(), 'Motion 122-1423')]", "//*[contains(text(), 'Sensor 11 Tamper**')]", PGSensorsActivity.TAMPER);
        pgprimaryCall(122, 1423, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is Arm Away mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is Arm Away mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 24)
    public void ArmAwayDelay_25() throws Exception {
        add_to_report("Arm Away Motion sensor group25");
        log.log(LogStatus.INFO, ("ArmAway during delay Activate motion sensor group25"));
        ArmAway_Activate(120, 1445, 15 / 2, "//*[contains(text(), 'Activated')]", Armed_Away, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Away mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Away mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 25)
    public void ArmAway_25() throws Exception {
        add_to_report("Arm Away Motion sensor group25");
        log.log(LogStatus.INFO, ("ArmAway Activate motion sensor group25"));
        ArmAway_Activate(120, 1445, 15, "//*[contains(text(), 'Motion 120-1445')]", Armed_Away, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Away mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Away mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 26)
    public void ArmAway_25_Tamper() throws Exception {
        add_to_report("Arm Away Motion sensor group25 tamper");
        log.log(LogStatus.INFO, ("ArmAway Activate motion sensor group25 tamper"));
        ArmAway_Activate(120, 1445, 15, "//*[contains(text(), 'Sensor 12 Tamper**')]", Armed_Away, PGSensorsActivity.TAMPER);
        pgprimaryCall(120, 1445, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Away, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Away mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 27)
    public void ArmAwayDelay_35() throws Exception {
        add_to_report("Arm Away Motion sensor group35");
        log.log(LogStatus.INFO, ("ArmAway during delay Activate motion sensor group35"));
        ArmAway_Activate(123, 1446, 15/2, "//*[contains(text(), 'Motion 123-1446')]",Armed_Away, PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Arm Away mode, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Arm Away mode, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 28)
    public void ArmAway_35() throws Exception {
        add_to_report("Arm Stay Motion sensor group35");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group35"));
        ArmAway_Activate_Alarm(123, 1446, 15, "//*[contains(text(), 'Entry delay on sensor 13')]", "//*[contains(text(), 'Pending Alarm')]", PGSensorsActivity.MOTIONACTIVE);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }
    @Test(priority = 29)
    public void ArmAway_35_Tamper() throws Exception {
        add_to_report("Arm Stay Motion sensor group35 tamper");
        log.log(LogStatus.INFO, ("ArmStay Activate motion sensor group35 tamper"));
        ArmAway_Activate_Alarm(123, 1446, 15, "//*[contains(text(), 'Sensor 13 Tamper**')]", "//*[contains(text(), 'Pending Alarm')]", PGSensorsActivity.TAMPER);
        pgprimaryCall(123, 1446, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        adc.driver1.quit();
        service.stop();
    }
    @AfterMethod
    public void webDriverQuit(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
        System.out.println("close webpage");
        adc.driver1.close();
    }
}
