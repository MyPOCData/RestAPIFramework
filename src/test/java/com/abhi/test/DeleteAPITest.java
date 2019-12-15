package com.abhi.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.abhi.base.TestBase;
import com.abhi.client.RestClient;

public class DeleteAPITest extends TestBase{
	
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;
	
	
	@BeforeMethod
	public void setup() {
		testBase = new TestBase();
		serviceUrl = properties.getProperty("URL");
		apiUrl = properties.getProperty("deleteServiceURL");		
		url = serviceUrl + apiUrl;		
	}
	
	@Test
	public void removeUser() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closebaleHttpResponse = restClient.delete(url);
		
		int statusCode = restClient.getStatus(closebaleHttpResponse);		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_204, "Status code is not 204");
		
		String responseJson = restClient.getEntityBody(closebaleHttpResponse);
		System.out.println(responseJson);
		Assert.assertTrue(responseJson.contains("\"entity\":null"));		
	}

}
