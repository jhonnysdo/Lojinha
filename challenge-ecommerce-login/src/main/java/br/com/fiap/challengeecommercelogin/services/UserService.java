package br.com.fiap.challengeecommercelogin.services;

import br.com.fiap.challengeecommercelogin.entity.User;
import br.com.fiap.challengeecommercelogin.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.fiap.challengeecommercelogin.entity.CustomUserDetails;

@Service
@Slf4j
// https://medium.com/@truongbui95/jwt-authentication-and-authorization-with-spring-boot-3-and-spring-security-6-2f90f9337421
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Repositório JPA para acessar os usuários no banco de dados

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        log.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }
}
