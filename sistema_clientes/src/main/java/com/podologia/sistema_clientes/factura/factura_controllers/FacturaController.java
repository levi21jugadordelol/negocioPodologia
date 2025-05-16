package com.podologia.sistema_clientes.factura.factura_controllers;

import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.factura.factura_service.IFacturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FacturaController {
    private final IFacturaService facturaService;

    @PostMapping("factura/crear")
    public ResponseEntity<String> saveFactura(@RequestBody FacturaEntity factura){
        facturaService.saveFactura(factura);
        return  ResponseEntity.status(HttpStatus.CREATED).body("factura creada");
    }

    @GetMapping("factura/todas")
    public ResponseEntity<List<FacturaEntity>> traerTodasFacturas(){
        List<FacturaEntity> listaFacturas = facturaService.getFactura();
        if(listaFacturas.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(listaFacturas);
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaFacturas);
    }

    @GetMapping("factura/{idFactura}")
    public ResponseEntity<?> buscarFacturaPorId(@PathVariable Long idFactura){
        Optional<FacturaEntity> optional = facturaService.findFactura(idFactura);
        if(optional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optional.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FACTURA NO ENCONTRADO");
        }
    }

    @DeleteMapping("factura/eliminar/{idFactura}")
    public ResponseEntity<String> eliminarFactura(@PathVariable Long idFactura){
        facturaService.deleteFactura(idFactura);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("factura ELIMINADa");
    }


    @PutMapping("factura/editar/{idFactura}")
    public ResponseEntity<String> editarFactura(@PathVariable Long idFactura, @RequestBody FacturaEntity facturaNuevo){
        facturaService.editFactura(idFactura,facturaNuevo);
        return ResponseEntity.status(HttpStatus.OK).body("CLIENT EDITADA");
    }


    @GetMapping("factura/codigo/{codigo}")
    public ResponseEntity<?> buscarFacturaPorCodigo(@PathVariable String codigo) {
        Optional<FacturaEntity> optional = facturaService.buscarcodigo(codigo);
        if(optional.isPresent()){
            return  ResponseEntity.status(HttpStatus.OK).body(optional.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FACTURA NO ENCONTRADA");
        }
    }


    @GetMapping("factura/id_cliente/{idCliente}")
    public ResponseEntity<?> obtenerFacturaPorIdCliente(@PathVariable Long idCliente) {
        List<FacturaEntity> listaFacturaIdCliente = facturaService.findFacturaCliente(idCliente);
        if(listaFacturaIdCliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO SE ENCONTRARON FACTURAS PARA ESTE CLIENTE");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(listaFacturaIdCliente);
        }
    }



}
