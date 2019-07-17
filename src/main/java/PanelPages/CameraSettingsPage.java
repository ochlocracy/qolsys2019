package PanelPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CameraSettingsPage {
    @FindBy(xpath = "//android.widget.TextView[@text='Secure Delete Images']")
    public WebElement Secure_Delete_Images;
    @FindBy(xpath = "//android.widget.TextView[@text='Deleting panel camera images requires valid Master code']")
    public WebElement Secure_Delete_Images_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Anyone can delete panel camera images']")
    public WebElement Secure_Delete_Images_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Disarm Photos']")
    public WebElement Disarm_Photos;
    @FindBy(xpath = "//android.widget.TextView[@text='Disarm Photos are Enabled']")
    public WebElement Disarm_Photos_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Disarm Photos are Disabled']")
    public WebElement Disarm_Photos_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Alarm Photos']")
    public WebElement Alarm_Photos;
    @FindBy(xpath = "//android.widget.TextView[@text='Alarm Photos are Enabled']")
    public WebElement Alarm_Photos_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Alarm Photos are Disabled']")
    public WebElement Alarm_Photos_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Alarm Videos']")
    public WebElement Alarm_Videos;
    @FindBy(xpath = "//android.widget.TextView[@text='Alarm Videos are Disabled']")
    public WebElement Alarm_Videos_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Alarm Videos are Enabled']")
    public WebElement Alarm_Videos_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Settings Photos']")
    public WebElement Settings_Photos;
    @FindBy(xpath = "//android.widget.TextView[@text='Settings Photos are Disabled']")
    public WebElement Setting_Photos_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Settings Photos are Enabled']")
    public WebElement Setting_Photos_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Allow Master Code to access Camera Settings']")
    public WebElement Allow_Master_Code_to_access_Camera_Settings;
    @FindBy(xpath = "//android.widget.TextView[@text='Allowing Master Code to access Camera settings is Disabled']")
    public WebElement Allow_Master_Code_to_access_Camera_Settings_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Allowing Master Code to access Camera settings is Enabled']")
    public WebElement Allow_Master_Code_to_access_Camera_Settings_summery_enabled;
}
