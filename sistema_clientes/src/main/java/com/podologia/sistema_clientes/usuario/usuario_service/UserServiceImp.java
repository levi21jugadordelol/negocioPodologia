package com.podologia.sistema_clientes.usuario.usuario_service;

import com.google.common.hash.Hashing;
import com.podologia.sistema_clientes.shared.mappers.UsuarioMapper;
import com.podologia.sistema_clientes.shared.util.secretKey;
import com.podologia.sistema_clientes.usuario.IUserRepo;
import com.podologia.sistema_clientes.usuario.dto.UsuarioDto;
import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.usuario.usuario_service.UserServiceImp.class);

   // private final secretKey key;
    private final IUserRepo userRepo;
    private final UsuarioMapper usuarioMapper;
    private final secretKey key;

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

    @Transactional
    @Override
    public void addUser(UsuarioDto usuarioDto) {

        if(!usuarioDto.getContrasenia().equals(usuarioDto.getContraseniaConfirmada())){
            log.info("las contraseñas no coinciden");
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        String hashPassword = Hashing.sha256()
                .hashString(usuarioDto.getContrasenia() + key.getSecret(), StandardCharsets.UTF_8)
                .toString();

      /*  nuevoUsuario.setContrasenia(hashPassword);
        userRepo.save(nuevoUsuario); */
        UsuarioEntity usuario = usuarioMapper.toUsuarioEntity(usuarioDto);
        usuario.setContrasenia(hashPassword);

        userRepo.save(usuario);
    }

    @Override
    public void updateUser(Long id, UsuarioEntity updateUser) {

    }
}
