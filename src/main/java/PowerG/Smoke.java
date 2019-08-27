package PowerG;

import ADC.ADC;
import ServiceCalls.PanelInfo_ServiceCalls;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import PanelPages.EmergencyPage;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;

/* Number of test cases:  6;  Estimate run time: 12m 15sec */

public class Smoke extends Setup {
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    ADC adc = new ADC();
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public Smoke() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/Smoke.html");
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/Smoke.html", false);
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

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver();
        servcall.set_AUTO_STAY(0);
        servcall.set_ARM_STAY_NO_DELAY_disable();
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    public void ArmStay_Activate_Restore_Tamper(int time, String element_to_verify1, String element_to_verify2, String state, String state1) throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        ARM_STAY();
        Thread.sleep(time);
        pgprimaryCall(201, 1541, state);
        Thread.sleep(5000);
        pgprimaryCall(201, 1541, state1);
        Thread.sleep(15000);
        if (state == PGSensorsActivity.TAMPER) {
            verifySystemState("ARMED STAY");
            Thread.sleep(1000);
            DISARM();
        } else if(state == PGSensorsActivity.SMOKE){
            elementVerification(emg.Alarm_verification, "Fire Emergency");
            enterDefaultUserCode();
        }
        Thread.sleep(2000);
        ADC_verification(element_to_verify1, element_to_verify2);
    }

    /*** Arm Stay Activate -Tamper ***/
    @Test
    public void ArmStayExitDelay_26() throws Exception {
        create_report("ArmStay Smoke sensor");
        log.log(LogStatus.INFO, ("ArmStay - Activate/Restore smoke sensor during exit delay"));
        System.out.println("ArmStay - Activate/Restore smoke sensor during exit delay");
        ArmStay_Activate_Restore_Tamper(1500/2, "//*[contains(text(), 'Sensor 14 Alarm**')]", "//*[contains(text(), 'Fire Alarm')]", PGSensorsActivity.SMOKE, PGSensorsActivity.SMOKEREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 1)
    public void ArmStay_26() throws Exception {
        add_to_report("ArmStay Smoke sensor");
        log.log(LogStatus.INFO, ("ArmStay - Activate/Restore smoke sensor"));
        System.out.println("ArmStay - Activate/Restore smoke sensor");
        ArmStay_Activate_Restore_Tamper(1500, "//*[contains(text(), 'Sensor 14 Alarm**')]", "//*[contains(text(), 'Fire Alarm')]", PGSensorsActivity.SMOKE, PGSensorsActivity.SMOKEREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 2)
    public void ArmStay_26_Tamper() throws Exception {
        add_to_report("ArmStay Smoke sensor tamper");
        log.log(LogStatus.INFO, ("ArmStay - Tamper smoke sensor"));
        System.out.println("ArmStay - Tamper smoke sensor");
        ArmStay_Activate_Restore_Tamper(1500, "//*[contains(text(), 'Sensor 14 Tamper**')]", "//*[contains(text(), 'End of Tamper')]", PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }


    /*** Arm Away Activate -Tamper ***/
    public void ArmAway_Activate_Restore_Tamper(int time, String element_to_verify1, String element_to_verify2, String state, String state1) throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        ARM_AWAY(ConfigProps.longExitDelay + 2);
        Thread.sleep(time);
        pgprimaryCall(201, 1541, state);
        Thread.sleep(5000);
        pgprimaryCall(201, 1541, state1);
        Thread.sleep(15000);
        if (state == PGSensorsActivity.TAMPER){
            verifyInAlarm();
            enterDefaultUserCode();
        }else if (state == PGSensorsActivity.SMOKE) {
            elementVerification(emg.Alarm_verification, "Fire Emergency");
            enterDefaultUserCode();
        }
        Thread.sleep(2000);
        ADC_verification(element_to_verify1, element_to_verify2);
    }

    @Test(priority = 3)
    public void ArmAwayExitDelay_26() throws Exception {
        add_to_report("ArmAway Smoke sensor");
        log.log(LogStatus.INFO, ("ArmAway -Activate smoke sensor during exit delay"));
        System.out.println("ArmAway -Activate smoke sensor during exit delay");
        ArmAway_Activate_Restore_Tamper(1500/2, "//*[contains(text(), 'Sensor 14 Alarm**')]", "//*[contains(text(), 'Fire Alarm')]", PGSensorsActivity.SMOKE, PGSensorsActivity.SMOKEREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 4)
    public void ArmAway_26() throws Exception {
        add_to_report("ArmAway Smoke sensor");
        log.log(LogStatus.INFO, ("ArmAway -Activate smoke sensor"));
        System.out.println("ArmAway -Activate smoke sensor");
        ArmAway_Activate_Restore_Tamper(1500, "//*[contains(text(), 'Sensor 14 Alarm**')]", "//*[contains(text(), 'Fire Alarm')]", PGSensorsActivity.SMOKE, PGSensorsActivity.SMOKEREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @Test(priority = 5)
    public void ArmAway_26_Tamper() throws Exception {
        add_to_report("ArmAway Smoke sensor tamper");
        log.log(LogStatus.INFO, ("ArmAway -Tamper smoke sensor"));
        ArmAway_Activate_Restore_Tamper(1500, "//*[contains(text(), '(Sensor 14) Tamper')]", "//*[contains(text(), 'Smoke 201-1541 (Sensor 14) ')]", PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
        System.out.println("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website" + "\n__________________________");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }

    @AfterMethod
    public void webDriverQuit(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = captureScreenshot(driver, result.getName());
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            log.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
        adc.driver1.quit();
    }
}