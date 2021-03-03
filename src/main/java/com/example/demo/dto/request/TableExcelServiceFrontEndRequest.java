package com.example.demo.dto.request;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TableExcelServiceFrontEndRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pageName;

	private List<RecordExcelServiceFrontEndRequest> records;
}
