package PanelPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InstallationPage {

    @FindBy(xpath = "//android.widget.TextView[@text='Devices']")
    public WebElement DEVICES;
    @FindBy(xpath = "//android.widget.TextView[@text='Dealer Settings']")
    public WebElement DEALER_SETTINGS;
    @FindBy(xpath = "//android.widget.TextView[@text='Installer Settings']")
    public WebElement INSTALLER_SETTINGS;
    @FindBy(xpath = "//android.widget.TextView[@text='System Logs']")
    public WebElement SYSTEM_LOGS;
    @FindBy(xpath = "//android.widget.TextView[@text='Siren and Alarms']")
    public WebElement SIREN_AND_ALARMS;
    @FindBy(xpath = "//android.widget.TextView[@text='Security and Arming']")
    public WebElement SECURITY_AND_ARMING;
    @FindBy(xpath = "//android.widget.TextView[@text='Camera Settings']")
    public WebElement CAMERA_SETTINGS;
    @FindBy(xpath = "//android.widget.TextView[@text='Load Help Videos']")
    public WebElement LOAD_HELP_VIDEOS;


    //within the security and arming page. enabled / disabled linktext.

    @FindBy(xpath = "//android.widget.TextView[@text='Allowing Master Code to access Security and Arming settings is Enabled']")
    public WebElement SecAndArmingEnabled;

    @FindBy(xpath = "//android.widget.TextView[@text='Allowing Master Code to access Siren and Alarms settings is Enabled']")
    public WebElement SirenandAlarmsEnabled;

    @FindBy(xpath = "//android.widget.TextView[@text='Allowing Master Code to access Camera settings is Enabled']")
    public WebElement CameraSettingsEnabled;

    @FindBy(xpath = "//android.widget.TextView[@text='Allow Keyfob to disarm']")
    public WebElement Keyfob_Disarm;
}
