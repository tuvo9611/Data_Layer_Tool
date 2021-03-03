package com.example.demo.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ForeignKeyPostgresTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
		
	private String foreignKeyConstraint;
	
	private String foreignKey;
	
	private String tableReference;
}
 