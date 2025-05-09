package factory;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;


public class SeleniumHandle  {


	private static final Logger logger = LogManager.getLogger(SeleniumHandle.class);
	public WebDriver driver = CucumberHooks.driver;


	public void navigateToUrl(String url)
	{
		try {
			driver.get(url);
			waitForPageLoad();
			logger.info("Successfully naviagted to the URL:   "+url);
		} catch (Exception e) {
			logger.error("Unable to navigate to the URL:   "+url, e);
			throw e;
		}
	}

	public void waitForPageLoad()
	{
		try {
			new WebDriverWait(driver, Duration.ofSeconds(3)).until(
					webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		} catch (Exception e) {
			logger.error("Exception occured while waiting for the page to load! ", e);
		}
	}

	public Object executeJavaScript(String script) {
		Object obj = ((JavascriptExecutor)driver).executeScript(script,new Object[0]);
		return obj;
	}

	public Object executeJavaScript(String script, WebElement element) {
		Object obj = ((JavascriptExecutor)driver).executeScript(script, new Object[]{element});
		return obj;
	}


	
	
	public void enterText(By by, String textToEnter, String elementName)
	{
		try {
			//findElement(by).clear();
			findElement(by).sendKeys(textToEnter);
			logger.info("Successfully entered the text"+textToEnter +" in the element locator:   "+by +"-----> Element Name: "+elementName);
		} catch (Exception e) {
			logger.error("Unable to enter the text in the element locator:   "+by +"for Element: "+elementName, e);
			throw e;
		}
	}
	
	public void clickButton(By by, String elementName)
	{
		try {
			WebElement ele = findElement(by);
			ele.click();
			logger.info("Successfully clicked the element locator:   "+by +"-----> Element Name: "+elementName);
		} catch (Exception e) {
			logger.error("Unable to click the element locator:   "+by +"-----> Element Name: "+elementName, e);
			throw e;
		}
	}


	
	public void terminateBrowser()
	{
		try {
			if(driver != null)
			{
				driver.quit();
				driver = null;
			}
			logger.info("Successfully terminated the browser");
		} catch (Exception e) {
			logger.error("Unable to terminate the browser! ", e);
		}
	}
	
	public void hardWait(long millisec)
	{
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			logger.debug("Hard Wait interrupted! "+ e);
		}
	}
	
	public void takeScreenshot(String filePath)
	{
		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String path = filePath + "//Screenshot_"+System.currentTimeMillis()+".png";
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
			logger.error("Exception occured while taking the screenshot! ", e);
		}
	}
	
	public void takeScreenshotOfCompletePage(String filePath)
	{
	    try {
	    	//Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
	    	//String path = filePath + "//Screenshot_"+System.currentTimeMillis()+".png";
			//ImageIO.write(fpScreenshot.getImage(),"PNG",new File(path));
	    	if(driver != null)
	    	{
		    	JavascriptExecutor jexec = (JavascriptExecutor)driver;
		        jexec.executeScript("window.scrollTo(0,0)"); // will scroll to (0,0) position 
		        boolean isScrollBarPresent = (boolean)jexec.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight");
		        long scrollHeight = (long)jexec.executeScript("return document.documentElement.scrollHeight");
		        long clientHeight = (long)jexec.executeScript("return document.documentElement.clientHeight");
		        int fileIndex = 1;
	            if(isScrollBarPresent){
	                while(scrollHeight > 0){
	                	String path = filePath + "//Screenshot_"+System.currentTimeMillis()+".png";
	                    File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	                    FileUtils.copyFile(srcFile, new File(path));
	                    jexec.executeScript("window.scrollTo(0,"+clientHeight*fileIndex++ +")");
	                    scrollHeight = scrollHeight - clientHeight;
	                }
	            }else{
	                  takeScreenshot(filePath);
	            }
	    	}
		} catch (Exception e) {
			logger.error("Exception occured while taking the screenshot! ", e);
		}
	}
	
	public String getCurrentURL()
	{
		String sUrl = "";
		try {
			sUrl = driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("Exception occured while getting the current URL! ", e);
			throw e;
		}
		return sUrl;
	}
	
	public void waitTillElementVisible(By by)
	{
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Exception occured while waiting for the element to be visible! ", e);
			throw e;
		}
	}
	

	public void verifyTextPresent(By by, String expectedText, String msg)
	{
		String actualtext = findElement(by).getText().trim();
		Assert.assertEquals(msg, expectedText, actualtext);
	}
	
	public void verifyText(String expectedText, String actualtext, String msg)
	{
		Assert.assertEquals(msg, expectedText, actualtext);
	}
	
	public void verifyElementPresent(By by, String msg)
	{
		boolean isPresent = findElement(by).isDisplayed();
		Assert.assertTrue(isPresent);
	}
	
	public boolean isElementPresent(By by)
	{
		try {
			findElement(by).isDisplayed();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public void clickIfElementPresent(By by, String elementName)
	{
		try {
			if(findElement(by).isDisplayed())
			{
				findElement(by).click();
				logger.info("Successfully clicked the element locator:   "+by +"-----> Element Name: "+elementName);
			}
		} catch (Exception e) {
			logger.error("Unable to click the element locator:   "+by +"-----> Element Name: "+elementName, e);
		}
	}
	
	public void refreshPage()
	{
		try {
			driver.navigate().refresh();
			waitForPageLoad();
			logger.info("Successfully refreshed the page");
		} catch (Exception e) {
			logger.error("Unable to refresh the page", e);
		}
	}
	
	public void selectByVisibleText(By by, String value, String dropDownName)
	{
		try {
			Select dropdown = new Select(findElement(by));
			dropdown.selectByVisibleText(value);
			logger.info("Successfully selected the element: "+value+" in dropdown: "+dropDownName);
		} catch (Exception e) {
			logger.error("Unable to Select the Element with value: "+value, e);
			throw e;
		}
	}
	
	public void verifyPartialTextPresent(By by, String expectedText, String msg)
	{
		String actualtext = findElement(by).getText();
		Assert.assertTrue( actualtext.contains(expectedText));
	}
	
	public WebElement findElement(By by) {
	    WebElement elem = driver.findElement(by);
	    // draw a border around the found element
	    if (driver instanceof JavascriptExecutor) {
	        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid green'", elem);
	    }
	    return elem;
	}
	
	public void switchToNewWindow()
	{
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	}
	
	public void switchToFrame(By by)
	{
		driver.switchTo().frame(driver.findElement(by));
	}
}
