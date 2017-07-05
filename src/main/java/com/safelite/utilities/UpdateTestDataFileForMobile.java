package com.safelite.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateTestDataFileForMobile {
	public static String getDataFile() {
		return testDataFile;
	}

	private static void setDataFile(String dataFile) {
		UpdateTestDataFileForMobile.testDataFile = dataFile;
	}

	private static String testDataFile = "TestData_Mobile";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if (!args[0].isEmpty()) {
			testDataFile = args[0];
		}
		setDataFile(testDataFile);
		Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir") + "//TestData/" + testDataFile + ".xlsx");
		int rowcount = xls.getRowCount("Mobile");
		System.out.println("Total number of rows" + rowcount);
		FileInputStream file = new FileInputStream(
				new File(System.getProperty("user.dir") + "//TestData/" + testDataFile + ".xlsx"));

		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet("Mobile");
        XSSFSheet Exec_sheet = workbook.getSheet("ExecutionType");
		/*String browser="Android_Native,IOS_Native";
		String ExecutionType="Parallel";
		String SprintName="allTests";
		String instances="2";*/
        
        String AppCategory="Native_bhadri";
		String ExecutionFlag="Parallel_bhadri";
		String SprintName="Sprint1_Impact";
		String Android_Device="Android_Baki";
		String seleniumGridURL="local";
		
		try
		{
			AppCategory = args[1];
			System.out.println("=====================================================================");
			System.out.println("AppCategory: "+AppCategory);
			
			ExecutionFlag = args[2];
			System.out.println("=====================================================================");
			System.out.println("ExecutionFlag: "+ExecutionFlag);

			SprintName = args[3];
			System.out.println("=====================================================================");
			System.out.println("SprintName: "+SprintName);

			Android_Device = args[4];
			System.out.println("=====================================================================");
			System.out.println("Android_Device: "+Android_Device);
			seleniumGridURL=args[5];
			
		
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		if(AppCategory.equals("Native")){

		if (!Android_Device.isEmpty()) {
			
			System.out.println("=====================================================================");
			System.out.println("Android Device is not empty"+"---------"+"ios device is not empty");
			System.out.println("=====================================================================");
            //Jenkins Block
			String[] devicelist = Android_Device.split(",");
			//String[] iosdevicelist = iOS_Device.split(",");
			for (int i = 1; i < rowcount; i++) {
				sheet.removeRow(sheet.getRow(i));
			}
			//Updating execution type from Jenkins
            XSSFRow row0 = Exec_sheet.createRow(0);
            XSSFCell r0c1 = row0.createCell(0);
            r0c1.setCellValue("ExecutionType");
            XSSFCell r0c12 = row0.createCell(1);
            r0c12.setCellValue(ExecutionFlag);

          /*  XSSFRow row1 = Exec_sheet.createRow(1);
            XSSFCell r1c1 = row1.createCell(0);
            r1c1.setCellValue("Instances");
            XSSFCell r1c12 = row1.createCell(1);
            r1c12.setCellValue(instances);*/

			int i = 1;
			for (String devicename : devicelist) {
				// 2nd row
				XSSFRow row2 = sheet.createRow(i);
				XSSFCell r2c0 = row2.createCell(0);
				r2c0.setCellValue("Android-Native");
				
				XSSFCell r2c1 = row2.createCell(1);
				r2c1.setCellValue(devicename);

				XSSFCell r2c2 = row2.createCell(2);
				r2c2.setCellValue("Y");
				i++;
			}
			
			try {
				FileOutputStream output_file = new FileOutputStream(
						new File(System.getProperty("user.dir") + "//TestData/" + testDataFile + ".xlsx"));
				// Create Second Row
				workbook.write(output_file);
				// close the stream
				output_file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		else{
                //Local Block
				//ExecutionType= "Parallel";
				ExecutionFlag=xls.getCellData("ExecutionType", 1, 1);
			    System.out.println("Execution type: "+ExecutionFlag);
				SprintName= "allTests";
				/*instances=xls.getCellData("ExecutionType", 1, 2);
				int instance_count=Integer.parseInt(instances);
				instances= Integer.toString(instance_count);
				System.out.println("No of Instances : "+instance_count);*/
			   for(int i=2;i<=rowcount;i++) {
			   	 if(xls.getCellData("Mobile", 2, i).equalsIgnoreCase("Y")){
					 System.out.println( xls.getCellData("Mobile", 0, i));
					 System.out.println( xls.getCellData("Mobile", 1, i));
				 }
			   }
		}
		}
		try {

			CreateXMLForMobile.createXml("Mobile",AppCategory, ExecutionFlag, SprintName,Android_Device,seleniumGridURL);

		} catch (Exception ex) {
			ex.printStackTrace();
		
	}

		}
	}

	
