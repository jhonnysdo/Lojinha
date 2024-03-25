package br.com.fiap.challengeecommercecarrinhodecompras.filters;

import br.com.fiap.challengeecommercecarrinhodecompras.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
// https://www.baeldung.com/spring-rest-template-error-handling
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private final String AUTH_URL = "http://localhost:8080/api/v1/auth/validate";
    private static final HttpStatus UNAUTHORIZED_STATUS = HttpStatus.UNAUTHORIZED;
    private final RestTemplate restTemplate = new RestTemplate();
    private final List<String> SWAGGER_ENDPOINTS = List.of("/carrinho-de-compras/v3/api-docs",
            "/carrinho-de-compras/swagger-ui/", "/carrinho-de-compras/swagger-ui/index.html");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
        ) throws ServletException, IOException {

        final String requestURI = request.getRequestURI();

        if (isSwaggerEndpoint(requestURI)) {
            // Se a solicitação for para um endpoint do Swagger, permita-a sem autenticação JWT
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> authHeader = getAuthHeader(request);

        if (authHeader.isEmpty()) {
            response.setStatus(UNAUTHORIZED_STATUS.value());
            return;
        }

        String jwtToken = authHeader.get();
        if (!isTokenValid(jwtToken)) {
            response.setStatus(UNAUTHORIZED_STATUS.value());
            response.getWriter().write("Authorization token invalido ou expirado.");
            return;
        }

        UsernamePasswordAuthenticationToken token = createAuthenticationToken(jwtToken);
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }

    private Optional<String> getAuthHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_PREFIX)) {
            return Optional.empty();
        }
        return Optional.of(authHeader.substring(AUTH_HEADER_PREFIX.length()));
    }

    private boolean isTokenValid(String jwtToken) {
        HttpHeaders headers = createAuthHeaders(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Void> authResponse = restTemplate.exchange(AUTH_URL, HttpMethod.GET, entity, Void.class);

            // Verifique o código de status da resposta
            HttpStatusCode statusCode = authResponse.getStatusCode();

            if (statusCode.is2xxSuccessful()) {
                return true; // Token válido
            } else if (statusCode == HttpStatus.FORBIDDEN) {
                // O token está expirado, você pode tratar aqui
                return false;
            } else {
                // Tratar outros códigos de status, se necessário
                return false;
            }
        } catch (Exception e) {
            // A exceção HttpClientErrorException$Forbidden é lançada quando o restTemplate.exchange recebe a resposta HTTP com código 403.
            // https://www.baeldung.com/spring-rest-template-error-handling
            log.error(e.getMessage());
            return false;
        }
    }

    private HttpHeaders createAuthHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("scope", "api");


        return headers;
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(String jwtToken) {
        String username = jwtService.extractUsername(jwtToken);
        return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
    }

    private boolean isSwaggerEndpoint(String requestURI) {
        return SWAGGER_ENDPOINTS.stream().anyMatch(requestURI::startsWith);
    }
}
