package br.com.fiap.challengeecommercepagamentos.services;

import br.com.fiap.challengeecommercepagamentos.dto.request.formasPagamentosDTO.CartaoDTO;
import br.com.fiap.challengeecommercepagamentos.entity.Cartao;

import br.com.fiap.challengeecommercepagamentos.mapper.CartaoMapper;
import br.com.fiap.challengeecommercepagamentos.mapper.FormaPagamentoMapper;
import br.com.fiap.challengeecommercepagamentos.repository.CartaoRepository;
import br.com.fiap.challengeecommercepagamentos.repository.FormaPagamentoRepository;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FormaPagamentoService {
    @Lazy
    private final FormaPagamentoRepository formaPagamentoRepository;
    @Lazy
    private final CartaoMapper cartaoMapper;
    @Lazy
    private final CartaoRepository cartaoRepository;


    public CartaoDTO cadastrarCartao(CartaoDTO cartaoDTO) {
        final Cartao cartaoCadastrado = cartaoRepository.save(cartaoMapper.toEntity(cartaoDTO));
        return cartaoMapper.toDTO(cartaoCadastrado);
    }

    public List<CartaoDTO> listarCartoes() {
        List<Cartao> cartoes = cartaoRepository.findAll();
        return cartoes.stream().map(cartaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CartaoDTO buscarCartaoPorId(Long id) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            return cartaoMapper.toDTO(cartaoOptional.get());
        }
        return new CartaoDTO();

    }

//    public ResponseEntity<?> buscarFormaPagamentoPorUsername(String username) {
//        return ResponseEntity.ok(formaPagamentoRepository.findByUsername(username));
//    }


    public CartaoDTO editarFormaPagamento(Long id, CartaoDTO cartaoDTO) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            Cartao cartao = cartaoOptional.get();

            cartao.setNumeroCartao(cartaoDTO.getNumeroCartao());
            cartao.setNomeResponsavel(cartaoDTO.getNomeResponsavel());
            cartao.setDataVencimento(cartaoDTO.getDataVencimento());
            cartao.setCodigoSeguranca(cartaoDTO.getCodigoSeguranca());

            cartao = cartaoRepository.save(cartao);

            return cartaoMapper.toDTO(cartao);
        }
        return new CartaoDTO();
    }

    public void removerFormaPagamento(Long id) {
        formaPagamentoRepository.deleteById(id);
    }


}
