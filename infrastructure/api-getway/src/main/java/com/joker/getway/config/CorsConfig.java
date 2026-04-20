package com.joker.getway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration() {{
            addAllowedMethod("*");
            addAllowedOrigin("*");
            addAllowedHeader("*");
        }};

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource(new PathPatternParser()) {{
            registerCorsConfiguration("/**", corsConfiguration);
        }};
        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }
}
