package steps;

import factory.SeleniumHandle;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import pages.BroadcastMessages;
import utilityManager.CommonMethods;
import factory.UINotificationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class BroadcastMessagesSteps extends SeleniumHandle {

    private static final Logger log = LoggerFactory.getLogger(BroadcastMessagesSteps.class);
    BroadcastMessages broadcastMessages;
    UINotificationService uiNotificationService;
    String msg_Title = null;
    String msg_Content = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
    LocalDateTime now = null;
    String[] usersArray = null;


    @Given("user navigates to BroadcastMessages screen and click Create Broadcast Message")
    public void user_navigates_to_BroadcastMessages_screen() {
        uiNotificationService = UINotificationService.getInstance(driver);
        broadcastMessages = new BroadcastMessages(driver);
        broadcastMessages.navigateToBroadcastMessages();
        broadcastMessages.clickCreateBroadcastMessage();
    }


    @When("^user enter ([^\"]*) title and ([^\"]*) content and select users ([^\"]*) for the broadcast message$")
    public void user_enter_data_for_create_broadcastMessage(String title, String Content, String users) {
        usersArray = users.split(",");
        msg_Title = title;
        msg_Content = Content;
        broadcastMessages.fillCreateBroadcastMessage(title, Content, usersArray);
    }

    @Then("user should be able to see a success message")
    public void userShouldBeAbleToSeeASuccessMessage() {
        uiNotificationService.startPushNotificationListener("https://develop.obs-dev.pssobs.net/");
        broadcastMessages.sendBroadcastMessage();
        now = LocalDateTime.now();
    }


    @Then("user should be receive a web push notification")
    public void userShouldBeReceiveAWebPushNotification() {

        CommonMethods.sendtoSleep(500);
        Map<String, String> notificationFilter = new HashMap<>();
        notificationFilter.put("title", broadcastMessages.getMsg_Title());
        notificationFilter.put("body", broadcastMessages.getMsg_Content());
        notificationFilter.put("icon", "https://develop.obs-dev.pssobs.net/favicon.ico");
        boolean flag = false;
        for (int i = 0; i < 20; i++) {
            flag = uiNotificationService.isNotificationPresent(notificationFilter, "push");
            if (flag == false) {
                CommonMethods.sendtoSleep(200);
            }
        }
        Assert.assertTrue(flag);
        uiNotificationService.stopPushNotificationListener();
    }


    @Given("user has created a broadcast message")
    public void userHadSentBroadCastMessage() {

        Assert.assertEquals(broadcastMessages.getTableHeader("1") , "Message Title");
        Assert.assertEquals(broadcastMessages.getTableHeader("2") , "Message Content");
        Assert.assertEquals(broadcastMessages.getTableHeader("3") , "Date Time Sent");
        Assert.assertEquals(broadcastMessages.getTableHeader("4") , "No. Sent");
        Assert.assertEquals(broadcastMessages.getTableHeader("5") , "No. of Seen");
        Assert.assertEquals(broadcastMessages.getTableHeader("6") , "No. of Acknowledged");

    }

    @Given("user should see the sent message details added in the broadcast messages table")
    public void verifyBroadCastMessageListTable() {
        Assert.assertEquals(broadcastMessages.getTableValues("1") , broadcastMessages.getMsg_Title());
        Assert.assertEquals(broadcastMessages.getTableValues("2") , broadcastMessages.getMsg_Content());
        Assert.assertEquals(broadcastMessages.getTableValues("3") , dtf.format(now));
        Assert.assertEquals(broadcastMessages.getTableValues("4") , String.valueOf(usersArray.length));
        Assert.assertEquals(broadcastMessages.getTableValues("5") , "0");
        Assert.assertEquals(broadcastMessages.getTableValues("6") , "0");
    }

    @Given("cancel button should take user back to previous page")
    public void verifyCancelButton() {
        broadcastMessages.verifyCancelBtn();
        broadcastMessages.clickCreateBroadcastMessage();
        broadcastMessages.clickBroadcastMessageLinkText();


    }



}
