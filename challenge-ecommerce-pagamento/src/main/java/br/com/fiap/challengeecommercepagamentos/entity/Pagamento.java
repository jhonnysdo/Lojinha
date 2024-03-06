package br.com.fiap.challengeecommercepagamentos.entity;

import br.com.fiap.challengeecommercepagamentos.enums.StatusPagamento;
import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private LocalDate dataPagamento;
    @NotNull(message = "A Forma de Pagamento não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private TipoFormaPagamento tipoFormaPagamento;
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    private Long pedidoId;



}
