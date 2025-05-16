package com.podologia.sistema_clientes.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;

import java.util.Optional;

@Repository
public interface IProductoRepo extends JpaRepository<ProductoEntity,Long> {
    @Query("SELECT c from ProductoEntity c WHERE c.nombreProducto = :nombreProducto")
    Optional<ProductoEntity> findProductoByNombre(@Param("nombreProducto") String nombreProducto);
}
