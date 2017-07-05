package com.safelite.accelerators;

import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.beust.jcommander.internal.Maps;
import com.safelite.support.MyListener;
import com.safelite.utilities.Excel_Utility;
import com.safelite.utilities.GenerateHtmlReport;
import com.safelite.utilities.Property;

import jxl.Sheet;

public class Base {
	public static Property configProps=new Property();
	public static long startTime;
	public static long endTime;
	private static String sutieName;
	public static DocumentBuilderFactory docFactory;
	public static DocumentBuilder docBuilder;
	public static Document doc;
	public static org.w3c.dom.Element rootElement;
	public static org.w3c.dom.Element testElement;
	public static  long suiteStartTime = System.currentTimeMillis();

	private static	boolean isXMLReportCreated = false;
	public static String resultsFolderPath ;
	public static String mainBrowserName;

	public static long iTestStartTime = 0;
	public static long iTestEndTime = 0;
	public static long iTestExecutionTime = 0;
	public static long iStepEndTime = 0;
	public static long iStepStartTime = 0;
	public static long iStartTime = 0;
	public static long iEndTime = 0;
	public long iExecutionTime = 0;
	public static long iSuiteStartTime = 0;
	public static long iSuiteEndTime = 0;
	public static double iSuiteExecutionTime = 0;
	public static Map<String, String> testCaseNameMap = Maps.newHashMap();
	public String FilePath=null;
	public Sheet sheet;
	public int testCaseThreadCount =0;
	public static Sheet objInputSheet =null;
	WebDriver webDriver = null;

	public static int testCaseCount = 0;
	private  List<WebDriver> webDriverPool = Collections.synchronizedList(new ArrayList<WebDriver>());
	private List<String> testCasePool =  Collections.synchronizedList(new ArrayList<String>());
	public ThreadLocal<WebDriver> driverThread;
	private ThreadLocal<String> testCaseThred;
	public static ArrayList<Long> list = new ArrayList<Long>();

	public static String browserType;

	@BeforeSuite(alwaysRun = true) 	
	@Parameters("browserName")
	public  void setupSuite(ITestContext ctx,@Optional("") String browserName) throws Throwable {
		String strBrowserType ="";

		if(browserName.equalsIgnoreCase("")){
			strBrowserType = getPropertyData("browserType");
		}else{
			strBrowserType = browserName;
		}
		mainBrowserName = strBrowserType;
		sutieName = ctx.getSuite().getXmlSuite().getName();
		if(!isXMLReportCreated){

			createXMLReport();
			createResultsFolder();
		}
	}

	public long calculateTestCaseExecutionTime(){

		iEndTime = System.currentTimeMillis();
		iExecutionTime = (iEndTime-iStartTime);
		list.add(iExecutionTime);
		System.out.println(iExecutionTime/1000.000);

		return iExecutionTime;
	}

	public String getPropertyData(String key){

		return new Property().getProperty(key);
	}

	/**
	 * Create XML Reports
	 * @throws ParserConfigurationException
	 */
	public void createXMLReport() throws ParserConfigurationException{
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();

		doc = docBuilder.newDocument();
		rootElement = doc.createElement("TestSuite");
		rootElement.setAttribute("name", "testSuite");

		doc.appendChild(rootElement);
		isXMLReportCreated = true;

	}

	private void  createResultsFolder(){
		File f = new File(System.getProperty("user.dir")+"/Reports/");
		if(!f.exists()){
			f.mkdir();
		}
		resultsFolderPath = System.getProperty("user.dir")+"/Reports/"+sutieName+"_"+suiteStartTime;
		new File(resultsFolderPath).mkdir();
	}

	@BeforeTest(alwaysRun=true)
	@Parameters({ "browserName", "browserVersion", "url"})
	public void createTestCaseReport(final ITestContext ctx, final String browserName, @Optional("none") String browserVersion, final String url) throws Exception{
		iStartTime = System.currentTimeMillis();
		browserType=browserName;
		FilePath = System.getProperty("user.dir")+"\\Macros\\"+new Property().getProperty("MacroFile");				
		sheet =new Excel_Utility().getSheetObject(FilePath,"Automation_Input_Parameters");	
		final String sExecutionType = new Excel_Utility().getSheetCellData(sheet, "ExecutionType",1);

		org.w3c.dom.Element testElement = doc.createElement("TestCase");

		if( ctx.getSuite().getXmlSuite().getTests().get(testCaseCount).getName()!=null){
			testElement.setAttribute("name", ctx.getSuite().getXmlSuite().getTests().get(testCaseCount).getName());
			rootElement.appendChild(testElement);
			testCaseCount++;
		}

		driverThread = new ThreadLocal<WebDriver>() {

			@Override
			protected WebDriver initialValue() {
				webDriver = null;
				try {
					DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
					if(browserName.equalsIgnoreCase("firefox")){
						if(sExecutionType.equalsIgnoreCase("Grid")){
							desiredCapabilities.firefox();
							webDriver = new RemoteWebDriver(new URL(url), new DesiredCapabilities().firefox());
							webDriver.manage().window().maximize();


						}else{
//							System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");	
							FirefoxProfile profile = new FirefoxProfile();
							
							profile.setPreference("browser.download.folderList", 2);
							profile.setPreference("browser.download.manager.showWhenStarting", false);
							profile.setPreference("browser.download.dir", System.getProperty("user.dir")+"\\TestData");
							profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;application/vnd.ms-excel");

												
							
							
							
							webDriver = new FirefoxDriver(profile);
							webDriver.manage().window().maximize();
						}
					}else if(browserName.equalsIgnoreCase("chrome")){
						if(sExecutionType.equalsIgnoreCase("Grid")){
							desiredCapabilities.chrome();
							webDriver = new RemoteWebDriver(new URL(url), new DesiredCapabilities().chrome());
							webDriver.manage().window().maximize();

						}else{
							System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");	

							ChromeOptions options = new ChromeOptions();
				

							options.addArguments("test-type");
							options.setExperimentalOption("excludeSwitches",Arrays.asList("test-type"));
							DesiredCapabilities capabilities = DesiredCapabilities.chrome();
							capabilities.setCapability(ChromeOptions.CAPABILITY, options);

							Map<String, Object> prefs = new HashMap<String, Object>();
							prefs.put("download.default_directory", System.getProperty("user.dir")+"\\TestData");

							DesiredCapabilities caps = DesiredCapabilities.chrome();


							options.setExperimentalOption("prefs", prefs);
							caps.setCapability(ChromeOptions.CAPABILITY, options);

							//WebDriver driver = new ChromeDriver(caps);

							webDriver = new ChromeDriver(caps);
							webDriver.manage().window().maximize();
						}
					}if(browserName.equalsIgnoreCase("ie")){
						if(sExecutionType.equalsIgnoreCase("Grid")){
							desiredCapabilities.internetExplorer();
							webDriver = new RemoteWebDriver(new URL(url), new DesiredCapabilities().internetExplorer());
							webDriver.manage().window().maximize();


						}else{
							/*URL ieDriverPath = this.getClass().getResource("Drivers/IEDriverServer.exe");
							String dirverFile = ieDriverPath.getFile();
							 */
							System.setProperty("webdriver.ie.driver", "Drivers/IEDriverServer.exe");
							webDriver= new InternetExplorerDriver();
							webDriver.manage().window().maximize();
						}
					}

					webDriverPool.add(webDriver);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}		

				return webDriver;
			}

		};

		testCaseThred = new ThreadLocal<String>() {

			@Override
			protected String initialValue() {


				String testCaseName = ctx.getSuite().getXmlSuite().getTests().get(testCaseThreadCount).getName();

				testCasePool.add(testCaseName);
				testCaseThreadCount++;

				return testCaseName;


			}

		};


	}


	public EventFiringWebDriver  getDriver() {
		EventFiringWebDriver driver = new EventFiringWebDriver(driverThread.get());

		MyListener myListener = new MyListener();
		driver.register(myListener);

		return driver;

	}

	public String getTestCaseName(){
		return testCaseThred.get();
	}




	//	@AfterSuite(alwaysRun=true)
	public void generateXMLReport() throws Exception{


		//rootElement.setAttribute("executionTime", String.valueOf(Math.abs(suiteStartTime-System.currentTimeMillis())/1000));


		org.w3c.dom.Element testElement = doc.createElement("SummaryReport");


		testElement.setAttribute("name", "SummaryReport");


		rootElement.appendChild(testElement);

		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = "//SummaryReport[@name='SummaryReport']";	
		NodeList currentElement =(NodeList)xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);


		Map<String, Object> dataMap =	new GenerateHtmlReport().readXML(doc,sutieName,mainBrowserName,resultsFolderPath);
		System.out.println(resultsFolderPath);

		org.w3c.dom.Element stepElement = null;
		for(int i=0;i<currentElement.getLength();i++){			
			Node e = currentElement.item(0);
			stepElement = doc.createElement("Summary");
			stepElement.setAttribute("PassCount", String.valueOf(dataMap.get("totalNoOfTestCasesPass")));
			stepElement.setAttribute("FailCount", String.valueOf(dataMap.get("totalNoTestCasesFail")));
			stepElement.setAttribute("TotalTestCases",String.valueOf(dataMap.get("totalNoOfTestCases")));
			e.appendChild(stepElement);

		}

		String filepath=System.getProperty("user.dir")+"\\XMLReports";
		stepElement.setAttribute("hostName",(String) dataMap.get("hostName"));
		stepElement.setAttribute("osName", (String) dataMap.get("osName"));
		stepElement.setAttribute("osVersion", (String) dataMap.get("osVersion"));
		stepElement.setAttribute("suiteTimeInSec", "suiteTime");
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
//		new GenerateHtmlReport().generateDetailedSummmaryReport(dataMap, resultsFolderPath+"/");
//		new GenerateHtmlReport().generateSummaryReport(dataMap,resultsFolderPath+"/"+sutieName+"_"+suiteStartTime+"_Summary"+".html");
		StreamResult result = new StreamResult(new File(resultsFolderPath+"/"+sutieName+"_"+suiteStartTime+".xml"));
		transformer.transform(source, result);
		FileUtils.copyFile(new File(resultsFolderPath+"/"+sutieName+"_"+suiteStartTime+".xml"), new File(filepath+"/"+sutieName+"_"+suiteStartTime+".xml"));
		System.out.println(result);
	}


	/*@AfterTest(alwaysRun=true)
	public void closeBrowser1(){
		calculateTestCaseExecutionTime();
		webDriver.close();
		//driver.close();
		//driver.quit();
	}*/

	@AfterSuite(alwaysRun=true)
	public void close(){

	}
	//@AfterSuite(alwaysRun=true)
	public void tearDown() throws Throwable
	{
		//open the result file
		String dir =  getPropertyData("openResult"); // path to your new file
		File fl = new File(dir);
		File[] folders = fl.listFiles(new FileFilter() {                    
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choise = null;
		for (File folder : folders) {
			if (folder.lastModified() > lastMod) {
				choise = folder;
				lastMod = folder.lastModified();
			}
		}
		dir = ""+choise;
		fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {               
			public boolean accept(File file) {
				return file.isFile();
			}
		});



		lastMod = Long.MIN_VALUE;
		choise = null;


		for (File file : files) {
			System.out.println(file);
			if (file.getName().contains("Summary")) {
				choise = file;
				/* lastMod = file.toPath().toString().contains("summary");*/
			}
		}
		dir = ""+choise;
		File htmlFile = new File(dir);
		Desktop.getDesktop().browse(htmlFile.toURI());

		Thread.currentThread().interrupt();
		//webDriver.quit();
	}
}
