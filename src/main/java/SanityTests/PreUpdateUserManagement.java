package SanityTests;

import PanelPages.HomePage;
import PanelPages.UserManagementPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Setup;

public class PreUpdateUserManagement extends Setup{
    UserManagementPage user_m;
    HomePage home;

    public PreUpdateUserManagement() throws Exception {
    }

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver();
    }

    @Test
    public void addUserUnlimited() throws InterruptedException {
        System.out.println("Adding a new user NewUser with the user code 5643");
        user_m = PageFactory.initElements(driver, UserManagementPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        navigateToUserManagementPage();
        user_m.Add_User.click();
        Thread.sleep(1000);
        addUser("NewUser", "5643");
        user_m.Add_User_add_page.click();
        Thread.sleep(1000);
        home.Home_button.click();
        Thread.sleep(4000);
    }

    @Test(priority = 1)
    public void verifyNewUserCodeWorks() throws Exception {
        System.out.println("Verifying a new user code is working correctly");
        home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(3000);
        home.DISARM.click();
        home.Five.click();
        home.Six.click();
        home.Four.click();
        home.Three.click();
        Thread.sleep(1000);
        verifySystemState("DISARMED");
        Thread.sleep(1000);
    }

    @Test(priority = 2)
    public void addMasterUnlimited() throws InterruptedException {
        System.out.println("Adding a new Master NewMaster with the code 3331");
        user_m = PageFactory.initElements(driver, UserManagementPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        navigateToUserManagementPage();
        user_m.Add_User.click();
        Thread.sleep(1000);
        addUser("NewMaster", "3331");
        user_m.Add_User_Type_options.click();
        user_m.User_Type_Master.click();
        user_m.Add_User_add_page.click();
        home.Home_button.click();
        Thread.sleep(1000);
    }

    @Test(priority = 3)
    public void verifyNewMasterCodeWorks() throws Exception {
        System.out.println("Verifying a new user code is working correctly");
        home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(3000);
        home.DISARM.click();
        home.Three.click();
        home.Three.click();
        home.Three.click();
        home.One.click();
        Thread.sleep(1000);
        verifySystemState("DISARMED");
        Thread.sleep(1000);
    }

    @Test(priority = 4)
    public void addGuestUnlimited() throws InterruptedException {
        System.out.println("Adding a new Guest NewGuest with the code 8800");
        user_m = PageFactory.initElements(driver, UserManagementPage.class);
        home = PageFactory.initElements(driver, HomePage.class);
        navigateToUserManagementPage();
        user_m.Add_User.click();
        Thread.sleep(1000);
        addUser("NewGuest", "8800");
        user_m.Add_User_Type_options.click();
        user_m.User_Type_Guest.click();
        user_m.Add_User_add_page.click();
        home.Home_button.click();
        Thread.sleep(1000);
    }

    @Test(priority = 5)
    public void verifyNewGuestCodeWorks() throws Exception {
        System.out.println("Verifying a new user code is working correctly");
        home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(3000);
        home.DISARM.click();
        home.Eight.click();
        home.Eight.click();
        home.Zero.click();
        home.Zero.click();
        Thread.sleep(1000);
        verifySystemState("DISARMED");
    }

    @AfterClass
    public void tearDown() throws Exception {
        quitDriver();
    }

    public void addUser(String Name, String UserCode) throws InterruptedException {
        UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
        user_m.Add_User_Name_field.sendKeys(Name);
        user_m.Add_User_Code_field.sendKeys(UserCode);
        user_m.Add_Confirm_User_Code_field.sendKeys(UserCode);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
