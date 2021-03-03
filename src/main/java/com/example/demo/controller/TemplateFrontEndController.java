package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.CommonConstants;
import com.example.demo.common.util.ExcelServiceFrontEndUtil;
import com.example.demo.common.util.MustacheUtil;
import com.example.demo.common.util.StringUtil;
import com.example.demo.config.GlobalConfig;
import com.example.demo.dto.FrontEndServiceTemplate;
import com.example.demo.dto.ListFrontEndService;
import com.example.demo.dto.request.RecordExcelServiceFrontEndRequest;
import com.example.demo.dto.request.TableExcelServiceFrontEndRequest;

/**
 * @author CMC Global - DU10
 *
 */
@RestController
public class TemplateFrontEndController {

	private static final String METHOD_GET = "get";
	private static final String METHOD_DELETE = "delete";
	private static final String FILE_NAME_SERVICE = ".service";
	@Autowired
	private GlobalConfig globalVariable;

	/**
	 * Read data from excel to DTO
	 * 
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/service")
	public Map<String, Object> service() throws IOException {
		List<TableExcelServiceFrontEndRequest> listTable;
		// Read file excel template
		String fileDir = String.format(CommonConstants.FOLDER_TEMPLATE,
				globalVariable.getInput().getTemplateServiceExcel());
		listTable = ExcelServiceFrontEndUtil.readExcelTemplate(fileDir);

		HashMap<String, Object> map = new HashMap<>();
		List<FrontEndServiceTemplate> listLandingServiceTemplate = new ArrayList<>();
		String fileNameService = "";
		String pageNames = "";
		for (TableExcelServiceFrontEndRequest tbl : listTable) {
			String pageName = WordUtils.capitalize(tbl.getPageName());
			fileNameService = StringUtil.formatValueLower(pageName.replace(CommonConstants.SPECIAL_CHARACTER_WHITESPACE,
					CommonConstants.SPECIAL_CHARACTER_HYPHEN));
			pageNames = pageName.replaceAll(CommonConstants.REGEX_SPACE_1_BYTE, CommonConstants.EMPTY_SPACE);
			List<RecordExcelServiceFrontEndRequest> records = tbl.getRecords();
			for (RecordExcelServiceFrontEndRequest rc : records) {
				String methodName = rc.getMethodname()
						.replaceAll(CommonConstants.REGEX_SPACE_1_BYTE, CommonConstants.EMPTY_SPACE)
						.replaceAll(CommonConstants.REGEX_SPACE_2_BYTE, CommonConstants.EMPTY_SPACE);
				String url = rc.getUrl().replaceAll(CommonConstants.REGEX_SPACE_1_BYTE, CommonConstants.EMPTY_SPACE)
						.replaceAll(CommonConstants.REGEX_SPACE_2_BYTE, CommonConstants.EMPTY_SPACE);
				String method = rc.getHttpmethod()
						.replaceAll(CommonConstants.REGEX_SPACE_1_BYTE, CommonConstants.EMPTY_SPACE)
						.replaceAll(CommonConstants.REGEX_SPACE_2_BYTE, CommonConstants.EMPTY_SPACE);
				// Create Class
				FrontEndServiceTemplate landingServiceTemplate = new FrontEndServiceTemplate(pageNames, methodName, url,
						method);
				if (method.equals(METHOD_GET) || method.equals(METHOD_DELETE)) {
					landingServiceTemplate.setMethodmapping(true);
				} else {
					landingServiceTemplate.setMethodmapping(false);
				}

				listLandingServiceTemplate.add(landingServiceTemplate);
			}
		}
		map.put("result", listLandingServiceTemplate);
		ListFrontEndService result = new ListFrontEndService();
		result.setMethods(listLandingServiceTemplate);
		result.setPagename(pageNames);
		generateLandingServiceTs(result, fileNameService);
		return map;
	}

	/**
	 * Generate File landing.service.ts
	 */
	private void generateLandingServiceTs(ListFrontEndService data, String fileName) throws IOException {

		// Export file
		MustacheUtil.exportFileWithMustache(data, globalVariable.getMustache().getTemplateLandingService(),
				globalVariable.getOutput().getFolderLandingService(),
				String.format(CommonConstants.FILE_WITH_ENTENSION_TS, fileName + FILE_NAME_SERVICE));
	}
}
