package com.podologia.sistema_clientes.detalleCita.detalle_service;

import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;

import java.util.List;
import java.util.Optional;

public interface IDetalleService {
    List<DetalleEntity> listaDetalle() ;
    void saveDetalle(DetalleEntity detalleEntity,Long idCita);
    void deleteDetalle(Long id_detalle);
    Optional<DetalleEntity> findDetalle(Long id_detalle);
    void editDetalle(Long id_detalle,DetalleEntity detalleEntity);

    List<DetalleEntity> detallesBaseServicioId(Long id_servicio);

}
