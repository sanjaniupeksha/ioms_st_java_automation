package steps;

import factory.SeleniumHandle;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import pages.LoginIOMS;
import utilityManager.CommonMethods;
import utilityManager.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;


public class LoginSteps extends SeleniumHandle {

    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);
    LoginIOMS loginioms;


    @Given("user navigate to IOMS Web Application by entering correct username and password")
    public void user_navigate_to_ioms_web_application_by_entering_correct_username_and_password() {
        loginioms = new LoginIOMS(driver);
        navigateToUrl(Constants.WEBURL_IOMS);
        driver.manage().deleteAllCookies();
        loginioms.clickLoginButton(Constants.usename1 ,Constants.password1);
        waitForPageLoad();
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.delay(200);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.delay(200);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        CommonMethods.sendtoSleep(2000);

        }

    @Then("user should be able to navigate to IOMS web successfully and see the IOMS logo")
    public void user_should_be_able_to_see_the_button_with_ioms_logo() {
        loginioms.verifyloginIOMS();
    }




}
