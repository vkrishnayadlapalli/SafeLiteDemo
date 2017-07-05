package com.safelite.commonutilities;

import org.openqa.selenium.By;

import com.safelite.accelerators.ActionEngineMobile;
import com.safelite.report.ConfigFileReadWrite;
import com.safelite.report.ReporterConstants;

public class mobileUtilities extends ActionEngineMobile {
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

	static {

		username = By.cssSelector("#username");
		password = By.cssSelector("#password");
		login = By.cssSelector("#submitLogin");
		userDropDown = By.cssSelector("#headerUserName");
		logout = By.cssSelector("#logOutDiv");
		ncusername = By.xpath("//*[@id='username']");
		Emlogout = By.xpath("(//img[@class='nav-img'])[4]");
		NClogout = By.xpath("//a[@title='Logout']/img");
	}

	
	
	

}


