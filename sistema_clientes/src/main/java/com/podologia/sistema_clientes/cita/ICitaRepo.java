package com.podologia.sistema_clientes.cita;

import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitaRepo extends JpaRepository<CitaEntity,Long> {
}
