package com.podologia.sistema_clientes.cliente.cliente_controllers;

import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.cliente.cliente_service.IClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ClienteController {
    private final IClienteService clienteService;

    @PostMapping("cliente/crear")
    public ResponseEntity<String> saveCliente(@RequestBody ClienteEntity cliente){
        clienteService.saveCliente(cliente);
        return  ResponseEntity.status(HttpStatus.CREATED).body("cliente creada");
    }

    @GetMapping("cliente/todas")
    public ResponseEntity<List<ClienteEntity>> traerTodosClientes(){
        List<ClienteEntity> listaClientes = clienteService.getCliente();
        if(listaClientes.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(listaClientes);
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaClientes);
    }

    @GetMapping("cliente/{idCliente}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Long idCliente){
        Optional<ClienteEntity> optional = clienteService.findCliente(idCliente);
        if(optional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optional.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CLIENTE NO ENCONTRADO");
        }
    }

    @DeleteMapping("cliente/eliminar/{idCliente}")
    public ResponseEntity<String> eliminarCita(@PathVariable Long idCliente){
        clienteService.deleteCliente(idCliente);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("CLIENTE ELIMINADO");
    }

    @PutMapping("cliente/editar/{idCliente}")
    public ResponseEntity<String> editarCliente(@PathVariable Long idCliente, @RequestBody ClienteEntity clienteNuevo){
        clienteService.editCliente(idCliente,clienteNuevo);
        return ResponseEntity.status(HttpStatus.OK).body("CLIENT EDITADA");
    }

    @GetMapping("cliente/dni/{dni}")
    public ResponseEntity<?> buscarClientePorDni(@PathVariable String dni) {
       Optional<ClienteEntity> optional = clienteService.buscarClienteDni(dni);
       if(optional.isPresent()){
           return  ResponseEntity.status(HttpStatus.OK).body(optional.get());
       }else{
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CLIENTE NO ENCONTRADA");
       }
    }


    @GetMapping("cliente/nombre/{nombre}")
    public ResponseEntity<?> buscarClientePorNombre(@PathVariable String nombre) {
        Optional<ClienteEntity> optional = clienteService.buscarNombreCliente(nombre);
        if(optional.isPresent()){
            return  ResponseEntity.status(HttpStatus.OK).body(optional.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CLIENTE NO ENCONTRADA");
        }
    }

    @GetMapping("cliente/cita/{idCita}")
    public ResponseEntity<?> obtenerClientePorCita(@PathVariable Long idCita) {
        Optional<ClienteEntity> optional = clienteService.obtenerClientePorCitaId(idCita);
        if(optional.isPresent()){
            return  ResponseEntity.status(HttpStatus.OK).body(optional.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CLIENTE NO ENCONTRADA");
        }
    }




}
