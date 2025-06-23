package com.podologia.sistema_clientes.producto.producto_controller;

import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.producto.producto_dtos.ProductDto;
import com.podologia.sistema_clientes.producto.producto_dtos.ProductRequestDto;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.producto.producto_service.IProductService;
import com.podologia.sistema_clientes.shared.mappers.ProductMapper;
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
@CrossOrigin(origins = "*")
public class ProductController {
    private final IProductService productService;
    private final ProductMapper productMapper;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.producto.producto_controller.ProductController.class);

    @PostMapping("/crear")
    public ResponseEntity<String> saveProducto(@RequestBody ProductRequestDto productoRequestDto){

        ProductoEntity producto = productMapper.toProductEntity(productoRequestDto);

        productService.saveProduct(producto);
        return  ResponseEntity.status(HttpStatus.CREATED).body("product save");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductDto>> listarProductos() {
        List<ProductDto> productosDto = productService.getProducts()
                .stream()
                .map(productMapper::toProductDto)
                .toList();

        return ResponseEntity.ok(productosDto);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<ProductDto> obtenerProductoPorId(@PathVariable Long id) {
        return productService.findProduct(id)
                .map(productMapper::toProductDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/buscarNombre")
    public ResponseEntity<ProductDto> buscarProductoPorNombre(@RequestParam String nombre) {
        return productService.buscarProducto(nombre)
                .map(productMapper::toProductDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<ProductDto> editarProducto(
            @PathVariable Long id,
            @RequestBody ProductRequestDto productRequestDto) {

        log.info("🔧 Editando producto con id: {}", id);

        // 1. Convertir DTO a entidad
        ProductoEntity productoEntity = productMapper.toProductEntity(productRequestDto);

        // 2. Llamar al servicio para editar
        ProductoEntity actualizado = productService.editProducto(id, productoEntity);


        // 3. Convertir entidad actualizada a DTO de salida
        ProductDto productoDto = productMapper.toProductDto(actualizado);

        // 4. Retornar respuesta estructurada con JSON
        return ResponseEntity.ok(productoDto);
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }





}
