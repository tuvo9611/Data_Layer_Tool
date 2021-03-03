package com.example.demo.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RepositoryTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	private String packageName;
	
	private String packageEntity;
	
	private String className;
	
	private String dataType;
}
