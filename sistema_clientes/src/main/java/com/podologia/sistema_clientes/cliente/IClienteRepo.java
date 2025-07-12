package com.podologia.sistema_clientes.cliente;

import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IClienteRepo extends JpaRepository<ClienteEntity,Long> {

    @Query("SELECT c from ClienteEntity c WHERE c.nombreCliente = :nombreCliente")
    Optional<ClienteEntity> findByNombre(@Param("nombreCliente") String nombreCliente);

   @Query("Select c FROM ClienteEntity c WHERE c.dniCliente = :dniCliente")
    Optional<ClienteEntity> findByDni(@Param("dniCliente") String dniCliente);

   /* @Query("SELECT c FROM ClienteEntity c WHERE c.createdAt BETWEEN :inicio AND :fin")
    List<ClienteEntity> getClientXday(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin); */

    @Query("SELECT c FROM ClienteEntity c WHERE c.updatedAt BETWEEN :inicio AND :fin")
    List<ClienteEntity> getClientXday(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);





}
