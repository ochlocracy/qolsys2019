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

public class DisarmPhotos extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public DisarmPhotos() throws Exception {
        ConfigProps.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/DisarmPhotos.html");
        log = report.startTest(test_area_name);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Disarm_Photos_works() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        create_report("Disarm_Photo");
        log.log(LogStatus.INFO, ("*Disarm_Photo_01* Disarm Photos already enabled -> Expected result = a photo is taken"));
        Thread.sleep(2000);
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Disarm_photos.click();
        if (camera.Photo_lable.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: Disarm photo is displayed"));
        } else {
            log.log(LogStatus.FAIL, ("Failed: Disarm photo is NOT displayed"));
        }
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        log.log(LogStatus.INFO, ("*Disarm_Photo_02* Disable Disarm Photos  -> Expected result = a photo is not taken upon disarm"));
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        set_cam.Disarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Disarm_photos.click();
        try {
            if (camera.Photo_lable.isDisplayed())
            log.log(LogStatus.FAIL, ("Failed: Disarm photo is displayed"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: Disarm photo is NOT displayed"));
        }
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Disarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
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
    }
}