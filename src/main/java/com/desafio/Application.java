package com.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@OpenAPIDefinition(
		info = @Info(title = "Api do desafio", version = "3.0.1", description = "Api do desafio"), 
		servers = {
				@Server(url = "http://localhost:8080")
		})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
