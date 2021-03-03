package com.example.demo.common;

import java.util.HashMap;
import java.util.Map;

public class ImportLibraryAnnotation {
	private ImportLibraryAnnotation() {
		// empty constructor
	}

	public static String getLibraryByAnnotatation(String key) {
		return getLibraryToAnnotation().get(key);
	}

	private static Map<String, String> getLibraryToAnnotation() {
		Map<String, String> mapping = new HashMap<>();
		// javax.persistence
		mapping.put(AnnotationConstants.ANNOTATION_ONE_TO_ONE,
				String.format(CommonConstants.IMPORT_LIBRARY_PERSISTENCE, AnnotationConstants.ANNOTATION_ONE_TO_ONE));
		mapping.put(AnnotationConstants.ANNOTATION_MANY_TO_ONE,
				String.format(CommonConstants.IMPORT_LIBRARY_PERSISTENCE, AnnotationConstants.ANNOTATION_MANY_TO_ONE));
		mapping.put(AnnotationConstants.ANNOTATION_JOIN_COLUMN,
				String.format(CommonConstants.IMPORT_LIBRARY_PERSISTENCE, AnnotationConstants.ANNOTATION_JOIN_COLUMN));
		mapping.put(AnnotationConstants.ANNOTATION_ONE_TO_MANY,
				String.format(CommonConstants.IMPORT_LIBRARY_PERSISTENCE, AnnotationConstants.ANNOTATION_ONE_TO_MANY));
		mapping.put(AnnotationConstants.ANNOTATION_PRIMARY_KEY,
				String.format(CommonConstants.IMPORT_LIBRARY_PERSISTENCE, AnnotationConstants.ANNOTATION_PRIMARY_KEY));
		mapping.put(AnnotationConstants.ANNOTATION_AUTO_INCREMENT, String
				.format(CommonConstants.IMPORT_LIBRARY_PERSISTENCE, AnnotationConstants.ANNOTATION_AUTO_INCREMENT));
		mapping.put(AnnotationConstants.ANNOTATION_COLUMN,
				String.format(CommonConstants.IMPORT_LIBRARY_PERSISTENCE, AnnotationConstants.ANNOTATION_COLUMN));
		mapping.put(AnnotationConstants.ANNOTATION_GENERATION_TYPE, String
				.format(CommonConstants.IMPORT_LIBRARY_PERSISTENCE, AnnotationConstants.ANNOTATION_GENERATION_TYPE));
		mapping.put(AnnotationConstants.ANNOTATION_FETCH_TYPE, String
				.format(CommonConstants.IMPORT_LIBRARY_PERSISTENCE, AnnotationConstants.ANNOTATION_FETCH_TYPE));
		

		// javax.validation.constraints
		mapping.put(AnnotationConstants.ANNOTATION_NOT_NULL,
				String.format(CommonConstants.IMPORT_LIBRARY_CONSTRAINTS, AnnotationConstants.ANNOTATION_NOT_NULL));
		mapping.put(AnnotationConstants.ANNOTATION_SIZE,
				String.format(CommonConstants.IMPORT_LIBRARY_CONSTRAINTS, AnnotationConstants.ANNOTATION_SIZE));
		mapping.put(AnnotationConstants.ANNOTATION_PATTERN,
				String.format(CommonConstants.IMPORT_LIBRARY_CONSTRAINTS, AnnotationConstants.ANNOTATION_PATTERN));
		mapping.put(AnnotationConstants.ANNOTATION_MIN,
				String.format(CommonConstants.IMPORT_LIBRARY_CONSTRAINTS, AnnotationConstants.ANNOTATION_MIN));
		mapping.put(AnnotationConstants.ANNOTATION_MAX,
				String.format(CommonConstants.IMPORT_LIBRARY_CONSTRAINTS, AnnotationConstants.ANNOTATION_MAX));

		// org.springframework.web.bind.annotation IMPORT_LIBRARY_WEB_BIND
		mapping.put(AnnotationConstants.ANNOTATION_GET_MAPPING,
				String.format(CommonConstants.IMPORT_LIBRARY_WEB_BIND, AnnotationConstants.ANNOTATION_GET_MAPPING));
		mapping.put(AnnotationConstants.ANNOTATION_POST_MAPPING,
				String.format(CommonConstants.IMPORT_LIBRARY_WEB_BIND, AnnotationConstants.ANNOTATION_POST_MAPPING));
		mapping.put(AnnotationConstants.ANNOTATION_PUT_MAPPING,
				String.format(CommonConstants.IMPORT_LIBRARY_WEB_BIND, AnnotationConstants.ANNOTATION_PUT_MAPPING));
		mapping.put(AnnotationConstants.ANNOTATION_DELETE_MAPPING,
				String.format(CommonConstants.IMPORT_LIBRARY_WEB_BIND, AnnotationConstants.ANNOTATION_DELETE_MAPPING));
		mapping.put(AnnotationConstants.ANNOTATION_REQUEST_BODY,
				String.format(CommonConstants.IMPORT_LIBRARY_WEB_BIND, AnnotationConstants.ANNOTATION_REQUEST_BODY));
		mapping.put(AnnotationConstants.ANNOTATION_REQUEST_PARAM,
				String.format(CommonConstants.IMPORT_LIBRARY_WEB_BIND, AnnotationConstants.ANNOTATION_REQUEST_PARAM));

		// Other library
		mapping.put(CommonConstants.STR_LIST, "java.util.List");
		mapping.put(CommonConstants.STR_SERIALIZABLE, "java.io.Serializable");
		mapping.put(CommonConstants.STR_DATE, "java.util.Date");

		return mapping;
	}

}
