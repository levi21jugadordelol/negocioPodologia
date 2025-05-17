package com.podologia.sistema_clientes.factura.factura_service;

import com.podologia.sistema_clientes.factura.IFacturaRepo;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FacturaServiceImpl implements IFacturaService {

    private final IFacturaRepo facturaRepo;

    @Override
    public List<FacturaEntity> getFactura() {
        List<FacturaEntity> listaFactura = facturaRepo.findAll();
        log.info("el total de factura es : {}",listaFactura.size());
        return  listaFactura;
    }

    @Transactional
    @Override
    public void saveFactura(FacturaEntity facturaEntity) {
          if(facturaEntity == null){
              log.warn("no puede haber ser nullo el objeto factura");
              throw new IllegalArgumentException("factura no puede ser null");
          }
          facturaRepo.save(facturaEntity);
        log.info("factura guardada con éxito: {}", facturaEntity);
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
        FacturaEntity factura = facturaRepo.findById(id_factura)
                .orElseThrow(()->{
                    log.warn("factura no encontrado con ID: {}", id_factura);
                    return new EntidadNoEncontradaException("factura con ID " + id_factura + " no encontrado.");
                });
        facturaEntity.setIdFactura(id_factura);
        facturaRepo.save(facturaEntity);
        log.info("factura actualizado con ID: {}", id_factura);
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
