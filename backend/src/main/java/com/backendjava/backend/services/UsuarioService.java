package com.backendjava.backend.services;

import com.backendjava.backend.model.Usuario;
import com.backendjava.backend.repository.UsuarioRepository;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @PersistenceContext
    private EntityManager entityManager;

    //metodo para traer todos los usuarios
    public List<Usuario> getAllUsers(){
        return usuarioRepository.findAll();
    }

//metodo para eliminar usuario
    public void deleteUser(Long id){
        usuarioRepository.deleteById(id);
    }


    //metodo para traer solo un usuario
    public Usuario getUserById(Long id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        return optionalUsuario.get();
    }

    //metodo para crear usuario
    public Usuario createdUser (Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    // metodo para modificar usuario
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setApellido(usuarioActualizado.getApellido());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
        usuarioExistente.setPassword(usuarioActualizado.getPassword());
        return usuarioRepository.save(usuarioExistente);
    }

    public Usuario optenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email= :email";
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()) {
            return null;
        } else {
            String passwordHashed = lista.get(0).getPassword();

            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            if (argon2.verify(passwordHashed, usuario.getPassword())) {
                return lista.get(0);
            }
            return null;
        }
    }
}
