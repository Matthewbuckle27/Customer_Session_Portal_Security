package com.maveric.projectcharter.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(description = "LOCAL swagger  ui", url = "http://localhost:8080/sessions"),})
public class SessionConfig {
	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}
}
