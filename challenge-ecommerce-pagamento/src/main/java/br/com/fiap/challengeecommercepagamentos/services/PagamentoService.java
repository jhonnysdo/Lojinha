package br.com.fiap.challengeecommercepagamentos.services;

import br.com.fiap.challengeecommercepagamentos.dto.CarrinhoDTO;
import br.com.fiap.challengeecommercepagamentos.dto.PagamentoDTO;
import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import br.com.fiap.challengeecommercepagamentos.enums.FormaPagamento;
import br.com.fiap.challengeecommercepagamentos.enums.Status;
import br.com.fiap.challengeecommercepagamentos.exceptions.CarrinhoNotFoundException;
import br.com.fiap.challengeecommercepagamentos.exceptions.PagamentoNotFoundException;
import br.com.fiap.challengeecommercepagamentos.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;

    private final JwtService jwtService;

    private final CarrinhoService carrinhoService;

    private final ModelMapper modelMapper = new ModelMapper();

    public PagamentoService(PagamentoRepository pagamentoRepository, JwtService jwtService, CarrinhoService carrinhoService) {
        this.pagamentoRepository = pagamentoRepository;
        this.jwtService = jwtService;
        this.carrinhoService = carrinhoService;
    }

    public PagamentoDTO criarPagamento(String authorizationHeader) {

        try {

            ResponseEntity<CarrinhoDTO> response = carrinhoService.fetchCarrinho(
                    authorizationHeader);

            if (response.getStatusCode().is2xxSuccessful()) {

                Pagamento pagamento = pagamentoRepository.findByUsernameAndStatusIsCriado(
                        jwtService.extractUsername(authorizationHeader.substring(7)));

                if (pagamento == null) {

                    pagamento = Pagamento.builder()
                            .carrinhoId(Objects.requireNonNull(response.getBody()).getId())
                            .status(Status.CRIADO)
                            .carrinhoValorTotal(Objects.requireNonNull(response.getBody()).getValorTotal())
                            .build();
                } else {

                    pagamento.setCarrinhoId(Objects.requireNonNull(response.getBody()).getId());
                    pagamento.setCarrinhoValorTotal(Objects.requireNonNull(response.getBody()).getValorTotal());

                }

                return modelMapper.map(pagamentoRepository.save(pagamento), PagamentoDTO.class);

            } else {
                throw new CarrinhoNotFoundException(
                        jwtService.extractUsername(authorizationHeader.substring(7)));
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void realizarPagamento(FormaPagamento formaPagamento, String authorizationHeader) {

        Pagamento pagamento = pagamentoRepository.findByUsernameAndStatusIsCriado(
                jwtService.extractUsername(authorizationHeader.substring(7)));

        if (pagamento == null) {
            throw new PagamentoNotFoundException(
                    jwtService.extractUsername(authorizationHeader.substring(7)));
        }

        pagamento.setStatus(Status.PAGO);
        pagamento.setFormaPagamento(formaPagamento);
        pagamento.setDataPagamento(LocalDateTime.now());

        return modelMapper.map(pagamentoRepository.save(pagamento), PagamentoDTO.class);
    }
}