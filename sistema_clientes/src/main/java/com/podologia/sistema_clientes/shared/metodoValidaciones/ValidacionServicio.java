package com.podologia.sistema_clientes.shared.metodoValidaciones;

import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.servicio.IServicioRepo;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import com.podologia.sistema_clientes.shared.exception.ValidacionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ValidacionServicio {

    private final IServicioRepo servicioRepo;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionServicio.class);

    public void validateServicioToSave(ServicioEntity servicio){
        if (servicio == null) {
            throw new ValidacionException("El objeto Servicio no puede ser null.");
        }

        if (servicio.getNombreServicio() == null || servicio.getNombreServicio().isBlank()) {
            throw new ValidacionException("El nombre del servicio es obligatorio.");
        }

        if (servicio.getPrecioServicio() <= 0) {
            throw new ValidacionException("El precio del servicio debe ser mayor a cero.");
        }

        if (servicio.getDuracionServicio() <= 0) {
            throw new ValidacionException("La duración del servicio debe ser mayor a cero.");
        }

        if (servicio.getDescripcionServicio() != null && servicio.getDescripcionServicio().length() > 500) {
            throw new ValidacionException("La descripción no puede superar los 500 caracteres.");
        }
    }

    public List<ServicioEntity> validateAndReturnServicios(){
        List<ServicioEntity> listaServicio = servicioRepo.findAll();
        if(listaServicio.isEmpty()){
            log.error("lista vacia ");
            throw new EntidadNoEncontradaException("No existen servicios disponibles.");
        }
        return  listaServicio;
    }

    public ServicioEntity validateParametersToEditService(Long idServicio, ServicioEntity nuevoServicio) {
        log.info("Validando existencia y preparando actualización para servicio con ID: {}", idServicio);

        ServicioEntity existente = servicioRepo.findById(idServicio)
                .orElseThrow(() -> {
                    log.error("No se encontró servicio con ID: {}", idServicio);
                    return new EntidadNoEncontradaException("Servicio con ID " + idServicio + " no existe.");
                });

        if (nuevoServicio == null) {
            log.error("La entidad Servicio recibida es null");
            throw new ValidacionException("Servicio no puede ser null");
        }

        log.info("=== Datos recibidos para actualizar servicio ===");
        log.info("Nombre: {}", nuevoServicio.getNombreServicio() != null ? nuevoServicio.getNombreServicio() : "no se proporcionó");
        log.info("Precio: {}", nuevoServicio.getPrecioServicio());
        log.info("Descripción: {}", nuevoServicio.getDescripcionServicio() != null ? nuevoServicio.getDescripcionServicio() : "no se proporcionó");
        log.info("Duración: {}", nuevoServicio.getDuracionServicio());

        // Actualización campo por campo
        if (nuevoServicio.getNombreServicio() != null) {
            existente.setNombreServicio(nuevoServicio.getNombreServicio());
        }
        if (nuevoServicio.getPrecioServicio() > 0) {
            existente.setPrecioServicio(nuevoServicio.getPrecioServicio());
        }
        if (nuevoServicio.getDescripcionServicio() != null) {
            existente.setDescripcionServicio(nuevoServicio.getDescripcionServicio());
        }
        if (nuevoServicio.getDuracionServicio() > 0) {
            existente.setDuracionServicio(nuevoServicio.getDuracionServicio());
        }

        log.info("Servicio validado y listo para ser persistido");

        return existente;
    }




}




