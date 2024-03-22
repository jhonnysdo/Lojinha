package br.com.fiap.challengeecommercecarrinhodecompras.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCarrinhoDTO {

    @JsonIgnore
    private Long id;

    @NotNull(message = "O ID do produto não pode ser nulo")
    private Long produtoId;

    @JsonIgnore
    private String produtoNome;

    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private int quantidade;

    @JsonIgnore
    private Double precoUnitario;

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public String getProdutoNome() {
        return produtoNome;
    }

    @JsonProperty
    public Double getPrecoUnitario() {
        return precoUnitario;
    }
}
