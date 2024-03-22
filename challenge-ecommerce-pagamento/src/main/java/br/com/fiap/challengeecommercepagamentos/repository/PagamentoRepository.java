package br.com.fiap.challengeecommercepagamentos.repository;

import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {

    Pagamento findByUsernameAndStatusIsCriado(String username);
}
