package com.podologia.sistema_clientes.servicio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;

import java.util.Optional;

@Repository
public interface IServicioRepo extends JpaRepository<ServicioEntity,Long> {

    @Query("SELECT c from ServicioEntity c where c.nombreServicio = :nombreServicio")
    Optional<ServicioEntity> findServicioByNombre(@Param("nombreServicio") String nombreServicio);


    @Query("SELECT COUNT(d) FROM DetalleEntity d WHERE d.servicio.id = :idServicio")
    long countDetallesPorServicio(@Param("idServicio") Long idServicio);

}
