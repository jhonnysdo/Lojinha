package br.com.fiap.challengeecommercepagamentos.services;

import br.com.fiap.challengeecommercepagamentos.dto.request.formasPagamentosDTO.CartaoDTO;
import br.com.fiap.challengeecommercepagamentos.entity.Cartao;

import br.com.fiap.challengeecommercepagamentos.mapper.CartaoMapper;
import br.com.fiap.challengeecommercepagamentos.mapper.FormaPagamentoMapper;
import br.com.fiap.challengeecommercepagamentos.repository.CartaoRepository;
import br.com.fiap.challengeecommercepagamentos.repository.FormaPagamentoRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;
    private final FormaPagamentoMapper formaPagamentoMapper;
    private final CartaoMapper cartaoMapper;
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
