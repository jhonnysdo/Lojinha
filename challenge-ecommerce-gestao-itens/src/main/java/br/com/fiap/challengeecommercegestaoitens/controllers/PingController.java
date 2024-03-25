package br.com.fiap.challengeecommercegestaoitens.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Verifica Token JWT", description = "API para verificar se o token JWT está funcionando corretamente.")
public class PingController {
    @Operation(summary = "Verifica Status Token", description = "Verifica se o token está ativo")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Alive!";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
