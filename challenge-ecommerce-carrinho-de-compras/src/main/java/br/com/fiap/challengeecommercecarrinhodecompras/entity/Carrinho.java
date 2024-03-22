package br.com.fiap.challengeecommercecarrinhodecompras.entity;

import jakarta.persistence.*;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome de usuário não pode ser nulo nem branco.")
    private String username;

    private boolean fechado;

    private LocalDate dataCriacao;

    private Double valorTotal;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemCarrinho> itens;
}
