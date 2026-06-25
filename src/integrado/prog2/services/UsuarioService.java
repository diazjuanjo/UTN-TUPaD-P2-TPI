package integrado.prog2.services;

import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.exception.FoodStoreException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private long nextId = 1;

    public List<Usuario> listar() {
        List<Usuario> result = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                result.add(u);
            }
        }
        return result;
    }

    public Usuario crear(String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) {
        if (mailExiste(mail)) {
            throw new FoodStoreException("El mail ya está registrado: " + mail);
        }
        Usuario u = new Usuario(nombre, apellido, mail, celular, contraseña, rol);
        u.setId(nextId++);
        usuarios.add(u);
        return u;
    }

    public Usuario buscarPorId(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        throw new FoodStoreException("Usuario no encontrado: " + id);
    }

    public Usuario editar(Long id, String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) {
        Usuario u = buscarPorId(id);
        if (!u.getMail().equals(mail) && mailExiste(mail)) {
            throw new FoodStoreException("El mail ya está registrado: " + mail);
        }
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setMail(mail);
        u.setCelular(celular);
        u.setContraseña(contraseña);
        u.setRol(rol);
        return u;
    }

    public void eliminar(Long id) {
        Usuario u = buscarPorId(id);
        u.softDelete();
    }

    private boolean mailExiste(String mail) {
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)) {
                return true;
            }
        }
        return false;
    }
}
