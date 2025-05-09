package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import utilityManager.Constants;

import java.util.concurrent.TimeUnit;

public class DriverFactory {

public static WebDriver driver;

    public void initializeDriver() {

       // get browser to run from env properties and load driver
        String browser = Constants.BROWSER_NAME;
        if(driver == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    // Make sure to add your ChromeDriver executable path
                    System.setProperty("webdriver.chrome.driver", Constants.DRIVERS_PATH + "\\chromedriver.exe");
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("test-type");
                    options.addArguments("start-maximized");
                    options.addArguments("--js-flags=--expose-gc");
                    options.addArguments("--enable-precise-memory-info");
                    options.addArguments("--disable-popup-blocking");
                    options.addArguments("--disable-default-apps");
                    options.addArguments("test-type=browser");
                    options.addArguments("disable-infobars");
                    options.addArguments("--ignore-ssl-errors=yes");
                    options.addArguments("--ignore-certificate-errors");
                    driver = new ChromeDriver(options);

                    break;
               case "edge":
                    // Make sure to add your EdgeDriver executable path
                    System.setProperty("webdriver.edge.driver", Constants.DRIVERS_PATH + "\\chromedriver.exe");
                   driver = new EdgeDriver();
                    break;
                case "safari":
                    // SafariDriver is supported by default on macOS, no need to set the driver path
                    driver = new SafariDriver();
                    break;
                default:
                    System.out.println("Unsupported browser, defaulting to Chrome");
                    System.setProperty("webdriver.chrome.driver", Constants.DRIVERS_PATH + "\\chromedriver.exe");
                    driver = new ChromeDriver();
            }
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Constants.WEBDRIVER_WAIT_GLOBAL , TimeUnit.SECONDS);
    }


    public void close()
    {
        driver.close();
    }




}