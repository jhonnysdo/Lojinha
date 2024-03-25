package br.com.fiap.challengeecommercegestaoitens;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(title = "Tech Challenge 5 - E-commerce System - Microsserviço de Gestão de Itens", version = "1.0", description = "Esse microsserviço é responsável por Gerenciar os Itens do E-Commerce."))
@SpringBootApplication
public class ChallengeEcommerceGestaoItensApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeEcommerceGestaoItensApplication.class, args);
	}

}
