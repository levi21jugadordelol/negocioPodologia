package com.podologia.sistema_clientes.usuario.usuario_service;

import com.podologia.sistema_clientes.usuario.dto.UsuarioDto;
import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;

import java.util.List;

public interface UserService {
    UsuarioEntity getUser(Long id);
    List<UsuarioEntity> getAllUser();
    void removeUser(Long id);
    void addUser(UsuarioDto nuevoUsuario);
    void updateUser(Long id, UsuarioEntity updateUser);

}
