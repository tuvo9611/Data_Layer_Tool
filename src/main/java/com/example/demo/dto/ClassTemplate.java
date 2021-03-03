package com.example.demo.dto;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClassTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String packageName;
	
	private String tableName;
	
	private String className;
	
	private String extendOrImpl;
	
	private String classExtendOrImpl;
	
	private List<PropertyTemplate> properties;
	
	private LinkedHashSet<String> libraries;

	public ClassTemplate(String tableName, String className, String extendOrImpl, String classExtendOrImpl,
			List<PropertyTemplate> properties) {
		super();
		this.tableName = tableName;
		this.className = className;
		this.extendOrImpl = extendOrImpl;
		this.classExtendOrImpl = classExtendOrImpl;
		this.properties = properties;
	}
}
