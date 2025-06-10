package com.podologia.sistema_clientes.cliente.cliente_controllers;

import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteRequestDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.cliente.cliente_service.IClienteService;
import com.podologia.sistema_clientes.shared.mappers.ClienteMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController

@RequestMapping("cliente")
@CrossOrigin(origins = "*")
public class ClienteController {
    private final IClienteService clienteService;

    private final ClienteMapper clienteMapper;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.cliente.cliente_controllers.ClienteController.class);

    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> saveCliente(@RequestBody ClienteRequestDto clienteRequestDto) {

        /*Map<String, Object> nos permite enviar:
         - "mensaje"	"Cliente guardado con éxito"
         - "idCliente"	clienteEntity.getIdCliente() (por ejemplo 42)
         */

        ClienteEntity clienteEntity = clienteMapper.toClienteEntity(clienteRequestDto);
        clienteService.saveCliente(clienteEntity);


        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Cliente guardado con éxito");
        response.put("idCliente", clienteEntity.getIdCliente()); // ← incluye el ID


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @GetMapping("/todas")
    public ResponseEntity<List<ClienteDto>> traerTodosClientes(){
        List<ClienteDto> listaClientes = clienteService.getCliente()
                .stream()
                .map(clienteMapper::toClienteDto)
                .toList();

      /*  if(listaClientes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } */
        log.info("🔁 Solicitud GET /cliente/todas → Se encontraron {} clientes", listaClientes.size());

        return ResponseEntity.ok(listaClientes);
    }

    @GetMapping("/export-excel")
    public void exportExcel(final HttpServletResponse response) throws IOException{
        response.setHeader("Content-Disposition","attachment; filname=clientes.xlsx");
        this.clienteService.exportExcel(response);
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
    public ResponseEntity<ClienteDto> editarCliente(@PathVariable Long idCliente, @RequestBody ClienteRequestDto clienteRequestDto){
        log.info("el id del duenio a editar es : "+idCliente);

        ClienteEntity nuevoCliente = clienteMapper.toClienteEntity(clienteRequestDto);

        ClienteEntity actualziado =clienteService.editCliente(idCliente, nuevoCliente);

        ClienteDto clienteDto = clienteMapper.toClienteDto(actualziado);

        return ResponseEntity.ok(clienteDto);
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
