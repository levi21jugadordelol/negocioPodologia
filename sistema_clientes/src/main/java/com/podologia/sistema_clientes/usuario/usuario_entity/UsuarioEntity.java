package com.podologia.sistema_clientes.usuario.usuario_entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.podologia.sistema_clientes.enume.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private Status status;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasenia;

}
