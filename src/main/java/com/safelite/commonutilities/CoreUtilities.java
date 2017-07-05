package com.safelite.commonutilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.safelite.accelerators.ActionEngine;
import com.safelite.accelerators.TestEngineWeb;
import com.safelite.report.ConfigFileReadWrite;
import com.safelite.report.ReporterConstants;

import org.apache.log4j.Logger;

public class CoreUtilities extends ActionEngine {
	public final Logger LOG = Logger.getLogger(TestEngineWeb.class);
	String APP_BASE_URL = ConfigFileReadWrite.read(
			ReporterConstants.configReporterFile, "baseUrl");

	public static By username;
	public static By password;
	public static By login;
	public static By selectLocation;
	public static By userDropDown;
	public static By logout;
	public static By ncusername;
	public static By Emlogout;
	public static By NClogout;
	public static By error404;
	public static By error403;

	static {

		username = By.cssSelector("#username");
		password = By.cssSelector("#password");
		login = By.cssSelector("#submitLogin");
		userDropDown = By.cssSelector("#headerUserName");
		logout = By.cssSelector("#logOutDiv");
		ncusername = By.xpath("//*[@id='username']");
		Emlogout = By.xpath("(//img[@class='nav-img'])[4]");
		NClogout = By.xpath("//a[@title='Logout']/img");
		error404 = By.xpath("//p[contains(text(),'404')]");
		error403 = By.xpath("//p[contains(text(),'403')]");
	}

	/**
	 * doLogin
	 *
	 * @throws Throwable the throwable
	 */
	public void doLogin(String UserName, String Password) throws Throwable {
		if (isElementPresent(CoreUtilities.login, "login")) {

			this.reporter.SuccessReport("Launch TeamWorx", "TeamWorx WebPage launch sucesfully");
			type(CoreUtilities.username, UserName, "usename");
			type(CoreUtilities.password, Password, "Password");
			click(CoreUtilities.login, "login");
		} else {
			this.reporter.failureReport("Launch TeamWorx", "Failed to launch Teamworx web page", driver);
			throw new RuntimeException();
		}
	}

	/**
	 * twxlogout
	 *
	 * @throws Throwable the throwable
	 */
	public void logout() throws Throwable {

		JSClick(CoreUtilities.userDropDown, "User Dropdown");
		JSClick(CoreUtilities.logout, "Logout");
	}

	/**
	 * selectLocation
	 *
	 * @throws Throwable the throwable
	 */

	public void selectLocation(String value) throws Throwable {
		By valuidentifier = By.xpath("//table[@id='locationsTable']//div//span[text()='" + value + "']/..");
		JSClick(valuidentifier, "Location ");
	}

	/**
	 * Verifylogout
	 *
	 * @throws Throwable the throwable
	 */
	public void verifylogout() throws Throwable {
		if (!isElementPresent(CoreUtilities.login, "login")) {
			int a = getElementsSize(CoreUtilities.userDropDown);
			// boolean falg=isElementPresent(CoreLoginLogout.userDropDown, "User Dropdown");
			if (a == 0) {

				//backuntilLogoutbutton();
				logout();
			} else {
				logout();
			}
		}
	}

	/**
	 * Verifylogout
	 *
	 * @throws Throwable the throwable
	 */
	public void backuntilLogoutbutton(org.openqa.selenium.WebDriver driver) throws Throwable {
		String Url = driver.getCurrentUrl();
			if (!(Url.contains("em.nc")) && !(Url.contains(".nc"))) {
				if((isElementPresent(CoreUtilities.error404,"404 Not found") || isElementPresent(CoreUtilities.error403,"403 Not found"))){
	
				List<WebElement> flag1 = driver.findElements(CoreUtilities.login);
				int a = flag1.size();
	
				LOG.info("this elimemt size    : " + a);
	
				if (a == 0) {
					List<WebElement> flag2 = driver.findElements(CoreUtilities.userDropDown);
					int b = flag2.size();
					System.out.println("this elimemt size b: " + b);
					boolean flag3 = isElementPresent(driver, CoreUtilities.userDropDown, "userDropDown");
					if (!flag3) {
	
						for (int i = 0; i < 10; i++) {
							System.out.println("Inside for loop  : " + i);
	
							driver.navigate().back();
							boolean flag4 = isElementPresent(driver, CoreUtilities.userDropDown, "userDropDown");
	
							if (flag4) {
								break;
							}
						}
						
						click(CoreUtilities.userDropDown, "User Dropdown");
						click(CoreUtilities.logout, "Logout");
					} else {
						click(CoreUtilities.userDropDown, "User Dropdown");
						click(CoreUtilities.logout, "Logout");
					}
				}
			
					
					}else 
						LOG.info(" PAGE NOT FOUND");
			}
	
			
			
	}


	public boolean isElementPresent(org.openqa.selenium.WebDriver driver, By by, String locatorName) throws Throwable {
		boolean status = false;
		try {

			dynamicWait(by);
			driver.findElement(by);
			status = true;
		} catch (Exception e) {
			status = false;

			//throw new RuntimeException(e);
		}
		return status;
	}
}

