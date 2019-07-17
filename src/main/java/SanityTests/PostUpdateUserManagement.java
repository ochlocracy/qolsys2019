package SanityTests;

import PanelPages.HomePage;
import PanelPages.UserManagementPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Setup;

import java.util.List;

public class PostUpdateUserManagement extends Setup {
    HomePage home;

    public PostUpdateUserManagement() throws Exception {
    }

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver();
    }

    @Test()
    public void verifyNewUserCodeWorks() throws Exception {
        System.out.println("Verifying a new user code is working correctly");
        home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(1000);
        home.DISARM.click();
        home.Five.click();
        home.Six.click();
        home.Four.click();
        home.Three.click();
        Thread.sleep(1000);
        verifySystemState("DISARMED");
        System.out.println("Pass: new user code is working correctly");
    }

    @Test(priority = 1)
    public void verifyNewMasterCodeWorks() throws Exception {
        System.out.println("Verifying a new master code is working correctly");
        home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(1000);
        home.DISARM.click();
        home.Three.click();
        home.Three.click();
        home.Three.click();
        home.One.click();
        Thread.sleep(1000);
        verifySystemState("DISARMED");
        System.out.println("Pass: new master code is working correctly");
    }

    @Test(priority = 2)
    public void verifyNewGuestCodeWorks() throws Exception {
        System.out.println("Verifying a new guest code is working correctly");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(1000);
        home.DISARM.click();
        home.Eight.click();
        home.Eight.click();
        home.Zero.click();
        home.Zero.click();
        Thread.sleep(1000);
        verifySystemState("DISARMED");
        System.out.println("Pass: new guest code is working correctly");
    }

    @Test(priority =3)
    public void deleteNewUsers() throws Exception {
        UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
         home = PageFactory.initElements(driver, HomePage.class);
        navigateToUserManagementPage();
        List<WebElement> delete = driver.findElements(By.id("com.qolsys:id/deleteImg"));
        for (int i = 3; i > 0; i--) {
            delete.get(1).click();
            user_m.User_Management_Delete_User_Ok.click();
        }
        Thread.sleep(1000);
        home.Home_button.click();
    }

    @AfterClass
    public void tearDown() throws Exception {
        quitDriver();
    }
}
