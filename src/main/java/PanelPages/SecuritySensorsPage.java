package PanelPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SecuritySensorsPage {
    @FindBy(xpath = "//android.widget.TextView[@text='Auto Learn Sensor']")
    public WebElement Auto_Learn_Sensor;
    @FindBy(xpath = "//android.widget.TextView[@text='Add Sensor']")
    public WebElement Add_Sensor;
    @FindBy(xpath = "//android.widget.TextView[@text='Edit Sensor']")
    public WebElement Edit_Sensor;
    @FindBy(xpath = "//android.widget.TextView[@text='Delete Sensor']")
    public WebElement Delete_Sensor;
    @FindBy(xpath = "//android.widget.TextView[@text='Sensor Status']")
    public WebElement Sensor_Status;
    @FindBy(xpath = "//android.widget.TextView[@text='Sensor Group']")
    public WebElement Sensor_Group;
    @FindBy(id = "com.qolsys:id/imageView1")
    public WebElement Edit_Img;
    @FindBy(id = "com.qolsys:id/sensorDescText")
    public WebElement Custom_Description;
    @FindBy(id = "com.qolsys:id/addsensor")
    public WebElement Save;
    @FindBy(id = "com.qolsys:id/btnDelete")
    public WebElement Delete;
    @FindBy(id = "com.qolsys:id/ok")
    public WebElement OK;
    @FindBy(id = "com.qolsys:id/sensor_id")
    public WebElement Sensor_DLID;
    @FindBy(id = "com.qolsys:id/partition_name")
    public WebElement Partition_Name;
}
