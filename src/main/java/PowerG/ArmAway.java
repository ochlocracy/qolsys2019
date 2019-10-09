package PowerG;

import ADC.ADC;

import ADC.UIRepo;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ServiceCalls.PanelInfo_ServiceCalls;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;
import com.relevantcodes.extentreports.LogStatus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ArmAway extends Setup{
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();


    public ArmAway() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/PowerG_ArmAway.html");
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/PowerG_ArmAway.html", false);
        log = report.startTest(test_case_name);
    }

    public void usersite_alarm_disarm(String login, String password) throws InterruptedException, IOException {
        adc.New_ADC_session_User();
        Thread.sleep(5000);
        adc.driver1.get("https://www.alarm.com/web/system/alerts-issues");
        Thread.sleep(7000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Stop Alarm']"))).click();
        Thread.sleep(4000);
        adc.driver1.findElement(By.xpath("(//*[text()='Stop Alarms'])[last()]")).click();
        Thread.sleep(10000);
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
    public void AA_01() throws Exception {
        UIRepo ui = PageFactory.initElements(adc.driver1, UIRepo.class);
        create_report( "GEN-3256");
        log.log(LogStatus.INFO, ("*Away_01* Verify the system can be disarmed from the ADC"));
        System.out.println("*Away_01* Verify the system can be disarmed from the ADC");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        adc.New_ADC_session_User();
        Thread.sleep(10000);
        ui.Arm_Away_state.click();
        Thread.sleep(1000);
        ui.Disarm.click();
        Thread.sleep(10000);
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed from ADC user website"));
        System.out.println("System is Disarmed from ADC user website" + "\n__________________________");
    }  // run time 1 min 30 sec
    @Test
    public void AA_03_dw10() throws Exception {
        add_to_report("GEN-3258");
        log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay DW10"));
        System.out.println("*Verify the system can be disarmed during the entry delay DW10");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 0 sec
    @Test
    public void AA_04_dw12() throws Exception {
        add_to_report("GEN-3259");
        log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay DW12"));
        System.out.println("*Verify the system can be disarmed during the entry delay DW12");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 0 sec
    @Test
    public void AA_05_dw12() throws Exception {
        add_to_report("GEN-3260");
        log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay using a Guest code DW12"));
        System.out.println("*Verify the system can be disarmed during the entry delay using a Guest code DW12");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        enterGuestCode();
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 56 sec
    @Test
    public void AA_06_dw10_dw12() throws Exception {
        add_to_report("GEN-3261");
        log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay DW10, DW12"));
        System.out.println("*Verify the system can be disarmed during the entry delay DW10, DW12");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 2 sec
    @Test
    public void AA_07_dw10_m15() throws Exception {
        add_to_report("GEN-3262");
        log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay DW10, Motion15"));
        System.out.println("*Verify the system can be disarmed during the entry delay DW10, Motion15");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(120, 1411, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 57 sec
    @Test
    public void AA_08_m20() throws Exception {
        add_to_report("GEN-3263");
        log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay Motion20"));
        System.out.println("*Verify the system can be disarmed during the entry delay Motion20");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(122, 1423, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 56 sec
    @Test
    public void AA_09() throws Exception {
        add_to_report("GEN-3264");
        log.log(LogStatus.INFO, ("*Verify the system will pretend to disarm if a valid duress code is used and a duress alarm will be sent to ADC, simulating the user being forced to disarm the system."));
        System.out.println("*Verify the system will pretend to disarm if a valid duress code is used and a duress alarm will be sent to ADC, simulating the user being forced to disarm the system.");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        enterDefaultDuressCode();
        verifySystemState("DISARMED");
        usersite_alarm_disarm(ConfigProps.login, ConfigProps.password);
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 48 sec
    @Test
    public void AA_10() throws Exception {
        add_to_report("GEN-3265");
        log.log(LogStatus.INFO, ("*Verify the system will pretend to disarm if a valid duress code is used and a duress alarm will be sent to ADC, simulating the user being forced to disarm the system."));
        System.out.println("*Verify the system will pretend to disarm if a valid duress code is used and a duress alarm will be sent to ADC, simulating the user being forced to disarm the system.");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        enterDefaultDuressCode();
        verifySystemState("DISARMED");
        usersite_alarm_disarm(ConfigProps.login, ConfigProps.password);
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 50 sec
    @Test
    public void AA_13_dw10() throws Exception {
        add_to_report("GEN-3268");
        log.log(LogStatus.INFO, ("*Verify the system will go into alarm at the end of the entry delay if a sensor in group 10 is opened in Arm Away*"));
        System.out.println("*Verify the system will go into alarm at the end of the entry delay if a sensor in group 10 is opened in Arm Away*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 15 sec
    @Test
    public void AA_14_dw12() throws Exception {
        add_to_report("GEN-3269");
        log.log(LogStatus.INFO, ("*Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened in Arm Away*"));
        System.out.println("*Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened in Arm Away*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +4);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 15 sec
    @Test
    public void AA_15_dw13() throws Exception {
        add_to_report("GEN-3270");
        log.log(LogStatus.INFO, ("*Verify the system will go into immediate alarm if a sensor in group 13 is opened in Arm Away*"));
        System.out.println("*Verify the system will go into immediate alarm if a sensor in group 13 is opened in Arm Away*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INCLOSE);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 59 sec
    @Test
    public void AA_16_dw14() throws Exception {
        add_to_report("GEN-3271");
        log.log(LogStatus.INFO, ("*Verify the system will go into immediate alarm if a sensor in group 14 is opened in Arm Away*"));
        System.out.println("*Verify the system will go into immediate alarm if a sensor in group 14 is opened in Arm Away*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INCLOSE);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 59 sec
    @Test
    public void AA_17_dw16() throws Exception {
        add_to_report("GEN-3272");
        log.log(LogStatus.INFO, ("*Verify the system will go into immediate alarm if a sensor in group 16 is opened in Arm Away*"));
        System.out.println("*Verify the system will go into immediate alarm if a sensor in group 16 is opened in Arm Away*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INCLOSE);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 59 sec
    @Test
    public void AA_18_m35() throws Exception {
        add_to_report("GEN-3273");
        log.log(LogStatus.INFO, ("*Verify the system will go into alarm at the end of the entry delay if a sensor in group 35 is Activated in Arm Away*"));
        System.out.println("*Verify the system will go into alarm at the end of the entry delay if a sensor in group 35 is Activated in Arm Away*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(123, 1446, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 11 sec
    @Test
    public void AA_19_m17() throws Exception {
        add_to_report("AA_19");
        log.log(LogStatus.INFO, ("*Verify the system goes into immediate pending alarm and then alarm after dialer delay*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(123, 1441, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(32);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 29 sec
    @Test
    public void AA_20_dw10_dw12() throws Exception {
        add_to_report("AA_20");
        log.log(LogStatus.INFO, ("*Verify the system will report alarm on both sensors at the end of the entry delay*"));
        System.out.println("*Verify the system will report alarm on both sensors at the end of the entry delay*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 13 sec
    @Test
    public void AA_21_dw10_dw13() throws Exception {
        add_to_report("AA_21");
        log.log(LogStatus.INFO, ("*Verify the system will go into immediate alarm when a group 13 sensor is opened and will report alarm on both sensors *"));
        System.out.println("*Verify the system will go into immediate alarm when a group 13 sensor is opened and will report alarm on both sensors *");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 12 sec
    @Test
    public void AA_22_dw10_dw14() throws Exception {
        add_to_report("AA_22");
        log.log(LogStatus.INFO, ("*Verify the system will report alarm on both sensors at the end of the entry delay*"));
        System.out.println("*Verify the system will report alarm on both sensors at the end of the entry delay*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 12 sec
    @Test
    public void AA_23_dw10_dw16() throws Exception {
        add_to_report("AA_23");
        log.log(LogStatus.INFO, ("*Verify the system will report alarm on both sensors at the end of the entry delay*"));
        System.out.println("*Verify the system will report alarm on both sensors at the end of the entry delay*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    } // run time 1 min 12 sec
    @Test
    public void AA_24_dw10_m35() throws Exception {
        add_to_report("AA_24");
        log.log(LogStatus.INFO, ("*Verify the system will report alarm on both sensors at the end of the entry delay*"));
        System.out.println("*Verify the system will report alarm on both sensors at the end of the entry delay*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(123, 1446, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  //run time 1 min 12 sec
    @Test
    public void AA_31_dw8() throws Exception {
        add_to_report("AA_31");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group8*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1127, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1127, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 0 sec
    @Test
    public void AA_32_dw9() throws Exception {
        add_to_report("AA_32");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group9*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group9*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1123, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1123, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 0 sec
    @Test
    public void AA_33_dw10() throws Exception {
        add_to_report("AA_33");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group10*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group10*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 59 sec
    @Test
    public void AA_34_dw12() throws Exception {
        add_to_report("AA_34");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group12*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group12*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 0 sec
    @Test
    public void AA_35_dw13() throws Exception {
        add_to_report("AA_35");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group13*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group13*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1231, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 0 sec
    @Test
    public void AA_36_dw14() throws Exception {
        add_to_report("AA_36");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group14*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group14*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 1 sec
    @Test
    public void AA_37_dw16() throws Exception {
        add_to_report("AA_37");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group16*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group16*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 0 sec
    @Test
    public void AA_38_m15() throws Exception {
        add_to_report("AA_38");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group15*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group15*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(120, 1411, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(120, 1411, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 1 sec
    @Test
    public void AA_39_m17() throws Exception {
        add_to_report("AA_39");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group17*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group17*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(123, 1441, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(123, 1441, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 2 sec
    @Test
    public void AA_40_m20() throws Exception {
        add_to_report("AA_40");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group20*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group20*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(122, 1423, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(122, 1423, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 2 sec
    @Test
    public void AA_41_m35() throws Exception {
        add_to_report("AA_41");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group35*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group35*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(123, 1446, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(123, 1446, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 1 sec
    @Test
    public void AA_42_smoke() throws Exception {
        add_to_report("AA_42");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group26*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group26*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(201, 1541, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(201, 1541, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 1 sec
    @Test
    public void AA_43_co() throws Exception {
        add_to_report("AA_43");
        log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group34*"));
        System.out.println("*Verify the panel will report an immediate tamper alarm group34*");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifySystemState("DISARMED");
        log.log(LogStatus.PASS, ("System is Disarmed"));
        System.out.println("System is Disarmed" + "\n__________________________");
    }  // run time 1 min 1 sec


    @AfterTest(alwaysRun = true)
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }
    public void report_tear_down(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = captureScreenshot(driver, result.getName());
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            log.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
    }

    @AfterMethod
    public void webDriverQuit(ITestResult result) throws IOException {
        report_tear_down(result);
        adc.driver1.quit();
    }
}
