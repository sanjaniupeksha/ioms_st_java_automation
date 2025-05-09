package factory;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class UINotificationService extends SeleniumHandle {

	private static final Logger logger = LoggerFactory.getLogger(UINotificationService.class);
	private WebDriver driver;
	private static UINotificationService ourInstance = new UINotificationService();
	private static final String startWebNotificationJsScript = "window.notifications = []; window.DefaultNotification = window.Notification; (function () { function notificationCallback(title, opt) { console.log(\"notification title: \", title); console.log(\"notification body: \", opt.body); console.log(\"notification tag: \", opt.tag); console.log(\"notification icon: \", opt.icon); } const handler = { construct(target, args) { notificationCallback(...args); var notification = new target(...args); window.notifications.push(notification); return notification; } }; const ProxifiedNotification = new Proxy(Notification, handler); window.Notification = ProxifiedNotification; })();";
	private static final String stopWebNotificationJsScript = "window.notifications = []; window.Notification = window.DefaultNotification;";

	private static final String startPushNotificationJsScript = "window.notificationsMap = Object.create(null); async function getServiceWorkerRegistration(){ window.myServiceWorkerRegistration = await navigator.serviceWorker.getRegistration(\"%s\"); return window.myServiceWorkerRegistration;}; async function getNotifications() { window.myNotifications = await window.myServiceWorkerRegistration.getNotifications();}; window.notificationListener = setInterval(async function() { console.log(\"checking for notifications...\"); await getServiceWorkerRegistration(); await getNotifications(); for(var key in window.myNotifications){ window.notificationsMap[window.myNotifications[key].tag] = window.myNotifications[key]; }; }, 2000);";
	private static final String stopPushNotificationJsScript = "clearInterval(window.notificationListener); window.notificationsMap = Object.create(null); window.notifications = [] ;";
	private static final String getPushNotificationsJsScript = "function getCollectedNotifications(){ window.notifications = [] ; var count = 0; for (var prop in window.notificationsMap) { count++; window.notifications.push(window.notificationsMap[prop]); } console.log(\"Total notifications count is: \" + count); return window.notifications;}; return getCollectedNotifications();";

	public static UINotificationService getInstance(WebDriver driver) {
		ourInstance.driver = driver;
		return ourInstance;
	}

	private UINotificationService() {
	}

	public void startWebNotificationListener(){
		executeJavaScript(startWebNotificationJsScript);
	}

	public void startPushNotificationListener(String notificationServiceURL){
		executeJavaScript(String.format(startPushNotificationJsScript,notificationServiceURL));
	}

	public void stopWebNotificationListener(){
		executeJavaScript(stopWebNotificationJsScript);
	}
	public void stopPushNotificationListener(){
		executeJavaScript(stopPushNotificationJsScript);
	}


	public boolean isNotificationPresent(Map<String,String> filter, String notificationType){
		ArrayList<Map> notifications;
		if(notificationType.equalsIgnoreCase("web"))
			notifications = (ArrayList<Map>) executeJavaScript("return window.notifications;");
		else
			notifications = (ArrayList<Map>) executeJavaScript(getPushNotificationsJsScript);

		AtomicInteger ai = new AtomicInteger(0);
		AtomicBoolean ab = new AtomicBoolean(false);
		System.out.println(notifications);
		notifications.stream()
				.forEach(n->{
					System.out.println(n.get("title"));
					System.out.println(filter.get("title"));

						if(n.get("title").toString().trim().equals(filter.get("title").toString().trim()))
						{
							Assert.assertEquals(n.get("body"),(filter.get("body")));
							Assert.assertEquals(n.get("icon"),(filter.get("icon")));
							logger.info("Closing this UI Notification : " + n.get("title"));
							System.out.println("Closing this UI Notification : " + n.get("title"));
							String closeScript = String.format("window.notifications[%d].close();",ai.get());
							((JavascriptExecutor) driver).executeScript(closeScript);
							ab.set(true);
						}
				});

		return ab.get();
	}





}
