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
public class DtoTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String packageName;
	
	private String className;
	
	private String classType;
	
	private List<PropertyTemplate> properties;
	
	private LinkedHashSet<String> libraries;
}
