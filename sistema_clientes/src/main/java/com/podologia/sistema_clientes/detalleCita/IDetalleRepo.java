package com.podologia.sistema_clientes.detalleCita;

import com.podologia.sistema_clientes.detalleCita.detalle_entity.DetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleRepo extends JpaRepository<DetalleEntity,Long> {
}
