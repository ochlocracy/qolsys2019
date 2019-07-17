package SanityTests;

import ServiceCalls.PanelInfo_ServiceCalls;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;
import java.io.IOException;

/**
 * this class customizes default settings values
 */

public class PreUpdateSettings extends Setup {
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public PreUpdateSettings() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver();
    }

    @Test
    public void invertSettings() throws IOException, InterruptedException {
        int ON = 1;
        int OFF = 0;
        int one_sec = 1000;

        System.out.println("Changing default settings values... waiting time 1min 15sec");
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(one_sec);
        servcall.set_SPEAKER_VOLUME(1);
        Thread.sleep(one_sec);
        servcall.set_ALL_VOICE_PROMPTS(OFF);
        Thread.sleep(one_sec);
        servcall.set_ALL_CHIMES(ON);
        Thread.sleep(one_sec);
        servcall.set_ENABLE_ALL_TROUBLE_BEEPS(ON);
        Thread.sleep(one_sec);
        servcall.set_FIRE_SAFETY_DEVICE_TROUBLE_BEEPS(ON);
        Thread.sleep(one_sec);
        servcall.set_WiFi(ON);
        Thread.sleep(one_sec);
        servcall.set_SECURE_DELETE_IMAGES(OFF);
        Thread.sleep(one_sec);
        servcall.set_DISARM_PHOTO(OFF);
        Thread.sleep(one_sec);
        servcall.set_ALARM_VIDEOS(OFF);
        Thread.sleep(one_sec);
        servcall.set_ALARM_PHOTOS(OFF);
        Thread.sleep(one_sec);
        servcall.set_SETTINGS_PHOTOS(ON);
        Thread.sleep(one_sec);
        servcall.set_DURESS_AUTHENTICATION_enable();
        Thread.sleep(one_sec);
        servcall.set_SECURE_ARMING_enable();
        Thread.sleep(one_sec);
        servcall.set_NO_ARMING_ON_LOW_BATTERY_enable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_BYPASS(OFF);
        Thread.sleep(one_sec);
        servcall.set_AUTO_STAY(OFF);
        Thread.sleep(one_sec);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_EXIT_TIME_EXTENSION(OFF);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_ALARM_DISARM(ON);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_DISARMING(OFF);
        Thread.sleep(one_sec);
        servcall.set_NORMAL_ENTRY_DELAY(11);
        Thread.sleep(one_sec);
        servcall.set_NORMAL_EXIT_DELAY(10);
        Thread.sleep(one_sec);
        servcall.set_LONG_ENTRY_DELAY(13);
        Thread.sleep(one_sec);
        servcall.set_LONG_EXIT_DELAY(12);
        Thread.sleep(one_sec);
        servcall.set_SIREN_DISABLE(OFF);
        Thread.sleep(one_sec);
        servcall.set_FIRE_VERIFICATION(ON);
        Thread.sleep(one_sec);
        servcall.set_SEVERE_WEATHER_SIREN_WARNING(OFF);
        Thread.sleep(one_sec);
        servcall.set_DIALER_DELAY(23);
        Thread.sleep(one_sec);
        servcall.set_SIREN_TIMEOUT(420);
        Thread.sleep(one_sec);
        servcall.set_WATER_FREEZE_ALARM_enable();
        Thread.sleep(one_sec);
        servcall.set_POLICE_PANIC(OFF);
        Thread.sleep(one_sec);
        servcall.set_FIRE_PANIC(OFF);
        Thread.sleep(one_sec);
        servcall.set_AUXILIARY_PANIC(OFF);
        Thread.sleep(one_sec);
        servcall.set_AUTO_UPLOAD_LOGS(ON);
        Thread.sleep(one_sec);
        //The following setting causes Refuse Arming When Battery Low to be inaccessible
        servcall.set_POWER_MANAGEMENT_ON_OFF_disable();
        Thread.sleep(one_sec);
        servcall.set_SIA_POWER_RESTORATION_enable();
        Thread.sleep(one_sec);
        servcall.set_LOSS_OF_SUPERVISORY_TIMEOUTY_12h();
        Thread.sleep(one_sec);
        servcall.set_LOSS_OF_SUPERVISORY_EMERGENCY_TIMEOUT(12);
        Thread.sleep(one_sec);
        servcall.set_Cell_Signal_Timeout(25);
        Thread.sleep(one_sec);
        servcall.set_RF_JAM_DETECT_enable();
        Thread.sleep(one_sec);
        servcall.set_OPEN_CLOSE_REPORTS_FOR_AUTO_LEARN(OFF);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH(ON);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH_DISARM(ON);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH_DISARM_TIMEOUT(30);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_IMAGE_SETTINGS(ON);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SECURITY_AND_ARMING(ON);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SIREN_AND_ALARMS(ON);
        Thread.sleep(one_sec);
        System.out.println("Done!");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        quitDriver();
    }
}
