package com.example.demo.dto.request;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TemplateControllerRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String controllerName;

	private List<TemplateMethodControllerRequest> methodControllers;

	private List<TemplateDtoControllerRequest> dtos;

	private List<TemplateItemValidationRequest> validations;
	
	private List<TemplateParameterControllerRequest> parameters;
}
