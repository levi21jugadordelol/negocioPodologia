package com.podologia.sistema_clientes.servicio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;

@Repository
public interface IServicioRepo extends JpaRepository<ServicioEntity,Long> {

}
