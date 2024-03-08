package br.com.fiap.challengeecommercepagamentos.dto.request;

import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FormaPagamentoDtoRequest {

    private String descricao;
    private TipoFormaPagamento tipoFormaPagamento;
    private String username;

}
