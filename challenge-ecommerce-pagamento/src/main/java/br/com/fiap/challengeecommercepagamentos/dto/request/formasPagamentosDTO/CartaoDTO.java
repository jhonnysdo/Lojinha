package br.com.fiap.challengeecommercepagamentos.dto.request.formasPagamentosDTO;

import br.com.fiap.challengeecommercepagamentos.enums.StatusPagamento;
import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CartaoDTO {
    private String idCartao;
    @NotNull(message = "O Numero do Cartão não pode estar vazio.")
    private String numeroCartao;
    @NotNull(message = "A Data de Vencimento do Cartão não pode estar vazia.")
    private String dataVencimento;
    @NotNull(message = "O Codigo de Segurança não pode estar vazio.")
    private String codigoSeguranca;
    @NotNull(message = "O Nome do Responsável do Cartão não pode estar vazio.")
    private String nomeResponsavel;
    @NotNull(message = "O Tipo de da Forma de Pagamento não pode ser Nulo.")
    private TipoFormaPagamento tipoFormaPagamento;

    private double valor;

    private StatusPagamento statusPagamento;
}
