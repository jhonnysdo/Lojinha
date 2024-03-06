package br.com.fiap.challengeecommercepagamentos.controllers;

import br.com.fiap.challengeecommercepagamentos.dto.request.FormaPagamentoDTO;
import br.com.fiap.challengeecommercepagamentos.entity.FormaPagamento;
import br.com.fiap.challengeecommercepagamentos.repository.FormaPagamentoRepository;
import br.com.fiap.challengeecommercepagamentos.services.FormaPagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/formas-pagamento")

public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;

    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> cadastrarFormaPagamento(@RequestBody FormaPagamentoDTO formaPagamentoDTO) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamento>> listarFormasPagamento() {
     return new ResponseEntity<>(formaPagamentoService.listarFormasPagamento(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FormaPagamento> editarFormaPagamento() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>removerFormaPagamento(@PathVariable String id) {
       formaPagamentoService.removerFormaPagamento(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);

}

}
