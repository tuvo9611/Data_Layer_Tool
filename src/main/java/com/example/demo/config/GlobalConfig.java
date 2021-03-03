package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.example.demo.config.dto.Input;
import com.example.demo.config.dto.Mustache;
import com.example.demo.config.dto.Output;
import com.example.demo.config.dto.PackageName;
import com.example.demo.config.dto.Pattern;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class GlobalConfig {
	
	private Mustache mustache;
	
	private Input input;
	
	private Output output;
	
	private PackageName packageName;
	
	private Pattern pattern;
}
