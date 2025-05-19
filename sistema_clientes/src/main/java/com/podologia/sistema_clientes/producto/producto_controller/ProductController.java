package com.podologia.sistema_clientes.producto.producto_controller;

import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.producto.producto_service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("producto")
public class ProductController {
    private final IProductService productService;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.producto.producto_controller.ProductController.class);

    @PostMapping("/crear")
    public ResponseEntity<String> saveProducto(@RequestBody ProductoEntity producto){
        productService.saveProduct(producto);
        return  ResponseEntity.status(HttpStatus.CREATED).body("producto creado");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoEntity>> listarProductos() {
        List<ProductoEntity> productos = productService.getProducts();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> obtenerProductoPorId(@PathVariable Long id) {
        return productService.findProduct(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/buscar")
    public ResponseEntity<ProductoEntity> buscarProductoPorNombre(@RequestParam String nombre) {
        return productService.buscarProducto(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<String> editarProducto(@PathVariable Long id, @RequestBody ProductoEntity producto) {
        productService.editProducto(id, producto);
        return ResponseEntity.ok("Producto actualizado correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }





}
