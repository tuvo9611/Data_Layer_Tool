package com.example.demo.common;

import java.util.Arrays;
import java.util.List;

public class CommonConstants {
	private CommonConstants() {
		// empty constructor
	}

	public static final char CHAR_WHITE_SPACE = ' ';

	public static final char UNDERSCORE = '_';
	
	public static final String WHITE_SPACE_JAPAN = "\u3000";
	
	public static final String WHITE_SPACE = " ";
	/**
	 * Special character for null value
	 */
	public static final String SPECIAL_CHARACTER_HYPHEN = "-";
	/**
	 * Special character for white space
	 */
	public static final String SPECIAL_CHARACTER_WHITESPACE = " ";
	/**
	 * Special character for white space
	 */
	public static final String REGEX_SPACE_1_BYTE = "\\s+";
	/**
	 * Special character for white space
	 */
	public static final String EMPTY_SPACE = "";
	/**
	 * Special character for white space
	 */
	public static final String REGEX_SPACE_2_BYTE = "　";
	/**
	 * Special character for True value
	 */
	public static final String SPECIAL_CHARACTER_CYCLE_TRUE_VALUE = "○";

	/**
	 * Folder stored file excel and mustache
	 */
	public static final String FOLDER_TEMPLATE = "classpath:templates/%s";

	public static final String COMMA = ",";
	public static final String STR_EXTENDS = "extends";
	public static final String STR_BASE_ENTITY = "BaseEntity";
	public static final String STR_IMPLEMENTS = "implements";
	public static final String STR_DATE = "Date";
	public static final String STR_SERIALIZABLE = "Serializable";
	public static final String STR_LIST = "List";
	public static final String STR_NOT_NULL = " NOT NULL";
	public static final String STR_VARCHAR = "varchar";
	public static final String STR_VARCHAR_POSTGRES = "character varying";
	public static final String STR_JSONB = "JSONB";
	public static final String STR_NUMERIC = "NUMERIC";
	public static final String STR_TEXT = "TEXT";

	public static final String DATA_TYPE_ONE_TO_MANY = "List<%s>";
	public static final String BRACKET_BETWEEN = "(%s)";
	public static final String IMPORT_LIBRARY_CONSTRAINTS = "javax.validation.constraints.%s";
	public static final String IMPORT_LIBRARY_PERSISTENCE = "javax.persistence.%s";
	public static final String FILE_REPOSITORY = "%sRepository.java";
	public static final String FILE_WITH_ENTENSION_JAVA = "%s.java";
	public static final String FILE_BASE_ENTITY = "BaseEntity.java";
	public static final String DIRECTORY_FILE_BASE_ENTITY = "src/main/java/com/example/demo/entity/BaseEntity.java";
	public static final String FORMAT_FOREIGN_KEY_CONSTRAINT = "fk%s";
	public static final String IMPORT_LIBRARY_WEB_BIND = "org.springframework.web.bind.annotation.%s";
	public static final String FORMAT_ANNOTATION_AND_WHITE_SPACE = "@%s ";
	public static final String FORMAT_ANNOTATION = "@%s";
	public static final String FORMAT_CONTROLLER_NAME = "%sController";
	public static final String FORMAT_LIBRARY_IMPORT = "%s.%s";

	public static final String CLASS_TYPE_REQUEST = "Request";
	public static final String CLASS_TYPE_RESPONSE = "Response";
	public static final String FORMAT_DEFAULT_VALUE_STRING = "'%s'";
	public static final String FORMAT_REQUEST_PARAM = "@RequestParam(value = \"%1$s\", required = false) final %2$s %1$s";
	/**
	 * Value
	 */
	public static final String FORMAT_DEFAULT_VALUE = " DEFAULT %s";

	public static List<String> IGNORE_BASE_ENTITY = Arrays.asList("CREATED_BY", "CREATED_DATE", "IS_DELETED",
			"UPDATED_BY", "UPDATED_DATE", "VERSION");
	
	public static final String FILE_WITH_ENTENSION_TS = "%s.ts";
	
	public static List<String> METHOD_REQUEST_BODY = Arrays.asList("POST", "PUT", "DELETE");

	public static List<String> SPECIAL_DATA_TYPE_POSTGRES = Arrays.asList(STR_JSONB, STR_NUMERIC);
}
