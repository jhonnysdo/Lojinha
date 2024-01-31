package br.com.fiap.challengeecommercelogin.services.interfaces;

import br.com.fiap.challengeecommercelogin.dao.request.SignUpRequest;
import br.com.fiap.challengeecommercelogin.dao.request.SigninRequest;
import br.com.fiap.challengeecommercelogin.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
