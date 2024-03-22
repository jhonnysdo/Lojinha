package br.com.fiap.challengeecommercepagamentos.dto;

import br.com.fiap.challengeecommercepagamentos.enums.FormaPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PagamentoDTO {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String username;

    @NotNull
    private LocalDate dataPagamento;

    @NotNull
    private Long carrinhoId;

    @NotNull
    private Double carrinhoValorTotal;

    @NotNull
    private FormaPagamento formaPagamento;

    @JsonProperty
    public Long getId() {
        return id;
    }
}
