package com.example.demo.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.example.demo.common.AnnotationConstants;
import com.example.demo.common.CommonConstants;
import com.example.demo.dto.AnnotationTemplate;
import com.example.demo.dto.request.TemplateRecordEntityRequest;

public class AnnotationUtil {
	private AnnotationUtil() {
		// empty constructor
	}

	public static List<AnnotationTemplate> generateAllAnnotation(TemplateRecordEntityRequest item) {
		String columnName = item.getColumnName();
		String dataType = item.getDataType();
		Boolean primaryKey = item.getPrimaryKey();
		String length = item.getLength();
		Boolean unique = item.getUnique();
		Boolean notNull = item.getNotNull();
		Boolean autoIncement = item.getAutoIncement();
		String defaultValue = item.getDefaultValue();
		String pattern = item.getPattern();

		List<AnnotationTemplate> annotations = new ArrayList<>();
		// Primary Key
		if (primaryKey != null && primaryKey) {
			AnnotationTemplate annotationPK = AnnotationUtil.generateAnnotaionPrimaryKey();
			annotations.add(annotationPK);
		}

		// Auto Increment
		if (autoIncement != null && autoIncement) {
			AnnotationTemplate annotationAutoIncrement = AnnotationUtil.generateAnnotaionAutoIncrement();
			annotations.add(annotationAutoIncrement);
		}

		// Column
		if (ObjectUtils.isNotEmpty(columnName)) {
			AnnotationTemplate annotationColumn = AnnotationUtil.generateAnnotaionColumn(columnName, unique, dataType,
					defaultValue);
			annotations.add(annotationColumn);
		}

		// Not Null
		if (notNull != null && notNull) {
			AnnotationTemplate annotationNotNull = AnnotationUtil.generateAnnotaionNotNull();
			annotations.add(annotationNotNull);
		}

		// Max Length
		if (ObjectUtils.isNotEmpty(length)) {
			AnnotationTemplate annotationSize = AnnotationUtil.generateAnnotaionSize(length);
			annotations.add(annotationSize);
		}

		// Pattern
		if (ObjectUtils.isNotEmpty(pattern)) {
			AnnotationTemplate annotationPattern = AnnotationUtil.generateAnnotaionPattern(pattern);
			annotations.add(annotationPattern);
		}

		return annotations;
	}

	public static AnnotationTemplate generateAnnotaionColumn(String name, Boolean unique, String dataType,
			String defaultValue) {
		AnnotationTemplate annotaion = new AnnotationTemplate();
		annotaion.setAnnotationName(AnnotationConstants.ANNOTATION_COLUMN);
		String annotationValue = String.format(AnnotationConstants.ANNOTATION_COLUMN_VALUE_NAME, name);
		if (ObjectUtils.isNotEmpty(unique) && unique) {
			annotationValue += AnnotationConstants.ANNOTATION_COLUMN_VALUE_UNIQUE;
		}

		// Special data type
		boolean isSpecialDataType = false;
		if (ObjectUtils.isEmpty(defaultValue) && dataType.contains(CommonConstants.STR_JSONB)
				|| dataType.contains(CommonConstants.STR_NUMERIC) || dataType.contains(CommonConstants.STR_TEXT)) {
			// Format numeric data type
			dataType = dataType.replace(" ", "");
			if (dataType.contains(CommonConstants.STR_NUMERIC)) {
				String numeric = StringUtils.substringBetween(dataType, "(", ")");
				String[] splitNum = numeric.split(CommonConstants.COMMA);
				String precision = splitNum[0];
				String scale = "0";
				if (splitNum.length > 1) {
					scale = splitNum[1];
				}
				annotationValue += String.format(AnnotationConstants.ANNOTATION_COLUMN_VALUE_NUMERIC_FORMAT, precision,
						scale);
			}

			String valueOtherDataType = String.format(AnnotationConstants.ANNOTATION_COLUMN_VALUE_OTHER_DATA_TYPE,
					dataType);
			annotationValue += valueOtherDataType;
			isSpecialDataType = true;
		}

		// Default value String or Text
		if (ObjectUtils.isNotEmpty(defaultValue) && (CommonConstants.STR_VARCHAR_POSTGRES.equalsIgnoreCase(dataType)
				|| CommonConstants.STR_TEXT.equalsIgnoreCase(dataType))) {
			defaultValue = String.format(CommonConstants.FORMAT_DEFAULT_VALUE_STRING, defaultValue);
		}

		// Have default value
		if (ObjectUtils.isNotEmpty(defaultValue) && isSpecialDataType) {
			String valueDefault = String.format(AnnotationConstants.ANNOTATION_COLUMN_VALUE_DEFAULT_VALUE,
					defaultValue);
			annotationValue = annotationValue.substring(0, annotationValue.length() - 1);
			annotationValue += valueDefault;
		} else if (ObjectUtils.isNotEmpty(defaultValue) && !isSpecialDataType) {
			String valueDefault = String.format(
					AnnotationConstants.ANNOTATION_COLUMN_VALUE_COLUMN_DEFINE_AND_DEFAULT_VALUE, dataType,
					defaultValue);
			annotationValue += valueDefault;
		}

		annotationValue = String.format(CommonConstants.BRACKET_BETWEEN, annotationValue);
		annotaion.setAnnotationValue(annotationValue);
		return annotaion;
	}

	public static AnnotationTemplate generateAnnotaionPrimaryKey() {
		AnnotationTemplate annotaion = new AnnotationTemplate();
		annotaion.setAnnotationName(AnnotationConstants.ANNOTATION_PRIMARY_KEY);
		return annotaion;
	}

	public static AnnotationTemplate generateAnnotaionAutoIncrement() {
		AnnotationTemplate annotaion = new AnnotationTemplate();
		annotaion.setAnnotationName(AnnotationConstants.ANNOTATION_AUTO_INCREMENT);
		annotaion.setAnnotationValue(AnnotationConstants.ANNOTATION_VALUE_AUTO_INCREMENT);
		return annotaion;
	}

	public static AnnotationTemplate generateAnnotaionNotNull() {
		AnnotationTemplate annotaion = new AnnotationTemplate();
		annotaion.setAnnotationName(AnnotationConstants.ANNOTATION_NOT_NULL);
		annotaion.setAnnotationValue(AnnotationConstants.ANNOTATION_NOT_NULL_VALUE);
		return annotaion;
	}

	public static AnnotationTemplate generateAnnotaionNotNull(String message) {
		AnnotationTemplate annotaion = new AnnotationTemplate();
		annotaion.setAnnotationName(AnnotationConstants.ANNOTATION_NOT_NULL);
		annotaion.setAnnotationValue(String.format(AnnotationConstants.ANNOTATION_VALUE_MESSAGE, message));
		return annotaion;
	}

	public static AnnotationTemplate generateAnnotaionSize(String length) {
		AnnotationTemplate annotaion = new AnnotationTemplate();
		annotaion.setAnnotationName(AnnotationConstants.ANNOTATION_SIZE);
		annotaion.setAnnotationValue(String.format(AnnotationConstants.ANNOTATION_VALUE_SIZE_MAX, length));
		return annotaion;
	}

	public static AnnotationTemplate generateAnnotaionMin(String length, String message) {
		AnnotationTemplate annotaion = new AnnotationTemplate();
		annotaion.setAnnotationName(AnnotationConstants.ANNOTATION_MIN);
		annotaion.setAnnotationValue(String.format(AnnotationConstants.ANNOTAITON_VALUE_LENGTH, length, message));
		return annotaion;
	}

	public static AnnotationTemplate generateAnnotaionMax(String length, String message) {
		AnnotationTemplate annotaion = new AnnotationTemplate();
		annotaion.setAnnotationName(AnnotationConstants.ANNOTATION_MAX);
		annotaion.setAnnotationValue(String.format(AnnotationConstants.ANNOTAITON_VALUE_LENGTH, length, message));
		return annotaion;
	}

	public static AnnotationTemplate generateAnnotaionPattern(String regex) {
		AnnotationTemplate annotaion = new AnnotationTemplate();
		annotaion.setAnnotationName(AnnotationConstants.ANNOTATION_PATTERN);
		annotaion.setAnnotationValue(String.format(AnnotationConstants.ANNOTATION_PATTERN_REGEX, regex));
		return annotaion;
	}

	public static AnnotationTemplate generateAnnotaionPattern(String regex, String message) {
		AnnotationTemplate annotaion = new AnnotationTemplate();
		annotaion.setAnnotationName(AnnotationConstants.ANNOTATION_PATTERN);
		annotaion.setAnnotationValue(String.format(AnnotationConstants.ANNOTATION_VALUE_PATTERN_REGEX, regex, message));
		return annotaion;
	}
}
