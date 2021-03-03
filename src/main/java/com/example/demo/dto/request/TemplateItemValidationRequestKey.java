package com.example.demo.dto.request;

import java.util.Arrays;
import java.util.Objects;

public enum TemplateItemValidationRequestKey {
	ITEM_ID(1, "ITEM_ID"), DISPLAY_NAME(2, "DISPLAY_NAME"), TYPE(3, "TYPE"), TABLE_INDEX(4, "TABLE_INDEX"),
	NOT_NULL(5, "NOT_NULL"), FORMAT_DATE(6, "FORMAT_DATE"), FORMAT_MAIL(7, "FORMAT_MAIL"),
	FORMAT_PHONE(8, "FORMAT_PHONE"), MIN_LENGTH(9, "MIN_LENGTH"), MAX_LENGTH(10, "MAX_LENGTH");

	private int value;
	private String name;

	TemplateItemValidationRequestKey(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static TemplateItemValidationRequestKey getByValue(int value) {
		return Arrays.stream(TemplateItemValidationRequestKey.values()).filter(x -> Objects.equals(x.value, value))
				.findFirst().orElse(null);
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}