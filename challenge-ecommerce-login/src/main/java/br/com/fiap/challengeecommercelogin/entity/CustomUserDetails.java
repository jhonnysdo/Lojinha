package br.com.fiap.challengeecommercelogin.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import br.com.fiap.challengeecommercelogin.enums.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// https://medium.com/spring-boot/spring-boot-3-spring-security-6-jwt-authentication-authorization-98702d6313a5
public class CustomUserDetails extends User implements UserDetails {
    private String username;
    private String password;

    @Getter
    private Role role;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User byUsername) {
        this.username = byUsername.getUsername();
        this.password= byUsername.getPassword();
        this.role = byUsername.getRole();
        List<GrantedAuthority> auths = new ArrayList<>();

        if (byUsername.getRole() != null) {
            auths.add(new SimpleGrantedAuthority(byUsername.getRole().name().toUpperCase()));
        } else {
            auths.add(new SimpleGrantedAuthority("USER"));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
