package com.podologia.sistema_clientes.shared.metodoValidaciones;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor

@Component
//todos las validaciones de detalle se hara en el service de servicio
public class ValidacionDetalle {

    private static final Logger log = LoggerFactory.getLogger(com.podologia.sistema_clientes.shared.metodoValidaciones.ValidacionDetalle.class);
}
