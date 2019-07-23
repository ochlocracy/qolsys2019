package PanelPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class Lights {

    @FindBy(id = "com.qolsys:id/statusButton")
    public WebElement Light_status;
    @FindBy(id = "com.qolsys:id/uiName")
    public WebElement Light_name;
    @FindBy(id = "com.qolsys:id/lightSelect")
    public WebElement Light_checkbox;
    @FindBy(id = "com.qolsys:id/selectallbtn")
    public WebElement SelectAll;
    @FindBy(id = "com.qolsys:id/allOn")
    public WebElement AllOn;
    @FindBy(id = "com.qolsys:id/allOff")
    public WebElement AllOff;
    @FindBy(id = "com.qolsys:id/getStatusButton")
    public WebElement GetStatus;

}
