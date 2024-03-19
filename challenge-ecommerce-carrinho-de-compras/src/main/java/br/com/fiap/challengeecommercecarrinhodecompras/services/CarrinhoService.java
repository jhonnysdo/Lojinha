package br.com.fiap.challengeecommercecarrinhodecompras.services;

import br.com.fiap.challengeecommercecarrinhodecompras.entity.Carrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.entity.ItemCarrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.repository.CarrinhoRepository;
import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import br.com.fiap.challengeecommercepagamentos.services.PagamentoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrinhoService {

    private PagamentoService pagamentoService;
    private TipoFormaPagamento tipoFormaPagamento;
    private static CarrinhoRepository carrinhoRepository;

    public List<ItemCarrinho> listarItensCarrinho(String username) {
        return carrinhoRepository.findByUsername(username);
    }

    public ItemCarrinho adicionarItemCarrinho(ItemCarrinho itemCarrinho) {
        return carrinhoRepository.save(itemCarrinho);
    }

    public ResponseEntity<String> fecharCarrinho(String username, TipoFormaPagamento tipoFormaPagamento) {
        List<ItemCarrinho> itens = listarItensCarrinho(username);
        double total = itens.stream().mapToDouble(item -> item.getPreco() * item.getQuantidade()).sum();
        return pagamentoService.iniciarPagamento(username, total, tipoFormaPagamento);
    }

    public ResponseEntity<List<ItemCarrinho>> buscarCarrinhoPorUsername(String username) {
        List<ItemCarrinho> carrinho = carrinhoRepository.findByUsername(username);
        return ResponseEntity.ok(carrinho);
    }

    public void removerItemCarrinho(Long id) {
        carrinhoRepository.deleteById(id);
    }

//    public double calcularTotal(Carrinho carrinho) {
//        return carrinho.getItens().stream()
//                .mapToDouble(item -> item.getPreco() * item.getQuantidade())
//                .sum();
//    }
}
