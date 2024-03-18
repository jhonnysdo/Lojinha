package br.com.fiap.challengeecommercepagamentos.mapper;

import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import org.mapstruct.Mapper;

@Mapper
public interface PagamentoMapper {

    PagamentoDtoRequest toDto(Pagamento pagamento);

    Pagamento toEntity(PagamentoDtoRequest pagamentoDtoRequest);

}
