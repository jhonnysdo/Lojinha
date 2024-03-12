package br.com.fiap.challengeecommercepagamentos.mapper;

import br.com.fiap.challengeecommercepagamentos.dto.request.formasPagamentosDTO.CartaoDTO;
import br.com.fiap.challengeecommercepagamentos.entity.Cartao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CartaoMapper {
    CartaoDTO toDTO(Cartao cartao);
    Cartao toEntity(CartaoDTO cartaoDTO);

    List<CartaoDTO> toDTO(List<Cartao> cartoes);
}
