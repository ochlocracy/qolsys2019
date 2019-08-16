package ADC;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UIRepo {
    @FindBy(xpath ="//*[contains(@id, 'ember')]/h4")
    public WebElement System_State;

    @FindBy(xpath = "//div[contains(@class, 'icon ') and contains(@title, 'Disarmed ')]")
    public WebElement Disarm_state;

    @FindBy(xpath = "//button[contains(@id, 'ember') and contains(@class, 'armed-stay btn ember-view')]")
    public WebElement Arm_Stay;
    @FindBy(xpath = "//button[contains(@id, 'ember') and contains(@class, 'armed-away btn ember-view')]")
    public WebElement Arm_Away;


    @FindBy(xpath = "//div[contains(@class, 'icon ') and contains(@title, 'Armed Stay ')]")
    public WebElement Arm_Stay_state;

    @FindBy(xpath = "//div[contains(@class, 'icon ') and contains(@title, 'Armed Away ')]")
    public WebElement Arm_Away_state;

    @FindBy(xpath = "//button[contains(@id, 'ember') and contains(@class, 'disarmed btn ember-view')]")
    public WebElement Disarm;

    @FindBy(xpath = "//*[contains(@id, 'ember') and contains(@class, 'icon-circle icon-light-status off ember-view')]")
    public WebElement Light_Off;
    @FindBy(xpath = "//*[contains(@id, 'ember') and contains(@class, 'icon-circle icon-light-status on ember-view')]")
    public WebElement Light_On;
    @FindBy(xpath = "//*[contains(@id, 'ember')]/h4/span")
    public WebElement Light_Status;
    @FindBy(xpath = "//*[contains(@id, 'ember')]/h3")
    public WebElement Light_Name;
    @FindBy(xpath= "//*[contains(text(), 'Today')]")
    public WebElement Light_Time_Stamp;
}
