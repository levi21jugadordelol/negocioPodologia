package com.podologia.sistema_clientes.auth;

import com.google.common.hash.Hashing;
import com.podologia.sistema_clientes.shared.util.secretKey;
import com.podologia.sistema_clientes.usuario.IUserRepo;
import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IAuthServiceImp implements IAuthService{
    //private final secretKey key;
    private final IUserRepo userRepo;
    private final secretKey secretKey;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.auth.IAuthServiceImp.class);
    @Override
    public UsuarioEntity login(String email, String password) {
        log.info("el email traido del controller es: {}",email);
        log.info("el password traido del controller es: {}",password);
        log.info("la palbrta secreta es: {} ",secretKey.getSecret());
        String hashPassword = Hashing.sha256()
                .hashString(password + secretKey.getSecret(), StandardCharsets.UTF_8)
                .toString();
        log.info("Hash generado con clave secreta: {}", hashPassword);
        List<UsuarioEntity> result = userRepo.findByEmailAndPassword(email, hashPassword);
        log.info("Cantidad de usuarios encontrados: {}", result.size());
        if(result.isEmpty()){
            log.warn("No se encontró un usuario con ese email y password.");
            return null;
        }else{
            //log.info("Usuario autenticado: {}", result.get(0).getNombre_usuario());
            log.info("usuario autenticado: {}",result.get(0).getNombreUsuario());
            return result.get(0);
        }
    }


}
