package com.safelite.quotepage;

import org.openqa.selenium.By;

public class QuotePage1 {
	public static By lblQuotePrice;
	public static By txtZip;
	public static By radioBtnServiceInShop;
	public static By radioBtnPayByCash;
	public static By btnGetEstimate;
	public static By radioBtnWinshield;
	public static By btnGetMyQuote;
	public static By lstYear;
	public static By eleYear;
	public static By lstMake;
	public static By eleMake;
	public static By lstModel;
	public static By eleModel;
	public static By chkWindsheeld;
	public static By radiobtnReplace;
	public static By chkDriversSide;
	public static By chkDriverSideFrontDoor;
	public static By chkDriverSideQuarterpanel;
	public static By chkPassengersside;
	public static By chkPassengerSideFrontDoor;
	public static By chkPassengerSideQuarterpanel;
	public static By btnContinue;
	public static By radiobtnLaneDeparture;
	public static By radiobtnHeadsUpDisplay;
	public static By radiobtnLightSensor;
	public static By btnFind;
	public static By lblVIN;
	public static By txtSearchForVIN;
	public static By btnVerifyVIN;
	public static By radiobtnCurrentDriver;
	public static By radiobtnCurrentPassenger;
	
	static {
		btnGetMyQuote = By.xpath(".//button[contains(text(),'Get my quote')]");
		lstYear = By.id("vehYear");
		eleYear = By.xpath(".//*[@id='vehYear']/option[contains(@value,'2016')]");
		lstMake = By.id("vehMake");
		eleMake = By.xpath(".//*[@id='vehMake']/option[contains(@value,'BMW')]");
		lstModel = By.id("vehModel");
		eleModel = By.xpath(".//*[@id='vehModel']/option[contains(@value,'428I')]");
		chkWindsheeld = By.xpath(".//*[@id='primaryContent']//label[contains(.,'Windshield')]/input");
		//radiobtnReplace = By.xpath(".//*[@id='primaryContent']//label[contains(.,'Windshield')]//following-sibling::ul//input[contains(@value,'replace')]");
		radiobtnReplace = By.xpath(".//input[contains(@value,'replace')]");
		//chkDriversSide = By.xpath(".//*[@id='primaryContent']//label[contains(.,'Driver')]/input");
		chkDriversSide = By.xpath(".//input[@data-bind='checked: selectedDriverSideGlassCategory']");
		chkDriverSideFrontDoor = By.xpath(".//*[@id='primaryContent']//label[contains(.,'Driver')]//following-sibling::ul//label[contains(.,'Front door')]/input");
		chkDriverSideQuarterpanel = By.xpath(".//*[@id='primaryContent']//label[contains(.,'Driver')]//following-sibling::ul//label[contains(.,'Quarter panel')]/input");
		chkPassengersside = By.xpath(".//input[@data-bind='checked: selectedPassengerSideGlassCategory']");
		chkPassengerSideFrontDoor = By.xpath(".//*[@id='primaryContent']//label[contains(.,\"Passenger's side\")]//following-sibling::ul//label[contains(.,'Front door')]/input");
		chkPassengerSideQuarterpanel = By.xpath(".//*[@id='primaryContent']//label[contains(.,\"Passenger's side\")]//following-sibling::ul//label[contains(.,'Quarter panel')]/input");
		btnContinue = By.xpath(".//*[@id='pageNavigation']//button[text()='Continue']");
		radiobtnLaneDeparture = By.xpath(".//*[@id='primaryContent']//label[contains(.,'Lane Departure Warning')]//following-sibling::ul//input[contains(@value,'Y')]");
		radiobtnHeadsUpDisplay = By.xpath(".//*[@id='primaryContent']//label[contains(.,'Heads Up Display')]//following-sibling::ul//input[contains(@value,'Y')]");
		radiobtnLightSensor = By.xpath(".//*[@id='primaryContent']//label[contains(.,'light sensor')]//following-sibling::ul//input[contains(@value,'Y')]");
		btnFind = By.xpath(".//button[text()='Find']");
		radioBtnServiceInShop = By.id("ServiceInShop");
		txtSearchForVIN = By.id("vinSpsLookup");
		btnVerifyVIN = By.xpath(".//*[@id='primaryContent']//button[contains(.,'Verify')]");
		lblVIN = By.xpath(".//*[@id='primaryContent']//label[contains(.,'VIN')]");
		lblQuotePrice = By.xpath("//div[@class='quotePrice']//h4/span[1]");
		radiobtnCurrentDriver = By.id("driverrearquarterglass-greentint");
		radiobtnCurrentPassenger = By.id("passengerrearquarterglass-greentint");
		txtZip = By.id("txtZip");
		radioBtnPayByCash = By.id("PayTypeCash");
		btnGetEstimate = By.id("navNext");
		radioBtnWinshield = By.xpath("//input[@value='1-Y']");
	}
}