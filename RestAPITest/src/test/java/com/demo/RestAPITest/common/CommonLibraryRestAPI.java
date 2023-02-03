package com.demo.RestAPITest.common;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonLibraryRestAPI {
	public Properties properties;
	public FileInputStream fis;
	public RequestSpecification requestSpec;
	public static Response response;
	public static Logger log;
	public String separator = StringUtils.repeat("*", 120);
	public XSSFWorkbook workBook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;
	public XSSFCell cell = null;

	@BeforeSuite
	public void initialization() throws IOException {
		log = Logger.getLogger(CommonLibraryRestAPI.class);
		PropertyConfigurator.configure("log4j.properties");
		log.info("Log4j initialized");
		
		RestAssured.baseURI = getConfigProperty("baseURI");
		log.info("Base URI = '" + getConfigProperty("baseURI") + "'");
	}
	
	public String getConfigProperty(String propertyName) throws IOException {
		properties = new Properties();
		fis = new FileInputStream("./src/test/resources/config.properties");
		properties.load(fis);
		String propertyValue = properties.getProperty(propertyName);
		return propertyValue;
	}
	
	
	@DataProvider(name="inputData")
	public String[][] getDataFromExcel(Method m) throws FileNotFoundException, IOException {
		workBook = new XSSFWorkbook(new FileInputStream(getConfigProperty("testDataExcelPath")));
		sheet = workBook.getSheet(m.getName());
		
		int rows = sheet.getLastRowNum() + 1;
		int cols = sheet.getRow(0).getLastCellNum() - 1;
		System.out.println("Rows: " + rows + ", Cols: " + cols);
		
		String[][] data = new String[rows - 1][cols];
		
		DataFormatter formatter = new DataFormatter();
		for (int i = 1; i < rows; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j < cols; j++) {
				cell = row.getCell(j);
				data[i - 1][j] = formatter.formatCellValue(cell);
			}
		}
		
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j] + " | ");
			}
			System.out.println("");
		}
		return data;
	}
	
	public void writeToExcel(String sheetName, String data, int rowNum, int colNum) throws FileNotFoundException, IOException {
		workBook = new XSSFWorkbook(new FileInputStream(getConfigProperty("testDataExcelPath")));
		sheet = workBook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.createCell(colNum);
		cell.setCellValue(data);
		log.info("Row Number " + rowNum);
		workBook.write(new FileOutputStream(getConfigProperty("testDataExcelPath")));
	}
	
	
	/* public void initializeRequest(String requestType) {
		switch(requestType.toUpperCase()) {
		case "GET":
			response = requestSpec.request(Method.GET);
			log.info("Request type is GET");
			break;
			
		case "POST":
			response = requestSpec.request(Method.POST);
			log.info("Request type is POST");
			break;
		
		case "PUT":
			response = requestSpec.request(Method.PUT);
			log.info("Request type is PUT");
			break;
		
		case "PATCH":
			response = requestSpec.request(Method.PATCH);
			log.info("Request type is PATCH");
			break;
		
		case "DELETE":
			response = requestSpec.request(Method.DELETE);
			log.info("Request type is DELETE");
			break;
		}
	} */
	
	public void printAllResponse() {
		log.info("Printing the response...");
		log.info(response.prettyPrint());
	}
	
	public String getContentType() {
		String contentType = response.header("Content-Type");
		log.info("Content-Type - " + contentType);
		return contentType;
	}
	
	public String getHeaderValue(String header) {
		String headerValue = response.getHeader(header);
		log.info("Value of the '" + header + "' header - " + headerValue);
		return headerValue;
	}
	
	public void checkStatusCode(int expectedCode) {
		int actualStatusCode = response.getStatusCode();
		log.info("Expected Status Code - " + expectedCode);
		log.info("Actual Status Code - " + actualStatusCode);
		Assert.assertEquals(actualStatusCode, expectedCode);
	}
	
	public int getStatusCode() {
		int statusCode = response.getStatusCode();
		log.info("Status code: " + statusCode);
		return statusCode;
	}
	
	public void getStatusLine(String expectedStatusLine) {
		String actualStatusLine = response.getStatusLine();
		log.info("Expected Status Line - " + expectedStatusLine);
		log.info("Actual Status Line - " + actualStatusLine);
		Assert.assertEquals(actualStatusLine, expectedStatusLine);
	}
	
	public String getStringValueOfNode(String node) {
		String nodeValue = response.jsonPath().get(node);
		log.info("Value of the '" + node + "' node - " + nodeValue);
		return nodeValue;
	}
	
	public int getIntValueOfNode(String node) {
		int nodeValue = response.jsonPath().get(node);
		log.info("Value of the '" + node + "' node - " + nodeValue);
		return nodeValue;
	}
	
	public void printAllHeaders() {
		log.info("**********Printing all headers**********");
		Headers allHeaders = response.headers();
		for (Header header : allHeaders) {
			System.out.println(header.getName() + " : " + header.getValue());
		}
		log.info("****************************************");
	}
	
	public void writeToFile(String content) throws IOException {
		FileWriter fw = new FileWriter("./src/test/resources/UserId.txt");
		fw.write(content);
		log.info("Newly created UserId successfully written to file");
		fw.close();
	}
	
	public String readFromFile() throws IOException {
		log.info("Reading content from ./src/test/resources/UserId.txt");
		String data = "";
		data = new String(Files.readAllBytes(Paths.get("./src/test/resources/UserId.txt")));
		return data;
	}
	
}
