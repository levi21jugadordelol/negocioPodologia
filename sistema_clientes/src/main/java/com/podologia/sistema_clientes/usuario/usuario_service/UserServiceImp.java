package com.podologia.sistema_clientes.usuario.usuario_service;

import com.google.common.hash.Hashing;
import com.podologia.sistema_clientes.shared.util.secretKey;
import com.podologia.sistema_clientes.usuario.IUserRepo;
import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

   // private final secretKey key;
    private IUserRepo userRepo;

    @Override
    public UsuarioEntity getUser(Long id) {
        return null;
    }

    @Override
    public List<UsuarioEntity> getAllUser() {
        return List.of();
    }

    @Override
    public void removeUser(Long id) {

    }

    @Override
    public void addUser(UsuarioEntity nuevoUsuario) {
        String hashPassword = Hashing.sha256()
                .hashString(nuevoUsuario.getContrasenia() + secretKey.getSecret(), StandardCharsets.UTF_8)
                .toString();

        nuevoUsuario.setContrasenia(hashPassword);
        userRepo.save(nuevoUsuario);
    }

    @Override
    public void updateUser(Long id, UsuarioEntity updateUser) {

    }
}
