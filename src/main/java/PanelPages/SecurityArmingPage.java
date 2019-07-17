package PanelPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SecurityArmingPage {
    @FindBy(xpath = "//android.widget.TextView[@text='Dealer Code']")
    public WebElement Dealer_Code;
    @FindBy(xpath = "//android.widget.TextView[@text='Edit Dealer Code']")
    public WebElement Dealer_Code_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Installer Code']")
    public WebElement Installer_Code;
    @FindBy(xpath = "//android.widget.TextView[@text='Set Name and Code to access installer-level settings']")
    public WebElement Installer_Code_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='User cannot create a duress code to trigger silent alarms']")
    public WebElement Duress_Authentication_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='User can create a duress code for triggering silent alarms']")
    public WebElement Duress_Authentication_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Swinger Shutdown']")
    public WebElement SwingerShutdown;
    @FindBy(xpath = "//android.widget.TextView[@text='Swinger shutdown enabled']")
    public WebElement SwingerShutdownEnabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Swinger shutdown disabled']")
    public WebElement SwingerShutdownDisabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Swinger Shutdown Count']")
    public WebElement SwingerShutdownCount;


    @FindBy(xpath = "//android.widget.TextView[@text='Secure Arming']")
    public WebElement Secure_Arming;
    @FindBy(xpath = "//android.widget.TextView[@text='Require valid User Code to Arm the System']")
    public WebElement Secure_Arming_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Refuse Arming When Battery Low']")
    public WebElement Refuse_Arming_When_Battery_Low;
    @FindBy(xpath = "//android.widget.TextView[@text='During A/C failure, deny Arming attempt if battery is low']")
    public WebElement Refuse_Arming_When_Battery_Low_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Auto Bypass']")
    public WebElement Auto_Bypass;
    @FindBy(xpath = "//android.widget.TextView[@text='Set to automatically bypass open sensors when Arming']")
    public WebElement Auto_Bypass_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Auto Stay']")
    public WebElement Auto_Stay;
    @FindBy(xpath = "//android.widget.TextView[@text='If Arming Away and no delay door is opened, system assumes you are still inside and changes arming to Stay Mode']")
    public WebElement Auto_Stay_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Arm Stay - No Delay']")
    public WebElement Arm_Stay_No_Delay;
    @FindBy(xpath = "//android.widget.TextView[@text='Arm stay immediately with no countdown timer']")
    public WebElement Arm_Stay_No_Delay_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Auto Exit Time Extension']")
    public WebElement Auto_Exit_Time_Extension;
    @FindBy(xpath = "//android.widget.TextView[@text='Automatically extend countdown timer if delay door is opened twice during countdown process']")
    public WebElement Auto_Exit_Time_Extension_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Keyfob Instant Arming']")
    public WebElement Keyfob_Instant_Arming;
    @FindBy(xpath = "//android.widget.TextView[@text='Turn off exit and entry delays if keyfob is used to Arm or Disarm System']")
    public WebElement Keyfob_Instant_Arming_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Keyfob Alarm Disarm']")
    public WebElement Keyfob_Alarm_Disarm;
    @FindBy(xpath = "//android.widget.TextView[@text='Allow Keyfob to disarm alarms setting is Disabled']")
    public WebElement Keyfob_Alarm_Disarm_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Allow Keyfob to disarm alarms setting is Enabled']")
    public WebElement Keyfob_Alarm_Disarm_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Keyfob Disarming']")
    public WebElement Keyfob_Disarming;
    @FindBy(xpath = "//android.widget.TextView[@text='Allow Keyfob to disarm']")
    public WebElement Keyfob_Disarming_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Allow Master Code To Access Security and Arming']")
    public WebElement Allow_Master_Code_To_Access_Security_and_Arming;
    @FindBy(xpath = "//android.widget.TextView[@text='Allowing Master Code to access Security and Arming settings is Disabled']")
    public WebElement Allow_Master_Code_To_Access_Security_and_Arming_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Allowing Master Code to access Security and Arming settings is Enabled']")
    public WebElement Allow_Master_Code_To_Access_Security_and_Arming_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Normal Entry Delay']")
    public WebElement Normal_Entry_Delay;
    @FindBy(xpath = "//android.widget.TextView[@text='Normal Exit Delay']")
    public WebElement Normal_Exit_Delay;
    @FindBy(xpath = "//android.widget.TextView[@text='Long Entry Delay']")
    public WebElement Long_Entry_Delay;
    @FindBy(xpath = "//android.widget.TextView[@text='Long Exit Delay']")
    public WebElement Long_Exit_Delay;
    @FindBy(id = "android:id/numberpicker_input")
    public WebElement Input_Field;
    @FindBy(id = "com.qolsys:id/Set")
    public WebElement Delay_Set;
    @FindBy(id = "android:id/checkbox")
    public WebElement Checkbox;
}
