package SanityTests;

import PanelPages.EmergencyPage;
import PanelPages.HomePage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigProps;
import utils.Setup;

public class EmergencyFromPanel extends Setup {
    public EmergencyFromPanel() throws Exception {
        ConfigProps.init();
    }

    EmergencyPage emergency;
    HomePage home;

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver();
        preconditions();
    }

    @Test(priority =1)
    public void policePanic(){
        emergency = PageFactory.initElements(driver, EmergencyPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Creating police alarm from a panel");
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Assert.assertTrue(emergency.Alarm_status.getText().equals("Police Emergency"));
        alarm_verification();
    }

    @Test(priority =2)
    public void policePanicSilent() throws InterruptedException {
        emergency = PageFactory.initElements(driver, EmergencyPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Creating silent police alarm from a panel");
        home.Emergency_Button.click();
        emergency.Police_Silent.click();
        Thread.sleep(1000);
        Assert.assertTrue(emergency.Alarm_status.getText().equals("Silent Police Emergency"));
        alarm_verification();
    }

    @Test(priority =3)
    public void firePanic() throws InterruptedException {
        emergency = PageFactory.initElements(driver, EmergencyPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Creating fire alarm from a panel");
        home.Emergency_Button.click();
        emergency.Fire_icon.click();
        Thread.sleep(1000);
        Assert.assertTrue(emergency.Alarm_status.getText().equals("Fire Emergency"));
        alarm_verification();
    }

    @Test(priority =4)
    public void auxiliaryPanic(){
        emergency = PageFactory.initElements(driver, EmergencyPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Creating auxiliary alarm from a panel");
        home.Emergency_Button.click();
        emergency.Auxiliary_icon.click();
        Assert.assertTrue(emergency.Alarm_status.getText().equals("Auxiliary Emergency"));
        alarm_verification();
    }

    @Test(priority =5)
    public void auxiliaryPanicSilent() throws InterruptedException {
        emergency = PageFactory.initElements(driver, EmergencyPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Creating silent auxiliary alarm from a panel");
        home.Emergency_Button.click();
        emergency.Auxiliary_Silent.click();
        Thread.sleep(1000);
        Assert.assertTrue(emergency.Alarm_status.getText().equals("Silent Auxiliary Emergency"));
        alarm_verification();
    }
    @AfterClass
    public void tearDown() throws Exception {
        quitDriver();
    }
}
