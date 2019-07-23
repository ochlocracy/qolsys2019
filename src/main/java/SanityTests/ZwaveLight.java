package SanityTests;

import PanelPages.Lights;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigProps;
import utils.Setup;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.util.List;

public class ZwaveLight extends Setup {

    public ZwaveLight() throws Exception {
        ConfigProps.init();
    }

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver();
        preconditions();
    }

    @Test
    public void Test1() throws Exception {
        Lights lights = PageFactory.initElements(driver, Lights.class);
        File LightOnIconImg = new File(projectPath + "/screen_shot/LightOnIconImg");
        File LightOffIconImg = new File(projectPath + "/screen_shot/LightOffIconImg");
        swipeLeft();
        WebElement ele = driver.findElement(By.id("com.qolsys:id/statusButton"));

        lights.Light_status.click();
        Thread.sleep(3000);
        checkStatus(LightOnIconImg, ele);
        lights.Light_status.click();
        Thread.sleep(3000);
        checkStatus(LightOffIconImg, ele);

    }

    @AfterClass
    public void tearDown() throws Exception {
        quitDriver();
    }

    //compare actual light status to expected using screenshot comparison
    public boolean checkStatus(File cmp, WebElement ele) throws Exception {
        File tmp = takeScreenshot(ele);
        Thread.sleep(10000);

        if (compareImage(tmp, cmp)) {
            System.out.println("Pass: light icon is the expected color");
          java.lang.Runtime.getRuntime().exec("rm -f " + tmp.getAbsolutePath());
            return true;
        } else {
            System.out.println("Fail: light icon is not the expected color");
            java.lang.Runtime.getRuntime().exec("rm -f " + tmp.getAbsolutePath());
            return false;
        }
    }
    //takes a screenshot of the given element and saves it to the given destination
    public File takeScreenshot(WebElement ele) throws Exception {
        // Get entire page screenshot
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage  fullImg = ImageIO.read(screenshot);

        // Get the location of element on the page
        Point point = ele.getLocation();

        // Get width and height of the element
        int eleWidth = ele.getSize().getWidth();
        int eleHeight = ele.getSize().getHeight();

        // Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
                eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);

        // Copy the element screenshot to disk
        File screenshotLocation = new File(projectPath + "/screen_shot/test");
        FileUtils.copyFile(screenshot, screenshotLocation);
        return  screenshotLocation;
    }
    //compares two images and returns whether or not they're identical
    public boolean compareImage(File fileA, File fileB) {
        try {
            // take buffer data from both image files //
            BufferedImage biA = ImageIO.read(fileA);
            DataBuffer dbA = biA.getData().getDataBuffer();
            int sizeA = dbA.getSize();
            BufferedImage biB = ImageIO.read(fileB);
            DataBuffer dbB = biB.getData().getDataBuffer();
            int sizeB = dbB.getSize();
            // compare data-buffer objects //
            if (sizeA == sizeB) {
                for (int i = 0; i < sizeA; i++) {
                    if (dbA.getElem(i) != dbB.getElem(i)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Failed to compare image files ..." + e);
            return false;
        }
    }

}
