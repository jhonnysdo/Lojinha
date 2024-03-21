package br.com.fiap.challengeecommercelogin.services;

import br.com.fiap.challengeecommercelogin.dao.request.SignUpRequest;
import br.com.fiap.challengeecommercelogin.dao.request.SigninRequest;
import br.com.fiap.challengeecommercelogin.dao.response.JwtAuthenticationResponse;
import br.com.fiap.challengeecommercelogin.entity.User;
import br.com.fiap.challengeecommercelogin.enums.Role;
import br.com.fiap.challengeecommercelogin.exceptions.UserAlreadyExistsException;
import br.com.fiap.challengeecommercelogin.repository.UserRepository;
import br.com.fiap.challengeecommercelogin.services.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {

        User existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser != null) {
            throw new UserAlreadyExistsException(String.format("Usuário com username %s já existe.", request.getUsername()));
        }

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User byUsername = userRepository.findByUsername(request.getUsername());
        if (byUsername == null) {
            log.error("Username not found: " + request.getUsername());
            throw new UsernameNotFoundException("Usuário não encontrado: " + request.getUsername());
        }

        var jwt = jwtService.generateToken(byUsername);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
