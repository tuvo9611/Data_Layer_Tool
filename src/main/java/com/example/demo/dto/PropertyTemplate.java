package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PropertyTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<AnnotationTemplate> annotations;

	private String dataType;

	private String fieldName;
	
	private String jsonProperty;

	public PropertyTemplate(List<AnnotationTemplate> annotations, String dataType, String fieldName) {
		super();
		this.annotations = annotations;
		this.dataType = dataType;
		this.fieldName = fieldName;
	}
}
