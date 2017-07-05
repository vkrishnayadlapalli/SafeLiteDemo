package com.safelite.utilities;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import jxl.Sheet;

//import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
//import com.safelite.accelerators.Base;

public class GenerateHtmlReport {
	public String FilePath=null;
	public Sheet sheet;


	public  Map<String, Object> readXML(Document doc,String suiteName,String browserName,String summaryReportPath) throws Exception{

		int testCasePassCount =0;
		int testCaseFailCount =0;
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		Map<String, Object> dataMap = Maps.newHashMap();
		
		NodeList nList2 = doc.getElementsByTagName("TestSuite");
		Node nNode2 = nList2.item(0);
		Element eElement2 = (Element) nNode2;
		String suitTime = eElement2.getAttribute("executionTime");
		dataMap.put("suiteTime", suitTime);
		
				
		NodeList nList = doc.getElementsByTagName("TestCase");

		dataMap.put("totalNoOfTestCases", nList.getLength());
		
		List<String> testCasesList = Lists.newArrayList();
		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			Element eElement = (Element) nNode;
			String testName = eElement.getAttribute("name");
			XPath xPath =  XPathFactory.newInstance().newXPath();

			String failXpath = "//TestCase[@name='"+testName+"']//Step[@status='Fail']";	
			//	String passXpath = "//TestCase[@name='"+testName+"']//Step[@status='Success']";	

			NodeList failCount =(NodeList)xPath.compile(failXpath).evaluate(doc, XPathConstants.NODESET);
			String status = "";
			if(failCount.getLength()==0){
				testCasePassCount++;
				status ="pass";
				FilePath = System.getProperty("user.dir")+"\\Macros\\"+new Property().getProperty("MacroFile");				
				sheet =new Excel_Utility().getSheetObject(FilePath,"Automation_Input_Parameters");	
				new Excel_Utility().updateCellInSheet(temp+1, 8, FilePath, "Automation_Input_Parameters", status);
				
			}else{
				status ="fail";
				testCaseFailCount++;
				FilePath = System.getProperty("user.dir")+"\\Macros\\"+new Property().getProperty("MacroFile");				
				sheet =new Excel_Utility().getSheetObject(FilePath,"Automation_Input_Parameters");	
				new Excel_Utility().updateCellInSheet(temp+1, 8, FilePath, "Automation_Input_Parameters", status);
			}
//			long time=Math.abs(Base.list.get(temp));
//			//testCaseTimes.add(String.valueOf(time/1000).toString());
//			String browserName1=testName.split("_")[0];
//			testCasesList.add(testName+","+browserName1+","+status+","+testName.replace(":", "_")+"Detailed"+".html"+","+time/1000);

		}
		Map<String, List<String>> testCasesMap = Maps.newHashMap();
		for (int temp = 0; temp < nList.getLength(); temp++) {
			List<String> stepDetails = Lists.newArrayList();
			Node nNode = nList.item(temp);
			Element eElement = (Element) nNode;
			String testName = eElement.getAttribute("name");
			NodeList childNodeList = eElement.getChildNodes();
			for(int stepName = 0; stepName < childNodeList.getLength(); stepName++){
				Node nNode1 = childNodeList.item(stepName);
				Element eElement1 = (Element) nNode1;
					if(eElement1.getAttribute("status").equalsIgnoreCase("Fail")){
						stepDetails.add(eElement1.getAttribute("name")+"&"+eElement1.getAttribute("description")+"&"+eElement1.getAttribute("status")+"&"+eElement1.getAttribute("screenshot"));
					}else{
						stepDetails.add(eElement1.getAttribute("name")+"&"+eElement1.getAttribute("description")+"&"+eElement1.getAttribute("status"));
					}
				
			}
			testCasesMap.put(testName, stepDetails);
		}
		dataMap.put("hostName", InetAddress.getLocalHost().getHostName());
		dataMap.put("osName", System.getProperty("os.name"));
		dataMap.put("osVersion", System.getProperty("os.name"));
		dataMap.put("testCases", testCasesList);
		dataMap.put("noOfTestCases", testCasesList.size());
		dataMap.put("totalNoOfTestCasesPass", testCasePassCount);
		dataMap.put("totalNoTestCasesFail",testCaseFailCount);
		dataMap.put("suiteName",suiteName);
		dataMap.put("testCaseStepDetails", testCasesMap);
		//dataMap.put("testCaseTime", testCaseTimes);
		//System.out.println(dataMap);
		return dataMap;
	}

/*	public synchronized void  generateDetailedSummmaryReport(Map<String, Object> dataMap,String summaryReportFilePath) throws Exception{

		 
		Map<String, List<String>> testCaseDetails = (Map<String, List<String>>)dataMap.get("testCaseStepDetails");
		for(Entry<String, List<String>> data:testCaseDetails.entrySet()){
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();

			Template t = ve.getTemplate("templates/DetailReport.vm");
			  create a context and add data 
			VelocityContext context = new VelocityContext();
			context.put("testCaseDetails", data.getValue());
			context.put("testCaseName", data.getKey());
			StringWriter writer = new StringWriter();
			t.merge( context, writer );

			//System.out.println( writer.toString() );
			File file = new File(summaryReportFilePath+"\\"+data.getKey().replace(":", "_")+"Detailed"+".html");


			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(writer.toString());
			bw.close();
		}
		System.out.println();
	}
*/
/*	public void generateSummaryReport(Map<String, Object> dataMap,String summaryReportFilePath) throws ResourceNotFoundException, ParseErrorException, Exception{
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		Template t = ve.getTemplate("templates/SummaryReport.vm");
		  create a context and add data 
		VelocityContext context = new VelocityContext();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		List<String> testCasesList = (List<String>)dataMap.get("testCases");
		context.put("hostName", InetAddress.getLocalHost().getHostName());
		context.put("osName", System.getProperty("os.name"));
		context.put("osVersion", System.getProperty("os.version"));
		context.put("suiteName", "TestSuite");
		context.put("noOfTestCases", testCasesList.size());
		context.put("testCases", dataMap.get("testCases"));
		context.put("totalNoOfTestCasesPass", dataMap.get("totalNoOfTestCasesPass"));
		context.put("totalNoTestCasesFail",dataMap.get("totalNoTestCasesFail"));
		context.put("runDateAndTime", sdf.format(new Date()));
		context.put("suiteTime", dataMap.get("suiteTime"));
		 now render the template into a StringWriter 
		StringWriter writer = new StringWriter();
		t.merge( context, writer );
		 show the World 


		//System.out.println( writer.toString() ); 

		File file = new File(summaryReportFilePath);

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(writer.toString());
		bw.close();

		System.out.println("Done");

	}
*/
}
