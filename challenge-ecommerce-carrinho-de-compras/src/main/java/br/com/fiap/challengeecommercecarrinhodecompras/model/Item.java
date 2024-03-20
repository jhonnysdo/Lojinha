package br.com.fiap.challengeecommercecarrinhodecompras.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double preco;

    private Integer quantidade;
}
