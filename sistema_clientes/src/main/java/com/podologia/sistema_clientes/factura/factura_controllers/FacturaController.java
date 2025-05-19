package com.podologia.sistema_clientes.factura.factura_controllers;

import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.factura.factura_service.IFacturaService;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("factura")
public class FacturaController {
    private final IFacturaService facturaService;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.factura.factura_controllers.FacturaController.class);

    @PostMapping("/crear")
    public ResponseEntity<String> saveFactura(@RequestBody FacturaEntity factura){
        facturaService.saveFactura(factura);
        return  ResponseEntity.status(HttpStatus.CREATED).body("factura creada");
    }

    @GetMapping("/todas")
    public ResponseEntity<List<FacturaEntity>> traerTodasFacturas(){
        List<FacturaEntity> listaFacturas = facturaService.getFactura();
        if(listaFacturas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(listaFacturas);
    }

    @GetMapping("/{idFactura}")
    public ResponseEntity<FacturaEntity> buscarFacturaPorId(@PathVariable Long idFactura){
        FacturaEntity response = facturaService.findFactura(idFactura).get();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar/{idFactura}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long idFactura){
        facturaService.deleteFactura(idFactura);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/editar/{idFactura}")
    public ResponseEntity<String> editarFactura(@PathVariable Long idFactura, @RequestBody FacturaEntity facturaNuevo){
        facturaService.editFactura(idFactura,facturaNuevo);
        return ResponseEntity.ok("factura editado");
    }


    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<FacturaEntity> buscarFacturaPorCodigo(@PathVariable String codigo) {
        FacturaEntity factura = facturaService.buscarcodigo(codigo).get();
        return  ResponseEntity.ok(factura);
    }


    @GetMapping("/id_cliente/{idCliente}")
    public List<FacturaEntity> obtenerFacturaPorIdCliente(@PathVariable Long idCliente) {
        List<FacturaEntity> listaFacturaIdCliente = facturaService.findFacturaCliente(idCliente);
        if(listaFacturaIdCliente.isEmpty()){
            log.warn("No se encontraron factura para el cliente con ID: {}", idCliente);
            throw new EntidadNoEncontradaException("No se encontraron citas para el cliente con ID: " + idCliente);
        }
        return listaFacturaIdCliente;
    }



}
