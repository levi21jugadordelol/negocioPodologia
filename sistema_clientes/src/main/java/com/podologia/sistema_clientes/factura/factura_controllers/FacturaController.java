package com.podologia.sistema_clientes.factura.factura_controllers;

import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.factura.factura_dtos.FacturaDto;
import com.podologia.sistema_clientes.factura.factura_dtos.FacturaRequestDto;
import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.factura.factura_service.IFacturaService;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;
import com.podologia.sistema_clientes.shared.mappers.FacturaMapper;
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
    private final FacturaMapper facturaMapper;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.factura.factura_controllers.FacturaController.class);

    @PostMapping("/crear")
    public ResponseEntity<String> saveFactura(@RequestBody FacturaRequestDto facturaRequestDto){

        FacturaEntity factura = facturaMapper.toFacturaEntity(facturaRequestDto);
        facturaService.saveFactura(factura);
        return  ResponseEntity.status(HttpStatus.CREATED).body("facture save");
    }

    @GetMapping("/todas")
    public ResponseEntity<List<FacturaDto>> traerTodasFacturas(){
        List<FacturaDto> listaFacturas = facturaService.getFactura()
                .stream()
                .map(facturaMapper::toFacturaDto)
                .toList();


        if(listaFacturas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(listaFacturas);
    }

    @GetMapping("/{idFactura}")
    public ResponseEntity<FacturaDto> buscarFacturaPorId(@PathVariable Long idFactura){
        FacturaEntity response = facturaService.findFactura(idFactura).get();
        return ResponseEntity.ok(facturaMapper.toFacturaDto(response));
    }

    @DeleteMapping("/eliminar/{idFactura}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long idFactura){
        facturaService.deleteFactura(idFactura);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/editar/{idFactura}")
    public ResponseEntity<String> editarFactura(@PathVariable Long idFactura, @RequestBody FacturaRequestDto facturaRequestDto){

        FacturaEntity facturaActualizada = facturaMapper.toFacturaEntity(facturaRequestDto);

        facturaService.editFactura(idFactura,facturaActualizada);
        return ResponseEntity.ok("facture edit");
    }


    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<FacturaDto> buscarFacturaPorCodigo(@PathVariable String codigo) {
        FacturaEntity factura = facturaService.buscarcodigo(codigo).get();
        return  ResponseEntity.ok(facturaMapper.toFacturaDto(factura));
    }


    @GetMapping("/id_cliente/{idCliente}")
    public ResponseEntity<List<FacturaDto>> obtenerFacturaPorIdCliente(@PathVariable Long idCliente) {
        List<FacturaEntity> listaFacturaIdCliente = facturaService.findFacturaCliente(idCliente);
        if(listaFacturaIdCliente.isEmpty()){
            log.warn("No se encontraron factura para el cliente con ID: {}", idCliente);
            throw new EntidadNoEncontradaException("No se encontraron citas para el cliente con ID: " + idCliente);
        }

        List<FacturaDto> dtoList = listaFacturaIdCliente.stream()
                .map(facturaMapper::toFacturaDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }



}
