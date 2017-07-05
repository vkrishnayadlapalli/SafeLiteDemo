package com.safelite.accelerators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.safelite.report.ReporterConstants;
import com.safelite.utilities.Xls_Reader;

import io.appium.java_client.android.AndroidDriver;

//import com.crunchtime.mobile.libs.TeamworxMobileLib;

public class ActionEngineMobile extends TestEngineMobile {
	private static final Logger LOG = Logger.getLogger(ActionEngineMobile.class);

	private final StringBuilder msgClickSuccess = new StringBuilder("Successfully Clicked On ");
	private final String msgClickFailure = "Unable To Click On ";
	private final String msgTypeSuccess = "Successfully Entered Value ";
	private final String msgTypeFailure = "Unable To Type On ";
	private final String msgIsElementFoundSuccess = "Successfully Found Element ";
	private final String msgIsElementFoundFailure = "Unable To Found Element ";
	public Xls_Reader TestData = new Xls_Reader(System.getProperty("user.dir") + "/TestData/TestData_Mobile.xlsx");
    
	
	/**
     * navigateToHomePage
     * @Description This method helps to navigate to the URL provided.
     * @author Cigniti Technologies 
     * @return void
     * @throws Throwable the throwable
     * @LastModifiedDate 05 june 2016
     */
	public boolean click(By locator, String locatorName) throws Throwable {
		boolean status = false;
		try {
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : click  ::  Locator : " + locatorName);
			WebDriverWait wait = new WebDriverWait(this.appiumDriver, 8);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			this.appiumDriver.findElement(locator).click();
			//		if(TeamworxMobileLib.resultFlag){
			this.reporter.SuccessReport("Click :" + locatorName, this.msgClickSuccess + locatorName);
			//		}
			status = true;
			LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ++++++++++++++");
		} catch (Exception e) {
			LOG.info("++++++++++++++++++++++++++++Catch Block Start+++++++++++++++++++++++++++++++++++++++++++");
			status = false;
			LOG.info(e.getMessage());
			this.reporter.failureReport("Click", this.msgClickFailure + locatorName, this.appiumDriver);
			
		}
		return status;
	}
	public boolean assertTextMatching(By by, String text, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
			String ActualText = getText(by, locatorName).trim();
			LOG.info("ActualText is : " + ActualText);

			if (ActualText.contains(text.trim())) {
				flag = true;
				LOG.info("String comparison with actual text :: " + "actual text is : " + ActualText + "And expected text is : " + text);
				LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				return true;
			} else {
				LOG.info("String comparison with actual text :: " + "actual text is : " + ActualText + "And expected text is : " + text);
				LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				reporter.failureReport("Verify : " + locatorName, text + " is not present in the element : ");
				return false;
			} else if (flag) {
				reporter.SuccessReport("Verify : " + locatorName, text + " is  present in the element : " + locatorName);
			}
		}
	}

	public boolean Waittime() throws Throwable {
		boolean status = true;
		String time = ReporterConstants.Timeout;
		long timevalue = Long.parseLong(time);
		System.out.println("Time out value is" + timevalue);
		// Driver.manage().timeouts().implicitlyWait(timevalue,
		// TimeUnit.SECONDS);
		return status;
	}

	public String getText(By locator, String locatorName) throws Throwable {
		String text = "";
		boolean flag = false;
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		try {
			Waittime();
			if (isElementPresent(locator, locatorName, true)) {
				text = appiumDriver.findElement(locator).getText();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag == false) {
				reporter.warningReport("GetText", "Unable to get Text from" + locatorName);
			} else if (flag == true) {
				reporter.SuccessReport("GetText", "" + locatorName + " is" + text);
			}
		}
		return text;
	}
	
	

	public boolean isElementPresent(By by, String locatorName, boolean expected) throws Throwable {
		boolean status = false;
		try {
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			this.appiumDriver.findElement(by);
			this.reporter.SuccessReport("isElementPresent", this.msgIsElementFoundSuccess + locatorName);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
			if (expected == status) {
				this.reporter.SuccessReport("isElementPresent :",locatorName );
			} else {
				this.reporter.failureReport("isElementPresent", this.msgIsElementFoundFailure + locatorName, this.appiumDriver);
			}
		}
		return status;
	}

	public boolean type(By locator, String testdata, String locatorName) throws Throwable {
		boolean status = false;
		try {
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			WebDriverWait wait = new WebDriverWait(this.appiumDriver, 8);
			wait.until(ExpectedConditions.visibilityOf(this.appiumDriver.findElement(locator)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			this.appiumDriver.findElement(locator).clear();
			this.appiumDriver.findElement(locator).sendKeys(testdata);
			this.reporter.SuccessReport("type " + locatorName, this.msgTypeSuccess + testdata);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
			this.reporter.failureReport("type ", this.msgTypeFailure + locatorName, this.appiumDriver);
		}

		return status;
	}

	public boolean waitForElementPresent(By by, String locator, int secs)
			throws Throwable {
		boolean status = false;

		try {
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			WebDriverWait wait = new WebDriverWait(this.appiumDriver,5);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			for (int i = 0; i < secs / 2; i++) {
				List<WebElement> elements = this.appiumDriver.findElements(by);
				if (elements.size() > 0) {
					status = true;
					return status;
				} else {
					this.appiumDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				}
			}
		} catch (Exception e) {

			return status;
		}

		return status;
	}

	public void compareList(ArrayList<String> listA, ArrayList<String> listB) throws Throwable {
		if (listA.size() != 0) {
			listA.removeAll(listB);
			if (listA.size() == 0) {
				reporter.SuccessReport("verifying the values", "Successfully Verified the values Position In Out and Hrs");
			} else {
				reporter.failureReport("verifying the values", "Successfully Not Verified the values Position In Out and Hrs");
			}
		}
	}
	public void SwipeBottom(WebElement remoteelem){
		if(remoteelem != null){
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
				double browser_top_offset = 0.0;
					browser_top_offset = 0;
				//				RemoteWebElement remoteelem = ((RemoteWebElement)element);        	
				//				JavascriptExecutor js = (JavascriptExecutor)driver;
				Point eloc =  remoteelem.getLocation();
				double yloc = eloc.getY();
				double xloc = eloc.getX() + remoteelem.getSize().width/2;
				Double swipe_xratio = xloc;
				double elemheight = remoteelem.getSize().getHeight();
				Double yStartRatio = (yloc + elemheight + browser_top_offset)/2; 
				Double yEndRatio = (eloc.getY()+browser_top_offset);
				if(swipe_xratio < 10.0){swipe_xratio = 10.0;}
				if(yEndRatio < 50.0){yEndRatio = 50.0;}
				HashMap<String, Double> swipeObject = new HashMap<String, Double>();
				swipeObject.put("startX", swipe_xratio);
				swipeObject.put("startY", yStartRatio);
				swipeObject.put("endX", swipe_xratio);
				swipeObject.put("endY", yEndRatio);
				swipeObject.put("duration", 1.0);
				//				js.executeScript("mobile: swipe", swipeObject); 
				if(appiumDriver!=null && appiumDriver.getClass().toString().toUpperCase().contains("IOS")){
					appiumDriver.swipe(swipe_xratio.intValue(), yStartRatio.intValue(), swipe_xratio.intValue(), yEndRatio.intValue(), 1);
				}
		}

	}
	

	public void SwipeTop(WebElement element) {
		try{
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			double browser_top_offset = 0.0;
				browser_top_offset = 0;
			RemoteWebElement remoteelem = ((RemoteWebElement)element);        	
			JavascriptExecutor js = (JavascriptExecutor)appiumDriver;
			Point eloc =  remoteelem.getLocation();
			double yloc = eloc.getY();
			double xloc = eloc.getX()/2 + remoteelem.getSize().width/2;
			double swipe_xratio = xloc;
			double elemheight = remoteelem.getSize().getHeight();
			double yStartRatio = (yloc + elemheight/2 + browser_top_offset)/2; 
			double yEndRatio = (eloc.getY()+browser_top_offset);
			if(swipe_xratio < 10.0){swipe_xratio = 10.0;}
			if(yEndRatio < 250.0){yEndRatio = 250.0;}
			HashMap<String, Double> swipeObject = new HashMap<String, Double>();
			swipeObject.put("startX", swipe_xratio);
			swipeObject.put("startY",yEndRatio);
			swipeObject.put("endX", swipe_xratio);
			swipeObject.put("endY",yStartRatio);
			swipeObject.put("duration", 1.0);
			js.executeScript("mobile: swipe", swipeObject); 
		}
		catch(Exception e1){

		}
	}
	
	// swipe left to right

	public void SwipeLeftToRight() throws Throwable {
		try {
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			reporter.SuccessReport("Inside SwipeLeftToRight Method","Successfully navigated to SwipeLeftToRight Method");
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			/*appiumDriver.context("NATIVE_APP");
			Dimension size = appiumDriver.manage().window().getSize();
			int startx = (int) (size.width * 0.8);
			int endx = (int) (size.width * 0.20);
			int starty = size.height / 2;
			
			System.out.println("Size Height"+ size.height);
			System.out.println("Size width"+size.width);
			System.out.println("Size end X "+endx);
			System.out.println("Size startx"+startx);
			System.out.println("Size startY"+starty);
			//appiumDriver.swipe(startx, starty, endx, starty, 1000);
			//driver.swipe(endx, starty, startx, starty, 3000);
			appiumDriver.swipe(endx, starty, startx, starty, 1000);*/
			JavascriptExecutor js = (JavascriptExecutor) this.appiumDriver;
			HashMap swipeObject = new HashMap();
			/*swipeObject.put("startX", 0.95);
			swipeObject.put("startY", 0.5);
			swipeObject.put("endX", 0.05);
			swipeObject.put("endY", 0.5);
			swipeObject.put("duration", 1.8);*/
			
			swipeObject.put("startX", 0.5);
			swipeObject.put("startY", 0.95);
			swipeObject.put("endX", 0.05);
			swipeObject.put("endY", 0.5);
			swipeObject.put("duration", 1.8);
			js.executeScript("mobile: swipe", swipeObject);
			reporter.SuccessReport("successfully performed swiping",
					"Successfully navigated to Menu screen on sliding");
			
			/*WebElement seekBar = driver.findElement(By.id("com.cj.scrolling:id/seekbar"));
			//Get start point of seekbar.
			int startX = seekBar.getLocation().getX();
			System.out.println(startX);
			//Get end point of seekbar.
			    int endX = seekBar.getSize().getWidth();
			    System.out.println(endX);
			    //Get vertical location of seekbar.
			    int yAxis = seekBar.getLocation().getY();
			    //Set slidebar move to position.
			    // this number is calculated based on (offset + 3/4width)
			    int moveToXDirectionAt = 1000 + startX;
			    System.out.println("Moving seek bar at " + moveToXDirectionAt+" In X direction.");
			    //Moving seekbar using TouchAction class.
			    TouchAction act=new TouchAction(driver);
			    act.longPress(startX,yAxis).moveTo(moveToXDirectionAt,yAxis).release().perform();*/
		} catch (Exception e1) {
			
			reporter.failureReport("unable to perform swiping",
					"unable to  navigate to Menu screen on sliding");
		}
	}

	// swipe Right to left

	/*public void SwipeRightToLeft() {
		try {
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			appiumDriver.context("NATIVE_APP"); 
			Dimension size = appiumDriver.manage().window().getSize(); 
			int startx = (int) (size.width * 0.8); 
			int endx = (int) (size.width * 0.20); 
			int starty = size.height / 2; 
			appiumDriver.swipe(startx, starty, endx, starty, 1000);
		} catch (Exception e1) {

		}
	}*/
	
	public void swipeHorizantal() throws Throwable{
		try {
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			appiumDriver.context("NATIVE_APP"); 
			//appiumDriver.findElementByAndroidUIAutomator("UiSelector().className(\"android.view.View\").instance(1)").click();
			AndroidDriver AndroidDriver=(AndroidDriver)this.appiumDriver;
			//AndroidDriver.findElementByAndroidUIAutomator(using)
			AndroidDriver.findElementByAndroidUIAutomator("UiSelector().className(\"android.view.View\").instance(1)").click();
			
			//WebElement contact =AndroidDriver.findElementByAndroidUIAutomator("UiSelector().xpath(\"android.view.View[@index='1']\")");
			Thread.sleep(3000);
			AndroidDriver.swipe(10, 30, 25, 1024, 1000);//Horizontal from right to left
			//AndroidDriver.swipe(570,600,25,1024,2000);//Horizontal from left to right
	  }
		 catch (Exception e1) {
				reporter.failureReport("unable to perform swiping",
						"unable to  navigate to Menu screen on sliding");
			}
		}
	
	

	/**
	 * @description: Gets the class name at runtime for logging purpose
	 * @method: getCallerClassName
	 * @Parameter:
	 * @return:
	 */

	public static String getCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		return stElements[3].getClassName();
	}

	/**
	 * @description: Gets the method name at runtime for logging purpose
	 * @method: getCallerMethodName
	 * @Parameter:
	 * @return:
	 */

	public static String getCallerMethodName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		return stElements[3].getMethodName();
	}
	
	/**
	 * @Behavior: Function to get current time in client required format
	 * @param : (String) format to get date and time (e.g: h:mm)
	 * @return : String
	 * 
	 */
	public String getCurrentDateTime(String dateTimeFormat) throws Throwable {
		DateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
		Date date = new Date();
		String currentTime = dateFormat.format(date);
		return currentTime;
	}
	
	/**
	 * @Behavior: Function to get future or past date in client required format
	 * @param1 : (String) format to get date and time (e.g: MM/dd/yyyy)
	 * @param2 : (int) number to get date E.g. 1:Tomorrow date, -1: Yesterday date
	 * @return : String
	 * 
	 */
	public String getFutureDateTime(String dateTimeFormat, int days) throws Throwable {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR,days);
		Date tomorrow = calendar.getTime();
		String futureDate = sdf.format(tomorrow);
		return futureDate;
	}

	public String getcurrentTime() throws Throwable{
		long timeInMillis = System.currentTimeMillis();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(timeInMillis);
		SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm");
		String currenttime=dateFormat.format(cal1.getTime());
		return currenttime;
	}
	
	
	public int	datedifferencemethod(String Stardate,String enddate,String dateformat){
		
		String dateStart = Stardate;
		String dateStop = enddate;
		
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat(dateformat);

		Date d1 = null;
		Date d2 = null;
		int diffDays = 0;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			//in milliseconds
			int diff = (int) (d2.getTime() - d1.getTime());

			/*long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;*/
			diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " day, ");
			/*System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diffDays;
		
	}
	
	/**
	 * Assert text string matching.
	 *
	 * @param acttext the acttext
	 * @param expText the exp text
	 * @return true, if successful
	 * @throws Throwable the throwable
	 */
	public boolean assertTextStringMatching(String acttext, String expText) throws Throwable {
		boolean flag = false;
		try {
			// added loggers
			LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			String ActualText = acttext.trim();
			LOG.info("act - " + ActualText);
			LOG.info("exp - " + expText);
			if (ActualText.equalsIgnoreCase(expText.trim())) {
				LOG.info("in if loop");
				flag = true;
				LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				return true;
			} else {
				LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {

				reporter.failureReport("Verify : " + expText, acttext + ": is actual text and Expected Text is : "+expText);

				return false;
			} else if (flag) {

				reporter.SuccessReport("Verify : " + expText, acttext + ": is actual text and Expected Text is : "+expText);
			}
		}
	}

	public boolean isElementPresent(By by, String locatorName) throws Throwable {
		boolean status = false;
		try {
			Waittime();
			appiumDriver.findElement(by);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
		}
		return status;
	}
	
	
	
	public void dynamicWaitByLocator(By by,int time){
		AndroidDriver AndroidDriver=(AndroidDriver)this.appiumDriver;
		   WebDriverWait wait = new WebDriverWait(AndroidDriver,time);
		   wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}
	/**
	  * Assert text string matching.
	  *
	  * @param acttext the acttext
	  * @param expText the exp text
	  * @return true, if successful
	  * @throws Throwable the throwable
	  */
	 public boolean assertTextStringNotMatching(String acttext, String expText) throws Throwable {
	  boolean flag = false;
	  try {
	   // added loggers
	   LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	   LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
	   String ActualText = acttext.trim();
	   LOG.info("act - " + ActualText);
	   LOG.info("exp - " + expText);
	   
	   if (!(ActualText.equalsIgnoreCase(expText.trim()))) {
	    LOG.info("in if loop");
	    flag = true;
	    LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	    return true;
	   } else {
	    LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	    return false;
	   }
	  } catch (Exception e) {
	   e.printStackTrace();
	   return false;
	  } finally {
	   if (!flag) {

	    reporter.failureReport("Verify : " + expText, acttext + " is present in the element : ");

	    return false;
	   } else if (flag) {

	    reporter.SuccessReport("Verify : " + expText, acttext + " is not present in the element : ");
	   }
	  }
	 }

}


