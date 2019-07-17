package PanelPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserManagementPage{
    @FindBy(xpath = "//android.widget.TextView[@text='Id']")
    public WebElement User_Management_Id;
    @FindBy(xpath = "//android.widget.TextView[@text='Name']")
    public WebElement User_Management_Name;
    @FindBy(xpath = "//android.widget.TextView[@text='Type']")
    public WebElement User_Management_Type;
    @FindBy(xpath = "//android.widget.TextView[@text='Expiration Date']")
    public WebElement User_Management_Expiration_Date;
    @FindBy(xpath = "//android.widget.TextView[@text='Edit']")
    public WebElement User_Management_Edit;
    @FindBy(xpath = "//android.widget.TextView[@text='Delete']")
    public WebElement User_Management_Delete;
    @FindBy(xpath = "//android.widget.TextView[@text='Admin']")
    public WebElement User_Management_Admin_Name;
    @FindBy(xpath = "//android.widget.TextView[@text='Installer']")
    public WebElement User_Management_Installer_Name;
    @FindBy(xpath = "//android.widget.TextView[@text='Dealer']")
    public WebElement User_Management_Dealer_Name;
    @FindBy(id = "com.qolsys:id/btnAdd")
    public WebElement Add_User;
    @FindBy(xpath = "//android.widget.TextView[@text='Name']")
    public WebElement Add_User_Name;
    @FindBy(id = "com.qolsys:id/username")
    public WebElement Add_User_Name_field;
    @FindBy(xpath = "//android.widget.TextView[@text='User Code']")
    public WebElement Add_User_Code;
    @FindBy(id = "com.qolsys:id/user_pin")
    public WebElement Add_User_Code_field;
    @FindBy(xpath = "//android.widget.TextView[@text='Confirm User Code']")
    public WebElement Add_Confirm_User_Code;
    @FindBy(id = "com.qolsys:id/confirm_pin")
    public WebElement Add_Confirm_User_Code_field;
    @FindBy(id = "com.qolsys:id/usertype")
    public WebElement Add_User_Type;
    @FindBy(id = "com.qolsys:id/user_type")
    public WebElement Add_User_Type_options;
    @FindBy(xpath = "//android.widget.CheckedTextView[@text='User']")
    public WebElement User_Type_User;
    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Master']")
    public WebElement User_Type_Master;
    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Guest']")
    public WebElement User_Type_Guest;
    @FindBy(id = "com.qolsys:id/expirydateText")
    public WebElement Add_User_Expiration_Date;
    @FindBy(id = "com.qolsys:id/expiry_date")
    public WebElement Add_User_Expiration_Date_entry;
    @FindBy(id = "android:id/button3")
    public WebElement Calendar_Clear;
    @FindBy(id = "android:id/button2")
    public WebElement Calendar_Cancel;
    @FindBy(id = "android:id/button1")
    public WebElement Calendar_Ok;
    @FindBy(id = "com.qolsys:id/add")
    public WebElement Add_User_add_page;
    @FindBy(xpath = "//android.widget.LinearLayout[@index=1']/com.qolsys:id/deleteImg")
    public WebElement Delete_User;
    @FindBy(xpath = "//android.widget.TextView[@text='User Management']")
    public WebElement User_Management_Delete_User_title;
    @FindBy(id = "com.qolsys:id/message")
    public WebElement User_Management_Delete_User_message;
    @FindBy(id = "com.qolsys:id/cancel")
    public WebElement User_Management_Delete_User_Cancel;
    @FindBy(id = "com.qolsys:id/ok")
    public WebElement User_Management_Delete_User_Ok;
    @FindBy(id = "com.qolsys:id/Save")
    public WebElement User_Management_Save;
    @FindBy(id = "com.qolsys:id/ft_home_button")
    public WebElement Home_Button;
}
