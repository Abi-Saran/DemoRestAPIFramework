package com.demo.RestAPITest.utilities;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.demo.RestAPITest.common.CommonLibraryRestAPI;


public class TestListenersRestAPI extends CommonLibraryRestAPI implements ITestListener {
	
	private static ExtentReports extentReports = ExtentManagerRestAPI.createInstance();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	public Logger logger = Logger.getLogger(TestListenersRestAPI.class);
	
	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extentReports.createTest(result.getTestClass().getName() + " :: " + result.getMethod().getMethodName());
		extentTest.set(test);
		logger.info("In Test Class-'" + result.getTestClass().getName() + "', Testcase-'" + result.getMethod().getMethodName() + "' started...");
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		String logText = "<b> Test Method " + result.getMethod().getMethodName() + " Successful<b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTest.get().log(Status.PASS, markup);
		logger.info("Test " + result.getName() + " Success");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		extentTest.get().fail("<details><summary><b><font color=red> Response from RestAPI service:</font></b></summary>" + response.prettyPrint() +  " \n"
							+ "<summary><b><font color=red> Exception occurred:</font></b></summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details> \n");
		logger.info("Exception Message: " + exceptionMessage);
//		WebDriver driver = ((CommonLibrary)result.getInstance()).driver;
//		String path = takeScreenshot(CommonLibrary.driver, result.getMethod().getMethodName());
//		try {
//			extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>");
//		} catch (Exception e) {
//			extentTest.get().fail("Test failed, cannot attach screenshot");
//		}
		
		String logText = "<b> TestMethod " + methodName + " Failed</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, markup);
		logger.info("Test " + result.getName() + " Failed");
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		String logText = "<b> Test Method " + result.getMethod().getMethodName() + " Skipped</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		extentTest.get().log(Status.SKIP, markup);
		logger.info("Test " + result.getName() + " Skipped");
//		throw new SkipException("Test case Skipped") ;
	}
	
	@Override
	public void onStart(ITestContext context) {
		
	}
	
	@Override
	public void onFinish(ITestContext context) {
		if (extentReports != null) {
			extentReports.flush();
		}
	}
	


	
}
