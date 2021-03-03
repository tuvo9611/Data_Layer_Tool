package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TablePostgresTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tableName;
	
	private String primaryKey;
	
	private Boolean autoIncrement;
	
	private List<ColumnPostgresTemplate> columns;
	
	private List<ForeignKeyPostgresTemplate> foreignKeys;
}
 