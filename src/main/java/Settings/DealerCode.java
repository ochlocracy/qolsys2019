package Settings;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;

import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import PanelPages.*;
import utils.ConfigProps;
import utils.Setup;

import java.io.IOException;

public class DealerCode extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public DealerCode() throws Exception {
        ConfigProps.init();
    }
    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/DealerCode.html");
        log = report.startTest(test_area_name);
    }
    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/DealerCode.html", false);
        log = report.startTest(test_case_name);
    }

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test(priority =0)
    public void Verify_Dealer_Code_Change() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);

        create_report("Dealer_Code_01");
        log.log(LogStatus.INFO, ("*Dealer_Code_01* Change Dealer Name and Dealer Pass -> Expected result = Old Dealer Code will not access the settings page"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Dealer_Code.click();
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("NewDealer");
        user.Add_User_Code_field.clear();
        Thread.sleep(1000);
        user.Add_User_Code_field.sendKeys("5555");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        Thread.sleep(1000);
        user.Add_Confirm_User_Code_field.clear();
        user.Add_Confirm_User_Code_field.sendKeys("5555");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(1000);
        navigateToPartitionsAdvancedSettingsPage();
        Thread.sleep(2000);
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        Thread.sleep(3000);
        adv.USER_MANAGEMENT.click();
        Thread.sleep(1000);
//        driver.findElement(By.xpath("//android.widget.TextView[@text='NewDealer']")).isDisplayed();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.ADVANCED_SETTINGS.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        if (settings.Invalid_User_Code.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: old Dealer code does not work"));
        }
        Thread.sleep(2000);
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        log.log(LogStatus.PASS, ("Pass: New Dealer code works"));
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Dealer_Code.click();
        Thread.sleep(2000);
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("Dealer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("2222");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code_field.clear();
        user.Add_Confirm_User_Code_field.sendKeys("2222");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(1000);

    }

    @Test(priority = 1)
    public void Verify_6_Dealer_Code_Change() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);

        add_to_report("Dealer_Code_02");
        log.log(LogStatus.INFO, ("*Dealer_Code_02* Change Dealer Code to 6 digits -> Expected result = Old Dealer Code will not access the settings page"));
        Thread.sleep(2000);

        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.DEALER_SETTINGS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='6 Digit User Code']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(2000);

        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        if (settings.Invalid_User_Code.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: old Dealer code does not work"));
        }
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Zero.click();
        settings.Zero.click();

        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Dealer_Code.click();

        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("6Dealer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("222222");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        Thread.sleep(1000);
        user.Add_Confirm_User_Code_field.clear();
        user.Add_Confirm_User_Code_field.sendKeys("222222");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(1000);
        navigateToPartitionsAdvancedSettingsPage();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        log.log(LogStatus.PASS, ("Pass: New Dealer code works"));
        adv.INSTALLATION.click();
        inst.DEALER_SETTINGS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='6 Digit User Code']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(2000);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }

    @AfterMethod
    public void webDriverQuit(ITestResult result) throws IOException {
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
