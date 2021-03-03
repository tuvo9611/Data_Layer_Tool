package com.example.demo.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceFrontEndTemplate implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String methodname;
	private String url;
	private String method;
}
