package com.example.demo.common;

import java.util.HashMap;
import java.util.Map;

public class ConvertDataType {
	private ConvertDataType() {
		// empty constructor
	}
	
	public static String postgresToJava(String key){
		return postgresData().get(key);
	}
	
	private static Map<String, String> postgresData(){
		Map<String,String> mapping = new HashMap<>();
		mapping.put("BIGINT", "Long");
		mapping.put("CHARACTER VARYING", "String");
		mapping.put("BOOLEAN", "Boolean");
		mapping.put("JSONB", "String");
		mapping.put("INTEGER", "Integer");
		mapping.put("NUMERIC", "Double");
		mapping.put("TEXT", "String");
		mapping.put("TIMESTAMP WITHOUT TIME ZONE", "Date");
	    return mapping;
	}
	
	public static Map<String, String> excelTemplateData(){
		Map<String,String> mapping = new HashMap<>();
		mapping.put("VARCHAR", "CHARACTER VARYING");
	    return mapping;
	}
	
	public static String templateToPostgres(String key){
		return excelTemplateData().get(key);
	} 
}
