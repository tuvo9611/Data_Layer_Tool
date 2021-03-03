package com.example.demo.dto.request;

import java.io.Serializable;

import com.example.demo.common.CommonConstants;
import com.example.demo.common.util.StringUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TemplateRecordEntityRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String columnName;

	private String dataType;

	private Boolean primaryKey;

	private Boolean foreignKey;

	private String length;

	private Boolean unique;

	private Boolean notNull;

	private Boolean autoIncement;

	private String defaultValue;

	private String pattern;

	private String remark;

	public void setItem(int index, String value) {
		if (value != null && value.equals(CommonConstants.SPECIAL_CHARACTER_HYPHEN)) {
			value = null;
		}
		TemplateEntityRequestKey key = TemplateEntityRequestKey.getByValue(index);
		if (key != null) {
			switch (key) {
			case COLUMN_NAME:
				columnName = StringUtil.formatValueUpper(value);
				break;
			case DATA_TYPE:
				dataType = StringUtil.formatValueUpper(value);
				break;
			case PRIMARY_KEY:
				primaryKey = convertToBoolean(value);
				break;
			case FOREIGN_KEY:
				foreignKey = convertToBoolean(value);
				break;
			case LENGTH:
				length = StringUtil.trimValue(value);
				break;
			case UNIQUE:
				unique = convertToBoolean(value);
				break;
			case NOT_NULL:
				notNull = convertToBoolean(value);
				break;
			case AUTO_INCREMENT:
				autoIncement = convertToBoolean(value);
				break;
			case DEFAULT_VALUE:
				defaultValue = StringUtil.trimValue(value);
				break;
			case PATTERN:
				pattern = StringUtil.trimValue(value);
				break;
			case REMARK:
				remark = StringUtil.trimValue(value);
				break;
			default:
				break;
			}
		}
	}

	private Boolean convertToBoolean(String value) {
		if (value != null && value.trim().equals(CommonConstants.SPECIAL_CHARACTER_CYCLE_TRUE_VALUE)) {
			return true;
		} else {
			return false;
		}
	}
}
