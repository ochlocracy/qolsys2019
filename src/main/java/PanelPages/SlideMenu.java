package PanelPages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SlideMenu {
    @FindBy(id = "com.qolsys:id/btn_drop")
    public WebElement Slide_menu_open;
    @FindBy(id = "com.qolsys:id/ui_icon_system_state")
    public WebElement System_state_icon;
    @FindBy(id = "com.qolsys:id/ui_part1_battery")
    public WebElement Battery;
    @FindBy(id = "com.qolsys:id/ui_part1_wifi")
    public WebElement WiFi;
    @FindBy(id = "com.qolsys:id/ui_part1_bt")
    public WebElement Bluetooth;
    @FindBy(id = "com.qolsys:id/ui_part1_ns")
    public WebElement Cell_bar;
    @FindBy(id = "com.qolsys:id/ui_tv_click_details")
    public WebElement Cell_info;
    @FindBy(id = "com.qolsys:id/icon_volume_down")
    public WebElement Volume_down;
    @FindBy(id = "com.qolsys:id/icon_volume_up")
    public WebElement Volume_up;
    @FindBy(id = "com.qolsys:id/ui_tray_volume_adjuster")
    public WebElement Volume_adjuster;
    @FindBy(id = "com.qolsys:id/icon_bn_down")
    public WebElement Brightness_down;
    @FindBy(id = "com.qolsys:id/icon_bn_up")
    public WebElement Brightness_up;
    @FindBy(id = "com.qolsys:id/ui_tray_bn_adjuster")
    public WebElement Brightness_adjuster;
    @FindBy(id = "com.qolsys:id/tv_tray_settings")
    public WebElement Settings;
    @FindBy(id = "com.qolsys:id/tv_tray_msg")
    public WebElement Messages_Alerts;
    @FindBy(id = "com.qolsys:id/tv_tray_photoframe")
    public WebElement Photo_Frame;
    @FindBy(id = "com.qolsys:id/tv_tray_clean_screen")
    public WebElement Clean_Screen;
    @FindBy(id = "com.qolsys:id/tv_tray_language")
    public WebElement Espanol;
    @FindBy(id = "com.qolsys:id/ic_tray_close")
    public WebElement Slide_menu_close;
    @FindBy(id = "com.qolsys:id/ui_icon_system_state")
    public WebElement Lock_Screen;
}
