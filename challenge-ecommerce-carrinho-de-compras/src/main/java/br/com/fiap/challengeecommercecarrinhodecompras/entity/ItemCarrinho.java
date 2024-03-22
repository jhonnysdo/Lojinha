package br.com.fiap.challengeecommercecarrinhodecompras.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O ID do produto não pode ser nulo")
    private Long produtoId;

    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private int quantidade;

    private String produtoNome;

    @NotNull(message = "O preço não pode ser nulo")
    private Double precoUnitario;

    @JsonProperty
    public Long getId() {
        return id;
    }
}