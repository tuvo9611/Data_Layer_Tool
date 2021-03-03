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
public class TemplateParameterControllerRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String parameterId;

	private String name;

	private String valueType;

	public void setItem(int index, String value) {
		if (value != null && value.equals(CommonConstants.SPECIAL_CHARACTER_HYPHEN)) {
			value = null;
		}
		TemplateParameterControllerRequestKey key = TemplateParameterControllerRequestKey.getByValue(index);
		if (key != null) {
			switch (key) {
			case PARAMERTER_ID:
				parameterId = StringUtil.formatValueUpper(value);
				break;
			case NAME:
				name = StringUtil.trimValue(value);
				break;
			case VALUE_TYPE:
				valueType = StringUtil.trimValue(value);
				break;			
			default:
				break;
			}
		}
	}
}