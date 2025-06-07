package com.podologia.sistema_clientes.auth;

import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;

public interface IAuthService {
    UsuarioEntity login(String email, String password);
}
