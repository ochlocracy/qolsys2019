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
import PanelPages.SettingsPage;
import PanelPages.SirenAlarmsPage;
import utils.ConfigProps;
import utils.Setup;

import java.io.IOException;

public class SirenAndAlarmsAccess extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public SirenAndAlarmsAccess() throws Exception {
        ConfigProps.init();
    }
    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/SirenAndAlarms.html");
        log = report.startTest(test_area_name);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Master_Code_gets_access_to_Siren_and_Alarms_page() throws Exception {
        SirenAlarmsPage siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        create_report("MasterCodeAccessSirens_and_Alarms");
        Thread.sleep(1000);
        log.log(LogStatus.INFO, ("*MasterCodeAccessSirens_and_Alarms_01* Enable access to Sirens and Alarms page using Master Code -> Expected result = Sirens and Alarms icon is present"));
        Thread.sleep(3000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        if (inst.SIREN_AND_ALARMS.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: Siren and Alarms icon is present"));
        } else {
            log.log(LogStatus.FAIL, ("Failed: Siren and Alarms icon is NOT present"));
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        log.log(LogStatus.INFO, ("*MasterCodeAccessSirens_and_Alarms_02* Disable access to Sirens and Alarms page using Master Code -> Expected result = Sirens and Alarms icon is gone"));
        Thread.sleep(2000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.SIREN_AND_ALARMS.isDisplayed())
            log.log(LogStatus.FAIL, ("Failed: Siren and Alarms icon is present"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: Siren and Alarms icon is NOT present"));
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
