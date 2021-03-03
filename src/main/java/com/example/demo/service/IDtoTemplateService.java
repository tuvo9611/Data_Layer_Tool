package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import com.example.demo.dto.DtoTemplate;
import com.example.demo.dto.request.TemplateControllerRequest;

public interface IDtoTemplateService {
	/**
	 * Generate dto template
	 * 
	 * @param request
	 * @return
	 */
	public List<DtoTemplate> generateDtoTemplate(TemplateControllerRequest request) throws IOException;

	/**
	 * Generate dto request and response
	 * 
	 * @param request
	 * @throws IOException
	 */
	public void generateFileDto(List<DtoTemplate> request) throws IOException;
}
