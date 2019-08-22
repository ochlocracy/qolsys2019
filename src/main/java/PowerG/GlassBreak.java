package PowerG;

import ADC.ADC;
import ServiceCalls.PanelInfo_ServiceCalls;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GlassBreak extends Setup{

    /* Number of test cases:  10;  Estimate execution time:  11m 30sec*/

    ADC adc = new ADC();
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public GlassBreak() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
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
            }
            Thread.sleep(7000);
        }
    }

    public void default_state() throws IOException, InterruptedException {
        disarmServiceCall();
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(160, 1874, PGSensorsActivity.TAMPERREST);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1871, PGSensorsActivity.TAMPERREST);

    }public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/PowerG_ArmAway.html");
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/PowerG_ArmAway.html", false);
        log = report.startTest(test_case_name);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver();
        servcall.set_ARM_STAY_NO_DELAY_enable();
        servcall.set_AUTO_STAY(0);
    }

    @BeforeMethod
    public void webDriver() throws Exception {
        adc.webDriverSetUp();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void Glass_01() throws Exception {
        add_to_report("Glassbreak_01");
        log.log(LogStatus.INFO, ("*Glassbreak_01* Disarm mode tripping Glass_break group 13, 17 -> Expected result = system stays in Disarm mode"));
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(160, 1874, PGSensorsActivity.GB);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1871, PGSensorsActivity.GB);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        log.log(LogStatus.PASS, ("Pass: System is Disarmed"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: system stays in Disarm mode" + "\n__________________________");
    }
    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void Glass_02() throws Exception {
        add_to_report("Glassbreak_02");
        log.log(LogStatus.INFO, ("*Glassbreak_02* ArmStay mode tripping Glass_break group 13 -> Expected result = Instant Alarm"));
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.SECONDS.sleep(1);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1874, PGSensorsActivity.GB);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'GBreak 160-1874 ')]", "//*[contains(text(), 'Alarm**')]");
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }
    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void Glass_03() throws Exception {
        add_to_report("Glassbreak_03");
        log.log(LogStatus.INFO, ("*Glassbreak_03* ArmStay mode tripping Glass_break group 17 -> Expected result = ArmStay"));

        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.SECONDS.sleep(1);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1871, PGSensorsActivity.GB);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Armed Stay");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: System is Armed Stay"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("System is Armed Stay" + "\n__________________________");
    }
    @Test(priority = 4, retryAnalyzer = RetryAnalizer.class)
    public void Glass_04() throws Exception {
        add_to_report("Glassbreak_04");
        log.log(LogStatus.INFO, ("*Glassbreak_04* ArmAway mode tripping Glass_Break group 13 -> Expected result = Instant Alarm"));

        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.SECONDS.sleep(1);
        ARM_AWAY(ConfigProps.longExitDelay);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1874, PGSensorsActivity.GB);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'GBreak 160-1874 ')]", "//*[contains(text(), 'Alarm**')]");
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }
    @Test(priority = 5, retryAnalyzer = RetryAnalizer.class)
    public void Glass_05() throws Exception {
        add_to_report("Glassbreak_05");
        log.log(LogStatus.INFO, ("*Glassbreak_05* ArmAway mode tripping Glass_break group 17 -> Expected result = Instant Alarm"));
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.SECONDS.sleep(1);
        ARM_AWAY(ConfigProps.longExitDelay);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1871, PGSensorsActivity.GB);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'GBreak 160-1871 ')]", "//*[contains(text(), 'Alarm**')]");
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }
    @Test(priority = 6, retryAnalyzer = RetryAnalizer.class)
    public void Glass_06() throws Exception {
        add_to_report("Glassbreak_06");
        log.log(LogStatus.INFO, ("*Glassbreak_06* Disarm mode tamper glassbreak group 13, 17 -> Expected result = system stays in Disarm mode"));
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(160, 1874, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1871, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        pgprimaryCall(160, 1874, PGSensorsActivity.TAMPERREST);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1871, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is Disarmed"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("System is Disarmed" + "\n__________________________");

    }
    @Test(priority = 7, retryAnalyzer = RetryAnalizer.class)
    public void Glass_07() throws Exception {
        add_to_report("Glassbreak_07");
        log.log(LogStatus.INFO, ("*Glassbreak_07* ArmStay mode tamper glassbreak group 13 -> Expected result = Instant Alarm"));
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.SECONDS.sleep(1);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1874, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'GBreak 160-1874 ')]", "//*[contains(text(), 'Alarm**')]");
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1874, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }
    @Test(priority = 8, retryAnalyzer = RetryAnalizer.class)
    public void Glass_08() throws Exception {
        add_to_report("Glassbreak_08");
        log.log(LogStatus.INFO, ("*Glassbreak_08* ArmStay mode tamper glassbreak group 17 -> Expected result = ArmStay"));

        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }

        TimeUnit.SECONDS.sleep(1);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1871, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Armed Stay");
        DISARM();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1871, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is Armed Stay"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is Armed Stay" + "\n__________________________");
    }
    @Test(priority = 9, retryAnalyzer = RetryAnalizer.class)
    public void Glass_09() throws Exception {
        add_to_report("Glassbreak_09");
        log.log(LogStatus.INFO, ("*Glassbreak_09* ArmAway mode tripping Glass_Break group 13 -> Expected result = Instant Alarm"));
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.SECONDS.sleep(1);
        ARM_AWAY(ConfigProps.longExitDelay);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1874, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'GBreak 160-1874 ')]", "//*[contains(text(), 'Alarm**')]");
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1874, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }
    @Test(priority = 10, retryAnalyzer = RetryAnalizer.class)
    public void Glass_10() throws Exception {
        add_to_report("Glassbreak_10");
        log.log(LogStatus.INFO, ("*Glassbreak_10* ArmAway mode tripping Glass_break group 17 -> Expected result = Instant Alarm"));
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.SECONDS.sleep(1);
        ARM_AWAY(ConfigProps.longExitDelay);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1871, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'GBreak 160-1871 ')]", "//*[contains(text(), 'Alarm**')]");
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(160, 1871, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }

    public void report_tear_down(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = captureScreenshot(driver, result.getName());
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            log.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
    }

    @AfterMethod(alwaysRun = true)
    public void webDriverQuit(ITestResult result) throws IOException {
        report_tear_down(result);
        adc.driver1.quit();
    }
}
