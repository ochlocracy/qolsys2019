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

public class SecureDeleteImages extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public SecureDeleteImages() throws Exception {
        ConfigProps.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/SecureDeleteImages.html");
        log = report.startTest(test_area_name);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void Verify_Secure_Delete_Images_works() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        create_report("Secure_Delete_Images");
        log.log(LogStatus.INFO, ("*Secure_Del_Img_01* Delete a Photo -> Expected result = A code required to Delete the image"));
        Thread.sleep(2000);
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()) {
            System.out.println("Delete pop-up");
        }
        camera.Camera_delete_yes.click();
        if (home.Enter_Code_to_Access_the_Area.isDisplayed()) {
           log.log(LogStatus.PASS, ("Pass: Password is required to delete the image"));
        } else {
            log.log(LogStatus.FAIL, ("Failed: Password is NOT required to delete the image"));
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Secure_Delete_Images.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        log.log(LogStatus.INFO, ("*Secure_Del_Img_02* Disable code required to Delete a Photo -> Expected result = Image deleted without code"));
        Thread.sleep(2000);
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()) {
            System.out.println("Delete pop-up");
        }
        camera.Camera_delete_yes.click();
        try {
            if (home.Enter_Code_to_Access_the_Area.isDisplayed())
            log.log(LogStatus.FAIL, ("Failed: Password is required to delete the image"));
        } catch (Exception e) {
            log.log(LogStatus.PASS, ("Pass: Password is NOT required to delete the image"));
        }
        swipeFromLefttoRight();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Secure_Delete_Images.click();
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
        driver.quit();
        service.stop();
    }
}
