package br.com.fiap.challengeecommercelogin.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private User user; // Sua classe de entidade User

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Você pode configurar as autoridades conforme necessário
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Pode personalizar conforme necessário
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Pode personalizar conforme necessário
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Pode personalizar conforme necessário
    }

    @Override
    public boolean isEnabled() {
        return true; // Pode personalizar conforme necessário
    }
}
