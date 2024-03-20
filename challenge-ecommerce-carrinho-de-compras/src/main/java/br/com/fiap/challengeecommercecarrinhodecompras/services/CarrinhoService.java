package br.com.fiap.challengeecommercecarrinhodecompras.services;

import br.com.fiap.challengeecommercecarrinhodecompras.entity.ItemCarrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public List<ItemCarrinho> listarItensCarrinho(String username) {
        return carrinhoRepository.findByUsername(username);
    }

    public ItemCarrinho adicionarItemCarrinho(ItemCarrinho itemCarrinho) {
        return carrinhoRepository.save(itemCarrinho);
    }

    public void removerItemCarrinho(Long id) {
        carrinhoRepository.deleteById(id);
    }
}
