package com.example.demo.business.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.business.IControllerAndDtoBusiness;
import com.example.demo.common.BusinessException;
import com.example.demo.common.util.ExcelUtil;
import com.example.demo.dto.DtoTemplate;
import com.example.demo.dto.request.TemplateControllerRequest;
import com.example.demo.service.IControllerTemplateService;
import com.example.demo.service.IDtoTemplateService;

@Service
public class ControllerAndDtoBusinessImpl implements IControllerAndDtoBusiness {

	@Autowired
	private IDtoTemplateService dtoService;

	@Autowired
	private IControllerTemplateService controllerService;
// vi du thay doi cho nay di
	@Override
	public HashMap<String, Object> generateControllerAndDto(String fileTemplate) throws BusinessException, IOException {
		HashMap<String, Object> response = new HashMap<>();
		TemplateControllerRequest request = readTemplateExcel(fileTemplate);
		// Generate DTO
		List<DtoTemplate> listDto = dtoService.generateDtoTemplate(request);
		dtoService.generateFileDto(listDto);

		// Generate Controller
		controllerService.generateFileController(controllerService.generateControllerTemplate(request));

		response.put("result", listDto);
		return response;
	}

	/**
	 * Read file excel to dto
	 * 
	 * @param file
	 * @return
	 */
	private TemplateControllerRequest readTemplateExcel(String file) {
		TemplateControllerRequest response = null;
		response = ExcelUtil.readExcelControllerTemplate(file);
		return response;
	}

}
