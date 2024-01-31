package br.com.fiap.challengeecommercelogin.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// https://medium.com/spring-boot/spring-boot-3-spring-security-6-jwt-authentication-authorization-98702d6313a5
public class CustomUserDetails extends User implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User byUsername) {
        this.username = byUsername.getUsername();
        this.password= byUsername.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();

//        for(UserRole role : byUsername.getRoles()){
//            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
//        }
        if (byUsername.getRole() != null) {
            auths.add(new SimpleGrantedAuthority(byUsername.getRole().name().toUpperCase()));
        } else {
            // Lida com o caso em que 'role' é nulo (por exemplo, atribua uma autoridade padrão)
            // authority = new SimpleGrantedAuthority("ROLE_DEFAULT");
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
