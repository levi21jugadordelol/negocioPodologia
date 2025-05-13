package factura.factura_entity;

import cita.cita_entity.CitaEntity;
import cliente.cliente_entity.ClienteEntity;
import enume.FormatoRecibo;
import enume.TipoPago;
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
public class FacturaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;
    private String numeroFactura;
    private ClienteEntity clienteEntity;
    private CitaEntity citaEntity;
    private LocalDateTime fechaEnmision;
    private double totalFactura;
    private TipoPago tipoPago;
    private FormatoRecibo formatoRecibo;
}
