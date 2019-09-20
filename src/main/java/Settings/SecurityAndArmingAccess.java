package Settings;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import PanelPages.AdvancedSettingsPage;
import PanelPages.InstallationPage;
import PanelPages.SecurityArmingPage;
import PanelPages.SettingsPage;
import utils.ConfigProps;
import utils.Setup;

import java.io.IOException;

public class SecurityAndArmingAccess extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public SecurityAndArmingAccess() throws Exception {
        ConfigProps.init();
    }
    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/SecurityAndArmingAccess.html");
        log = report.startTest(test_area_name);
    }


    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Master_Code_gets_access_to_Security_and_Arming_page() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        create_report("MasterCodeAccessSec_and_Arming");
        log.log(LogStatus.INFO, ("*MasterCodeAccessSec_and_Arming_01* Enable access to Security and Arming page using Master Code -> Expected result = Security and Arming icon is present"));
        Thread.sleep(3000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        swipeVertical();
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPagewithMasterCode();
        Thread.sleep(2000);
        if (inst.SECURITY_AND_ARMING.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: Security and Arming icon is present"));
        } else {
            log.log(LogStatus.FAIL, ("Failed: Security and Arming icon is NOT present"));
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        log.log(LogStatus.INFO, ("*MasterCodeAccessSec_and_Arming_02* Disable access to Security and Arming page using Master Code -> Expected result = Security and Arming icon is gone"));
        navigateToAdvancedSettingsPagewithMasterCode();
        Thread.sleep(2000);
        try {
            if (inst.SECURITY_AND_ARMING.isDisplayed())
            log.log(LogStatus.FAIL, ("Failed: Security and Arming icon is present"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: Security and Arming icon is NOT present"));
        }
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
