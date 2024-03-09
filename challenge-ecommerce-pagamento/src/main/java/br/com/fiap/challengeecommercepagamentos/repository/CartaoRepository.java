package br.com.fiap.challengeecommercepagamentos.repository;

import br.com.fiap.challengeecommercepagamentos.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao,String> {
}
