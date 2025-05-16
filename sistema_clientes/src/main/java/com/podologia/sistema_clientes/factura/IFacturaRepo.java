package com.podologia.sistema_clientes.factura;

import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFacturaRepo extends JpaRepository<FacturaEntity,Long> {

    //buscar factura por su codigo
    @Query("SELECT c from FacturaEntity c WHERE c.numeroFactura = :numeroFactura")
    Optional<FacturaEntity> findFacturaByNumero(@Param("numeroFactura") String numeroFactura);

    //buscar factura a travez del id del cliente
    @Query("SELECT c FROM FacturaEntity c WHERE c.clienteEntity.idCliente = :clienteId")
    List<FacturaEntity> findFacturaByClientId(@Param("clienteId") Long clienteId);

}
