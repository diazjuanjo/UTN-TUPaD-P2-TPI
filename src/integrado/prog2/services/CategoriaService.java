package integrado.prog2.services;

import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.FoodStoreException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();
    private long nextId = 1;

    public List<Categoria> listar() {
        List<Categoria> result = new ArrayList<>();
        for (Categoria c : categorias) {
            if (!c.isEliminado()) {
                result.add(c);
            }
        }
        return result;
    }

    public Categoria crear(String nombre, String descripcion) {
        Categoria c = new Categoria(nombre, descripcion);
        c.setId(nextId++);
        categorias.add(c);
        return c;
    }

    public Categoria buscarPorId(Long id) {
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }
        }
        throw new FoodStoreException("Categoria no encontrada: " + id);
    }

    public Categoria editar(Long id, String nombre, String descripcion) {
        Categoria c = buscarPorId(id);
        c.setNombre(nombre);
        c.setDescripcion(descripcion);
        return c;
    }

    public void eliminar(Long id) {
        Categoria c = buscarPorId(id);
        c.softDelete();
    }
}
