package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.business.IControllerAndDtoBusiness;
import com.example.demo.business.IEntityAndPostgresBusiness;
import com.example.demo.common.BusinessException;
import com.example.demo.common.CommonConstants;
import com.example.demo.config.GlobalConfig;

/**
 * @author CMC Global - DU10
 *
 */
@RestController
public class TemplateController {

	@Autowired
	private GlobalConfig globalConfig;

	@Autowired
	private IControllerAndDtoBusiness controllerAndDtoBusiness;

	@Autowired
	private IEntityAndPostgresBusiness entityAndPostgresBusiness;

	@GetMapping("/generate/controller")
	public Map<String, Object> toolGenerateController() throws BusinessException, IOException {
		HashMap<String, Object> response = null;
		// Read data excel
		String fileDir = String.format(CommonConstants.FOLDER_TEMPLATE,
				globalConfig.getInput().getTemplateControllerExcel());
		response = controllerAndDtoBusiness.generateControllerAndDto(fileDir);

		return response;
	}

	@GetMapping("/generate/entity")
	public Map<String, Object> toolGenerateEntity() throws IOException, BusinessException {
		HashMap<String, Object> response = null;
		// Read file excel template
		String fileDir = String.format(CommonConstants.FOLDER_TEMPLATE,
				globalConfig.getInput().getTemplateEntityExcel());

		response = entityAndPostgresBusiness.generateEntityAndPostgres(fileDir);

		return response;
	}

}
