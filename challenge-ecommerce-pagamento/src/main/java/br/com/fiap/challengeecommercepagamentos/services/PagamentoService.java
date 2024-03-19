package br.com.fiap.challengeecommercepagamentos.services;

import br.com.fiap.challengeecommercepagamentos.dto.request.formasPagamentosDTO.CartaoDTO;
import br.com.fiap.challengeecommercepagamentos.enums.TipoFormaPagamento;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PagamentoService {

    private FormaPagamentoService formaPagamentoService;



    public ResponseEntity<String> iniciarPagamento(String username, double total, TipoFormaPagamento tipoFormaPagamento) {


        switch (tipoFormaPagamento) {
            case CARTAO_CREDITO:
            case CARTAO_DEBITO:
                List<CartaoDTO> cartoes = formaPagamentoService.listarCartoes();
                if (cartoes.isEmpty()) {
                    return ResponseEntity.badRequest().body("Nenhum cartão cadastrado para o usuário. ");
                }
                return ResponseEntity.ok("Pagamento iniciado. ");
            case PIX:
            case BOLETO:
                return ResponseEntity.ok("Pagamento iniciado. ");

            default:
                return ResponseEntity.badRequest().body("Tipo de pagamento inválido. ");
        }

    }
}