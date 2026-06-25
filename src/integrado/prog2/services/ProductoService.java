package integrado.prog2.services;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.exception.FoodStoreException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
    private long nextId = 1;

    public List<Producto> listar() {
        List<Producto> result = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                result.add(p);
            }
        }
        return result;
    }

    public Producto crear(String nombre, Double precio, String descripcion, int stock, String imagen, Boolean disponible, Categoria categoria) {
        Producto p = new Producto(nombre, precio, descripcion, stock, imagen, disponible, categoria);
        p.setId(nextId++);
        productos.add(p);
        return p;
    }

    public Producto buscarPorId(Long id) {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new FoodStoreException("Producto no encontrado: " + id);
    }

    public Producto editar(Long id, String nombre, Double precio, String descripcion, int stock, String imagen, Boolean disponible, Categoria categoria) {
        Producto p = buscarPorId(id);
        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setDescripcion(descripcion);
        p.setStock(stock);
        p.setImagen(imagen);
        p.setDisponible(disponible);
        p.setCategoria(categoria);
        return p;
    }

    public void eliminar(Long id) {
        Producto p = buscarPorId(id);
        p.softDelete();
    }

    public List<Producto> listarPorCategoria(Long categoriaId) {
        List<Producto> result = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado() && p.getCategoria() != null && p.getCategoria().getId().equals(categoriaId)) {
                result.add(p);
            }
        }
        return result;
    }
}
