package com.podologia.sistema_clientes.cliente;

import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepo extends JpaRepository<ClienteEntity,Long> {

    @Query("SELECT c from ClienteEntity c WHERE c.nombreCliente = :nombreCliente")
    Optional<ClienteEntity> findByNombre(@Param("nombreCliente") String nombreCliente);

   @Query("Select c FROM ClienteEntity c WHERE c.dniCliente = :dniCliente")
    Optional<ClienteEntity> findByDni(@Param("dniCliente") String dniCliente);



}
