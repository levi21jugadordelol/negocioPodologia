package com.podologia.sistema_clientes.auth;

import com.google.common.hash.Hashing;
import com.podologia.sistema_clientes.shared.util.secretKey;
import com.podologia.sistema_clientes.usuario.IUserRepo;
import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
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
    @Override
    public UsuarioEntity login(String email, String password) {
        String hashPassword = Hashing.sha256()
                .hashString(password + secretKey.getSecret(), StandardCharsets.UTF_8)
                .toString();
        List<UsuarioEntity> result = userRepo.findByEmailAndPassword(email, password);
        if(result.isEmpty()){
            return null;
        }else{
            return result.get(0);
        }
    }


}
