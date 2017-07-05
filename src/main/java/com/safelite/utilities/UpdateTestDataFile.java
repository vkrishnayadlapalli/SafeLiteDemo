package com.safelite.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateTestDataFile {
	public static String getDataFile() {
		return testDataFile;
	}

	private static void setDataFile(String dataFile) {
		UpdateTestDataFile.testDataFile = dataFile;
	}

	private static String testDataFile = "TestData";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if (!args[0].isEmpty()) {
			testDataFile = args[0];
		}
		setDataFile(testDataFile);
		Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir") + "//TestData/" + testDataFile + ".xlsx");
		int rowcount = xls.getRowCount("Browsers");
		System.out.println("Total number of rows" + rowcount);
		FileInputStream file = new FileInputStream(
				new File(System.getProperty("user.dir") + "//TestData/" + testDataFile + ".xlsx"));

		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet("Browsers");
        XSSFSheet Exec_sheet = workbook.getSheet("ExecutionType");
		String browser="chromeWindowsLocal,firefoxWindowsLocal";
		String ExecutionType="Parallel";
		String SprintName="allTests";
		String instances="2";
		String seleniumGridURL="local";
		try
		{
			browser = args[1];
			ExecutionType = args[2];
			SprintName = args[3];
			instances = args[4];
			seleniumGridURL=args[5];
		}catch(Exception e) {
			e.printStackTrace();
		}


		if (!browser.isEmpty()) {
            //Jenkins Block
			String[] browsersList = browser.split(",");
			for (int i = 1; i < rowcount; i++) {
				sheet.removeRow(sheet.getRow(i));
			}
			//Updating execution type from Jenkins
            XSSFRow row0 = Exec_sheet.createRow(0);
            XSSFCell r0c1 = row0.createCell(0);
            r0c1.setCellValue("ExecutionType");
            XSSFCell r0c12 = row0.createCell(1);
            r0c12.setCellValue(ExecutionType);

            XSSFRow row1 = Exec_sheet.createRow(1);
            XSSFCell r1c1 = row1.createCell(0);
            r1c1.setCellValue("Instances");
            XSSFCell r1c12 = row1.createCell(1);
            r1c12.setCellValue(instances);

			int i = 1;
			for (String browserName : browsersList) {
				// 2nd row
				XSSFRow row2 = sheet.createRow(i);
				XSSFCell r2c1 = row2.createCell(0);
				r2c1.setCellValue(browserName);
				XSSFCell r2c2 = row2.createCell(1);
				r2c2.setCellValue("Y");
				i++;
			}


/*		FileOutputStream output_file = new FileOutputStream(
				new File(System.getProperty("user.dir")+"//TestData/TestData.xlsx"));*/
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
		}else{
                //Local Block
				//ExecutionType= "Parallel";
			    ExecutionType=xls.getCellData("ExecutionType", 1, 1);
			    System.out.println("Execution type: "+ExecutionType);
				SprintName= "allTests";
				instances=xls.getCellData("ExecutionType", 1, 2);
				int instance_count=Integer.parseInt(instances);
				instances= Integer.toString(instance_count);
				System.out.println("No of Instances : "+instance_count);
			   for(int i=2;i<=rowcount;i++) {
			   	 if(xls.getCellData("Browsers", 1, i).equalsIgnoreCase("Y")){
					 System.out.println( xls.getCellData("Browsers", 0, i));
				 }
			   }
		}
			try {

				ExcelUtilityforSprintandExecutionType.createXml("Browsers", ExecutionType, SprintName,instances,seleniumGridURL);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	
