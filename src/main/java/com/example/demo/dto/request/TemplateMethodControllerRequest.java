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
public class TemplateMethodControllerRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String apiId;

	private String methodName;

	private String httpMethod;

	private String url;
	
	private String parameter;

	private String requestDtoId;

	private String responseDtoId;

	public void setItem(int index, String value) {
		if (value != null && value.equals(CommonConstants.SPECIAL_CHARACTER_HYPHEN)) {
			value = null;
		}
		TemplateControllerRequestKey key = TemplateControllerRequestKey.getByValue(index);
		if (key != null) {
			switch (key) {
			case API_ID:
				apiId = StringUtil.formatValueUpper(value);
				break;
			case METHOD_NAME:
				methodName = StringUtil.trimValue(value);
				break;
			case HTTP_METHOD:
				httpMethod = StringUtil.formatValueUpper(value);
				break;
			case URL_API:
				url = StringUtil.trimValue(value);
				break;
			case PARAMETERS:
				parameter = StringUtil.formatValueUpper(value);
				break;
			case REQUEST_DTO_ID:
				requestDtoId = StringUtil.formatValueUpper(value);
				break;
			case RESPONSE_DTO_ID:
				responseDtoId = StringUtil.formatValueUpper(value);
				break;
			default:
				break;
			}
		}
	}
}
