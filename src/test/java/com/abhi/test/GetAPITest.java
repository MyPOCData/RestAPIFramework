package com.abhi.test;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.abhi.base.TestBase;
import com.abhi.client.RestClient;
import com.abhi.util.TestUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class GetAPITest extends TestBase{
	
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;
	
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	
	@BeforeSuite
	public void setup(){
		htmlReporter = new ExtentHtmlReporter("extent.html");
	    extent = new ExtentReports();
	    extent.attachReporter(htmlReporter);
	}
	
	@AfterSuite
	public void clear() {
		extent.flush();
	}
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		serviceUrl = properties.getProperty("URL");
		apiUrl = properties.getProperty("serviceURL");		
		url = serviceUrl + apiUrl;		
	}
	
	
	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		closebaleHttpResponse = restClient.get(url);
		
		ExtentTest test = extent.createTest("Get API Method", "Execute Get Method and verify response");
		
		//a. Status Code:
		int statusCode = restClient.getStatus(closebaleHttpResponse);
		test.log(Status.INFO,"Status code is : " + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		//b. Json String:
		JSONObject responseJson = restClient.getJsonResponse(closebaleHttpResponse);
		
		//single value assertion:
		String perPageValue = TestUtil.getValueByJPath(responseJson, TestUtil.getSingleResponceValue(null,0,"per_page"));
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);

		//get the value from JSON ARRAY:
		String avatar = TestUtil.getValueByJPath(responseJson, TestUtil.getSingleResponceValue("data",2,"avatar"));
		System.out.println(avatar);
	
		System.out.println("Headers Array-->"+ restClient.getAllHeader(closebaleHttpResponse));		
	}
	
	
	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException{
		restClient = new RestClient();		
		closebaleHttpResponse = restClient.get(url,restClient.setHeaderMap());
		
		ExtentTest test = extent.createTest("Get API Method", "Execute Get Method and verify response");
		
		//a. Status Code:
		int statusCode = restClient.getStatus(closebaleHttpResponse);		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		//b. Json String:
		JSONObject responseJson = restClient.getJsonResponse(closebaleHttpResponse);
		
		//single value assertion:
		String totalValue = TestUtil.getValueByJPath(responseJson, TestUtil.getSingleResponceValue(null,0,"total"));
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		//get the value from JSON ARRAY:
		String firstName = TestUtil.getValueByJPath(responseJson, TestUtil.getSingleResponceValue("data",0,"first_name"));
		System.out.println(firstName);
		test.log(Status.INFO,"first_name : " + firstName);
				
		//c. All Headers
		System.out.println("Headers Array-->"+ restClient.getAllHeader(closebaleHttpResponse));	
	}
	
	
}
