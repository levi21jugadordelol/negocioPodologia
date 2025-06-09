package com.podologia.sistema_clientes.infrastructure.config;


import com.podologia.sistema_clientes.shared.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

//@RequiredArgsConstructor
//@Component
//@RequiredArgsConstructor
/*public class CustomAccessFilter implements Filter {

   private final JwtUtil jwtUtil;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // ✅ Permitir preflight (CORS)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }


        //es para ver si la requiest , sea true
        boolean authorized = isAuthorized(request);
        if (authorized) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(null, null, Collections.emptyList()));
            filterChain.doFilter(request, response);
        } else {
            //si es false me envia un 403
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private boolean isAuthorized(HttpServletRequest request) {
        String currentUrl = request.getRequestURI();
        String[] availableUrl = new String[] {
                "/api/auth/login",
              //  "/api/auth/register"
                "/api/user"
        };


        boolean authorized = Arrays.asList(availableUrl).contains(currentUrl);
        boolean isApiResource = currentUrl.startsWith("/api/");
        if (authorized || !isApiResource) {
            return true;
        }

        /* cuando se haga un request, se va enviar a las cabezeras,
        aca se puede ver en el backend en postman en cabezera
        cuando enviamos el request*/
 /*       try {
            String token = request.getHeader("Authorization");
            String userId = jwtUtil.getUserIdByToken(token);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

} */

// Permite que Spring detecte y registre automáticamente este filtro como un componente del contexto
@Component
public class CustomAccessFilter implements Filter {

    // 🔐 Utilidad para manejar JWT (validación, extracción de datos)
    private final JwtUtil jwtUtil;

    public CustomAccessFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 🛡️ Lista de rutas públicas que no requieren autenticación
    private static final Set<String> PUBLIC_ENDPOINTS = Set.of(
            "/api/auth/login",
            "/api/auth/register"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        // 📥 Cast para trabajar con objetos HTTP reales
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 🧭 Obtenemos la ruta y el método HTTP de la solicitud
        String path = request.getRequestURI();
        String method = request.getMethod();

        // ✅ Permitir solicitudes preflight (CORS OPTIONS), muy importantes para clientes JS
        if ("OPTIONS".equalsIgnoreCase(method)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // ✅ Si la ruta es pública (login o register), no se necesita token
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // ✅ Validar token JWT
        // 🔒 En rutas protegidas: se requiere el header Authorization con formato correcto
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // 🔑 Extrae el token, quitando el prefijo "Bearer "
        String token = header.substring(7); // Quita "Bearer "

        try {

            // ✅ Extrae el userId desde el token y también lo valida internamente (firma, expiración)
            String userId = jwtUtil.getUserIdByToken(token); // También valida firma y expiración
            if (userId == null) {
                throw new RuntimeException("Token inválido");
            }

            // Podrías agregar más validaciones aquí (roles, claims, etc.)


            // Autenticar al usuario en el contexto de Spring
            // ✅ Crea un contexto de autenticación para el usuario actual
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList())
            );

            // ▶️ Permite continuar la solicitud
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    // 🔍 Verifica si la ruta actual es pública
    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.contains(path);
    }
}
