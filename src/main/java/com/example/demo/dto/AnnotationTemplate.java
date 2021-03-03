package com.example.demo.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnnotationTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	private String annotationName;
	
	private String annotationValue;

	public AnnotationTemplate(String annotationName, String annotationValue) {
		super();
		this.annotationName = annotationName;
		this.annotationValue = annotationValue;
	}

	public AnnotationTemplate(String annotationName) {
		super();
		this.annotationName = annotationName;
	}
}
