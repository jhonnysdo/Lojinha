package br.com.fiap.challengeecommercepagamentos.services;

import br.com.fiap.challengeecommercepagamentos.dto.CarrinhoDTO;
import br.com.fiap.challengeecommercepagamentos.dto.PagamentoDTO;
import br.com.fiap.challengeecommercepagamentos.entity.Pagamento;
import br.com.fiap.challengeecommercepagamentos.enums.FormaPagamento;
import br.com.fiap.challengeecommercepagamentos.enums.Status;
import br.com.fiap.challengeecommercepagamentos.exceptions.CarrinhoNotFoundException;
import br.com.fiap.challengeecommercepagamentos.exceptions.PagamentoNotFoundException;
import br.com.fiap.challengeecommercepagamentos.repository.PagamentoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public List<PagamentoDTO> listarTodos(String authorizationHeader) {
        return pagamentoRepository.findAll().stream()
                .map(pagamento -> modelMapper.map(pagamento, PagamentoDTO.class))
                .toList();
    }
    @Transactional
    public void realizarPagamento(FormaPagamento formaPagamento, String authorizationHeader) {

        Pagamento pagamento = pagamentoRepository.findByUsernameAndStatusIsCriado(
                jwtService.extractUsername(authorizationHeader.substring(7)));

        ResponseEntity<CarrinhoDTO> carrinhoDTO = carrinhoService.fetchCarrinho(authorizationHeader);

        if (!carrinhoDTO.getStatusCode().is2xxSuccessful()) {
            throw new CarrinhoNotFoundException(
                    jwtService.extractUsername(authorizationHeader.substring(7)));
        }

        if (pagamento == null) {
            pagamento = Pagamento.builder()
                    .carrinhoId(carrinhoDTO.getBody().getId())
                    .status(Status.CRIADO)
                    .carrinhoValorTotal(Objects.requireNonNull(carrinhoDTO.getBody()).getValorTotal())
                    .build();
        } else {
            pagamento.setCarrinhoValorTotal(Objects.requireNonNull(carrinhoDTO.getBody()).getValorTotal());
        }

        pagamento.setStatus(Status.PAGO);
        pagamento.setFormaPagamento(formaPagamento);
        pagamento.setDataPagamento(LocalDateTime.now());

        ResponseEntity<CarrinhoDTO> atualizarStautsPago = carrinhoService.atualizarStautsPago(authorizationHeader);

        if (atualizarStautsPago.getStatusCode().is2xxSuccessful()) {
            pagamentoRepository.save(pagamento);
        } else {
            throw new CarrinhoNotFoundException(
                    jwtService.extractUsername(authorizationHeader.substring(7)));
        }
    }

    @Transactional
    public void cancelarPagamento(String authorizationHeader) {

        ResponseEntity<CarrinhoDTO> response = carrinhoService.atualizarStautsCancelado(authorizationHeader);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CarrinhoNotFoundException(
                    jwtService.extractUsername(authorizationHeader.substring(7)));
        }

        Pagamento pagamento = Pagamento.builder()
                .carrinhoId(response.getBody().getId())
                .status(Status.CANCELADO)
                .carrinhoValorTotal(response.getBody().getValorTotal())
                .build();

        pagamentoRepository.save(pagamento);
    }
}