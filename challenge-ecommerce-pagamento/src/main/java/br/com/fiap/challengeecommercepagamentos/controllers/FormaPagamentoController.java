package br.com.fiap.challengeecommercepagamentos.controllers;

import br.com.fiap.challengeecommercepagamentos.dto.request.FormaDePagamentoDTO;
import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class FormaPagamentoController {
    @PostMapping
    public ResponseEntity<FormaDePagamentoDTO> cadastrarFormaPagamento(@RequestBody FormaDePagamentoDTO formaPagamentoDTO){
        return null;
    }

    @GetMapping
    public List<Pagamento> listarFormasPagamento (){
        return null;
    }

    @PutMapping
    public ResponseEntity<Pagamento> editarFormaPagamento (){
        return null;
    }

    @DeleteMapping
    public ResponseEntity<Void> removerFormaPagamento(){
        return null;
    }

}
