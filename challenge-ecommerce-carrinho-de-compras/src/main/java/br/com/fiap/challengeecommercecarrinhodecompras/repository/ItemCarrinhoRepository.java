package br.com.fiap.challengeecommercecarrinhodecompras.repository;

import br.com.fiap.challengeecommercecarrinhodecompras.entity.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
}
