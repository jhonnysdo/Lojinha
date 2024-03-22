package br.com.fiap.challengeecommercecarrinhodecompras.repository;

import br.com.fiap.challengeecommercecarrinhodecompras.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Carrinho findByUsername(String username);

    Carrinho findByUsernameAndFechadoIsFalse(String username);
}
