package com.podologia.sistema_clientes.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration//esta anotacion sirve para que se levante esta clase, ni bien al iniciar el proyecto
@EnableWebSecurity//esta anotacion hace que tenga la clase este objeto SecurityFilterChain
public class SecurityConfiguration {

    private final CustomAccessFilter customAccessFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(customAccessFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Si necesitas reglas de autorización
                .cors(cors -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://127.0.0.1:5500")); // Cambia esto por el origen de tu frontend
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", config);

                    cors.configurationSource(source);
                });

        return http.build();
       /*Desactiva CSRF: http.csrf(AbstractHttpConfigurer::disable).
        La protección contra CSRF se desactiva porque
        normalmente es innecesaria para APIs REST si los tokens
        se usan correctamente.*/


     /*   http
                .cors() // ✅ Habilita CORS (debes tener un CorsConfigurationSource)
                .and()
                .csrf(AbstractHttpConfigurer::disable) // ✅ Desactiva CSRF
                .addFilterBefore(customAccessFilter, UsernamePasswordAuthenticationFilter.class); // ✅ Añade tu filtro personalizado

        return http.build(); */

      /*  http.addFilterBefore(customAccessFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build(); */


    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500")); // o "*", según tus necesidades
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
