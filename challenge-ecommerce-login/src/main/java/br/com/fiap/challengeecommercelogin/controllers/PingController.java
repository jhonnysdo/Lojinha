package br.com.fiap.challengeecommercelogin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Verifica Token JWT", description = "APIs Relacionadas a Verificação de Autenticação")
public class PingController {
    @Operation(summary = "Verificar Status Token", description = "Verifica se o token está ativo.")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Alive!";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
