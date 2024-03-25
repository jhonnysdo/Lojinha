package br.com.fiap.challengeecommercelogin.filters;

import br.com.fiap.challengeecommercelogin.services.UserService;
import br.com.fiap.challengeecommercelogin.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
// https://stackoverflow.com/questions/34595605/how-to-manage-exceptions-thrown-in-filters-in-spring
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    // fix: https://github.com/spring-projects/spring-security/issues/4368#issuecomment-709562668
    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/v1/auth/**");

    private final List<String> SWAGGER_ENDPOINTS = List.of("/v3/api-docs",
    "/swagger-ui/", "/swagger-ui/index.html");


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!this.requestMatcher.matches(request)) {
            try {

                final String requestURI = request.getRequestURI();

                if (isSwaggerEndpoint(requestURI)) {
                    // Se a solicitação for para um endpoint do Swagger, permita-a sem autenticação JWT
                    filterChain.doFilter(request, response);
                    return;
                }

                final String authHeader = request.getHeader("Authorization");
                String token = null;
                String username = null;

                if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
                    filterChain.doFilter(request, response);
                    return;
                }

                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);

                if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    if (Boolean.TRUE.equals(jwtService.isTokenValid(token, userDetails))) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }

                filterChain.doFilter(request, response);
            } catch (Exception e) {
                log.error("Spring Security Filter Chain Exception:", e);
                resolver.resolveException(request, response, null, e);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isSwaggerEndpoint(String requestURI) {
        return SWAGGER_ENDPOINTS.stream().anyMatch(requestURI::startsWith);
    }
}
