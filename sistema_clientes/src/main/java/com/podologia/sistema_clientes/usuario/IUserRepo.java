package com.podologia.sistema_clientes.usuario;

import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserRepo  extends JpaRepository<UsuarioEntity,Long> {

    @Query("SELECT c from UsuarioEntity c WHERE email = :email AND contrasenia = :contrasenia")
    List<UsuarioEntity>  findByEmailAndPassword(@Param("email") String email,
                                                @Param("contrasenia") String contrasenia);

}
