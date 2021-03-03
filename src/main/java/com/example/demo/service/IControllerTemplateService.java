package com.example.demo.service;

import java.io.IOException;

import com.example.demo.dto.ControllerTemplate;
import com.example.demo.dto.request.TemplateControllerRequest;

public interface IControllerTemplateService {
	/**
	 * Generate class template
	 * 
	 * @param request
	 * @return
	 */
	public ControllerTemplate generateControllerTemplate(TemplateControllerRequest request) throws IOException;

	/**
	 * Generate entity .java and repository .java
	 * 
	 * @param request
	 * @throws IOException
	 */
	public void generateFileController(ControllerTemplate request) throws IOException;
}
