package br.com.fiap.challengeecommercepagamentos.entity;

import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import br.com.fiap.challengeecommercepagamentos.services.FormaPagamentoService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import lombok.Setter;
@Entity
@Table(name = "tb_cartaos")
@Getter
@Setter
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCartao;
    @NotNull(message = "O Numero do Cartão não pode estar vazio.")
    private String numeroCartao;
    @NotNull(message = "A Data de Vencimento do Cartão não pode estar vazia.")
    private String dataVencimento;
    @NotNull(message = "O Codigo de Segurança não pode estar vazio.")
    private String codigoSeguranca;
    @NotNull(message = "O Nome do Responsável do Cartão não pode estar vazio.")
    private String nomeResponsavel;
    @Enumerated(EnumType.STRING)
    private TipoFormaPagamento tipoFormaPagamento;
    @NotNull(message = "O nome de usuário não pode ser nulo nem branco.")
    private String username;

}
