package cita.cita_entity;

import cliente.cliente_entity.ClienteEntity;
import enume.EstadoCita;
import enume.TipoCita;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;
    private ClienteEntity cliente;
    private TipoCita tipoCita;
    private LocalDateTime fechaCita;
    private EstadoCita estadoCita;
    private String observaciones;
}
