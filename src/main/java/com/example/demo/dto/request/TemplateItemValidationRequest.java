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
public class TemplateItemValidationRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String itemId;

	private String displayName;

	private String type;

	private String tableIndex;

	private Boolean notNull;

	private Boolean formatDate;

	private Boolean formatMail;

	private Boolean formatPhone;

	private String minLength;

	private String maxLength;

	public void setItem(int index, String value) {
		if (value != null && value.equals(CommonConstants.SPECIAL_CHARACTER_HYPHEN)) {
			value = null;
		}
		TemplateItemValidationRequestKey key = TemplateItemValidationRequestKey.getByValue(index);
		if (key != null) {
			switch (key) {
			case ITEM_ID:
				itemId = StringUtil.trimValue(value);
				break;
			case DISPLAY_NAME:
				displayName = StringUtil.trimValue(value);
				break;
			case TYPE:
				type = StringUtil.trimValue(value);
				break;
			case TABLE_INDEX:
				tableIndex = StringUtil.trimValue(value);
				break;
			case NOT_NULL:
				notNull = convertToBoolean(value);
				break;
			case FORMAT_DATE:
				formatDate = convertToBoolean(value);
				break;
			case FORMAT_MAIL:
				formatMail = convertToBoolean(value);
				break;
			case FORMAT_PHONE:
				formatPhone = convertToBoolean(value);
				break;
			case MIN_LENGTH:
				minLength = StringUtil.trimValue(value);
				break;
			case MAX_LENGTH:
				maxLength = StringUtil.trimValue(value);
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