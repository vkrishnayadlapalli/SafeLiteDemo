package com.safelite.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.safelite.accelerators.TestEngineMobile;

public class WriteIntoExcel extends TestEngineMobile {

	/**
	 * @param : Data Object
	 * @return : None
	 * @Behavior : Function to update the excel sheet with testData at Runtime
	 * @author : GallopAuthor006
	 */

	public void writeXLSXFile() throws IOException {

		try {
			FileInputStream file = new FileInputStream(new File(gTestData));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(globalsheetName);
			System.out.println(sheet.getSheetName());
			Cell cell = null;
			Cell updateCell = null;
			Xls_Reader xls2 = new Xls_Reader(gTestData);
			int cols = xls2.getColumnCount(globalsheetName,
					gTestCaseStartRowNum);
			// Retrieving the info of cell values of a particular row of method
			// in a sheet
			for (int cNum = 0; cNum < cols; cNum++) {
				cell = sheet.getRow(gTestCaseStartRowNum).getCell(cNum);
				String value = dataBean.getValue(cell.getStringCellValue());
				updateCell = sheet.getRow(gTestCaseStartRowNum + 1).getCell(
						cNum);
				if (value != null) {
					updateCell.setCellValue(value);
				}
			}
			file.close();
			FileOutputStream outFile = new FileOutputStream(new File(gTestData));
			workbook.write(outFile);
			outFile.close();
			dataBean.cleanMapObj();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}