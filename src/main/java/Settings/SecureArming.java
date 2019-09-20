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
import utils.Setup;

import java.io.IOException;

public class SecureArming extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public SecureArming() throws Exception {
        ConfigProps.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/SecureArming.html");
        log = report.startTest(test_area_name);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Secure_Arming_works() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        create_report("Secure_Arming");
        log.log(LogStatus.INFO, ("*Secure_Arming_01* Disable Secure Arming -> Expected result = No code required to Arm the system"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(2000);
        if (home.ARM_STAY_text.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: System armed without code"));
        }
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(2000);
        arming.Secure_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        log.log(LogStatus.INFO, ("*Secure_Arming_02* Enable Secure Arming -> Expected result = A code is required to Arm the system"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        if (home.Enter_Code_to_Access_the_Area.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: code is requires to Arm the system"));
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(2000);
        arming.Secure_Arming.click();
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
