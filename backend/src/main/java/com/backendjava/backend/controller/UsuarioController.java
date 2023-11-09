package com.backendjava.backend.controller;

import com.backendjava.backend.model.Usuario;
import com.backendjava.backend.services.UsuarioService;
import com.backendjava.backend.util.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JWTUtil jwtUtil;
    @PostMapping
    public void createdUser(@RequestBody Usuario usuario){
        Argon2 argon2= Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash= argon2.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(hash);
        usuarioService.createdUser(usuario);
    }
    @GetMapping
    public List<Usuario> getAllUsers() {
        return usuarioService.getAllUsers();
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        usuarioService.deleteUser(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioService.actualizarUsuario(id, usuarioActualizado);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}