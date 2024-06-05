package com.tickethub.backend.user.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(60 * 60 * 24)
                .allowedOrigins("http://tickethubs.net");
                //.allowedOrigins("http://localhost:3000");
    }
}
