package br.com.fiap.challengeecommercepagamentos.entity;

import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cartao{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private TipoFormaPagamento tipoFormaPagamento;


}
