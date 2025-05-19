package com.podologia.sistema_clientes.shared.metodoValidaciones;


import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cliente.IClienteRepo;
import com.podologia.sistema_clientes.factura.IFacturaRepo;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
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
public class ValidacionFactura {

    private final IFacturaRepo facturaRepo;
    private final IClienteRepo clienteRepo;
    private final ICitaRepo citaRepo;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionFactura.class);

    public void validateToCreate(FacturaEntity facturaEntity){
        if(facturaEntity == null || facturaEntity.getClienteEntity() == null
        || facturaEntity.getCitaEntity()== null){
            throw new ValidacionException("Cita o cliente no pueden ser null");
        }

        Long idCliente = facturaEntity.getClienteEntity().getIdCliente();

        if(idCliente == null || !clienteRepo.existsById(idCliente)){
            throw new EntidadNoEncontradaException("El cliente con ID " + idCliente + " no existe");
        }

        log.info("Cliente validado correctamente con ID: {}", idCliente);

        Long idCita = facturaEntity.getCitaEntity().getIdCita();

        if(idCita == null || !citaRepo.existsById(idCita)){
            throw new EntidadNoEncontradaException("la cita con ID " + idCita + " no existe");
        }

        log.info("Cita validado correctamente con ID: {}", idCita);





    }




    public FacturaEntity validateToEdit(Long idFactura, FacturaEntity nuevosDatos) {
        log.info("Validando y preparando actualización para Factura con ID: {}", idFactura);

        FacturaEntity existente = facturaRepo.findById(idFactura)
                .orElseThrow(() -> new EntidadNoEncontradaException("Factura con ID " + idFactura + " no existe"));

        if (nuevosDatos == null || nuevosDatos.getClienteEntity() == null || nuevosDatos.getCitaEntity() == null) {
            throw new ValidacionException("Cliente o cita no pueden ser null al editar la factura.");
        }

        // Logueamos qué campos llegan (debug útil)
        log.info("Datos recibidos: Cliente={}, Cita={}, TipoPago={}, Formato={}, Total={}",
                nuevosDatos.getClienteEntity().getIdCliente(),
                nuevosDatos.getCitaEntity().getIdCita(),
                nuevosDatos.getTipoPago(),
                nuevosDatos.getFormatoRecibo(),
                nuevosDatos.getTotalFactura());

        // Actualización campo por campo
        existente.setClienteEntity(nuevosDatos.getClienteEntity());
        existente.setCitaEntity(nuevosDatos.getCitaEntity());
        existente.setFechaEnmision(nuevosDatos.getFechaEnmision());
        existente.setTipoPago(nuevosDatos.getTipoPago());
        existente.setFormatoRecibo(nuevosDatos.getFormatoRecibo());
        existente.setTotalFactura(nuevosDatos.getTotalFactura());

        return existente;
    }





}



/*public class FacturaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;
    private String numeroRecibo;//usar un metodo para generar un numeroFactura en shared -> util, ahi crearlo, por mientras crearlo manualmente

    @ManyToOne
    @JoinColumn(name = "cliente_id",nullable = false)
    private ClienteEntity clienteEntity;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    //unique = true impone una restricción de uno a uno en base de datos (¡clave!).
    @JoinColumn(name = "cita_id",nullable = false ,unique = true) // esta columna será la foreign key
    private CitaEntity citaEntity;

    private LocalDateTime fechaEnmision;
    private double totalFactura;
    private TipoPago tipoPago;
    private FormatoRecibo formatoRecibo;

    -----------------------------------------------------

       @Transactional
    @Override
    public void saveFactura(FacturaEntity facturaEntity) {
          if(facturaEntity == null){
              log.warn("no puede haber ser nullo el objeto factura");
              throw new IllegalArgumentException("factura no puede ser null");
          }
          facturaRepo.save(facturaEntity);
        log.info("factura guardada con éxito: {}", facturaEntity);
    }








    */