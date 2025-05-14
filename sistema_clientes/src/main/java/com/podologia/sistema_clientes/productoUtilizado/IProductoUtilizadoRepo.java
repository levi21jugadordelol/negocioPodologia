package com.podologia.sistema_clientes.productoUtilizado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;

@Repository
public interface IProductoUtilizadoRepo extends JpaRepository<ProductUtilizadoEntity,Long> {
}
