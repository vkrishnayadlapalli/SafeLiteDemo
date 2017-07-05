package com.safelite.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import com.safelite.accelerators.Base;

public class Excel_Utility {

	public static Sheet wrkSheetObj;
    public static Workbook wrkBookObj =null;
    
	/**
	 * @author IN00893 Lokesh
	 * @param colindex
	 * @param sheetName
	 * @param path
	 * @return
	 */
	@SuppressWarnings("resource")
	public ArrayList<String> columnData(int colindex,String sheetName,String path)
	{
		ArrayList<String> columndata = null;
		try {
			File f = new File(path);
			FileInputStream ios = new FileInputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook(ios);
			XSSFSheet sheet = workbook.getSheet(sheetName);

			Iterator<Row> rowIterator = sheet.iterator();
			columndata = new ArrayList<String>();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					if(row.getRowNum() > 0){ //To filter column headings
						if(cell.getColumnIndex() == colindex){// To match column index
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								columndata.add(cell.getNumericCellValue()+"");
								break;
							case Cell.CELL_TYPE_STRING:
								columndata.add(cell.getStringCellValue());
								break;
							}
						}
					}
				}
			}
			ios.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		//returns the complete column data
		return columndata;

	}

	/**
	 * @author IN00893 Lokesh
	 * @param path
	 * @param uniqueName
	 * @param uniqueColumnValue
	 * @param getColumnValue
	 * @param sheetName
	 * @return
	 */
	@SuppressWarnings({ "unused" })
	public  List<String> getDataToList(String path,String uniqueName,int uniqueColumnValue,int getColumnValue, String sheetName) {
		List<String> getValue = new ArrayList<String>();
		Excel_Utility re=new Excel_Utility();
		ArrayList<String> Keys=re.columnData(uniqueColumnValue, sheetName,path);
		ArrayList<String> values=re.columnData(getColumnValue, sheetName,path);

		Map<String, String> excelConfig = new HashMap<String, String>();

		for(int i=0; i<Keys.size(); i++)
		{	
			if(Keys.get(i).equalsIgnoreCase(uniqueName)){
				for(int j=i; j<values.size(); j++)
				{
					if(Keys.get(j).equalsIgnoreCase(uniqueName)){
						excelConfig.put(Keys.get(i), values.get(j));
						String excel=excelConfig.put(Keys.get(i), values.get(j)).toString();
						getValue.add(excel);
					}
					break;
				}
			}}

		return getValue;

	}
	/**
	 * 
	 * @param path
	 * @param uniqueName
	 * @param ColumnValueOfCommonValue
	 * @param keyColNum
	 * @param valueColNum
	 * @param sheetName
	 * @return
	 */
	@SuppressWarnings({ "unused" })
	public  Map<String, String> keyValueBasedOnCommonValue(String path,String uniqueName,int ColumnValueOfCommonValue,int keyColNum,int valueColNum, String sheetName) {
		List<String> getValue = new ArrayList<String>();
		ArrayList<String> Keys=new ArrayList<String>();
		Map<String, String> excelConfig = new HashMap<String, String>();
		Excel_Utility re=new Excel_Utility();
		List<String> key=re.columnData(ColumnValueOfCommonValue, sheetName, path);
		ArrayList<String> values=re.columnData(keyColNum, sheetName, path);
		for (int i = 0; i < key.size(); i++) {
			if(key.get(i).equalsIgnoreCase(uniqueName)){
				for (int j = i; j < values.size(); j++) {
					if(key.get(j).equalsIgnoreCase(uniqueName)){
						excelConfig.put(key.get(i), values.get(j));
						String excel=excelConfig.put(key.get(i), values.get(j)).toString();
						Keys.add(excel);

					}
				}
				break;
			}
		}
		ArrayList<String> values1=re.columnData(valueColNum, sheetName, path);
		for(int i=0; i<Keys.size(); i++)
		{	
			for(int j=i; j<values1.size(); j++)
			{
				excelConfig.put(Keys.get(i), values1.get(j));
				break;
			}
		}
		return excelConfig;
	}
	/**
	 * 
	 * @param rowIndex
	 * @param endColIndex
	 * @param sheetIndex
	 * @param path
	 * @return
	 */
	@SuppressWarnings("resource")
	public ArrayList<String> rowData(int rowIndex, int endColIndex,int sheetIndex,String path){
		ArrayList<String> rowData = new ArrayList<String>();
		try {
			File f = new File(path);
			FileInputStream ios = new FileInputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook(ios);
			XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			for(int colStartPosition=0; colStartPosition<=endColIndex; colStartPosition++){
				Cell cell = sheet.getRow(rowIndex).getCell(colStartPosition);

				switch(cell.getCellType()) {
				case Cell.CELL_TYPE_STRING: 
					rowData.add(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:  
					rowData.add(cell.getNumericCellValue()+"");
					break;
				}
			}

			ios.close();   
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rowData;
	}

	/** Retrieve the data present in the excel cell.
	 * 
	 * @param rowNumber
	 * @param columnNumber
	 * @param path
	 * @param sheetIndex
	 * @return
	 */
	@SuppressWarnings("resource")
	public String getCell(int rowNumber,int columnNumber,String path, String sheetName){

		String cellData = null;
		try {
			File f = new File(path);
			FileInputStream ios = new FileInputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook(ios);
			XSSFSheet sheet=workbook.getSheet(sheetName);

			Cell cell = sheet.getRow(rowNumber).getCell(columnNumber);

			switch(cell.getCellType()) {
			case Cell.CELL_TYPE_STRING: 
				cellData = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:  
				cellData = ""+cell.getNumericCellValue();
				break;
			}
		}catch (Exception e) {
			System.out.println("Exception occured while reading the cell data "+e);
			e.printStackTrace();
		}

		return cellData;
	}

	/** Update the Numeric Value present in the String.
	 *   
	 * @param stringValue
	 * @return - Returns updated numeric value by 1, in String.
	 */
	private String incrementStringValueByOne(String stringValue){

		String sUpdatedString = null;
		try{
			String strValue = stringValue;
			String firstNumber = strValue.replaceFirst(".*?(\\d+).*", "$1");
			sUpdatedString = strValue.replaceAll(firstNumber, ""+(Integer.parseInt(firstNumber)+1));
		}catch (Exception e) {
			System.out.println("Unable to update the String '"+stringValue+"'. "+e);
			e.printStackTrace();
		}

		return sUpdatedString;
	}

	/** Updates the Value present in the excel cell.
	 * 
	 * @param rowNumber
	 * @param columnNumber
	 * @param path
	 * @param sheetIndex
	 * @param cellValueToUpdate
	 */
	@SuppressWarnings("resource")
	private void updateCell(int rowNumber,int columnNumber,String path, String sheetName, String cellValueToUpdate){
		try {
			File f = new File(path);
			FileInputStream ios = new FileInputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook(ios);
			XSSFSheet sheet = workbook.getSheet(sheetName);

//			Cell cell = sheet.getRow(rowNumber).getCell(columnNumber);
			XSSFRow row  = sheet.getRow(rowNumber);


			if (row == null)
				row = sheet.createRow(rowNumber);

			XSSFCell myCell = row.createCell(columnNumber);
			myCell.setCellValue(cellValueToUpdate);

			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

		}catch (Exception e) {
			System.out.println("Unable to update the cell. "+e);
			e.printStackTrace();
		}
	}

	/** Fetche's the test data from the excel file, increment's the test data value by 1 and 
	 * writes the updated test data value into excel file.
	 * 
	 * @param rowNumber
	 * @param columnNumber
	 * @param excelFilePath
	 * @param sheetIndex
	 */
	public void updateCellValue(int rowNumber, int columnNumber, String excelFilePath, String sheetName){
		String cellValue = null;
		String updateCellValue = null;

		//Fetche's value present in the cell based on row & column number.
		cellValue = getCell(rowNumber, columnNumber,excelFilePath,sheetName);

		//Increment the fetched excel value by 1 
		updateCellValue = incrementStringValueByOne(cellValue);

		//Update the cell value in to excell sheet, after incrementing value present in the cell.
		updateCell(rowNumber,columnNumber,excelFilePath, sheetName, updateCellValue);
	}


	/** Retrieves the absolute file path.
	 *
	 * @param relativePath
	 * @return
	 * @throws IOException
	 */
	public String getAbsoulteFilePath(String relativePath) throws IOException{
		String absolutePath = new File("").getAbsolutePath()+relativePath;
		return absolutePath;
	}


	/** Updates the Value present in the excel cell by using the sheetName.
	 * 
	 * @param rowNumber
	 * @param columnNumber
	 * @param path
	 * @param sheetIndex
	 * @param cellValueToUpdate
	 */
	@SuppressWarnings("resource")
	public void updateCellInSheet(int rowNumber,int columnNumber,String path, String sheetName, String cellValueToUpdate){
		try {
			File f = new File(path);
			FileInputStream ios = new FileInputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook(ios);
			XSSFSheet sheet = workbook.getSheet(sheetName);

//			Cell cell = sheet.getRow(rowNumber).getCell(columnNumber);
			XSSFRow row  = sheet.getRow(rowNumber);


			if (row == null)
				row = sheet.createRow(rowNumber);

			XSSFCell myCell = row.createCell(columnNumber);
			myCell.setCellValue(cellValueToUpdate);

			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

		}catch (Exception e) {
			/*System.out.println("Unable to update the cell. "+e);
			e.printStackTrace();*/
		}
	}

	/**
	 * 
	 * @param colindex
	 * @param sheetName
	 * @param path
	 * @return
	 */
	@SuppressWarnings("resource")
	public ArrayList<String> columnDataBasedOnSheetName(int colindex,String sheetName,String path)
	{
		ArrayList<String> columndata = null;
		try {
			File f = new File(path);
			FileInputStream ios = new FileInputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook(ios);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> rowIterator = sheet.iterator();
			columndata = new ArrayList<String>();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					if(row.getRowNum() > 0){ //To filter column headings
						if(cell.getColumnIndex() == colindex){// To match column index

							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								columndata.add(cell.getNumericCellValue()+"");
								break;
							case Cell.CELL_TYPE_STRING:
								columndata.add(cell.getStringCellValue());
								break;
							}
						}
					}
				}
			}
			ios.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//returns the complete column data
		return columndata;

	}

	/**
	 * 
	 * @param colHeader
	 * @param sheetName
	 * @param path
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String getSheetCellData(String colHeader, String sheetName,  String path)
	{
		String cellData = null;

		try {
			File f = new File(path);
			FileInputStream ios = new FileInputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook(ios);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String finalcell=""+cell;
					int colnum=cell.getColumnIndex();
					if(finalcell.equalsIgnoreCase(colHeader)){// To match column value
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							cellData=sheet.getRow(1).getCell(colnum).toString();
							break;
						case Cell.CELL_TYPE_STRING:
							cellData=sheet.getRow(1).getCell(colnum).toString();
							break;
						case Cell.CELL_TYPE_BLANK:
							try{
								System.out.println("Blank space");
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			ios.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//returns the complete column data
		return cellData;		
	}


	/**
	 * 
	 * @param colHeader
	 * @param sheetName
	 * @param path
	 * @return
	 */
	@SuppressWarnings("resource")
	public String getCellValueBasedOnHeaderName(String colHeader, String sheetName,  String path)
	{
		String cellData = null;

		try {
			File f = new File(path);
			FileInputStream ios = new FileInputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook(ios);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String finalcell=""+cell;
					int colnum=cell.getColumnIndex();
					if(finalcell.equalsIgnoreCase(colHeader)){// To match column value
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							cellData=sheet.getRow(1).getCell(colnum).toString();
							break;
						case Cell.CELL_TYPE_STRING:
							cellData=sheet.getRow(1).getCell(colnum).toString();
							break;
						case Cell.CELL_TYPE_BLANK:
							try{
								System.out.println("Blank space");
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			ios.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//returns the complete column data
		return cellData;		
	}


	/** Remove used Values.
	 * 
	 * @param path
	 * @param sheetIndex
	 */
	@SuppressWarnings("resource")
	public boolean removeUsedValues(String path, String sheetName){
		boolean flag= false;
		try {
			File f = new File(path);
			FileInputStream ios = new FileInputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook(ios);
			XSSFSheet sheet = workbook.getSheet(sheetName);

			int firstRowNum=1;
			int lastRowNum = sheet.getLastRowNum();
			if(lastRowNum >= firstRowNum){
				sheet.shiftRows(firstRowNum, lastRowNum, -1);  
				flag = true;
			}else{
				try{
					sheet.removeRow(sheet.getRow(lastRowNum));
					flag = true;
				}catch (Exception e) {
					System.out.println("No more values are available. "+e);
					e.printStackTrace();
					flag = false;
				}
			}
			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

		}catch (Exception e) {
			System.out.println("Unable to update the cells. "+e);
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}

	
	/**
	 * This method is used for reading two columns as Key,Value 
	 * and returns them in HASHMAP.... "excelConfig" returns Hashmap 
	 * @param col1
	 * @param col2
	 * @param sheet
	 * @param path
	 * @return
	 */
	@SuppressWarnings({ "unused" })
	public  Map<String, String> twoColumns(int col1,int col2,String sheet,String path) {

		Excel_Utility re=new Excel_Utility();
		ArrayList<String> Keys=re.columnData(col1, sheet, path);
		ArrayList<String> values=re.columnData(col2, sheet, path);

		Map<String, String> excelConfig = new HashMap<String, String>();

		for(int i=0; i<Keys.size(); i++)
		{	
			for(int j=i; j<values.size(); j++)
			{
				excelConfig.put(Keys.get(i), values.get(j));
				break;
			}
		}
		return excelConfig;
	}

	/**
	 * 
	 * @param path
	 * @param uniqueName
	 * @param uniqueColumnValue
	 * @param getColumnValue
	 * @param sheetName
	 * @return
	 */
	@SuppressWarnings({ "unused" })
	public  Map<String, String> twoColumnsFroKeys(String path,String uniqueName,int uniqueColumnValue,int getColumnValue, String sheetName) {
		List<String> getValue = new ArrayList<String>();
		ArrayList<String> Keys=new ArrayList<String>();
		Map<String, String> excelConfig = new HashMap<String, String>();
		Excel_Utility re=new Excel_Utility();
		List<String> key=re.columnData(0, sheetName, path);
		ArrayList<String> values=re.columnData(1, sheetName, path);
		for (int i = 0; i < key.size(); i++) {
			if(key.get(i).equalsIgnoreCase(uniqueName)){
				for (int j = i; j < values.size(); j++) {
					if(key.get(j).equalsIgnoreCase(uniqueName)){
						excelConfig.put(key.get(i), values.get(j));
						String excel=excelConfig.put(key.get(i), values.get(j)).toString();
						Keys.add(excel);
						//System.out.println(excel);
					}
				}
				break;
			}
		}
		ArrayList<String> values1=re.columnData(2, sheetName, path);
		for(int i=0; i<Keys.size(); i++)
		{	
			for(int j=i; j<values1.size(); j++)
			{
				excelConfig.put(Keys.get(i), values1.get(j));
				break;
			}
		}
		return excelConfig;
	}
	
	 /**
     * 
     * @param inputDataFilePath
     * @param SheetName
     * @return
     * @throws Exception
     */
    public static Sheet getSheetObject(String inputDataFilePath, String SheetName) throws Exception
	{
		FileInputStream file = new FileInputStream(new File(inputDataFilePath));
		Workbook wrkBookObj =  Workbook.getWorkbook(file); 
		Sheet wrkSheetObj= wrkBookObj.getSheet(SheetName);		
		return wrkSheetObj;
	}
    
    /**
     * 
     * @param sSheet
     * @param sColumnName
     * @param iRowCount
     * @return
     * @throws Exception
     */
//    @SuppressWarnings("static-access")
//	public static String getSheetCellData(String sColumnName, int iRowCount) throws Exception
//	{
//    	String cellData=null;
//		int SheetColCount=new Base().objInputSheet.getColumns();
//		for(int Colcnt=0;Colcnt<SheetColCount;Colcnt++)
//		{
//			if((new Base().objInputSheet.getCell(Colcnt,0).getContents()).equals(sColumnName))
//			{
//				cellData=new Base().objInputSheet.getCell(Colcnt, iRowCount).getContents();
//				return cellData;
//			}			
//		}				
//		return null;
//	}
    
    
    /**
     * 
     * @param sSheet
     * @param sColumnName
     * @param iRowCount
     * @return
     * @throws Exception
     */
//    @SuppressWarnings("static-access")
//	public static String returnsRowNumber(String sColumnName, int iRowCount) throws Exception
//	{
//    	String cellData=null;
//		int SheetColCount=new Base().objInputSheet.getColumns();
//		for(int Colcnt=0;Colcnt<SheetColCount;Colcnt++)
//		{
//			if((new Base().objInputSheet.getCell(Colcnt,0).getContents()).equals(sColumnName))
//			{
//				cellData=new Base().objInputSheet.getCell(Colcnt, iRowCount).getContents();
//				return cellData;
//			}			
//		}				
//		return null;
//	}
    
    /**
     * 
     * @param inputSheetObj
     * @param ColumnName
     * @param row
     * @return
     * @throws Exception
     */
    public String getSheetCellData(Sheet inputSheetObj, String ColumnName,int row) throws Exception
	{		
		String cellData=null;
		String cellDataFlag="False";
		int SheetColCount=inputSheetObj.getColumns();
		for(int Colcnt=0;Colcnt<SheetColCount;Colcnt++)
		{
			if((inputSheetObj.getCell(Colcnt,0).getContents()).equals(ColumnName))
			{
				cellData=inputSheetObj.getCell(Colcnt, row).getContents();
				cellDataFlag="True";
				//System.out.println("Column Match and data is  ="+inputSheetObj.getCell(Colcnt, row).getContents());
			}			
		}
		if(cellDataFlag.equals("False"))				
			System.out.println("Column Name: "+ColumnName+" doesn't exist in the sheet");
		return cellData;

	}
    
    /** Updates the Value present in the excel cell by using the sheetName.
	 * 
	 * @param rowNumber
	 * @param columnNumber
	 * @param path
	 * @param sheetIndex
	 * @param cellValueToUpdate
	 */
	@SuppressWarnings("resource")
	public void updateCellInSheet_XLS(int rowNumber,int columnNumber,String path, String sheetName, String cellValueToUpdate){
		try {
			File f = new File(path);
			FileInputStream ios = new FileInputStream(f);
			HSSFWorkbook workbook = new HSSFWorkbook(ios);
			HSSFSheet sheet = workbook.getSheet(sheetName);

			Cell cell = sheet.getRow(rowNumber).getCell(columnNumber);
			HSSFRow row  = sheet.getRow(rowNumber);

			if (row == null)
				row = sheet.createRow(rowNumber);

			HSSFCell myCell = row.createCell(columnNumber);
			myCell.setCellValue(cellValueToUpdate);

			cell = row.getCell(columnNumber, Row.RETURN_BLANK_AS_NULL);

			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

		}catch (Exception e) {
			System.out.println("Unable to update the cell. "+e);
			System.out.println();
			e.printStackTrace();
		}
	}

}

