package integrado.prog2.services;

import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.FoodStoreException;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();
    private long nextId = 1;

    public List<Pedido> listar() {
        List<Pedido> result = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                result.add(p);
            }
        }
        return result;
    }

    public Pedido crear(Usuario usuario, FormaPago formaPago, List<DetallePedido> items) {
        if (usuario == null) {
            throw new FoodStoreException("No se puede crear un pedido sin usuario");
        }
        if (items == null || items.isEmpty()) {
            throw new FoodStoreException("El pedido debe tener al menos un item");
        }

        for (DetallePedido d : items) {
            Producto p = d.getProducto();
            if (p == null) {
                throw new FoodStoreException("Item sin producto asignado");
            }
            if (p.getStock() < d.getCantidad()) {
                throw new FoodStoreException("Stock insuficiente para " + p.getNombre()
                        + ": solicitado " + d.getCantidad()
                        + ", disponible " + p.getStock());
            }
        }

        Pedido pedido = new Pedido(usuario, formaPago);
        pedido.setId(nextId++);
        for (DetallePedido d : items) {
            Producto p = d.getProducto();
            p.setStock(p.getStock() - d.getCantidad());
            d.setId(nextId++);
            pedido.getItems().add(d);
        }
        pedido.calcularTotal();
        pedidos.add(pedido);
        return pedido;
    }

    public Pedido buscarPorId(Long id) {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new FoodStoreException("Pedido no encontrado: " + id);
    }

    public Pedido actualizarEstado(Long id, Estado nuevoEstado) {
        Pedido p = buscarPorId(id);
        p.setEstado(nuevoEstado);
        return p;
    }

    public void eliminar(Long id) {
        Pedido p = buscarPorId(id);
        p.softDelete();
    }

    public List<Pedido> listarPorUsuario(Long usuarioId) {
        List<Pedido> result = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado() && p.getUsuario() != null && p.getUsuario().getId().equals(usuarioId)) {
                result.add(p);
            }
        }
        return result;
    }
}
