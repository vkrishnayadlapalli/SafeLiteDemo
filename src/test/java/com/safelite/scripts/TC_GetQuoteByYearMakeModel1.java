package com.safelite.scripts;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.safelite.libs.QuoteLib;
import com.safelite.utilities.TestUtil;

/**
 * TC_GetQuote
 * @Description This class holds all the scripts related to Find Store Using ZipCode
 * @author Cigniti Technologies
 * @LastModifiedDate 05 June 2017
 */
public class TC_GetQuoteByYearMakeModel1 extends QuoteLib {

	//Reads data from the test data sheet.
	@DataProvider
	public Object[][] getTestDataFor_getQuoteByYearMakeModelAndStyle() {
		return TestUtil.getData("getQuoteByYearMakeModelAndStyle", TestData, "GetQuote");
	}
	
	//loops the test script through the no. of rows in test data.
	@Test(dataProvider = "getTestDataFor_getQuoteByYearMakeModelAndStyle", groups = {"smoke","regression"})
	public void getQuoteByYearMakeModelAndStyle(Hashtable<String, String> data) throws Throwable {
		//Initiate the test description on the HTML report.
		this.reporter.initTestCaseDescription(data.get("TestCase_Description"));
		try {
			performGetMyQuote();	//Performs Get my quote
			performVehicleGuideSelection(data.get("Year"), data.get("Make"), data.get("Model"));//Selects Year, Make and Model of the vehicle
			selectDamagedGlassParts();	//Selects damaged parts of the vehicle
			handleQuestionsAboutVehicle();	//Provides answers about the vehicle
			getYourEstimate(data.get("ZipCode")); //performs the get your estimate
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}