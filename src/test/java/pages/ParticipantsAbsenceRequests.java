package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilityManager.CommonMethods;




public class ParticipantsAbsenceRequests {

    WebDriver driver;
    static String  participant;

    public ParticipantsAbsenceRequests(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//a[@data-tip='Participants’ Absence Requests']")
    private WebElement absenceRequests;

    @FindBy(xpath = "//h4[contains(text(),'Absence Requests')]")
    private WebElement absenceRequestsHeader;

    @FindBy(xpath = "//button[text()=' Create Absence Request']")
    private WebElement createabsenceRequestBtn;

    @FindBy(xpath = "//h3[text()='Create Participant Absence Request']")
    private WebElement createabsenceRequestHeader;

    @FindBy(xpath = "//button[text()='Close']")
    private WebElement cancelButton;

    @FindBy(xpath = "//span[text()='Course Name ']/ancestor::div[3]//input")
    private WebElement courceNamedropdown;

    @FindBy(xpath = "//span[text()='Select watch ']/ancestor::div[3]//input")
    private WebElement watchNamedropdown;

    @FindBy(xpath = "//span[text()='Select participant ']/ancestor::div[3]//input")
    private WebElement participantNamedropdown;

    @FindBy(xpath = "//span[text()='//span[text()='Related Medical/Non-Medical Log ']/ancestor::div[3]//input")
    private WebElement incidentLogdropdown;

    @FindBy(xpath = "//span[text()='Course Name ']/span")
    private WebElement courseNameLabel;

    @FindBy(xpath = "//span[text()='Select watch ']/span")
    private WebElement watchLabel;

    @FindBy(xpath = "//span[text()='Select participant ']/span")
    private WebElement participantLabel;

    @FindBy(xpath = "//span[text()='Reason ']/span")
    private WebElement reasonLabel;

    @FindBy(xpath = "//span[text()='Related Medical/Non-Medical Log ']/span")
    private WebElement medical_NonMedicalLog_Label;

    @FindBy(id = "Remarks")
    private WebElement RemarksTextbox;

    @FindBy(id = "name")
    private WebElement participantNamefilter;

    @FindBy(xpath = "//button[text()='Clear All']")
    private WebElement clearALL;


    @FindBy(xpath = "//span[text()='Status ']/ancestor::div[3]//input")
    private WebElement StatusFilter;

    @FindBy(xpath = "//button[text()=' Submit Request']")
    private WebElement submitBtn;

    @FindBy(xpath = "//button[text()='Respond']")
    private WebElement respondBtn;

    @FindBy(xpath = "//label[text()=' Approved ']/input")
    private WebElement approveRadioBtn;

    @FindBy(id = "Message")
    private WebElement messageTextBox;

    @FindBy(xpath = "//div[@data-tip='Delete']/button")
    private WebElement deleteButton;


    public void navigateToAbsenceRequests() {
        absenceRequests.click();
        CommonMethods.waitForvisibility(driver, absenceRequestsHeader);

    }

    public void clickCreateAbsenceRequests() {
        createabsenceRequestBtn.click();
        CommonMethods.waitForvisibility(driver, createabsenceRequestHeader);
        boolean value = driver.findElement(By.xpath("//span[text()='Participant Details: ']")).isDisplayed();
        Assert.assertTrue(value);
    }

    public void cancel() {
        CommonMethods.sendtoSleep(2000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", cancelButton);
        CommonMethods.sendtoSleep(1000);
        cancelButton.click();
        CommonMethods.waitForvisibility(driver, absenceRequestsHeader);
    }

    public void fillCreateBroadcastMessage(String courseName, String watch, String participantnm) {
        participant = participantnm;
        CommonMethods.sendtoSleep(1000);
        selectdropdown(courceNamedropdown, courseName);
        selectdropdown(watchNamedropdown, watch);
        selectdropdown(participantNamedropdown, participantnm);
    }

    public void selectReason(String reason, String log, String remarks) {
        String option = reason;
        driver.findElement(By.xpath("//label[text()=' " + option + " ']/input")).click();
        if (option.equals("Incident-related")) {

            selectdropdown(incidentLogdropdown, "MED");
        }
        RemarksTextbox.sendKeys(remarks);
    }

    public void saveRequest() {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", submitBtn);
        submitBtn.click();
    }

    public void SearchRequestCreatedInTable() {
        CommonMethods.sendtoSleep(2000);
        clearALL.click();
        CommonMethods.sendtoSleep(2000);
        participantNamefilter.click();
        participantNamefilter.sendKeys(participant);
        CommonMethods.sendtoSleep(3000);
   }

    public void selectStatusFilter() {
        StatusFilter.click();
        Actions action = new Actions(driver);
        CommonMethods.sendtoSleep(500);
        action.moveToElement(driver.findElement(By.id("data-undefined"))).click();
        action.build().perform();
        CommonMethods.sendtoSleep(500);
        StatusFilter.click();
        CommonMethods.sendtoSleep(500);
        action.moveToElement(driver.findElement(By.id("data-0"))).click();
        action.build().perform();
        CommonMethods.sendtoSleep(5000);

    }

    public void verifySameParticipantSelection() {
        int size = driver.findElements(By.xpath("//span[text()='A pending or approved request exists for this participant. Please select another participant']")).size();
        Assert.assertTrue(size > 0);
        cancel();
    }

    public void approve() {
        CommonMethods.moveToElement(respondBtn, driver);
        respondBtn.click();
        approveRadioBtn.click();

    }

    public void delete() {
        CommonMethods.moveToElement(deleteButton, driver);
        deleteButton.click();


    }


    public void checkMandatory() {
        String option = "Incident-related";
        Assert.assertEquals(courseNameLabel.getText().toString(), "*");
        Assert.assertEquals(participantLabel.getText().toString(), "*");
        Assert.assertEquals(watchLabel.getText().toString(), "*");
        Assert.assertEquals(reasonLabel.getText().toString(), "*");
        driver.findElement(By.xpath("//label[text()=' " + option + " ']/input")).click();
        CommonMethods.sendtoSleep(500);
        Assert.assertEquals(medical_NonMedicalLog_Label.getText().toString(), "*");

    }


    public void selectdropdown(WebElement elementname, String value) {
        Actions action = new Actions(driver);
        elementname.click();
        CommonMethods.sendtoSleep(2000);
        elementname.sendKeys(value);
        CommonMethods.sendtoSleep(3000);
        action.moveToElement(driver.findElement(By.id("data-0"))).click();
        action.build().perform();
        CommonMethods.sendtoSleep(2000);
    }


}
