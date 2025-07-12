package com.podologia.sistema_clientes.cliente.cliente_service;

import com.podologia.sistema_clientes.cita.ICitaRepo;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.IClienteRepo;
import com.podologia.sistema_clientes.cliente.cliente_dtos.ClienteDto;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.shared.exception.EntidadNoEncontradaException;

import com.podologia.sistema_clientes.shared.mappers.ClienteMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService{

    private final IClienteRepo clienteRepo;
    private final ICitaRepo citaRepo;
    private final ClienteMapper clienteMapper;

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.cliente.cliente_service.ClienteServiceImpl.class);

    @Override
    public List<ClienteEntity> getCliente() {
         List<ClienteEntity> listaClientes = clienteRepo.findAll();
         log.info("el total de clientes es: {}",listaClientes.size());
         return listaClientes;
    }

    @Override
    public ByteArrayOutputStream generarExcelClientes() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Clientes");

            // Crear cabecera
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Nombre");
            header.createCell(2).setCellValue("Apellido");
            header.createCell(3).setCellValue("DNI");
            header.createCell(4).setCellValue("Celular");
            header.createCell(5).setCellValue("Correo");

            // Obtener datos
            List<ClienteDto> clientes = this.obtenerClientes();

            int rowNum = 1;
            for (ClienteDto cliente : clientes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(cliente.getIdCliente());
                row.createCell(1).setCellValue(cliente.getNombreCliente());
                row.createCell(2).setCellValue(cliente.getApellidoCliente());
                row.createCell(3).setCellValue(cliente.getDniCliente());
                row.createCell(4).setCellValue(cliente.getCellCliente());
                row.createCell(5).setCellValue(cliente.getCorreoCliente());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream;

        } catch (IOException e) {
            throw new RuntimeException("Error generando Excel", e);
        }
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
    public ClienteEntity editCliente(Long id_cliente, ClienteEntity cliente) {
        ClienteEntity existente = clienteRepo.findById(id_cliente)
                .orElseThrow(() -> {
                    log.error("No se puede editar, cliente no existe con ID: {}", id_cliente);
                    return new EntidadNoEncontradaException("Cliente con ID " + id_cliente + " no encontrado para edición.");
                });

        cliente.setIdCliente(id_cliente);
        ClienteEntity actualizado = clienteRepo.save(cliente);
        log.info("Cliente actualizado con ID: {}", id_cliente);
        return actualizado;
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
        // 1. Verifica si la cita existe
        CitaEntity cita = citaRepo.findById(idCita)
                .orElseThrow(() -> {
                    log.warn("No se encontró la cita con ID: {}", idCita);
                    return new EntidadNoEncontradaException("No existe ninguna cita con ID " + idCita);
                });

        // 2. Verifica si tiene cliente asignado
        ClienteEntity cliente = cita.getCliente();
        if (cliente == null) {
            log.warn("La cita con ID {} no tiene cliente asignado", idCita);
            throw new EntidadNoEncontradaException("La cita con ID " + idCita + " no tiene un cliente asociado");
        }

        // 3. Todo OK, retorna el cliente
        return Optional.of(cliente);
    }

    @Override
    public List<ClienteDto> obtenerClientes() {
        List<ClienteEntity> entidades = clienteRepo.findAll();
        return entidades.stream()
                .map(clienteMapper::toClienteDto)
                .toList(); // o .collect(Collectors.toList())
    }

    @Override
    public List<ClienteEntity> guardarClientePorDia(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();              // 2025-06-28T00:00
        LocalDateTime fin = fecha.plusDays(1).atStartOfDay();     // 2025-06-29T00:00 (SIN .minusNanos)

        return clienteRepo.getClientXday(inicio, fin);
    }




}
