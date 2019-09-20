package Settings;

import ServiceCalls.PanelInfo_ServiceCalls;
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

public class AutoBypass extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();

    public AutoBypass() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/AutoBypass.html");
        log = report.startTest(test_area_name);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Auto_Bypass_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        serv.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        create_report("Auto_Bypass");
        log.log(LogStatus.INFO, ("*Auto_Bypass_01* Enable Auto Bypass -> Expected result = Sensors can be opened and closed without giving bypass pop-up message"));
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(3000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(3000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        verifySystemState("ARMED STAY");
        log.log(LogStatus.PASS, ("Pass: Bypass pop-up message is not present"));
        Thread.sleep(1000);
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(3000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        swipeVertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(3000);
        settings.Home_button.click();
        Thread.sleep(3000);
        log.log(LogStatus.INFO, ("*Auto_Bypass_02* Disable Auto Bypass -> Expected result = Sensors are opened and closed and give a bypass pop-up message"));
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        home.DISARM.click();
        Thread.sleep(2000);
        home.ARM_STAY.click();
        Thread.sleep(2000);
        elementVerification(home.Bypass_message, "Bypass pop-up message");
        log.log(LogStatus.PASS, ("Pass: Bypass pop-up message is present"));
        Thread.sleep(2000);
        home.Bypass_OK.click();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        verifySystemState("ARMED STAY");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
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