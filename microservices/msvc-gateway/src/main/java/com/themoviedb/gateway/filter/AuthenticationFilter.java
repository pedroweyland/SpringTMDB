package com.themoviedb.gateway.filter;

import com.themoviedb.gateway.config.RouteValidator;
import com.themoviedb.gateway.exception.InvalidTokenException;
import com.themoviedb.gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final RouteValidator routeValidator;
    private final JwtUtil jwtUtil;
    private final WebClient webClient; // Usa WebClient en lugar de Feign

    @Value("${msvc.auth.url}")
    private String msvcAuthUrl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (routeValidator.isSecured.test(exchange.getRequest())) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return Mono.error(new InvalidTokenException("Falta Authorization header"));

            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Mono.error(new InvalidTokenException("Authorization mal formado"));
            }

            String token = authHeader.substring(7);
            try {
                jwtUtil.validateToken(token);
            } catch (Exception e) {
                return Mono.error(new InvalidTokenException("Token inválido"));
            }

            return webClient.get()
                    .uri(msvcAuthUrl + "/auth/validate")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(isValid -> {
                        if (!isValid) {
                            return Mono.error(new InvalidTokenException("Token inválido"));
                        }
                        return chain.filter(exchange);
                    });
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // prioridad alta
    }
}
