package PanelPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SettingsPage {
    @FindBy(xpath = "//android.widget.TextView[@text='DISPLAY']")
    public WebElement DISPLAY;
    @FindBy(xpath = "//android.widget.TextView[@text='SD CARD']")
    public WebElement SD_CARD;
    @FindBy(xpath = "//android.widget.TextView[@text='WEATHER TEMPERATURE']")
    public WebElement WEATHER_TEMPERATURE;
    @FindBy(xpath = "//android.widget.TextView[@text='STATUS']")
    public WebElement STATUS;
    @FindBy(xpath = "//android.widget.TextView[@text='Z-wave Device Status']")
    public WebElement ZWAVE_STATUS;
    @FindBy(xpath = "//android.widget.TextView[@text='Other Z-wave Devices']")
    public WebElement OTHER_ZWAVE;
    @FindBy(xpath = "//android.widget.TextView[@text='Automation']")
    public WebElement AUTOMATION;
    @FindBy(xpath = "//android.widget.TextView[@text='Activity Monitor']")
    public WebElement ACTIVITY_MONITOR;
    @FindBy(xpath = "//android.widget.TextView[@text='ADVANCED SETTINGS']")
    public WebElement ADVANCED_SETTINGS;
    @FindBy(id = "com.qolsys:id/ft_back")
    public WebElement Back_button;
    @FindBy(id = "com.qolsys:id/ft_emergency")
    public WebElement Emergency_button;
    @FindBy(id = "com.qolsys:id/ft_home_button")
    public WebElement Home_button;
    @FindBy(id = "com.qolsys:id/tv_keyOne")
    public WebElement One;
    @FindBy(id = "com.qolsys:id/tv_keyTwo")
    public WebElement Two;
    @FindBy(id = "com.qolsys:id/tv_keyThree")
    public WebElement Three;
    @FindBy(id = "com.qolsys:id/tv_keyFour")
    public WebElement Four;
    @FindBy(id = "com.qolsys:id/tv_keyFive")
    public WebElement Five;
    @FindBy(id = "com.qolsys:id/tv_zero")
    public WebElement Zero;
    @FindBy(id = "com.qolsys:id/tv_clear")
    public WebElement Clear;
    @FindBy(id = "com.qolsys:id/TV12")
    public WebElement Delete;
    @FindBy(id = "com.qolsys:id/usr_error_msg")
    public WebElement Invalid_User_Code;
    @FindBy(id = "com.qolsys:id/uiTabName4")
    public WebElement Panel_history;
}
