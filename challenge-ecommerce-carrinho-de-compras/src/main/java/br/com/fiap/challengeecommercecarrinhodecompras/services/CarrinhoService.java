package br.com.fiap.challengeecommercecarrinhodecompras.services;

import br.com.fiap.challengeecommercecarrinhodecompras.dto.ItemCarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.dto.ProdutoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.entity.ItemCarrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.repository.CarrinhoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;

    private final JwtService jwtService;

    private final ProdutoService produtoService;

    private final ModelMapper modelMapper = new ModelMapper();


    public CarrinhoService(CarrinhoRepository carrinhoRepository, JwtService jwtService, ProdutoService produtoService) {
        this.carrinhoRepository = carrinhoRepository;
        this.jwtService = jwtService;
        this.produtoService = produtoService;
    }
    public List<ItemCarrinho> listarItensCarrinho(String username) {
        return carrinhoRepository.findByUsername(username);
    }

    public ItemCarrinhoDTO adicionarItemCarrinho(ItemCarrinhoDTO itemCarrinhoDTO, String authorizationHeader) {
        var jwtToken = authorizationHeader.substring(7);

        ItemCarrinho itemCarrinho = ItemCarrinho.builder()
                .produtoId(itemCarrinhoDTO.getProdutoId())
                .quantidade(itemCarrinhoDTO.getQuantidade())
                .username(jwtService.extractUsername(jwtToken))
                .build();

        ResponseEntity<ProdutoDTO> response = produtoService.fetchProduto(
                itemCarrinho.getProdutoId(), authorizationHeader);

        if (response.getStatusCode().is2xxSuccessful()) {
            itemCarrinho.setPreco(Objects.requireNonNull(response.getBody()).getPreco());
            return modelMapper.map(carrinhoRepository.save(itemCarrinho), ItemCarrinhoDTO.class);
        } else {
            throw new RuntimeException("Erro ao chamar o serviço de itens.");
        }
    }

    public void removerItemCarrinho(Long id) {
        carrinhoRepository.deleteById(id);
    }
}
