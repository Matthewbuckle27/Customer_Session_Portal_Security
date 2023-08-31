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
				.info(new Info().title(Constants.SWAGGER_TITLE)
						.description(Constants.SWAGGER_DESCRIPTION)
						.version(Constants.SWAGGER_VERSION));
	}
}
