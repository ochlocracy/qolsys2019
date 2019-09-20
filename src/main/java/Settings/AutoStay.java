package Settings;

import PanelPages.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;

public class AutoStay extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public AutoStay() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/AutoStay.html");
        log = report.startTest(test_area_name);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Auto_Stay_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        create_report("Auto_Stay_01");
        log.log(LogStatus.INFO, ("*Auto_Stay_01* Enable Auto Stay -> Expected result = System goes into Arm Stay when Arm Away is clicked"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay + 2);
        try {
            if (home.ArwAway_State.getText().equals("ARMED AWAY")) {
                log.log(LogStatus.FAIL, ("Failed: System is ARMED AWAY"));
                System.out.println("Failed: System is ARMED AWAY");
            } else {
                System.out.println("Pass: System is in ARMED Stay");
                log.log(LogStatus.PASS, ("Pass: System is in ARMED Stay"));
            }
        } catch (Exception e) {
        }
        home.DISARM.click();
        enterDefaultUserCode();
        log.log(LogStatus.INFO, ("*Auto_Stay_02* Disable Auto Stay -> Expected result = System goes into Arm Away when Arm Away is clicked"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        ARM_AWAY(ConfigProps.longExitDelay);
        try {
            if (home.ArwAway_State.isDisplayed())
                log.log(LogStatus.PASS, ("Pass: System is ARMED AWAY"));
        } catch (Exception e) {
            log.log(LogStatus.FAIL, ("Failed: System is in ARMED Stay"));
        }
        home.ArwAway_State.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @AfterMethod(alwaysRun = true)
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
