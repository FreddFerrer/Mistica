package com.mistica.EducarTransformar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EducarTransformarApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducarTransformarApplication.class, args);
	}

	@Configuration
	public class CorsConfig implements WebMvcConfigurer {

	}

}
