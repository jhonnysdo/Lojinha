package br.com.fiap.challengeecommercecarrinhodecompras.repository;

import br.com.fiap.challengeecommercecarrinhodecompras.entity.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    List<ItemCarrinho> findByUsername(String username);
}
