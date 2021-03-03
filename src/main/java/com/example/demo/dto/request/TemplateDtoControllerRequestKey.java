package com.example.demo.dto.request;

import java.util.Arrays;
import java.util.Objects;

public enum TemplateDtoControllerRequestKey {
	DTO_ID(3, "DTO_ID"), TYPE(4, "TYPE"), CLASS_NAME(5, "CLASS_NAME"), PROPERTY(6, "PROPERTY"),
	VALUE_TYPE(7, "VALUE_TYPE"), ITEM_ID(8, "ITEM_ID");

	private int value;
	private String name;

	TemplateDtoControllerRequestKey(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static TemplateDtoControllerRequestKey getByValue(int value) {
		return Arrays.stream(TemplateDtoControllerRequestKey.values()).filter(x -> Objects.equals(x.value, value))
				.findFirst().orElse(null);
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}