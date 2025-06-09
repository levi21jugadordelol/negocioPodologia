package com.podologia.sistema_clientes.usuario.usuario_controllers;


import com.podologia.sistema_clientes.auth.IAuthService;
import com.podologia.sistema_clientes.shared.util.JwtUtil;
import com.podologia.sistema_clientes.usuario.dto.RequestLogin;
import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private final IAuthService authService;
    private final JwtUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.usuario.usuario_controllers.AuthController.class);

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody RequestLogin requestLogin){
        String email = requestLogin.getEmail();
        String password = requestLogin.getPassword();

        log.info("el valor del email es: {}",email);
        log.info("el valor del password es: {}",password);

        UsuarioEntity usuario = authService.login(email,password);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario);
        return ResponseEntity.ok(token);

    }

}
