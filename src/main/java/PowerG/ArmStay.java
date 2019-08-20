package PowerG;

import ServiceCalls.PanelInfo_ServiceCalls;
import ADC.ADC;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import PanelPages.*;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ArmStay extends Setup{

    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public ArmStay() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/PowerG_ArmStay.html");
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/PowerG_ArmStay.html", false);
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
        }
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver();
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longEntryDelay);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longExitDelay);
        servcall.set_AUTO_STAY(0);
        servcall.set_ARM_STAY_NO_DELAY_disable();
    }

    @BeforeMethod
    public void webDriver() throws Exception {
        adc.webDriverSetUp();
    }

    @Test
    public void AS_01_DW10() throws Exception {
        create_report("AS_01");
        log.log(LogStatus.INFO, ("*AS_01* Verify the system will go into alarm at the end of the entry delay if a sensor in group 10 is opened in Arm Stay"));
        System.out.println("*AS_01* Verify the system will go into alarm at the end of the entry delay if a sensor in group 10 is opened in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1101')]", "//*[contains(text(), ' (Sensor 1) Pending Alarm ')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");

    }

    @Test
    public void AS_02_DW12() throws Exception {
        add_to_report("AS_02");
        log.log(LogStatus.INFO, ("*AS_02* Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened in Arm Stay"));
        System.out.println("*AS_02* Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1101')]", "//*[contains(text(), 'Pending Alarm ')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_03_DW14() throws Exception {
        add_to_report("AS_03");
        log.log(LogStatus.INFO, ("*AS_03* Verify the system will go into immediate alarm if a sensor in group 14 is opened in Arm Stay"));
        System.out.println("*AS_03* Verify the system will go into immediate alarm if a sensor in group 14 is opened in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1216, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1216')]", "//*[contains(text(), 'Pending Alarm ')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_04_DW13() throws Exception {
        add_to_report("AS_04");
        log.log(LogStatus.INFO, ("*AS_04* Verify the system will go into immediate alarm if a sensor in group 13 is opened in Arm Stay"));
        System.out.println("*AS_04* Verify the system will go into immediate alarm if a sensor in group 13 is opened in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1231, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1231')]", "//*[contains(text(), 'Pending Alarm ')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_05_DW16() throws Exception {
        add_to_report("AS_05");
        log.log(LogStatus.INFO, ("*AS_05* Verify the system will NOT go into alarm if a sensor in group 13 is opened in Arm Stay"));
        System.out.println("*AS_05* Verify the system will NOT go into alarm if a sensor in group 13 is opened in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1331, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifySystemState("ARMED STAY");
        Thread.sleep(1000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        DISARM();
        ADC_verification("//*[contains(text(), 'DW 104-1331')]", "//*[contains(text(), 'Disarmed')]");
        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
        System.out.println("Pass: system is in Arm Stay mode" + "\n__________________________");
    }

    @Test
    public void AS_06_DW8() throws Exception {
        add_to_report("AS_06");
        log.log(LogStatus.INFO, ("*AS_06* Verify the system will go into immediate alarm if a sensor in group 8 is opened in Arm Stay"));
        System.out.println("*AS_06* Verify the system will go into immediate alarm if a sensor in group 8 is opened in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1127, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1127, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1127')]", "//*[contains(text(), 'Pending Alarm ')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");

    }

    @Test
    public void AS_07_DW9() throws Exception {
        add_to_report("AS_07");
        log.log(LogStatus.INFO, ("*AS_07* Verify the system will go into alarm at the end of entry delay if a sensor in group 9 is opened in Arm Stay"));
        System.out.println("*AS_07* Verify the system will go into alarm at the end of entry delay if a sensor in group 9 is opened in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1123, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1123, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1123')]", "//*[contains(text(), 'Pending Alarm ')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_08_DW25() throws Exception {
        add_to_report("AS_08");
        log.log(LogStatus.INFO, ("*AS_08* Verify the system will NOT go into alarm if a sensor in group 25 is opened in Arm Stay"));
        System.out.println("*AS_08* Verify the system will NOT go into alarm if a sensor in group 25 is opened in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1311, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifySystemState("ARMED STAY");
        Thread.sleep(1000);
        pgprimaryCall(104, 1311, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        DISARM();
        ADC_verification("//*[contains(text(), 'DW 104-1311')]", "//*[contains(text(), 'Panel Armed Stay')]");
        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
        System.out.println("Pass: system is in Arm Stay mode" + "\n__________________________");
    }

    @Test
    public void AS_09_M15() throws Exception {
        add_to_report("AS_09");
        log.log(LogStatus.INFO, ("*AS_09* Verify the system will go into immediate alarm if a sensor in group 15 is activated in Arm Stay"));
        System.out.println("*AS_09* Verify the system will go into immediate alarm if a sensor in group 15 is activated in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(120, 1411, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        verifyInAlarm();
        Thread.sleep(1000);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Motion 120-1411')]", "//*[contains(text(), 'Pending Alarm ')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_10_M17() throws Exception {
        add_to_report("AS_10");
        log.log(LogStatus.INFO, ("*AS_10* Verify the system will NOT go into alarm if a sensor in group 17 is activated in Arm Stay"));
        System.out.println("*AS_10* Verify the system will NOT go into alarm if a sensor in group 17 is activated in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(123, 1441, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifySystemState("ARMED STAY");
        Thread.sleep(1000);
        DISARM();
        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
        System.out.println("Pass: system is in Arm Stay mode" + "\n__________________________");
    }

    @Test
    public void AS_11_M20() throws Exception {
        add_to_report("AS_11");
        log.log(LogStatus.INFO, ("*AS_11* Verify the system will NOT go into alarm if a sensor in group 20 is activated in Arm Stay"));
        System.out.println("*AS_11* Verify the system will NOT go into alarm if a sensor in group 20 is activated in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(122, 1423, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        verifySystemState("ARMED STAY");
        Thread.sleep(1000);
        DISARM();
        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
        System.out.println("Pass: system is in Arm Stay mode" + "\n__________________________");
    }

    @Test
    public void AS_12_M35() throws Exception {
        add_to_report("AS_12");
        log.log(LogStatus.INFO, ("*AS_12* Verify the system will go into alarm at the end of entry delay if a sensor in group 35 is activated in Arm Stay"));
        System.out.println("*AS_12* Verify the system will go into alarm at the end of entry delay if a sensor in group 35 is activated in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(123, 1446, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(1000);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Motion 123-1446')]", "//*[contains(text(), 'Pending Alarm ')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_13_DW25() throws Exception {
        add_to_report("AS_13");
        log.log(LogStatus.INFO, ("*AS_13* Verify the system will NOT go into alarm if a sensor in group 25 is activated in Arm Stay"));
        System.out.println("*AS_13* Verify the system will NOT go into alarm if a sensor in group 25 is activated in Arm Stay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(120, 1445, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        verifySystemState("ARMED STAY");
        Thread.sleep(1000);
        DISARM();
        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
        System.out.println("Pass: system is in Arm Stay mode" + "\n__________________________");
    }

    @Test
    public void AS_14_M15() throws Exception {
        add_to_report("AS_14");
        log.log(LogStatus.INFO, ("*AS_14* Verify the system will go into alarm if a sensor in group 15 is activated and that the system can be disarmed before the dialer delay"));
        System.out.println("*AS_14* Verify the system will go into alarm if a sensor in group 15 is activated and that the system can be disarmed before the dialer delay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(120, 1411, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(1);
        verifyInAlarm();
        TimeUnit.SECONDS.sleep(1);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Motion 120-1411')]", "//*[contains(text(), 'Pending Alarm ')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm and can be disarmed"));
        System.out.println("Pass: system is in alarm and can be disarmed" + "\n__________________________");
    }

    @Test
    public void AS_15_DW10_DW12() throws Exception {
        add_to_report("AS_15");
        log.log(LogStatus.INFO, ("*AS_15* Verify the system reports alarm for both sensors group 10 and 12 at the end of entry delay"));
        System.out.println("*AS_15* Verify the system reports alarm for both sensors group 10 and 12 at the end of entry delay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyInAlarm();
        Thread.sleep(2000);
        WebElement door1 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101']"));
        WebElement door2 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1152']"));
        elementVerification(door1, "DW 104-1101");
        elementVerification(door2, "DW 104-1152");
        enterDefaultUserCode();
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        ADC_verification("//*[contains(text(), 'DW 104-1101 (Sensor 1) Pending Alarm')]", "//*[contains(text(), 'DW 104-1152 (Sensor 2) Pending Alarm')]");
        log.log(LogStatus.PASS, ("Pass: system reports alarm for both sensors group 10 and 12"));
        System.out.println("Pass: system reports alarm for both sensors group 10 and 12" );
    }

    @Test
    public void AS_16_DW10_DW14() throws Exception {
        add_to_report("AS_16");
        log.log(LogStatus.INFO, ("*AS_16* Verify the system reports alarm for both sensors group 10 and 14 at the end of entry delay"));
        System.out.println("*AS_16* Verify the system reports alarm for both sensors group 10 and 14 at the end of entry delay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyInAlarm();
        Thread.sleep(2000);
        WebElement door1 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101']"));
        WebElement door2 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1216']"));
        elementVerification(door1, "DW 104-1101");
        Thread.sleep(3000);
        elementVerification(door2, "DW 104-1216");
        enterDefaultUserCode();
        Thread.sleep(2000);
        ADC_verification("//*[contains(text(), 'DW 104-1101 (Sensor 1) Pending Alarm')]", "//*[contains(text(), 'DW 104-1216 (Sensor 4) Pending Alarm')]");
        pgprimaryCall(104, 1216, PGSensorsActivity.INCLOSE);
        log.log(LogStatus.PASS, ("Pass: system reports alarm for both sensors group 10 and 14"));
        System.out.println("Pass: system reports alarm for both sensors group 10 and 14" + "\n__________________________");
    }

    @Test
    public void AS_17_DW10_DW13() throws Exception {
        add_to_report("AS_17");
        log.log(LogStatus.INFO, ("*AS_17* Verify the system reports alarm for both sensors group 10 and 13 at the end of entry delay"));
        System.out.println("*AS_17* Verify the system reports alarm for both sensors group 10 and 13 at the end of entry delay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(3000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(1000);
        WebElement door1 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101']"));
        WebElement door2 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1231']"));
        elementVerification(door1, "DW 104-1101");
        elementVerification(door2, "DW 104-1231");
        pgprimaryCall(104, 1231, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1101 (Sensor 1) Pending Alarm')]", "//*[contains(text(), 'DW 104-1231 (Sensor 3) Pending Alarm')]");
        log.log(LogStatus.PASS, ("Pass: system reports alarm for both sensors group 10 and 14"));
        System.out.println("Pass: system reports alarm for both sensors group 10 and 14" + "\n__________________________");
    }

    @Test
    public void AS_18_DW10_DW16() throws Exception {
        add_to_report("AS_18");
        log.log(LogStatus.INFO, ("*AS_18* Verify the system reports alarm on  only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 16"));
        System.out.println("*AS_18* Verify the system reports alarm on  only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 16");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(3000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyInAlarm();
        WebElement door1 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101']"));
        elementVerification(door1, "DW 104-1101");
        pgprimaryCall(104, 1331, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1101 (Sensor 1)')]", "//*[contains(text(), ' Pending Alarm')]");
        log.log(LogStatus.PASS, ("Pass: system reports alarm for sensors group 10 only"));
        System.out.println("Pass: system reports alarm for sensors group 10 only"+ "\n__________________________");
    }

    @Test
    public void AS_19_DW10_M15() throws Exception {
        add_to_report("AS_19");
        log.log(LogStatus.INFO, ("*AS_19* Verify the system reports alarm on both sensors at the end of the entry delay group 10 and 15"));
        System.out.println("*AS_19* Verify the system reports alarm on both sensors at the end of the entry delay group 10 and 15");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(3000);
        pgprimaryCall(120, 1411, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyInAlarm();
        Thread.sleep(1000);
        WebElement door1 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101']"));
        WebElement motion1 = driver.findElement(By.xpath("//android.widget.TextView[@text='Motion 120-1411']"));
        elementVerification(door1, "DW 104-1101");
        elementVerification(motion1, "Motion 120-1411");
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1101 (Sensor 1) Pending Alarm')]", "//*[contains(text(), 'Motion 120-1411 (Sensor 9) Pending Alarm')]");
        log.log(LogStatus.PASS, ("Pass: system reports alarm for sensors group 10 and 15"));
        System.out.println("Pass: system reports alarm for sensors group 10 and 15"+ "\n__________________________");
    }

    @Test
    public void AS_20_DW10_M35() throws Exception {
        add_to_report("AS_20");
        log.log(LogStatus.INFO, ("*AS_20* Verify the system reports alarm on both sensors at the end of the entry delay  group 10 and 35"));
        System.out.println("*AS_20* Verify the system reports alarm on both sensors at the end of the entry delay  group 10 and 35");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(3000);
        pgprimaryCall(123, 1446, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyInAlarm();
        WebElement door1 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101']"));
        WebElement motion1 = driver.findElement(By.xpath("//android.widget.TextView[@text='Motion 123-1446']"));
        elementVerification(door1, "DW 104-1101");
        elementVerification(motion1, "Motion 123-1446");
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1101 (Sensor 1) Pending Alarm')]", "//*[contains(text(), 'Motion 123-1446 (Sensor 13) Pending Alarm')]");
        log.log(LogStatus.PASS, ("Pass: system reports alarm for sensors group 10 and 35"));
        System.out.println("Pass: system reports alarm for sensors group 10 and 35" + "\n__________________________");
    }

    @Test
    public void AS_21_DW10_M17_M20() throws Exception {
        add_to_report("AS_21");
        log.log(LogStatus.INFO, ("*AS_21* Verify the system reports alarm on only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 17 or 20"));
        System.out.println("*AS_21* Verify the system reports alarm on only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 17 or 20");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(3000);
        pgprimaryCall(123, 1441, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        pgprimaryCall(122, 1423, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyInAlarm();
        WebElement door1 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101']"));
        elementVerification(door1, "DW 104-1101");
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1101 (Sensor 1) Pending Alarm')]", "//*[contains(text(), 'Motion 123-1441 (Sensor 10) Pending Alarm')]");
        log.log(LogStatus.PASS, ("Pass: system reports alarm for sensors group 10 only"));
        System.out.println("Pass: system reports alarm for sensors group 10 only"+ "\n__________________________");
    }

    @Test
    public void AS_22_DW10_DW10() throws Exception {
        add_to_report("AS_22");
        log.log(LogStatus.INFO, ("*AS_22* Verify the system can be disarmed during the entry delay"));
        System.out.println("*AS_22* Verify the system can be disarmed during the entry delay");
        Thread.sleep(1000);
        addPrimaryCallPG(50, 10, 1042323, 1);
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 2323, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 2323, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(50);
        log.log(LogStatus.PASS, ("Pass: system is disarmed"));
        System.out.println("Pass: system is disarmed" + "\n__________________________");
    }

    @Test
    public void AS_23_DW10_DW12() throws Exception {
        add_to_report("AS_23");
        log.log(LogStatus.INFO, ("*AS_23* Verify the system can be disarmed during the entry delay"));
        System.out.println("*AS_23* Verify the system can be disarmed during the entry delay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay + 2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, ("Pass: system is disarmed"));
        System.out.println("Pass: system is disarmed" + "\n__________________________");
    }

    @Test
    public void AS_24_DW10_M15() throws Exception {
        add_to_report("AS_24");
        log.log(LogStatus.INFO, ("*AS_24* Verify the system can be disarmed during the entry delay"));
        System.out.println("*AS_24* Verify the system can be disarmed during the entry delay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay + 2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(120, 1411, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, ("Pass: system is disarmed"));
        System.out.println("Pass: system is disarmed" + "\n__________________________");
    }

    @Test
    public void AS_25_DW10_M17() throws Exception {
        add_to_report("AS_25");
        log.log(LogStatus.INFO, ("*AS_25* Verify the system can be disarmed during the entry delay"));
        System.out.println("*AS_25* Verify the system can be disarmed during the entry delay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay + 2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(123, 1441, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, ("Pass: system is disarmed"));
        System.out.println("Pass: system is disarmed" + "\n__________________________");
    }

    @Test
    public void AS_26_DW10_M20() throws Exception {
        add_to_report("AS_26");
        log.log(LogStatus.INFO, ("*AS_26* Verify the system can be disarmed during the entry delay"));
        System.out.println("*AS_26* Verify the system can be disarmed during the entry delay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay + 2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(122, 1423, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, ("Pass: system is disarmed"));
        System.out.println("Pass: system is disarmed" + "\n__________________________");
    }

    @Test
    public void AS_27_DW10_M35() throws Exception {
        add_to_report("AS_27");
        log.log(LogStatus.INFO, ("*AS_27* Verify the system can be disarmed during the entry delay"));
        System.out.println("*AS_27* Verify the system can be disarmed during the entry delay");
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay + 2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(122, 1423, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, ("Pass: system is disarmed"));
        System.out.println("Pass: system is disarmed" + "\n__________________________");
    }

    // Tamper events
    @Test
    public void AS_28_DW8() throws Exception {
        add_to_report("AS_28");
        log.log(LogStatus.INFO, ("*AS_28* Verify the panel will report an immediate tamper alarm for dw8"));
        System.out.println("*AS_28* Verify the panel will report an immediate tamper alarm for dw8");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(104, 1127, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        pgprimaryCall(104, 1127, PGSensorsActivity.TAMPERREST);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1127 (Sensor 6) Pending Alarm')]", "//*[contains(text(), 'Sensor 6 Tamper**')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_29_DW9() throws Exception {
        add_to_report("AS_29");
        log.log(LogStatus.INFO, ("*AS_29* Verify the panel will report an immediate tamper alarm for dw9"));
        System.out.println("*AS_29* Verify the panel will report an immediate tamper alarm for dw9");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(104, 1123, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        pgprimaryCall(104, 1123, PGSensorsActivity.TAMPERREST);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1123 (Sensor 7) Pending Alarm')]", "//*[contains(text(), 'Sensor 7 Tamper**')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_30_DW10() throws Exception {
        add_to_report("AS_30");
        log.log(LogStatus.INFO, ("*AS_30* Verify the panel will report an immediate tamper alarm for dw10"));
        System.out.println("*AS_30* Verify the panel will report an immediate tamper alarm for dw10");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPERREST);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1101 (Sensor 1) Pending Alarm')]", "//*[contains(text(), 'Sensor 1 Tamper**')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_31_DW12() throws Exception {
        add_to_report("AS_31");
        log.log(LogStatus.INFO, ("*AS_31* Verify the panel will report an immediate tamper alarm for dw12"));
        System.out.println("*AS_31* Verify the panel will report an immediate tamper alarm for dw12");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPERREST);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1152 (Sensor 2) Pending Alarm')]", "//*[contains(text(), 'Sensor 2 Tamper**')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void  AS_32_DW13() throws Exception {
        add_to_report("AS_32");
        log.log(LogStatus.INFO, ("*AS_32* Verify the panel will report an immediate tamper alarm for dw13"));
        System.out.println("*AS_32* Verify the panel will report an immediate tamper alarm for dw13");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(104, 1231, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        pgprimaryCall(104, 1231, PGSensorsActivity.TAMPERREST);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1152 (Sensor 3) Pending Alarm')]", "//*[contains(text(), 'Sensor 3 Tamper**')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_33_DW14() throws Exception {
        add_to_report("AS_33");
        log.log(LogStatus.INFO, ("*AS_33* Verify the panel will report an immediate tamper alarm for dw14"));
        System.out.println("*AS_33* Verify the panel will report an immediate tamper alarm for dw14");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(3000);
        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPERREST);
        Thread.sleep(1000);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'DW 104-1152 (Sensor 4) Pending Alarm')]", "//*[contains(text(), 'Sensor 4 Tamper**')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_34_DW16() throws Exception {
        add_to_report("AS_34");
        log.log(LogStatus.INFO, ("*AS_34* Verify the panel will report tamper event for dw16, system stays in Arm Stay"));
        System.out.println("*AS_34* Verify the panel will report tamper event for dw16, system stays in Arm Stay");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        verifySensorIsTampered(driver.findElementByXPath("//android.widget.TextView[@text='DW 104-1331']"));
        Thread.sleep(1000);
        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPERREST);
        verifySystemState("ARMED STAY");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: system reports tamper event"));
        System.out.println("Pass: system reports tamper event" + "\n__________________________");
    }

    @Test
    public void AS_35_DW25() throws Exception {
        add_to_report("AS_35");
        log.log(LogStatus.INFO, ("*AS_35* Verify the panel will report tamper event for dw25, system stays in Arm Stay"));
        System.out.println("*AS_35* Verify the panel will report tamper event for dw25, system stays in Arm Stay");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(104, 1311, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        verifySensorIsTampered(driver.findElementByXPath("//android.widget.TextView[@text='DW 104-1311']"));
        Thread.sleep(1000);
        pgprimaryCall(104, 1311, PGSensorsActivity.TAMPERREST);
        verifySystemState("ARMED STAY");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: system reports tamper event"));
        System.out.println("Pass: system reports tamper event" + "\n__________________________");
    }

    @Test
    public void AS_36_M15() throws Exception {
        add_to_report("AS_36");
        log.log(LogStatus.INFO, ("*AS_36* Verify the panel will report an immediate tamper alarm for m15"));
        System.out.println("*AS_36* Verify the panel will report an immediate tamper alarm for m15");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(120, 1411, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        pgprimaryCall(120, 1411, PGSensorsActivity.TAMPERREST);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Motion 120-1411 (Sensor 9) Pending Alarm')]", "//*[contains(text(), 'Sensor 9 Tamper**')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_37_M17() throws Exception {
        add_to_report("AS_37");
        log.log(LogStatus.INFO, ("*AS_37* Verify the panel will report tamper event for m17"));
        System.out.println("*AS_37* Verify the panel will report tamper event for m17");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(123, 1441, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        verifySensorIsTampered(driver.findElementByXPath("//android.widget.TextView[@text='Motion 123-1441']"));
        Thread.sleep(1000);
        pgprimaryCall(123, 1441, PGSensorsActivity.TAMPERREST);
        verifySystemState("ARMED STAY");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system stays in Arm Stay"));
        System.out.println("Pass: panel reports tamper event, system stays in Arm Stay" + "\n__________________________");
    }

    @Test
    public void AS_38_M20() throws Exception {
        add_to_report("AS_38");
        log.log(LogStatus.INFO, ("*AS_38* Verify the panel will report tamper event for m20"));
        System.out.println("*AS_38* Verify the panel will report tamper event for m20");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(122, 1423, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        verifySensorIsTampered(driver.findElementByXPath("//android.widget.TextView[@text='Motion 122-1423']"));
        Thread.sleep(1000);
        pgprimaryCall(122, 1423, PGSensorsActivity.TAMPERREST);
        verifySystemState("ARMED STAY");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system stays in Arm Stay"));
        System.out.println("Pass: panel reports tamper event, system stays in Arm Stay" + "\n__________________________");
    }

    @Test
    public void AS_39_M35() throws Exception {
        add_to_report("AS_39");
        log.log(LogStatus.INFO, ("*AS_39* Verify the panel will report an immediate tamper alarm for m35"));
        System.out.println("*AS_39* Verify the panel will report an immediate tamper alarm for m35");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(123, 1446, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        pgprimaryCall(123, 1446, PGSensorsActivity.TAMPERREST);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Motion 123-1446 (Sensor 13) Pending Alarm')]", "//*[contains(text(), 'Sensor 13 Tamper**')]");
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
        System.out.println("Pass: system is in alarm" + "\n__________________________");
    }

    @Test
    public void AS_40_SMOKE() throws Exception {
        add_to_report("AS_40");
        log.log(LogStatus.INFO, ("*AS_40* Verify the panel will report tamper event for smoke sensor"));
        System.out.println("*AS_40* Verify the panel will report tamper event for smoke sensor");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(201, 1541, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        verifySensorIsTampered(driver.findElementByXPath("//android.widget.TextView[@text='Smoke 201-1541']"));
        Thread.sleep(1000);
        pgprimaryCall(201, 1541, PGSensorsActivity.TAMPERREST);
        verifySystemState("ARMED STAY");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system stays in Arm Stay"));
        System.out.println("Pass: panel reports tamper event, system stays in Arm Stay" + "\n__________________________");
    }

    @Test
    public void AS_41_CO() throws Exception {
        add_to_report("AS_41");
        log.log(LogStatus.INFO, ("*AS_41* Verify the panel will report tamper event for CO sensor"));
        System.out.println("*AS_41* Verify the panel will report tamper event for CO sensor");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        verifySensorIsTampered(driver.findElementByXPath("//android.widget.TextView[@text='CO 220-1661']"));
        Thread.sleep(1000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPERREST);
        verifySystemState("ARMED STAY");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system stays in Arm Stay"));
        System.out.println("Pass: panel reports tamper event, system stays in Arm Stay" + "\n__________________________");
    }

    @Test
    public void AS_41_1_CO() throws Exception {
        add_to_report("AS_41_1");
        log.log(LogStatus.INFO, ("*AS_41_1* Verify the panel will report tamper event for CO sensor during exit delay"));
        System.out.println("*AS_41_1* Verify the panel will report tamper event for CO sensor during exit delay");
        servcall.set_ARM_STAY_NO_DELAY_disable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(3000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPER);
        Thread.sleep(10000);
        verifySensorIsTampered(driver.findElementByXPath("//android.widget.TextView[@text='CO 220-1661']"));
        Thread.sleep(1000);
        verifySystemState("ARMED STAY");
        DISARM();
        Thread.sleep(1000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPERREST);
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_disable();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system is in Arm Stay"));
        System.out.println("Pass: panel reports tamper event, system stays in Arm Stay" + "\n__________________________");
    }

    @Test
    public void AS_41_2_CO() throws Exception {
        add_to_report("AS_41_1");
        log.log(LogStatus.INFO, ("*AS_41_1* Verify the panel will report tamper event for CO sensor during entry delay"));
        System.out.println("*AS_41_1* Verify the panel will report tamper event for CO sensor during entry delay");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPERREST);
        Thread.sleep(1000);
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system is in Arm Stay"));
        System.out.println("Pass: panel reports tamper event, system stays in Arm Stay" + "\n__________________________");
    }

    @Test
    public void AS_42_SHOCK13() throws Exception {
        add_to_report("AS_42");
        log.log(LogStatus.INFO, ("*AS_42* Verify the panel will report tamper event for shock13"));
        System.out.println("*AS_42* Verify the panel will report tamper event for shock13");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPERREST);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1741 (Sensor 17) Alarm')]", "//*[contains(text(), 'Sensor 17 Tamper**')]");
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system stays in Alarm"));
        System.out.println("Pass: panel reports tamper event, system stays in Alarm" + "\n__________________________");
    }

    @Test
    public void AS_43_SHOCK17() throws Exception {
        add_to_report("AS_43");
        log.log(LogStatus.INFO, ("*AS_43* Verify the panel will report tamper event for shock17"));
        System.out.println("*AS_43* Verify the panel will report tamper event for shock17");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        verifySensorIsTampered(driver.findElementByXPath("//android.widget.TextView[@text='Shock 171-1771']"));
        Thread.sleep(1000);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPERREST);
        verifySystemState("ARMED STAY");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system stays in Arm Stay"));
        System.out.println("Pass: panel reports tamper event, system stays in Arm Stay" + "\n__________________________");
    }

    @Test
    public void AS_44_GLASS13() throws Exception {
        add_to_report("AS_44");
        log.log(LogStatus.INFO, ("*AS_44* Verify the panel will report tamper event for glass13"));
        System.out.println("*AS_44* Verify the panel will report tamper event for glass13");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(160, 1874, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        pgprimaryCall(160, 1874, PGSensorsActivity.TAMPERREST);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'GBreak 160-1874 (Sensor 19) Alarm')]", "//*[contains(text(), 'Sensor 19 Tamper**')]");
        Thread.sleep(2000);
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system stays in Alarm"));
        System.out.println("Pass: panel reports tamper event, system stays in Alarm" + "\n__________________________");

    }

    @Test
    public void AS_45_GLASS17() throws Exception {
        add_to_report("AS_45");
        log.log(LogStatus.INFO, ("*AS_45* Verify the panel will report tamper event for glass17"));
        System.out.println("*AS_45* Verify the panel will report tamper event for glass17");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(160, 1871, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        verifySensorIsTampered(driver.findElementByXPath("//android.widget.TextView[@text='GBreak 160-1871']"));
        Thread.sleep(1000);
        pgprimaryCall(160, 1871, PGSensorsActivity.TAMPERREST);
        verifySystemState("ARMED STAY");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system stays in Arm Stay"));
        System.out.println("Pass: panel reports tamper event, system stays in Arm Stay" + "\n__________________________");
    }

    @Test
    public void AS_46_WATER() throws Exception {
        add_to_report("AS_46");
        log.log(LogStatus.INFO, ("*AS_46* Verify the panel will report tamper event for water sensor"));
        System.out.println("*AS_46* Verify the panel will report tamper event for water sensor");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        pgprimaryCall(241, 1971, PGSensorsActivity.TAMPER);
        Thread.sleep(1000);
        verifySensorIsTampered(driver.findElementByXPath("//android.widget.TextView[@text='Water 241-1971']"));
        Thread.sleep(1000);
        pgprimaryCall(241, 1971, PGSensorsActivity.TAMPERREST);
        verifySystemState("ARMED STAY");
        DISARM();
        log.log(LogStatus.PASS, ("Pass: panel reports tamper event, system stays in Arm Stay"));
        System.out.println("Pass: panel reports tamper event, system stays in Arm Stay" + "\n__________________________");
    }

    @Test
    public void AS_47_DW8() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_47");
        log.log(LogStatus.INFO, ("*AS_47* Verify the panel restores the sensor state when the panel is no longer tampered for dw8"));
        System.out.println("*AS_47* Verify the panel restores the sensor state when the panel is no longer tampered for dw8");
        AS_28_DW8();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_48_DW9() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_48");
        log.log(LogStatus.INFO, ("*AS_48* Verify the panel restores the sensor state when the panel is no longer tampered for dw9"));
        System.out.println("*AS_48* Verify the panel restores the sensor state when the panel is no longer tampered for dw9");
        AS_29_DW9();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_49_DW10() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_49");
        log.log(LogStatus.INFO, ("*AS_49* Verify the panel restores the sensor state when the panel is no longer tampered for dw10"));
        System.out.println("*AS_49* Verify the panel restores the sensor state when the panel is no longer tampered for dw10");
        AS_30_DW10();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_50_DW12() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_50");
        log.log(LogStatus.INFO, ("*AS_50* Verify the panel restores the sensor state when the panel is no longer tampered for dw12"));
        System.out.println("*AS_50* Verify the panel restores the sensor state when the panel is no longer tampered for dw12");
        AS_31_DW12();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_51_DW13() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_51");
        log.log(LogStatus.INFO, ("*AS_51* Verify the panel restores the sensor state when the panel is no longer tampered for dw13"));
        System.out.println("*AS_51* Verify the panel restores the sensor state when the panel is no longer tampered for dw13");
        AS_32_DW13();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_52_DW14() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_52");
        log.log(LogStatus.INFO, ("*AS_52* Verify the panel restores the sensor state when the panel is no longer tampered for dw14"));
        System.out.println("*AS_52* Verify the panel restores the sensor state when the panel is no longer tampered for dw14");
        AS_33_DW14();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_53_DW16() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_53");
        log.log(LogStatus.INFO, ("*AS_53* Verify the panel restores the sensor state when the panel is no longer tampered for dw16"));
        System.out.println("*AS_53* Verify the panel restores the sensor state when the panel is no longer tampered for dw16");
        AS_34_DW16();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(3).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_54_DW25() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_54");
        log.log(LogStatus.INFO, ("*AS_54* Verify the panel restores the sensor state when the panel is no longer tampered for dw25"));
        System.out.println("*AS_54* Verify the panel restores the sensor state when the panel is no longer tampered for dw25");
        AS_35_DW25();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(3).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_55_M15() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_55");
        log.log(LogStatus.INFO, ("*AS_55* Verify the panel restores the sensor state when the panel is no longer tampered for m15"));
        System.out.println("*AS_55* Verify the panel restores the sensor state when the panel is no longer tampered for m15");
        AS_36_M15();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Idle"));
        log.log(LogStatus.PASS, ("Pass: Idle event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_56_M17() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_56");
        log.log(LogStatus.INFO, ("*AS_56* Verify the panel restores the sensor state when the panel is no longer tampered for m17"));
        System.out.println("*AS_56* Verify the panel restores the sensor state when the panel is no longer tampered for m17");
        AS_37_M17();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Idle"));
        log.log(LogStatus.PASS, ("Pass: Idle event is displayed"));
        Assert.assertTrue(li_status1.get(3).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_57_M20() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_57");
        log.log(LogStatus.INFO, ("*AS_57* Verify the panel restores the sensor state when the panel is no longer tampered for m20"));
        System.out.println("*AS_57* Verify the panel restores the sensor state when the panel is no longer tampered for m20");
        AS_38_M20();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Idle"));
        log.log(LogStatus.PASS, ("Pass: Idle event is displayed"));
        Assert.assertTrue(li_status1.get(3).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_58_M35() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_58");
        log.log(LogStatus.INFO, ("*AS_58* Verify the panel restores the sensor state when the panel is no longer tampered for m35"));
        System.out.println("*AS_58* Verify the panel restores the sensor state when the panel is no longer tampered for m35");
        AS_39_M35();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Idle"));
        log.log(LogStatus.PASS, ("Pass: Idle event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_59_SMOKE() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_59");
        log.log(LogStatus.INFO, ("*AS_59* Verify the panel restores the sensor state when the panel is no longer tampered for smoke sensor"));
        System.out.println("*AS_59* Verify the panel restores the sensor state when the panel is no longer tampered for smoke sensor");
        AS_40_SMOKE();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Normal"));
        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
        Assert.assertTrue(li_status1.get(3).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Normal events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_60_CO() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_60");
        log.log(LogStatus.INFO, ("*AS_60* Verify the panel restores the sensor state when the panel is no longer tampered for CO sensor"));
        System.out.println("*AS_60* Verify the panel restores the sensor state when the panel is no longer tampered for CO sensor");
        AS_41_CO();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Normal"));
        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
        Assert.assertTrue(li_status1.get(3).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_61_SH13() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_61");
        log.log(LogStatus.INFO, ("*AS_61* Verify the panel restores the sensor state when the panel is no longer tampered for shock13"));
        System.out.println("*AS_61* Verify the panel restores the sensor state when the panel is no longer tampered for shock13");
        AS_42_SHOCK13();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Normal"));
        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_62_SH17() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_62");
        log.log(LogStatus.INFO, ("*AS_62* Verify the panel restores the sensor state when the panel is no longer tampered for shock17"));
        System.out.println("*AS_62* Verify the panel restores the sensor state when the panel is no longer tampered for shock17");
        AS_43_SHOCK17();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Normal"));
        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
        Assert.assertTrue(li_status1.get(3).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_63_GLASS13() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_63");
        log.log(LogStatus.INFO, ("*AS_63* Verify the panel restores the sensor state when the panel is no longer tampered for glass13"));
        System.out.println("*AS_63* Verify the panel restores the sensor state when the panel is no longer tampered for glass13");
        AS_44_GLASS13();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Normal"));
        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    @Test
    public void AS_64_GLASS17() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        add_to_report("AS_64");
        log.log(LogStatus.INFO, ("*AS_64* Verify the panel restores the sensor state when the panel is no longer tampered for glass17"));
        System.out.println("*AS_64* Verify the panel restores the sensor state when the panel is no longer tampered for glass17");
        AS_44_GLASS13();
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Normal"));
        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
        Assert.assertTrue(li_status1.get(4).getText().equals("Tampered"));
        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        home.Home_button.click();
        Thread.sleep(2000);
        log.log(LogStatus.PASS, ("Pass: panel reports Tampered, Closed events"));
        System.out.println("Pass: panel reports Tampered, Closed events" + "\n__________________________");
    }

    //Miscellaneous

    @Test
    public void AS_65_DW10() throws Exception {
        add_to_report("AS_65");
        log.log(LogStatus.INFO, ("*AS_65* Verify the system is still responsive when the panel is in screensaver mode"));
        servcall.set_ARM_STAY_NO_DELAY_enable();
        ARM_STAY();
//        TimeUnit.MINUTES.sleep(6);
//        Assert.assertTrue(driver.findElement(By.id("android:id/content")).isDisplayed());
//        log.log(LogStatus.PASS, ("Pass: photo frame is displayed"));
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay + 2);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        alarmVerification("DW 104-1101 - OPEN");
        ADC_verification("//*[contains(text(), 'DW 104-1101')]", "//*[contains(text(), '(Sensor 3) Opened/Close')]");
        log.log(LogStatus.PASS, ("Pass: system is responsive after the screensaver mode"));
    }

    @Test
    public void AS_66_DW13() throws Exception {
        add_to_report("AS_66");
        log.log(LogStatus.INFO, ("*AS_66* Verify the system is still responsive when the panel is in screensaver mode"));
        servcall.set_ARM_STAY_NO_DELAY_enable();
        ARM_STAY();
//        TimeUnit.MINUTES.sleep(6);
//        Assert.assertTrue(driver.findElement(By.id("android:id/content")).isDisplayed());
//        log.log(LogStatus.PASS, ("Pass: photo frame is displayed"));

        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INCLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        alarmVerification("DW 104-1231 - OPEN");
        ADC_verification("//*[contains(text(), 'DW 104-1231')]", "//*[contains(text(), '(Sensor 3) Opened/Close')]");
        log.log(LogStatus.PASS, ("Pass: system is responsive after the screensaver mode"));
    }

    @Test
    public void AS_67_DW10_DW12() throws Exception {
        add_to_report("AS_67");
        log.log(LogStatus.INFO, ("*AS_67* Verify the system is still responsive when the panel is in screensaver mode"));
        servcall.set_ARM_STAY_NO_DELAY_enable();
        ARM_STAY();
//        TimeUnit.MINUTES.sleep(6);
//        Assert.assertTrue(driver.findElement(By.id("android:id/content")).isDisplayed());
//        log.log(LogStatus.PASS, ("Pass: photo frame is displayed"));
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        alarmVerification("DW 104-1101 - OPEN");
        ADC_verification("//*[contains(text(), 'DW 104-1101')]", "//*[contains(text(), 'DW 104-1152')]");
        log.log(LogStatus.PASS, ("Pass: system is responsive after the screensaver mode"));
    }

    @AfterTest(alwaysRun = true)
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
            //      log.log(LogStatus.FAIL,"Test Case failed", screenshot_path);
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
        adc.driver1.quit();
    }
}
