package com.podologia.sistema_clientes.shared.metodoValidaciones;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cita.cita_service.CitaServiceImpl;
import com.podologia.sistema_clientes.cliente.IClienteRepo;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.detalleCita.IDetalleRepo;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.producto.IProductoRepo;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;
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

@RequiredArgsConstructor
@Component


public class ValidacionCita {


    private final IClienteRepo clienteRepo;
    private final ICitaRepo citaRepo;
    private final IDetalleRepo detalleRepo;
    private final IServicioRepo servicioRepo;
    private final IProductoRepo productoRepo;


    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionCita.class);


    public void existDuenio(CitaEntity citaEntity) {
        log.debug("Iniciando validación de cita: {}", citaEntity);
        if (citaEntity == null) {
            log.warn("La cita es null");
            throw new ValidacionException("Cita no puede ser null");
        }
        if (citaEntity.getCliente() == null) {
            log.warn("El cliente de la cita es null");
            throw new ValidacionException("Cliente no puede ser null");
        }

        Long idCliente = citaEntity.getCliente().getIdCliente();
        log.debug("ID cliente recibido: {}", idCliente);
        if (idCliente == null) {
            log.warn("El idCliente es null");
            throw new ValidacionException("Id del cliente no puede ser null");
        }

        if (!clienteRepo.existsById(idCliente)) {
            log.warn("Cliente con ID {} no existe", idCliente);
            throw new EntidadNoEncontradaException("El cliente con ID " + idCliente + " no existe");
        }

        log.info("Cliente validado correctamente con ID: {}", idCliente);
    }

    public void existIdCiteToSaveDetail(Long idCita, DetalleEntity detalle) {
        if (detalle == null) {
            log.error("El detalle recibido es null.");
            throw new ValidacionException("El detalle no puede ser null.");
        }

        log.info("Detalle recibido: idDetalleCita={}, servicio_id={}, motivo='{}', duracion_total={}, productosUsadosCount={}",
                detalle.getIdDetalleCita(),
                detalle.getServicio() != null ? detalle.getServicio().getIdServicio() : "null",
                detalle.getMotivo(),
                detalle.getDuracionTotal(),
                detalle.getListProductUtilziado() != null ? detalle.getListProductUtilziado().size() : 0
        );

        if (!findId(idCita)) {
            log.error("No existe la cita con ID {}", idCita);
            throw new EntidadNoEncontradaException("Cita con ID " + idCita + " no existe.");
        }

        if (detalle.getServicio() == null || detalle.getServicio().getIdServicio() == null) {
            log.error("El servicio o su ID es null.");
            throw new ValidacionException("El servicio no puede ser null.");
        }

        // Aquí recuperamos el ServicioEntity completo desde la base de datos
        ServicioEntity servicioReal = servicioRepo.findById(detalle.getServicio().getIdServicio())
                .orElseThrow(() -> {
                    log.error("El servicio con ID {} no existe.", detalle.getServicio().getIdServicio());
                    return new EntidadNoEncontradaException("Servicio con ID " + detalle.getServicio().getIdServicio() + " no existe.");
                });

        // Reemplazamos el servicio parcial por el completo
      //  detalle.setServicio(servicioReal);

        if (detalle.getListProductUtilziado() != null) {
            for (ProductUtilizadoEntity p : detalle.getListProductUtilziado()) {
                if (p.getProductoEntity() == null || p.getProductoEntity().getIdProducto() == null) {
                    throw new ValidacionException("Cada producto utilizado debe tener un producto con ID válido.");
                }

                if (!productoRepo.existsById(p.getProductoEntity().getIdProducto())) {
                    throw new EntidadNoEncontradaException("Producto con ID " + p.getProductoEntity().getIdProducto() + " no existe.");
                }
            }
        }
    }





    public void findIdtoDelete(Long id_cita){
        if(!citaRepo.existsById(id_cita)){
            log.error("no existe dicho id {}", id_cita);
            throw new EntidadNoEncontradaException("Cita con ID " + id_cita + " no existe.");

        }else{
            log.info("el id existe, si puede ser eliminado: {}",id_cita);
        }

    }

    private Boolean findId(Long id_cita){
        if(!citaRepo.existsById(id_cita)){
            log.error("no existe dicho id {}", id_cita);
            return false;
        }else{
            log.info("el id existe: {}",id_cita);
        }
       return true;
    }


    public CitaEntity validateParametersToEdit(Long id_cita, CitaEntity nuevosDatos) {

        log.info("---- INICIO VALIDACIÓN DE CITA PARA ACTUALIZACIÓN ----");
        log.info("ID recibido: {}", id_cita);
        log.info("Cliente recibido en nuevosDatos: {}", nuevosDatos.getCliente());


        if (id_cita == null) {
            log.error("ID de cita es null");
            throw new ValidacionException("El ID de la cita no puede ser null");
        }

        CitaEntity existente = citaRepo.findById(id_cita)
                .orElseThrow(() -> {
                    log.error("No se encontró cita con ID: {}", id_cita);
                    return new EntidadNoEncontradaException("Cita con ID " + id_cita + " no existe.");
                });

        if (nuevosDatos == null) {
            log.error("El objeto nuevosDatos es null");
            throw new ValidacionException("Los datos nuevos de la cita no pueden ser null");
        }

        // Mostrar los datos que vienen en nuevosDatos por consola sin JSON
        log.info("Datos recibidos para actualizar cita:");
        log.info("  Fecha: {}", nuevosDatos.getFechaCita() != null ? nuevosDatos.getFechaCita() : "no se proporcionó");
        log.info("  Tipo: {}", nuevosDatos.getTipoCita() != null ? nuevosDatos.getTipoCita() : "no se proporcionó");
        log.info("  Estado: {}", nuevosDatos.getEstadoCita() != null ? nuevosDatos.getEstadoCita() : "no se proporcionó");
        log.info("  Observaciones: {}", nuevosDatos.getObservaciones() != null ? nuevosDatos.getObservaciones() : "no se proporcionó");
        if (nuevosDatos.getCliente() != null && nuevosDatos.getCliente().getIdCliente() != null) {
            log.info("  Cliente ID: {}", nuevosDatos.getCliente().getIdCliente() != null ? nuevosDatos.getCliente().getIdCliente() : "no se proporcionó");
            log.info("  Cliente nombre: {}", nuevosDatos.getCliente().getNombreCliente() != null ? nuevosDatos.getCliente().getNombreCliente() : "no se proporcionó");
        } else {
            log.info("Cliente es null o no tiene ID");
        }

        if (nuevosDatos.getCliente() == null || nuevosDatos.getCliente().getIdCliente() == null) {
            log.error("El cliente dentro de nuevosDatos es null o no tiene ID");
            throw new ValidacionException("El cliente de la cita no puede ser null y debe tener un ID");
        }

        Long idCliente = nuevosDatos.getCliente().getIdCliente();

        ClienteEntity clienteExistente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> {
                    log.error("No se encontró cliente con ID: {}", idCliente);
                    return new EntidadNoEncontradaException("Cliente con ID " + idCliente + " no existe.");
                });

        // Actualiza cliente con el existente de BD
        existente.setCliente(clienteExistente);

        // Actualiza los campos de la cita existente solo si se proveen en nuevosDatos
        if (nuevosDatos.getFechaCita() != null) {
            existente.setFechaCita(nuevosDatos.getFechaCita());
        }
        if (nuevosDatos.getTipoCita() != null) {
            existente.setTipoCita(nuevosDatos.getTipoCita());
        }
        if (nuevosDatos.getEstadoCita() != null) {
            existente.setEstadoCita(nuevosDatos.getEstadoCita());
        }
        if (nuevosDatos.getObservaciones() != null) {
            existente.setObservaciones(nuevosDatos.getObservaciones());
        }

        log.info("✅ Cita con ID {} validada y lista para ser persistida", id_cita);

        // Mostrar estado final del objeto existente (actualizado) también con logs campo por campo
        log.info("Estado final de la cita actualizada:");
        log.info("  Fecha: {}", existente.getFechaCita());
        log.info("  Tipo: {}", existente.getTipoCita());
        log.info("  Estado: {}", existente.getEstadoCita());
        log.info("  Observaciones: {}", existente.getObservaciones());
        log.info("  Cliente ID: {}", existente.getCliente().getIdCliente());
        log.info("  Cliente nombre: {}", existente.getCliente().getNombreCliente());

        log.info("---- FIN VALIDACIÓN DE CITA ----");

        return existente;
    }



}
