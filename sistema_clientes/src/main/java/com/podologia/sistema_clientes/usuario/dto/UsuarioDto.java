package com.podologia.sistema_clientes.usuario.dto;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private String nombreUsuario;
    private String apellidoUsuario;
    private String email;
    private String contrasenia;
    private String contraseniaConfirmada;
}
