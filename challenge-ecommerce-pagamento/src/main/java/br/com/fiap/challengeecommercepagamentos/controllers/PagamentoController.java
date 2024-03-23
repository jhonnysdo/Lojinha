package br.com.fiap.challengeecommercepagamentos.controllers;


import br.com.fiap.challengeecommercepagamentos.dto.PagamentoDTO;
import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import br.com.fiap.challengeecommercepagamentos.enums.FormaPagamento;
import br.com.fiap.challengeecommercepagamentos.exceptions.HttpUnauthorizedException;
import br.com.fiap.challengeecommercepagamentos.services.JwtService;
import br.com.fiap.challengeecommercepagamentos.services.PagamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pagamento")
@AllArgsConstructor
public class PagamentoController {

    private final JwtService jwtService;

    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        return ResponseEntity.ok(pagamentoService.criarPagamento(authorizationHeader));
    }

    @PostMapping("/pagar")
    public ResponseEntity<String> realizarPagamento(
            @Valid @RequestBody FormaPagamento formaPagamento,
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        pagamentoService.realizarPagamento(formaPagamento, authorizationHeader);
        return ResponseEntity.ok("Pagamento realizado com sucesso");
    }

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
}
