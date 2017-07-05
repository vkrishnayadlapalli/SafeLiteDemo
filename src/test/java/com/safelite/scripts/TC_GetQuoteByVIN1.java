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
public class TC_GetQuoteByVIN1 extends QuoteLib {

	//Reads data from the test data sheet.
	@DataProvider
	public Object[][] getTestDataFor_GetQuoteByVIN() {
		return TestUtil.getData("GetQuoteByVIN", TestData, "GetQuote");
	}
	
	//loops the test script through the no. of rows in test data.
	@Test(dataProvider = "getTestDataFor_GetQuoteByVIN")
	public void GetQuoteByVIN(Hashtable<String, String> data) throws Throwable {

		//Initiate the test description on the HTML report.
		this.reporter.initTestCaseDescription(data.get("TestCase_Description"));
		
		try {
			
			performGetMyQuote();
			
			performVINSearch(data.get("VINNumber"));
			
			selectDamagedGlassParts();
			
			handleVINQuestionsAboutVehicle();
			
			getYourEstimate(data.get("ZipCode"));

		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}