package br.com.fiap.challengeecommercecarrinhodecompras.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Verifica Token JWT", description = "APIs Relacionadas a Verificação de Autenticação")
public class PingController {

    private static final String ALIVE_RESPONSE = "Alive!";

    @Operation(summary = "Verificar Status Token", description = "Verifica se o token está ativo")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/ping")
    public String pingResponse() {
        return ALIVE_RESPONSE;
    }
}
