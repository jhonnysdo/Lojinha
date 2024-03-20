package br.com.fiap.challengeecommercelogin;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(title = "Tech Challenge 5 - E-commerce System - Microsserviço de Login e Autenticação", version = "1", description = "Esse microsserviço é responsável por realizar o login e autenticação de usuários no sistema de e-commerce."))
@SpringBootApplication
public class ChallengeEcommerceLoginApplication {

	public static void main(String[] args) {

		SpringApplication.run(ChallengeEcommerceLoginApplication.class, args);
	}

}
