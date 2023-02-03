package com.demo.RestAPITest.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import com.demo.RestAPITest.common.CommonLibraryRestAPI;

public class TC004_UpdateUser_PATCH extends CommonLibraryRestAPI {
	
	@Test
	public void updateUser() throws IOException {
		log.info(separator);
		log.info("Starting UpdateUser with PATCH request...");
		String userId = readFromFile();
		String baseURI = getConfigProperty("baseURI") + userId;
		log.info("Update URI for PATCH request: " + baseURI);
		
		response = given()
				.auth()
				.oauth2(getConfigProperty("accessToken"))
				.formParams("email", "saran.qa8@microsoft.com")
				.patch(baseURI);

		printAllResponse();
		log.info("Statuscode: " + response.getStatusCode());
		log.info("Statusline: " + response.getStatusLine());
		
		/* initializeBaseUri("https://gorest.co.in/public/v2/users/" + userId);
		requestSpec.header("Accept", "application/json");
		requestSpec.header("Content-Type", "application/json");
		requestSpec.header("Authorization", "Bearer ad64ff6f1e0976dac30426c7b5e5b866322184b47ce7d233b3b70248d5d8a00d");
		
		HashMap<String, String> map = new HashMap<>();
		map.put("email", "abi.saran.6@yahoo.com");
		
		JSONObject inputData = new JSONObject(map);
		
		requestSpec.body(inputData.toJSONString());
		
		initializeRequest("patch");
		
		printAllResponse();
//		log.info(separator); */
	}

}
