package com.example.demo.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FrontEndServiceTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pagename;

	private String methodname;

	private String url;

	private String method;

	private boolean methodmapping;

	public FrontEndServiceTemplate(String pagename, String methodname, String url, String method) {
		super();
		this.pagename = pagename;
		this.methodname = methodname;
		this.url = url;
		this.method = method;
	}
}
