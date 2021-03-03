package com.example.demo.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ColumnPostgresTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	private String columnName;
	
	private String dataType;
	
	private String length;
	
	private String defaultValue;
	
	private String notNull;
	
	private String comma;
}
