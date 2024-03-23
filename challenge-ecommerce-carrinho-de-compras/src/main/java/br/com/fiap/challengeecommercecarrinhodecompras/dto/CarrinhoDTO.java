package br.com.fiap.challengeecommercecarrinhodecompras.dto;

import br.com.fiap.challengeecommercecarrinhodecompras.Enum.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CarrinhoDTO {

    private Long id;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private Status status;

    private LocalDate dataCriacao;

    private Double valorTotal;

    private List<ItemCarrinhoDTO> itens;

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public Status getStatus() {
        return status;
    }
}
