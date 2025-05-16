package com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_service;

import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;

import java.util.List;
import java.util.Optional;

public interface IProductUtilizadoService {
    public List<ProductUtilizadoEntity> getProductosUtilizados();

    public void saveProductoUtilizado(ProductUtilizadoEntity productUtilizado);

    public void deleteProductoUtilizado(Long id_ProductoUtilizado);

    public Optional<ProductUtilizadoEntity> findProductoUtilizado(Long id_productoUtilizado);

    public void editProductoUtilizado(Long id_productoUtilizadp, ProductUtilizadoEntity productUtilizado);

    List<ProductoEntity> buscarProductoUtilizado(Long id_productoEntity);


}
