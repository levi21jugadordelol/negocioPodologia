package detalleCita.detalle_entity;

import cita.cita_entity.CitaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import servicio.servicio_entity.ServicioEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DetalleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleCita;
    private CitaEntity cita;
    private ServicioEntity servicio;
    private double duracion_total;
    private String motivo;
}
