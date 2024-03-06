package br.com.fiap.challengeecommercepagamentos.services;

import br.com.fiap.challengeecommercepagamentos.dto.request.FormaPagamentoDTO;
import br.com.fiap.challengeecommercepagamentos.entity.FormaPagamento;
import br.com.fiap.challengeecommercepagamentos.repository.FormaPagamentoRepository;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@AllArgsConstructor
public class FormaPagamentoService {


    private final FormaPagamentoRepository formaPagamentoRepository;

    public ResponseEntity<FormaPagamentoDTO> cadastrarFormaPagamento(@RequestBody FormaPagamentoDTO formaPagamentoDTO) {
        return null;
    }


    public List<FormaPagamento> listarFormasPagamento() {
        return null;
    }


    public ResponseEntity<FormaPagamento> editarFormaPagamento() {
        return null;
    }
    
    public void removerFormaPagamento(String id) {
        formaPagamentoRepository.deleteById(id);
    }


}
