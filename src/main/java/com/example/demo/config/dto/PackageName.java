package com.example.demo.config.dto;

import lombok.Data;

@Data
public class PackageName {
	private String entity;
	
	private String repository;
	
	private String controller;
	
	private String dtoRequest;
	
	private String dtoResponse;
	
	private String businessException;
	
	private String logMessageConstants;
}
