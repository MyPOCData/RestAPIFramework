package com.abhi.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.abhi.base.TestBase;
import com.abhi.client.RestClient;
import com.abhi.data.Users;
import com.abhi.util.TestUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class PostAPITest extends TestBase{
	
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;
	ExtentReports extent;
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		serviceUrl = properties.getProperty("URL");
		apiUrl = properties.getProperty("serviceURL");
		url = serviceUrl + apiUrl;		
	}
	
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException, NoSuchMethodException, SecurityException{
		restClient = new RestClient();
		
//		ExtentTest test = extent.createTest("Post API Method", "Execute Post Method and verify response");
		
		String usersJsonString = restClient.getEntityBody(new Users("morpheus","leader"));		
		closebaleHttpResponse = restClient.post(url, usersJsonString, restClient.setHeaderMap()); //call the API

		//1. status code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);
//		test.log(Status.INFO, "Status code : " + statusCode);
		
		//2. JsonString:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from API is:"+ responseJson);
		
		//single value assertion:
		String createdAt = TestUtil.getValueByJPath(responseJson, TestUtil.getSingleResponceValue(null,0,"createdAt"));
		System.out.println(createdAt);
		
		String id = TestUtil.getValueByJPath(responseJson, TestUtil.getSingleResponceValue(null,0,"id"));
		System.out.println(id);	
	}

}
