package com.example.demo.dto.request;

import java.util.Arrays;
import java.util.Objects;

public enum TemplateEntityRequestKey {
	NO(0, "NO"), COLUMN_NAME(1, "COLUMN_NAME"), DATA_TYPE(2, "DATA_TYPE"), PRIMARY_KEY(3, "PRIMARY_KEY"),
	FOREIGN_KEY(4, "FOREIGN_KEY"), LENGTH(5, "LENGTH"), UNIQUE(6, "UNIQUE"), NOT_NULL(7, "NOT_NULL"),
	AUTO_INCREMENT(8, "AUTO_INCREMENT"), DEFAULT_VALUE(9, "DEFAULT_VALUE"), PATTERN(10, "PATTERN"),
	REMARK(11, "REMARK");

	private int value;
	private String name;

	TemplateEntityRequestKey(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static TemplateEntityRequestKey getByValue(int value) {
		return Arrays.stream(TemplateEntityRequestKey.values()).filter(x -> Objects.equals(x.value, value)).findFirst()
				.orElse(null);
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}