package com.safelite.accelerators;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.safelite.commonutilities.CoreUtilities;
import com.safelite.report.CReporter;
import com.safelite.report.ConfigFileReadWrite;
import com.safelite.report.ReporterConstants;
import com.safelite.support.ExcelReader;
import com.safelite.support.MyListener;
import com.safelite.utilities.DataBean;
import com.safelite.utilities.WriteIntoExcel;

import io.appium.java_client.AppiumDriver;

/**
 * @author in01518
 */

/**
 * @author in01518
 */
public class TestEngineWeb {
	public final Logger LOG = Logger.getLogger(TestEngineWeb.class);
	protected AppiumDriver appiumDriver = null;
	protected WebDriver WebDriver = null;
	public EventFiringWebDriver driver = null;
	protected CReporter reporter = null;
	public ITestContext ctx = null;
	public static String gTestCaseDesc=null;

	/* cloud platform */
	public String browser = null;
	public String version = null;
	public String platform = null;
	public String seleniumgridurl=null;
	public String environment = null;
	public String localBaseUrl = null;
	public String cloudBaseUrl = null;
	public String userName = null;
	public String deviceName = null;
	public String platformVersion = null;
	public String accessKey = null;
	public String cloudImplicitWait = null;
	public String cloudPageLoadTimeOut = null;
	public String updateJira = null;
	public String buildNumber = "";
	public String jobName = "";
	public String executedFrom = null;
	public String executionType = null;
	public String suiteExecution = null;
	public String suiteStartTime = null;
	/*String APP_BASE_URL = ConfigFileReadWrite.read(
			ReporterConstants.configReporterFile, "baseUrl");*/
	public String APP_BASE_URL = null;
	public String SUMMARY_REPORTER_BASEURL = null;
	public static String LOCATION_CLIENT_LOGO = null;


	public static long startTime;
	public static String fileName = System.getProperty("user.dir")
			+ "/TestData/TestData.xls";
	public static ExcelReader xlsrdr = new ExcelReader(fileName);
	public static DataBean dataBean = new DataBean();
	public static WriteIntoExcel excelEntry = new WriteIntoExcel();
	public HashMap<String, String> mapObj = new HashMap<String, String>();

	// Below parameters are for update the test data into Excel at run time
	public static String testCase;
	public static String globalsheetName = null;
	public static int gTestCaseStartRowNum = 0;

	public static String gTestData = System.getProperty("user.dir")
			+ File.separator + "TestData" + File.separator + "TestData.xlsx";
	//declared variable for testdata multiple iterations of a testcase
	public static ArrayList<String> listofTestCaseDescription = new ArrayList<String>();
	public static int i=0;

	/**/
	// private DesiredCapabilities capabilitiesForAppium = new
	// DesiredCapabilities();
	//@Parameters({"executionType", "suiteExecuted"})
	@BeforeSuite(alwaysRun = true)
	//public void beforeSuite(ITestContext ctx, String type, String suite)
	public void beforeSuite() throws Throwable {
		startTime = System.currentTimeMillis();
		//ctx.setAttribute("browser", System.getenv("Browsers"));
		System.out.println(System.getenv("Browsers"));
		LOG.info("--------------------------------------------------------------------------");
//		LOG.info("------------------Suite :: " + suite
//				+ "------------------------------");
		LOG.info("Execution Start Time :: " + startTime);
		LOG.info("Test Data Path :: " + fileName);
		LOG.info("Executing on :: " + System.getenv("Browsers"));
		LOG.info("---------------------------End Suite Details-----------------------------------");
		//executionType = type;
		//suiteExecution = suite;
		PropertyConfigurator.configure(System.getProperty("user.dir")
				+ "/Log.properties");
		// ReportStampSupport.calculateSuiteStartTime();
		// ObjectRepository.storeIdentification();
		// ObjectRepository.storeValue();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy hh mm ss SSS");
		String formattedDate = sdf.format(date);
		suiteStartTime = formattedDate.replace(":", "_").replace(" ", "_");
		System.out.println("Suite time ==============>" + suiteStartTime);
	}

	// @Parameters({"browser"})
	@BeforeClass(alwaysRun = true)
	// @BeforeTest
	@Parameters({"automationName", "browser", "browserVersion", "environment",
			"platformName","seleniumgridurl","deviceName","platformVersion"})
	public void beforeClass(String automationName, String browser,
						   String browserVersion, String environment, String platformName,String seleniumgridurl,@Optional String deviceName,@Optional String platformVersion)
			throws IOException, InterruptedException {

		if(this.getClass()
				.getName().contains("showroom")){
			//Setting CES base url and logo for execution and summry report logo attachment
			APP_BASE_URL = ConfigFileReadWrite.read(
					ReporterConstants.configReporterFile, "cesBaseUrl");
			LOCATION_CLIENT_LOGO = ReporterConstants.CES_LOCATION_CLIENT_LOGO;
		}else{
			//Setting TWX baseUrl and logo for execution and summry report logo attachment
			APP_BASE_URL = ConfigFileReadWrite.read(
					ReporterConstants.configReporterFile, "baseUrl");
			LOCATION_CLIENT_LOGO = ReporterConstants.LOCATION_CLIENT_LOGO;
		}
		SUMMARY_REPORTER_BASEURL=APP_BASE_URL;

		/*
		 * PropertyConfigurator.configure(System.getProperty("user.dir")+
		 * "\\Log.properties");
		 * System.out.println(System.getProperty("user.dir")+"\\Log.properties")
		 * ;
		 *
		 *
		 *
		 */
		/* get configuration */
		// String webbrowser = System.getenv("Browsers");
		// this.browser = (String)ctx.getAttribute("browser");
		LOG.info("---------------------");
		LOG.info("-----Before Class----");
		LOG.info("---------------------");
		LOG.info("Execution Start Time :: " + startTime);
		LOG.info("browser name :: " + browser + " Browser version :: "
				+ browserVersion + " platform ::" + platformName);

		this.browser = browser;
		this.version = browserVersion;
		this.platform = platformName;
		this.environment = environment;
		this.seleniumgridurl=seleniumgridurl;
		this.localBaseUrl = ReporterConstants.APP_BASE_URL;
		this.userName = ReporterConstants.SAUCELAB_USERNAME;
		this.accessKey = ReporterConstants.SAUCELAB_ACCESSKEY;
		this.executedFrom = System.getenv("COMPUTERNAME");
		this.cloudImplicitWait = ReporterConstants.CLOUD_IMPLICIT_WAIT;
		this.cloudPageLoadTimeOut = ReporterConstants.CLOUD_PAGELOAD_TIMEOUT;
		this.updateJira = "";
		this.deviceName = deviceName;
		this.platformVersion = platformVersion;

		/**/

		System.out.println(environment);
		if (environment.equalsIgnoreCase("local")) {
			LOG.info("Selenium Grid URL"+seleniumgridurl);
			this.setWebDriverForLocal(browser,seleniumgridurl,platformName);
		}
		/*Set environment as Grid*/
		if(environment.equalsIgnoreCase("grid"))
		{
			this.setWebDriverForRemote(browser);
		}
		if (environment.equalsIgnoreCase("cloudSauceLabs")) {
			this.setRemoteWebDriverForCloudSauceLabs();
		}
		if (environment.equalsIgnoreCase("cloudSauceLabsJenkins")) {
			this.updateConfigurationForCloudSauceLabsJenkins();
			/* set remoteWebDriver for cloudsaucelabs */
			this.setRemoteWebDriverForCloudSauceLabs();
		}
		if (environment.equalsIgnoreCase("cloudBrowserStackJenkins")) {
			/* TBD: Not Implemented For Running Using Jenkins */
			this.updateConfigurationForCloudBrowserStackJenkins();
		}
		reporter = CReporter.getCReporter(browser, platformName, environment,
				true);
		driver = new EventFiringWebDriver(this.WebDriver);
		MyListener myListener = new MyListener();
		driver.register(myListener);
		driver.get(SUMMARY_REPORTER_BASEURL);
		// Driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		reporter.calculateSuiteStartTime();
		LOG.info("---------------------");
		LOG.info("---End Before Class--");
		LOG.info("---------------------");
	}

	@Parameters({"browser"})
	@AfterClass(alwaysRun = true)
	// public void close(String browser) throws Exception{
	// @AfterTest
	public void afterTest(String browser) throws Throwable {
		if (browser.equalsIgnoreCase("firefox")) {

			driver.quit();
			LOG.info("Driver quit ::" + browser);
			/*
			 * Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
			 * Runtime.getRuntime().exec(
			 * "taskkill /F /IM plugin-container.exe");
			 * Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe");
			 */
		} else if (browser.equalsIgnoreCase("chrome")) {
			System.out.println("After class executing...");

			//this.WebDriver.navigate().back();
			driver.quit();
			LOG.info("Driver quit ::" + browser);
			/*
			 * Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
			 * Runtime.getRuntime().exec(
			 * "taskkill /F /IM plugin-container.exe");
			 * Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe");
			 */
		} else if (browser.equalsIgnoreCase("ie")) {
			driver.quit();
			LOG.info("Driver quit ::" + browser);
			/*
			 * Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
			 * Runtime.getRuntime().exec(
			 * "taskkill /F /IM plugin-container.exe");
			 * Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe");
			 */
		} else if (browser.equalsIgnoreCase("edge")) {
			driver.quit();
			LOG.info("Driver quit ::" + browser);
			/*
			 * Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
			 * Runtime.getRuntime().exec(
			 * "taskkill /F /IM plugin-container.exe");
			 * Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe");
			 */
		} else {
			try {
				driver.quit();
				LOG.info("Driver quit ::" + browser);
			} catch (Exception e) {
			}
		}
		// Driver.close();
		reporter.calculateSuiteExecutionTime();
		//reporter.createHtmlSummaryReport(ReporterConstants.APP_BASE_URL, true);
		reporter.createHtmlSummaryReport(SUMMARY_REPORTER_BASEURL, true);
		reporter.closeSummaryReport();
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		int k=0;
		// get browser info
		// reporter = CReporter.getCReporter(deviceName, platformName,
		// platformVersion, true);
		//list object is the value of testcasedescription

		  HashMap<String,Integer> tcDescriptionMapObject  = new HashMap<String,Integer>();
		  for(int j=0;j<listofTestCaseDescription.size();j++){
			  if(tcDescriptionMapObject.get(listofTestCaseDescription.get(j).replace(" ",""))==null)
			  tcDescriptionMapObject.put(listofTestCaseDescription.get(j).replace(" ",""),j);
		  }
 		 if(listofTestCaseDescription.size()==0){
			 k=0;
		 }else{
			  k= tcDescriptionMapObject.get(listofTestCaseDescription.get(i).replace(" ",""));
			  i=i+1;
		 }
		 		//System.out.println("listofTestCaseDescriptionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"+listofTestCaseDescription.get(i));
				reporter.initTestCase(
						this.getClass()
								.getName()
								.substring(0,
										this.getClass().getName().lastIndexOf(".")),
						method.getName()+"-"+k,null, true);
				//i=i+1;
	}

	@AfterMethod
	public void afterMethod() throws Throwable {
		System.out.println("After method executing...");
		/*if(driver.findElement(By.xpath("//i[@class='fa fa-caret-down']")).isDisplayed()){
			driver.findElement(By.xpath("//i[@class='fa fa-caret-down']")).click();
			driver.findElement(By.xpath("//a[@title='Log Off']")).click();
		}*/

		reporter.calculateTestCaseExecutionTime();
		reporter.closeDetailedReport();
		reporter.updateTestCaseStatus();
		if(i==0){
		this.reporter.initTestCaseDescriptionUpdated(gTestCaseDesc/*listofTestCaseDescription.get(i)*/);
		}else{
			this.reporter.initTestCaseDescriptionUpdated(/*gTestCaseDesc*/listofTestCaseDescription.get(i-1));
		}
		CoreUtilities coreUtilities=new CoreUtilities();
		//coreLoginLogout.verifylogout();
		coreUtilities.backuntilLogoutbutton(this.WebDriver);
		//driver.get(APP_BASE_URL);
		//coreLoginLogout.verifylogout();
        System.out.println("The value of the path is " + gTestData);
        System.out.println("The value of test case description"+gTestCaseDesc);

		//excelEntry.writeXLSXFile();
	}

	public void setWebDriverForLocal(String browser,String seleniumgridurl,String platform) throws IOException,
			InterruptedException {
		 DesiredCapabilities capab=null;

		switch (browser) {
			case "firefox":
				try{
					final FirefoxProfile firefoxProfile = new FirefoxProfile();
					firefoxProfile.setPreference("browser.download.folderList", 2);
					firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile","application/pdf,application/download,application/octet-stream");
					firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/octet-stream");
					firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/octet-stream," + "application/pdf,application/download");
					firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
					firefoxProfile.setPreference("pdfjs.disabled", true);
					capab = DesiredCapabilities.firefox();
					capab.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
					String FirefoxdriverPath = System.getProperty("user.dir")+"\\Drivers\\";
					System.setProperty("webdriver.gecko.driver", FirefoxdriverPath
							+ "geckodriver.exe");
					capab.setCapability("marionette", true);
					capab.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					capab.setJavascriptEnabled(true);
					if(seleniumgridurl.equalsIgnoreCase("local")) {
						this.WebDriver = new FirefoxDriver(capab);
					}
				}
				catch(Exception err)
				{
					System.out.println("Error: " + err.getMessage());
					throw err;
				}
				break;


			case "ie":
				capab = DesiredCapabilities.internetExplorer();
				File file = new File("Drivers\\IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver",file.getAbsolutePath());
				//To disable popup blocker.
				capab.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
				//to enable protected mode settings
				capab.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				capab.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				//to get window focus
				capab.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,true);
				//  to set zoom level is set to 100% so that the native mouse events can be set to the correct coordinates.
				capab.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);
				capab.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
				capab.setCapability(InternetExplorerDriver.NATIVE_EVENTS,false);
				Process p = Runtime
						.getRuntime()
						.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
				p.waitFor();
				if(seleniumgridurl.equalsIgnoreCase("local")){
				this.WebDriver = new InternetExplorerDriver(capab);
				}
				break;
			case "chrome":
				if(platform.equalsIgnoreCase("Android")) {
					if (environment.equalsIgnoreCase("local")) {
						if (browser.equalsIgnoreCase("Chrome")
								&& platform.equalsIgnoreCase("Android")) {
							 capab = new DesiredCapabilities();
							capab.setCapability("browserName", browser);
							capab.setCapability("deviceName", deviceName);
							capab.setCapability("platformName", platform);
							capab.setCapability("maxSession", "2");
							capab.setCapability("maxInstances", "2");
							//this.appiumDriver = new AndroidDriver(new URL(seleniumgridurl), capab);
						}
					}
				}
				else {
					HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
					chromePrefs.put("profile.default_content_settings.popups", 0);
					chromePrefs.put("download.default_directory", System.getProperty("user.dir") + "\\Downloads");

					capab = DesiredCapabilities.chrome();
					System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
					ChromeOptions options = new ChromeOptions();

					options.setExperimentalOption("prefs", chromePrefs);
					capab.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

					options.addArguments("test-type");
					options.addArguments("chrome.switches", "--disable-extensions");
					capab.setCapability(ChromeOptions.CAPABILITY, options);
					if (seleniumgridurl.equalsIgnoreCase("local")) {
						this.WebDriver = new ChromeDriver(capab);
					}
				}
				LOG.info("Driver launch ::" + browser);
				break;
			case "edge":
				System.out.println("In the case Edge");
				System.setProperty("webdriver.edge.driver",
						"Drivers\\MicrosoftWebDriver.exe");
				capab = DesiredCapabilities.edge();
				if(seleniumgridurl.equalsIgnoreCase("local")){
				this.WebDriver = new EdgeDriver(capab);
				}
				LOG.info("Driver launch ::" + browser);
				break;

			case "Safari":
				for (int i = 1; i <= 10; i++) {
					try {
						if(seleniumgridurl.equalsIgnoreCase("local")){
						this.WebDriver = new SafariDriver();
						}
						break;
					} catch (Exception e1) {
						// Runtime.getRuntime().exec("taskkill /F /IM Safari.exe");
						// Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
						// Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe");
						continue;
					}
				}

		}
		//ReporterConstants.SELENIUM_GRID_HUB_URL
		System.out.println("");
		if(!seleniumgridurl.equalsIgnoreCase("local")){
		this.WebDriver=new RemoteWebDriver(new URL(seleniumgridurl), capab);
		}
	}

	/*Set Browser environment for Grid*/
	public void setWebDriverForRemote(String browser) throws IOException, InterruptedException {
		if (browser.equalsIgnoreCase("firefox")){
			Thread.sleep(10000);
			try{
				DesiredCapabilities capab = DesiredCapabilities.firefox();
				File file = new File("Drivers\\geckodriver.exe");
				System.setProperty("webdriver.gecko.driver",file.getAbsolutePath());
				capab.setCapability("marionette", false);
				capab.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capab.setCapability("acceptInsecureCerts", "true");
				capab.setJavascriptEnabled(true);
				this.WebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capab);
			}
			catch(Exception e1)
			{
				System.out.println("Error: " + e1.getMessage());
			}
			Thread.sleep(5000);
		}
		else if (browser.equalsIgnoreCase("ie")){
			Thread.sleep(10000);
			DesiredCapabilities capab = DesiredCapabilities.internetExplorer();
			capab.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			DesiredCapabilities.internetExplorer().setCapability("ignoreProtectedModeSettings", true);
			File file = new File("Drivers\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver",file.getAbsolutePath());
			capab.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capab.setJavascriptEnabled(true);
			capab.setCapability("enablePersistentHover", false);
			this.WebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capab);
			Thread.sleep(8000);
		}
		else if (browser.equalsIgnoreCase("chrome")) {
			Thread.sleep(2000);
			System.out.println("Chrome Browser Launch");
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			options.addArguments("chrome.switches", "--disable-extensions");
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			this.WebDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
			Thread.sleep(5000);
		}
	}

	private void setRemoteWebDriverForCloudSauceLabs() throws IOException,
			InterruptedException {
		if(this.platform.equalsIgnoreCase("Android")){
			if(this.browser.equalsIgnoreCase("chrome") || this.browser.equalsIgnoreCase("browser") ){
				DesiredCapabilities desiredCapabilities = DesiredCapabilities.android();
				desiredCapabilities.setCapability("appiumVersion", "1.6.4");
				desiredCapabilities.setCapability("deviceName",this.deviceName);
				desiredCapabilities.setCapability("browserName", this.browser);
				desiredCapabilities.setCapability("username", this.userName);
				desiredCapabilities.setCapability("accessKey", this.accessKey);
				desiredCapabilities.setCapability("platformVersion",this.platformVersion);
				desiredCapabilities.setCapability("platformName",this.platform);
				desiredCapabilities.setCapability("name", this.executedFrom + " - "
						+ this.platform + " - " + this.browser);
				URL commandExecutorUri = new URL(
						"https://" + this.userName + ":" + this.accessKey +"@ondemand.saucelabs.com:443/wd/hub");
				this.WebDriver = new RemoteWebDriver(commandExecutorUri,desiredCapabilities);
			}

		}
		else if (this.browser.equalsIgnoreCase("Safari")) {
			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME,
					this.browser);
			desiredCapabilities.setCapability(CapabilityType.VERSION,
					this.version);
			desiredCapabilities.setCapability(CapabilityType.PLATFORM,
					this.platform);
			desiredCapabilities.setCapability("username", this.userName);
			desiredCapabilities.setCapability("accessKey", this.accessKey);
			desiredCapabilities.setCapability("accessKey", this.accessKey);
			desiredCapabilities.setCapability("name", this.executedFrom + " - "
					+ this.platform + " - " + this.browser);
			URL commandExecutorUri = new URL(
					"http://ondemand.saucelabs.com/wd/hub");
			for (int i = 1; i <= 10; i++) {
				try {
					this.WebDriver = new RemoteWebDriver(commandExecutorUri,
							desiredCapabilities);
					break;
				} catch (Exception e1) {
					// Runtime.getRuntime().exec("taskkill /F /IM Safari.exe");
					continue;
				}
			}
		} else {
			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME,
					this.browser);
			desiredCapabilities.setCapability(CapabilityType.VERSION,
					this.version);
			desiredCapabilities.setCapability(CapabilityType.PLATFORM,
					this.platform);
			desiredCapabilities.setCapability("username", this.userName);
			desiredCapabilities.setCapability("accessKey", this.accessKey);
			desiredCapabilities.setCapability("accessKey", this.accessKey);
			desiredCapabilities.setCapability("name", this.executedFrom + " - " /*
																				 * +
																				 * this
																				 * .
																				 * jobName
																				 * +
																				 * " - "
																				 * +
																				 * this
																				 * .
																				 * buildNumber
																				 */
					+ this.platform + " - " + this.browser);
			desiredCapabilities.setCapability("idleTimeout", 1000);
			desiredCapabilities.setCapability("commandTimeout", 600);
			URL commandExecutorUri = new URL(
					"http://ondemand.saucelabs.com/wd/hub");
			this.WebDriver = new RemoteWebDriver(commandExecutorUri,
					desiredCapabilities);
		}
	}

	private void updateConfigurationForCloudSauceLabsJenkins() {
		this.browser = System.getenv("Browsers");
		this.version = System.getenv("SELENIUM_VERSION");
		this.platform = System.getenv("SELENIUM_PLATFORM");
		this.userName = System.getenv("SAUCE_USER_NAME");
		this.accessKey = System.getenv("SAUCE_API_KEY");
		this.buildNumber = System.getenv("BUILD_NUMBER");
		this.jobName = System.getenv("JOB_NAME");
		/* For Debug Purpose */
		LOG.info("Debug: browser = " + this.browser);
		LOG.info("Debug: version = " + this.version);
		LOG.info("Debug: platform = " + this.platform);
		LOG.info("Debug: userName = " + this.userName);
		LOG.info("Debug: accessKey = " + this.accessKey);
		LOG.info("Debug: executedFrom = " + this.executedFrom);
		LOG.info("Debug: BUILD_NUMBER = " + this.buildNumber);
		LOG.info("Debug: jobName = " + this.jobName);
	}

	/* TBD: Not Implemented For Running Using Jenkins */
	private void updateConfigurationForCloudBrowserStackJenkins() {
	}

	public String getBrowser() {
		return this.browser;
	}
	
	@Parameters({"executionType", "suiteExecuted"})
	@AfterSuite(alwaysRun = true)
	public void afterSuite(ITestContext ctx, String type, String suite)
			throws Throwable {
		startTime = System.currentTimeMillis();
		ctx.setAttribute("browser", System.getenv("Browsers"));
		LOG.info("--------------------------------------------------------------------------");
		LOG.info("------------------Suite :: " + suite
				+ "------------------------------");
		LOG.info("AfterSuite Start Time :: " + startTime);
		LOG.info("---------------------------End After Suite Details-----------------------------------");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy hh mm ss SSS");
		String formattedDate = sdf.format(date);
		suiteStartTime = formattedDate.replace(":", "_").replace(" ", "_");
		System.out.println("After Suite end time ==============>" + suiteStartTime);
		LOG.info("Before killing "+browser+" browser");
		Runtime.getRuntime().exec("taskkill /F /IM chromedriver_32Bit.exe");
		Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer64bit.exe");
		Runtime.getRuntime().exec("taskkill /F /IM MicrosoftWebDriver.exe");
		LOG.info("After killing "+browser+" browser");
	}
}