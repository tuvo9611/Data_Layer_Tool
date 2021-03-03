package com.example.demo.dto.request;

import java.util.Arrays;
import java.util.Objects;

public enum TemplateParameterControllerRequestKey {
	PARAMERTER_ID(2, "PARAMERTER_ID"), NAME(3, "NAME"), VALUE_TYPE(4, "VALUE_TYPE");

	private int value;
	private String name;

	TemplateParameterControllerRequestKey(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static TemplateParameterControllerRequestKey getByValue(int value) {
		return Arrays.stream(TemplateParameterControllerRequestKey.values()).filter(x -> Objects.equals(x.value, value))
				.findFirst().orElse(null);
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}