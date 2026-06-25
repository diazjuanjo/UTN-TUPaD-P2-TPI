package integrado.prog2.entities;

import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.interfaces.Calculable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {

    private Usuario usuario;
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> items;

    public Pedido() {
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
        this.items = new ArrayList<>();
    }

    public Pedido(Usuario usuario, FormaPago formaPago) {
        this();
        this.usuario = usuario;
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public List<DetallePedido> getItems() {
        return items;
    }

    public void addDetallePedido(int cantidad, Double precio, Producto producto) {
        DetallePedido detalle = new DetallePedido(producto, cantidad, precio);
        items.add(detalle);
        calcularTotal();
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (DetallePedido d : items) {
            if (d.getProducto().getId().equals(producto.getId())) {
                return d;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        items.removeIf(d -> d.getProducto().getId().equals(producto.getId()));
        calcularTotal();
    }

    @Override
    public void calcularTotal() {
        this.total = 0.0;
        for (DetallePedido d : items) {
            this.total += d.getSubtotal();
        }
    }

    @Override
    public String toString() {
        return "Pedido{id=" + getId()
                + ", usuario=" + (usuario != null ? usuario.getNombre() : "null")
                + ", fecha=" + fecha
                + ", estado=" + estado
                + ", total=" + total
                + ", formaPago=" + formaPago
                + ", items=" + items.size() + "}";
    }
}
