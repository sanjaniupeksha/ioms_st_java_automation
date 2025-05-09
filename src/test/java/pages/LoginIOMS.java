package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utilityManager.CommonMethods;


public class LoginIOMS {

    WebDriver driver;


    public LoginIOMS(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(name = "loginfmt")
    private WebElement usernameField;

    @FindBy(name = "passwd")
    private WebElement passwordField;

    @FindBy(xpath = "//a[text()=' Login With W.O.G']")
    private WebElement loginButton;

    @FindBy(xpath = " //*[@type='submit']")
    private WebElement submit;

    @FindBy(xpath = "//*[text()='Yes']")
    private WebElement passwordRememberField;

    @FindBy(xpath = "//*[@aria-label='Dashboard']//div[text()='IOMS']")
    private WebElement IOMSLogo;

    public void clickLoginButton(String username, String pssword) {
        loginButton.click();
        CommonMethods.waitForvisibility(driver, usernameField);
        usernameField.click();
        usernameField.sendKeys(username);
        submit.click();
        CommonMethods.waitForvisibility(driver, passwordField);
        passwordField.click();
        passwordField.sendKeys(pssword);
        submit.click();
        CommonMethods.waitForvisibility(driver, passwordRememberField);
        passwordRememberField.click();
        CommonMethods.sendtoSleep(4000);
    }

    public void verifyloginIOMS() {
        Assert.assertEquals(driver.getTitle(), "IOMS");
        driver.findElement(By.xpath("//*[@alt='logo']")).isDisplayed();
        driver.findElement(By.xpath("(//div[text()='IOMS'])[1]")).isDisplayed();
    }

}
