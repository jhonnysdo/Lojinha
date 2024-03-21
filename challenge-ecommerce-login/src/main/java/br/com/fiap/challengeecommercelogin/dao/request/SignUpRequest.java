package br.com.fiap.challengeecommercelogin.dao.request;

import br.com.fiap.challengeecommercelogin.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotNull(message = "Role is mandatory")
    private Role role;
}
