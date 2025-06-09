package com.podologia.sistema_clientes.usuario.dto;


import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RequestLogin {
    private String email;
    private String password;
}
