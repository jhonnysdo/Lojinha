package br.com.fiap.challengeecommercelogin.controllers.dtos;

import br.com.fiap.challengeecommercelogin.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO {

    private String username;
    private String password;
    private Role role;
}
