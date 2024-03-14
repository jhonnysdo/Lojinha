package br.com.fiap.challengeecommercegestaoitens.filters;

import br.com.fiap.challengeecommercegestaoitens.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class JwtAuthFilterTest {

    private final JwtService jwtService = mock(JwtService.class);
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final JwtAuthFilter filter = new JwtAuthFilter(jwtService, restTemplate);
    private final FilterChain filterChain = mock(FilterChain.class);

    @Test
    public void testDoFilterInternal_success() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer jwtToken");

        when(jwtService.extractUsername(anyString())).thenReturn("username");
        when(restTemplate.exchange(anyString(), any(), any(), eq(Void.class))).thenReturn(ResponseEntity.ok().build());

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertEquals(200, response.getStatus());
    }
}