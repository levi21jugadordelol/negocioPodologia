package com.podologia.sistema_clientes.factura.factura_service;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.IClienteRepo;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.factura.IFacturaRepo;
import com.podologia.sistema_clientes.factura.factura_dtos.FacturaRequestDto;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import com.podologia.sistema_clientes.shared.mappers.FacturaMapper;
import com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionFactura;
import com.podologia.sistema_clientes.shared.util.generarNumeroFactura.ToMakeNumberFacture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FacturaServiceImpl implements IFacturaService {

    private final IFacturaRepo facturaRepo;
    private final ValidacionFactura validacionFactura;
    private final ToMakeNumberFacture toMakeNumberFacture;
    private final ICitaRepo citaRepo;
    private final IClienteRepo clienteRepo;
    private final FacturaMapper facturaMapper;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.factura.factura_service.FacturaServiceImpl.class);

    @Override
    public List<FacturaEntity> getFactura() {
        List<FacturaEntity> listaFactura = facturaRepo.findAll();
        log.info("el total de factura es : {}",listaFactura.size());
        return  listaFactura;
    }

    @Transactional
    @Override
    public void saveFactura(FacturaRequestDto requestDto) {

        // 1. Convertimos datos simples del DTO a la entidad
        FacturaEntity facturaEntity = facturaMapper.toFacturaEntity(requestDto);

        // 2. Cargamos las relaciones
        ClienteEntity cliente = clienteRepo.findById(requestDto.getClienteId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Cliente no encontrado"));
        facturaEntity.setClienteEntity(cliente);

        CitaEntity cita = citaRepo.findById(requestDto.getCitaId())
                .orElseThrow(() -> new EntidadNoEncontradaException("Cita no encontrada"));
        facturaEntity.setCitaEntity(cita);

        // ✅ 3. Validación de negocio — AQUÍ VA validateToCreate
        validacionFactura.validateToCreate(facturaEntity);

        // 4. Agregamos la fecha de emisión
        LocalDateTime ahora = LocalDateTime.now();
        facturaEntity.setFechaEnmision(ahora);

        // 5. Calculamos rango de fechas del mes actual
        LocalDate primerDiaDelMes = ahora.toLocalDate().withDayOfMonth(1);
        LocalDateTime inicio = primerDiaDelMes.atStartOfDay();
        LocalDateTime fin = inicio.plusMonths(1);

        Long contador = facturaRepo.contarFacturasPorMes(inicio, fin);
        contador = (contador == null) ? 0L : contador;

        // 6. Generamos el código de recibo
        String codigoRecibo = toMakeNumberFacture.generarCodigoRecibo(primerDiaDelMes, contador);
        facturaEntity.setNumeroRecibo(codigoRecibo);

        // 7. Guardamos
        facturaRepo.save(facturaEntity);
        log.info("Factura guardada correctamente: {}", facturaEntity);
    }


    @Transactional
    @Override
    public void deleteFactura(Long id_factura) {
           if(!facturaRepo.existsById(id_factura)){
               log.error("no existe dicho id {}", id_factura);
               throw new EntidadNoEncontradaException("factura con ID " + id_factura + " no existe.");
           }else{
               facturaRepo.deleteById(id_factura);
               log.info("el id fue eliminado correctamente: {}",id_factura);
           }
    }

    @Override
    public Optional<FacturaEntity> findFactura(Long id_Factura) {
         FacturaEntity factura = facturaRepo.findById(id_Factura)
                 .orElseThrow(()->{
                     log.warn("factura no encontrado con ID: {}", id_Factura);
                     return new EntidadNoEncontradaException("factura con ID " + id_Factura + " no encontrado.");
                 });
        log.info("factura encontrado exitosamente con ID: {}", id_Factura);
        return Optional.of(factura);
    }

    @Transactional
    @Override
    public void editFactura(Long id_factura, FacturaRequestDto requestDto) {

        log.info("🛠️ [SERVICE] Iniciando lógica de edición para ID: {}", id_factura);

        // 🔄 Convertimos DTO a entidad base (sin relaciones cargadas)
        FacturaEntity facturaEditada = facturaMapper.toFacturaEntity(requestDto);
        log.info("🔄 [MAPPER] DTO convertido a entidad base: {}", facturaEditada);

        // 🔁 Cargamos relaciones desde la base de datos
        // Cargamos el cliente
        ClienteEntity cliente = clienteRepo.findById(requestDto.getClienteId())
                .orElseThrow(() -> {
                    log.error("❌ Cliente no encontrado con ID: {}", requestDto.getClienteId());
                    return new EntidadNoEncontradaException("Cliente no encontrado");
                });
        log.info("✅ Cliente cargado: {}", cliente);

        // Cargamos la cita
        CitaEntity cita = citaRepo.findById(requestDto.getCitaId())
                .orElseThrow(() -> {
                    log.error("❌ Cita no encontrada con ID: {}", requestDto.getCitaId());
                    return new EntidadNoEncontradaException("Cita no encontrada");
                });
        log.info("✅ Cita cargada: {}", cita);

        facturaEditada.setClienteEntity(cliente);
        facturaEditada.setCitaEntity(cita);
        log.info("🔗 Relaciones establecidas en factura editada");


        // Validamos antes de editar (puedes comparar si cambió algo importante, etc.)
        FacturaEntity entidadLista = validacionFactura.validateToEdit(id_factura, facturaEditada);
        log.info("✅ Validación completada. Entidad lista para guardar: {}", entidadLista);


        //  Guardamos
        facturaRepo.save(entidadLista);
        log.info("Factura actualizada con ID: {}", id_factura);
    }

    @Override
    public Optional<FacturaEntity> buscarcodigo(String codigo) {

        FacturaEntity factura = facturaRepo.findFacturaByNumero(codigo)
                .orElseThrow(()->{
                    log.warn("factura no encontrado con el codigo: {}", codigo);
                    return new EntidadNoEncontradaException("factura con el codigo " + codigo + " no encontrado.");
                });
        log.info("factura encontrado exitosamente con el codigo : {}", codigo);
        return Optional.of(factura);

    }

    @Override
    public List<FacturaEntity> findFacturaCliente(Long id_cliente) {
         List<FacturaEntity> listaFactura = facturaRepo.findFacturaByClientId(id_cliente);
         if(listaFactura.isEmpty()){
             log.warn("No se encontraron facturas para el cliente con ID: {}", id_cliente);
             throw new EntidadNoEncontradaException("No se encontraron factura para el cliente con ID: " + id_cliente);
         }

        return listaFactura;
    }
}
