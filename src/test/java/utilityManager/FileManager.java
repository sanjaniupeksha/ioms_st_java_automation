package utilityManager;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class FileManager {

	public static String screenshotsPath;
	public static String testResultsPath;
	static String currentDate;
	private static Logger logger = LoggerFactory.getLogger(FileManager.class);

	public static Properties readPropertyFile(String fileName) {
		Properties propertyFile = null;
		FileReader reader = null;
		try {
			reader = new FileReader(fileName);
			propertyFile = new Properties();
			propertyFile.load(reader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to read the properies file! ", e);
			//throw e;
		}
		return propertyFile;
	}

	
	public static List<Map<String, String>> readExcelFile(String apiName, String version) throws Exception {
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		List<Map<String, String>> excelData = new ArrayList<Map<String, String>>();
		try {
			fis = new FileInputStream(new File("src/main/resources/NegativeScenarios/NegativeScenarios_"+version+".xls"));
			wb = new HSSFWorkbook(fis);
			sheet = wb.getSheet(apiName);
			List<String> colNames = new ArrayList<String>();
			Row row = sheet.getRow(0);
			Iterator<Cell> iterator = row.cellIterator();
			DataFormatter df = new DataFormatter();
			while(iterator.hasNext()){
				String cellValue = "";
			     Cell cell = (Cell)iterator.next();
			     if(cell != null)
			     {
			    	 cellValue = df.formatCellValue(cell);
			     }
			     colNames.add(cellValue);
			}
			for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++)
			{
				Map<String, String> eachRowMap = new LinkedHashMap<String, String>();
				for(int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j ++)
				{
					eachRowMap.put(colNames.get(j), df.formatCellValue(sheet.getRow(i).getCell(j)));
				}
				excelData.add(eachRowMap);
			}
		} catch (IOException e) {
			logger.error("Unable to read the Excel file! ", e);
			throw e;
		}
		return excelData;
	}
	
	public static List<Map<String, String>> getDefectsInfoFromExcel() throws Exception {
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		List<Map<String, String>> excelData = new ArrayList<Map<String, String>>();
		try {
			fis = new FileInputStream(new File(Constants.DEFECTS_FILE_PATH));
			wb = new HSSFWorkbook(fis);
			sheet = wb.getSheet("Defects");
			List<String> colNames = new ArrayList<String>();
			Row row = sheet.getRow(0);
			Iterator<Cell> iterator = row.cellIterator();
			DataFormatter df = new DataFormatter();
			while(iterator.hasNext()){
				String cellValue = "";
			     Cell cell = (Cell)iterator.next();
			     if(cell != null)
			     {
			    	 cellValue = df.formatCellValue(cell);
			     }
			     colNames.add(cellValue);
			}
			for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++)
			{
				Map<String, String> eachRowMap = new LinkedHashMap<String, String>();
				for(int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j ++)
				{
					eachRowMap.put(colNames.get(j), df.formatCellValue(sheet.getRow(i).getCell(j)));
				}
				excelData.add(eachRowMap);
			}
		} catch (IOException e) {
			logger.error("Unable to read the Excel file! ", e);
			throw e;
		}
		return excelData;
	}

	public static void createScreenshotsFolder(String fname) {
		screenshotsPath = testResultsPath+"\\" + Constants.SCREENSHOTS_PATH + "\\" + fname;
		try {
			new File(screenshotsPath).mkdirs();
		} catch (Exception e) {
			logger.error("Error While creating the test results file, Path-> "+testResultsPath+ "\\" + fname +" Exception Details "+e);
		}
	}

	public static void createResultsFolder()
	{
		currentDate = LocalDate.now().toString();
		String currentTime = new SimpleDateFormat("HH_mm_ss").format(new Date());
		String environment = Constants.getEnvironment().toUpperCase();
		System.out.println("################# Test Execution Started in "+environment+" Environment ###############");
		testResultsPath = "TestResults\\"+currentDate + "\\" + currentTime+"_"+environment;
		try {
			new File(testResultsPath).mkdirs();
		} catch (Exception e) {
			logger.error("Error While creating the test results folder, Path-> "+testResultsPath +" Exception Details "+e);
		}
	}
}
