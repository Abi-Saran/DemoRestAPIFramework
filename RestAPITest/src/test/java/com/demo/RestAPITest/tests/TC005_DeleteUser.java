package com.demo.RestAPITest.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import com.demo.RestAPITest.common.CommonLibraryRestAPI;

public class TC005_DeleteUser extends CommonLibraryRestAPI {
	
	@Test
	public void deleteUser() throws IOException {
		log.info(separator);
		log.info("Starting DeleteUser with DELETE request...");
		
		String userId = readFromFile();
		String baseURI = getConfigProperty("baseURI") + userId;
		log.info("Update URI for PATCH request: " + baseURI);

		response = given()
						.auth()
						.oauth2(getConfigProperty("accessToken"))
						.delete(baseURI);

		printAllResponse();
		log.info("Statuscode: " + response.getStatusCode());
		log.info("Statusline: " + response.getStatusLine());

		/* initializeBaseUri("https://gorest.co.in/public/v2/users/4425");
		
		requestSpec.header("Accept", "application/json");
		requestSpec.header("Content-Type", "application/json");
		requestSpec.header("Authorization", "Bearer ad64ff6f1e0976dac30426c7b5e5b866322184b47ce7d233b3b70248d5d8a00d");
		
		initializeRequest("delete");
		
		printAllResponse();
//		log.info(separator); */
	}

}
