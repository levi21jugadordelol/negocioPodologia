package com.podologia.sistema_clientes.factura;

import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacturaRepo extends JpaRepository<FacturaEntity,Long> {
}
