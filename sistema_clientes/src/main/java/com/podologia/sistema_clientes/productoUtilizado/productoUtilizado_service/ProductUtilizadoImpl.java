package com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_service;


import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.producto.producto_service.IProductService;
import com.podologia.sistema_clientes.productoUtilizado.IProductoUtilizadoRepo;
import com.podologia.sistema_clientes.productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;
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
         }else{
             productoUtilizadoRepo.save(productUtilizado);
         }
    }

    @Transactional
    @Override
    public void deleteProductoUtilizado(Long id_ProductoUtilizado) {
            if(productoUtilizadoRepo.existsById(id_ProductoUtilizado)){
                productoUtilizadoRepo.deleteById(id_ProductoUtilizado);
                log.info("el id fue eliminado correctamente: {}",id_ProductoUtilizado);
            }else{
                log.error("no existe dicho id {}", id_ProductoUtilizado);
            }
    }

    @Override
    public Optional<ProductUtilizadoEntity> findProductoUtilizado(Long id_productoUtilizado) {
        Optional<ProductUtilizadoEntity> prodUtilizaId = productoUtilizadoRepo.findById(id_productoUtilizado);
        if(prodUtilizaId.isPresent()){
            log.info("prod utlizado  encontrada con id:{}",id_productoUtilizado);
        }else{
            log.warn("no se encontro el id {}",id_productoUtilizado);
        }
        return  prodUtilizaId;
    }

    @Override
    public void editProductoUtilizado(Long id_productoUtilizadp, ProductUtilizadoEntity productUtilizado) {
         Optional<ProductUtilizadoEntity> prodUtilizaId = productoUtilizadoRepo.findById(id_productoUtilizadp);
         if(prodUtilizaId.isPresent()){
             productUtilizado.setIdProductoUtilizado(id_productoUtilizadp);
             productoUtilizadoRepo.save(productUtilizado);
             log.info("product utilizado  con el id :{}",id_productoUtilizadp);
         }else{
             log.info("product utilizado no hallada con el id :{}",id_productoUtilizadp);
         }
    }

    @Override
    public List<ProductoEntity> buscarProductoUtilizado(Long id_productoEntity) {
        return productoUtilizadoRepo.buscarProductoUtilizado(id_productoEntity);
    }
}
