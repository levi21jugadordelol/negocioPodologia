package com.podologia.sistema_clientes.servicio.servicio_controller;


import com.podologia.sistema_clientes.factura.factura_entity.FacturaEntity;
import com.podologia.sistema_clientes.servicio.servicio_entity.ServicioEntity;
import com.podologia.sistema_clientes.servicio.servicio_service.IServicioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ServicioController {

    private final IServicioService servicioService;

    @PostMapping("servicio/crear")
    public ResponseEntity<String> saveServicio(@RequestBody ServicioEntity servicio){
        servicioService.saveServicio(servicio);
        return  ResponseEntity.status(HttpStatus.CREATED).body("servicio creada");
    }

}
