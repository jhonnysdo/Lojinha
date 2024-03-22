package br.com.fiap.challengeecommercepagamentos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CarrinhoDTO {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String username;

    @NotNull
    private boolean fechado;

    @NotNull
    private LocalDate dataCriacao;

    @NotNull
    private Double valorTotal;

    @NotNull
    private List<ItemCarrinhoDTO> itens;

    @JsonProperty
    public Long getId() {
        return id;
    }
}
