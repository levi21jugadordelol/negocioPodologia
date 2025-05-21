package com.podologia.sistema_clientes.cliente.cliente_controllers;

import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteRequestDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.cliente.cliente_service.IClienteService;
import com.podologia.sistema_clientes.shared.mappers.ClienteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController

@RequestMapping("cliente")
public class ClienteController {
    private final IClienteService clienteService;

    private final ClienteMapper clienteMapper;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.cliente.cliente_controllers.ClienteController.class);

    @PostMapping("/crear")
    public ResponseEntity<String> saveCliente(@RequestBody ClienteRequestDto clienteRequestDto){

        ClienteEntity clienteEntity = clienteMapper.toClienteEntity(clienteRequestDto);

        clienteService.saveCliente(clienteEntity);
        return  ResponseEntity.status(HttpStatus.CREATED).body("client save");
    }


    @GetMapping("/todas")
    public ResponseEntity<List<ClienteDto>> traerTodosClientes(){
        List<ClienteDto> listaClientes = clienteService.getCliente()
                .stream()
                .map(clienteMapper::toClienteDto)
                .toList();

        if(listaClientes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(listaClientes);
    }


    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDto> buscarClientePorId(@PathVariable Long idCliente){
        ClienteEntity cliente = clienteService.findCliente(idCliente).get(); // service ya lanza si no existe
        return ResponseEntity.ok(clienteMapper.toClienteDto(cliente));
    }


    @DeleteMapping("/eliminar/{idCliente}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long idCliente){
        clienteService.deleteCliente(idCliente);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/editar/{idCliente}")
    public ResponseEntity<String> editarCliente(@PathVariable Long idCliente, @RequestBody ClienteRequestDto clienteRequestDto){
        log.info("el id del duenio a editar es : "+idCliente);

        ClienteEntity nuevoCliente = clienteMapper.toClienteEntity(clienteRequestDto);

        clienteService.editCliente(idCliente, nuevoCliente);
        return ResponseEntity.ok("Client edit");
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<ClienteDto> buscarClientePorDni(@PathVariable String dni) {
        ClienteEntity cliente = clienteService.buscarClienteDni(dni).get();
        return ResponseEntity.ok(clienteMapper.toClienteDto(cliente));
    }


    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ClienteDto> buscarClientePorNombre(@PathVariable String nombre) {
        ClienteEntity cliente = clienteService.buscarNombreCliente(nombre).get();
        return ResponseEntity.ok(clienteMapper.toClienteDto(cliente));
    }

    @GetMapping("/cita/{idCita}")
    public ResponseEntity<ClienteDto> obtenerClientePorCita(@PathVariable Long idCita) {
        ClienteEntity cliente = clienteService.obtenerClientePorCitaId(idCita).get();
        return ResponseEntity.ok(clienteMapper.toClienteDto(cliente));
    }




}
