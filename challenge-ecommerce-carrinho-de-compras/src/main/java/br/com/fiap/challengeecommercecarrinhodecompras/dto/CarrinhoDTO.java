package br.com.fiap.challengeecommercecarrinhodecompras.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarrinhoDTO {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String username;

    private boolean fechado;
    private List<ItemCarrinhoDTO> itens;

    @JsonProperty
    public Long getId() {
        return id;
    }
}
