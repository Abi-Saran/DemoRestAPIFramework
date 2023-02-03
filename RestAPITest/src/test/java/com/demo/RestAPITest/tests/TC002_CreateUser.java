package com.demo.RestAPITest.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.demo.RestAPITest.common.CommonLibraryRestAPI;

public class TC002_CreateUser extends CommonLibraryRestAPI {
	
	public String methodName;
	
	@Test(dataProviderClass=CommonLibraryRestAPI.class,dataProvider="inputData")
	public void validData(String sNo, String name, String email, String gender, String status) throws IOException {
		log.info(separator);
		log.info("Starting CreateUser with POST request...");
		methodName = new Throwable().getStackTrace()[0].getMethodName(); 
		log.info("Currently executing method: " + methodName);
		
		response = given()
						.auth()
						.oauth2(getConfigProperty("accessToken"))
						.formParams("name", name)
						.formParams("email", email)
						.formParams("gender", gender)
						.formParams("status", status)
						.post();
		printAllResponse();
		log.info("Statuscode: " + response.getStatusCode());
		log.info("Statusline: " + response.getStatusLine());
		String responseBody = response.getBody().asString();
		
		int userId = getIntValueOfNode("id");
		System.out.println(userId);
		
		writeToFile(String.valueOf(userId));
		
		if (responseBody.contains("email") && responseBody.contains("has already been taken")) {
			log.info("inside if condition");
			writeToExcel(methodName, "Failed", Integer.parseInt(sNo), 5);
		} else {
			writeToExcel(methodName, "Passed", Integer.parseInt(sNo), 5);
		}
		
//		log.info("Value of the node email: " + getStringValueOfNode("email"));
		getStatusCode();
	}	

	
	
	@Test(dataProviderClass=CommonLibraryRestAPI.class,dataProvider="inputData")
	public void inValidData(String sNo, String name, String email, String gender, String status) throws IOException {
		log.info(separator);
		log.info("Starting CreateUser with POST request...");
		methodName = new Throwable().getStackTrace()[0].getMethodName(); 
		log.info("Currently executing method: " + methodName);
		
		response = given()
						.auth()
						.oauth2(getConfigProperty("accessToken"))
						.formParams("name", name)
						.formParams("email", email)
						.formParams("gender", gender)
						.formParams("status", status)
						.post();
		log.info("before response");
		printAllResponse();
		log.info("after response");

		String responseBody = response.getBody().asString();
		
		log.info("Statuscode: " + response.getStatusCode());
		log.info("Statusline: " + response.getStatusLine());
		
		log.info("Response Body: " + responseBody);
		
		if (responseBody.contains("email") && responseBody.contains("has already been taken")) {
			writeToExcel(methodName, "Failed", Integer.parseInt(sNo), 5);
		} else {
			writeToExcel(methodName, "Passed", Integer.parseInt(sNo), 5);
		}
		
		int statusCode = getStatusCode();
		if (statusCode == 422) {
			if ((getStringValueOfNode("[0].field").equals("email")) && (getStringValueOfNode("[0].message").equals("has already been taken"))) {
				Assert.fail("Email has already been taken");
			}
		}
	}
//		
//		/**
//		 * To check whether a Key is available in response or not
//		 **/
////		 JSONObject jsonObject = new JSONObject(response.asString());
////		System.out.println(jsonObject.has("id"));
////		Assert.assertTrue(jsonObject.has("id"),"ID key is not present in the JSON response");   
//		
//		/**
//		 * Basic Authentication 
//		 **/
////		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
////		authScheme.setUserName("");
////		authScheme.setPassword("");
////		RestAssured.authentication = authScheme; 
//	} 
	
		/* @Test
		public void oldCode() {
			initializeBaseUri("https://gorest.co.in/public/v2/users");
			requestSpec.header("Accept", "application/json");
			requestSpec.header("Content-Type", "application/json");
			requestSpec.header("Authorization", "Bearer ad64ff6f1e0976dac30426c7b5e5b866322184b47ce7d233b3b70248d5d8a00d");
					
			HashMap<String, String> inputData = new HashMap<>();
			inputData.put("name", "AbiSarvan");
			inputData.put("gender", "male");
			inputData.put("email", "abi.saran.8@gmail.com");
			inputData.put("status", "active");
			
			JSONObject jsonData = new JSONObject(inputData);
			
			requestSpec.body(jsonData.toJSONString());
			
			initializeRequest("post");
			
			printAllResponse();
			
			int userId = getIntValueOfNode("id");
			System.out.println(userId);
			
			writeToFile(String.valueOf(userId));
	//		log.info(separator);
		} */
	
	
		
}
