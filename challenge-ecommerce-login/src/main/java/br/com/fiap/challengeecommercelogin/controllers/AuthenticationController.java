package br.com.fiap.challengeecommercelogin.controllers;

import br.com.fiap.challengeecommercelogin.dao.request.SignUpRequest;
import br.com.fiap.challengeecommercelogin.dao.request.SigninRequest;
import br.com.fiap.challengeecommercelogin.dao.response.JwtAuthenticationResponse;
import br.com.fiap.challengeecommercelogin.services.JwtService;
import br.com.fiap.challengeecommercelogin.services.UserService;
import br.com.fiap.challengeecommercelogin.services.interfaces.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints para autenticação de usuários.")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;

    @Operation(summary = "Cadastrar um novo usuário", description = "Cadastra um novo usuário no sistema.")
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @Operation(summary = "Realizar login", description = "Realiza o login de um usuário no sistema.")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @Operation(summary = "Validar token", description = "Valida um token de autenticação.")
    @GetMapping("/validate")
    public ResponseEntity<String> validate(
            @RequestHeader("Authorization") String tokenHeader
    ) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token format.");
        }
        String token = tokenHeader.substring(7); // Remover "Bearer " do cabeçalho

        String username = jwtService.extractUsername(token);
        List<String> roles = jwtService.extractRoles(token);

        try {
            if (StringUtils.isNotEmpty(username)) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (Boolean.TRUE.equals(jwtService.isTokenValid(token, userDetails))) {
                    return ResponseEntity.ok("Token is valid.");
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
    }
}
