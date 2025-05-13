package detalleCita;

import detalleCita.detalle_entity.DetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleRepo extends JpaRepository<DetalleEntity,Long> {
}
