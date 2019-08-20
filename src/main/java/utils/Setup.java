package utils;

import PanelPages.*;
import ServiceCalls.PanelInfo_ServiceCalls;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static utils.ConfigProps.adbPath;

public class Setup extends Driver {
    public String projectPath = new String(System.getProperty("user.dir"));
    HomePage home_page;
    SlideMenu menu;
    SettingsPage settings;
    AdvancedSettingsPage advanced;
    PanelInfo_ServiceCalls servcall;

    public Setup() throws Exception {
        ConfigProps.init();
    }

    //miscellanies
    public static String captureScreenshot(AndroidDriver driver, String screenshotName) throws IOException {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs((OutputType.FILE));
            String dest = new String(System.getProperty("user.dir")) + "/Report/" + screenshotName + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot taken");
            return dest;
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
            return e.getMessage();
        }
    }

    public void swipeRight() throws InterruptedException {
        int startY = 400;
        int endX = 660;
        int startX = 360;
        driver.swipe(startX, startY, endX, startY, 500);
        Thread.sleep(2000);
    }

    public void swipeLeft() throws InterruptedException {
        int startY = 400;
        int endX = 360;
        int startX = 660;
        driver.swipe(startX, startY, endX, startY, 500);
        Thread.sleep(2000);
    }

    public void swipeUp() throws InterruptedException {
        int startY = 616;
        int endY = 227;
        int startX = 314;
        driver.swipe(startX, startY, startX, endY, 3000);
        Thread.sleep(2000);
    }

    public void swipeFromLefttoRight() throws Exception {
        Thread.sleep(2000);
        int sx = (int) (driver.manage().window().getSize().width * 0.90);
        int ex = (int) (driver.manage().window().getSize().width * 0.10);
        int sy = driver.manage().window().getSize().height / 2;
        driver.swipe(ex, sy, sx, sy, 3000);
        Thread.sleep(2000);
    }

    public void swipeFromRighttoLeft() throws Exception {
        Thread.sleep(2000);
        int sx = (int) (driver.manage().window().getSize().width * 0.90);
        int ex = (int) (driver.manage().window().getSize().width * 0.10);
        int sy = driver.manage().window().getSize().height / 2;
        driver.swipe(sx, sy, ex, sy, 3000);
        Thread.sleep(2000);
    }

    public void swipeVertical() throws InterruptedException {
        int starty = 660;
        int endy = 260;
        int startx = 502;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    public void DISARM() {
        home_page = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Disarm");
        home_page.DISARM.click();
        enterDefaultUserCode();
    }

    public void disarmServiceCall() throws IOException {
        String servicecall = " shell service call qservice 1 i32 0 i32 0 i32 0 i32 0 i32 0 i32 1 i32 0 i32 0 i32 1";
        rt.exec(ConfigProps.adbPath + " " + servicecall);
    }

    public void ARM_STAY() {
        home_page = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Arm Stay");
        home_page.DISARM.click();
        home_page.ARM_STAY.click();
    }

    public void ARM_AWAY(int delay) throws Exception {
        home_page = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Arm Away");
        home_page.DISARM.click();
        home_page.ARM_AWAY.click();
        TimeUnit.SECONDS.sleep(delay);
    }

    public void ARM_AWAY() throws Exception {
        home_page = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Arm Away");
        home_page.DISARM.click();
        home_page.ARM_AWAY.click();
    }

    public String verifySystemState(String state) throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        String panel_state = null;
        try {
            panel_state = home_page.Disarmed_text.getText();
            if (home_page.Disarmed_text.getText().equals(state.toUpperCase())) {
                switch (state) {
                    case "DISARMED":
                        break;
                    case "ARMED STAY":
                        break;
                    case "ARMED AWAY":
                        break;
                }
                System.out.println("Pass: System state is " + panel_state);
                return "System is: " + panel_state;
            } else {
                System.out.println("Failed: System is not in the expected state! Current state: " + home_page.Disarmed_text.getText());
                System.exit(0);
            }
        } catch (NoSuchElementException e) {
        }
        return "System is: " + panel_state;
    }

    public WebElement elementVerification(String element, String element_name) throws Exception {
        WebElement elem = driver.findElement(By.id(element));
        try {
            if (elem.isDisplayed() & elem.getText().equals(element_name)) {
                System.out.println("Pass: " + element_name + " is present, value = " + elem.getText());
            } else {
                System.out.println("*" + element_name + "* - Element is not found! Element ");
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            return elem;
        }
    }

    public WebElement elementVerification(WebElement element, String element_name) throws Exception {
        try {
            if (element.isDisplayed()) {
                System.out.println("Pass: " + element_name + " is present, value = " + element.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return element;
        }
    }

    public void alarm_verification() {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        try {
            if (home.ALARM.isDisplayed()) {
                System.out.println("Pass: Alarm is displayed!");
                enterDefaultUserCode();
            } else {
                System.out.println("FAIL: No ALARM event!");
                System.exit(0);
            }
        } catch (NoSuchElementException e) {
        }
    }

    public boolean alarmVerification(String sensor_name) throws Exception {
        swipeFromRighttoLeft();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='ALARMS']")).click();
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.xpath("//android.widget.TextView[@text='" + sensor_name + "']"));
        List<WebElement> date_time = driver.findElements(By.id("com.qolsys:id/type"));
        try {
            if (element.isDisplayed()) {
                System.out.println("Pass: sensor alarm is displayed " + sensor_name);
                System.out.println(date_time.get(1).getText());
                swipeFromLefttoRight();
            }
            return true;
        } catch (NoSuchElementException e) {
        }
        return false;

    }

    public void verifyInAlarm() throws Exception {
        HomePage home_page = PageFactory.initElements(this.driver, HomePage.class);
        try {
            if (home_page.ALARM.isDisplayed()) {
                System.out.println("Pass: System is in ALARM");
            }
        } catch (NoSuchElementException e) {
            System.out.println("FAIL: System is not in ALARM");
        }
    }

    public void verifySensorIsTampered(WebElement sensor_name) throws Exception {
        if (sensor_name.isDisplayed()) {
            System.out.println(sensor_name.getText() + " is successfully tampered");
        } else {
            System.out.println(sensor_name + " is NOT tampered");
        }
    }

    public void enterDefaultDuressCode() {
        home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.Nine.click();
        home_page.Nine.click();
        home_page.Nine.click();
        home_page.Eight.click();
    }

    public void enterDefaultUserCode() {
        home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.One.click();
        home_page.Two.click();
        home_page.Three.click();
        home_page.Four.click();
    }

    public void enterGuestCode() {
        home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.One.click();
        home_page.Two.click();
        home_page.Three.click();
        home_page.Three.click();
    }

    public void enterDefaultDealerCode() {
        home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.Two.click();
        home_page.Two.click();
        home_page.Two.click();
        home_page.Two.click();
    }

    public void navigateToUserManagementPage() throws InterruptedException {
        advanced = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        navigateToAdvancedSettingsPage();
        advanced.USER_MANAGEMENT.click();
        Thread.sleep(1000);
    }

    public void navigateToAdvancedSettingsPage() throws InterruptedException {
        menu = PageFactory.initElements(driver, SlideMenu.class);
        settings = PageFactory.initElements(driver, SettingsPage.class);
        menu.Slide_menu_open.click();
        System.out.println("Settings Menu");
        menu.Settings.click();
        Thread.sleep(1000);
        System.out.println("Advanced Settings");
        settings.ADVANCED_SETTINGS.click();
        Thread.sleep(2000);
        enterDefaultDealerCode();
    }

    public void addPrimaryCall(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(adbPath + add_primary);
    }

    public void primaryCall(String DLID, String State) throws IOException {
        String primary_send = " shell rfinjector 02 " + DLID + " " + State;
        rt.exec(ConfigProps.adbPath + primary_send);
        System.out.println(primary_send);
    }

    public void deleteFromPrimary(int zone) throws IOException, InterruptedException {
        String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
        rt.exec(adbPath + deleteFromPrimary);
        System.out.println(deleteFromPrimary);
    }

    public void preconditions() throws Exception {
        servcall = new PanelInfo_ServiceCalls();
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(1000);
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longExitDelay);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longEntryDelay);
    }

    public void deleteAllCameraPhotos() throws Exception {
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        swipeFromRighttoLeft();
        Thread.sleep(2000);
        try {
            while (camera.Camera_delete.isDisplayed()) {
                camera.Camera_delete.click();
                Thread.sleep(2000);
                camera.Camera_delete_yes.click();
                if (home_page.Four.isDisplayed()) {
                    enterDefaultUserCode();
                }
            }
        } catch (Exception e) {
            System.out.println("No photos left to delete...");
        }
        swipeFromLefttoRight();
        Thread.sleep(1000);
    }

    public void navigateToSettingsPage() {
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        menu.Slide_menu_open.click();
        menu.Settings.click();
    }

    public void navigate_to_autolearn_page() throws InterruptedException {
        Thread.sleep(2000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        SecuritySensorsPage ss = PageFactory.initElements(driver, SecuritySensorsPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        instal.DEVICES.click();
        dev.Security_Sensors.click();
        ss.Auto_Learn_Sensor.click();
        Thread.sleep(1000);
    }

    //sensors activity
    public void pgprimaryCall(int type, int id, String status) throws IOException {
        String status_send = " shell powerg_simulator_status " + type + "-" + id + " " + status;
        rt.exec(adbPath + status_send);
        System.out.println(status_send);
    }

    public void addPrimaryCallPG(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type + " i32 8";
        rt.exec(adbPath + add_primary);
        // shell service call qservice 50 i32 100 i32 10 i32 3201105 i32 21
    }

    public void addPrimaryCallPG(int zone, int group, String sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type + " i32 8";
        rt.exec(adbPath + add_primary);
        // shell service call qservice 50 i32 100 i32 10 i32 3201105 i32 21
    }

    public void pgarmer(int type, int id, String status) throws IOException {
        String status_send = " shell powerg_simulator_armer " + type + "-" + id + " " + status;
        rt.exec(adbPath + status_send);
        System.out.println(status_send);
    }

    public void powerGregistrator(int type, int id) throws IOException {
        String add_pg = " shell powerg_simulator_registrator " + type + "-" + id;
        rt.exec(adbPath + add_pg);
        //shell powerg_simulator_registrator 101-0001
    }

    public void powerGactivator(int type, int id) throws IOException, InterruptedException {
        Thread.sleep(2000);
        String activate_pg = " shell powerg_simulator_activator " + type + "-" + id;
        rt.exec(adbPath + activate_pg);
        Thread.sleep(2000);
        //shell powerg_simulator_activatortor 101-0001
    }

    public void powerGjamer(int state) throws IOException {
        String status_send = " shell powerg_simulator_jamer " + state;
        rt.exec(adbPath + status_send);
        System.out.println(status_send);
    }

    public void addPGSensors(String sensor, int Type, int Id, int gn) throws IOException, InterruptedException {
        Thread.sleep(1000);
        powerGregistrator(Type, Id);
        Thread.sleep(3000);
        driver.findElementById("com.qolsys:id/ok").click();

        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/sensor_desc")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Custom Description']")).click();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).sendKeys(sensor + " " + Type + "-" + Id);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            //       e.printStackTrace();
        }
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/grouptype")).click();
        List<WebElement> gli = driver.findElements(By.id("android:id/text1"));
        gli.get(gn).click();

        Thread.sleep(1000);
        driver.findElementById("com.qolsys:id/addsensor").click();
        Thread.sleep(1000);
        powerGactivator(Type, Id);
        Thread.sleep(2000);
    }

    public void activation_restoration(int type, int id, String status1, String status2) throws InterruptedException, IOException {
        pgprimaryCall(type, id, status1);
        Thread.sleep(5000);
        pgprimaryCall(type, id, status2);
        Thread.sleep(1000);
    }

    public void powerGsupervisory(int type, int id) throws IOException {
        String status_send = " shell powerg_simulator_supervisory " + type + "-" + id + " 1 0 0 0";
        rt.exec(adbPath + status_send);
        System.out.println(status_send);
    }

    public void dw_sensor_testing() throws IOException, InterruptedException {
        List<Integer> dw_sticker_type = Arrays.asList(100, 101, 104, 107, 108, 109, 110);

        for (int i = 0; i < dw_sticker_type.size(); i++) {
            addPrimaryCallPG(50, 10, dw_sticker_type.get(i) + "1102", 1);
            Thread.sleep(3000);
            pgprimaryCall(dw_sticker_type.get(i), 1102, PGSensorsActivity.INOPEN);
            Thread.sleep(3000);
            try {
                if (driver.findElement(By.id("com.qolsys:id/t3_home_tv_SensorName")).getText().equals("Door-Window 50"))
                    System.out.println(dw_sticker_type.get(i) + " sticker type sensor state is OPEN");
            } catch (Exception e) {
                System.out.println(dw_sticker_type.get(i) + " sticker type DOES NOT work correctly!");
            }

            pgprimaryCall(dw_sticker_type.get(i), 1102, PGSensorsActivity.INCLOSE);
            Thread.sleep(3000);

            try {
                if (driver.findElement(By.id("com.qolsys:id/t3_home_tv_SensorName")).getText().equals("Door-Window 50"))
                    System.out.println(dw_sticker_type.get(i) + " sticker type DOES NOT work correctly, sensor state is OPEN!");
            } catch (Exception e) {
                System.out.println(dw_sticker_type.get(i) + " sticker type works correctly, sensor state is CLOSE!");
            } finally {
                deleteFromPrimary(50);
                Thread.sleep(3000);
            }
        }
    }
}
