package com.kaora.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration cf = new CorsConfiguration();
        cf.setAllowCredentials(true);
        cf.addAllowedOrigin("*");
        cf.addAllowedHeader("*");
        cf.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", cf);
        return new CorsFilter(source);
    }
}
