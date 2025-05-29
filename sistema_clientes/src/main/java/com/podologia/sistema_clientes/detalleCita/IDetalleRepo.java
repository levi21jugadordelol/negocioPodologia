package com.podologia.sistema_clientes.detalleCita;

import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDetalleRepo extends JpaRepository<DetalleEntity,Long> {

    //buscas detalles a travez del id_cita
    @Query("SELECT c FROM DetalleEntity c WHERE c.servicio.id = :idServicio")
    List<DetalleEntity> findByServicioId(@Param("idServicio") Long idServicio);


    @Query("""
    SELECT d.servicio 
    FROM DetalleEntity d 
    WHERE d.cita.idCita = :idCita
    """)
    Optional<ServicioEntity> findUnicoServicioByCitaId(@Param("idCita") Long idCita);


}
