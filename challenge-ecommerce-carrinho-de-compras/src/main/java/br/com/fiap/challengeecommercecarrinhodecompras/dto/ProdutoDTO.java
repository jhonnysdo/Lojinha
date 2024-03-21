package br.com.fiap.challengeecommercecarrinhodecompras.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {
    private Long id;

    @NotNull(message = "O nome do produto não pode ser nulo")
    private String nome;

    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private int quantidade;

    @NotNull(message = "O preço não pode ser nulo")
    private Double preco;
}
