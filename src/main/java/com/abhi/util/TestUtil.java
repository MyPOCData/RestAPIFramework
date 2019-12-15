package com.abhi.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestUtil {
	
	public static String getValueByJPath(JSONObject responsejson, String jpath){
		Object obj = responsejson;
		for(String s : jpath.split("/")) 
			if(!s.isEmpty()) 
				if(!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if(s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
	}
	
	
	public static String getSingleResponceValue(String arrayName, int index, String key) {		
		String jsonPath = null;
		if(arrayName!=null && key!=null) {
			jsonPath = "/"+arrayName+"["+index+"]"+"/"+key;			
		}else if(arrayName==null && key!=null) {
			jsonPath = "/"+key;
		}else if(arrayName==null && key==null) {
			jsonPath = null;
		}
		return jsonPath;
	}

}
