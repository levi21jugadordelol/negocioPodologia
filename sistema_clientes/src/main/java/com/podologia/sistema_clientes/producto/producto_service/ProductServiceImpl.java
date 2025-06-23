package com.podologia.sistema_clientes.producto.producto_service;

import com.podologia.sistema_clientes.producto.IProductoRepo;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionProducto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductoRepo productoRepo;
    private final ValidacionProducto validacionProducto;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.producto.producto_service.ProductServiceImpl.class);

    @Override
    public List<ProductoEntity> getProducts() {
        List<ProductoEntity> listaProductos = productoRepo.findAll();
        log.info("el total de lista de productos es : {}",listaProductos.size());
        return listaProductos;
    }

    @Transactional
    @Override
    public void saveProduct(ProductoEntity productoEntity) {
       validacionProducto.validateProductToSave(productoEntity);
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
    public ProductoEntity editProducto(Long idProducto, ProductoEntity productoEntity) {
        // Validar existencia, y posibles reglas del dominio
        ProductoEntity productoEditado = validacionProducto.validateParametersToEditProduct(idProducto, productoEntity);

        // Guardar cambios y obtener entidad actualizada
        ProductoEntity productoActualizado = productoRepo.save(productoEditado);

        // Log de auditoría
        log.info("Producto actualizado correctamente con ID: {}", productoActualizado.getIdProducto());

        return productoActualizado;
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
