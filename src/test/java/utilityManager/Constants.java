package utilityManager;

import org.junit.rules.Timeout;

import java.time.Duration;
import java.util.Properties;

public class Constants {

	//---- Run properties -----------------//
	static Properties runProp = FileManager.readPropertyFile("src/main/resources/configfiles/run.properties");
	public static String DEFECTS_FILE_PATH = runProp.getProperty("defectsfilepath");
	public static String DRIVERS_PATH = runProp.getProperty("drivers");
	public static String SCREENSHOTS_PATH = runProp.getProperty("screenshotspath");


	//---- Env properties -----------------//
	public static Properties envProp = FileManager.readPropertyFile("src/main/resources/configfiles/"+getEnvironment()+"-config.properties");
	public static String BROWSER_NAME = envProp.getProperty("browser");
	public static Long WEBDRIVER_WAIT_GLOBAL = Long.parseLong(envProp.getProperty("webdriver.globalWait"));
	public static int WEBDRIVER_WAIT = Integer.parseInt(envProp.getProperty("webdriver.wait"));
	public static String WEBURL_IOMS = envProp.getProperty("ioms.url");
    public static String usename1 = envProp.getProperty("username1");
	public static String password1 = envProp.getProperty("password1");


	public static String getEnvironment()
	{
		String env = System.getProperty("env");
		if(env == null)
		{
			env = "uat";
		}
		return env;
	}
	

	

	

	
}
