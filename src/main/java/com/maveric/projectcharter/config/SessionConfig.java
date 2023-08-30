package com.maveric.projectcharter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {
	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}

	@Bean
	public OpenAPI myOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("customer_session_portal")
						.description("session_management_api")
						.version("1.0"));
	}
}
