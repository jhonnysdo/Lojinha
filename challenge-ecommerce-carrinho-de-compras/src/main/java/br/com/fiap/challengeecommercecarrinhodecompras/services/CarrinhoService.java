package br.com.fiap.challengeecommercecarrinhodecompras.services;

import br.com.fiap.challengeecommercecarrinhodecompras.Enum.Status;
import br.com.fiap.challengeecommercecarrinhodecompras.dto.CarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.dto.ItemCarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.dto.ProdutoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.entity.Carrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.entity.ItemCarrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.CarrinhoNotFoundException;
import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.ItemNotFoundException;
import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.ItemOutOfStockException;
import br.com.fiap.challengeecommercecarrinhodecompras.repository.CarrinhoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<CarrinhoDTO> listarCarrinhos() {
        return carrinhoRepository.findAll().stream()
                .map(carrinho -> modelMapper.map(carrinho, CarrinhoDTO.class))
                .collect(Collectors.toList());
    }

    public CarrinhoDTO listarCarrinhoUsuario(String authorizationHeader) {
        String username = jwtService.extractUsername(authorizationHeader.substring(7));

        Carrinho carrinho = carrinhoRepository.findByUsernameAndStatusIsCriado(username);
        if (carrinho == null) {
            throw new CarrinhoNotFoundException(username);
        }
        return modelMapper.map(carrinho, CarrinhoDTO.class);
    }

    public CarrinhoDTO listarCarrinhoUsuarioPendentePagamento(String authorizationHeader) {
        String username = jwtService.extractUsername(authorizationHeader.substring(7));

        Carrinho carrinho = carrinhoRepository.findByUsernameAndStatusIsPendentePagamento(username);
        if (carrinho == null) {
            throw new CarrinhoNotFoundException(username);
        }
        return modelMapper.map(carrinho, CarrinhoDTO.class);
    }

    @Transactional
    public CarrinhoDTO adicionarItemCarrinho(ItemCarrinhoDTO itemCarrinhoDTO, String authorizationHeader) {

        ItemCarrinho itemCarrinho = ItemCarrinho.builder()
                .produtoId(itemCarrinhoDTO.getProdutoId())
                .quantidade(itemCarrinhoDTO.getQuantidade())
                .build();

        ResponseEntity<ProdutoDTO> response = produtoService.fetchProduto(
                itemCarrinho.getProdutoId(), authorizationHeader);

        if (response.getStatusCode().is2xxSuccessful()) {

            itemCarrinho.setPrecoUnitario(Objects.requireNonNull(response.getBody()).getPreco());
            itemCarrinho.setProdutoNome(Objects.requireNonNull(response.getBody()).getNome());
            Carrinho carrinho = carrinhoRepository.findByUsernameAndStatusIsCriado(
                    jwtService.extractUsername(authorizationHeader.substring(7)));

            if (carrinho == null) {

                carrinho = Carrinho.builder()
                        .username(jwtService.extractUsername(authorizationHeader.substring(7)))
                        .dataCriacao(LocalDate.now())
                        .status(Status.CRIADO)
                        .itens(new ArrayList<>())
                        .build();
                carrinho.getItens().add(itemCarrinho);

                ResponseEntity<ProdutoDTO> adicinarItemCarrinho = produtoService.adicinarItemCarrinho(
                        itemCarrinho.getProdutoId(), itemCarrinho.getQuantidade(), authorizationHeader);

                if (!adicinarItemCarrinho.getStatusCode().is2xxSuccessful()) {
                    throw new ItemOutOfStockException(itemCarrinho.getProdutoId());
                }

            } else {

                Optional<ItemCarrinho> itemCarrinhoOptional = carrinho.getItens().stream()
                        .filter(item -> item.getProdutoId().equals(itemCarrinho.getProdutoId()))
                        .findFirst();

                if (itemCarrinhoOptional.isPresent()) {


                    if (itemCarrinhoOptional.get().getQuantidade() < itemCarrinhoDTO.getQuantidade()) {

                        ResponseEntity<ProdutoDTO> atualizaItemCarrinho = produtoService.adicinarItemCarrinho(
                                itemCarrinho.getProdutoId(),
                                itemCarrinhoDTO.getQuantidade() - itemCarrinhoOptional.get().getQuantidade()
                                , authorizationHeader);

                        if (!atualizaItemCarrinho.getStatusCode().is2xxSuccessful()) {
                            throw new ItemOutOfStockException(itemCarrinho.getProdutoId());
                        }

                    } else if (itemCarrinhoOptional.get().getQuantidade() > itemCarrinhoDTO.getQuantidade()) {
                        produtoService.removeItemCarrinho(
                                itemCarrinho.getProdutoId(),
                                itemCarrinhoOptional.get().getQuantidade() - itemCarrinhoDTO.getQuantidade()
                                , authorizationHeader);
                    }

                    ItemCarrinho item = itemCarrinhoOptional.get();
                    item.setQuantidade(itemCarrinho.getQuantidade());
                    item.setPrecoUnitario(itemCarrinho.getPrecoUnitario());
                    item.setProdutoNome(itemCarrinho.getProdutoNome());

                } else {

                    ResponseEntity<ProdutoDTO> atualizaItemCarrinho = produtoService.adicinarItemCarrinho(
                            itemCarrinho.getProdutoId(),
                            itemCarrinhoDTO.getQuantidade()
                            , authorizationHeader);

                    if (!atualizaItemCarrinho.getStatusCode().is2xxSuccessful()) {
                        throw new ItemOutOfStockException(itemCarrinho.getProdutoId());
                    }

                    carrinho.getItens().add(itemCarrinho);
                }
            }

            //atualiza valor total
            carrinho.setValorTotal(carrinho.getItens().stream()
                    .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                    .sum());

            return modelMapper.map(carrinhoRepository.save(carrinho), CarrinhoDTO.class);

        } else {

            throw new RuntimeException("Erro ao chamar o serviço de itens.");
        }
    }

    @Transactional
    public void removerProdutoCarrinho(Long id, String authorizationHeader) {

        String username = jwtService.extractUsername(authorizationHeader.substring(7));
        Carrinho carrinho = carrinhoRepository.findByUsernameAndStatusIsCriado(username);

        if (carrinho == null) {
            throw new CarrinhoNotFoundException(username);
        }

        Optional<ItemCarrinho> itemCarrinhoOptional = carrinho.getItens().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (itemCarrinhoOptional.isPresent()) {

            ResponseEntity<ProdutoDTO> response = produtoService.removeItemCarrinho(
                    itemCarrinhoOptional.get().getProdutoId(),
                    itemCarrinhoOptional.get().getQuantidade(),
                    authorizationHeader);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ItemNotFoundException(itemCarrinhoOptional.get().getProdutoId());
            }

            carrinho.getItens().remove(itemCarrinhoOptional.get());
            carrinho.setValorTotal(carrinho.getItens().stream()
                    .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                    .sum());
            carrinhoRepository.save(carrinho);

        } else {

            throw new ItemNotFoundException(id);

        }
    }

    @Transactional
    public void finalizarCompra(String authorizationHeader) {

        String username = jwtService.extractUsername(authorizationHeader.substring(7));
        Carrinho carrinho = carrinhoRepository.findByUsernameAndStatusIsCriado(username);

        if (carrinho == null) {
            throw new CarrinhoNotFoundException(username);
        }

        carrinho.setStatus(Status.PENDENTE_PAGAMENTO);
        carrinhoRepository.save(carrinho);
    }

    @Transactional
    public CarrinhoDTO atualizarStatusPago(String authorizationHeader) {

        String username = jwtService.extractUsername(authorizationHeader.substring(7));
        Carrinho carrinho = carrinhoRepository.findByUsernameAndStatusIsPendentePagamento(username);

        if (carrinho == null) {
            throw new CarrinhoNotFoundException(username);
        }

        carrinho.setStatus(Status.PAGO);
        return modelMapper.map(carrinhoRepository.save(carrinho), CarrinhoDTO.class);
    }


    @Transactional
    public CarrinhoDTO cancelarCompra(String authorizationHeader) {

        String username = jwtService.extractUsername(authorizationHeader.substring(7));
        Carrinho carrinho = carrinhoRepository.findByUsernameAndStatusIsPendentePagamento(username);

        if (carrinho == null) {
            throw new CarrinhoNotFoundException(username);
        }

        carrinho.getItens().forEach(itemCarrinho -> {
            ResponseEntity<ProdutoDTO> response = produtoService.removeItemCarrinho(
                    itemCarrinho.getProdutoId(),
                    itemCarrinho.getQuantidade(),
                    authorizationHeader);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ItemNotFoundException(itemCarrinho.getProdutoId());
            }
        });

        carrinho.setStatus(Status.CANCELADO);
        return modelMapper.map(carrinhoRepository.save(carrinho), CarrinhoDTO.class);
    }
}
