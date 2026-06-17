package com.movie.api_gateway.Config;

import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfig {

    private static final Logger logger = LoggerFactory.getLogger(GlobalFilterConfig.class);

    @Bean
    @Order(1)
    public GlobalFilter requestLoggingFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            logger.info("Request: {} {} from {}",
                    request.getMethod(),
                    request.getURI(),
                    request.getRemoteAddress());

            long startTime = System.currentTimeMillis();

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                long duration = System.currentTimeMillis() - startTime;
                logger.info("Response: {} - {} ({} ms)",
                        request.getURI(),
                        response.getStatusCode(),
                        duration);
            }));
        };
    }

    @Bean
    @Order(2)
    public GlobalFilter addRequestHeadersFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest()
                    .mutate()
                    .header("X-Forwarded-For", exchange.getRequest().getRemoteAddress().getAddress().getHostAddress())
                    .header("X-Gateway-Version", "1.0")
                    .build();

            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}