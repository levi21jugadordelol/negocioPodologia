package servicio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import servicio.servicio_entity.ServicioEntity;

@Repository
public interface IServicioRepo extends JpaRepository<ServicioEntity,Long> {

}
