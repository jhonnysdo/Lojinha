package br.com.fiap.challengeecommercepagamentos.entity;

import br.com.fiap.challengeecommercepagamentos.enums.FormaPagamento;
import br.com.fiap.challengeecommercepagamentos.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long carrinhoId;

    @NotNull
    private Double carrinhoValorTotal;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private String username;

    private LocalDateTime dataPagamento;

    private FormaPagamento formaPagamento;
}
