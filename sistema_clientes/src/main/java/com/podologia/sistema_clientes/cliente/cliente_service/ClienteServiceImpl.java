package com.podologia.sistema_clientes.cliente.cliente_service;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cliente.IClienteRepo;
import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.shared.util.mapper.ClienteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private  final ClienteMapper clienteMapper;

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
        }else{
            clienteRepo.save(clienteEntity);
        }
    }



    @Transactional
    @Override
    public void deleteCliente(Long id_cliente) {
        if(!(clienteRepo.existsById(id_cliente))){
            log.error("no existe el id :{}",id_cliente);
        }
        clienteRepo.deleteById(id_cliente);
        log.info("el id fue eliminado correctamente: {}",id_cliente);

    }

    @Override
    public Optional<ClienteEntity> findCliente(Long id_cliente) {
        Optional<ClienteEntity> clienteId= clienteRepo.findById(id_cliente);
        if(clienteId.isPresent()){
            log.info("cliente encontrado: con el id: {}",id_cliente);
        }else{
            log.warn("no se encontro cliente con el id:{}",id_cliente);
        }
        return clienteId;
    }

    @Override
    public void editCliente(Long id_cliente, ClienteEntity cliente) {
         Optional<ClienteEntity> clienteId = clienteRepo.findById(id_cliente);
         if(clienteId.isPresent()){
            cliente.setIdCliente(id_cliente);
            clienteRepo.save(cliente);
            log.info("cliente actualizado con el id: {}", id_cliente);
         }else {
             log.error("Intento de editar una cliente inexistente con ID: {}\", id_cliente");
         }
    }

    @Override
    public Optional<ClienteEntity> buscarClienteDni(String dni_cliente) {
        return clienteRepo.findByDni(dni_cliente);
    }

    @Override
    public Optional<ClienteEntity> buscarNombreCliente(String nombre_cliente) {

        return clienteRepo.findByNombre(nombre_cliente);
    }

    @Override
    public Optional<ClienteEntity> obtenerClientePorCitaId(Long cita_id) {
        return citaRepo.findClienteByCitaId(cita_id);
    }


}
