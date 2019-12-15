package com.abhi.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	
	public int RESPONSE_STATUS_CODE_200 = 200;
	public int RESPONSE_STATUS_CODE_204 = 204;
	public int RESPONSE_STATUS_CODE_500 = 500;
	public int RESPONSE_STATUS_CODE_400 = 400;
	public int RESPONSE_STATUS_CODE_401 = 401;
	public int RESPONSE_STATUS_CODE_201 = 201;
	
	public Properties properties;
	
	public TestBase() {		
		try {
			properties = new Properties();
			FileInputStream fis = new FileInputStream("/Users/b0097042/eclipse-workspace/RestAPIFramework/src/main/java/com/abhi/config/config.properties");
//			FileInputStream fis = new FileInputStream(System.clearProperty("user.dir") + "/src/main/java/com/abhi/config/config.properties");
			properties.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
