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
import PanelPages.CameraSettingsPage;
import PanelPages.InstallationPage;
import PanelPages.SettingsPage;
import utils.ConfigProps;

import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;

public class CameraSettingsAccess extends Setup {

    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public CameraSettingsAccess() throws Exception {
        ConfigProps.init();
    }
    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/CameraSettingsAccess.html");
        log = report.startTest(test_area_name);
    }


    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Master_Code_gets_access_to_Camera_Settings_page() throws Exception {
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        create_report("MasterCodeAccessCamera");
        Thread.sleep(1000);
        log.log(LogStatus.INFO, ("*MasterCodeAccessCamera_01* Enable access to Camera Settings when using Master Code -> Expected result = Camera Settings icon is present"));
        Thread.sleep(3000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        set_cam.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPagewithMasterCode();
        Thread.sleep(2000);
        if (inst.CAMERA_SETTINGS.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: Camera settings icon is present"));
        } else {
            log.log(LogStatus.FAIL, ("Failed: Camera settings icon is NOT present"));
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        set_cam.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        log.log(LogStatus.INFO, ("*MasterCodeAccessCamera_02* Disable access to Camera Settings when using Master Code -> Expected result = Camera Settings icon is gone"));
        Thread.sleep(3000);
        navigateToAdvancedSettingsPagewithMasterCode();
        Thread.sleep(2000);
        try {
            if (inst.CAMERA_SETTINGS.isDisplayed())
            log.log(LogStatus.FAIL, ("Failed: Camera settings icon is present"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: Camera settings icon is NOT present"));
        }
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = captureScreenshot(driver, result.getName());
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            log.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            //      log.log(LogStatus.FAIL,"Test Case failed", screenshot_path);
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
        driver.quit();
        service.stop();
    }
}
