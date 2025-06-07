package com.podologia.sistema_clientes.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@RequiredArgsConstructor
@Configuration//esta anotacion sirve para que se levante esta clase, ni bien al iniciar el proyecto
@EnableWebSecurity//esta anotacion hace que tenga la clase este objeto SecurityFilterChain
public class SecurityConfiguration {

    private final CustomAccessFilter customAccessFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
       /*Desactiva CSRF: http.csrf(AbstractHttpConfigurer::disable).
        La protección contra CSRF se desactiva porque
        normalmente es innecesaria para APIs REST si los tokens
        se usan correctamente.*/

        http.addFilterBefore(customAccessFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
