package com.safelite.commonlib;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;

import com.safelite.accelerators.ActionEngine;



public class CommonLib1 extends ActionEngine {
    public static Logger LOG = Logger.getLogger(ActionEngine.class);
    static String email;
    public String date;
    String position;
    static int num = new Random().nextInt(99999) + 1000;
    String listName;
    String tab = " ";
    String productName = "";
    String brandType="";

    /**
     * selectManageSchedul:Function to click the Manage schduled Option
     *
     * @throws: Throwable the throwable
     */
   /* public void selectManageSchedul() throws Throwable {
        JSClick(MiniSwitcherPage.selectManageSchedul,  "Select Manage Schdule");
    }*/
    /**
     * selectManageSchedul:Function to click the Manage scheduled Option
     *
     * @throws Throwable the throwable
     */

    /*public void waitcloseBusyIcon() throws Throwable {
        By bysyIcon = By.xpath("//span[@class='busyIcon']");
		int iCounter = 0;
		boolean displaystatus = false;
		do {
			iCounter = iCounter + 1;
			try {
				displaystatus = driver.findElement(bysyIcon).isDisplayed();
				System.out.println("Waiting for page to load :: " + iCounter);
			} catch (Exception e) {
			}
		} while (displaystatus == false || iCounter >= 4);
		//shortWait();
	}*/


    /**
     * selectLocation
     *
     * @throws Throwable the throwable
     */

    public void selectLocation(String value) throws Throwable {
        By valuidentifier = By.xpath("//table[@id='locationsTable']//div//span[text()='" + value + "']/..");
        scrollToWebElement(valuidentifier);
        JSClick(valuidentifier, "Location ");
    }


    /**
     * Selct top week end date value.
     *
     * @return the string
     * @throws Throwable the throwable
     */
    public void selctExistingDateValue() throws Throwable {
        try {
            System.out.println(date + "................");

            By valuidentifier = By
                    .xpath("//div[@id='existingScheduleSelect-dialog']//div//div[2]//ul//li//div//div//a[contains(text(),'" + date + "')]");

            click(valuidentifier, "Existing Date");
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }


    private File getLatestFilefromDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }


    /**
     * Description: clicking The time slots clickingTimeSlots
     *
     * @throws Throwable the throwable
     */

    public void clickingTimeSlotsUsingMouse(int j, int x, String table) throws Throwable {
        for (int i = j; i < x; i++) {
            int decrement = 4;

            //Robot robot =new Robot();
            Actions act = new Actions(driver);
            String strCssPart1 = "#" + table + ">tbody tr:nth-child(3)>td:nth-child(";
            String Element = strCssPart1 + i + ")";
            act.clickAndHold(driver.findElement(By.cssSelector(Element))).build().perform();

            String strCssPart2 = "#templateSetUpWrapper div:nth-child(1)>div>table>thead tr:nth-child(2) th:nth-child(" + (i - decrement) + ")";
            By HoursTopgrid = By.cssSelector(strCssPart2);
            isElementPresent(HoursTopgrid, "HoursTopgrid", true);
        }
    }

    /**
     * verifyNewsPopUpClose
     *
     * @throws Throwable the throwable
     */
    public void verifyNewsPopUpClose()
            throws Throwable {
        if (getElementsSize(By.xpath("//div[contains(@class,'x-css-shadow')]/..//div[contains(@class,'x-tool-tool-el x-tool-img x-tool-close')]")) == 1) {
            driver.switchTo().defaultContent();
            //driver.switchTo().frame()
            JSClick(By.xpath("//div[contains(@class,'x-css-shadow')]/..//div[contains(@class,'x-tool-tool-el x-tool-img x-tool-close')]"), "POPUP close");
        }
    }

    public void verifyintheExcelData1(String excelFilePath) throws Throwable {
        boolean flag = false;
        try {
            LOG.info("Verifying excel data started");
            FileInputStream inputStream = new FileInputStream(excelFilePath);

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            //DynamicWaitbylocator(MiniSwitcherPage.selectManageSchedul, 4);
            DateFormat dateFormat = new SimpleDateFormat("dd");
            Date date = new Date();
            String Currentdate = dateFormat.format(date);
            /*String[] Cdate = Currentdate.split(" ");
            String CurrentdateFormet = Cdate[0];*/
            //DynamicWaitbylocator(MiniSwitcherPage.selectManageSchedul, 4);
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            //System.out.print("Excel data: "+cell.getStringCellValue().toString());
                            if (!cell.getStringCellValue().trim().equals("")) {
                                if (cell.getStringCellValue().contains(Currentdate)) {
                                    flag = true;
                                    String DateCellValue = cell.getStringCellValue();
                                    // System.out.print(cell.getStringCellValue());
                                    if (!flag) {
                                        reporter.failureReport(
                                                "Daily Labor Schedule report Location on the left in header, timestamp with date/ time",
                                                DateCellValue + " is  Not Visibile");
                                    } else if (flag) {
                                        reporter.SuccessReport(
                                                "Daily Labor Schedule report Location on the left in header, timestamp with date/ time",
                                                DateCellValue + " is  Visibile");
                                    }
                                }
                            }
                            break;

                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue());
                            break;
                    }
                    if (flag) {
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }

			/**/
            inputStream.close();
            // workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean elementPresent(By by, String locatorName, boolean expected) throws Throwable {
        boolean status = false;
        try {
            //Waittime();
            System.out.println("Element present searching" + locatorName);
            driver.findElement(by);

            System.out.println("Element found" + locatorName);
            status = true;
        } catch (Exception e) {

            status = false;
        }
        return status;
    }

    
   
   

    public void clickonselecteddatepublisheditbuttons(String Date, String Button) throws Throwable {
        click(By.xpath("//td[text()='" + Date + "']/../td[5]/button[text()='" + Button + "']"), Button);
    }

    public void verifyEditPresent() throws Throwable {
        System.out.println(date);

        isElementPresent(
                By.xpath(".//table[@id='manageSchedulesTable']//tr//td[contains(text(),'" + date + "')]//.."), "Edit Not Visible", true);
    }

    /**
     * @throws Throwable the throwable
     * @description:Clicking on publish button in manager schedule
     * @method: clickPublishinManagerSchedule
     */
    public void clickPublishinManagerSchedule(Hashtable<String, String> data)
            throws Throwable {

        try {
            scrollToWebElement(By.xpath(".//*[@id='manageSchedulesTable']//button[text()='View']/../..//td[text()='" + date + "']/..//button[text()='Publish']"));
            click(By.xpath(".//*[@id='manageSchedulesTable']//button[text()='View']/../..//td[text()='" + date + "']/..//button[text()='Publish']"), "Publish");
            waitcloseBusyIcon();
            if (isElementPresent(By.xpath(".//*[@id='openShiftsAlertDialog']//a[text()='Publish & Post Open Shifts']"), "")) {
                scrollToWebElement(By.xpath(".//*[@id='openShiftsAlertDialog']//a[text()='Publish & Post Open Shifts']"));
                click(By.xpath(".//*[@id='openShiftsAlertDialog']//a[text()='Publish & Post Open Shifts']"), "Publish");
                if (isElementPresent(By.xpath("//div[@id='errorListDialog']//a[contains(text(),'Publish')]"), "")) {
                    scrollToWebElement(By.xpath("//div[@id='errorListDialog']//a[contains(text(),'Publish')]"));
                    JSClick(By.xpath("//div[@id='errorListDialog']//a[contains(text(),'Publish')]"), "Publish");
                    waitcloseBusyIcon();
                    click(By.xpath(".//*[@id='scheduleActionCompletedPopup']//a[text()='OK']"), "Ok");
                } else {
                    JSClick(By.xpath("(//*[@id='publishConfirmationPopup']//a[text()='Yes'])[last()]"), "yes");
                    waitcloseBusyIcon();
                    click(By.xpath(".//*[@id='scheduleActionCompletedPopup']//a[text()='OK']"), "Ok");
                }
            } else if (isElementPresent(By.xpath("//div[@id='errorListDialog']//a[contains(text(),'Publish')]"), "")) {

                JSClick(By.xpath("//div[@id='errorListDialog']//a[contains(text(),'Publish')]"), "Publish");
                waitcloseBusyIcon();
                click(By.xpath(".//*[@id='scheduleActionCompletedPopup']//a[text()='OK']"), "Ok");
            } else {
                click(By.xpath(".//*[@id='publishConfirmationPopup']//a[text()='Yes']"), "yes");
                waitcloseBusyIcon();
                click(By.xpath(".//*[@id='scheduleActionCompletedPopup']//a[text()='OK']"), "Ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
            reporter.failureReport("Publish button :: ", "Error while clicking on Publish button in manage schedule");
            throw new RuntimeException(e);
        }
    }

    public List<WebElement> zoomInandOutHandling(By webEle, By tagValue) throws Throwable {
        List<WebElement> totaltimeslots = null;
        try {
            WebElement totaltimes = driver.findElement(webEle);
            totaltimeslots = totaltimes.findElements(tagValue);
            return totaltimeslots;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totaltimeslots;
    }


    /**
     * Description:Press the Enter From Keyboard
     * RobotEnter
     *
     * @throws Throwable the throwable
     * @author :  GallopAuthor002
     */

    public void RobotEnter() throws Throwable {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Get text from PDF file.
     *
     * @return (String) text from PDF file
     * @throws Throwable the throwable
     * @param1: (String) path of the PDF file
     */
    public String getTextFromPDF(String pdfFilePath) throws Throwable {
        File pdfFile = new File(pdfFilePath);

        PDFParser parser = new PDFParser(new FileInputStream(pdfFile));
        parser.parse();

        COSDocument cosDoc = parser.getDocument();
        PDDocument pdDoc = new PDDocument(cosDoc);

        PDFTextStripper pdfStripper = new PDFTextStripper();
        String parsedText = pdfStripper.getText(pdDoc);
        LOG.info("Text in PDF is: \n" + parsedText);
        parser.getPDDocument().close();
        return parsedText;
    }


    /**
     * Description: clicking The time slots clickingTimeSlots
     *
     * @throws Throwable the throwable
     */

    public void clickingTimeSlotsInLaborSchedule(int j, int x) throws Throwable {
        try {
            for (int i = j; i < x; i++) {
                String strCssPart1 = "#laborScheduleTable>tbody tr:nth-child(3)>td:nth-child(";
                click(By.cssSelector(strCssPart1 + i + ")"), "TimeSlots");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * selectManageSchedul:Function to wait busy icon closes
     * If we use this method it will wait until spinner will close then next element will be identified
     *
     * @throws Throwable the throwable
     */
    public void waitcloseBusyIcon() throws Throwable {


        By busyIcon = By.xpath("//span[@class='busyIcon']");
        dynamicWaitByLocator(By.id("busyiconbutton"), 3);
        for (int i = 1; i <= 100; i++) {
            try {
                // if error 500 displayed stop the loop. temporary code need to remove.
                if (internalServerErrorHandler()) {


                    break;
                }
                System.out.println("After runtime exception");
                System.out.println("Waiting for closing spinner: " + i);
                if (driver.findElement(busyIcon).isDisplayed()) {
                    dynamicWaitByLocator(By.id("busyiconbutton"), 1);
                } else {
                    break;
                }
            } catch (Exception e) {
                dynamicWaitByLocator(By.id("busyiconbutton"), 1);
                //e.printStackTrace();
                break;
            }
        }

       /* int icounter = 0;
        do {
            try {
                if (!isElementPresent(busyIcon, "Wait for Element : Busy Icon")) {
                    DynamicWait(busyIcon);
                    if (!isElementPresent(busyIcon, "Wait for Element : Busy Icon")){
                        break;
                    }

                } else {
                    icounter = icounter + 1;
                }
                if (icounter >= 10) {
                    break;
                }

            } catch (Exception e) {
                LOG.info("Retrying for the object :: " + busyIcon
                        + " :: Iteration : " + icounter);
            }

        } while (icounter <= 10);
*/
    }


    public void closeBusyIconInNC(By locator) throws InterruptedException {

        for (int i = 1; i <= 30; i++) {
            try {
                if (driver.findElement(locator).isDisplayed()) {
                    dynamicWaitByLocator(By.id("busyiconbutton"), 1);
                } else {
                    break;
                }
            } catch (Exception e) {
                dynamicWaitByLocator(By.id("busyiconbutton"), 1);

                break;

            }
        }
    }


    public void switchToLastWindow() throws Throwable {

        try {
            Set<String> windowList = driver.getWindowHandles();
            for (String window : windowList) {
                List<Object> Windows = Arrays.asList(windowList.toArray());
                driver.switchTo().window(Windows.get(1).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void switchToParentWindow() throws Throwable {

        try {
            Set<String> windowList = driver.getWindowHandles();
            List<Object> Windows = Arrays.asList(windowList.toArray());
            driver.switchTo().window(Windows.get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * finding new position
     *
     * @throws Throwable the throwable
     */
    public void VerfifyNewPosition(Hashtable<String, String> data) throws Throwable {
        try {
            dynamicWait(By.xpath(".//*[@id='laborScheduleTable']/tbody/tr//td[contains(text(),'" + position + "')]"));
            isElementPresent(By.xpath(".//*[@id='laborScheduleTable']/tbody/tr//td[contains(text(),'" + position + "')]"), "Position", true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * finding  position
     *
     * @throws Throwable the throwable
     */
    public void VerfifyPositionAfterUncheckAltLocations(Hashtable<String, String> data) throws Throwable {
        try {

            isElementPresent(By.xpath(".//*[@id='laborScheduleTable']/tbody/tr//td[contains(text(),'" + position + "')]"), "Position", false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Description:uploadFile
     * uploadFile
     *
     * @throws Throwable the throwable
     * @author :  GallopAuthor002
     */

    public static void uploadFile(String fileLocation) {
        try {
            //Setting clipboard with file location
            setClipboardData(fileLocation);
            //native key strokes for CTRL, V and ENTER keys
            Robot robot = new Robot();

            //  robot.keyPress(KeyEvent.VK_ENTER);
            // robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            // C:/Users/E002734/Desktop/crunchtime.jpg
            robot.keyPress(KeyEvent.VK_V);

            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception exp) {

        }
    }

    /**
     * Description:saveFile
     * saveFile
     *
     * @throws Throwable the throwable
     * @author :  GallopAuthor008
     */

    public static void saveFile(String fileAction) {
        try {


            //Setting clipboard with file location
            //setClipboardData(fileLocation);
            Screen screen = new Screen();
            if (fileAction.equals("Save")) {
                screen.click("Images/SaveFile.png");
            } else if (fileAction.equals("Cancel")) {
                screen.click("Images/CancelFile.png");
            } else if (fileAction.equals("Open")) {
                screen.click("Images/OpenFile.png");


            }


        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }


    /**
     * Description:setClipboardData
     * setClipboardData
     *
     * @throws Throwable the throwable
     */
    public static void setClipboardData(String string) {
        //StringSelection is a class that can be used for copy and paste operations.
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    /**
     * Reusable method to read data from particular column
     *
     * @return : void
     * @throws Throwable the throwable
     * @param1 : (String) filePath, sheetName
     * @param2 : (String) colNumber
     * @Behavior: readExcelDataFromAColumn
     * @author : GallopAuthor007
     */
    public ArrayList<String> readExcelDataFromAColumn(String filePath, String sheetName, int rowNum, int colNumber) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        ArrayList<String> al = new ArrayList<String>();
        for (int i = rowNum; i <= rowCount; i++) {
            if (sheet.getRow(i).getCell(colNumber).getCellType() == Cell.CELL_TYPE_STRING) {
                al.add(sheet.getRow(i).getCell(colNumber).getStringCellValue());
            } else if (sheet.getRow(i).getCell(colNumber).getCellType() == Cell.CELL_TYPE_BLANK) {
                al.add("");
            } else if (sheet.getRow(i).getCell(colNumber).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                al.add(sheet.getRow(i).getCell(colNumber).getRawValue());
            } else {
                System.out.println("Invalid format in excel");
            }
        }
        return al;


    }


    public void clickShowQuotedTextInGmail(String employee) {

        try {
            driver.findElement(By.xpath("//div[text()='Hi " + employee + ",']//a[text()='- Show quoted text -']")).click();
        } catch (Exception e) {


        }
    }

    public void deleteEmail() throws Throwable {
        if (driver.findElement(By.cssSelector("span.ts")).isDisplayed()) {
            click(By.xpath("//span[@class='ts']/preceding::input[1]"), "Check box");
            click(By.xpath("//input[@value='Delete']"), "delete button");

        }
    }


    public boolean verifyPDFContent(String reqTextInPDF) {

        boolean flag = false;

        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        String parsedText = null;


        try {
            File file = new File(System.getProperty("user.dir") + "\\Downloads//employeeBreaks.pdf");
            PDFParser parser = new PDFParser(new FileInputStream(file));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(1);

            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
        } catch (MalformedURLException e2) {
            System.err.println("URL string could not be parsed " + e2.getMessage());
        } catch (IOException e) {
            System.err.println("Unable to open PDF Parser. " + e.getMessage());

            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (pdDoc != null)
                    pdDoc.close();
            } catch (Exception e1) {
                e.printStackTrace();


            }
        }

        System.out.println("+++++++++++++++++");
        System.out.println(parsedText);
        System.out.println("+++++++++++++++++");

        if (parsedText.contains(reqTextInPDF)) {
            flag = true;

        }

        return flag;

    }

    /**
     * @throws Throwable the throwable
     * @description:Clicking on Pick ups button in available shifts
     * @method: clickPickUpsInAvailableShifts
     */
    public void clickPickUpsInAvailableShifts(Hashtable<String, String> data)
            throws Throwable {

        click(By.xpath("(//table[@id='availableShiftsTable']//span[contains(text(),'" + date + "')]//..//..//following::button[text()='Pick Up'])[1]"), "PickUps");
    }


    public void clickDateInPickUps(Hashtable<String, String> data)
            throws Throwable {

        try {
            click(By.xpath("//table[@id='activityLogTable']//span[text()='Pick Ups']//..//..//following::span[contains(text(),'" + date + "')]"), "Pickups");
            waitcloseBusyIcon();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);


        }
    }


    public boolean internalServerErrorHandler() throws Throwable {
        LOG.info("++++++++++++++++++++++++++++++ internal Server Error Handler +++++++++++++++++++++++++++++++++++++++++");
        boolean flag = false;
        try {
            if (getElementsSize(By.xpath("//div[@id='errorPopUp-popup']//a[text()='OK']")) != 0) {
                driver.findElement(By.xpath("//div[@id='errorPopUp-popup']//a[text()='OK']")).click();
                LOG.info("Time stamp is: " + System.currentTimeMillis());
                System.out.println("Before runtime exception");
                flag = true;


                reporter.failureReport("Internal server error", "Internal server error is displayed");
            }
        } catch (Exception e) {
        }
        return flag;
    }

    public void waitforElementHide(By locator) throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }


    /**
     * @param : (String) fileName
     * @return : ArrayList
     * @throws Throwable the throwable
     * @Behavior: readTextFile
     */
    public List<String> readTextFile(String fileName) throws Throwable {
        File f = new File(fileName);
        List<String> al = new ArrayList<String>();
        if (f.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            try {
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    al.add(sCurrentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            reporter.failureReport("Text file:: ", "Text file does not exist");
        }
        return al;
    }

    /**
     * @throws Throwable the throwable
     * @description:Clicking on publish button in manager schedule with Date
     * @method: clickPublishinManagerSchedulewithDate
     */
    public void clickPublishinManagerSchedulewithDate(String date)
            throws Throwable {

        try {
            scrollToWebElement(By.xpath(".//*[@id='manageSchedulesTable']//button[text()='View']/../..//td[text()='" + date + "']/..//button[text()='Publish']"));
            click(By.xpath(".//*[@id='manageSchedulesTable']//button[text()='View']/../..//td[text()='" + date + "']/..//button[text()='Publish']"), "Publish");
            waitcloseBusyIcon();
            if (isElementPresent(By.xpath(".//*[@id='openShiftsAlertDialog']//a[text()='Publish & Post Open Shifts']"), "")) {
                scrollToWebElement(By.xpath(".//*[@id='openShiftsAlertDialog']//a[text()='Publish & Post Open Shifts']"));
                JSClick(By.xpath(".//*[@id='openShiftsAlertDialog']//a[text()='Publish & Post Open Shifts']"), "Publish");
                if (isElementPresent(By.xpath("//div[@id='errorListDialog']//a[contains(text(),'Publish')]"), "")) {
                    scrollToWebElement(By.xpath("//div[@id='errorListDialog']//a[contains(text(),'Publish')]"));
                    JSClick(By.xpath("//div[@id='errorListDialog']//a[contains(text(),'Publish')]"), "Publish");
                    waitcloseBusyIcon();
                    JSClick(By.xpath(".//*[@id='scheduleActionCompletedPopup']//a[text()='OK']"), "Ok");
                } else {
                    JSClick(By.xpath("(//*[@id='publishConfirmationPopup']//a[text()='Yes'])[last()]"), "yes");
                    waitcloseBusyIcon();
                    JSClick(By.xpath(".//*[@id='scheduleActionCompletedPopup']//a[text()='OK']"), "Ok");
                }
            } else if (isElementPresent(By.xpath("//div[@id='errorListDialog']//a[contains(text(),'Publish')]"), "")) {

                JSClick(By.xpath("//div[@id='errorListDialog']//a[contains(text(),'Publish')]"), "Publish");
                waitcloseBusyIcon();
                JSClick(By.xpath(".//*[@id='scheduleActionCompletedPopup']//a[text()='OK']"), "Ok");
            } else {
                verifyElementPresent(By.xpath("//*[@id='publishConfirmationPopup']/[text()='Are you sure you want to publish this schedule?']"), "Publish Confirmation Popup", true);
                JSClick(By.xpath("(//*[@id='publishConfirmationPopup']//a[text()='Yes'])[last()]"), "yes");
                waitcloseBusyIcon();
                JSClick(By.xpath(".//*[@id='scheduleActionCompletedPopup']//a[text()='OK']"), "Ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @throws Throwable the throwable
     * @description: SelectWeek Ending Date in Labor Schedule
     * @method: ncSelectWeekEndingDate
     */
    public String ncSelectWeekEndingDate()
            throws Throwable {

        Select select = null;


        try {


            select = new Select(driver.findElement(By.xpath(".//*[@id='weekEndingDate']")));
            List<WebElement> rows_table = select.getOptions();
            int rows_count = rows_table.size();
            select.selectByIndex(rows_count - 1);


        } catch (Exception e) {
            e.printStackTrace();


        }

        return (select.getFirstSelectedOption().getText());


    }


    /**
     * Reusable method to select left side menu items in TWX
     */
    public void selectTWXMenuOption(By locator, By waitLocator, String message) throws Throwable {
        JSClickUntil(locator, waitLocator, message);
    }


    /**
     * Reusable method to convert date format from one to other
     */
    public String getConvertedDataFormat(String actDate, String dateFormat, String convertDateFormat) throws ParseException {
        String dateStr = actDate;
        DateFormat srcDf = new SimpleDateFormat(dateFormat);


        // parse the date string into Date object
        Date date = srcDf.parse(dateStr);


        DateFormat destDf = new SimpleDateFormat(convertDateFormat);

        // format the date into another format
        dateStr = destDf.format(date);
        System.out.println("Converted date is : " + dateStr);
        return dateStr;
    }

   


    
    
}
