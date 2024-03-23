package br.com.fiap.challengeecommercecarrinhodecompras.repository;

import br.com.fiap.challengeecommercecarrinhodecompras.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Carrinho findByUsername(String username);

    @Query("SELECT c FROM Carrinho c WHERE c.username = :username AND c.status = 'CRIADO'")
    Carrinho findByUsernameAndStatusIsCriado(String username);

    @Query("SELECT c FROM Carrinho c WHERE c.username = :username AND c.status = 'PENDENTE_PAGAMENTO'")
    Carrinho findByUsernameAndStatusIsPendentePagamento(String username);
}
