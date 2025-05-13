package productoUtilizado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import productoUtilizado.productoUtilizado_entity.ProductUtilizadoEntity;

@Repository
public interface IProductoUtilizadoRepo extends JpaRepository<ProductUtilizadoEntity,Long> {
}
