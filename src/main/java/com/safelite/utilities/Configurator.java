package com.safelite.utilities;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;

import com.safelite.support.ConfiguratorSupport;

public class Configurator {
	public static ConfiguratorSupport configProps = new ConfiguratorSupport(
			"config.properties");

	public static String[][] getTableArray(String sheetName, String testCaseName)
			throws Exception {
		String[][] tabArray = null;
		try {
			Workbook workbook = Workbook.getWorkbook(new File(configProps
					.getProperty("TestData")
					+ configProps.getProperty("ExcelFileName")));
			Sheet sheet = workbook.getSheet(sheetName);
			int startRow, startCol, endRow, endCol, ci, cj;
			Cell tableStart = sheet.findCell(testCaseName);
			startRow = tableStart.getRow();
			startCol = tableStart.getColumn();

			Cell tableEnd = sheet.findCell(testCaseName, startCol + 1,
					startRow + 1, 100, 64000, false);

			endRow = tableEnd.getRow();
			endCol = tableEnd.getColumn();
			tabArray = new String[endRow - startRow][endCol - startCol - 1];
			ci = 0;

			for (int i = startRow + 1; i <= endRow; i++, ci++) {
				cj = 0;
				for (int j = startCol + 1; j < endCol; j++, cj++) {
					tabArray[ci][cj] = sheet.getCell(j, i).getContents();
				}
			}

			return (tabArray);
		} catch (Exception e) {
			System.out.println(e
					+ Thread.currentThread().getStackTrace()[1].getClassName()
					+ " dataprovider");
			return null;
		}
	}
}
