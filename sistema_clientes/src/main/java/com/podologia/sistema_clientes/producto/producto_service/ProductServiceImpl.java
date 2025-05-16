package com.podologia.sistema_clientes.producto.producto_service;

import com.podologia.sistema_clientes.producto.IProductoRepo;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
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

        }else{
            productoRepo.save(productoEntity);
        }
    }

    @Transactional
    @Override
    public void deleteProduct(Long id_producto) {
         if(productoRepo.existsById(id_producto)){
           productoRepo.deleteById(id_producto);
             log.info("el id fue eliminado correctamente: {}",id_producto);
        }else{
             log.error("no existe dicho id {}", id_producto);
         }
    }

    @Override
    public Optional<ProductoEntity> findProduct(Long id_producto) {
        Optional<ProductoEntity> productoId = productoRepo.findById(id_producto);

        if(productoId.isPresent()){
            log.info("producto encontrada con id:{}",id_producto);
        }else{
            log.warn("no se encontro el id {}",id_producto);
        }
        return productoId;
    }

    @Override
    public void editProducto(Long id_producto, ProductoEntity productoEntity) {
           Optional<ProductoEntity> productoId = productoRepo.findById(id_producto);
           if(productoId.isPresent()){
               productoEntity.setIdProducto(id_producto);
               productoRepo.save(productoEntity);
               log.info("producto actualizada con el id :{}",id_producto);
           }else{
               log.info("factura con el id no hallada :{}",id_producto);
           }
    }

    @Override
    public Optional<ProductoEntity> buscarProducto(String nombre_producto) {
        return productoRepo.findProductoByNombre(nombre_producto);
    }
}
