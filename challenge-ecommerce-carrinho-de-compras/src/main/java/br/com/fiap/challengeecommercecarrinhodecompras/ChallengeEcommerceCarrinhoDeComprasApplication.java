package br.com.fiap.challengeecommercecarrinhodecompras;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(title = "Tech Challenge 5 - E-commerce System - Microsserviço de Carrinho de compras", version = "1", description = "Esse microsserviço é responsável por gerenciar o carrinho de compras do e-commerce."))
@SpringBootApplication
public class ChallengeEcommerceCarrinhoDeComprasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeEcommerceCarrinhoDeComprasApplication.class, args);
	}
}
