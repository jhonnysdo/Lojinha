package br.com.fiap.challengeecommercecarrinhodecompras.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome de usuário não pode ser nulo nem branco.")
    private String username;

    @NotNull(message = "O ID do produto não pode ser nulo")
    private Long produtoId;

    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private int quantidade;
}