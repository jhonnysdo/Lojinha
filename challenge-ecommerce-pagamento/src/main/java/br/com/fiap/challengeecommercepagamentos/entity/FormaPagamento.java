package br.com.fiap.challengeecommercepagamentos.entity;

import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String descricao;
    private TipoFormaPagamento tipoFormaPagamento;
    @NotNull(message = "O nome de usuário não pode ser nulo nem branco.")
    private String usuario;

}
