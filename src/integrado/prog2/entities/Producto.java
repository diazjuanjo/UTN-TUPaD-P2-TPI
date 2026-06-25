package integrado.prog2.entities;

import integrado.prog2.exception.FoodStoreException;

public class Producto extends Base {

    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;

    public Producto() {
        this.disponible = true;
    }

    public Producto(String nombre, Double precio, String descripcion, int stock, String imagen, Boolean disponible, Categoria categoria) {
        setPrecio(precio);
        setStock(stock);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.disponible = disponible;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public final void setPrecio(Double precio) {
        if (precio < 0) {
            throw new FoodStoreException("El precio no puede ser negativo: " + precio);
        }
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public final void setStock(int stock) {
        if (stock < 0) {
            throw new FoodStoreException("El stock no puede ser negativo: " + stock);
        }
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Producto{id=" + getId()
                + ", nombre='" + nombre + "'"
                + ", precio=" + precio
                + ", stock=" + stock
                + ", disponible=" + disponible
                + ", categoria=" + (categoria != null ? categoria.getNombre() : "null") + "}";
    }
}
