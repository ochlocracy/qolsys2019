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

public class AlarmPhotos extends Setup {

    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public AlarmPhotos() throws Exception {
        ConfigProps.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/AlarmPhotos.html");
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/AlarmPhotos.html", false);
        log = report.startTest(test_case_name);
    }


    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Alarm_Photos_works() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        //disable alarm videos
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        swipeUp();
        set_cam.Alarm_Videos.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(1000);

        create_report("AlarmPhotos");
        log.log(LogStatus.INFO, ("*AlarmPhotos_01* Arm and Disarm Panel -> Expected result = Alarm photo is taken"));
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(1000);
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Alarms_photo.click();
        if (camera.POLICE_EMERGENCY_PANEL.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: Emergency Alarm photo is displayed"));
        } else {
            log.log(LogStatus.FAIL, ("Fail: Emergency Alarm photo is not displayed"));
        }
        try {
            while (camera.Camera_delete.isDisplayed()) {
                camera.Camera_delete.click();
                Thread.sleep(2000);
                camera.Camera_delete_yes.click();
                if (home.Four.isDisplayed()) {
                    enterDefaultUserCode();
                }
            }
        } catch (Exception e) {
            System.out.println("No photos left to delete...");
        }

        Thread.sleep(1000);
        log.log(LogStatus.INFO, ("*AlarmPhotos_02* Disable Alarm Photo setting -> No photo is displayed"));
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        set_cam.Alarm_Photos.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(1000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(1000);
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Alarms_photo.click();
        try {
            if (camera.Photo_lable.isDisplayed())
                log.log(LogStatus.FAIL, ("Failed: Alarm photo is displayed"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: Alarm photo is NOT displayed"));
        }
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Alarm_Photos.click();
        Thread.sleep(1000);
        swipeUp();
        set_cam.Alarm_Videos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @AfterMethod(alwaysRun = true)
    public void webDriverQuit(ITestResult result) throws IOException {
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