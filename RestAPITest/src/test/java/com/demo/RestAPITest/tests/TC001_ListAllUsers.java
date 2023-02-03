package com.demo.RestAPITest.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import com.demo.RestAPITest.common.CommonLibraryRestAPI;

public class TC001_ListAllUsers extends CommonLibraryRestAPI {

	@Test(priority=1)
	public void listAllUsers() throws IOException {
		log.info(separator);
		log.info("Starting ListAllUsers with GET request...");
		
		response = given()
			.auth()
			.oauth2(getConfigProperty("accessToken"))
			.get();
		
		printAllResponse();

		/* //for all users
		initializeBaseUri("https://gorest.co.in/public/v2/users");
		requestSpec.header("Authorization", "Bearer ad64ff6f1e0976dac30426c7b5e5b866322184b47ce7d233b3b70248d5d8a00d");
		initializeRequest("GET");
		
		//for specific user
//		getRequest("https://gorest.co.in/public/v2/users/4125", "GET");
		
		printAllResponse();
		
		getHeaderValue("Server");
		
		getContentType();
		
		printAllHeaders();
		
//		log.info(separator); */
	}
	
	
	
}
