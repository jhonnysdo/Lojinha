package br.com.fiap.challengeecommercegestaoitens;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class ChallengeEcommerceGestaoItensApplicationTests {

	@Test
	public void testMainMethod() {
		// Verifica se o método main da aplicação é executado sem lançar exceções
		assertDoesNotThrow(() -> ChallengeEcommerceGestaoItensApplication.main(new String[]{}));
	}

	}

