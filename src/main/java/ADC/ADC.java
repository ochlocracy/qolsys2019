package ADC;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import utils.ConfigProps;
import utils.Setup;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ADC extends Setup {

    public ADC() throws Exception {
        ConfigProps.init();
    }
    public String ADCexecute;
    public String getADCexecute() {return ADCexecute;}
    HashMap<String, String> hmap = new HashMap<String, String>();

    public String getAccountId() throws IOException {
        String accountId = null;
        if (get_UDID().equals("8ebdbcf6")) {
            accountId = "5432189";
            return accountId;
        } else if (get_UDID().equals("62864b84")) { //Anya AT&T
            accountId = "5434143";
        } else if (get_UDID().equals("9110ead0")) {   // Denis
            accountId = "7377258";
        } else if (get_UDID().equals("2c68bc67")) {    // Denis
            accountId = "5323198";
        }
        return accountId;
    }

    public String getLogin(String UDID){
        hmap.put("9110ead0", "powerG_prod");
        hmap.put("62864b84", "LeBron_James");
        hmap.put("623cf0f1", "qautozone");
        return hmap.get(UDID);
    }

    public void ADC_verification(String string, String string1) throws IOException, InterruptedException {
        String[] message = {string, string1};
        if (getADCexecute().equals("true")) {
            New_ADC_session(getAccountId());
            driver1.findElement(By.partialLinkText("Sensors")).click();
            Thread.sleep(2000);
            Request_equipment_list();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
            Thread.sleep(7000);
            driver1.navigate().refresh();
            Thread.sleep(7000);
            for (int i = 0; i < message.length; i++) {
                driver1.navigate().refresh();
                Thread.sleep(2000);
                try {
                    WebElement history_message = driver1.findElement(By.xpath(message[i]));
                    Assert.assertTrue(history_message.isDisplayed());
                    {
                        System.out.println("Pass: message is displayed " + history_message.getText());
                    }
                } catch (Exception e) {
                    System.out.println("***No such element found!***");
                }
            }
        } else {
            System.out.println("Set execute to TRUE to run ADC verification part");
        }
        Thread.sleep(7000);
    }
    public void ADC_verification_PG(String string, String string1) throws IOException, InterruptedException {
        String[] message = {string, string1};
        Thread.sleep(4000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        Thread.sleep(7000);
        driver1.navigate().refresh();
        Thread.sleep(7000);
        for (int i = 0; i < message.length; i++) {
            driver1.navigate().refresh();
            Thread.sleep(2000);
            try {
                WebElement history_message = driver1.findElement(By.xpath(message[i]));
                Assert.assertTrue(history_message.isDisplayed());
                {
                    System.out.println("Pass: message is displayed " + history_message.getText());
                }
            } catch (Exception e) {
                System.out.println("***No such element found!***");
            }
        }
    }

    public void New_ADC_session(String accountID) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        driver1.manage().window().maximize();
        String ADC_URL = "https://alarmadmin.alarm.com/Support/CustomerInfo.aspx?customer_Id=" + accountID;
        driver1.get(ADC_URL);
        String login = "qautomation";
        String password = "Qolsys123";
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtUsername")));
        driver1.findElement(By.id("txtUsername")).sendKeys(login);
        driver1.findElement(By.id("txtPassword")).sendKeys(password);
        driver1.findElement(By.id("butLogin")).click();
        driver1.findElement(By.id("ctl00_txtNewCustomerId")).click();
        driver1.findElement(By.id("ctl00_txtNewCustomerId")).sendKeys(accountID);
        driver1.findElement(By.id("ctl00_butChangeCustomer")).click();
        driver1.findElement(By.partialLinkText("Equipment")).click();
        TimeUnit.SECONDS.sleep(3);
    }
    public void New_ADC_session_User() throws InterruptedException, IOException {
        driver1.manage().window().maximize();
        String ADC_URL = "https://www.alarm.com/login.aspx";
        driver1.get(ADC_URL);
        driver1.findElement(By.id("ctl00_ContentPlaceHolder1_loginform_txtUserName")).sendKeys(ConfigProps.login);
        driver1.findElement(By.className("password")).sendKeys(ConfigProps.password);
        Thread.sleep(1000);
        driver1.findElement(By.id("ctl00_ContentPlaceHolder1_loginform_signInButton")).click();
        Thread.sleep(10000);
        try {
            if (driver1.findElement(By.xpath("//*[@id='ember735']")).isDisplayed()) {
                driver1.findElement(By.xpath("//*[@id='ember735']")).click();
            }
        } catch (NoSuchElementException e) {
        }
        Thread.sleep(3000);
    }

    public void Request_equipment_list() throws InterruptedException {
        System.out.println("Request sensor list and Sensor names");
        driver1.findElement(By.id("ctl00_phBody_sensorList_butRequest")).click();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("Request equipment list");
        driver1.findElement(By.id("ctl00_refresh_sensors_button_btnRefreshPage")).click();
        TimeUnit.SECONDS.sleep(3);
        driver1.findElement(By.xpath("//input[@value='Request Sensor Names']")).click();
        TimeUnit.SECONDS.sleep(2);
        try {
            Alert alert = driver1.switchTo().alert();
            driver1.switchTo().alert().accept();
            alert.accept();
        } catch (Exception e) {}
        TimeUnit.SECONDS.sleep(5);
        driver1.findElement(By.id("ctl00_refresh_sensors_button_btnRefreshPage")).click();
        TimeUnit.SECONDS.sleep(10);
        driver1.findElement(By.xpath("//input[@value='Request Sensor Names']")).click();
        TimeUnit.SECONDS.sleep(5);
        try {
            Alert alert = driver1.switchTo().alert();
            driver1.switchTo().alert();
            alert.accept();
        } catch (Exception e) {}
        TimeUnit.SECONDS.sleep(5);
        driver1.findElement(By.id("ctl00_refresh_sensors_button_btnRefreshPage")).click();
        TimeUnit.SECONDS.sleep(5);
    }
    public void update_sensors_list() throws InterruptedException, IOException {
        New_ADC_session(getAccountId());
        Thread.sleep(3000);
        driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(5000);
        Request_equipment_list();
        Thread.sleep(1000);
    }

    public void Sensor_verification(String name, String group, String sensor_type, int number) {
        //number = number of the table row
        String sName = driver1.findElement(By.xpath(".//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[" + number + "]/td[2]")).getText();
        boolean sensor_name = sName.contains(name);
        if (sensor_name == true) {
            System.out.println("Sensor name is " + name + ": " + sensor_name);
        } else {
            System.out.println("Sensor name is " + name + ": " + sensor_name + " *** " + sName + " *** ");
        }
        String sGroup = driver1.findElement(By.xpath(".//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[" + number + "]/td[3]")).getText();
        boolean sensor_group = sGroup.contains(group);
        if (sensor_group == true) {
            System.out.println("Sensor group is " + group + ": " + sensor_group);
        } else {
            System.out.println("Sensor group is " + group + ": " + sensor_group + " *** " + sGroup + " *** ");
        }
        String sType = driver1.findElement(By.xpath(".//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[" + number + "]/td[4]")).getText();
        boolean sens_type = sType.contains(sensor_type);
        if (sens_type == true) {
            System.out.println("Sensor type is " + sensor_type + ": " + sens_type);
        } else {
            System.out.println("Sensor type is " + sensor_type + ": " + sens_type + " *** " + sType + " *** ");
        }
    }

    public void Sensor_verification_full(String name, String group, String sensor_type, String rep_type, String input_status, int number) {
        //number = number of the table row
        String sName = driver1.findElement(By.xpath(".//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[" + number + "]/td[2]")).getText();
        boolean sensor_name = sName.contains(name);
        if (sensor_name == true) {
            System.out.println("Sensor name is " + name + ": " + sensor_name);
        } else {
            System.out.println("Sensor name is " + name + ": " + sensor_name + " *** " + sName + " *** ");
        }
        String sGroup = driver1.findElement(By.xpath(".//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[" + number + "]/td[3]")).getText();
        boolean sensor_group = sGroup.contains(group);
        if (sensor_group == true) {
            System.out.println("Sensor group is " + group + ": " + sensor_group);
        } else {
            System.out.println("Sensor group is " + group + ": " + sensor_group + " *** " + sGroup + " *** ");
        }
        String sType = driver1.findElement(By.xpath(".//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[" + number + "]/td[4]")).getText();
        boolean sens_type = sType.contains(sensor_type);
        if (sens_type == true) {
            System.out.println("Sensor type is " + sensor_type + ": " + sens_type);
        } else {
            System.out.println("Sensor type is " + sensor_type + ": " + sens_type + " *** " + sType + " *** ");
        }
        String reporting_type = driver1.findElement(By.xpath(".//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[" + number + "]/td[5]")).getText();
        boolean sensor_reporting_type = reporting_type.contains(rep_type);
        if (sensor_reporting_type == true) {
            System.out.println("Sensor reporting type is " + rep_type + ": " + sensor_reporting_type);
        } else {
            System.out.println("Sensor reporting type is " + rep_type + ": " + sensor_reporting_type + " *** " + reporting_type + " *** ");
        }
        String status = driver1.findElement(By.xpath(".//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[" + number + "]/td[6]")).getText();
        boolean current_status = status.contains(input_status);
        if (current_status == true) {
            System.out.println("Sensor status is " + input_status + ": " + current_status);
        } else {
            System.out.println("Sensor status is " + input_status + ": " + current_status + " *** " + status + " *** ");
        }
    }

    public WebElement fluentWait(String identifier) throws TimeoutException {
        FluentWait<WebDriver> fwait = new FluentWait<>(driver1)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement ele = null;
        // start waiting for given element
        ele = fwait.until(ExpectedConditions.visibilityOfElementLocated(By.id(identifier)));
        return ele;
    }

}