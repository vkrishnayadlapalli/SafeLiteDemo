package com.safelite.libs;
import com.safelite.commonlib.CommonLib;
import com.safelite.quotepage.QuotePage;
import com.thoughtworks.selenium.webdriven.commands.Click;

public class QuoteLib extends CommonLib{

	/**
	 * @MethodName clickGetMyQuote
	 * @Description This method is used to click Get My Quote button
	 * @author Cigniti Technologies
	 * @param NA
	 * @return void
	 * @throws Throwable the throwable
	 * @LastModifiedDate 05 June 2017
	 **/	
	public void performGetMyQuote() throws Throwable {
		driver.manage().deleteAllCookies(); //Kills all browser cookies
		waitForVisibilityOfElement(QuotePage.btnGetMyQuote, "Get my quote button");
		JSClick(QuotePage.btnGetMyQuote, "Get my quote button");
		
		if(isElementPresent(QuotePage.btnGetMyQuote, "Get my quote button")){
			JSClick(QuotePage.btnGetMyQuote, "Get my quote button");
		}
	}
	
	/**
	 * @MethodName performVehicleGuideSelection
	 * @Description This method is used to select year, make and Model
	 * @author Cigniti Technologies
	 * @param String strYear, String strMake, String strModel
	 * @return void
	 * @throws Throwable the throwable
	 * @LastModifiedDate 05 June 2017
	 **/
	public void performVehicleGuideSelection(String strYear, String strMake, String strModel) throws Throwable {
		try{
			waitForVisibilityOfElement(QuotePage.lstYear, "Vehicle Year"); //waits for year drop down.
			selectByValue(QuotePage.lstYear, strYear, "Vehicle Year"); //selects year value form drop down.
			selectByValue(QuotePage.lstMake, strMake, "Vehicle Make"); //selects make value form drop down.
			selectByValue(QuotePage.lstModel, strModel, "Vehicle Model"); //selects model value form drop down.
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	/**
	 * @MethodName selectDamagedGlassParts
	 * @Description This method is used to select damaged glass parts
	 * @author Cigniti Technologies
	 * @param NA
	 * @return void
	 * @throws Throwable the throwable
	 * @LastModifiedDate 05 June 2017
	 **/
	public void selectDamagedGlassParts() throws Throwable{
		//WindShield
			Thread.sleep(8000);
			click(QuotePage.chkWindsheeld, "Windshield");
			waitForVisibilityOfElement(QuotePage.radiobtnReplace, "Windshield - Replace");
			JSClick(QuotePage.radiobtnReplace, "Windshield - Replace");
			
		//Driver's Side
			click(QuotePage.chkDriversSide, "Driver's Side");		
			click(QuotePage.chkDriverSideFrontDoor, "Driver's Side - Front Door");
			click(QuotePage.chkDriverSideQuarterpanel, "Driver's Side - Quarter panel");
			Thread.sleep(3000);
		//Passenger's Side
			click(QuotePage.chkPassengersside, "Passenger's Side");
			click(QuotePage.chkPassengerSideFrontDoor, "Passenger's Side - Front Door");
			click(QuotePage.chkPassengerSideQuarterpanel, "Passenger's Side - Quarter panel");
			Thread.sleep(3000);
		click(QuotePage.btnContinue, "Continue button");
	}
	
	/**
	 * @MethodName handleQuestionsAboutVehicle
	 * @Description This method is used to select questions about your vehicle
	 * @author Cigniti Technologies
	 * @param NA
	 * @return void
	 * @throws Throwable the throwable
	 * @LastModifiedDate 05 June 2017
	 **/
	public void handleQuestionsAboutVehicle() throws Throwable{
		
		waitForVisibilityOfElement(QuotePage.radioBtnWinshield, "Windshield - Replace");
		
		click(QuotePage.radioBtnWinshield, "Windshield - Replace");
		
		//click(QuotePage.btnContinue, "Continue button");
		
		click(QuotePage.radiobtnLaneDeparture, "Lane Departure option");
		
		click(QuotePage.radiobtnHeadsUpDisplay, "Heads Up display option");
		
		click(QuotePage.radiobtnLightSensor, "Light sensor option");
			
		click(QuotePage.btnContinue, "Continue button");
	}
	
	/**
	 * @MethodName handleQuestionsAboutVehicle
	 * @Description This method is used to select questions about your vehicle
	 * @author Cigniti Technologies
	 * @param NA
	 * @return void
	 * @throws Throwable the throwable
	 * @LastModifiedDate 05 June 2017
	 **/
	public void handleVINQuestionsAboutVehicle() throws Throwable{
		
		waitForVisibilityOfElement(QuotePage.radioBtnWinshield, "Windshield - Replace");
		
		click(QuotePage.radioBtnWinshield, "Windshield - Replace");
		
		click(QuotePage.btnContinue, "Continue button");
		
		click(QuotePage.radiobtnCurrentDriver, "Select your current driver rear quarter glass color");
		
		click(QuotePage.radiobtnCurrentPassenger, "Select your current passenger rear quarter glass color");
		
		click(QuotePage.btnContinue, "Continue button");
	}
	
	/**
	 * @MethodName getYourEstimate
	 * @Description This method is used to get estimate bassed on the location 
	 * @author Cigniti Technologies
	 * @param NA
	 * @return void
	 * @throws Throwable the throwable
	 * @LastModifiedDate 05 June 2017
	 **/
	public void getYourEstimate(String strZipCode) throws Throwable{
		
		type(QuotePage.txtZip, strZipCode, "Zip Code");
		
		click(QuotePage.btnFind, "Find Zip Code");
		
		waitForVisibilityOfElement(QuotePage.radioBtnServiceInShop, "You come to our store");
		
		click(QuotePage.radioBtnServiceInShop, "You come to our store");
		
		click(QuotePage.radioBtnPayByCash, "Pay on your own");
		
		click(QuotePage.btnGetEstimate, "GetEstimate button");
		
		if(isElementPresent(QuotePage.lblQuotePrice, "Your Service Estimate*")){
			getText(QuotePage.lblQuotePrice, "Your Service Estimate*");
		}else{
			this.reporter.failureReport("Your Service Estimate", "Your Service Estimate is not displayed.", this.WebDriver);
		}
	}
	
	/**
	 * @MethodName performVINSearch
	 * @Description This method is used to perform search with VIN
	 * @author Cigniti Technologies
	 * @param String strYear, String strMake, String strModel
	 * @return void
	 * @throws Throwable the throwable
	 * @LastModifiedDate 05 June 2017
	 **/
	public void performVINSearch(String strVIN) throws Throwable {
		try{
			waitForVisibilityOfElement(QuotePage.lblVIN, "Enter your VIN");
			click(QuotePage.lblVIN, "VIN tab");
			waitForVisibilityOfElement(QuotePage.txtSearchForVIN, "Enter your VIN");
			type(QuotePage.txtSearchForVIN, strVIN, "Enter your VIN");
			click(QuotePage.btnVerifyVIN, "Verify VIN button");
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
}
