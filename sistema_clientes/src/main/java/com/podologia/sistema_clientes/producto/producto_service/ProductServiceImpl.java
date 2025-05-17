package com.podologia.sistema_clientes.producto.producto_service;

import com.podologia.sistema_clientes.producto.IProductoRepo;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductoRepo productoRepo;

    @Override
    public List<ProductoEntity> getProducts() {
        List<ProductoEntity> listaProductos = productoRepo.findAll();
        log.info("el total de lista de productos es : {}",listaProductos.size());
        return listaProductos;
    }

    @Transactional
    @Override
    public void saveProduct(ProductoEntity productoEntity) {
        if (productoEntity == null) {
            log.warn("no puede haber ser nullo el objeto producto");
            throw new IllegalArgumentException("producto no puede ser null");
        }
        productoRepo.save(productoEntity);
        log.info("producto guardada con éxito: {}", productoEntity);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id_producto) {
         if(!productoRepo.existsById(id_producto)){
             log.error("no existe dicho id {}", id_producto);
             throw new EntidadNoEncontradaException("producto con ID " + id_producto + " no existe.");
        }else{
             productoRepo.deleteById(id_producto);
             log.info("el id fue eliminado correctamente: {}",id_producto);
         }
    }

    @Override
    public Optional<ProductoEntity> findProduct(Long id_producto) {
         ProductoEntity producto = productoRepo.findById(id_producto)
                 .orElseThrow(()->{
                     log.warn("producto no encontrado con ID: {}", id_producto);
                     return new EntidadNoEncontradaException("producto con ID " + id_producto + " no encontrado.");
                 });
        log.info("producto encontrado exitosamente con ID: {}", id_producto);
        return Optional.of(producto);
    }

    @Transactional
    @Override
    public void editProducto(Long id_producto, ProductoEntity productoEntity) {
        ProductoEntity producto = productoRepo.findById(id_producto)
                .orElseThrow(()->{
                    log.warn("producto no encontrado con ID: {}", id_producto);
                    return new EntidadNoEncontradaException("producto con ID " + id_producto + " no encontrado.");
                });
        productoEntity.setIdProducto(id_producto);
        productoRepo.save(productoEntity);
        log.info("producto actualizado con ID: {}", id_producto);
    }

    @Override
    public Optional<ProductoEntity> buscarProducto(String nombre_producto) {
        ProductoEntity producto = productoRepo.findProductoByNombre(nombre_producto)
                .orElseThrow(()->{
                    log.warn("producto no encontrado con el nombre: {}", nombre_producto);
                    return new EntidadNoEncontradaException("producto con el nombre: " + nombre_producto + " no encontrado.");
                });
        log.info("producto encontrado exitosamente con el nombre: {}", nombre_producto);
        return Optional.of(producto);
    }
}
