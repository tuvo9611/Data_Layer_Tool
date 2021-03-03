package com.example.demo.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MethodControllerTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String httpMethod;
	
	private String url;
	
	private String methodName;
	
	private String responseModel;
	
	private String requestModel;
	
	private String requestBody;
	
	private String requestParameter;
}
