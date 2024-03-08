package br.com.fiap.challengeecommercepagamentos.dto.request.formasPagamentosDTO;

import br.com.fiap.challengeecommercepagamentos.dto.request.FormaPagamentoDtoRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoCreditoDTO extends FormaPagamentoDtoRequest {
    @NotNull(message = "O Numero do Cartão não pode estar vazio.")
    private String numeroCartao;
    @NotNull(message = "A Data de Vencimento do Cartão não pode estar vazia.")
    private String dataVencimento;
    @NotNull(message = "O Codigo de Segurança não pode estar vazio.")
    private String codigoSeguranca;
    @NotNull(message = "O Nome do Responsável do Cartão não pode estar vazio.")
    private String nomeResponsavel;
}
