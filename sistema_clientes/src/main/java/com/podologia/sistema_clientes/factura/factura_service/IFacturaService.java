package com.podologia.sistema_clientes.factura.factura_service;

import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;

import java.util.List;
import java.util.Optional;

public interface IFacturaService {
    public List<FacturaEntity> getFactura();

    public void saveFactura(FacturaEntity facturaEntity);

    public void deleteFactura(Long id_factura);

    public Optional<FacturaEntity> findFactura(Long id_Factura);

    public void editFactura(Long id_factura, FacturaEntity facturaEntity);

    Optional<FacturaEntity> buscarcodigo(String codigo);

    List<FacturaEntity> findFacturaCliente(Long id_cliente);
}
