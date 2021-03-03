package com.example.demo.common;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.text.CaseUtils;

import com.example.demo.common.util.AnnotationUtil;
import com.example.demo.common.util.StringUtil;
import com.example.demo.dto.AnnotationTemplate;
import com.example.demo.dto.ClassTemplate;
import com.example.demo.dto.PropertyTemplate;
import com.example.demo.dto.request.TemplateRecordEntityRequest;

public class GenerateEntity {
	private GenerateEntity() {
		// empty constructor
	}

	/**
	 * Input column PK, FK and Excel data
	 * 
	 * @param primaryKey
	 * @param foriegnKey
	 * @param listTable
	 * @return
	 */
	public static boolean checkOneToOne(TemplateRecordEntityRequest template) {
		boolean result = false;

		if (AnnotationConstants.ANNOTATION_ONE_TO_ONE.equalsIgnoreCase(template.getRemark())) {
			result = true;
		}
		return result;
	}

	/**
	 * Generate Properties OneToOne
	 * 
	 * @OneToOne
	 * @JoinColumn(name = "PROFILE_ID", referencedColumnName = "PROFILE_ID",
	 *                  nullable = false)
	 * 
	 * @param columnName
	 * @param currentTable
	 * @return
	 */
	public static PropertyTemplate generatePropertiesOneToOne(String columnName) {
		List<AnnotationTemplate> listAnno = new ArrayList<>();
		AnnotationTemplate annotationOneToOne = new AnnotationTemplate();
		AnnotationTemplate annotationJoinColumn = new AnnotationTemplate();

		int indexRemove = columnName.lastIndexOf(CommonConstants.UNDERSCORE);

		String tableReferenceLower = columnName.substring(0, indexRemove).toLowerCase();
		String tableReference = CaseUtils.toCamelCase(tableReferenceLower, true, CommonConstants.UNDERSCORE);

		annotationOneToOne.setAnnotationName(AnnotationConstants.ANNOTATION_ONE_TO_ONE);
		annotationJoinColumn.setAnnotationName(AnnotationConstants.ANNOTATION_JOIN_COLUMN);
		annotationJoinColumn
				.setAnnotationValue(String.format(AnnotationConstants.ANNOTATION_VALUE_JOIN_COLUMN, columnName));
		listAnno.add(annotationOneToOne);
		listAnno.add(annotationJoinColumn);

		PropertyTemplate propertyOneToOne = new PropertyTemplate();
		String dataType = tableReference;
		String fieldName = CaseUtils.toCamelCase(tableReferenceLower, false, CommonConstants.UNDERSCORE);
		propertyOneToOne.setDataType(dataType);
		propertyOneToOne.setFieldName(fieldName);
		propertyOneToOne.setAnnotations(listAnno);

		return propertyOneToOne;
	}

	/**
	 * Create class template
	 * 
	 * @param tableName
	 * @param properties
	 * @param isExtendBaseEntity
	 * @return
	 */
	public static ClassTemplate createClassTemplate(String tableName, List<PropertyTemplate> properties,
			boolean isExtendBaseEntity) {
		String extend = "";
		String classExtend = "";
		if (isExtendBaseEntity) {
			extend = CommonConstants.STR_EXTENDS;
			classExtend = CommonConstants.STR_BASE_ENTITY;
		} else {
			extend = CommonConstants.STR_IMPLEMENTS;
			classExtend = CommonConstants.STR_SERIALIZABLE;
		}
		ClassTemplate classTemplate = new ClassTemplate();
		classTemplate.setTableName(tableName);
		classTemplate.setClassName(CaseUtils.toCamelCase(tableName, true, CommonConstants.UNDERSCORE));
		classTemplate.setProperties(properties);
		classTemplate.setExtendOrImpl(extend);
		classTemplate.setClassExtendOrImpl(classExtend);

		return classTemplate;
	}

	/**
	 * Create One to Many To do: update performance
	 */
	public static void mappingOneToManyOrOneToOne(List<ClassTemplate> listClassTemplate) {
		for (ClassTemplate classTemplate : listClassTemplate) {
			for (PropertyTemplate property : classTemplate.getProperties()) {
				List<AnnotationTemplate> annotations = property.getAnnotations();
				String dataType = property.getDataType(); // Data Type is Table Reference
				for (AnnotationTemplate annotation : annotations) {
					String annotationName = annotation.getAnnotationName();
					// One To Many
					if (annotationName.equals(AnnotationConstants.ANNOTATION_MANY_TO_ONE)) {
						ClassTemplate classReference = listClassTemplate.stream()
								.filter(x -> x.getClassName().equals(dataType)).findFirst().orElse(null);
						if (classReference != null) {
							PropertyTemplate properyOneToMany = generatePropertiesOneToMany(
									StringUtil.pascalToCamelCase(classReference.getClassName()), classTemplate.getClassName());
							List<PropertyTemplate> properties = classReference.getProperties();
							properties.add(properyOneToMany);
							classReference.setProperties(properties);
						}
						// One To One
					} else if (annotationName.equals(AnnotationConstants.ANNOTATION_ONE_TO_ONE)) {
						ClassTemplate classReference = listClassTemplate.stream()
								.filter(x -> x.getClassName().equals(dataType)).findFirst().orElse(null);
						if (classReference != null) {
							String classNameReference = classReference.getClassName();
							String currentClass = classTemplate.getClassName();
							// Data Type and Field
							PropertyTemplate properyOneToOneAnother = new PropertyTemplate();
							properyOneToOneAnother.setDataType(currentClass);
							properyOneToOneAnother.setFieldName(CaseUtils.toCamelCase(currentClass, false));
							// Annotation
							List<AnnotationTemplate> listAnno = new ArrayList<>();
							AnnotationTemplate anno = new AnnotationTemplate();
							anno.setAnnotationName(AnnotationConstants.ANNOTATION_ONE_TO_ONE);
							anno.setAnnotationValue(String.format(AnnotationConstants.ANNOTATION_VALUE_MAPPED_BY,
									StringUtil.pascalToCamelCase(classNameReference)));
							listAnno.add(anno);
							properyOneToOneAnother.setAnnotations(listAnno);

							List<PropertyTemplate> properties = classReference.getProperties();
							properties.add(properyOneToOneAnother);
						}
					}
				}
			}
		}
	}

	/**
	 * generate properties ManyToOne
	 * 
	 * @param columnName
	 * @return
	 */
	public static PropertyTemplate generatePropertiesManyToOne(String columnName) {
		List<AnnotationTemplate> listAnno = new ArrayList<>();
		AnnotationTemplate annotationManyToOne = new AnnotationTemplate();
		AnnotationTemplate annotationJoinColumn = new AnnotationTemplate();

		int indexRemove = columnName.lastIndexOf(CommonConstants.UNDERSCORE);

		String tableReferenceLower = columnName.substring(0, indexRemove).toLowerCase();
		String tableReference = CaseUtils.toCamelCase(tableReferenceLower, true, CommonConstants.UNDERSCORE);

		annotationManyToOne.setAnnotationName(AnnotationConstants.ANNOTATION_MANY_TO_ONE);
		annotationJoinColumn.setAnnotationName(AnnotationConstants.ANNOTATION_JOIN_COLUMN);
		annotationJoinColumn
				.setAnnotationValue(String.format(AnnotationConstants.ANNOTATION_VALUE_JOIN_COLUMN, columnName));
		listAnno.add(annotationManyToOne);
		listAnno.add(annotationJoinColumn);

		PropertyTemplate propertyManyToOne = new PropertyTemplate();
		String dataType = tableReference;
		String fieldName = CaseUtils.toCamelCase(tableReferenceLower, false, CommonConstants.UNDERSCORE);
		propertyManyToOne.setDataType(dataType);
		propertyManyToOne.setFieldName(fieldName);
		propertyManyToOne.setAnnotations(listAnno);

		return propertyManyToOne;
	}

	/**
	 * Generate properties OneToMany
	 * 
	 * @param mappedBy
	 * @param className
	 * @return
	 */
	public static PropertyTemplate generatePropertiesOneToMany(String mappedBy, String className) {
		List<AnnotationTemplate> listAnno = new ArrayList<>();
		AnnotationTemplate annotationOneToMany = new AnnotationTemplate();
		String dataType = String.format(CommonConstants.DATA_TYPE_ONE_TO_MANY, className);
		// Camel case list object
		String fieldName = StringUtil.pascalToCamelCase(className) + "s";

		annotationOneToMany.setAnnotationName(AnnotationConstants.ANNOTATION_ONE_TO_MANY);
		annotationOneToMany.setAnnotationValue(String.format(AnnotationConstants.ANNOTATION_VALUE_MAPPED_BY, mappedBy));
		listAnno.add(annotationOneToMany);

		PropertyTemplate property = new PropertyTemplate();
		property.setDataType(dataType);
		property.setFieldName(fieldName);

		property.setAnnotations(listAnno);

		return property;
	}

	/**
	 * Generate property
	 * 
	 * @param tableName
	 * @param item
	 * @return
	 */
	public static PropertyTemplate generateProperty(String tableName, TemplateRecordEntityRequest item) {
		String columnName = item.getColumnName();
		String dataType = item.getDataType();

		PropertyTemplate property = new PropertyTemplate();
		// Generate all annotation in property
		List<AnnotationTemplate> annotations = AnnotationUtil.generateAllAnnotation(item);

		// Change data type numeric
		if (dataType.contains(CommonConstants.STR_NUMERIC)) {
			dataType = CommonConstants.STR_NUMERIC;
		}

		String propDataType = ConvertDataType.postgresToJava(dataType);
		String propFieldName = generateFieldName(tableName, columnName);

		property.setAnnotations(annotations);
		property.setFieldName(propFieldName);
		property.setDataType(propDataType);

		return property;
	}

	/**
	 * Generate Field Name
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public static String generateFieldName(String tableName, String columnName) {
		String value;
		if (columnName.indexOf(tableName) != -1) {
			value = columnName.replace(tableName, "");
		} else {
			value = columnName;
		}
		value = CaseUtils.toCamelCase(value, false, CommonConstants.UNDERSCORE);
		return value;
	}

	/**
	 * Import library for Class
	 */
	public static void importLibrary(List<ClassTemplate> listClassTemplate) {
		for (ClassTemplate cls : listClassTemplate) {
			List<PropertyTemplate> properties = cls.getProperties();
			String classExtend = cls.getClassExtendOrImpl();
			// Get library for import by annotation name
			LinkedHashSet<String> libraries = importLibraryByAnnotation(properties, classExtend);
			cls.setLibraries(libraries);
		}
	}

	/**
	 * Import library by annotation
	 */
	public static LinkedHashSet<String> importLibraryByAnnotation(List<PropertyTemplate> properties,
			String classExtend) {
		try {
			LinkedHashSet<String> libraries = new LinkedHashSet<>();

			for (PropertyTemplate prop : properties) {
				for (AnnotationTemplate item : prop.getAnnotations()) {
					String annotationName = item.getAnnotationName();
					String lib = ImportLibraryAnnotation.getLibraryByAnnotatation(annotationName);
					// Auto increment import annotation GENERATION_TYPE
					if (annotationName.equals(AnnotationConstants.ANNOTATION_AUTO_INCREMENT)) {
						String generationType = AnnotationConstants.ANNOTATION_GENERATION_TYPE;
						String libGenerationType = ImportLibraryAnnotation.getLibraryByAnnotatation(generationType);
						libraries.add(libGenerationType);
					}

					libraries.add(lib);
				}

				String dataType = prop.getDataType();
				if (dataType != null) {
					// Import library List
					if (dataType.contains(CommonConstants.STR_LIST)) {
						String annoList = CommonConstants.STR_LIST;
						String libList = ImportLibraryAnnotation.getLibraryByAnnotatation(annoList);
						libraries.add(libList);
					}

					// Import library Date
					if (dataType.contains(CommonConstants.STR_DATE)) {
						String annoDate = CommonConstants.STR_DATE;
						String libList = ImportLibraryAnnotation.getLibraryByAnnotatation(annoDate);
						libraries.add(libList);
					}
				}
			}

			// Import library SERIALIZABLE
			if (classExtend.equals(CommonConstants.STR_SERIALIZABLE)) {
				String annoSerializable = CommonConstants.STR_SERIALIZABLE;
				String libSerializable = ImportLibraryAnnotation.getLibraryByAnnotatation(annoSerializable);
				libraries.add(libSerializable);
			}

			return libraries;
		} catch (Exception e) {
			return null;
		}
	}
}
