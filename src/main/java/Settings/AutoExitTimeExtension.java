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

public class AutoExitTimeExtension extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public AutoExitTimeExtension() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/AutoExitTimeExtension.html");
        log = report.startTest(test_area_name);
    }
    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Exit_Time_Extension_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        create_report("Auto_Exit_Time_Extension_01");
        log.log(LogStatus.INFO, ("*Auto_Exit_Time_Extension_01* Enable Auto Exit Time Extension -> Expected result = System extends timer 'til armed when sensor is opened"));
        Thread.sleep(2000);
        Thread.sleep(2000);
        ARM_AWAY(3);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(15000);
        try {
            if (home.ArwAway_State.isDisplayed())
                log.log(LogStatus.FAIL, ("Failed: System is ARMED AWAY"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: System is NOT ARMED AWAY"));
        } finally {
        }
        Thread.sleep(60000);
        verifySystemState("ARMED AWAY");
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        log.log(LogStatus.INFO, ("*Auto_Exit_Time_Extension_02* Enable Auto Exit Time Extension -> Expected result = System does not extend timer 'til armed when sensor is opened"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
        swipeVertical();
        arming.Auto_Exit_Time_Extension.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        ARM_AWAY(3);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(10000);
        verifySystemState("ARMED AWAY");
        try {
            if (home.ArwAway_State.isDisplayed())
            log.log(LogStatus.PASS, ("Pass: System is ARMED AWAY"));
        } catch (Exception e) {
            log.log(LogStatus.FAIL, ("Failed: System is NOT ARMED AWAY"));
        } finally {
        }
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
        swipeVertical();
        arming.Auto_Exit_Time_Extension.click();
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
