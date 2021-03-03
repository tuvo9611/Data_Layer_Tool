package com.example.demo.dto.request;

import java.util.Arrays;
import java.util.Objects;

public enum TemplateControllerRequestKey {
	API_ID(1, "API_ID"), METHOD_NAME(2, "METHOD_NAME"), HTTP_METHOD(3, "HTTP_METHOD"), URL_API(4, "URL_API"),
	PARAMETERS(5, "PARAMETERS"), REQUEST_DTO_ID(6, "REQUEST_DTO_ID"), RESPONSE_DTO_ID(7, "RESPONSE_DTO_ID");

	private int value;
	private String name;

	TemplateControllerRequestKey(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static TemplateControllerRequestKey getByValue(int value) {
		return Arrays.stream(TemplateControllerRequestKey.values()).filter(x -> Objects.equals(x.value, value))
				.findFirst().orElse(null);
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}