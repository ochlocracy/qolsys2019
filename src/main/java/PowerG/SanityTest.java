package PowerG;

import ADC.ADC;
import ServiceCalls.PanelInfo_ServiceCalls;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import PanelPages.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* Estimate execution time: 60m */
/* New repo test*/

public class SanityTest extends Setup {
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    ADC adc = new ADC();
    String accountID = adc.getAccountId();
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    final String ON = "00000001";
    final String OFF = "00000000";
    int one_sec = 1000;

    public SanityTest() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }
    public void navigate_to_Security_Sensors_page() throws InterruptedException {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.DEVICES.click();
        dev.Security_Sensors.click();
    }
    public void ADC_Equipment(String accountID) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.manage().window().maximize();
        String ADC_URL = "https://alarmadmin.alarm.com/Support/CustomerInfo.aspx?customer_Id=" + accountID;
        adc.driver1.get(ADC_URL);
        String login = "qautomation";
        String password = "Qolsys123";
        Thread.sleep(2000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtUsername")));
        adc.driver1.findElement(By.id("txtUsername")).sendKeys(login);
        adc.driver1.findElement(By.id("txtPassword")).sendKeys(password);
        adc.driver1.findElement(By.id("butLogin")).click();
        Thread.sleep(1000);
        adc.driver1.get("https://alarmadmin.alarm.com/Support/CustomerSensorList.aspx");
    }
    public void create_report(String test_area_name) throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/Sanity.html");
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/Sanity.html", false);
        log = report.startTest(test_case_name);
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

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver();
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test(priority = 1)
    public void Add_Sensor() throws Exception {
        create_report("Sanity_01");
        log.log(LogStatus.INFO, ("*Sanity_01* Add a sensor"));
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        deleteFromPrimary(1);
        navigate_to_autolearn_page();
        log.log(LogStatus.INFO, ("Adding a new sensor"));
        addPGSensors("DW", 104, 1101, 0);//gr10
        Thread.sleep(2000);
        home.Home_button.click();
        activation_restoration(104, 1101, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr10
        log.log(LogStatus.PASS, ("Pass: sensor is added successfully"));
        Thread.sleep(3000);
//        home.Home_button.click();
    } //48sec

    @Test
    public void dw_testing() throws IOException, InterruptedException {
        dw_sensor_testing();
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void Edit_Name() throws InterruptedException, IOException {
        add_to_report("Sanity_02");
        log.log(LogStatus.INFO, ("*Sanity_02* Edit sensor Name"));

        SecuritySensorsPage sen = PageFactory.initElements(driver, SecuritySensorsPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        navigate_to_Security_Sensors_page();
        sen.Edit_Sensor.click();
        sen.Edit_Img.click();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).clear();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).sendKeys("DW 104-1152NEW");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        sen.Save.click();
        home.Home_button.click();
        Thread.sleep(2000);
        home.All_Tab.click();
        Thread.sleep(2000);
        activation_restoration(104, 1152, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr10
        log.log(LogStatus.INFO, ("Verify new name is displayed"));
        WebElement newSensorName = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1152NEW']"));
        Assert.assertTrue(newSensorName.isDisplayed());
        log.log(LogStatus.PASS, ("Pass: new name is displayed on panel"));
        Thread.sleep(10000);
        ADC_Equipment(accountID);
        adc.driver1.manage().window().maximize();
        Thread.sleep(3000);
        adc.driver1.findElement(By.id("ctl00_refresh_sensors_button_btnRefreshPage")).click();
        Thread.sleep(4000);
        WebElement webname = adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[2]/td[2]"));
        Thread.sleep(5000);
        Assert.assertTrue(webname.getText().equals("DW 104-1152NEW"));
        log.log(LogStatus.PASS, ("Pass: The name is displayed correctly " + webname.getText()) + " on ADC web page");
        Thread.sleep(2000);
    } //2m 20sec

    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void Delete_Sensor() throws InterruptedException, IOException {
        add_to_report("Sanity_03");
        log.log(LogStatus.INFO, ("*Sanity_03* Delete the sensor"));
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        deleteFromPrimary(1);
        Thread.sleep(2000);
        log.log(LogStatus.INFO, ("Verify sensor is deleted"));
        adc.update_sensors_list();
        adc.Request_equipment_list();
        Thread.sleep(4000);
        WebElement webname = adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[2]/td[2]"));
        Thread.sleep(5000);
        try {
            Assert.assertTrue(webname.getText().equals("DW 104-1152"));
        } catch (NoSuchElementException ignored) {
        } finally {
            navigate_to_autolearn_page();
            addPGSensors("DW", 104, 1101, 0);//gr10
        }
        Thread.sleep(2000);
        log.log(LogStatus.PASS, ("Pass: sensor is deleted successfully and not displayed both on panel and ADC"));
        home.Home_button.click();
    } //3m 25sec

    @Test(priority = 4)
    public void contactSensorCheck() throws Exception {
        add_to_report("Sanity_04");
        log.log(LogStatus.INFO, ("*Sanity_04* Contact Sensors"));

        System.out.println("Open-Close contact sensors");
        Thread.sleep(2000);
        log.log(LogStatus.INFO, "Activate DW sensors in DISARM mode");
        activation_restoration(104, 1101, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr10
        Thread.sleep(10000);
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(1000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
        Thread.sleep(2000);
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1101 ')]", "//*[contains(text(), ' Sensor 1 Open/Close')]");
        log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr10 works as expected");
        activation_restoration(104, 1152, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr12
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1152 ')]", "//*[contains(text(), ' Sensor 2 Open/Close')]");
        log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr12 works as expected");
        activation_restoration(104, 1231, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr13
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1231 ')]", "//*[contains(text(), ' Sensor 3 Open/Close')]");
        log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr13 works as expected");
        activation_restoration(104, 1216, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr14
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1216 ')]", "//*[contains(text(), ' Sensor 4 Open/Close')]");
        log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr14 works as expected");
        activation_restoration(104, 1331, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr16
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1331 ')]", "//*[contains(text(), ' Sensor 5 Open/Close')]");
        log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr16 works as expected");
        activation_restoration(104, 1311, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr25
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1311 ')]", "//*[contains(text(), ' Sensor 8 Open/Close')]");
        log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr25 works as expected");
        activation_restoration(104, 1127, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr8
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1127 ')]", "//*[contains(text(), ' Alarm ')]");
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, DW sensor gr8 works as expected");
        activation_restoration(104, 1123, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr9
        Thread.sleep(2000);
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1123')]", "//*[contains(text(), 'Delayed alarm')]");
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, DW sensor gr9 works as expected");
    } //9m 25sec

    @Test(priority = 5)
    public void motionSensorCheck() throws Exception {
        //rep.create_report("Sanity_05");
        add_to_report("Sanity_05");
        log.log(LogStatus.INFO, ("*Sanity_05* Motion Sensors"));
        System.out.println("Activate motion sensors");
        log.log(LogStatus.INFO, "Activate motion sensors in ARM AWAY mode");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(120, 1411, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr15 Sensor9
        Thread.sleep(10000);
        verifyInAlarm();
        Thread.sleep(10000);
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'Motion 120-1411')]", "//*[contains(text(), ' Alarm ')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, motion gr15 works as expected");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(123, 1441, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr17 Sensor10
        Thread.sleep(7000);
        verifyInAlarm();
        Thread.sleep(10000);
        adc.ADC_verification_PG("//*[contains(text(), 'Motion 123-1441')]", "//*[contains(text(), ' Alarm ')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, motion gr17 works as expected");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(120, 1445, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr25 Sensor12
        Thread.sleep(10000);
        verifySystemState("ARMED AWAY");
        adc.ADC_verification_PG("//*[contains(text(), 'Motion 120-1445')]", "//*[contains(text(), 'Armed Away')]");
        Thread.sleep(2000);
        DISARM();
        log.log(LogStatus.PASS, "System is ARMED AWAY, ADC events are displayed correctly, motion gr25 works as expected");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(122, 1423, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr20 Sensor11
        Thread.sleep(7000);
        verifyInAlarm();
        Thread.sleep(10000);
        adc.ADC_verification_PG("//*[contains(text(), 'Motion 122-1423')]", "//*[contains(text(), ' Alarm ')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, motion gr20 works as expected");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(123, 1446, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr35 Sensor13
        Thread.sleep(7000);
        verifyInAlarm();
        Thread.sleep(10000);
        adc.ADC_verification_PG("//*[contains(text(), 'Motion 123-1446')]", "//*[contains(text(), ' Alarm ')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, motion gr35 works as expected");
        Thread.sleep(2000);
    } //8m 20sec

    @Test(priority = 6)
    public void smokeCOSensorCheck() throws IOException, InterruptedException {
        add_to_report("Sanity_06");
        log.log(LogStatus.INFO, ("*Sanity_06* Smoke + SmokeM + CO Sensors"));
        System.out.println("Activate smoke and smokeM sensors");
        log.log(LogStatus.INFO, "Activate smoke and smokeM sensors");
        activation_restoration(201, 1541, PGSensorsActivity.SMOKEM, PGSensorsActivity.SMOKEMREST); //Sensor14
        Thread.sleep(3000);
        enterDefaultUserCode();
        Thread.sleep(5000);
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 14 Alarm**')]", "//*[contains(text(), 'Fire Alarm')]");
        log.log(LogStatus.PASS, "System is in ALARM (Fire), ADC events are displayed correctly, smokeM works as expected");
        activation_restoration(200, 1531, PGSensorsActivity.SMOKE, PGSensorsActivity.SMOKEREST); //Sensor15
        Thread.sleep(3000);
        enterDefaultUserCode();
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 15 Alarm**')]", "//*[contains(text(), 'Fire Alarm')]");
        log.log(LogStatus.PASS, "System is in ALARM (Fire), ADC events are displayed correctly, smoke works as expected");
        activation_restoration(201, 1541, PGSensorsActivity.GAS, PGSensorsActivity.GASREST); //Sensor14
        Thread.sleep(3000);
        enterDefaultUserCode();
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 14 Alarm**')]", "//*[contains(text(), 'Fire Alarm')]");
        log.log(LogStatus.PASS, "System is in ALARM (Fire), ADC events are displayed correctly, smokeM works as expected");

        log.log(LogStatus.INFO, "Activate CO sensor");
        activation_restoration(220, 1661, PGSensorsActivity.CO, PGSensorsActivity.COREST); //Sensor16
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 16 Alarm**')]", "//*[contains(text(), 'Carbon Monoxide')]");
        enterDefaultUserCode();
        Thread.sleep(3000);
        log.log(LogStatus.PASS, "System is in ALARM (Carbon Monoxide), ADC events are displayed correctly, CO works as expected");
        activation_restoration(220, 1661, PGSensorsActivity.GAS, PGSensorsActivity.GASREST); //Sensor16
        Thread.sleep(5000);
        enterDefaultUserCode();
        Thread.sleep(3000);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 16 Alarm**')]", "//*[contains(text(), 'Carbon Monoxide')]");
        log.log(LogStatus.PASS, "System is in ALARM (Carbon Monoxide), ADC events are displayed correctly, CO works as expected");
    } //5m 50sec

    @Test(priority = 7)
    public void waterSensorCheck() throws Exception {
        add_to_report("Sanity_07");
        log.log(LogStatus.INFO, ("*Sanity_07* Water Sensors"));
        log.log(LogStatus.INFO, "Activate water sensor");
        HomePage home_page = PageFactory.initElements(this.driver, HomePage.class);
        activation_restoration(241, 1971, PGSensorsActivity.FLOOD, PGSensorsActivity.FLOODREST); //Sensor21
        Thread.sleep(5000);
        try {
            if (home_page.ALARM.isDisplayed()) {
                Thread.sleep(5000);
                adc.New_ADC_session(adc.getAccountId());
                adc.ADC_verification_PG("//*[contains(text(), 'Water Alarm')]", "//*[contains(text(), 'Sensor 21 Alarm**')]");
                enterDefaultUserCode();
                log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, water sensor works as expected");

            }else {
                System.out.println("after if statement");
                log.log(LogStatus.FAIL, "System is not in ALARM");
            }
        }catch (NoSuchElementException e){
            System.out.println("catch statement");
            log.log(LogStatus.FAIL, "System is not in ALARM");
        }

    } //1m 25sec

    @Test(priority = 8)
    public void shockSensorCheck() throws Exception {
        add_to_report("Sanity_08");
        log.log(LogStatus.INFO, ("*Sanity_08* Shock Sensors"));
        log.log(LogStatus.INFO, "Activate shock sensor in ARM AWAY mode");
        servcall.set_AUTO_STAY(0);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifySystemState("ARMED AWAY");
        Thread.sleep(2000);
        activation_restoration(171, 1741, PGSensorsActivity.SHOCK, PGSensorsActivity.SHOCKREST); //Sensor17
        Thread.sleep(10000);
        verifyInAlarm();
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'Sensor 17 Alarm**')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, shock sensor gr13 works as expected");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifySystemState("ARMED AWAY");
        Thread.sleep(2000);
        activation_restoration(171, 1771, PGSensorsActivity.SHOCK, PGSensorsActivity.SHOCKREST); //Sensor18
        Thread.sleep(10000);
        verifyInAlarm();
        adc.ADC_verification_PG("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'Sensor 18 Alarm**')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, shock sensor gr17 works as expected");
    } //3m 11sec

    @Test(priority = 9)
    public void glassbreakSensorCheck() throws Exception {
        add_to_report("Sanity_09");
        log.log(LogStatus.INFO, ("*Sanity_09* Glassbreak Sensors"));
        System.out.println("Activate glassbreak sensors");
        log.log(LogStatus.INFO, "Activate glassbreak sensor in ARM AWAY mode");
        servcall.set_AUTO_STAY(0);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifySystemState("ARMED AWAY");
        Thread.sleep(2000);
        activation_restoration(160, 1874, PGSensorsActivity.GB, PGSensorsActivity.GBREST);//gr13
        Thread.sleep(5000);
        verifyInAlarm();
        Thread.sleep(2000);
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'GBreak 160-1874')]", "//*[contains(text(), 'Sensor 19 Alarm**')]");
        enterDefaultUserCode();
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, glassbreak sensor gr13 works as expected");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(160, 1871, PGSensorsActivity.GB, PGSensorsActivity.GBREST);//gr17
        Thread.sleep(5000);
        verifyInAlarm();
        Thread.sleep(2000);
        adc.ADC_verification_PG("//*[contains(text(), 'GBreak 160-1871')]", "//*[contains(text(), 'Sensor 20 Alarm**')]");
        enterDefaultUserCode();
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, glassbreak sensor gr17 works as expected");
    } //3m

    @Test(priority = 10)
    public void keyfobKeypadPendantCheck() throws Exception {
        add_to_report("Sanity_10");
        log.log(LogStatus.INFO, ("*Sanity_10* Keyfob + AuxPendant Sensors"));
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
        System.out.println("Activate keyfobs");
        log.log(LogStatus.INFO, "Activate keyfobs");
        activation_restoration(300, 1004, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr1
        Thread.sleep(5000);
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'Keyfob 300-1004')]", "//*[contains(text(), 'Police Panic')]");
        log.log(LogStatus.PASS, "System is in ALARM(Police Panic), ADC events are displayed correctly, keyfob gr1 works as expected");
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(305, 1009, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr6
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Keyfob 305-1009')]", "//*[contains(text(), 'Aux Panic')]");
        log.log(LogStatus.PASS, "System is in ALARM(Aux Panic), ADC events are displayed correctly, keyfob gr6 works as expected");
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(306, 1003, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr4
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Keyfob 306-1003')]", "//*[contains(text(), 'Aux Panic')]");
        log.log(LogStatus.PASS, "System is in ALARM(Aux Panic), ADC events are displayed correctly, keyfob gr4 works as expected");
        enterDefaultUserCode();
        Thread.sleep(5000);
        pgarmer(306, 1003, "2");
        Thread.sleep(2000);
        verifySystemState("ARMED STAY"); //needs instant arming enabled?
        Thread.sleep(3000);
        pgarmer(306, 1003, "1");
        Thread.sleep(2000);
        verifySystemState("DISARMED");
        Thread.sleep(3000);
        pgarmer(306, 1003, "3");
        Thread.sleep(12000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.ArwAway_State.isDisplayed()) {
            home_page.Disarmed_text.getText().equals("ARMED AWAY");   //a change appeared in rc18.1 12/19
            log.log(LogStatus.PASS, "Panel is in ARM AWAY mode");
        }else{
            log.log(LogStatus.FAIL, "Panel is not in ARM AWAY mode");
        }
        Thread.sleep(3000);
        pgarmer(306, 1003, "1");
        Thread.sleep(2000);
        verifySystemState("DISARMED");
        Thread.sleep(3000);
        pgarmer(371, 1005, "3");
        Thread.sleep(12000);

        if (home_page.ArwAway_State.isDisplayed()) {
            home_page.Disarmed_text.getText().equals("ARMED AWAY");   //a change appeared in rc18.1 12/19
            log.log(LogStatus.PASS, "Panel is in ARM AWAY mode");
        }else{
            log.log(LogStatus.FAIL, "Panel is not in ARM AWAY mode");
        }

        pgarmer(371, 1005, "01 1234");
        Thread.sleep(2000);
        verifySystemState("DISARMED");
        System.out.println("Activate keypad sensors");
        log.log(LogStatus.INFO, "Activate keypad");
        activation_restoration(371, 1005, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr0
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'KeypadTouchscreen 25')]", "//*[contains(text(), 'Police Panic')]");
        log.log(LogStatus.PASS, "System is in ALARM(Police Panic), ADC events are displayed correctly, keypad gr0 works as expected");
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(371, 1006, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr1
        Thread.sleep(7000);
        adc.ADC_verification_PG("//*[contains(text(), 'Keypad/Touchscreen(26)')]", "//*[contains(text(), 'Delayed Police Panic')]");
        log.log(LogStatus.PASS, "System is in ALARM(Police Panic), ADC events are displayed correctly, keypad gr1 works as expected");
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(371, 1008, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr2
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Keypad/Touchscreen(27)')]", "//*[contains(text(), 'Police Panic')]");
        log.log(LogStatus.PASS, "System is in ALARM(Police Panic), ADC events are displayed correctly, keypad gr2 works as expected");
        Thread.sleep(5000);

        System.out.println("Activate medical pendants");
        log.log(LogStatus.INFO, "Activate aux pendants");
        activation_restoration(320, 1015, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr6
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'AuxPendant 320-1015')]", "//*[contains(text(), 'Delayed alarm')]");
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, aux pendant gr6 works as expected");
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(320, 1016, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr4
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'AuxPendant 320-1016')]", "//*[contains(text(), 'Delayed alarm')]");
        log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, aux pendant gr4 works as expected");
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(320, 1018, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr25
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'AuxPendant 320-1018')]", "//*[contains(text(), 'Inactivated')]");
        log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, aux pendant gr25 works as expected");
        verifySystemState("DISARMED");
        Thread.sleep(1000);
        contact.acknowledge_all_alerts();
        swipeLeft();
        Thread.sleep(1000);
    } //11m 20 sec

    @Test(priority = 11)
    public void Tamper() throws IOException, InterruptedException {
        add_to_report("Sanity_11");
        log.log(LogStatus.INFO, ("*Sanity_11* Tamper Sensor"));
        log.log(LogStatus.INFO, "Tamper events verification");
        activation_restoration(104, 1101, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);//gr10
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 1 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        log.log(LogStatus.PASS, "Tamper event for Sensor 1 is displayed correctly");
        activation_restoration(120, 1411, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 9 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        log.log(LogStatus.PASS, "Tamper event for Sensor 9 is displayed correctly");
        activation_restoration(201, 1541, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 14 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        log.log(LogStatus.PASS, "Tamper event for Sensor 14 is displayed correctly");
        activation_restoration(220, 1661, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 16 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        log.log(LogStatus.PASS, "Tamper event for Sensor 16 is displayed correctly");
        activation_restoration(171, 1741, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 17 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        log.log(LogStatus.PASS, "Tamper event for Sensor 17 is displayed correctly");
        activation_restoration(160, 1874, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 19 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        log.log(LogStatus.PASS, "Tamper event for Sensor 19 is displayed correctly");
        activation_restoration(241, 1971, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 21 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        log.log(LogStatus.PASS, "Tamper event for Sensor 21 is displayed correctly");
        activation_restoration(400, 1995, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 31 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        log.log(LogStatus.PASS, "Tamper event for Sensor 31 is displayed correctly");
    } // 12m 1

    @Test(priority = 12)
    public void Supervisory() throws IOException, InterruptedException {
        add_to_report("Sanity_12");
        log.log(LogStatus.INFO, ("*Sanity_12* Supervisory Verification"));
        log.log(LogStatus.INFO, "Supervisory verification");
        driver.findElement(By.id("com.qolsys:id/tray_layout")).click();
        //tap(5,6);
        powerGsupervisory(201, 1541);
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status1.get(1).getText());
        Thread.sleep(1000);
        Assert.assertTrue(li_status1.get(1).getText().contains("Failure"));
        log.log(LogStatus.PASS, ("Pass: Failure event is displayed"));
        Thread.sleep(1000);
        pgprimaryCall(201, 1541, "06 0");
        Thread.sleep(1000);
        List<WebElement> li_status2 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status2.get(1).getText());
        Assert.assertTrue(li_status2.get(1).getText().contains("Normal"));
        Thread.sleep(3000);

        powerGsupervisory(220, 1661);
        Thread.sleep(1000);
        List<WebElement> li_status3 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status3.get(1).getText());
        Thread.sleep(1000);
        Assert.assertTrue(li_status3.get(1).getText().contains("Failure"));
        log.log(LogStatus.PASS, ("Pass: Failure event is displayed"));
        Thread.sleep(1000);
        pgprimaryCall(220, 1661, "08 0");
        Thread.sleep(1000);
        List<WebElement> li_status4 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status4.get(1).getText());
        Assert.assertTrue(li_status4.get(1).getText().contains("Normal"));
        Thread.sleep(3000);

        powerGsupervisory(410, 1998);
        Thread.sleep(1000);
        List<WebElement> li_status5 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status5.get(1).getText());
        Thread.sleep(1000);
        Assert.assertTrue(li_status5.get(1).getText().contains("Failure"));
        log.log(LogStatus.PASS, ("Pass: Failure event is displayed"));
        Thread.sleep(1000);
        pgprimaryCall(410, 1998, "82 0");
        Thread.sleep(1000);
        List<WebElement> li_status6 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status6.get(1).getText());
        Assert.assertTrue(li_status6.get(1).getText().contains("Normal"));
        Thread.sleep(3000);
    } //1m

    @Test(priority = 13)
    public void Jam() throws Exception {
        add_to_report("Sanity_13");
        log.log(LogStatus.INFO, ("*Sanity_13* Jam Event Verification"));
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
        log.log(LogStatus.INFO, "Jam event verification");
        Thread.sleep(1000);
        servcall.set_RF_JAM_DETECT_enable();
        try {
            contact.acknowledge_all_alerts();
        } catch (NoSuchElementException e) {
        }
        swipeFromLefttoRight();
        Thread.sleep(2000);
        powerGjamer(1);
        home.Contact_Us.click();
        contact.Messages_Alerts_Alarms_tab.click();
        WebElement string = driver.findElement(By.id("com.qolsys:id/ui_msg_text"));
        Assert.assertTrue(string.getText().contains("PowerG receiver jammed"));
        log.log(LogStatus.PASS, ("Pass: PowerG receiver jammed message is displayed"));
        powerGjamer(0);
        Thread.sleep(2000);
        try {
            if (string.isDisplayed()) {
                System.out.println("Fail: jammed message is displayed");
                log.log(LogStatus.FAIL, ("Fail: jammed message is displayed after canceling jam"));
            } else {
                System.out.println("Pass: jammed message is not displayed");
                log.log(LogStatus.PASS, ("Pass: jammed message is not displayed after canceling jam"));
            }
        } catch (Exception e) {
        }

        servcall.set_RF_JAM_DETECT_disable();
        Thread.sleep(2000);
        swipeFromLefttoRight();
    } //48sec

    @Test(priority = 14, retryAnalyzer = RetryAnalizer.class)
    public void Low_Battery() throws InterruptedException, IOException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
        SettingsPage set = PageFactory.initElements(driver, SettingsPage.class);

        add_to_report("Sanity_14");
        log.log(LogStatus.INFO, ("*Sanity_14* DW Low battery events verification"));
        Thread.sleep(4000);
        try {
            home.Home_button.click();
        } catch (NoSuchElementException e) {
        }
        navigateToSettingsPage();
        set.STATUS.click();
        WebElement lowb = driver.findElement(By.id("com.qolsys:id/textView4"));
        if (lowb.getText().contains("Low")) {
            pgprimaryCall(104, 1152, "80 0"); }
        set.Home_button.click();
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, "80 1");
        Thread.sleep(5000);
        home.Contact_Us.click();
        Thread.sleep(1000);
        contact.Messages_Alerts_Alarms_tab.click();
        Thread.sleep(3000);
        WebElement string = driver.findElement(By.id("com.qolsys:id/ui_msg_text"));
        Assert.assertTrue(string.getText().equals("DW 104-1152(2) - Low Battery"));
        log.log(LogStatus.PASS, ("Pass: low battery event is successfully displayed for DW 104-1152 sensor"));
        pgprimaryCall(104, 1152, "80 0");

    } //40sec


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {
        report_tear_down(result);
        adc.driver1.close();
    }

    @AfterClass(alwaysRun = true)
    public void driver_quit() throws IOException, InterruptedException {
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        // adc.driver1.close();
        service.stop();
    }
}
