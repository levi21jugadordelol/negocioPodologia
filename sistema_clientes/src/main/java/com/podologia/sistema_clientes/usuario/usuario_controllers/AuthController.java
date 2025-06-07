package com.podologia.sistema_clientes.usuario.usuario_controllers;


import com.podologia.sistema_clientes.auth.IAuthService;
import com.podologia.sistema_clientes.shared.util.JwtUtil;
import com.podologia.sistema_clientes.usuario.dto.RequestLogin;
import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/auth/login")
    public String login(@RequestBody RequestLogin requestLogin){
        String email = requestLogin.getEmail();
        String password = requestLogin.getPassword();

        UsuarioEntity usuario = authService.login(email,password);

        String token = JwtUtil.generateToken(usuario);
        return token;

    }

}
