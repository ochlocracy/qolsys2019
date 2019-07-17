package PanelPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactUs {

    @FindBy(id = "com.qolsys:id/img_contact_us")
    public WebElement Contact_Us;
    @FindBy(id = "com.qolsys:id/uiTabName1")
    public WebElement Contact_us_tab;
    @FindBy(id = "com.qolsys:id/uiTabName2")
    public WebElement Video_Tutorials_tab;
    @FindBy(id = "com.qolsys:id/uiTabName4")
    public WebElement Messages_Alerts_Alarms_tab;
    @FindBy(id = "com.qolsys:id/tv_dealer_web")
    public WebElement Dealer_website;
    @FindBy(id = "com.qolsys:id/uiCBAck")
    public WebElement Acknowledge_All;
    @FindBy(id = "com.qolsys:id/ok")
    public WebElement OK;
    @FindBy(id = "com.qolsys:id/cancel")
    public WebElement CANCEL;

    public void acknowledge_all_alerts() {
        Contact_Us.click();
        Messages_Alerts_Alarms_tab.click();
        Acknowledge_All.click();
        OK.click();
    }
}
