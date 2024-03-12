package br.com.fiap.challengeecommercepagamentos.mapper;

import br.com.fiap.challengeecommercepagamentos.dto.request.PagamentoDtoRequest;
import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PagamentoMapper {

    PagamentoDtoRequest toDto(Pagamento pagamento);

    Pagamento toEntity(PagamentoDtoRequest pagamentoDtoRequest);

}
