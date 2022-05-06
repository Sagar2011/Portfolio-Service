package com.business.portfolio;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class PortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${spring.application.version}") String appVersion) {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("Portfolio Tracking Service").version(appVersion)
						.license(new License().name("Portfolio 1.0").url("https://github.com/Sagar2011/Portfolio-Service")));
	}

}
