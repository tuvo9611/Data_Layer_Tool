package com.example.demo.dto.request;

import java.util.Arrays;
import java.util.Objects;

public enum TemplateServiceFrontEndRequestKey {
	PAGE_NAME(6, "PAGE_NAME"), API_ID(1, "API_ID"), METHOD_NAME(2, "METHOD_NAME"), HTTP_METHOD(3, "HTTP_METHOD"),
	URL(4, "URL");

	private int value;
	private String name;

	TemplateServiceFrontEndRequestKey(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static TemplateServiceFrontEndRequestKey getByValue(int value) {
		return Arrays.stream(TemplateServiceFrontEndRequestKey.values()).filter(x -> Objects.equals(x.value, value))
				.findFirst().orElse(null);
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
