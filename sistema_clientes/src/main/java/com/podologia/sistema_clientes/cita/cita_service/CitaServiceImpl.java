package com.podologia.sistema_clientes.cita.cita_service;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_dtos.CitaDto;
import com.podologia.sistema_clientes.cita.cita_dtos.CitaRequestDto;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.IClienteRepo;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.detalleCita.IDetalleRepo;
import com.podologia.sistema_clientes.detalleCita.detalle_dtos.DetalleRequestDto;
import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import com.podologia.sistema_clientes.enume.EstadoCita;
import com.podologia.sistema_clientes.producto.IProductoRepo;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;
import com.podologia.sistema_clientes.servicio.IServicioRepo;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import com.podologia.sistema_clientes.shared.exception.ValidacionException;
import com.podologia.sistema_clientes.shared.mappers.CitaMapper;
import com.podologia.sistema_clientes.shared.mappers.DetalleMapper;
import com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionCita;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CitaServiceImpl implements ICitaService {

    private final ICitaRepo citaRepo;
    private final ValidacionCita validacionCita;
    private final IServicioRepo servicioRepo;
    private final IDetalleRepo detalleRepo;
    private  final IProductoRepo productoRepo;
    private final IClienteRepo clienteRepo;
    private final CitaMapper citaMapper;
    private  final DetalleMapper detalleMapper;


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
                // Protección ante nulos
                if (p.getProductoEntity() == null || p.getProductoEntity().getIdProducto() == null) {
                    throw new ValidacionException("Cada producto utilizado debe tener un producto con ID válido.");
                }

                Long productoId = p.getProductoEntity().getIdProducto();

                // Reemplazamos el objeto ProductoEntity parcial por el real desde BD
                ProductoEntity producto = productoRepo.findById(productoId)
                        .orElseThrow(() -> new EntidadNoEncontradaException("Producto con ID " + productoId + " no existe."));

                p.setProductoEntity(producto);     // Relación directa
                p.setDetalleEntity(detalle);       // Relación inversa
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



  /*  @Transactional
    @Override
    public void editCita(Long id_cita, CitaEntity nuevosDatos) {
        log.info("Iniciando edición de cita con ID: {}", id_cita);

        CitaEntity citaActualziada = validacionCita.validateParametersToEdit(id_cita,nuevosDatos);

        // Obtener ClienteEntity con clienteId del DTO
        ClienteEntity cliente = clienteRepo.findById(nuevosDatos.getCliente().getIdCliente())
                .orElseThrow(() -> new EntidadNoEncontradaException("Cliente no encontrado"));

        // Setear cliente en la cita actualizada
        citaActualziada.setCliente(cliente);

        citaRepo.save(citaActualziada);

        log.info("Cita actualizada con éxito. ID: {}", id_cita);


    } */

    @Transactional
    @Override
    public void editCita(Long id_cita, CitaRequestDto nuevosDatosDto) {
        log.info("Iniciando edición de cita con ID: {}", id_cita);

        CitaEntity citaActual = citaRepo.findById(id_cita)
                .orElseThrow(() -> new EntidadNoEncontradaException("Cita no encontrada"));

        // Actualiza campos simples usando mapper
        citaMapper.updateCitaEntityFromDto(nuevosDatosDto, citaActual);

        // Setea cliente explícitamente
        ClienteEntity cliente = clienteRepo.findById(nuevosDatosDto.getClienteId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Cliente no encontrado"));
        citaActual.setCliente(cliente);

        log.info("Tamaño actual de la lista de detalles: {}",
                citaActual.getListaDetalle() != null ? citaActual.getListaDetalle().size() : "null");

        if (citaActual.getListaDetalle() == null) {
            log.warn("La lista de detalles está en null. Se inicializa manualmente para evitar NullPointerException.");
            citaActual.setListaDetalle(new ArrayList<>());
        }

        // Actualizar detalles
        citaActual.getListaDetalle().clear();

        if (nuevosDatosDto.getDetalles() != null) {
            for (DetalleRequestDto detalleDto : nuevosDatosDto.getDetalles()) {
                DetalleEntity detalle = detalleMapper.toDetalleEntity(detalleDto);
                citaActual.addDetalle(detalle);
            }
        }

        citaRepo.save(citaActual);

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

    @Override
    public List<CitaDto> getListPendiente(EstadoCita estadoCita) {
        List<CitaEntity> listaPendiente = citaRepo.getListPendiente(estadoCita);
        if(listaPendiente.isEmpty()){
            log.warn("no hay nada en la lista: {}", listaPendiente.size());
            throw new EntidadNoEncontradaException("No se encontraron citas con estado: " + estadoCita);

        }


        // ✅ AQUI AGREGA ESTA LINEA DE DEBUG:
        log.info(">>> Cita servicio: {}", listaPendiente.get(0).getServicio());


        return listaPendiente.stream()
                .map(citaMapper::toCitaDto) // Aquí se usa el mapper
                .collect(Collectors.toList());
    }

   @Override
    public Optional<ServicioEntity> obtenerServicioPorIdCita(Long idCita) {
        Optional<ServicioEntity> servicio = detalleRepo.findUnicoServicioByCitaId(idCita);

        if (servicio.isPresent()) {
            System.out.println(">>> SERVICIO CARGADO: " + servicio.get().getNombreServicio());
        } else {
            System.out.println(">>> NO SE ENCONTRÓ SERVICIO PARA CITA ID: " + idCita);
        }

        return servicio;
    }

}
