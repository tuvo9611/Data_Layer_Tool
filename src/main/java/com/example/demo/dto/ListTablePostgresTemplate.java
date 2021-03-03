package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListTablePostgresTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<TablePostgresTemplate> tables;
}
 