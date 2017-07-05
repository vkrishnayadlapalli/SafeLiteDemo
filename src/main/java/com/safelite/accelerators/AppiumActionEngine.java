package com.safelite.accelerators;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.safelite.report.ReporterConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import com.crunchtime.mobile.libs.TeamworxMobileLib;

public class AppiumActionEngine extends TestEngineWeb {
	private static final Logger LOG = Logger.getLogger(AppiumActionEngine.class);

	private final String msgClickSuccess = "Successfully Clicked On ";
	private final String msgClickFailure = "Unable To Click On ";
	private final String msgTypeSuccess = "Successfully Entered Value ";
	private final String msgTypeFailure = "Unable To Type On ";
	private final String msgIsElementFoundSuccess = "Successfully Found Element ";
	private final String msgIsElementFoundFailure = "Unable To Found Element ";

	/**
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Throwable
	 */
	public boolean click(By locator, String locatorName) throws Throwable {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(this.appiumDriver, 400);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			this.appiumDriver.findElement(locator).click();
			//		if(TeamworxMobileLib.resultFlag){
			this.reporter.SuccessReport("Click :" + locatorName, this.msgClickSuccess + locatorName);
			//		}
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
			this.reporter.failureReport("Click", this.msgClickFailure + locatorName, this.appiumDriver);
		}
		return status;
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
			this.appiumDriver.findElement(by);
			this.reporter.SuccessReport("isElementPresent", this.msgIsElementFoundSuccess + locatorName);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
			if (expected == status) {
				this.reporter.SuccessReport("isElementPresent", "isElementPresent");
			} else {
				this.reporter.failureReport("isElementPresent", this.msgIsElementFoundFailure + locatorName, this.appiumDriver);
			}
		}
		return status;
	}

	public boolean type(By locator, String testdata, String locatorName) throws Throwable {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(this.appiumDriver, 80);
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

			WebDriverWait wait = new WebDriverWait(this.appiumDriver, 60);
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
}
