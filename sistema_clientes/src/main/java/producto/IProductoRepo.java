package producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import producto.producto_entity.ProductoEntity;

@Repository
public interface IProductoRepo extends JpaRepository<ProductoEntity,Long> {
}
