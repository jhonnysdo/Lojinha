package br.com.fiap.challengeecommercepagamentos.dto.request;

import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import lombok.*;

@Setter
@Getter

public class FormaPagamentoDtoRequest {

    private String descricao;
    private TipoFormaPagamento tipoFormaPagamento;
    private String username;

}
