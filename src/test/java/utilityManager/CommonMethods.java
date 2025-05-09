package utilityManager;

import factory.SeleniumHandle;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonMethods {

    private static final Logger logger = LogManager.getLogger(CommonMethods.class);


    public static void sendtoSleep(int number)
    {
        try
        {
            Thread.sleep(number);
        }catch (Exception e)
        {

            System.out.println( "Thread.sleep(number) unsuccessfull");

        }
    }

    public static void waitForvisibility(WebDriver  driver , WebElement element)
    {
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void handleAlert(boolean condition , WebDriver driver)
    {
        Alert alert = driver.switchTo().alert();
        if(condition==true) {
        alert.accept();
        }
        else
        {
            alert.dismiss();
        }
    }


    public static void moveToElement( WebElement element , WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }








}
