package com.podologia.sistema_clientes.factura.factura_service;

import com.podologia.sistema_clientes.factura.IFacturaRepo;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
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
          }else{
              facturaRepo.save(facturaEntity);
          }
    }

    @Transactional
    @Override
    public void deleteFactura(Long id_factura) {
           if(facturaRepo.existsById(id_factura)){
               facturaRepo.deleteById(id_factura);
               log.info("el id fue eliminado correctamente: {}",id_factura);
           }else{
               log.error("no existe dicho id {}", id_factura);
           }
    }

    @Override
    public Optional<FacturaEntity> findFactura(Long id_Factura) {
          Optional<FacturaEntity> facturaId = facturaRepo.findById(id_Factura);
          if(facturaId.isPresent()){
              log.info("detalle encontrada con id:{}",id_Factura);
          }else{
              log.warn("no se encontro el id {}",id_Factura);
          }
          return facturaId;
    }

    @Override
    public void editFactura(Long id_factura, FacturaEntity facturaEntity) {
           Optional<FacturaEntity> facturaId = facturaRepo.findById(id_factura);
           if(facturaId.isPresent()){
               facturaEntity.setIdFactura(id_factura);
               facturaRepo.save(facturaEntity);
               log.info("factura actualizada con el id :{}",id_factura);
           }else{
               log.info("factura no hallada con el id :{}",id_factura);
           }
    }

    @Override
    public Optional<FacturaEntity> buscarcodigo(String codigo) {
        return facturaRepo.findFacturaByNumero(codigo);
    }

    @Override
    public List<FacturaEntity> findFacturaCliente(Long id_cliente) {
          return facturaRepo.findFacturaByClientId(id_cliente);
    }
}
