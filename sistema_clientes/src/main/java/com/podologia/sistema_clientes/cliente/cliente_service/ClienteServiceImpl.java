package com.podologia.sistema_clientes.cliente.cliente_service;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cliente.IClienteRepo;
import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService{

    private final IClienteRepo clienteRepo;
    private final ICitaRepo citaRepo;


    @Override
    public List<ClienteEntity> getCliente() {
         List<ClienteEntity> listaClientes = clienteRepo.findAll();
         log.info("el total de clientes es: {}",listaClientes.size());
         return listaClientes;
    }

    @Transactional
    @Override
    public void saveCliente(ClienteEntity clienteEntity) {
        if(clienteEntity == null){
            log.warn("el objeto cliente no puede ser null");
            throw new IllegalArgumentException("El cliente no puede ser null");
        }
        clienteRepo.save(clienteEntity);
        log.info("Cliente guardado con éxito: {}", clienteEntity);

    }



    @Transactional
    @Override
    public void deleteCliente(Long id_cliente) {
        if(!clienteRepo.existsById(id_cliente)){
            log.error("no existe el id :{}",id_cliente);
            throw new EntidadNoEncontradaException("Cliente con ID " + id_cliente + " no existe.");
        }
        clienteRepo.deleteById(id_cliente);
        log.info("el id fue eliminado correctamente: {}",id_cliente);

    }

    @Override
    public Optional<ClienteEntity> findCliente(Long id_cliente) {
        ClienteEntity cliente = clienteRepo.findById(id_cliente)
                .orElseThrow(()->{
                    log.warn("cliente no encontrado con ID: {}", id_cliente);
                    return new EntidadNoEncontradaException("cliente con ID " + id_cliente + " no encontrado.");
                });
        log.info("cliente encontrado exitosamente con ID: {}", id_cliente);
        return Optional.of(cliente);
    }

    @Transactional
    @Override
    public void editCliente(Long id_cliente, ClienteEntity cliente) {
        ClienteEntity existente = clienteRepo.findById(id_cliente)
                .orElseThrow(() -> {
                    log.error("No se puede editar, cliente no existe con ID: {}", id_cliente);
                    return new EntidadNoEncontradaException("Cliente con ID " + id_cliente + " no encontrado para edición.");
                });

        cliente.setIdCliente(id_cliente);
        clienteRepo.save(cliente);
        log.info("Cliente actualizado con ID: {}", id_cliente);
    }

    @Override
    public Optional<ClienteEntity> buscarClienteDni(String dniCliente) {
        return Optional.of(
                clienteRepo.findByDni(dniCliente)
                        .orElseThrow(() -> {
                            log.warn("Cliente no encontrado con DNI: {}", dniCliente);
                            return new EntidadNoEncontradaException("Cliente con DNI " + dniCliente + " no encontrado.");
                        })
        );
    }

    @Override
    public Optional<ClienteEntity> buscarNombreCliente(String nombreCliente) {
        return Optional.of(
                clienteRepo.findByNombre(nombreCliente)
                        .orElseThrow(() -> {
                            log.warn("Cliente no encontrado con nombre: {}", nombreCliente);
                            return new EntidadNoEncontradaException("Cliente con nombre " + nombreCliente + " no encontrado.");
                        })
        );
    }

    @Override
    public Optional<ClienteEntity> obtenerClientePorCitaId(Long idCita) {
        return Optional.of(
                citaRepo.findClienteByCitaId(idCita)
                        .orElseThrow(() -> {
                            log.warn("Cliente no encontrado con cita ID: {}", idCita);
                            return new EntidadNoEncontradaException("Cliente con cita ID " + idCita + " no encontrado.");
                        })
        );
    }


}
