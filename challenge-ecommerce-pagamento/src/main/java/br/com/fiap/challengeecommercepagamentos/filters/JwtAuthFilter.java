package br.com.fiap.challengeecommercepagamentos.filters;

import br.com.fiap.challengeecommercepagamentos.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    @Autowired
    private JwtService jwtService;

    private final String AUTH_URL = "http://localhost:8080/api/v1/auth/validate";

    private final RestTemplate restTemplate = new RestTemplate();
    private final List<String> SWAGGER_ENDPOINTS = List.of("/pagamentos/v3/api-docs",
            "/pagamentos/swagger-ui/", "/pagamentos/swagger-ui/index.html");



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestURI = request.getRequestURI();

        if (isSwaggerEndpoint(requestURI)) {
            // Se a solicitação for para um endpoint do Swagger, permita-a sem autenticação JWT
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            return;
        }

        jwtToken = authHeader.substring(7);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> authResponse = restTemplate.exchange(AUTH_URL, HttpMethod.GET, entity, Void.class);
            if (authResponse.getStatusCode().is2xxSuccessful()) {
                String username = jwtService.extractUsername(jwtToken);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(token);

                filterChain.doFilter(request, response);
            }
        } catch (HttpClientErrorException e) {
            response.setStatus(401);
            return;
        }
    }

    private boolean isSwaggerEndpoint(String requestURI) {
        return SWAGGER_ENDPOINTS.stream().anyMatch(requestURI::startsWith);
    }
}
