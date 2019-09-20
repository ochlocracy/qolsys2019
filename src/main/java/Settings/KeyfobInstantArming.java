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

public class KeyfobInstantArming extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public KeyfobInstantArming() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/KeyfobInstantArming.html");
        log = report.startTest(test_area_name);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Keyfob_Instant_Arming_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        create_report("Keyfob_Instant_Arming");
        log.log(LogStatus.INFO, ("*Keyfob_Inst_Arm_01* Keyfob Instant Arming enabled -> Expected result = Keyfob Arm system with no delay"));
        Thread.sleep(2000);
        System.out.println("Verify that Keyfob Instant Arming works when enabled");
        System.out.println("Adding sensors...");
        System.out.println("Arm Stay the system");
        Thread.sleep(3000);

        pgarmer(300, 1004, "02");
        Thread.sleep(5000);
        verifySystemState("ARMED STAY");
        if (home.ARM_STAY_text.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: Keyfob Instant Arm Stay works"));
        }
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Arm Away the system");
        pgarmer(300, 1004, "03");
        Thread.sleep(4000);
        verifySystemState("ARMED AWAY");
        if (home.ARM_AWAY_text.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: Keyfob Instant Arm Away works"));
        }
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        log.log(LogStatus.INFO, ("*Keyfob_Inst_Arm_02* Keyfob Instant Arming disabled -> Expected result = Keyfob should Arm system with delay"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        pgarmer(300, 1004, "02");
        Thread.sleep(1000);
        try {
            if (home.ARM_STAY_text.isDisplayed())
                log.log(LogStatus.FAIL, ("Failed: System is ARMED STAY without delay"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: System is NOT ARMED STAY yet"));
        }
        Thread.sleep(10000);
        verifySystemState("ARMED STAY");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Arm Away the system");
        pgarmer(300, 1004, "03");
        Thread.sleep(1000);
        try {
            if (home.ARM_AWAY_text.isDisplayed())
            log.log(LogStatus.FAIL, ("Failed: System is ARMED AWAY without delay"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: System is NOT ARMED AWAY yet"));
        }
        Thread.sleep(10000);
        verifySystemState("ARMED AWAY");
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Instant_Arming.click();
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
