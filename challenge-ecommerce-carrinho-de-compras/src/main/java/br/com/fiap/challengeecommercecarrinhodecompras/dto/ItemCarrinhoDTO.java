package br.com.fiap.challengeecommercecarrinhodecompras.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCarrinhoDTO {

    @NotNull(message = "O ID do produto não pode ser nulo")
    private Long produtoId;

    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private int quantidade;
}
