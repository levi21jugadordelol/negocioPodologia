package com.podologia.sistema_clientes.producto.producto_controller;

import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.producto.producto_service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final IProductService productService;

    @PostMapping("producto/crear")
    public ResponseEntity<String> saveProducto(@RequestBody ProductoEntity producto){
        productService.saveProduct(producto);
        return  ResponseEntity.status(HttpStatus.CREATED).body("producto creado");
    }
}
