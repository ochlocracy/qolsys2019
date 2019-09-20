package Settings;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import PanelPages.*;

import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;

public class KeyfobAlarmDisarm extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/KeyfobAlarmDisarm.html");
        log = report.startTest(test_area_name);
    }
    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/KeyfobAlarmDisarm.html", false);
        log = report.startTest(test_case_name);
    }

    public KeyfobAlarmDisarm() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Keyfob_Alarm_Disarm_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);

        create_report("Keyfob_Alarm_01");
        log.log(LogStatus.INFO, ("*Keyfob_Alarm_01* Keyfob emergency Alarm Disarm setting disabled -> Expected result = Keyfob will not disarm the alarm"));
        pgarmer(300, 1004, "01");
        Thread.sleep(2000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        pgarmer(300, 1004, "01");
        Thread.sleep(8000);
        if (emergency.Police_Emergency_Alarmed.isDisplayed()) {
           log.log(LogStatus.PASS, ("Pass: Police Emergency Alarm is displayed"));
        } else {

            log.log(LogStatus.FAIL, ("Failed: Police Emergency Alarm is NOT displayed"));
        }
        enterDefaultUserCode();
        log.log(LogStatus.INFO, ("*Keyfob_Alarm_02* Keyfob emergency Alarm Disarm setting enabled -> Expected result = Keyfob will disarm the alarm"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Alarm_Disarm.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        pgarmer(300, 1004, "01");
        Thread.sleep(2000);
        if (home.Disarmed_text.isDisplayed()) {
           log.log(LogStatus.PASS, ("Pass: Keyfob disarm works"));
        } else {

            log.log(LogStatus.FAIL, ("Failed: Keyfob disarm did not worked"));
        }
        verifySystemState("DISARMED");
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Alarm_Disarm.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = captureScreenshot(driver, result.getName());
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            log.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
        driver.quit();
        service.stop();
    }
}
