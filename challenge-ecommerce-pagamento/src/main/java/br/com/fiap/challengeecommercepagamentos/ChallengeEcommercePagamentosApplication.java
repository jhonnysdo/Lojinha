package br.com.fiap.challengeecommercepagamentos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(title = "Tech Challenge 5 - E-commerce System - Microsserviço de Pagamentos", version = "1", description = "Esse Microsserviço é Responsável por Realizar Pagamentos no Sistema de E-Commerce."))
@SpringBootApplication
public class ChallengeEcommercePagamentosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeEcommercePagamentosApplication.class, args);
    }

}
