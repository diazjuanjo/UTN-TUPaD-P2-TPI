package integrado.prog2.entities;

import integrado.prog2.exception.FoodStoreException;

public class DetallePedido extends Base {

    private Producto producto;
    private int cantidad;
    private Double subtotal;

    public DetallePedido() {
    }

    public DetallePedido(Producto producto, int cantidad, Double precio) {
        setCantidad(cantidad);
        this.producto = producto;
        this.subtotal = cantidad * precio;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public final void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new FoodStoreException("La cantidad debe ser mayor a 0: " + cantidad);
        }
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "DetallePedido{producto=" + (producto != null ? producto.getNombre() : "null")
                + ", cantidad=" + cantidad
                + ", subtotal=" + subtotal + "}";
    }
}
