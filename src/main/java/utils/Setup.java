package utils;

import PanelPages.*;
import ServiceCalls.PanelInfo_ServiceCalls;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static utils.ConfigProps.adbPath;

public class Setup extends Driver {
    HomePage home_page;
    SlideMenu menu;
    SettingsPage settings;
    AdvancedSettingsPage advanced;

    public Setup() throws Exception {
        ConfigProps.init();
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

    public void ARM_STAY() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Arm Stay");
        home_page.DISARM.click();
        home_page.ARM_STAY.click();
    }

    public void ARM_AWAY(int delay) throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Arm Away");
        home_page.DISARM.click();
        home_page.ARM_AWAY.click();
        TimeUnit.SECONDS.sleep(delay);
    }

    public void ARM_AWAY() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
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
        } catch (NoSuchElementException e){}
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
        PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
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

}
