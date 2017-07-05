package com.safelite.accelerators;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.safelite.accelerators.TestEngineWeb;
import com.safelite.report.ConfigFileReadWrite;
import com.safelite.report.ReporterConstants;
import com.safelite.utilities.Xls_Reader;

public class ActionEngine extends TestEngineWeb {
    private final Logger LOG = Logger.getLogger(ActionEngine.class);
    private final String msgClickSuccess = "Successfully Clicked On ";
    private final String msgClickFailure = "Unable To Click On ";
    private final String msgRightClickSuccess = "Successfully Mouse Right Clicked On ";
    private final String msgRightClickFailure = "Unable To Right Click On ";
    private final String msgTypeSuccess = "Successfully Entered value ";
    private final String msgTypeFailure = "Unable To Type On ";
    private final String msgIsElementFoundSuccess = "Successfully Found Element ";
    private final String msgIsElementFoundFailure = "Unable To Found Element ";
    private final String msgCheckboxisnotChecked = "Checkbox is not Selected";
    public boolean reportIndicator = true;
    public static Xls_Reader TestData = new Xls_Reader(System.getProperty("user.dir") + "/TestData/TestData.xlsx");
    //public static Xls_Reader CesTestData = new Xls_Reader(System.getProperty("user.dir") + "/TestData/CesTestData.xlsx");
    String APP_BASE_URL = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "baseUrl");
    public String CES_BASE_URL = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "cesBaseUrl");
    public String EM_BASE_URL = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "emBaseUrl");
    public String NC_BASE_URL = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "ncBaseUrl");
    private static final long DEFAULT_TIMEOUT_SEC = 90;
    private static final int SLEEP_MILLI_SEC = 1000;

    /**
     * click
     * @Description This method helps to perform click operation.
     * @author Cigniti Technologies 
     * @return boolean
     * @throws Throwable the throwable
     * @LastModifiedDate 05 june 2016
     */
    public boolean click(By locator, String locatorName) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : click  ::  Locator : " + locatorName);
            
            //Waiting for element
            WebDriverWait wait = new WebDriverWait(driver, 30);		
            LOG.info("Waiting for element");
            LOG.info("Locator is Visible :: " + locator);
            
            //validates whether the element is clickable or not
            wait.until(ExpectedConditions.elementToBeClickable(locator));	
            LOG.info("Clicked on the Locator");
            driver.findElement(locator).click();	//performs click operation on the element
            LOG.info("identified the element :: " + locator);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (!status) {
                if (reportIndicator) {
                	//reports a step in the report in case of clicking failure.
                    reporter.failureReport("Click : " + locatorName, msgClickFailure + locatorName, driver);
                }
            } else {
            	//reports a step in the report in case of successful clicking.
                reporter.SuccessReport("Click : " + locatorName, msgClickSuccess + locatorName);
            }
            reportIndicator = true;
        }
        return status;
    }
    
    /**
     * navigateToHomePage
     * @Description This method helps to navigate to the URL provided.
     * @author Cigniti Technologies 
     * @return void
     * @throws Throwable the throwable
     * @LastModifiedDate 05 june 2016
     */
    public void navigateToHomePage() throws Throwable {
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName());
            //Navigates to App base url.
            navigateTo(APP_BASE_URL);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            LOG.info("++++++++++++++++++++++++++++Catch Block Start+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            e.printStackTrace();
            LOG.info("++++++++++++++++++++++++++++Catch Block End+++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    /**
     * selectByIndex
     * @Description This method helps to select list value by index.
     * @author Cigniti Technologies
     * @param  By locator, int index and String locatorName
     * @return boolean
     * @throws Throwable the throwable
     * @LastModifiedDate 05 june 2016
     */
    public boolean selectByIndex(By locator, int index, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            Select s = new Select(driver.findElement(locator));
            s.selectByIndex(index);// Selects the element based on Index
            flag = true;
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return true;
        } catch (Exception e) {
            LOG.info("++++++++++++++++++++++++++++Catch Block Start+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("++++++++++++++++++++++++++++Catch Block End+++++++++++++++++++++++++++++++++++++++++++");
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                if (reportIndicator) {
                    reporter.failureReport("Select Value from the Dropdown :: " + locatorName,
                            "Option at index :: " + index + " is Not Select from the DropDown :: " + locatorName,driver);
                } else if (flag) {
                    reporter.SuccessReport("Select Value from the Dropdown :: " + locatorName,
                            "Option at index :: " + index + "is Selected from the DropDown :: " + locatorName);
                }
            }
            reportIndicator = true;
        }
    }
    
    /**
     * assertTrue
     * @Description This method helps to assert a given condition.
     * @author Cigniti Technologies
     * @param  By locator, int index and String locatorName
     * @return boolean
     * @throws Throwable the throwable
     * @LastModifiedDate 05 june 2016
     */
    public boolean assertTrue(boolean condition, String message) throws Throwable {
        try

        {
            if (condition)
                return true;
            else
                return false;
        } catch (
                Exception e)

        {
            LOG.info("++++++++++++++++++++++++++++Catch Block Start+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("++++++++++++++++++++++++++++Catch Block End+++++++++++++++++++++++++++++++++++++++++++");
            return false;
        } finally

        {
            if (!condition) {
                reporter.failureReport("Expected :: " + message, message + " is :: " + condition, driver);
            } else {
                reporter.SuccessReport("Expected :: " + message, message + " is :: " + condition);
            }
        }
    }

    /**
     * assertTrue
     * @Description This method enables dynamic wait
     * @author Cigniti Technologies
     * @param  By locator, int time
     * @return None
     * @throws Throwable the throwable
     * @LastModifiedDate 05 june 2016
     */
    public void dynamicWaitByLocator(By locator, int time) throws InterruptedException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, time);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * dynamicWaitByLocator
     * @Description This method enables dynamic wait
     * @author Cigniti Technologies
     * @param  By locator
     * @return None
     * @throws InterruptedException the throwable
     * @LastModifiedDate 05 june 2016
     */
    public void dynamicWaitByLocator(By locator) throws InterruptedException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT_SEC, SLEEP_MILLI_SEC);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
    }

    /**
     * VerifyJIRATicket
     * @Description This method helps to attach JIRA# to the report statement
     * @author Cigniti Technologies
     * @param  boolean flag, String JIRATicket
     * @return boolean
     * @throws throws the throwable
     * @LastModifiedDate 05 june 2016
     */
    public boolean VerifyJIRATicket(boolean flag, String JIRATicket) throws Throwable {
        try {
            if (!flag) {
                reporter.failureReport(JIRATicket + " ", "Retested Successfully Completed ");
                return false;
            } else {
                reporter.SuccessReport(JIRATicket + " ", "Retested Unsuccessfully Completed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * assertElementPresent
     * @Description This method helps to assert element presence and reports
     * @author Cigniti Technologies
     * @param  By by, String locatorName
     * @return boolean
     * @throws throws the throwable
     * @LastModifiedDate 05 june 2016
     */
    public boolean assertElementPresent(By by, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            Assert.assertTrue(isElementPresent(by, locatorName, true));
            flag = true;
        } catch (Exception e) {
            LOG.info("++++++++++++++++++++++++++++Catch Block Start+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            e.printStackTrace();
            LOG.info("++++++++++++++++++++++++++++Catch Block End+++++++++++++++++++++++++++++++++++++++++++");
        } finally {
            if (!flag) {
                reporter.failureReport("AssertElementPresent :: ", locatorName + " present in the page :: ",driver);
                //return false;
            } else {
                reporter.SuccessReport("AssertElementPresent :: ", locatorName + " is not present in the page :: ");
            }
        }
        return flag;
    }
    
    /**
     * mouseHoverByJavaScript
     * @Description This method helps to perform mouse hover 
     * @author Cigniti Technologies
     * @param  By locator, String locatorName
     * @return boolean
     * @throws throws the throwable
     * @LastModifiedDate 05 june 2016
     */
    public boolean mouseHoverByJavaScript(By locator, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            WebElement mo = driver.findElement(locator);
            String javaScript = "var evObj = document.createEvent('MouseEvents');"
                    + "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
                    + "arguments[0].dispatchEvent(evObj);";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(javaScript, mo);
            flag = true;
            LOG.info("MoveOver action is done on  :: " + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return true;
        } catch (Exception e) {
            LOG.info("++++++++++++++++++++++++++++Catch Block Start+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("++++++++++++++++++++++++++++Catch Block End+++++++++++++++++++++++++++++++++++++++++++");
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                reporter.failureReport("MouseOver :: ", "MouseOver action is not perform on :: " + locatorName,driver);
            } else {
                reporter.SuccessReport("MouseOver :: ", "MouserOver Action is Done on :: " + locatorName);
            }
        }
    }

    /**
     * waitForVisibilityOfElement
     * @param  by of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean waitForVisibilityOfElement(By by, String locatorName) throws Throwable {
        boolean flag = false;
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
        LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
        WebDriverWait wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            flag = true;
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return true;
        } catch (Exception e) {
            LOG.info("++++++++++++++++++++++++++++Catch Block Start+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            e.printStackTrace();
            LOG.info("++++++++++++++++++++++++++++Catch Block End+++++++++++++++++++++++++++++++++++++++++++");
            return false;
        } finally {
            if (!flag) {
                reporter.failureReport("Visible of element is false ", "Element_" + locatorName + " is not visible", driver);
            } else{
                reporter.SuccessReport("Visible of element is true ", "Element_" + locatorName + "  is visible");
            }
        }
    }

    /**
     * clickUsingJavascriptExecutor
     * @param  locator of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean clickUsingJavascriptExecutor(By locator, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            WebElement element = driver.findElement(locator);
            isElementPresent(locator, locatorName);
            //internalServerErrorHandler();
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            flag = true;
            LOG.info("clicked : " + locatorName);
        } catch (Exception e) {
            LOG.info("++++++++++++++++++++++++++++Catch Block Start+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("++++++++++++++++++++++++++++Catch Block End+++++++++++++++++++++++++++++++++++++++++++");
            flag = false;
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                if (reportIndicator) {
                    reporter.failureReport("Click : " + locatorName, msgClickFailure + locatorName, driver);
                }
            } else {
                reporter.SuccessReport("Click : " + locatorName, msgClickSuccess + locatorName);
            }
            reportIndicator = true;
        }
        return flag;
    }

    /**
     * selectByValue
     * @param  locator of (By)
     * @param  value of (String)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean selectByValue(By locator, String value, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            Select s = new Select(driver.findElement(locator));
            s.selectByValue(value);
            flag = true;
            LOG.info("Successfully selected the value" + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                reporter.failureReport("Select",
                        "Option with value attribute : " + value + " is Not Select from the DropDown : " + locatorName,driver);
            } else {
                reporter.SuccessReport("Select",
                        "Option with value attribute : " + value + " is  Selected from the DropDown : " + locatorName);
            }
        }
    }

    /**
     * selectByVisibleText
     * @param  locator of (By)
     * @param  visibleText of (String)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean selectByVisibleText(By locator, String visibleText, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            Select s = new Select(driver.findElement(locator));
            s.selectByVisibleText(visibleText.trim());
            flag = true;
            return true;
        } catch (Exception e) {
            //return false;
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                reporter.failureReport("Select", visibleText + " is Not Select from the DropDown " + locatorName,driver);
            } else {
                reporter.SuccessReport("Select", visibleText + "  is Selected from the DropDown " + locatorName);
            }
        }
    }

    class AcceptThread extends Thread {
        public void run() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Robot r = new Robot();
                r.keyPress(KeyEvent.VK_ENTER);
                r.keyRelease(KeyEvent.VK_ENTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * isVisible
     * @param  locator of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean isVisible(By locator, String locatorName) throws Throwable {
         boolean flag = false;
        try {
            //added loggers
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name :: " + getCallerClassName() + " Method name :: " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            //value = driver.findElement(locator).isDisplayed();
            flag = driver.findElement(locator).isDisplayed();
            //value = true;
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            flag = false;
        } finally {
            if (!flag) {
                reporter.failureReport("IsVisible : ", locatorName + " Element is Not Visible",driver);
            } else {
                reporter.SuccessReport("IsVisible : ", locatorName + " Element is Visible ");
            }
        }
        return flag;
    }

    /**
     * getElementsSize
     * @param  locator of (By)
     * @return int
     */
    public int getElementsSize(By locator) {
        int a = 0;
        try {
            List<WebElement> rows = driver.findElements(locator);
            a = rows.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * assertTextMatching
     * @param  by of (By)
     * @param  text of (String)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
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
                reporter.failureReport("Verify : " + locatorName, text + " is not present in the element : ",driver);
                //return false;
            } else {
                reporter.SuccessReport("Verify : " + locatorName, text + " is  present in the element : " + locatorName);
            }
        }
    }

    /**
     * assertTextMatchingWithAttribute
     * @param  by of (By)
     * @param  text of (String)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean assertTextMatchingWithAttribute(By by, String text, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            // added loggers
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            String ActualText = getAttributeByValue(by, text).trim();
            LOG.info("ActualText is" + ActualText);
            if (ActualText.contains(text.trim())) {
                flag = true;
                // added loggers
                LOG.info("String comparison with actual text :: " + "actual text is :" + ActualText + "And expected text is : " + text);
                LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (!flag) {
                reporter.failureReport("Verify : " + locatorName, text + " is not present in the element : ",driver);
                //return false;
            } else {
                reporter.SuccessReport("Verify : " + locatorName, text + " is  present in the element : ");
            }
        }
    }

    /**
     * assertTextStringMatching
     * @param  actText of (String)
     * @param  expText of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean assertTextStringMatching(String actText, String expText) throws Throwable {
        boolean flag = false;
        try {
            // added loggers
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            String ActualText = actText.replace("\n", "").replace("\r", "").trim();
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
                reporter.failureReport("Verify : " + expText, actText + " is not present in the element : ",driver);
                //return false;
            } else{
                reporter.SuccessReport("Verify : " + expText, actText + " is  present in the element : ");
            }
        }
    }



    /**
     * getTimeStamp
     * @return String
     */
    public String getTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = sdf.format(date);
        suiteStartTime = formattedDate.replace(":", "/").replace(" ", "/");
        return suiteStartTime;
    }

    public String getNextDate(int days) throws Throwable{
        Date date =  new Date();
        Date dt1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dt = sdf.format(date);
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, days);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        dt1  = sdf.parse(dt);
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        String fdate = sdf.format(dt1);
        System.out.println("Date"+fdate);
        return  fdate;
    }

    /**
     * isElementPresent
     * @param  by of (By)
     * @param  locatorName of (String)
     * @param  expected of (boolean)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean isElementPresent(By by, String locatorName, boolean expected) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            waitTime();
            driver.findElement(by);
            this.reporter.SuccessReport("isElementPresent : " + locatorName,
                    this.msgIsElementFoundSuccess + locatorName);
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
            LOG.info(e.getMessage());
            if (expected == status) {
				LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				reporter.SuccessReport("isElementPresent : ", locatorName + "isElementPresent");
			} else {
				LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				LOG.info(e.getMessage());
				reporter.failureReport("isElementPresent : ", msgIsElementFoundFailure + locatorName, driver);
			//throw new RuntimeException(e);
            }
        } /*finally {
            if (!status) {
                if (reportIndicator) {
                    LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					*//*LOG.info(e.getMessage());*//*
                    reporter.failureReport("isElementPresent : ", msgIsElementFoundFailure + locatorName, driver);
					*//*throw new RuntimeException(e);*//*
                }
            } else {
                LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                reporter.SuccessReport("isElementPresent : ", locatorName + ", isElementPresent : true");
            }
            reportIndicator = true;
        }*/
        return status;
    }

    /**
     * isElementPresent
     * @param  by of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean isElementPresent(By by, String locatorName) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            dynamicWait(by);
            driver.findElement(by);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            this.reporter.SuccessReport("isElementPresent : " + locatorName,this.msgIsElementFoundSuccess + locatorName);
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            //throw new RuntimeException(e);
        }
        return status;
    }
    
    
    /**
     * isElementPresent
     * @param  by of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean checkIsElementPresent(By by, String locatorName) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            dynamicWait(by);
            driver.findElement(by);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            this.reporter.SuccessReport("isElementPresent : " + locatorName,this.msgIsElementFoundSuccess + locatorName);
            status = true;
        } catch (Exception e) {
            status = false;
//            LOG.info(e.getMessage());
            //throw new RuntimeException(e);
        }
        return status;
    }

    /**
     * scroll
     * @param  by of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean scroll(By by, String locatorName) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName());
            WebElement element = this.driver.findElement(by);
            Actions actions = new Actions(this.driver);
            actions.moveToElement(element);
            actions.build().perform();
            LOG.info("Scroll is performed : " + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * JSScroll
     * @param  by of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean JSScroll(By by, String locatorName) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName());
            WebElement element = this.driver.findElement(by);
            JavascriptExecutor js = ((JavascriptExecutor) this.driver);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            LOG.info("Scroll is performed : " + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * verifyElementPresent
     * @param  by of (By)
     * @param  locatorName of (String)
     * @param  expected of (boolean)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean verifyElementPresent(By by, String locatorName, boolean expected) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName());
            if (this.driver.findElement(by).isDisplayed()) {
                this.reporter.SuccessReport("VerifyElementPresent : " + locatorName,
                        this.msgIsElementFoundSuccess + locatorName);
                LOG.info("Element is available :: " + locatorName);
                LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                status = true;
            } else {
                status = false;
            }
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            //throw new RuntimeException(e);
        }
        return status;
    }

    public boolean verifyElementExist(By by, String locatorName, boolean expected) throws Throwable {
        boolean status = false;
        try {
            if (driver.findElements(by).size() > 0) {
                status = true;
                System.out.println("Status In IF CONDITION>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+status);
                this.reporter.SuccessReport("verifyElementExist : " + locatorName,
                        this.msgIsElementFoundSuccess + locatorName);
            } else {
                status = false;
                System.out.println("Status is>>>>>>>>>>>>>>>>>>>>>>"+status);
                this.reporter.failureReport("verifyElementExist : " + locatorName,
                        this.msgIsElementFoundFailure + locatorName,driver);
            }
        }catch (Exception e) {
            System.out.println("Exception Occurs@@@@@@@@@@@@@@@@@@@@@");
            status = false;
        }

        return  status;
    }

    public boolean waitTime() throws Throwable {
        boolean status = true;
        String time = ReporterConstants.Timeout;
        long timeValue = Long.parseLong(time);
        LOG.info("Time out value is : " + timeValue);
        return status;
    }

    /**
     * type
     * @param  locator of (By)
     * @param  testData of (String)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean type(By locator, String testData, String locatorName) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : Type  ::  Locator : " + locatorName + " :: Data :" + testData);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            LOG.info("Waiting for element :");
            //wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            LOG.info("Locator is Visible :: " + locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).click();
            LOG.info("Clicked on the Locator : ");
            driver.findElement(locator).clear();
            LOG.info("Cleared the existing Locator data : ");
            driver.findElement(locator).sendKeys(testData);
            LOG.info("Typed the Locator data :: " + testData);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            reporter.SuccessReport("Enter text in :: " + locatorName, msgTypeSuccess + testData);
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            reporter.failureReport("Enter text in :: " + locatorName, msgTypeFailure + testData, driver);
            throw new RuntimeException(e);
        }
        return status;
    }

    /**
     * typeUsingJavaScriptExecutor
     * @param  locator of (By)
     * @param  testData of (String)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean typeUsingJavaScriptExecutor(By locator, String testData, String locatorName) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + locatorName);
            waitTime();
            WebElement searchbox = driver.findElement(locator);
            JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
            myExecutor.executeScript("arguments[0].value=' " + testData + "'; ", searchbox);
            reporter.SuccessReport("Enter text in :: " + locatorName, msgTypeSuccess + locatorName);
            LOG.info("Clicked on  : " + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            reporter.failureReport("Enter text in :: " + locatorName, msgTypeFailure + locatorName, driver);
            throw new RuntimeException(e);
        }
        return status;
    }

    /**
     * Moves the mouse to the middle of the element. The element is scrolled
     * into view and its location is calculated using getBoundingClientRect.
     *
     * @param locator : Action to be performed on element (Get it from Object
     *                repository)
     */
    public boolean waitForTitlePresent(By locator) throws Throwable {
        boolean flag = false;
        boolean bValue = false;
        try {
            for (int i = 0; i < 200; i++) {
                if (driver.findElements(locator).size() > 0) {
                    flag = true;
                    bValue = true;
                    break;
                } else {
                    driver.wait(50);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                reporter.failureReport("WaitForTitlePresent :: ", "Title is wrong : ",driver);
            } else {
                reporter.SuccessReport("WaitForTitlePresent :: ", "Launched successfully expected Title : ");
            }
        }
        return bValue;
    }

    /**
     * getTitle
     * @return String
     * @throws Throwable the throwable
     */
    public String getTitle() throws Throwable {
        String text = driver.getTitle();
        {
            reporter.SuccessReport("Title :: ", "Title of the page is :: " + text);
        }
        return text;
    }

    /**
     * assertText
     * @return boolean
     * @throws Throwable the throwable
     */

    public BigDecimal formatBigDecimal(String bigDecimal){
        //Handling the $ and spaces in the value
        String bigDecimalUpdated = "";
        try {
            if (bigDecimal.contains("$")) {
                bigDecimalUpdated = bigDecimal.substring(bigDecimal.indexOf("$") + 1).replaceAll(" ", "");
            } else
                bigDecimalUpdated = bigDecimal.replaceAll(" ", "");

            //Format Big Decimal
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator(',');
            symbols.setDecimalSeparator('.');
            String pattern = "#,##0.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            //Parse Big Decimal
            BigDecimal formattedBigDecimal = (BigDecimal) decimalFormat.parse(bigDecimalUpdated);
            return formattedBigDecimal.setScale(2);
        } catch (Exception e) {
            System.out.println("Unable to parse the value "+bigDecimalUpdated);
            return null;
        }
    }


    /**
     * assertText
     * @param  by of (By)
     * @param  text of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean assertText(By by, String text) throws Throwable {
        boolean flag = false;
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
        try {
            Assert.assertEquals(getText(by, text).trim(), text.trim());
            flag = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (!flag) {
                reporter.failureReport("AssertText :: ", text + " is not present in the element : ");
                return false;
            } else {
                reporter.SuccessReport("AssertText :: ", text + " is  present in the element : ");
            }
        }
    }

    
    /**
     * assertText
     * @param  by of (By)
     * @param  text of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean assertText(String actual, String expected) throws Throwable {
        boolean flag = false;
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
        try {
            Assert.assertEquals(actual.trim(), expected.trim());
            flag = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (!flag) {
                reporter.failureReport("AssertText :: ", expected + " is not present in the element : ");
                return false;
            } else {
                reporter.SuccessReport("AssertText :: ", expected + " is  present in the element : ");
            }
        }
    }

    /**
     * assertTitle
     * @param  title of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean assertTitle(String title) throws Throwable {
        boolean flag = false;
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
        try {
            By windowTitle = By.xpath("//title[contains(text(),'" + title + "')]");
            if (waitForTitlePresent(windowTitle)) {
                Assert.assertEquals(getTitle(), title);
                flag = true;
                LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                return true;
            } else {
                LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (!flag) {
                reporter.failureReport("AsserTitle :: ", "Page title is not matched with : " + title,driver);
                return false;
            } else {
                reporter.SuccessReport("AsserTitle :: ", " Page title is verified with : " + title);
            }
        }
    }

    /**
     * getText
     * @param  locator of (By)
     * @param  locatorName of (String)
     * @return String
     * @throws Throwable the throwable
     */
    public String getText(By locator, String locatorName) throws Throwable {
        String text = "";
        boolean flag = false;
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
        try {
            waitTime();
            if (isElementPresent(locator, locatorName, true)) {
                text = driver.findElement(locator).getText();
                LOG.info("Locator is Visible and text is retrieved :: " + text);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                reporter.warningReport("GetText :: ", "Unable to get Text from :: " + locatorName);
                LOG.info("GetText :: Unable to get Text from :: " + locatorName);
            } else {
                reporter.SuccessReport("GetText :: " + locatorName, "" + locatorName + " is :" + text);
                LOG.info("Locator is Visible and text is retrieved :: " + text);
            }
        }
        return text;
    }

    /**
     * getAttributeByValue
     * @param  locator of (By)
     * @param  locatorName of (String)
     * @return String
     * @throws Throwable the throwable
     */
    public String getAttributeByValue(By locator, String locatorName) throws Throwable {
        String text = "";
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName());
            waitTime();
            if (isElementPresent(locator, locatorName, true)) {
                text = driver.findElement(locator).getAttribute("value");
                LOG.info("Locator is Visible and attribute value is retrieved :: " + text);
                LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                reporter.warningReport("GetAttribute :: ", "Unable to get Attribute value from :: " + locatorName);
                LOG.info("GetAttribute :: Unable to get Attribute value from :: " + locatorName);
            } else {
                reporter.SuccessReport("GetAttribute :: ", "" + locatorName + " is" + text);
                LOG.info("Locator is Visible and attribute value is retrieved :: " + text);
            }
        }
        return text;
    }

    /**
     * getAttributeByValue
     * @param  locator of (By)
     * @param  locatorName of (String)
     * @return String
     * @throws Throwable the throwable
     */
    public String getAttributeByClass(By locator, String locatorName) throws Throwable {
        String text = "";
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName());
            waitTime();
            if (isElementPresent(locator, locatorName, true)) {
                text = driver.findElement(locator).getAttribute("class");
                LOG.info("Locator is Visible and attribute value is retrieved :: " + text);
                LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                reporter.warningReport("GetAttribute :: ", "Unable to get Attribute value from :: " + locatorName);
                LOG.info("GetAttribute :: Unable to get Attribute value from :: " + locatorName);
            } else {
                reporter.SuccessReport("GetAttribute :: ", "" + locatorName + " is" + text);
                LOG.info("Locator is Visible and attribute value is retrieved :: " + text);
            }
        }
        return text;
    }

    
    
    /**
     * Moves the mouse to the middle of the element. The element is scrolled
     * into view and its location is calculated using getBoundingClientRect.
     *
     * @param locator     : Action to be performed on element (Get it from Object
     *                    repository)
     * @param locatorName : Meaningful name to the element (Ex:link,menus etc..)
     */
    public boolean mouseHover(By locator, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Mouse over start :: " + locatorName);
            WebElement mo = this.driver.findElement(locator);
            new Actions(this.driver).moveToElement(mo).build().perform();
            flag = true;
            LOG.info("Mouse over End :: " + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++");
            return true;
        } catch (Exception e) {
            //return false;
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                this.reporter.failureReport("MouseOver :: ", "MouseOver action is not perform on ::" + locatorName,
                        this.driver);
            } else {
                this.reporter.SuccessReport("MouseOver :: ", "MouserOver Action is Done on  :: " + locatorName);
            }
        }
    }

    /**
     * JSClick
     * @param  locator of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean JSClick(By locator, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            //added the loggers for click method

            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            LOG.info("Method : click  ::  Locator : " + locatorName);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            LOG.info("Waiting for element");
            //internalServerErrorHandler();
            //wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            LOG.info("Locator is Visible :: " + locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            WebElement element = this.driver.findElement(locator);
            JavascriptExecutor executor = (JavascriptExecutor) this.driver;
            executor.executeScript("arguments[0].click();", element);
            //LOG.info("Successfully clicked on :: " + locatorName);
            // driver.executeAsyncScript("arguments[0].click();", element);
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                LOG.info("Inside Finally block");
                this.reporter.failureReport("Click : " + locatorName, "Click is not performed on : " + locatorName, driver);
            } else {
                LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                this.reporter.SuccessReport("Click : " + locatorName, "Successfully click on  : " + locatorName);
            }
        }
        return flag;
    }

    /**
     * JSClickUntil
     * @param  locator of (By)
     * @param  waitLocator of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean JSClickUntil(By locator, By waitLocator, String locatorName)
            throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            //wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            WebElement element = this.driver.findElement(locator);
            JavascriptExecutor executor = (JavascriptExecutor) this.driver;
            int icounter = 0;
            do {
                try {
                    if (isElementPresent(waitLocator, "Wait for Element : " + locatorName)) {
                        flag = true;
                        break;
                    } else {
                        icounter = icounter + 1;
                    }
                    if (icounter >= 3) {
                        flag = false;
                        break;
                    }
                    executor.executeScript("arguments[0].click();", element);
                } catch (Exception e) {
                    LOG.info("Retrying for the object :: " + waitLocator
                            + " :: Iteration : " + icounter);
                }

            } while (icounter <= 3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                this.reporter.failureReport("Click : ",
                        "Click action is not perform on : " + locatorName, driver);
                //return flag;
            } else {
                this.reporter.SuccessReport("Click : ", "Clicked : " + locatorName);
                //return flag;
            }
        }
        return flag;
    }

    /**
     * clickUntil
     * @param  locator of (By)
     * @param  waitLocator of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean clickUntil(By locator, By waitLocator, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method :" + getCallerMethodName() + "  ::  Locator : " + locatorName);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            LOG.info("Waiting for element");
            //internalServerErrorHandler();
            //wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            LOG.info("Locator is Visible :: " + locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            LOG.info("Clicked on the Locator");

            int icounter = 0;
            do {
                try {
                    if (isElementPresent(waitLocator, "Wait for Element : " + locatorName)) {
                        flag = true;
                        break;
                    } else {
                        icounter = icounter + 1;
                    }
                    if (icounter >= 3) {
                        flag = false;
                        break;
                    }
                    driver.findElement(locator).click();
                } catch (Exception e) {
                    LOG.info("Retrying for the object :: " + waitLocator
                            + " :: Iteration : " + icounter);
                }

            } while (icounter <= 3);

            LOG.info("identified the element :: " + locator);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            reporter.SuccessReport("Click : " + locatorName, msgClickSuccess + locatorName);
        } catch (Exception e) {
            flag = false;
            LOG.info(e.getMessage());
            reporter.failureReport("Click : " + locatorName, msgClickFailure + locatorName, driver);
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**
     * jsMouseHover
     * @param  locator of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean jsMouseHover(By locator, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method :" + getCallerMethodName() + "  ::  Locator : " + locatorName);
            WebElement HoverElement = this.driver.findElement(locator);
            String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
            ((JavascriptExecutor) this.driver).executeScript(mouseOverScript, HoverElement);
            LOG.info("JSmousehover is performed  on :: " + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                this.reporter.failureReport("MouseOver : ", "MouseOver action is not perform on : " + locatorName,driver);
                //return flag;
            } else {
                this.reporter.SuccessReport("MouseOver : ", "MouserOver Action is Done on" + locatorName);
               // return flag;
            }
        }
        return flag;
    }

    /**
     * getWebElementList
     * @param  by of (By)
     * @param  locatorName of (String)
     * @return  List<WebElement>
     * @throws Throwable the throwable
     */
    public List<WebElement> getWebElementList(By by, String locatorName) throws Throwable {
        List<WebElement> elements = null;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method :" + getCallerMethodName() + "  ::  Locator : " + locatorName);
            WebDriverWait wait = new WebDriverWait(this.driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            elements = driver.findElements(by);
            LOG.info("Size of List ::" + elements.size());
            for (int i = 0; i < elements.size(); i++) {
                //elements = this.driver.findElements(by);
                LOG.info("List value are :: " + elements.get(i).getText());
            }
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elements;
    }

    /**
     * getWebElementList
     * @param  by of (By)
     * @param  locatorName of (String)
     * @return  List<WebElement>
     * @throws Throwable the throwable
     */
    public List<WebElement> getWebElementListandCompareWithString(By by, String locatorName, String expectedText) throws Throwable {
        List<WebElement> elements = null;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method :" + getCallerMethodName() + "  ::  Locator : " + locatorName);
            WebDriverWait wait = new WebDriverWait(this.driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            elements = driver.findElements(by);
            LOG.info("Size of List ::" + elements.size());
            for (int i = 0; i < elements.size(); i++) {
                Assert.assertEquals(elements.get(i).getText(), expectedText);
            }
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            this.reporter.failureReport("getWebElementListandCompareWithString : ", "Element is not visible " + locatorName,driver);
            e.printStackTrace();

        }
        return elements;
    }

    /**
     * getWebElementList
     * @param  by of (By)
     * @param  locatorName of (String)
     * @return  List<WebElement>
     * @throws Throwable the throwable
     */
    public List<WebElement> getWebElementListandContainsWithString(By by, String locatorName, String expectedText) throws Throwable {
        List<WebElement> elements = null;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method :" + getCallerMethodName() + "  ::  Locator : " + locatorName);
            WebDriverWait wait = new WebDriverWait(this.driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            elements = driver.findElements(by);
            LOG.info("Size of List ::" + elements.size());
            for (int i = 0; i <elements.size(); i++) {
                String elementText= elements.get(i).getText();
                Assert.assertTrue(elementText.contains(expectedText));
            }
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            e.printStackTrace();
            this.reporter.failureReport("getWebElementListandCompareWithString : ", "Element is not visible " + locatorName,driver);

        }
        return elements;
    }


    /**
     * getWebElementList
     * @param  by of (By)
     * @param  locatorName of (String)
     * @return  List<WebElement>
     * @throws Throwable the throwable
     */
    public List<WebElement> getWebElementListandContainsWithList(By by, String locatorName, Set<String> expectedSet) throws Throwable {
        List<WebElement> elements = null;
        Set<String> actualSetData = new HashSet<>();
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method :" + getCallerMethodName() + "  ::  Locator : " + locatorName);
            WebDriverWait wait = new WebDriverWait(this.driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            elements = driver.findElements(by);
            LOG.info("Size of List ::" + elements.size());

            for (int i = 0; i < elements.size(); i++) {
                actualSetData.add(elements.get(i).getText());
            }
            actualSetData.containsAll(expectedSet);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            e.printStackTrace();
            this.reporter.failureReport("getWebElementListandContainsWithList : ", "Element is not visible " + locatorName,driver);

        }
        return elements;
    }
    /**
     * elementLoadingTime
     * @param  locator of (By)
     * @return  float
     * @throws Throwable the throwable
     */
    public float elementLoadingTime(By locator) throws Throwable {
        boolean flag = false;
        float timeTaken = 0;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            long start = System.currentTimeMillis();
            LOG.info("start " + start);
            WebDriverWait wait = new WebDriverWait(driver, 50);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            // wait.until(ExpectedConditions.elementToBeClickable(locator));
            long stop = System.currentTimeMillis();
            timeTaken = (stop - start);
            LOG.info("The time taken for the page to load is : " + timeTaken + " milli seconds : ");
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeTaken;
    }

    /**
     * elementVisibleTime
     * @param  locator of (By)
     * @return void
     * @throws Throwable the throwable
     */
    public void elementVisibleTime(By locator) throws Throwable {
        boolean flag = false;
        float timeTaken = 0;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            long start = System.currentTimeMillis();
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            long stop = System.currentTimeMillis();
            timeTaken = (stop - start) / 1000;
            LOG.info("Took : " + timeTaken + " secs to display the results : ");
            reporter.SuccessReport("Total time taken for element visible :: ", "Time taken load the element :: " + timeTaken + " seconds");
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves the mouse to the middle of the element. The element is scrolled
     * into view and its location is calculated using getBoundingClientRect.
     *
     * @param destinationLocator : Action to be performed on element (Get it from Object
     *                           repository)
     * @param locatorName        : Meaningful name to the element (Ex:link,menus etc..)
     */
    public boolean dragAndDrop(By souceLocator, By destinationLocator, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            Actions builder = new Actions(this.driver);
            WebElement souceElement = this.driver.findElement(souceLocator);
            WebElement destinationElement = this.driver.findElement(destinationLocator);
			/*Action dragAndDrop = builder.clickAndHold(souceElement).moveToElement(destinationElement)
					.release(destinationElement).build();
			dragAndDrop.perform();*/
            builder.dragAndDrop(souceElement, destinationElement).build().perform();
            flag = true;
            LOG.info("drag and drop performed ");
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                this.reporter.failureReport("DragDrop : ", "Drag and Drop action is not performed on : " + locatorName,
                        this.driver);
            } else {
                this.reporter.SuccessReport("DragDrop : ", "Drag and Drop Action is Done on : " + locatorName);
            }
        }
    }

    /**
     * navigateTo
     * @param  Url of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean navigateTo(String Url) throws Throwable {
        boolean flag = false;
        try {
            waitTime();
            WebDriver.navigate().to(Url);
            LOG.info("Navigated URL is : " + Url);
            flag = true;
            return flag;
        } catch (Exception e) {
            flag = false;
            LOG.info(e.getMessage());
        } finally {
            if (!flag) {
                reporter.failureReport("Unable to Open : ", Url,driver);
                return false;
            } else {
                reporter.SuccessReport("Successfully Opened : ", Url);
            }
        }
        return flag;
    }

    /**
     * navigateTo
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean browserNavigatedTo(String navigationAction) throws Throwable {
        boolean flag = false;
        try {
            switch (navigationAction) {
                case "forward":
                    WebDriver.navigate().forward();
                    break;
                case "backward":
                    WebDriver.navigate().back();
                    break;
            }
            waitTime();
            LOG.info("Navigate to"+navigationAction);
            flag = true;
            return flag;
        } catch (Exception e) {
            flag = false;
            LOG.info(e.getMessage());
        } finally {
            if (!flag) {
                reporter.failureReport("Browser navigated to : ", navigationAction,driver);
                return false;
            } else {
                reporter.SuccessReport("Successfully Navigated to : ", navigationAction);
            }
        }
        return flag;
    }
    /**
     * generateRandomNumber
     * @return int
     * @throws Throwable the throwable
     */
    public int generateRandomNumber() throws Throwable {
        Random generator = new Random();
        int intRandom_number = generator.nextInt(9999) + 10000;
        return intRandom_number;
    }

    /**
     * rightClick
     * @param  locator of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean rightClick(By locator, String locatorName) throws Throwable {
        boolean status;
        try {
            //added loggers
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            Actions action = new Actions(driver);
            action.contextClick(driver.findElement(locator)).build().perform();
            driver.findElement(locator).click();
            reporter.SuccessReport("Click : " + locatorName, msgRightClickSuccess + locatorName);
            LOG.info("Right click performed  on :: " + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            reporter.failureReport("Click : " + locatorName, msgRightClickFailure + locatorName, driver);
            throw new RuntimeException(e);
        }
        return status;
    }

    /**
     * dynamicWait
     * @param  locator of (By)
     * @return void
     * @throws Throwable the throwable
     */
    public void dynamicWait(By locator) throws Throwable {
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locator);
            String time = ReporterConstants.DYNAMIC_TIMEOUT;
            int timevalue = Integer.parseInt(time);
            WebDriverWait wait = new WebDriverWait(driver, timevalue);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            LOG.info(locator + ":: displayed succussfully");
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            LOG.info(e.getMessage());
            //reporter.failureReport("Unable to find Element :: " + locator, msgIsElementFoundFailure + locator, driver);
            //throw new RuntimeException(e);

        }
    }

    /**
     * getCallerClassName
     * @return String
     */
    public static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        return stElements[3].getClassName();
    }

    /**
     * getCallerMethodName
     * @return String
     */
    public static String getCallerMethodName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        return stElements[3].getMethodName();
    }

    /**
     * Double click the mouse to the middle of the element. The element is scrolled
     * into view and its location is calculated using getBoundingClientRect.
     *
     * @param locator     : Action to be performed on element (Get it from Object
     *                    repository)
     * @param locatorName : Meaningful name to the element (Ex:link,menus etc..)
     */
    public boolean mouseDoubleClick(By locator, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Mouse Double Click start :: " + locatorName);
            WebElement mo = this.driver.findElement(locator);
            new Actions(this.driver).moveToElement(mo).doubleClick(mo).build().perform();
            flag = true;
            LOG.info("Mouse Double Click :: " + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++");
            return true;
        } catch (Exception e) {
            //return false;
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                this.reporter.failureReport("double Click :: ", "double Click action is not perform on ::" + locatorName,
                        this.driver);
            } else {
                this.reporter.SuccessReport("double Click :: ", "double Click Action is Done on  :: " + locatorName);
            }
        }
    }

    /**
     * click the mouse to the middle of the element. The element is scrolled
     * into view and its location is calculated using getBoundingClientRect.
     *
     * @param locator     : Action to be performed on element (Get it from Object
     *                    repository)
     * @param locatorName : Meaningful name to the element (Ex:link,menus etc..)
     */
    public boolean mouseClick(By locator, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Mouse Double Click start :: " + locatorName);
            WebElement mo = this.driver.findElement(locator);
            new Actions(this.driver).click(mo).build().perform();
            flag = true;
            LOG.info("Mouse Double Click :: " + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++");
            return true;
        } catch (Exception e) {
            //return false;
            throw new RuntimeException(e);
        } finally {
            if (!flag) {
                this.reporter.failureReport("Click :: ", "Click action is not perform on ::" + locatorName,
                        this.driver);
            } else {
                this.reporter.SuccessReport(" Click :: ", " Click Action is Done on  :: " + locatorName);
            }
        }
    }

    /**
     * getYear, Function to get required year e.g: 0-Current year, 1-Next year,
     * @param  number of (int) Number to get year (e.g: -1,0,1 etc)
     * @return int
     * @throws Throwable the throwable
     */
    public int getYear(int number) throws Throwable {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR) + number;
        LOG.info("Year is : " + year);
        return year;
    }

    /**
     * dateFormatVerification, Function to verify date format by giving actual date
     * @param  actualDate of (String) actual date e.g: 21-11-2015
     * @param  formatToVerify of (String) format type e.g: dd-MM-yyyy
     * @return boolean
     */
    public boolean dateFormatVerification(String actualDate, String formatToVerify) {
        boolean flag = false;

        if (actualDate.toLowerCase().contains("am")) {
            flag = formatVerify(actualDate, formatToVerify);
        } else if (actualDate.toLowerCase().contains("pm")) {
            flag = formatVerify(actualDate, formatToVerify);
        } else if (!actualDate.toLowerCase().contains("am") || !actualDate.toLowerCase().contains("pm")) {
            flag = formatVerify(actualDate, formatToVerify);
        }
        return flag;
    }

    /**
     * formatVerify, Reusable Function to verify date format by giving actual date
     * @return : boolean
     * @param  actualDate of (String)e.g: 21-11-2015
     * @param  formatToVerify of (String) type e.g: dd-MM-yyyy
     */
    public boolean formatVerify(String actualDate, String formatToVerify) {
        boolean flag = false;
        try {
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat(formatToVerify);
            Date date = sdf.parse(actualDate);
            String formattedDate = sdf.format(date);
            if (actualDate.equals(formattedDate)) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * replaceAll, Function to replace the regular expression values with client required values
     * @return : String
     * @param   text of (String)
     * @param   pattern of (String), regular expression of actual value
     * @param   replaceWith of (String), value to replace the actual
     */
    public String replaceAll(String text, String pattern, String replaceWith) {
        String flag = null;
        try {
            flag = text.replaceAll(pattern, replaceWith);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * subString, Function to get sub string of given actual string text
     * @return : String
     * @param  text of (String), Actual text
     * @param  startIndex of (int), Start index of sub string
     * @param  endIndex of (int), end index of sub string
     */
    public String subString(String text, int startIndex, int endIndex) {
        String flag = null;
        try {
            flag = text.substring(startIndex, endIndex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * getCssValue, Function to get the value of a given CSS property (e.g. width)
     * @return : String
     * @param  locator of (By)
     * @param  cssValue of (String), CSS property
     */
    public String getCssValue(By locator, String cssValue) {
        String result = "";
        try {
            result = this.driver.findElement(locator).getCssValue(cssValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * getBackGroundColor, Function to get the background color of a given web element (e.g. background-color)
     * @return : String
     * @param  locator of (By)
     * @param  cssValue of (String), CSS property (e.g. background-color)
     */
    public String getBackGroundColor(By locator, String cssValue) {
        String hexColor = "";
        try {
            String bColor = this.driver.findElement(locator).getCssValue(cssValue);
            hexColor = Color.fromString(bColor).asHex();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hexColor;
    }

    /**
     * switchToFrame, Function to switch to frame
     * @return : void
     * @param  locator of (By)
     */
    public void switchToFrame(By locator) {
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
        WebDriverWait wait = new WebDriverWait(driver, 30);
        LOG.info("Waiting for element");
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        LOG.info("Locator is Visible :: " + locator);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        driver.switchTo().frame(driver.findElement(locator));
    }

    /**
     * getCurrentDateTime, Function to get current time in client required format
     * @param  dateTimeFormat of (String), format to get date and time (e.g: h:mm)
     * @return : String
     */
    public String getCurrentDateTime(String dateTimeFormat) throws Throwable {
        DateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * getFutureDateTime, Function to get future or past date in client required format
     * @return : String
     * @param  dateTimeFormat of (String), format to get date and time (e.g: MM/dd/yyyy)
     * @param  days of (int), number to get date E.g. 1:Tomorrow date, -1: Yesterday date
     */
    public String getFutureDateTime(String dateTimeFormat, int days) throws Throwable {
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        Date tomorrow = calendar.getTime();
        return sdf.format(tomorrow);
    }

    /**
     * assertTextStringContains, Assert text string matching.
     * @param actText of (String)
     * @param expText of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean assertTextStringContains(String actText, String expText) throws Throwable {
        boolean flag = false;
        try {
            // added loggers
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            String ActualText = actText.trim();
            LOG.info("act - " + ActualText);
            LOG.info("exp - " + expText);
            if (ActualText.contains(expText.trim())) {
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
                reporter.failureReport("Verify : " + expText, actText + " is not present in the element : ",driver);
                return false;
            } else if (flag) {
                reporter.SuccessReport("Verify : " + expText, actText + " is  present in the element : ");
            }
        }
    }

    /**
     * deleteDirectory, Delete directory from local machine
     *
     * @param directoryPath of (String),  path for the directory to delete
     * @return void
     * @throws IOException
     */
    public void deleteDirectory(String directoryPath) throws IOException {
        FileUtils.deleteDirectory(new File(directoryPath));
    }

    /**
     * getRandomString, Get random String
     *
     * @param noOfCharacters of (int), Number of characters to get randomly
     * @return String
     * @throws IOException
     */
    public String getRandomString(int noOfCharacters) throws IOException {
        return RandomStringUtils.randomAlphabetic(noOfCharacters);
    }

    /**
     * getRandomNumeric, Get random Numeric
     *
     * @param  noOfCharacters of (int),  Number of characters to get randomly
     * @return String
     * @throws IOException
     */
    public String getRandomNumeric(int noOfCharacters) throws IOException {
        return RandomStringUtils.randomNumeric(noOfCharacters);
    }

    /**
     * getAttributeValue, Function to get the value of a given attribute (e.g. class)
     * @return : String
     * @param  locator of (By)
     * @param  attributeName of (String)
     */
    public String getAttributeValue(By locator, String attributeName) {
        String result = "";
        try {
            result = this.driver.findElement(locator).getAttribute(attributeName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * refreshPage
     * @return void
     * @throws Throwable
     */
    public void refreshPage() throws Throwable {
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName());
            driver.navigate().refresh();
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            LOG.info("++++++++++++++++++++++++++++Catch Block Start+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            e.printStackTrace();
            LOG.info("++++++++++++++++++++++++++++Catch Block End+++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    /**
     * clearData, Clear value from textBox
     * @param  locator of (By)
     * @return void
     * @throws Throwable
     */
    public void clearData(By locator) throws Throwable {
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName());
            WebElement element = driver.findElement(locator);
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);
            element.clear();
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            LOG.info("++++++++++++++++++++++++++++Catch Block Start+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            e.printStackTrace();
            LOG.info("++++++++++++++++++++++++++++Catch Block End+++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    /**
     * keyBoardOperations
     * @param  locator of (By)
     * @param  testData of (Keys)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable
     */
    public boolean keyBoardOperations(By locator, Keys testData, String locatorName) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : Type  ::  Locator : " + locatorName + " :: Data :" + testData);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            LOG.info("Waiting for element :");
            //wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            LOG.info("Locator is Visible :: " + locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).sendKeys(testData);
            LOG.info("Typed the Locator data :: " + testData);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            reporter.SuccessReport("Enter text in :: " + locatorName, msgTypeSuccess + testData);
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            reporter.failureReport("Enter text in :: " + locatorName, msgTypeFailure + testData, driver);
            throw new RuntimeException(e);
        }
        return status;
    }

    /**
     * keyBoardOperations
     * @param  locator of (By)
     * @param  testData of (String)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable
     */
    public boolean typeWitoutReport(By locator, String testData, String locatorName) throws Throwable {
        boolean status = false;
  	  /*try {*/
        //Waittime();
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
        LOG.info("Method : Type  ::  Locator : " + locatorName + " :: Data :" + testData);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        LOG.info("Waiting for element :");
        driver.findElement(locator).click();
        LOG.info("Clicked on the Locator : ");
        driver.findElement(locator).clear();
        LOG.info("Cleared the existing Locator data : ");
        driver.findElement(locator).sendKeys(testData);
        LOG.info("Typed the Locator data :: " + testData);
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        status = true;
  	  /*} catch (Exception e) {
  	   status = false;
  	   LOG.info(e.getMessage());
  	  }*/
        return status;
    }

    /**
     * Switch to frame using index value
     *
     * @param  index of (int), frame number to switch
     * @return void
     */
    public void switchToFrameByIndex(int index) {
        driver.switchTo().frame(index);
    }

    /**
     * come out from frame
     * @return void
     */
    public void comeOutFromFrame() {
        driver.switchTo().defaultContent();
    }

    /**
     * Click on OK button on alert
     *
     * @return void
     */
    public void acceptAlert() {
        try {
        	
        	WebDriverWait wait = new WebDriverWait(driver, 5);
        	 wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Click on OK button on alert
     *
     * @return void
     */
    public String getAlertText() {
    	String alertText="";
        try {
        	
        	WebDriverWait wait = new WebDriverWait(driver, 5);
        	 wait.until(ExpectedConditions.alertIsPresent());
        	 Alert alert = driver.switchTo().alert();
        	 alertText = alert.getText();
        	 System.out.println("ALertText"+alertText);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alertText;
    }

    /**
     * findWebElement
     * @param  locator of (By)
     * @param  locatorName of (String)
     * @return WebElement
     * @throws Throwable
     */
    public WebElement findWebElement(By locator, String locatorName) throws Throwable {
        WebElement element;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : click  ::  Locator : " + locatorName);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            LOG.info("Waiting for element");
            LOG.info("Locator is Visible :: " + locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            LOG.info("Clicked on the Locator");
            element = driver.findElement(locator);
            LOG.info("identified the element :: " + locator);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            LOG.info(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return element;
    }

    /**
     * checkBoxIsChecked
     * @param  by of (By)
     * @param  locatorName of (String)
     * @param  expected of (boolean)
     * @return boolean
     * @throws Throwable
     */
    public boolean checkBoxIsChecked(By by, String locatorName, boolean expected) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            waitTime();
            driver.findElement(by).isSelected();
            this.reporter.SuccessReport("checkBoxIsChecked : " + locatorName,
                    this.msgIsElementFoundSuccess + locatorName);
            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());

        } finally {
            if (!status) {
                if (reportIndicator) {
                    LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					reporter.failureReport("checkBoxIsChecked : ", msgCheckboxisnotChecked + locatorName, driver);
                }
            } else {
                LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                reporter.SuccessReport("checkBoxIsChecked : ", locatorName + ", checkBoxIsChecked : true");
            }
            reportIndicator = true;
       }
        return status;
    }


    /**
     * switchToWindow, Function to switch to latest window
     * @return : void
     */
    public void switchToWindow() {
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    }

    /**
     * switchToParentWindow, Function to switch to parent window
     * @return  void
     * @param  handle of (String), window handle to switch
     */
    public void switchToParentWindow(String handle) {
        driver.switchTo().window(handle);
    }

    /**
     * closeWindow, Function to close the currently focused window
     * @return : void
     */
    public void closeWindow() {
        driver.close();
    }

    /**
     * getWindowHandle, Function to get the current window handle
     * @return : String
     */
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    /**
     * scrollToWebElement, Function to scroll to a particular element
     * @param  element of (By)
     * @return : void
     */
    public void scrollToWebElement(By element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(element));
    }
    
    public void deleteSpecificFile(String fileName) throws InterruptedException{
    	try {
    		File file = new File(fileName);
        	if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * findWebElementVisibility, Function returns WebElement
     * @return : WebElement
     */

    public WebElement findWebElementVisibility(By locator, String locatorName) throws Throwable {
        WebElement element;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : click  ::  Locator : " + locatorName);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            LOG.info("Waiting for element");
            LOG.info("Locator is Visible :: " + locator);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            LOG.info("Element Found on the Locator");
            element = driver.findElement(locator);
            LOG.info("identified the element :: " + locator);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            LOG.info(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return element;
    }
    
    public boolean isCheckBoxSelected(By locator){
    	boolean flag = false;
    	flag = driver.findElement(locator).isSelected();
    	return flag;
    }
    /**
     * isVisible
     * @param  locator of (By)
     * @param  locatorName of (String)
     * @return boolean
     * @throws Throwable the throwable
     */
    public boolean isVisibleOnly(By locator, String locatorName) throws Throwable {
        boolean flag = false;
        try {
            //added loggers
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name :: " + getCallerClassName() + " Method name :: " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
            //value = driver.findElement(locator).isDisplayed();
            flag = driver.findElement(locator).isDisplayed();
            //value = true;
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            flag = false;

        }
        return flag;
    }
    
	/**
	 * This function wait until element Not visible
	 * 
	 * @return
	 * @throws Throwable
	 */

	public boolean waitForInVisibilityOfElement(By by, String locatorName) throws Throwable {
		boolean flag = false;

		WebDriverWait wait = new WebDriverWait(driver, 90);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				reporter.failureReport("InVisible of element is false :: ",
						"Element :: " + locatorName + " is not visible", driver);
			} else if (flag) {
				reporter.SuccessReport("InVisible of element is true :: ",
						"Element :: " + locatorName + "  is visible");
			}
		}
	}
	
	public void sync() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void verifyElementClassValue(By by, String value, String locatorName) throws Throwable
	{
		boolean flag = false;
		WebDriverWait wait = new WebDriverWait(driver, 90);
		try {
			String elementClassValue = driver.findElement(by).getAttribute("class");
			assertText(elementClassValue, value);
			flag = true;			
		} catch (Exception e) {
			e.printStackTrace();			
		
	}
	
	}
	

	public boolean assertSelectedItem(By by, String text) throws Throwable {
        boolean flag = false;
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
        try {
	        Select select = new Select((WebElement) driver.findElement(by));
	        WebElement option = select.getFirstSelectedOption();	        
            Assert.assertEquals(option.getText().trim(), text.trim());
            flag = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (!flag) {
                reporter.failureReport("AssertText :: ", text + " is not present in the element : ");
                return false;
            } else {
                reporter.SuccessReport("AssertText :: ", text + " is  present in the element : ");
            }
        }
    }
	
	public boolean assertDisable(By by, Boolean bool) throws Throwable {
        boolean flag = false;
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
        try {        	
        	String disabledProp = driver.findElement(by).getAttribute("disabled");
        	Boolean actual = disabledProp==null?false:true;
            Assert.assertEquals(actual, bool);            
            flag = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (!flag) {
                reporter.failureReport("assertDisable :: ", " element is disabled : ");
                return false;
            } else {
                reporter.SuccessReport("assertDisable :: ", " element is disabled : ");
            }
        }
    }


    public String getColorOfWebElement(By by) throws Throwable {
        boolean flag = false;
        String elementClassValue = null;
        try {
            elementClassValue = driver.findElement(by).getCssValue("color");
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!flag) {
                reporter.failureReport("assertDisable :: ", " element is disabled : ");
                return elementClassValue;
            } else {
                reporter.SuccessReport("assertDisable :: ", " element is disabled : ");
            }
        }
        return elementClassValue;
    }

    public boolean dragAndDropSliderHandle(By by, int X, int Y, String locatorName) throws Throwable {
        boolean status = false;
        try {
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
            LOG.info("Method : " + getCallerMethodName());
            scrollToWebElement(by);
            WebElement element = this.driver.findElement(by);

            Actions actions = new Actions(this.driver);
            actions.clickAndHold(element);
            //actions.clickAndHold(element);
            actions.moveByOffset(X,Y);
            actions.release();
            actions.build().perform();
            LOG.info("Scroll is performed : " + locatorName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}