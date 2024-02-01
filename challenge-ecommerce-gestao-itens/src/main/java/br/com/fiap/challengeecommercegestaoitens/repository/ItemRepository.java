package br.com.fiap.challengeecommercegestaoitens.repository;

import br.com.fiap.challengeecommercegestaoitens.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
