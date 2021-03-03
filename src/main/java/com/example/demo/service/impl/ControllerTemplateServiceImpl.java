package com.example.demo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.AnnotationConstants;
import com.example.demo.common.CommonConstants;
import com.example.demo.common.ConvertHttpMethod;
import com.example.demo.common.ImportLibraryAnnotation;
import com.example.demo.common.util.MustacheUtil;
import com.example.demo.config.GlobalConfig;
import com.example.demo.dto.ControllerTemplate;
import com.example.demo.dto.MethodControllerTemplate;
import com.example.demo.dto.request.TemplateControllerRequest;
import com.example.demo.dto.request.TemplateDtoControllerRequest;
import com.example.demo.dto.request.TemplateMethodControllerRequest;
import com.example.demo.dto.request.TemplateParameterControllerRequest;
import com.example.demo.service.IControllerTemplateService;

@Service
public class ControllerTemplateServiceImpl implements IControllerTemplateService {

	@Autowired
	private GlobalConfig globalConfig;

	private static final int REMOVE_SPECIAL_CHAR_ANNOTATION = 1;
	private static final String WHITE_SPACE_COMMA = ", ";

	@Override
	public ControllerTemplate generateControllerTemplate(TemplateControllerRequest request) throws IOException {
		// Convert data excel to data template mustache
		ControllerTemplate controllerTemplate = null;
		controllerTemplate = convertDataToTemplateControllerMustache(request);

		return controllerTemplate;
	}

	@Override
	public void generateFileController(ControllerTemplate request) throws IOException {
		String fileName = String.format(CommonConstants.FILE_WITH_ENTENSION_JAVA, request.getClassName());
		// Export file
		MustacheUtil.exportFileWithMustache(request, globalConfig.getMustache().getTemplateController(),
				globalConfig.getOutput().getFolderController(), fileName);
	}

	/**
	 * Convert Excel data to Template Mustache Controller
	 * 
	 * @param template
	 * @return
	 */
	private ControllerTemplate convertDataToTemplateControllerMustache(TemplateControllerRequest template) {
		String packageName = globalConfig.getPackageName().getController();
		String className = template.getControllerName();
		// Format controller name
		if (className.indexOf(CommonConstants.CHAR_WHITE_SPACE) > -1) {
			className = CaseUtils.toCamelCase(className, true, CommonConstants.CHAR_WHITE_SPACE);
		}
		className = String.format(CommonConstants.FORMAT_CONTROLLER_NAME, className);
		List<TemplateMethodControllerRequest> methodsTemplate = template.getMethodControllers();
		List<TemplateDtoControllerRequest> dtosTemplate = template.getDtos();
		List<TemplateParameterControllerRequest> parametersTemplate = template.getParameters();

		ControllerTemplate response = new ControllerTemplate();
		response.setPackageName(packageName);
		response.setClassName(className);

		List<MethodControllerTemplate> listMethod = new ArrayList<>();
		LinkedHashSet<String> libraries = new LinkedHashSet<>();
		for (TemplateMethodControllerRequest item : methodsTemplate) {
			String methodName = item.getMethodName();
			String httpMethod = ConvertHttpMethod.httpMethodMapping(item.getHttpMethod());
			String requestModel = findClassNameByDtoId(item.getRequestDtoId(), dtosTemplate);
			String responseModel = findClassNameByDtoId(item.getResponseDtoId(), dtosTemplate);
			String url = item.getUrl();
			String requestParam = findRequestParamByParamId(item.getParameter(), parametersTemplate);
			boolean isRequestBody = false;
			if (CommonConstants.METHOD_REQUEST_BODY.contains(item.getHttpMethod())
					&& ObjectUtils.isNotEmpty(requestModel)) {
				isRequestBody = true;
			}

			MethodControllerTemplate method = new MethodControllerTemplate();
			method.setMethodName(methodName);
			method.setHttpMethod(String.format(CommonConstants.FORMAT_ANNOTATION, httpMethod));
			method.setRequestModel(requestModel);
			method.setResponseModel(responseModel);
			method.setRequestParameter(requestParam);
			method.setUrl(url);
			listMethod.add(method);
			if (isRequestBody) {
				method.setRequestBody(String.format(CommonConstants.FORMAT_ANNOTATION_AND_WHITE_SPACE,
						AnnotationConstants.ANNOTATION_REQUEST_BODY));
			}
			// Import library
			libraries.addAll(importLibraryForController(method));
		}

		response.setLibraries(libraries);
		response.setMethods(listMethod);
		return response;
	}

	/**
	 * Import library to controller
	 * 
	 * @param method
	 * @return
	 */
	private LinkedHashSet<String> importLibraryForController(MethodControllerTemplate method) {
		String httpMethod = method.getHttpMethod().substring(REMOVE_SPECIAL_CHAR_ANNOTATION);
		String responseModel = method.getResponseModel();
		String requestModel = method.getRequestModel();
		String requestBody = method.getRequestBody();
		String requestParam = method.getRequestParameter();
		LinkedHashSet<String> libraries = new LinkedHashSet<>();

		// Import library for BusnessException and LogMessage
		libraries.add(globalConfig.getPackageName().getBusinessException());
		libraries.add(globalConfig.getPackageName().getLogMessageConstants());

		if (requestBody != null) {
			libraries
					.add(ImportLibraryAnnotation.getLibraryByAnnotatation(AnnotationConstants.ANNOTATION_REQUEST_BODY));
		}

		if (requestModel != null) {
			libraries.add(String.format(CommonConstants.FORMAT_LIBRARY_IMPORT,
					globalConfig.getPackageName().getDtoRequest(), requestModel));
		}

		if (responseModel != null) {
			libraries.add(String.format(CommonConstants.FORMAT_LIBRARY_IMPORT,
					globalConfig.getPackageName().getDtoResponse(), responseModel));
		}

		if (httpMethod != null) {
			libraries.add(ImportLibraryAnnotation.getLibraryByAnnotatation(httpMethod));
		}

		if (requestParam != null) {
			if (requestParam.contains(CommonConstants.STR_LIST)) {
				libraries.add(ImportLibraryAnnotation.getLibraryByAnnotatation(CommonConstants.STR_LIST));
			}
			libraries.add(
					ImportLibraryAnnotation.getLibraryByAnnotatation(AnnotationConstants.ANNOTATION_REQUEST_PARAM));
		}

		return libraries;
	}

	private String findClassNameByDtoId(String dtoId, List<TemplateDtoControllerRequest> dtos) {
		String className = null;
		Optional<TemplateDtoControllerRequest> value = dtos.stream().filter(x -> x.getDtoId().equals(dtoId))
				.findFirst();
		if (value.isPresent()) {
			className = value.get().getClassName();
		}
		return className;
	}

	private String findRequestParamByParamId(String paramId, List<TemplateParameterControllerRequest> params) {
		if (paramId != null) {
			ArrayList<String> listRequestParam = new ArrayList<>();
			String result = null;
			List<TemplateParameterControllerRequest> listParam = params.stream()
					.filter(x -> x.getParameterId().equals(paramId)).collect(Collectors.toList());
			if (ObjectUtils.isNotEmpty(listParam)) {
				for (TemplateParameterControllerRequest item : listParam) {
					listRequestParam.add(
							String.format(CommonConstants.FORMAT_REQUEST_PARAM, item.getName(), item.getValueType()));
				}
			}
			result = String.join(WHITE_SPACE_COMMA, listRequestParam);
			return result;
		}
		return null;
	}
}
