package br.com.fiap.challengeecommercepagamentos.repository;

import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {

    @Query("SELECT p FROM Pagamento p WHERE p.username = ?1 AND p.status = 'CRIADO'")
    Pagamento findByUsernameAndStatusIsCriado(String username);
}
