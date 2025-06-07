package com.podologia.sistema_clientes.infrastructure.config;


import com.podologia.sistema_clientes.shared.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Component
public class CustomAccessFilter implements Filter {

    public CustomAccessFilter() {
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

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
                "/api/auth/register"
        };


        boolean authorized = Arrays.asList(availableUrl).contains(currentUrl);
        boolean isApiResource = currentUrl.startsWith("/api/");
        if (authorized || !isApiResource) {
            return true;
        }

        /* cuando se haga un request, se va enviar a las cabezeras,
        aca se puede ver en el backend en postman en cabezera
        cuando enviamos el request*/
        try {
            String token = request.getHeader("Authorization");
            String userId = JwtUtil.getUserIdByToken(token);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

}
