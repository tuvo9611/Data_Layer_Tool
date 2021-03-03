package com.example.demo.common;

import java.util.HashMap;
import java.util.Map;

public class ConvertHttpMethod {
	private ConvertHttpMethod() {
		// empty constructor
	}

	public static String httpMethodMapping(String key) {
		return httpMethodData().get(key);
	}

	private static Map<String, String> httpMethodData() {
		Map<String, String> mapping = new HashMap<>();
		mapping.put("GET", "GetMapping");
		mapping.put("PUT", "PutMapping");
		mapping.put("POST", "PostMapping");
		mapping.put("DELETE", "DeleteMapping");
		return mapping;
	}
}
