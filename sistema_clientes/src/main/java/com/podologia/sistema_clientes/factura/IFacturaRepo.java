package com.podologia.sistema_clientes.factura;

import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IFacturaRepo extends JpaRepository<FacturaEntity,Long> {

    //buscar factura por su codigo
    // Cambiar en IFacturaRepo
    @Query("SELECT c from FacturaEntity c WHERE c.numeroRecibo = :numeroRecibo")
    Optional<FacturaEntity> findFacturaByNumero(@Param("numeroRecibo") String numeroRecibo);


    //buscar factura a travez del id del cliente
    @Query("SELECT c FROM FacturaEntity c WHERE c.clienteEntity.idCliente = :clienteId")
    List<FacturaEntity> findFacturaByClientId(@Param("clienteId") Long clienteId);

    @Query("SELECT COUNT(f) FROM FacturaEntity f WHERE f.fechaEnmision >= :inicio AND f.fechaEnmision < :fin")
    Long contarFacturasPorMes(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

}
