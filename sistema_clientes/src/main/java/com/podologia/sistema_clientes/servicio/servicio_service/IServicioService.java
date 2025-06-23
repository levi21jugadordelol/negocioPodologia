package com.podologia.sistema_clientes.servicio.servicio_service;

import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;

import java.util.List;
import java.util.Optional;

public interface IServicioService {

    List<ServicioEntity> getServicios();
    ServicioEntity saveServicio(ServicioEntity servicio);
    void deleteServicio(Long id_servicio);
    Optional<ServicioEntity> findServicio(Long id_servicio);
    ServicioEntity editServicio(Long id_servicio,ServicioEntity servicio);

    Optional<ServicioEntity> buscarNombre(String nombre_service);

    Optional<ServicioEntity> obtenerServicioPorIdCita(Long idCita);


}
