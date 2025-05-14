package com.podologia.sistema_clientes.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;

@Repository
public interface IProductoRepo extends JpaRepository<ProductoEntity,Long> {
}
