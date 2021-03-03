package com.example.demo.common;

public class AnnotationConstants {
	private AnnotationConstants() {
		// empty constructor
	}

	/**
	 * 
	 * javax.persistence
	 * 
	 */
	public static final String ANNOTATION_ONE_TO_ONE = "OneToOne";
	public static final String ANNOTATION_MANY_TO_ONE = "ManyToOne";
	public static final String ANNOTATION_JOIN_COLUMN = "JoinColumn";
	public static final String ANNOTATION_ONE_TO_MANY = "OneToMany";
	public static final String ANNOTATION_PRIMARY_KEY = "Id";
	public static final String ANNOTATION_AUTO_INCREMENT = "GeneratedValue";
	public static final String ANNOTATION_COLUMN = "Column";
	public static final String ANNOTATION_GENERATION_TYPE = "GenerationType";
	public static final String ANNOTATION_FETCH_TYPE = "FetchType";

	/**
	 * 
	 * javax.persistence value
	 * 
	 */
	public static final String ANNOTATION_VALUE_ONE_TO_ONE = "(mappedBy = \"%s\", targetEntity = %s.class)";
	public static final String ANNOTATION_VALUE_JOIN_COLUMN = "(name = \"%1$s\", referencedColumnName = \"%1$s\", nullable = false)";
	public static final String ANNOTATION_VALUE_MAPPED_BY = "(mappedBy = \"%s\")";
	public static final String ANNOTATION_VALUE_AUTO_INCREMENT = "(strategy = GenerationType.IDENTITY)";
	public static final String ANNOTATION_COLUMN_VALUE_NAME = "name = \"%s\"";
	public static final String ANNOTATION_COLUMN_VALUE_UNIQUE = ", unique = true";
	public static final String ANNOTATION_COLUMN_VALUE_COLUMN_DEFINE_AND_DEFAULT_VALUE = ", columnDefinition = \"%s default %s\"";
	public static final String ANNOTATION_COLUMN_VALUE_OTHER_DATA_TYPE = ", columnDefinition = \"%s\"";
	public static final String ANNOTATION_COLUMN_VALUE_DEFAULT_VALUE = " default %s\"";
	public static final String ANNOTATION_COLUMN_VALUE_NUMERIC_FORMAT = ", precision = %s, scale = %s";

	/**
	 * 
	 * javax.validation.constraints
	 * 
	 */
	public static final String ANNOTATION_NOT_NULL = "NotNull";
	public static final String ANNOTATION_SIZE = "Size";
	public static final String ANNOTATION_PATTERN = "Pattern";
	public static final String ANNOTATION_MIN = "Min";
	public static final String ANNOTATION_MAX = "Max";

	/**
	 * 
	 * javax.validation.constraints value
	 * 
	 */
	public static final String ANNOTATION_NOT_NULL_VALUE = "(message = \"Cannot be null\")";
	public static final String ANNOTATION_VALUE_MESSAGE = "(message = \"%s\")";
	public static final String ANNOTATION_VALUE_SIZE_MAX = "(max = %1$s, message = \"Max length is %1$s\")";
	public static final String ANNOTATION_PATTERN_REGEX = "(message = \"Invalid pattern.\", regexp = \"%s\")";
	public static final String ANNOTATION_VALUE_PATTERN_REGEX = "(regexp = \"%s\", message = \"%s\")";
	public static final String ANNOTAITON_VALUE_LENGTH = "(value = %s, message = \"%s\")";

	/**
	 * 
	 * org.springframework.web.bind.annotation
	 * 
	 */
	public static final String ANNOTATION_REQUEST_BODY = "RequestBody";
	public static final String ANNOTATION_GET_MAPPING = "GetMapping";
	public static final String ANNOTATION_POST_MAPPING = "PostMapping";
	public static final String ANNOTATION_PUT_MAPPING = "PutMapping";
	public static final String ANNOTATION_DELETE_MAPPING = "DeleteMapping";
	public static final String ANNOTATION_REQUEST_PARAM = "RequestParam";
}
