package com.abhi.client;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient {
	
	
	//1. GET Method without Headers:
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException{		
		CloseableHttpClient httpClient = HttpClients.createDefault(); //create httpclient link
		HttpGet httpget = new HttpGet(url); //http get request
		CloseableHttpResponse closebaleHttpResponse =  httpClient.execute(httpget); //hit the GET URL		
		return closebaleHttpResponse;			
	}
		
	//2. GET Method with Headers:
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closebaleHttpResponse =  httpClient.execute(httpget); //hit the GET URL
		return closebaleHttpResponse;				
	}
		
	//3. POST Method:
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url); //http post request
		httppost.setEntity(new StringEntity(entityString)); //for payload				
		//for headers:
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httppost.addHeader(entry.getKey(), entry.getValue());
		}		
		CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httppost);
		return closebaleHttpResponse;				
	}
	
	//4. Delete Method:
	public CloseableHttpResponse delete(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpdelete = new HttpDelete(url);
		CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httpdelete);
		return closebaleHttpResponse;	
	}
	
	//5. Delete with Header:
	public CloseableHttpResponse delete(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpdelete = new HttpDelete(url);
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpdelete.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httpdelete);
		return closebaleHttpResponse;
	}
	
	//6. Put Method :
	public CloseableHttpResponse put(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpput = new HttpPut(url); //http post request
		httpput.setEntity(new StringEntity(entityString)); //for payload				
		//for headers:
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpput.addHeader(entry.getKey(), entry.getValue());
		}		
		CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httpput);
		return closebaleHttpResponse;	
	}
	
	// Get Status Code
	public int getStatus(CloseableHttpResponse closebaleHttpResponse) {		
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		return statusCode;
	}
	
	// Get JSON Response
	public JSONObject getJsonResponse(CloseableHttpResponse closebaleHttpResponse) throws ParseException, IOException {
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");		
		JSONObject responseJson = new JSONObject(responseString);
		return responseJson;
	}
	
	// Get Header Map
	public HashMap<String, String> getAllHeader(CloseableHttpResponse closebaleHttpResponse){
		Header[] headersArray =  closebaleHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();	
		for(Header header : headersArray){
			allHeaders.put(header.getName(), header.getValue());
		}	
		return allHeaders;
	}
	
	// Set Header Map
	public HashMap<String, String> setHeaderMap(){
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
//		headerMap.put("username", "test@amazon.com");
//		headerMap.put("password", "test213");
//		headerMap.put("Auth Token", "12345");
		return headerMap;
	}
	
	// Set Request Entity Body (In Post Call)
	public String getEntityBody(Object c) throws IOException {		
		ObjectMapper mapper = new ObjectMapper();
		Object obj = c;
		mapper.writeValue(new File("/Users/b0097042/eclipse-workspace/RestAPIFramework/src/main/java/com/abhi/data/users.json"), obj);
		String usersJsonString = mapper.writeValueAsString(obj);		
		return usersJsonString;
	}
}
