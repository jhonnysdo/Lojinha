package br.com.fiap.challengeecommercepagamentos.controllers;


import br.com.fiap.challengeecommercepagamentos.dto.request.formasPagamentosDTO.CartaoDTO;
import br.com.fiap.challengeecommercepagamentos.services.FormaPagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/formas-pagamento")

public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;

    @PostMapping
    public ResponseEntity<CartaoDTO> cadastrarCartao(@RequestBody CartaoDTO cartaoDTO) {
        var cartaoCadastrado = formaPagamentoService.cadastrarCartao(cartaoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand((cartaoCadastrado.getIdCartao())).toUri();
        return ResponseEntity.created(uri).body(cartaoCadastrado);
    }


    @GetMapping
    public ResponseEntity<List<CartaoDTO>> listarCartoes(){
        List<CartaoDTO> cartoes = formaPagamentoService.listarCartoes();
        return ResponseEntity.ok(cartoes);

    }



    @PutMapping("/{id}")
    public ResponseEntity<CartaoDTO> editarFormaPagamento(@RequestBody CartaoDTO cartaoDTO, @PathVariable String idCartao) {
        var cartaoAtualizado = formaPagamentoService.editarFormaPagamento(idCartao, cartaoDTO);
        return ResponseEntity.ok(cartaoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFormaPagamento(@PathVariable String id) {
        formaPagamentoService.removerFormaPagamento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
