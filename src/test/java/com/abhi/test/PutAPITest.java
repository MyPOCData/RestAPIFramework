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
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class PutAPITest extends TestBase{
	
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		serviceUrl = properties.getProperty("URL");
		apiUrl = properties.getProperty("deleteServiceURL");
		url = serviceUrl + apiUrl;		
	}
	
	@Test
	public void putAPITest() throws JsonGenerationException, JsonMappingException, IOException, NoSuchMethodException, SecurityException{
		restClient = new RestClient();
		String usersJsonString = restClient.getEntityBody(new Users("morpheus","zion resident"));		
		closebaleHttpResponse = restClient.put(url, usersJsonString, restClient.setHeaderMap()); //call the API

		//1. status code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_200);
		
		//2. JsonString:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from API is:"+ responseJson);
		
		//single value assertion:
		String updatedAt = TestUtil.getValueByJPath(responseJson, TestUtil.getSingleResponceValue(null,0,"updatedAt"));
		System.out.println(updatedAt);
		
		String job = TestUtil.getValueByJPath(responseJson, TestUtil.getSingleResponceValue(null,0,"job"));
		System.out.println(job);	
	}

}
