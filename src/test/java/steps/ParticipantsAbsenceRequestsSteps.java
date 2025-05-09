package steps;

import factory.SeleniumHandle;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import pages.ParticipantsAbsenceRequests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ParticipantsAbsenceRequestsSteps extends SeleniumHandle {

    private static final Logger log = LoggerFactory.getLogger(ParticipantsAbsenceRequestsSteps.class);
    ParticipantsAbsenceRequests  participantsAbsenceRequests;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
    LocalDateTime now = null;


    @Given("user navigates to Participants Absence Request page")
    public void user_navigates_to_ParticipantsAbsenceRequestPage() {
        participantsAbsenceRequests = new ParticipantsAbsenceRequests(driver);
        participantsAbsenceRequests.navigateToAbsenceRequests();

    }

    @Given("^user filter created Absence request with participant name and click delete button$")
    public void user_delete_request() {
        participantsAbsenceRequests.SearchRequestCreatedInTable();
        participantsAbsenceRequests.delete();
        Assert.assertTrue(driver.findElements(By.xpath("//*[text()='Delete Absence Request']")).size()>0);
        Assert.assertTrue(driver.findElements(By.xpath("//*[text()='This cannot be undone']")).size()>0);
        driver.findElement(By.xpath("//button[text()=' Delete']")).click();

    }

    @And("^user filter created Absence request with participant name$")
    public void user_search_request() {
        participantsAbsenceRequests.SearchRequestCreatedInTable();
    }

    @And("^user approves created Absence request$")
    public void user_approves_request() {
        participantsAbsenceRequests.approve();

    }






    @Given("click on create new Participants Absence Request button")
    public void create_new_ParticipantsAbsenceRequest() {
        participantsAbsenceRequests.clickCreateAbsenceRequests();

    }

    @Given("user save Absence Request")
    public void Verify_new_ParticipantsAbsenceRequest() {

        participantsAbsenceRequests.saveRequest();
    }

    @Given("user should see an error message for participant")
    public void Verify_ParticipantsAbsenceRequestforSameParticipant() {

        participantsAbsenceRequests.verifySameParticipantSelection();
    }

    @Given("^user should see a record added with status Pending and ([^\"]*) course code$")
    public void Save_new_ParticipantsAbsenceRequest(String code) {


        participantsAbsenceRequests.SearchRequestCreatedInTable();
        Assert.assertTrue(driver.findElements(By.xpath("//td/div[text()='Pending']")).size()==1);
        Assert.assertTrue(driver.findElements(By.xpath("//td[text()='"+code+"']")).size()==1);


    }

    @When("^fill ([^\"]*) course  ([^\"]*) watchName and ([^\"]*) participant  details$")
    public void fill_data(String course, String watch, String participant) {

        participantsAbsenceRequests.fillCreateBroadcastMessage(course ,watch ,participant);
    }

    @When("^user select reason ([^\"]*) and  incident ([^\"]*) if related and remarks ([^\"]*)$")
    public void user_enter_data_for_create_broadcastMessage(String reason, String log, String remarks) {
        participantsAbsenceRequests.selectReason(reason,log,remarks);

    }


    @Given("user navigated back to previous page on click of button cancel")
    public void verifyCancel() {
        participantsAbsenceRequests.cancel();

    }

    @Given("verify Mandatory fields marked properly")
    public void verifyMandatory() {
        participantsAbsenceRequests.checkMandatory();
    }




}
