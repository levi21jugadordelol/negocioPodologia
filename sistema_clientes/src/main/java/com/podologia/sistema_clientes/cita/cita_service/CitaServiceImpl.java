package com.podologia.sistema_clientes.cita.cita_service;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.detalleCita.IDetalleRepo;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.producto.IProductoRepo;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;
import com.podologia.sistema_clientes.servicio.IServicioRepo;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionCita;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CitaServiceImpl implements ICitaService {

    private final ICitaRepo citaRepo;
    private final ValidacionCita validacionCita;
    private final IServicioRepo servicioRepo;
    private final IDetalleRepo detalleRepo;
    private  final IProductoRepo productoRepo;

    private static final Logger log = LoggerFactory.getLogger( com.podologia.sistema_clientes.cita.cita_service.CitaServiceImpl.class);

    @Override
    public List<CitaEntity> getCita() {
        List<CitaEntity> listaCitas = citaRepo.findAll();
        log.info("el total de citas es : {}",listaCitas.size());
        return listaCitas;
    }

    @Transactional
    @Override
    public void saveCita(CitaEntity citaEntity) {

        validacionCita.existDuenio(citaEntity);
        citaRepo.save(citaEntity);
        log.info("cita guardada con éxito: {}", citaEntity);

    }

    @Transactional
    @Override
    public DetalleEntity saveDetalle(Long idCita, DetalleEntity detalle) {
        validacionCita.existIdCiteToSaveDetail(idCita,detalle);


        // Obtener las entidades para asignarlas (ya sabemos que existen por la validación)
        CitaEntity cita = citaRepo.findById(idCita).get(); // no fallará, ya fue validado
        ServicioEntity servicio = servicioRepo.findById(detalle.getServicio().getIdServicio()).get(); // tampoco fallará


        // Asignamos
        detalle.setCita(cita);
        detalle.setServicio(servicio);

        // Asociar los productos utilizados con el detalle
        if (detalle.getListProductUtilziado() != null) {
            for (ProductUtilizadoEntity p : detalle.getListProductUtilziado()) {
                Long productoId = p.getProductoEntity().getIdProducto();
                ProductoEntity producto = productoRepo.findById(productoId).get(); // ya validado

                p.setProductoEntity(producto); // importante
                p.setDetalleEntity(detalle);  // relación inversa
            }
        }

        // Guardar
        return detalleRepo.save(detalle);

    }

    @Transactional
    @Override
    public void deleteCita(Long id_cita) {

           validacionCita.findIdtoDelete(id_cita);

            citaRepo.deleteById(id_cita);
            log.info("el id fue eliminado correctamente: {}",id_cita);

    }

    @Override
    public Optional<CitaEntity> findCita(long id_cita) {
      CitaEntity cita = citaRepo.findById(id_cita)
              .orElseThrow(()->{
                  log.warn("cita no encontrado con ID: {}", id_cita);
                  return new EntidadNoEncontradaException("cita con ID " + id_cita + " no encontrado.");
              });
        log.info("cita encontrado exitosamente con ID: {}", id_cita);
        return Optional.of(cita);
    }



    @Transactional
    @Override
    public void editCita(Long id_cita, CitaEntity nuevosDatos) {
        log.info("Iniciando edición de cita con ID: {}", id_cita);

        CitaEntity citaActualziada = validacionCita.validateParametersToEdit(id_cita,nuevosDatos);

        citaRepo.save(citaActualziada);

        log.info("Cita actualizada con éxito. ID: {}", id_cita);


    }


    @Override
    public List<CitaEntity> buscarCitasPorClienteId(Long clienteId) {
        List<CitaEntity> citas = citaRepo.buscarCitasPorClienteId(clienteId);
        if (citas.isEmpty()) {
            log.warn("No se encontraron citas para el cliente con ID: {}", clienteId);
            throw new EntidadNoEncontradaException("No se encontraron citas para el cliente con ID: " + clienteId);
        }
        return citas;
    }

}
