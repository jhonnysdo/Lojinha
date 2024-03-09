package br.com.fiap.challengeecommercepagamentos.dto.request.formasPagamentosDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoDTO {
    @NotNull(message = "O ID Do cartão não pode ser nulo.")
    private String idCartao;
    @NotNull(message = "O Numero do Cartão não pode estar vazio.")
    private String numeroCartao;
    @NotNull(message = "A Data de Vencimento do Cartão não pode estar vazia.")
    private String dataVencimento;
    @NotNull(message = "O Codigo de Segurança não pode estar vazio.")
    private String codigoSeguranca;
    @NotNull(message = "O Nome do Responsável do Cartão não pode estar vazio.")
    private String nomeResponsavel;
    @NotNull(message = "")
    private String tipoFormaPagamento;
}
