package com.safelite.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateXMLForMobile {

	static FileWriter fos;
	public static Xls_Reader xls = new Xls_Reader(
			System.getProperty("user.dir") + File.separator + "TestData" + File.separator + UpdateTestDataFileForMobile.getDataFile() + ".xlsx");
	static final String browserConfigSheetName = "browserConfiguration";
	/*static final String allTestsSheetName = "allTests";*/
	//static final String allTestsSheetName = "Sprint 1";

	synchronized public static void createXml(String sheetName, String AppCategory,String ExecutionFlag,String SprintName,String Android_Device,String seleniumGridURL) throws IOException {

		try {
			System.out.println("Test Data Sheet " + xls.path + xls.filename + System.getProperty("user.dir"));
			String outputFileName = System.getProperty("user.dir") + File.separator + "testng.xml";

			if (new File(outputFileName).exists()) {
				System.out.println("Test suite exists in" + outputFileName);
				new File(outputFileName).delete();
			}

			fos = new FileWriter(outputFileName);
			fos.write("<?xml version='1.0' encoding='UTF-8'?>\n");
			fos.write("<!DOCTYPE suite SYSTEM 'http://testng.org/testng-1.0.dtd'>\n");
			if (ExecutionFlag.equalsIgnoreCase("Sequential")) {
				fos.write("<suite name='CrunchTime Automation test suite' configfailurepolicy='continue'>\n\n");

				fos.write("<parameter name='suiteExecuted' value='Regression' />\n");
				fos.write("<parameter name='executionType' value='Sequential' />\n");
			} else if (ExecutionFlag.equalsIgnoreCase("Parallel")) {
				fos.write("<suite name='CrunchTime Automation test suite' configfailurepolicy='continue' parallel='tests' thread-count='3'>\n\n");
				fos.write("<parameter name='suiteExecuted' value='Regression' />\n");
				fos.write("<parameter name='executionType' value='Parallel' />\n");
			}
			fos.write("<listeners>\n");
			fos.write("\t<listener class-name=\"org.uncommons.reportng.HTMLReporter\" />\n");
			fos.write("\t<listener class-name=\"org.uncommons.reportng.JUnitXMLReporter\" />\n");
			fos.write("\t<listener class-name=\"com.crunchtime.utilities.AssignTestPriorityTransformer\" />\n");

			fos.write("</listeners>\n");

			if(AppCategory.equalsIgnoreCase("Native")){
				
			int startRow = 2;

			for (int rNum = startRow; rNum <= xls.getRowCount(sheetName); rNum++) {

				String MobileDevice = xls.getCellData(sheetName, "DeviceType", rNum);
				if ((!MobileDevice.isEmpty()) && (MobileDevice.contains("Android"))  && (xls.getCellData(sheetName, "RunMode", rNum).equalsIgnoreCase("Y"))) {

					fos.write("\t<test name=\"" + MobileDevice + "\" preserve-order='true'>\n");

					HashMap<String, String> browseConfigData = getBrowserDetails(browserConfigSheetName, MobileDevice);
					fos.write("\t\t\t<parameter name=\"browserName\" value=\"" + browseConfigData.get("browser")
							+ "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"automationName\" value=\""
							+ browseConfigData.get("automationName") + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"browserVersion\" value=\""
							+ browseConfigData.get("browserVersion") + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"platformName\" value=\"" + browseConfigData.get("platformName")
							+ "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"environment\" value=\"" + browseConfigData.get("environment")
							+ "\"></parameter>\n");
					
					fos.write("\t\t\t<parameter name=\"app\" value=\"" + browseConfigData.get("App")
					+ "\"></parameter>\n");
			
					fos.write("\t\t\t<parameter name=\"appPackage\" value=\"" + browseConfigData.get("appPackage")
					+ "\"></parameter>\n");
					
					fos.write("\t\t\t<parameter name=\"appActivity\" value=\"" + browseConfigData.get("appActivity")
					+ "\"></parameter>\n");
					
					fos.write("\t\t\t<parameter name=\"platformVersion\" value=\"" + browseConfigData.get("platformVersion")
					+ "\"></parameter>\n");
					
					fos.write("\t\t\t<parameter name=\"appiumUrl\" value=\"" + browseConfigData.get("appiumURL")
					+ "\"></parameter>\n");
					
					fos.write("\t\t\t<parameter name=\"deviceName\" value=\"" + xls.getCellData(sheetName, "DeviceName", rNum) + "\"></parameter>\n");
					
					/*//if(!gridURL.isEmpty()){
						fos.write("\t\t\t<parameter name=\"seleniumgridurl\" value=\""+ SeleniumGridURL + "\"></parameter>\n");
					//}
					*/
					if (seleniumGridURL.isEmpty() || seleniumGridURL.equalsIgnoreCase("local")) {
						fos.write("\t\t\t<parameter name=\"seleniumgridurl\" value=\"local\"></parameter>\n");
					} else {
						fos.write("\t\t\t<parameter name=\"seleniumgridurl\" value=\"http://"+ seleniumGridURL + ":4444/wd/hub\"></parameter>\n");
					}
					fos.write("\t\t <classes>\n");

					List<String> allClasses = getClasses(SprintName, MobileDevice);
					System.out.println("The size of all classes " + allClasses.size());

					for (int k = 0; k < allClasses.size(); k++) {

						System.out.println("class ->" + allClasses.get(k));
						fos.write("\t\t\t<class name=\"" + allClasses.get(k) + "\">\n");
						List<String> allMethods = getMethods(SprintName, allClasses.get(k));
						fos.write("\t\t\t\t<methods>\n");
						String userrole = getuserrole(SprintName, allClasses.get(k));
						if (!userrole.isEmpty())
							fos.write("\t\t\t<parameter name=\"userrole\" value=\"" + userrole + "\"></parameter>\n");
						for (int l = 0; l < allMethods.size(); l++) {
							System.out.println("\t method ->" + allMethods.get(l));
							fos.write("\t\t\t\t\t <include name=\"" + allMethods.get(l) + "\" />\n");
						}
						fos.write("\t\t\t\t</methods>\n");

						fos.write("\t\t\t </class>\n");
					}
					fos.write("\t\t </classes>\n");
					fos.write("\t</test>\n");
					fos.write("\n");
				}else if ((!MobileDevice.isEmpty()) && (MobileDevice.contains("iOS"))  && (xls.getCellData(sheetName, "RunMode", rNum).equalsIgnoreCase("Y"))) {

					fos.write("\t<test name=\"" + MobileDevice + "\" preserve-order='true'>\n");

					HashMap<String, String> browseConfigData = getBrowserDetails(browserConfigSheetName, MobileDevice);
					fos.write("\t\t\t<parameter name=\"browser\" value=\"" + browseConfigData.get("browser")
							+ "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"automationName\" value=\""
							+ browseConfigData.get("automationName") + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"browserVersion\" value=\""
							+ browseConfigData.get("browserVersion") + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"platformName\" value=\"" + browseConfigData.get("platformName")
							+ "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"environment\" value=\"" + browseConfigData.get("environment")
							+ "\"></parameter>\n");
					
					fos.write("\t\t\t<parameter name=\"app\" value=\"" + browseConfigData.get("App")
					+ "\"></parameter>\n");
			
					fos.write("\t\t\t<parameter name=\"appPackage\" value=\"" + browseConfigData.get("appPackage")
					+ "\"></parameter>\n");
					
					fos.write("\t\t\t<parameter name=\"platformVersion\" value=\"" + browseConfigData.get("platformVersion")
					+ "\"></parameter>\n");
					
					fos.write("\t\t\t<parameter name=\"appiumUrl\" value=\"" + browseConfigData.get("appiumURL")
					+ "\"></parameter>\n");
					
					fos.write("\t\t\t<parameter name=\"deviceName\" value=\"" + xls.getCellData(sheetName, "DeviceName", rNum) + "\"></parameter>\n");
					
					/*//if(!gridURL.isEmpty()){
						fos.write("\t\t\t<parameter name=\"seleniumgridurl\" value=\""+ SeleniumGridURL + "\"></parameter>\n");
					//}
					*/
					fos.write("\t\t <classes>\n");

					List<String> allClasses = getClasses(SprintName, MobileDevice);
					System.out.println("The size of all classes " + allClasses.size());

					for (int k = 0; k < allClasses.size(); k++) {

						System.out.println("class ->" + allClasses.get(k));
						fos.write("\t\t\t<class name=\"" + allClasses.get(k) + "\">\n");
						List<String> allMethods = getMethods(SprintName, allClasses.get(k));
						fos.write("\t\t\t\t<methods>\n");
						String userrole = getuserrole(SprintName, allClasses.get(k));
						if (!userrole.isEmpty())
							fos.write("\t\t\t<parameter name=\"userrole\" value=\"" + userrole + "\"></parameter>\n");
						for (int l = 0; l < allMethods.size(); l++) {
							System.out.println("\t method ->" + allMethods.get(l));
							fos.write("\t\t\t\t\t <include name=\"" + allMethods.get(l) + "\" />\n");
						}
						fos.write("\t\t\t\t</methods>\n");

						fos.write("\t\t\t </class>\n");
					}
					fos.write("\t\t </classes>\n");
					fos.write("\t</test>\n");
					fos.write("\n");
				}
			}
			}else if(AppCategory.equalsIgnoreCase("Web")){
				
			}
			fos.write("</suite>");
			fos.flush();

			if (new File(outputFileName).exists()) {
				System.out.println("Test suite successfully generated in" + outputFileName);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static List<String> getClasses(String allTestsSheetName, String browser) {
		List<String> classes = new ArrayList<String>();

		int startRow = 2;
		for (int i = startRow; i < xls.getRowCount(allTestsSheetName); i++) {

			if ((xls.getCellData(allTestsSheetName, "deviceToExecute", i).contains(browser))
					&& (xls.getCellData(allTestsSheetName, "Runmode", i).equalsIgnoreCase("Y"))) {

				String classname = xls.getCellData(allTestsSheetName, "classname", i);
				classes.add(classname);
			}
		}

		return classes;
	}

	public static List<String> getMethods(String allTestsSheetName, String className) {
		List<String> methods = new ArrayList<String>();

		int startRow = 2;
		for (int i = startRow; i < xls.getRowCount(allTestsSheetName); i++) {

			if ((xls.getCellData(allTestsSheetName, "classname", i).equalsIgnoreCase(className))
					&& (xls.getCellData(allTestsSheetName, "Runmode", i).equalsIgnoreCase("Y"))) {
				int methodNum = i + 1;
				do {
					String methodName = xls.getCellData(allTestsSheetName, "testmethod", methodNum);
					if (!(methodName.isEmpty())
							&& (xls.getCellData(allTestsSheetName, "Runmode", methodNum).equalsIgnoreCase("Y"))) {

						methods.add(methodName);
					}
					methodNum++;
				} while (!(xls.getCellData(allTestsSheetName, "testmethod", methodNum).isEmpty()));
			}
		}

		return methods;
	}

	public static String getuserrole(String allTestsSheetName, String className) {

		String userrole = "";

		int startRow = 2;
		for (int i = startRow; i < xls.getRowCount(allTestsSheetName); i++) {

			if ((xls.getCellData(allTestsSheetName, "classname", i).equalsIgnoreCase(className))) {
				userrole = xls.getCellData(allTestsSheetName, "userrole", i);
			}
		}

		return userrole;
	}

	public static HashMap<String, String> getBrowserDetails(String browserConfigSheetName, String browser) {
		HashMap<String, String> browserDetails = new HashMap<String, String>();

		int startRow = 1;
		for (int i = startRow; i < xls.getRowCount(browserConfigSheetName); i++) {

			String browserShown = xls.getCellData(browserConfigSheetName, 0, i);
			if ((!(browserShown.isEmpty()) && browserShown.equalsIgnoreCase(browser))) {

				int colCount = xls.getColumnCount(browserConfigSheetName, i);
				for (int j = 0; j < colCount; j++) {

					String key = xls.getCellData(browserConfigSheetName, j, i + 1);
					// System.out.println("key -" + key);
					String value = xls.getCellData(browserConfigSheetName, j, i + 2);
					// System.out.println("value -" + value);
					browserDetails.put(key, value);
				}
			}
		}

		return browserDetails;
	}

	public static String getBrowser() {

		String browser = null;
		try {

			String sheetName = "config";
			for (int rNum = 1; rNum <= xls.getRowCount(sheetName); rNum++) {
				if ("browser".equals(xls.getCellData(sheetName, 0, rNum))) {
					browser = xls.getCellData(sheetName, 1, rNum);
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return browser;
	}

	public static String getglobalUsername() {
		String browser = null;
		try {
			String sheetName = "config";
			for (int rNum = 1; rNum <= xls.getRowCount(sheetName); rNum++) {
				if ("userrole".equals(xls.getCellData(sheetName, 0, rNum))) {
					browser = xls.getCellData(sheetName, 1, rNum);
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return browser;
	}

	public static boolean isClassExecutable(String sheetName, int rNum) {
		boolean flag = false;

		try {
			if (xls.getCellData(sheetName, "Runmode", rNum).equalsIgnoreCase("Y"))
				flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	public static boolean isMethodExecutable(String sheetName, int rNum) {
		boolean flag = false;
		try {
			if (xls.getCellData(sheetName, "Runmode", rNum).equalsIgnoreCase("Y"))
				flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}
}
