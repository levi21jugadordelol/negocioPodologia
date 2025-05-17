package com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_service;


import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.producto.producto_service.IProductService;
import com.podologia.sistema_clientes.productoUtilizado.IProductoUtilizadoRepo;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;
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

public class ProductUtilizadoImpl implements IProductUtilizadoService {

    private final IProductoUtilizadoRepo productoUtilizadoRepo;

    @Override
    public List<ProductUtilizadoEntity> getProductosUtilizados() {
        List<ProductUtilizadoEntity> listaProductosUtilizados = productoUtilizadoRepo.findAll();
        log.info("el total de la lista de productos utilizados es : {}",listaProductosUtilizados.size());
        return listaProductosUtilizados;
    }

    @Transactional
    @Override
    public void saveProductoUtilizado(ProductUtilizadoEntity productUtilizado) {
         if(productUtilizado == null){
             log.warn("no puede haber ser nullo el objeto prod. utilizados");
             throw new IllegalArgumentException("cita no puede ser null");
         }
             productoUtilizadoRepo.save(productUtilizado);
        log.info("productoUtilizado guardada con éxito: {}", productUtilizado);

    }

    @Transactional
    @Override
    public void deleteProductoUtilizado(Long id_ProductoUtilizado) {
            if(!productoUtilizadoRepo.existsById(id_ProductoUtilizado)){
                log.error("no existe dicho id {}", id_ProductoUtilizado);
            }else{
                productoUtilizadoRepo.deleteById(id_ProductoUtilizado);
                log.info("el id fue eliminado correctamente: {}",id_ProductoUtilizado);
            }
    }

    @Override
    public Optional<ProductUtilizadoEntity> findProductoUtilizado(Long id_productoUtilizado) {
        ProductUtilizadoEntity productUtilizado = productoUtilizadoRepo.findById(id_productoUtilizado)
                .orElseThrow(()->{
                    log.warn("producto usado no encontrado con ID: {}", id_productoUtilizado);
                    return new EntidadNoEncontradaException("producto suado con ID " + id_productoUtilizado + " no encontrado.");
                });
        log.info("product usado encontrado exitosamente con ID: {}", id_productoUtilizado);
        return Optional.of(productUtilizado);
    }

    @Transactional
    @Override
    public void editProductoUtilizado(Long id_productoUtilizadp, ProductUtilizadoEntity productUtilizado) {
        ProductUtilizadoEntity productUsadoNuevo = productoUtilizadoRepo.findById(id_productoUtilizadp)
                .orElseThrow(()->{
                    log.warn("producto usado no encontrado con ID: {}", id_productoUtilizadp);
                    return new EntidadNoEncontradaException("producto suado con ID " + id_productoUtilizadp + " no encontrado.");
                });
        productUtilizado.setIdProductoUtilizado(id_productoUtilizadp);
        productoUtilizadoRepo.save(productUtilizado);
        log.info("producto suado actualizado con ID: {}", id_productoUtilizadp);
    }

    @Override
    public List<ProductoEntity> buscarProductoUtilizado(Long id_productoEntity) {
        List<ProductoEntity> listaProductosUsados = productoUtilizadoRepo.buscarProductoUtilizado(id_productoEntity);
        if(listaProductosUsados.isEmpty()){
            log.warn("No se encontraron productos usados con el producto con ID: {}", id_productoEntity);
            throw new EntidadNoEncontradaException("No se encontraron productos  con ID: " + id_productoEntity);
        }
        return listaProductosUsados;
    }
}
