package factura;

import factura.factura_entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacturaRepo extends JpaRepository<FacturaEntity,Long> {
}
