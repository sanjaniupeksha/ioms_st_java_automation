package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilityManager.CommonMethods;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class BroadcastMessages {

    WebDriver driver;

    String msg_Title = null;

    public String getMsg_Title() {
        return msg_Title;
    }

    String msg_Content = null;

    public String getMsg_Content() {
        return msg_Content;
    }

    public BroadcastMessages(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@data-tip='Broadcast Messages']")
    private WebElement broadcastMessageButton;

    @FindBy(xpath = "//span[text()='Broadcast Messages']")
    private WebElement broadcastMessageHeader;

    @FindBy(xpath = " //a[text()=' Create Broadcast Message']")
    private WebElement createbroadcastMessageBtn;

    @FindBy(xpath = " //li[text()='Create New Broadcast Message']")
    private WebElement createnewbroadcastMessageheader;


    @FindBy(id = "title")
    private WebElement textBoxMessageTitle;

    @FindBy(xpath = "//span[text()='Message Title ']/span")
    private WebElement labelMessageTitle;

    @FindBy(id = "content")
    private WebElement textBoxMessageContent;

    @FindBy(xpath = "//span[text()='Message Content ']/span")
    private WebElement labelMessageContent;

    @FindBy(xpath = "//span[text()='Users ']/span")
    private WebElement labelUsers;

    @FindBy(xpath = "//span[text()='Users ']/../..//label/input")
    private WebElement dropdownUsers;

    @FindBy(xpath = "//button[text()='Send Message']")
    private WebElement buttonSendMessage;

    @FindBy(xpath = "//a[text()='Cancel']")
    private WebElement buttonCancel;


    @FindBy(linkText = "Broadcast Messages")
    private WebElement broadCastMessagesLinkTexttonavigateBack;




    public void navigateToBroadcastMessages() {
        broadcastMessageButton.click();
        CommonMethods.waitForvisibility(driver, broadcastMessageHeader);
    }

    public void clickCreateBroadcastMessage() {
        createbroadcastMessageBtn.click();
        CommonMethods.waitForvisibility(driver,createnewbroadcastMessageheader);

    }

    public void clickBroadcastMessageLinkText() {
        broadCastMessagesLinkTexttonavigateBack.click();
        CommonMethods.waitForvisibility(driver, broadcastMessageHeader);
    }



    public void fillCreateBroadcastMessage(String title, String content, String[] users) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Assert.assertEquals(labelMessageTitle.getText().toString(),"*");
        textBoxMessageTitle.click();

        msg_Title = title + "_" + dtf.format(now);
        textBoxMessageTitle.sendKeys(msg_Title);

        Assert.assertEquals(labelMessageContent.getText().toString(),"*");
        textBoxMessageContent.click();

        msg_Content = content + "_" + dtf.format(now);
        textBoxMessageContent.sendKeys(msg_Content);

        Assert.assertEquals(labelUsers.getText().toString(),"*");

        Actions action = new Actions(driver);

        for(String user :users) {
            dropdownUsers.click();
            CommonMethods.sendtoSleep(1000);
            dropdownUsers.sendKeys(user);
            CommonMethods.sendtoSleep(1000);
            action.moveToElement(driver.findElement(By.id("data-0"))).click();
            action.build().perform();
        }

        for(String user :users) {
           driver.findElement(By.xpath("//span[text()='"+user+" ']")).isDisplayed();
        }

    }

    public void sendBroadcastMessage(  )
    {
        buttonCancel.isDisplayed();
        buttonSendMessage.click();
        WebElement ele = driver.findElement(By.xpath("//span[text()='Send broadcast message successfully']"));
        CommonMethods.waitForvisibility(driver, ele);
    }


    public String getTableHeader( String header  )
    {
        String headers = "(// th[@role='columnheader'])["+header+"]";
        System.out.println(driver.findElement(By.xpath(headers)).getText());
        return driver.findElement(By.xpath(headers)).getText();

    }

    public String getTableValues( String value  )
    {
        String tableValues = "(//tbody/tr[1]/td[@role='cell'])["+value+"]";
        System.out.println(tableValues);
        System.out.println(driver.findElement(By.xpath(tableValues)).getText());
        return driver.findElement(By.xpath(tableValues)).getText();

    }

    public void verifyCancelBtn(   )
    {
      buttonCancel.click();
      CommonMethods.waitForvisibility(driver, broadcastMessageHeader);
    }



}
