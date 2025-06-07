package com.podologia.sistema_clientes.usuario.usuario_controllers;


import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;
import com.podologia.sistema_clientes.usuario.usuario_service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}") // Traer un cliente especifico
    public UsuarioEntity getUser(@PathVariable Long id) {
         return userService.getUser(id);
        //return service.getUser(id);
    }

    @GetMapping("/user") // Traer todos los clientes
    public List<UsuarioEntity> getAllUsers() {


      //  return service.getAllUsers();
        return userService.getAllUser();
    }

    @DeleteMapping("/user/{id}") // Eliminar un cliente
    public void removeUser(@PathVariable Long id) {
        //service.removeUser(id);
        userService.removeUser(id);
    }

    @PostMapping("/user") // Agregar cliente
    public void register(@RequestBody UsuarioEntity user) {
        //service.addUser(user);
        userService.addUser(user);
    }

    @PutMapping("/user/{id}") // Modificar cliente
    public void updateUser(@PathVariable Long id,
                           @RequestBody UsuarioEntity updateUser) {
      //  service.updateUser(id, updateUser);
        userService.updateUser(id,updateUser);
    }


  /*  @GetMapping("/user/search") // Busqueda
    public List<UsuarioEntity> searchUser(@RequestParam(name = "email", required = false) String email,
                                 @RequestParam(name = "address", required = false) String address) {
        //return service.searchUser(email, address);
        return userService.se
    } */

}
