package com.example.demo.business.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.business.IEntityAndPostgresBusiness;
import com.example.demo.common.BusinessException;
import com.example.demo.common.CommonConstants;
import com.example.demo.common.util.ExcelUtil;
import com.example.demo.config.GlobalConfig;
import com.example.demo.dto.ClassTemplate;
import com.example.demo.dto.request.TemplateTableEntitylRequest;
import com.example.demo.service.IEntityAndRepositoryTemplateService;
import com.example.demo.service.IPostgresTemplateService;

@Service
public class EntityAndPostgresBusinessImpl implements IEntityAndPostgresBusiness {

	@Autowired
	private GlobalConfig globalConfig;

	@Autowired
	private IEntityAndRepositoryTemplateService entityAndResitoryService;

	@Autowired
	private IPostgresTemplateService postgresService;

	@Override
	public HashMap<String, Object> generateEntityAndPostgres(String fileTemplate)
			throws BusinessException, IOException {
		HashMap<String, Object> response = new HashMap<>();
		// Read file excel template
		String fileDir = String.format(CommonConstants.FOLDER_TEMPLATE,
				globalConfig.getInput().getTemplateEntityExcel());

		List<TemplateTableEntitylRequest> listTable = ExcelUtil.readExcelEntityTemplate(fileDir);

		// Generate entity
		List<ClassTemplate> listClassTemplate = entityAndResitoryService.generateClassTemplate(listTable);

		// Generate file entity
		entityAndResitoryService.generateFileEntityAndRepository(listClassTemplate);

		// Generate postgres
		postgresService.generateFilePostgresTableSql(postgresService.generatePostgresTemplate(listTable));

		response.put("result", listClassTemplate);

		return response;
	}
}
