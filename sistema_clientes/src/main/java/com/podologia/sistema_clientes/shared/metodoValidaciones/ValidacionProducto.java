package com.podologia.sistema_clientes.shared.metodoValidaciones;


import com.podologia.sistema_clientes.producto.IProductoRepo;
import com.podologia.sistema_clientes.producto.producto_entity.ProductoEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import com.podologia.sistema_clientes.shared.exception.ValidacionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ValidacionProducto {

    private final IProductoRepo productoRepo;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionProducto.class);

    public void validateProductToSave(ProductoEntity producto){
        if(producto == null){
            throw new ValidacionException("El objeto producto no puede ser null.");
        }

        if(producto.getNombreProducto() == null || producto.getNombreProducto().isBlank()){
            throw new ValidacionException("El nombre del producto es obligatorio.");
        }

        if(producto.getStock() <=0){
            throw new ValidacionException("El precio del producto debe ser mayor a cero.");
        }

        if(producto.getTipoProducto() == null){
            throw new ValidacionException("El tipo de producto no puede estar vacio.");
        }

    }

    public ProductoEntity validateParametersToEditProduct(Long idProduct, ProductoEntity nuevoProducto) {
        log.info("Validando existencia y preparando actualización para producto con ID: {}", idProduct);

        ProductoEntity existente = productoRepo.findById(idProduct)
                .orElseThrow(() -> {
                    log.error("No se encontró producto con ID: {}", idProduct);
                    return new EntidadNoEncontradaException("Producto con ID " + idProduct + " no existe.");
                });

        if (nuevoProducto == null) {
            log.error("La entidad producto recibida es null");
            throw new ValidacionException("Producto no puede ser null");
        }

        log.info("=== Datos recibidos para actualizar producto ===");
        log.info("Nombre: {}", nuevoProducto.getNombreProducto() != null ? nuevoProducto.getNombreProducto() : "no se proporcionó");
        log.info("Stock: {}", nuevoProducto.getStock());
        log.info("Tipo: {}", nuevoProducto.getTipoProducto() != null ? nuevoProducto.getTipoProducto() : "no se proporcionó");

        // Actualización campo por campo
        if (nuevoProducto.getNombreProducto() != null) {
            existente.setNombreProducto(nuevoProducto.getNombreProducto());
        }
        if (nuevoProducto.getStock() >= 0) {  // Aseguras que no se ponga negativo
            existente.setStock(nuevoProducto.getStock());
        }
        if (nuevoProducto.getTipoProducto() != null) {
            existente.setTipoProducto(nuevoProducto.getTipoProducto());
        }

        log.info("Producto validado y listo para ser persistido");

        return existente;
    }






}


/*  @Transactional
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

        public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    private String nombreProducto;
    private double stock;
    private String tipoProducto;

    @OneToMany(mappedBy = "productoEntity",
            fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Set<ProductUtilizadoEntity> listProductUtilizado = new HashSet<>();

    public void addProductUtilizado(ProductUtilizadoEntity productUtilizadoEntity){
        this.listProductUtilizado.add(productUtilizadoEntity);
        productUtilizadoEntity.setProductoEntity(this);
    }
    }*/