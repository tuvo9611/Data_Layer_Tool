package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import com.example.demo.dto.ListTablePostgresTemplate;
import com.example.demo.dto.request.TemplateTableEntitylRequest;

public interface IPostgresTemplateService {
	/**
	 * Generate postgres table file .txt
	 * @param data
	 * @throws IOException
	 */
	public void generateFilePostgresTableSql(ListTablePostgresTemplate data) throws IOException;
	
	/**
	 * Generate data postgres template
	 * @param request
	 * @return
	 */
	public ListTablePostgresTemplate generatePostgresTemplate(List<TemplateTableEntitylRequest> request);
}
