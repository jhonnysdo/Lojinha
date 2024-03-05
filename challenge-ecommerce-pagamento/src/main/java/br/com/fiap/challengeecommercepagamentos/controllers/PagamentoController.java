package br.com.fiap.challengeecommercepagamentos.controllers;


import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


public class PagamentoController {

    @PostMapping
    public ResponseEntity<Pagamento> confirmarPagamento(){
        return null;
    }

    @PostMapping
    public ResponseEntity<Pagamento> realizarPagamento(){
        return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<TipoFormaPagamento> obterFormaPagamento (){
        return null;
    }




}
