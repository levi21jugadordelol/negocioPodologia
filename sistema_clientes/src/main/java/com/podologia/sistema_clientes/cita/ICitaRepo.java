package com.podologia.sistema_clientes.cita;

import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICitaRepo extends JpaRepository<CitaEntity,Long> {

    @Query("SELECT c FROM CitaEntity c WHERE c.cliente.id = :clienteId")
    List<CitaEntity> buscarCitasPorClienteId(@Param("clienteId") Long clienteId);


    //encontrar a cliente por medio del id cita
    @Query("SELECT c.cliente FROM CitaEntity c WHERE c.idCita = :idCita")
    Optional<ClienteEntity> findClienteByCitaId(@Param("idCita") Long idCita);

}
