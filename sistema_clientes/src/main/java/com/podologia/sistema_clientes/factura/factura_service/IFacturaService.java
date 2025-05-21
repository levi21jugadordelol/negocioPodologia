package com.podologia.sistema_clientes.factura.factura_service;

import com.podologia.sistema_clientes.factura.factura_dtos.FacturaRequestDto;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;

import java.util.List;
import java.util.Optional;

public interface IFacturaService {
    public List<FacturaEntity> getFactura();

    public void saveFactura(FacturaRequestDto facturaRequestDto);

    public void deleteFactura(Long id_factura);

    public Optional<FacturaEntity> findFactura(Long id_Factura);

    public void editFactura(Long id_factura, FacturaRequestDto requestDto);

    Optional<FacturaEntity> buscarcodigo(String codigo);

    List<FacturaEntity> findFacturaCliente(Long id_cliente);
}
