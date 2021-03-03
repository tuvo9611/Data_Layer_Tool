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
public class RecordExcelServiceFrontEndRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String methodname;
	private String httpmethod;
	private String url;

	public void setItem(int index, String value) {
		if (value != null && value.equals(CommonConstants.SPECIAL_CHARACTER_HYPHEN)) {
			value = null;
		}

		TemplateServiceFrontEndRequestKey key = TemplateServiceFrontEndRequestKey.getByValue(index);
		if (key != null) {
			switch (key) {
			case METHOD_NAME:
				methodname = StringUtil.trimValue(value);
				break;
			case HTTP_METHOD:
				httpmethod = StringUtil.formatValueLower(value);
				break;
			case URL:
				url = StringUtil.trimValue(value);
				break;
			default:
				break;
			}
		}
	}
}
