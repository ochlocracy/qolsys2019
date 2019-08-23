package PowerG;

import ADC.ADC;
import PanelPages.HomePage;
import ServiceCalls.PanelInfo_ServiceCalls;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.RetryAnalizer;
import utils.Setup;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Shock extends Setup {

    /* Estimate execution time:  21m */

    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public Shock() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/Shock.html");
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/Shock.html", false);
        log = report.startTest(test_case_name);
    }

    public void ADC_verification(String string, String string1) throws InterruptedException, IOException {
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("History")).click();
        Thread.sleep(7000);
        String[] message = {string, string1};
        adc.driver1.navigate().refresh();
        Thread.sleep(7000);
        for (int i = 0; i < message.length; i++) {
            adc.driver1.navigate().refresh();
            try {
                WebElement history_message = adc.driver1.findElement(By.xpath(message[i]));
                Assert.assertTrue(history_message.isDisplayed());
                {
                    System.out.println("Pass: message is displayed " + history_message.getText());
                }
            } catch (Exception e) {
                System.out.println("***No such element found!***");
            }
            Thread.sleep(7000);
        }
    }

    public void default_state() throws IOException, InterruptedException {
        disarmServiceCall();
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPERREST);
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPERREST);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver();
        servcall.set_ARM_STAY_NO_DELAY_enable();
        servcall.set_AUTO_STAY(0);
    }

    @BeforeMethod
    public void webDriver() throws Exception {
        adc.webDriverSetUp();
    }

    @Test
    public void Shock() throws IOException, InterruptedException {
        create_report("Shock_0");
        log.log(LogStatus.INFO, ("*Shock_0* Set sensitivity lvl from ADC dealer website"));
        System.out.println("*Shock_0* Set sensitivity lvl from ADC dealer website");
        adc.New_ADC_session(adc.getAccountId());
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.findElement(By.xpath("//*[@id='ctl00_navLinks']/ul/li[11]/a")).click();
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_UcAirFxMenu1_UcSensorSettings_lnkAdvancedSensorSettings']/li/span")).click();
        TimeUnit.SECONDS.sleep(2);
        Select list = new Select(adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_ddlDeviceList_ddlDevicesDdl']")));
        list.selectByVisibleText("17 - Shock 171-1741");
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_gvSettings_ctl18_btnEdit1']")).click();
        TimeUnit.SECONDS.sleep(3);
        Select list1 = new Select(adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_drpNewValue']")));
        list1.selectByVisibleText("High");
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_butChange']")).click();
        TimeUnit.SECONDS.sleep(62);

        Select list2 = new Select(adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_ddlDeviceList_ddlDevicesDdl']")));
        list2.selectByVisibleText("17 - Shock 171-1741");
        TimeUnit.SECONDS.sleep(3);
        adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_gvSettings_ctl23_refreshButton']")).click();
        TimeUnit.SECONDS.sleep(3);

        WebElement desired = adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_gvSettings']/tbody/tr[18]/td[2]"));
        Assert.assertTrue(desired.getText().equals("High"));
        log.log(LogStatus.PASS, ("Pass: Sensitivity lvl is set to \"High\""));
        System.out.println("Pass: Sensitivity lvl is set to \"High\"" + "\n__________________________");
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void Shock_01() throws Exception {
        add_to_report("Shock_01");
        log.log(LogStatus.INFO, ("*Shock_01* Disarm mode tripping Shock group 13 -> Disarm"));
        System.out.println("*Shock_01* Disarm mode tripping Shock group 13 -> Disarm");
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(171, 1741, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        ADC_verification("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'sensor 17')]");
        log.log(LogStatus.PASS, ("Pass: System is Disarmed"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is Disarmed" + "\n__________________________");
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void Shock_02() throws Exception {
        add_to_report("Shock_02");
        log.log(LogStatus.INFO, ("*Shock_02* ArmStay mode tripping Shock group 13 -> Instant Alarm"));
        System.out.println("*Shock_02* ArmStay mode tripping Shock group 13 -> Instant Alarm");
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.SECONDS.sleep(1);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1741, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(3);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'sensor 17')]");
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void Shock_03() throws Exception {
        add_to_report("Shock_03");
        log.log(LogStatus.INFO, ("*Shock_03* ArmAway mode tripping Shock group 13 -> Instant Alarm"));
        System.out.println("*Shock_03* ArmAway mode tripping Shock group 13 -> Instant Alarm");
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_AWAY(ConfigProps.longExitDelay + 2);
        pgprimaryCall(171, 1741, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'sensor 17')]");
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalizer.class)
    public void Shock_04() throws Exception {
        add_to_report("Shock_04");
        log.log(LogStatus.INFO, ("*Shock_04* Disarm mode tripping Shock group 17 -> Disarm"));
        System.out.println("*Shock_04* Disarm mode tripping Shock group 17 -> Disarm");
        pgprimaryCall(171, 1771, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        ADC_verification("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'Disarmed')]");
        log.log(LogStatus.PASS, ("Pass: System is Disarmed"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is Disarmed" + "\n__________________________");
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalizer.class)
    public void Shock_05() throws Exception {
        add_to_report("Shock_05");
        log.log(LogStatus.INFO, ("*Shock_05* ArmStay mode tripping Shock group 17 -> ArmStay"));
        System.out.println("*Shock_05* ArmStay mode tripping Shock group 17 -> ArmStay");
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_STAY();
        pgprimaryCall(171, 1771, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Armed Stay");
        ADC_verification("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'Armed Stay')]");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: System is Armed Stay"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is Armed Stay" + "\n__________________________");
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalizer.class)
    public void Shock_06() throws Exception {
        add_to_report("Shock_06");
        log.log(LogStatus.INFO, ("*Shock_06* ArmAway mode tripping Shock group 17 -> Instant Alarm"));
        System.out.println("*Shock_06* ArmAway mode tripping Shock group 17 -> Instant Alarm");
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_AWAY(ConfigProps.longExitDelay + 2);
        pgprimaryCall(171, 1771, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'sensor 18')]");
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalizer.class)
    public void Shock_07() throws Exception {
        add_to_report("Shock_07");
        log.log(LogStatus.INFO, ("*Shock_07* Disarm mode tamper Shock_other group 13, 17 -> Expected result = system stays in Disarm mode"));
        System.out.println("*Shock_07* Disarm mode tamper Shock_other group 13, 17 -> Expected result = system stays in Disarm mode");
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPERREST);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is Disarmed"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is Disarmed" + "\n__________________________");
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalizer.class)
    public void Shock_08() throws Exception {
        add_to_report("Shock_08");
        log.log(LogStatus.INFO, ("*Shock_08* ArmStay mode tamper Shock_other group 13 -> Instant Alarm"));
        System.out.println("*Shock_08* ArmStay mode tamper Shock_other group 13 -> Instant Alarm");
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'sensor 17')]");
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalizer.class)
    public void Shock_09() throws Exception {
        add_to_report("Shock_09");
        log.log(LogStatus.INFO, ("*Shock_09* ArmStay mode tamper Shock_other group 17 -> ArmStay"));
        System.out.println("*Shock_09* ArmStay mode tamper Shock_other group 17 -> ArmStay");
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Armed Stay");
        TimeUnit.SECONDS.sleep(3);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPERREST);
        TimeUnit.SECONDS.sleep(2);
        ADC_verification("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'sensor 18')]");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: System is Armed Stay"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is Armed Stay" + "\n__________________________");
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalizer.class)
    public void Shock_10() throws Exception {
        add_to_report("Shock_10");
        log.log(LogStatus.INFO, ("*Shock_10* ArmAway mode tamper Shock_other group 13 -> Instant Alarm"));
        System.out.println("*Shock_10* ArmAway mode tamper Shock_other group 13 -> Instant Alarm");
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_AWAY(ConfigProps.longExitDelay + 2);
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(3);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'sensor 17')]");
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }

    @Test(priority = 11, retryAnalyzer = RetryAnalizer.class)
    public void Shock_11() throws Exception {
        add_to_report("Shock_11");
        log.log(LogStatus.INFO, ("*Shock_11* ArmAway mode tamper Shock_other group 17 -> Instant Alarm"));
        System.out.println("*Shock_11* ArmAway mode tamper Shock_other group 17 -> Instant Alarm");
        if (RetryAnalizer.retry == true) {
            default_state();
            log.log(LogStatus.SKIP, ("Rerunning the test case"));
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_AWAY(ConfigProps.longExitDelay);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'sensor 18')]");
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPERREST);
        log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        System.out.println("Pass: System is in Alarm" + "\n__________________________");
    }

    @Test(priority = 12)
    public void Shock_12() throws IOException, InterruptedException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("Shock_12");
        log.log(LogStatus.INFO, ("*Shock_12* Verify on a panel sensitivity lvl is set to \"High\""));
        System.out.println("*Shock_12* Verify on a panel sensitivity lvl is set to \"High\"");
        navigateToEditSensorPage();
        swipeUp();
        swipeUp();
        swipeUp();
        swipeUp();
        swipeUp();
        TimeUnit.SECONDS.sleep(3);
        WebElement b = driver.findElement(By.className("android.widget.LinearLayout"));
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/imageView1"));
        li.get(1).click();
        TimeUnit.SECONDS.sleep(3);
        WebElement a = driver.findElement(By.id("com.qolsys:id/pgsensitivitylevel"));
        List<WebElement> li1 = a.findElements(By.id("android:id/text1"));
        Assert.assertTrue(li1.get(0).getText().equals("High"));
        TimeUnit.SECONDS.sleep(1);
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: sensitivity lvl is set to \"High\""));
        System.out.println("Pass: sensitivity lvl is set to \"High\"" + "\n__________________________");
    }

    @Test(priority = 13)
    public void Shock_13() throws IOException, InterruptedException {
        add_to_report("Shock_13");
        log.log(LogStatus.INFO, ("*Shock_13* Set from panel sensitivity lvl to \"Medium\""));
        System.out.println("*Shock_13* Set from panel sensitivity lvl to \"Medium\"");
        navigateToEditSensorPage();
        swipeUp();
        swipeUp();
        swipeUp();
        swipeUp();
        swipeUp();
        TimeUnit.SECONDS.sleep(3);
        WebElement b = driver.findElement(By.className("android.widget.LinearLayout"));
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/imageView1"));
        li.get(1).click();
        TimeUnit.SECONDS.sleep(3);
        driver.findElement(By.id("com.qolsys:id/pgsensitivitylevel")).click();
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Medium']")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.id("com.qolsys:id/addsensor")).click();
        log.log(LogStatus.PASS, ("Pass: sensitivity lvl is set to \"Medium\""));
        System.out.println("Pass: sensitivity lvl is set to \"Medium\"" + "\n__________________________");
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }

    @AfterMethod(alwaysRun = true)
    public void webDriverQuit(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
        adc.driver1.quit();
    }
}
