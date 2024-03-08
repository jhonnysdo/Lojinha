package br.com.fiap.challengeecommercepagamentos.mapper;

import br.com.fiap.challengeecommercepagamentos.dto.request.FormaPagamentoDtoRequest;
import br.com.fiap.challengeecommercepagamentos.entity.FormaPagamento;
import org.mapstruct.Mapper;
@Mapper
public interface FormaPagamentoMapper {

    FormaPagamentoDtoRequest toDTO(FormaPagamento formaPagamento);

    FormaPagamento toEntity (FormaPagamentoDtoRequest formaPagamentoDtoRequest);
}
