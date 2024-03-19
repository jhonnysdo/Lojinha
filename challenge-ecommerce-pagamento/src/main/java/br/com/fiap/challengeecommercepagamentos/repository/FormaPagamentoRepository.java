package br.com.fiap.challengeecommercepagamentos.repository;

import br.com.fiap.challengeecommercepagamentos.entity.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento,Long> {

}
