package com.podologia.sistema_clientes.factura.factura_service;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.factura.IFacturaRepo;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
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

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.factura.factura_service.FacturaServiceImpl.class);

    @Override
    public List<FacturaEntity> getFactura() {
        List<FacturaEntity> listaFactura = facturaRepo.findAll();
        log.info("el total de factura es : {}",listaFactura.size());
        return  listaFactura;
    }

    @Transactional
    @Override
    public void saveFactura(FacturaEntity facturaEntity) {
        if (facturaEntity == null) {
            log.warn("No puede ser null el objeto factura");
            throw new IllegalArgumentException("Factura no puede ser null");
        }

        // Validación de negocio
        validacionFactura.validateToCreate(facturaEntity);

        // **Carga la cita gestionada desde la base de datos:**
        CitaEntity citaGestionada = citaRepo.findById(facturaEntity.getCitaEntity().getIdCita())
                .orElseThrow(() -> new EntidadNoEncontradaException("La cita con ID " + facturaEntity.getCitaEntity().getIdCita() + " no existe"));
        facturaEntity.setCitaEntity(citaGestionada);

        // Fecha de emisión
        LocalDateTime ahora = LocalDateTime.now();
        facturaEntity.setFechaEnmision(ahora);

        // Calcular rango del mes para contar facturas
        LocalDate primerDiaDelMes = ahora.toLocalDate().withDayOfMonth(1);
        LocalDateTime inicio = primerDiaDelMes.atStartOfDay();
        LocalDateTime fin = inicio.plusMonths(1);

        // Contar facturas en el mes actual
        Long contador = facturaRepo.contarFacturasPorMes(inicio, fin);
        contador = (contador == null) ? 0L : contador;

        // Generar código de recibo
        String codigoRecibo = toMakeNumberFacture.generarCodigoRecibo(primerDiaDelMes, contador);
        facturaEntity.setNumeroRecibo(codigoRecibo);

        // Guardar
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
    public void editFactura(Long id_factura, FacturaEntity facturaEntity) {
        FacturaEntity entidadLista = validacionFactura.validateToEdit(id_factura, facturaEntity);
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
