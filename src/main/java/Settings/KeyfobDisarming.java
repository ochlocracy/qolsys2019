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

public class KeyfobDisarming extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public KeyfobDisarming() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/KeyfobDisarming.html");
        log = report.startTest(test_area_name);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test(priority =0)
    public void Verify_Keyfob_Disarming_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        create_report("Keyfob_Disarming");
        log.log(LogStatus.INFO, ("*Keyfob_Arm_Dis_01* Keyfob Disarm setting enabled -> Expected result = Keyfob will disarm system"));
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(00);
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        pgarmer(300, 1004, "01");
        Thread.sleep(2000);
        verifySystemState("DISARMED");
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(15000);
        pgarmer(300, 1004, "01");
        Thread.sleep(2000);
        verifySystemState("DISARMED");
        if (home.Disarmed_text.isDisplayed()) {
           log.log(LogStatus.PASS, ("Pass: Keyfob disarm works"));
        } else {
            log.log(LogStatus.FAIL, ("Failed: Keyfob disarm did not worked"));
        }
        Thread.sleep(2000);
        log.log(LogStatus.INFO, ("*Keyfob_Arm_Dis_02* Keyfob Disarm setting disabled -> Expected result = Keyfob will not disarm system"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Disarming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        pgarmer(300, 1004, "01");
        Thread.sleep(2000);
        verifySystemState("ARMED STAY");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgarmer(300, 1004, "01");
        Thread.sleep(2000);
        if (home.ArwAway_State.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: Keyfob did not disarm"));
        } else {
            log.log(LogStatus.FAIL, ("Failed: Keyfob disarmed the system"));
        }
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Disarming.click();
        Thread.sleep(2000);

        settings.Home_button.click();
        servcall.set_AUTO_STAY(01);
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
