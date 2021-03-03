package com.example.demo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.AnnotationConstants;
import com.example.demo.common.CommonConstants;
import com.example.demo.common.ImportLibraryAnnotation;
import com.example.demo.common.MessageConstants;
import com.example.demo.common.util.AnnotationUtil;
import com.example.demo.common.util.MustacheUtil;
import com.example.demo.common.util.ObjectUtils;
import com.example.demo.common.util.StringUtil;
import com.example.demo.config.GlobalConfig;
import com.example.demo.dto.AnnotationTemplate;
import com.example.demo.dto.DtoTemplate;
import com.example.demo.dto.PropertyTemplate;
import com.example.demo.dto.request.TemplateControllerRequest;
import com.example.demo.dto.request.TemplateDtoControllerRequest;
import com.example.demo.dto.request.TemplateItemValidationRequest;
import com.example.demo.service.IDtoTemplateService;

@Service
public class DtoTemplateServiceImpl implements IDtoTemplateService {

	@Autowired
	private GlobalConfig globalConfig;

	@Override
	public List<DtoTemplate> generateDtoTemplate(TemplateControllerRequest request) throws IOException {
		List<TemplateDtoControllerRequest> dtos = request.getDtos();
		List<TemplateItemValidationRequest> validations = request.getValidations();
		List<DtoTemplate> listDtoTemplate = null;
		// Convert data excel to data template mustache
		listDtoTemplate = convertDataToTemplateDtoMustache(dtos, validations);
		return listDtoTemplate;
	}

	@Override
	public void generateFileDto(List<DtoTemplate> request) throws IOException {
		for (DtoTemplate data : request) {
			String fileName = String.format(CommonConstants.FILE_WITH_ENTENSION_JAVA, data.getClassName());
			if (CommonConstants.CLASS_TYPE_REQUEST.equalsIgnoreCase(data.getClassType())) {
				// Export file
				MustacheUtil.exportFileWithMustache(data, globalConfig.getMustache().getTemplateDto(),
						globalConfig.getOutput().getFolderDtoRequest(), fileName);
			} else {
				// Export file
				MustacheUtil.exportFileWithMustache(data, globalConfig.getMustache().getTemplateDto(),
						globalConfig.getOutput().getFolderDtoResponse(), fileName);
			}

		}
	}

	/**
	 * Convert Excel data to Template Mustache DTO
	 * 
	 * @param template
	 * @return
	 */
	private List<DtoTemplate> convertDataToTemplateDtoMustache(List<TemplateDtoControllerRequest> template,
			List<TemplateItemValidationRequest> templateValidations) {
		List<DtoTemplate> response = new ArrayList<>();

		Map<String, List<TemplateDtoControllerRequest>> groupByDtoId = template.stream()
				.collect(Collectors.groupingBy(x -> x.getDtoId()));

		for (Map.Entry<String, List<TemplateDtoControllerRequest>> entry : groupByDtoId.entrySet()) {
			List<TemplateDtoControllerRequest> listTemplateDto = entry.getValue();
			DtoTemplate dtoTemplate = new DtoTemplate();
			List<PropertyTemplate> properties = new ArrayList<>();
			LinkedHashSet<String> libraries = new LinkedHashSet<>();
			for (TemplateDtoControllerRequest item : listTemplateDto) {
				String classType = item.getType();
				String className = item.getClassName();
				String fieldName = item.getProperty();
				String dataType = item.getValueType();
				String itemId = item.getItemId();

				dtoTemplate.setClassName(className);
				dtoTemplate.setClassType(classType);

				List<AnnotationTemplate> annotations = new ArrayList<>();
				PropertyTemplate prop = new PropertyTemplate();
				prop.setDataType(dataType);
				prop.setFieldName(fieldName);
				prop.setJsonProperty(StringUtil.camelToSnakeCase(fieldName));
				prop.setAnnotations(annotations);

				// Import Annotation in SHEET Item Validation
				if (ObjectUtils.isNotEmpty(itemId)) {
					importAnnotationInItemValidation(itemId, annotations, templateValidations, libraries);
				}

				// Import library List
				if (dataType.contains(CommonConstants.STR_LIST)) {
					String libList = ImportLibraryAnnotation.getLibraryByAnnotatation(CommonConstants.STR_LIST);
					libraries.add(libList);
				}

				// Set package name
				if (CommonConstants.CLASS_TYPE_REQUEST.equalsIgnoreCase(classType)) {
					dtoTemplate.setPackageName(globalConfig.getPackageName().getDtoRequest());
				} else {
					dtoTemplate.setPackageName(globalConfig.getPackageName().getDtoResponse());
				}

				properties.add(prop);
			}

			dtoTemplate.setProperties(properties);
			dtoTemplate.setLibraries(libraries);

			response.add(dtoTemplate);
		}

		return response;
	}

	/**
	 * Import Annotaion and Library for Item Validation
	 * 
	 * @param itemId
	 * @param annotations
	 * @param templateValidations
	 * @param libraries
	 */
	private void importAnnotationInItemValidation(String itemId, List<AnnotationTemplate> annotations,
			List<TemplateItemValidationRequest> templateValidations, LinkedHashSet<String> libraries) {
		TemplateItemValidationRequest itemValidation = findByItemId(itemId, templateValidations);
		Boolean notNull = itemValidation.getNotNull();
		Boolean formatDate = itemValidation.getFormatDate();
		Boolean formatEmail = itemValidation.getFormatMail();
		Boolean formatPhone = itemValidation.getFormatPhone();
		String minLength = itemValidation.getMinLength();
		String maxLength = itemValidation.getMaxLength();
		String displayName = itemValidation.getDisplayName();

		if (notNull != null && notNull) {
			// Annotation
			String message = String.format(MessageConstants.MESSAGE_NOT_NULL, displayName);
			AnnotationTemplate annotation = AnnotationUtil.generateAnnotaionNotNull(message);
			annotations.add(annotation);

			// Library
			String libNotNull = ImportLibraryAnnotation
					.getLibraryByAnnotatation(AnnotationConstants.ANNOTATION_NOT_NULL);
			libraries.add(libNotNull);
		}

		if (formatDate != null && formatDate) {
			String regex = globalConfig.getPattern().getDate();
			String message = MessageConstants.MESSAGE_INVALID_DATE;
			AnnotationTemplate annotation = AnnotationUtil.generateAnnotaionPattern(regex, message);
			annotations.add(annotation);

			// Library
			String libPattern = ImportLibraryAnnotation
					.getLibraryByAnnotatation(AnnotationConstants.ANNOTATION_PATTERN);
			libraries.add(libPattern);
		}

		if (formatEmail != null && formatEmail) {
			// Annotation
			String regex = globalConfig.getPattern().getMail();
			String message = MessageConstants.MESSAGE_INVALID_EMAIL;
			AnnotationTemplate annotation = AnnotationUtil.generateAnnotaionPattern(regex, message);
			annotations.add(annotation);

			// Library
			String libPattern = ImportLibraryAnnotation
					.getLibraryByAnnotatation(AnnotationConstants.ANNOTATION_PATTERN);
			libraries.add(libPattern);
		}

		if (formatPhone != null && formatPhone) {
			// Annotation
			String regex = globalConfig.getPattern().getPhone();
			String message = MessageConstants.MESSAGE_INVALID_PHONE;
			AnnotationTemplate annotation = AnnotationUtil.generateAnnotaionPattern(regex, message);
			annotations.add(annotation);

			// Library
			String libPattern = ImportLibraryAnnotation
					.getLibraryByAnnotatation(AnnotationConstants.ANNOTATION_PATTERN);
			libraries.add(libPattern);
		}

		if (ObjectUtils.isNotEmpty(minLength)) {
			// Annotation
			String message = String.format(MessageConstants.MESSAGE_MIN_LENGTH, minLength);
			AnnotationTemplate annotation = AnnotationUtil.generateAnnotaionMin(minLength, message);
			annotations.add(annotation);

			// Library
			String libPattern = ImportLibraryAnnotation.getLibraryByAnnotatation(AnnotationConstants.ANNOTATION_MIN);
			libraries.add(libPattern);
		}

		if (ObjectUtils.isNotEmpty(maxLength)) {
			// Annotation
			String message = String.format(MessageConstants.MESSAGE_MAX_LENGTH, maxLength);
			AnnotationTemplate annotation = AnnotationUtil.generateAnnotaionMax(maxLength, message);
			annotations.add(annotation);

			// Library
			String libPattern = ImportLibraryAnnotation.getLibraryByAnnotatation(AnnotationConstants.ANNOTATION_MAX);
			libraries.add(libPattern);
		}
	}

	private TemplateItemValidationRequest findByItemId(String itemId, List<TemplateItemValidationRequest> data) {
		TemplateItemValidationRequest response = new TemplateItemValidationRequest();
		Optional<TemplateItemValidationRequest> value = data.stream()
				.filter(x -> x.getItemId().equalsIgnoreCase(itemId)).findFirst();
		if (value.isPresent()) {
			response = value.get();
		}
		return response;
	}
}
