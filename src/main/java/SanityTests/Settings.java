package SanityTests;

import PanelPages.*;
import ServiceCalls.PanelInfo_ServiceCalls;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.RetryAnalizer;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class Settings extends Setup {

    HomePage home;
    EmergencyPage emergency;
    PanelCameraPage camera;
    CameraSettingsPage set_cam;
    SettingsPage settings;
    AdvancedSettingsPage adv;
    InstallationPage inst;
    SecurityArmingPage arming;
    SirenAlarmsPage siren;
    PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();
    String OFF = "00000000";

    public Settings() throws Exception {
        SensorsActivity.init();
    }

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver();
        preconditions();
    }

    @BeforeMethod
    public void reset() throws IOException, InterruptedException {
        disarmServiceCall();
        deleteFromPrimary(3);
    }

    @Test(priority =1, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test1() throws Exception {
        home = PageFactory.initElements(driver, HomePage.class);
        emergency = PageFactory.initElements(driver, EmergencyPage.class);
        camera = PageFactory.initElements(driver, PanelCameraPage.class);
        set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        settings = PageFactory.initElements(driver, SettingsPage.class);
        adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        inst = PageFactory.initElements(driver, InstallationPage.class);

        System.out.println("Alarm_Photos testing");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        try {
            if (set_cam.Alarm_Videos_summery.isDisplayed())
                System.out.println("Setting is disabled, continue with the test.");
        } catch (Exception e) {
            set_cam.Alarm_Videos_summery_enabled.click();
        }
        home.Home_button.click();
        System.out.println("Verifying Alarm photo is taken when setting in enabled...");
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(1000);
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Alarms_photo.click();
        if (camera.Photo_lable.isDisplayed()) {
            System.out.println("Pass: Alarm photo is displayed");
        } else {
            System.out.println("Fail: Alarm photo is NOT displayed");
        }
        Thread.sleep(1000);
        swipeFromLefttoRight();
        Thread.sleep(1000);
        deleteAllCameraPhotos();
        Thread.sleep(1000);

        System.out.println("Verifying Alarm photo is NOT taken when setting in disabled...");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(1000);
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        set_cam.Alarm_Photos.click();
        Thread.sleep(1000);
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
            if (camera.Camera_delete.isDisplayed())
                System.out.println("Fail: Alarm photo is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Alarm photo is not displayed");
        }
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Alarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test2() throws InterruptedException {
        arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        settings = PageFactory.initElements(driver, SettingsPage.class);
        adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        inst = PageFactory.initElements(driver, InstallationPage.class);
        Thread.sleep(3000);
        System.out.println("Navigate to the Installation page to enable the access to the Security and Arming page using Master Code");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        swipeVertical();
        try {
            if (inst.SecAndArmingEnabled.isDisplayed())
                System.out.println("setting is enabled, continue with the test.");
        } catch (Exception e) {
            arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(3000);
        try {
            if (inst.SECURITY_AND_ARMING.isDisplayed())
                System.out.println("Pass: Security and Arming icon is present");
        } catch (Exception e) {
            System.out.println("Failed: Security and Arming icon is NOT present");
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        System.out.println("Verify Security and Arming icon disappears after disabling the setting");
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.SECURITY_AND_ARMING.isDisplayed())
                System.out.println("Failed: Security and Arming icon is present");
        } catch (Exception e) {
            System.out.println("Pass: Security and Arming icon is NOT present");
        } finally {
        }
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test3() throws InterruptedException {
        siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
        settings = PageFactory.initElements(driver, SettingsPage.class);
        adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        inst = PageFactory.initElements(driver, InstallationPage.class);
        Thread.sleep(3000);
        System.out.println("Navigate to the Installation page to enable the access to the Siren and Alarms page using Master Code");
        navigateToAdvancedSettingsPage(); //check if
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        try {
            if (inst.SirenandAlarmsEnabled.isDisplayed())
                System.out.println("setting is enabled, continue with the test to enable it.");
        } catch (Exception e) {
            siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.SIREN_AND_ALARMS.isDisplayed())
                System.out.println("Pass: Siren and Alarms icon is present");
        } catch (Exception e) {
            System.out.println("Failed: Siren and Alarms icon is NOT present");
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
        swipeVertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        System.out.println("Verify Siren and Alarms icon disappears after disabling the setting");
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.SIREN_AND_ALARMS.isDisplayed())
                System.out.println("Failed: Siren and Alarms icon is present");
        } catch (Exception e) {
            System.out.println("Pass: Siren and Alarms icon is NOT present");
        }
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test4() throws Exception {
        serv.set_ARM_STAY_NO_DELAY_enable();
        arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        settings = PageFactory.initElements(driver, SettingsPage.class);
        adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        inst = PageFactory.initElements(driver, InstallationPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        System.out.println("Verify that Arm Stay - No Delay works when enabled");
        ARM_STAY();
        Thread.sleep(2000);
        verifySystemState("ARMED STAY");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Verify that Arm Stay - No Delay does not work when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        arming.Arm_Stay_No_Delay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        ARM_STAY();
        try {
            if (home.Disarmed_text.getText().equals("ARMED STAY"))
                System.out.println("Failed: System is ARMED STAY");
        } catch (Exception e) {
            System.out.println("Pass: System is NOT ARMED STAY");
        }
        Thread.sleep(15000);
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Arm_Stay_No_Delay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test5() throws Exception {
        System.out.println("Settings.Auto_Bypass");
        arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        settings = PageFactory.initElements(driver, SettingsPage.class);
        adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        inst = PageFactory.initElements(driver, InstallationPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Adding sensors...");
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
        System.out.println("Verify that Auto Bypass works when enabled");
        primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(3000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(3000);
        primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        primaryCall("65 00 0A", SensorsActivity.CLOSE);
        verifySystemState("ARMED STAY");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(3000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        swipeVertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(3000);
        settings.Home_button.click();
        Thread.sleep(3000);
        System.out.println("Verify that Auto Bypass does not work when disabled");
        primaryCall("65 00 0A", SensorsActivity.OPEN);
        home.DISARM.click();
        Thread.sleep(2000);
        home.ARM_STAY.click();
        Thread.sleep(4000);
        if (home.Message.isDisplayed()) {
            System.out.println("Pass: Bypass message is displayed");
        } else
            Thread.sleep(2000);
        home.Bypass_OK.click();
        primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        verifySystemState("ARMED STAY");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        deleteFromPrimary(3);
        Thread.sleep(2000);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test6() throws Exception {
        System.out.println("Settings.Auto_Exit_Time_Extention");
        arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        settings = PageFactory.initElements(driver, SettingsPage.class);
        adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        inst = PageFactory.initElements(driver, InstallationPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        System.out.println("Verify that Auto Exit Time Extension works when enabled");
        System.out.println("Adding sensors...");
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
        ARM_AWAY(3);
        primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        try {
            if (home.ArwAway_State.isDisplayed())
                System.out.println("Failed: System is ARMED AWAY");
        } catch (Exception e) {
            System.out.println("Pass: System is NOT ARMED AWAY");
        }
        Thread.sleep(50000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Verify that Auto Exit Time Extension does not works when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Exit_Time_Extension.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        ARM_AWAY(3);
        primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(14000);
        try {
            if (home.ArwAway_State.isDisplayed())
                System.out.println("Pass: System is ARMED AWAY");
        } catch (Exception e) {
            System.out.println("Fail: System is NOT ARMED AWAY");
        }
        Thread.sleep(14000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical(); //sometimes swipe is good some times too far
        arming.Auto_Exit_Time_Extension.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        deleteFromPrimary(3);
        Thread.sleep(2000);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test7() throws Exception {
        System.out.println("Settings.Auto_Stay");
        int delay = 15;
        arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        settings = PageFactory.initElements(driver, SettingsPage.class);
        adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        inst = PageFactory.initElements(driver, InstallationPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Adding sensors...");
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
        System.out.println("Verify that Auto Stay works when enabled");
        Thread.sleep(2000);
        System.out.println("Arm Away the system");
        ARM_AWAY(delay);
        verifySystemState("ARMED STAY");
        home.DISARM.click();
        enterDefaultUserCode();
        System.out.println("Verify that Auto Stay does not works when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        System.out.println("Arm Away the system");
        ARM_AWAY(delay);
        verifySystemState("ARMED AWAY");
        home.ArwAway_State.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        deleteFromPrimary(3);
        Thread.sleep(2000);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test8() throws Exception {
        System.out.println("Settings.Keyfob_Alarm_Disarm");
        String disarm = "08 01";
        arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        settings = PageFactory.initElements(driver, SettingsPage.class);
        adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        inst = PageFactory.initElements(driver, InstallationPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        emergency = PageFactory.initElements(driver, EmergencyPage.class);
        System.out.println("Adding sensors...");
        addPrimaryCall(3, 4, 6619386, 102);
        Thread.sleep(2000);
        System.out.println("Verify that keyfob Alarm Disarm does not work when disabled");
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        if (emergency.Police_Emergency_Alarmed.isDisplayed()) {
            System.out.println("Pass: Police Emergency is displayed");
        } else {
            System.out.println("Failed: Police Emergency is NOT displayed");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Verify that keyfob Alarm Disarm  works when enabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Alarm_Disarm.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifySystemState("DISARMED");
        System.out.println("Pass: System is disarmed by a keyfob");
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Alarm_Disarm.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        deleteFromPrimary(3);
        Thread.sleep(2000);
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test9() throws Exception {
        System.out.println("Settings.Keyfob_Disarming");
        int delay = 15;
        String disarm = "08 01";
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelInfo_ServiceCalls service = PageFactory.initElements(driver, PanelInfo_ServiceCalls.class);
        System.out.println("Adding sensors...");
        addPrimaryCall(3, 4, 6619386, 102);
        Thread.sleep(2000);
        System.out.println("Verify that Keyfob Disarming works when enabled");
        ARM_STAY();
        Thread.sleep(2000);
        primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifySystemState("DISARMED");
        System.out.println("Pass: System is disarmed by a keyfob from Arm Stay");
        Thread.sleep(2000);
        ARM_AWAY(delay);
        Thread.sleep(2000);
        primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifySystemState("DISARMED");
        System.out.println("Pass: System is disarmed by a keyfob from Arm Away");
        Thread.sleep(2000);
        System.out.println("Verify that Keyfob Disarming does not work when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Disarming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifySystemState("ARMED STAY");
        System.out.println("Pass: System is NOT disarmed by a keyfob from Arm Stay");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        ARM_AWAY(delay);
        Thread.sleep(2000);
        primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifySystemState("ARMED AWAY");
        System.out.println("Pass: System is NOT disarmed by a keyfob from Arm Away");
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Disarming.click();
        Thread.sleep(2000);
        deleteFromPrimary(3);
        settings.Home_button.click();
        service.set_AUTO_STAY(01);
        Thread.sleep(2000);
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test10() throws Exception {
        String armstay = "04 01";
        String armaway = "04 04";
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
        System.out.println("Verify that Keyfob Instant Arming works when enabled");
        addPrimaryCall(3, 4, 6619386, 102);
        System.out.println("Arm Stay the system");
        Thread.sleep(3000);
        primaryCall("65 00 AF", armstay);
        Thread.sleep(3000);
        try {
            if (home.ARM_STAY_text.isDisplayed()) {
                System.out.println("Pass: System ARMED STAY, countdown is NOT displayed");
            } else {
                System.out.println("Fail: System is not ARMED STAY");
            }
        } catch (NoSuchElementException e) {
        }
        Thread.sleep(3000);
        DISARM();
        Thread.sleep(2000);
        System.out.println("Arm Away the system");
        primaryCall("65 00 AF", armaway);
        Thread.sleep(3000);
        verifySystemState("ARMED AWAY");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);

        System.out.println("Verify that Keyfob Instant Arming does not work when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        System.out.println("Arm Stay the system");
        primaryCall("65 00 AF", armstay);
        Thread.sleep(2000);
        verifySystemState("ARMED STAY");
        Thread.sleep(10000);
        DISARM();
        Thread.sleep(2000);
        System.out.println("Arm Away the system");
        primaryCall("65 00 AF", armaway);
        Thread.sleep(2000);
        verifySystemState("ARMED AWAY");
        Thread.sleep(12000);
        DISARM();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        deleteFromPrimary(3);
        Thread.sleep(2000);
    }

    @Test(priority = 11, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test11() throws Exception {
        System.out.println("Settings.Panic_Disable");
        SirenAlarmsPage siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        Thread.sleep(1000);
        System.out.println("Verify panic disappears from the Emergency page when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        System.out.println("Disable Police Panic");
        siren.Police_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Police_icon.isDisplayed())
                System.out.println("Failed: Police Emergency is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Police Emergency is NOT displayed");
        }
        swipeFromLefttoRight();
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
        System.out.println("Disable Fire Panic");
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Fire_icon.isDisplayed())
                System.out.println("Failed: Fire Emergency is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Fire Emergency is NOT displayed");
        }
        swipeFromLefttoRight();
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
        System.out.println("Disable Auxiliary Panic");
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        try {
            if (settings.Emergency_button.isDisplayed())
                System.out.println("Failed: Emergency button is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Emergency button is NOT displayed");
        }
        siren.Police_Panic.click();
        siren.Fire_Panic.click();
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 12, retryAnalyzer = RetryAnalizer.class)
    public void Settings_Test12() throws Exception {
        System.out.println("Settings.Secure_Arming");
        settings = PageFactory.initElements(driver, SettingsPage.class);
        arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        inst = PageFactory.initElements(driver, InstallationPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        System.out.println("Verify no code is required to Arm the system when setting is disabled");
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(2000);
        verifySystemState("ARMED STAY");
        System.out.println("Pass: System is in Arm Stay mode, no password was required");
        DISARM();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        arming.Secure_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        System.out.println("Verify code is required to Arm the system when setting is enabled");
        ARM_STAY();
        try {
            if (home.Enter_Code_to_Access_the_Area.isDisplayed()) {
                System.out.println("Pass: code is requires to Arm the system");
            } else {
                System.out.println("FAIL: no code is requires to Arm the system");
            }
        } catch (NoSuchElementException e) {}
        enterDefaultUserCode();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        arming.Secure_Arming.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown() throws Exception {
        quitDriver();
    }
}
