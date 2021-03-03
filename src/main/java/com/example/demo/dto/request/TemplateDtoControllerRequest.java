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
public class TemplateDtoControllerRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String dtoId;

	private String type;

	private String className;

	private String property;

	private String valueType;

	private String itemId;

	public void setItem(int index, String value) {
		if (value != null && value.equals(CommonConstants.SPECIAL_CHARACTER_HYPHEN)) {
			value = null;
		}
		TemplateDtoControllerRequestKey key = TemplateDtoControllerRequestKey.getByValue(index);
		if (key != null) {
			switch (key) {
			case DTO_ID:
				dtoId = StringUtil.trimValue(value);
				break;
			case TYPE:
				type = StringUtil.trimValue(value);
				break;
			case CLASS_NAME:
				className = StringUtil.trimValue(value);
				break;
			case PROPERTY:
				property = StringUtil.trimValue(value);
				break;
			case VALUE_TYPE:
				valueType = StringUtil.trimValue(value);
				break;
			case ITEM_ID:
				itemId = StringUtil.trimValue(value);
				break;
			default:
				break;
			}
		}
	}
}
