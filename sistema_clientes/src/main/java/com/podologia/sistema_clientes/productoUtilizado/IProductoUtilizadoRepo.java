package com.podologia.sistema_clientes.productoUtilizado;

import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;

import java.util.List;

@Repository
public interface IProductoUtilizadoRepo extends JpaRepository<ProductUtilizadoEntity,Long> {

    //lista de productos utilizados a base
    @Query("SELECT pu.productoEntity FROM ProductUtilizadoEntity pu WHERE pu.productoEntity.idProducto = :idProducto")
    List<ProductoEntity> buscarProductoUtilizado(@Param("idProducto") Long id_productoEntity);
}


