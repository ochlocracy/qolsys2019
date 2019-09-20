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

public class PanicDisable extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public PanicDisable() throws Exception {
        ConfigProps.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/PanicDisable.html");
        log = report.startTest(test_area_name);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Panic_Disable_works() throws Exception {
        SirenAlarmsPage siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);

        create_report("Panic_Disable");
        log.log(LogStatus.INFO, ("*Panic_Disable_01* Fire, Police, Auxiliary disabled -> Expected result = Element will not be there"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Police_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Police_icon.isDisplayed())
            log.log(LogStatus.FAIL, ("Failed: Police Emergency is displayed"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: Police Emergency is NOT displayed"));
        }
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Fire_icon.isDisplayed())
            log.log(LogStatus.FAIL, ("Failed: Fire Emergency is displayed"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: Fire Emergency is NOT displayed"));
        }
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Police_Panic.click();
        Thread.sleep(1000);
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Auxiliary_icon.isDisplayed())
            log.log(LogStatus.FAIL, ("Failed: Auxiliary Emergency is displayed"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: Auxiliary Emergency is NOT displayed"));
        }
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Fire_Panic.click();
        siren.Auxiliary_Panic.click();
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
