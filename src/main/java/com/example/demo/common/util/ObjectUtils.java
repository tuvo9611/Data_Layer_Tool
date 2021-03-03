package com.example.demo.common.util;

public class ObjectUtils {
	
	private ObjectUtils() {
		// empty constructor
	}

	public static boolean isNotEmpty(Object object) {
		return !org.springframework.util.ObjectUtils.isEmpty(object);
	}

	public static boolean isEmpty(Object object) {
		return org.springframework.util.ObjectUtils.isEmpty(object);
	}
}
