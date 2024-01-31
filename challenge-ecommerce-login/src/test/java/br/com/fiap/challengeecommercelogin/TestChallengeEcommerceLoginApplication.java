package br.com.fiap.challengeecommercelogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestChallengeEcommerceLoginApplication {

	public static void main(String[] args) {
		SpringApplication.from(ChallengeEcommerceLoginApplication::main).with(TestChallengeEcommerceLoginApplication.class).run(args);
	}

}
