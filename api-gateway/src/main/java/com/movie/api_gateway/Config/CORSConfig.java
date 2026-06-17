package com.movie.api_gateway.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CORSConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
       CorsConfiguration corsConfig = new CorsConfiguration();
       corsConfig.setAllowedOrigins(List.of(
               "http://localhost:300",
               "http://loalhost:4200",
               "http://localhost:8080"
       ));

       corsConfig.setAllowedMethods(List.of(
               "PUT", "GET", "PUT", "PATCH", "DELETE", "POST"
       ));

       corsConfig.setMaxAge(3600L);
       corsConfig.setAllowedHeaders(List.of(
               "Authorization",
               "Content-Type",
               "X-Requested-With",
               "Accept",
               "Origin",
               "Access-Control-Request-Method",
               "Access-Control-Request-Headers"
       ));

        corsConfig.setExposedHeaders(Arrays.asList(
                "Authorization",
                "X-User-Id",
                "X-User-Role",
                "X-User-Email"
        ));

       corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}