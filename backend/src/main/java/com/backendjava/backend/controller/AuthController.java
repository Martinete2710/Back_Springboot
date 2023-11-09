package com.backendjava.backend.controller;

import com.backendjava.backend.model.Usuario;
import com.backendjava.backend.services.UsuarioService;
import com.backendjava.backend.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
   private UsuarioService usuarioService;
    @Autowired
private JWTUtil jwtUtil;

  @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario) {

      Usuario usuarioLogueado=usuarioService.optenerUsuarioPorCredenciales(usuario);

      if(usuarioLogueado == null){
          return "FAIL";

      }else {
          String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getEmail());
          return tokenJwt;
      }
  }
}

