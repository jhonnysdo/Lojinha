package br.com.fiap.challengeecommercelogin.repository;

import br.com.fiap.challengeecommercelogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
