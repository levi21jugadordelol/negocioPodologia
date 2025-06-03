package com.podologia.sistema_clientes.cita;

import com.podologia.sistema_clientes.cita.cita_dtos.CitaDto;
import com.podologia.sistema_clientes.cita.cita_entity.CitaEntity;
import com.podologia.sistema_clientes.cliente.cliente_entity.ClienteEntity;
import com.podologia.sistema_clientes.enume.EstadoCita;
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

    //obtener todos las citas en estado pendiente
  /*  @Query("SELECT c FROM CitaEntity c WHERE c.estadoCita = :estadoCita")
    List<CitaEntity> getListPendiente(@Param("estadoCita") EstadoCita estadoCita);*/

    @Query("""
                     SELECT DISTINCT c FROM CitaEntity c
                     JOIN FETCH c.servicio s
                     WHERE c.estadoCita = :estado
    """)
    List<CitaEntity> getListPendiente(@Param("estado") EstadoCita estado);



     //buscar a cliente en la lista de pendiente por dni
    @Query("""
    SELECT DISTINCT c FROM CitaEntity c
    JOIN FETCH c.servicio s
    JOIN c.cliente cl
    WHERE c.estadoCita = :estado
    AND (:dni IS NULL OR cl.dniCliente = :dni)
    
""")
    List<CitaEntity> buscarPorEstadoYDni(@Param("estado") EstadoCita estado,@Param("dni") String dni);




    //buscar a cliente en la lista de pendiente por nombre
    @Query("""
    SELECT DISTINCT c FROM CitaEntity c
    JOIN FETCH c.servicio s
    JOIN c.cliente cl
    WHERE c.estadoCita = :estado
    AND (:nombre IS NULL OR LOWER(cl.nombreCliente) LIKE LOWER(CONCAT('%', :nombre, '%')))
""")
    List<CitaEntity> buscarPorEstadoYNombre(@Param("estado") EstadoCita estado, @Param("nombre") String nombre);



}
