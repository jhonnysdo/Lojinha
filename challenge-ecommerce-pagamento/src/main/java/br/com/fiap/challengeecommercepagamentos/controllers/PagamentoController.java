package br.com.fiap.challengeecommercepagamentos.controllers;


import br.com.fiap.challengeecommercepagamentos.dto.PagamentoDTO;
import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import br.com.fiap.challengeecommercepagamentos.enums.FormaPagamento;
import br.com.fiap.challengeecommercepagamentos.exceptions.HttpUnauthorizedException;
import br.com.fiap.challengeecommercepagamentos.services.JwtService;
import br.com.fiap.challengeecommercepagamentos.services.PagamentoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
@AllArgsConstructor
@OpenAPIDefinition
@Tag(name = "Pagamento", description = "APIs Relacionadas ao Pagamento")
public class PagamentoController {

    private final JwtService jwtService;

    private PagamentoService pagamentoService;

    @Operation(summary = "Lista Todos os Pagamentos")
    @GetMapping("/todos")
    public ResponseEntity<List<PagamentoDTO>> listarTodos(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAdminAuthorized(authorizationHeader);
        return ResponseEntity.ok(pagamentoService.listarTodos(authorizationHeader));
    }

    @Operation(summary = "Realiza Pagamento")
    @PutMapping("/pagar/{formaPagamento}")
    public ResponseEntity<String> realizarPagamento(
            @PathVariable FormaPagamento formaPagamento,
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        pagamentoService.realizarPagamento(formaPagamento, authorizationHeader);
        return ResponseEntity.ok("Pagamento realizado com sucesso");
    }

    @Operation(summary = "Cancelar Pagamento")
    @PutMapping("/cancelar")
    public ResponseEntity<String> cancelarPagamento(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        pagamentoService.cancelarPagamento(authorizationHeader);
        return ResponseEntity.ok("Pagamento cancelado com sucesso");
    }

    @Operation(summary = "Consultar Pagamento")
    @GetMapping
    public ResponseEntity<Pagamento> consultar(@RequestParam String id) {
        return ResponseEntity.ok(new Pagamento());
    }

    private void isAuthorized(String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            throw new HttpUnauthorizedException();
        }
        String token = tokenHeader.substring(7);

        String role = jwtService.extractRole(token);
        if (!role.equals("ADMIN") && !role.equals("USER")) {
            throw new HttpUnauthorizedException();
        }
    }

    private void isAdminAuthorized(String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            throw new HttpUnauthorizedException();
        }
        String token = tokenHeader.substring(7);

        String role = jwtService.extractRole(token);
        if (!role.equals("ADMIN")) {
            throw new HttpUnauthorizedException();
        }
    }
}
