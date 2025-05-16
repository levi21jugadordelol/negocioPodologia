package com.podologia.sistema_clientes.producto.producto_service;

import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    public List<ProductoEntity> getProducts();

    public void saveProduct(ProductoEntity productoEntity);

    public void deleteProduct(Long id_producto);

    public Optional<ProductoEntity> findProduct(Long id_producto);

    public void editProducto(Long id_producto, ProductoEntity productoEntity);

    Optional<ProductoEntity> buscarProducto(String nombre_producto);



}
