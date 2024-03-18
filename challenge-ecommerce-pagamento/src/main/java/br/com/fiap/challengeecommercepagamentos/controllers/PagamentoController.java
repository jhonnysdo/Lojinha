package br.com.fiap.challengeecommercepagamentos.controllers;


import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


public class PagamentoController {



    @PostMapping
    public ResponseEntity<Pagamento> iniciarPagamento(@RequestParam String username, @RequestParam double total, @RequestParam TipoFormaPagamento tipoFormaPagamento){

        return null;
    }

//    @PostMapping
//    public ResponseEntity<Pagamento> confirmarPagamento(){
//        return null;
//    }
}
