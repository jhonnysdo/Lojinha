package br.com.fiap.challengeecommercegestaoitens.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {

    @JsonIgnore
    private Long id;

    @NotNull(message = "O nome do produto não pode ser nulo")
    private String nome;

    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private int quantidade;

    @NotNull(message = "O preço não pode ser nulo")
    private Double preco;

    @JsonProperty
    public Long getId() {
        return id;
    }
}
