package PanelPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdvancedSettingsPage {
    @FindBy(xpath = "//android.widget.TextView[@text='Installation']")
    public WebElement INSTALLATION;
    @FindBy(xpath = "//android.widget.TextView[@text='User Management']")
    public WebElement USER_MANAGEMENT;
    @FindBy(xpath = "//android.widget.TextView[@text='About']")
    public WebElement ABOUT;
    @FindBy(xpath = "//android.widget.TextView[@text='System Tests']")
    public WebElement SYSTEM_TESTS;
    @FindBy(xpath = "//android.widget.TextView[@text='Z-Wave Device List']")
    public WebElement ZWAVE_DEVICE_LIST;
    @FindBy(xpath = "//android.widget.TextView[@text='Dealer Contact']")
    public WebElement DEALER_CONTACT;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel Reboot']")
    public WebElement PANEL_REBOOT;
    @FindBy(xpath = "//android.widget.TextView[@text='Power Down']")
    public WebElement POWER_DOWN;
    @FindBy(xpath = "//android.widget.TextView[@text='Upgrade Software']")
    public WebElement UPGRADE_SOFTWARE;
    @FindBy(xpath = "//android.widget.TextView[@text='WI-FI']")
    public WebElement WI_FI;
    @FindBy(xpath = "//android.widget.TextView[@text='SOUND']")
    public WebElement SOUND;
    @FindBy(xpath = "//android.widget.TextView[@text='DATE & TIME']")
    public WebElement DATE_TIME;
    @FindBy(xpath = "//android.widget.TextView[@text='PARTITIONS']")
    public WebElement PARTITIONS;
}
