package com.example.demo.common.util;

import com.example.demo.common.CommonConstants;

public class StringUtil {
	private StringUtil() {
		// empty constructor
	}

	/*
	 * Format value remove space and upper case
	 */
	public static String formatValueLower(String value) {
		if (value != null) {
			value = value.replace(CommonConstants.WHITE_SPACE_JAPAN, CommonConstants.WHITE_SPACE).trim().toLowerCase();
		}
		return value;
	}

	/*
	 * Format value remove space and upper case
	 */
	public static String formatValueUpper(String value) {
		if (value != null) {
			value = value.replace(CommonConstants.WHITE_SPACE_JAPAN, CommonConstants.WHITE_SPACE).trim().toUpperCase();
		}
		return value;
	}

	public static String trimValue(String value) {
		if (value != null) {
			value = value.replace(CommonConstants.WHITE_SPACE_JAPAN, CommonConstants.WHITE_SPACE).trim();
		}
		return value;
	}

	/**
	 * Pascal to Camel Case
	 * @param value (ex: HelloClass -> helloClass)
	 * @return
	 */
	public static String pascalToCamelCase(String value) {
		if (value != null) {
			value = value.substring(0, 1).toLowerCase() + value.substring(1);
		}
		return value;
	}

	/**
	 * Convert camel case to snake case
	 * 
	 * @param value (ex: HelloClass -> Hello_Class)
	 * @return
	 */
	public static String camelToSnakeCase(String value) {
		String regex = "([a-z])([A-Z]+)";
		String replacement = "$1_$2";

		value = value.replaceAll(regex, replacement).toLowerCase();
		return value;
	}
}
