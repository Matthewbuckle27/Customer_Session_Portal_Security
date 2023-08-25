package com.maveric.projectcharter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ProjectCharterApplication {
	public static void main(String[] args) {

		SpringApplication.run(ProjectCharterApplication.class, args);
	}

}
