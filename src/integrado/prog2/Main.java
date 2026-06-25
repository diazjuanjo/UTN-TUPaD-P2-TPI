package integrado.prog2;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;
import integrado.prog2.services.CategoriaService;
import integrado.prog2.services.PedidoService;
import integrado.prog2.services.ProductoService;
import integrado.prog2.services.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoriaService categoriaService = new CategoriaService();
    private static final ProductoService productoService = new ProductoService();
    private static final UsuarioService usuarioService = new UsuarioService();
    private static final PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {
        cargarDatosIniciales();
        int opcion;
        limpiarPantalla();
        do {
            System.out.println("===== FOOD STORE =====");
            System.out.println("1. Gestionar Categorias");
            System.out.println("2. Gestionar Productos");
            System.out.println("3. Gestionar Usuarios");
            System.out.println("4. Gestionar Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero();
            limpiarPantalla();
            switch (opcion) {
                case 1 :
                    menuCategorias();
                    break;
                case 2 : 
                    menuProductos();
                    break;
                case 3 :
                    menuUsuarios();
                    break;
                case 4 :
                    menuPedidos();
                    break;
                case 0 : 
                    System.out.println("Chau!");
                    System.out.println();
                    break;
                default : 
                    System.out.println("Opcion invalida");
                    System.out.println();
            }
        } while (opcion != 0);
    }

    // ==================== DATOS INICIALES ====================

    private static void cargarDatosIniciales() {
        Categoria catComida = categoriaService.crear("Comida", "Platos preparados");
        Categoria catBebida = categoriaService.crear("Bebida", "Bebidas frias y calientes");
        Categoria catPostre = categoriaService.crear("Postre", "Dulces y postres");

        productoService.crear("Hamburguesa", 4500.0, "Carne, queso, lechuga y tomate", 10, "hamburguesa.jpg", true, catComida);
        productoService.crear("Pizza", 3500.0, "Muzarella clasica", 8, "pizza.jpg", true, catComida);
        productoService.crear("Coca Cola", 1200.0, "Lata 355ml", 20, "coca.jpg", true, catBebida);
        productoService.crear("Agua Mineral", 800.0, "Botella 500ml", 15, "agua.jpg", true, catBebida);
        productoService.crear("Helado", 2000.0, "Dulce de leche", 12, "helado.jpg", true, catPostre);
        productoService.crear("Flan", 1500.0, "Con crema y dulce", 10, "flan.jpg", true, catPostre);

        Usuario admin = usuarioService.crear("Admin", "", "admin@foodstore.com", "123456789", "1234", Rol.ADMIN);
        Usuario user1 = usuarioService.crear("Juan", "Perez", "juan@mail.com", "987654321", "abcd", Rol.USUARIO);

        List<DetallePedido> items = new ArrayList<>();
        Producto hamburguesa = productoService.buscarPorId(1L);
        Producto coca = productoService.buscarPorId(3L);
        items.add(new DetallePedido(hamburguesa, 2, hamburguesa.getPrecio()));
        items.add(new DetallePedido(coca, 1, coca.getPrecio()));
        pedidoService.crear(user1, FormaPago.TARJETA, items);

        System.out.println("Datos iniciales cargados correctamente.");
    }

    // ==================== CATEGORIAS ====================

    private static void menuCategorias() {
        int opcion;
        limpiarPantalla();
        do {
            System.out.println();
            System.out.println("----- CATEGORIAS -----");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            opcion = leerEntero();
            limpiarPantalla();
            switch (opcion) {
                case 1:
                    listarCategorias();
                    break;
                case 2:
                    crearCategoria();
                    break;
                case 3:
                    editarCategoria();
                    break;
                case 4:
                    eliminarCategoria();
                    break;
                default : 
                    System.out.println("Opcion invalida");
                    System.out.println();
            }
        } while (opcion != 0);
    }

    private static void listarCategorias() {
        List<Categoria> lista = categoriaService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay categorias.");
            return;
        }
        lista.forEach(c -> System.out.println(c.getId() + ". " + c.getNombre()
                + " | " + c.getDescripcion()));
    }

    private static void crearCategoria() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Descripcion: ");
        String desc = scanner.nextLine().trim();
        Categoria c = categoriaService.crear(nombre, desc);
        System.out.println("Categoria creada: " + c);
    }

    private static void editarCategoria() {
        listarCategorias();
        System.out.print("ID de categoria a editar: ");
        Long id = leerLong();
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Nueva descripcion: ");
        String desc = scanner.nextLine().trim();
        try {
            categoriaService.editar(id, nombre, desc);
            System.out.println("Categoria actualizada.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarCategoria() {
        listarCategorias();
        System.out.print("ID de categoria a eliminar: ");
        Long id = leerLong();
        try {
            categoriaService.eliminar(id);
            System.out.println("Categoria eliminada (logica).");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== PRODUCTOS ====================

    private static void menuProductos() {
        int opcion;
        limpiarPantalla();
        do {
            System.out.println();
            System.out.println("----- PRODUCTOS -----");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            opcion = leerEntero();
            limpiarPantalla();
            switch (opcion) {
                case 1:
                    listarProductos();
                    break;
                case 2:
                    crearProducto();
                    break;
                case 3:
                    editarProducto();
                    break;
                case 4:
                    eliminarProducto();
                    break;
                default : 
                    System.out.println("Opcion invalida");
                    System.out.println();
            }
        } while (opcion != 0);
    }

    private static void listarProductos() {
        List<Producto> lista = productoService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay productos.");
            return;
        }
        lista.forEach(p -> System.out.println(p.getId() + ". " + p.getNombre()
                + " | $" + p.getPrecio()
                + " | Stock: " + p.getStock()
                + " | Disp: " + (p.getDisponible() ? "si" : "no")
                + " | " + (p.getCategoria() != null ? p.getCategoria().getNombre() : "sin categoria")));
    }

    private static void crearProducto() {
        List<Categoria> cats = categoriaService.listar();
        if (cats.isEmpty()) {
            System.out.println("Debe crear al menos una categoria primero.");
            return;
        }
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Descripcion: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Precio: ");
        Double precio = leerDouble();
        System.out.print("Stock: ");
        int stock = leerEntero();
        System.out.print("Imagen (ruta): ");
        String imagen = scanner.nextLine().trim();
        System.out.print("Disponible? (s/n): ");
        boolean disponible = scanner.nextLine().trim().equalsIgnoreCase("s");
        System.out.println("Categorias disponibles:");
        cats.forEach(c -> System.out.println(c.getId() + ". " + c.getNombre()));
        System.out.print("ID de categoria: ");
        Long catId = leerLong();
        try {
            Categoria cat = categoriaService.buscarPorId(catId);
            Producto p = productoService.crear(nombre, precio, desc, stock, imagen, disponible, cat);
            System.out.println("Producto creado: " + p);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editarProducto() {
        listarProductos();
        System.out.print("ID de producto a editar: ");
        Long id = leerLong();
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Nueva descripcion: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Nuevo precio: ");
        Double precio = leerDouble();
        System.out.print("Nuevo stock: ");
        int stock = leerEntero();
        System.out.print("Nueva imagen: ");
        String imagen = scanner.nextLine().trim();
        System.out.print("Disponible? (s/n): ");
        boolean disponible = scanner.nextLine().trim().equalsIgnoreCase("s");
        System.out.println("Categorias disponibles:");
        categoriaService.listar().forEach(c -> System.out.println(c.getId() + ". " + c.getNombre()));
        System.out.print("ID de categoria: ");
        Long catId = leerLong();
        try {
            Categoria cat = categoriaService.buscarPorId(catId);
            productoService.editar(id, nombre, precio, desc, stock, imagen, disponible, cat);
            System.out.println("Producto actualizado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarProducto() {
        listarProductos();
        System.out.print("ID de producto a eliminar: ");
        Long id = leerLong();
        try {
            productoService.eliminar(id);
            System.out.println("Producto eliminado (logica).");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== USUARIOS ====================

    private static void menuUsuarios() {
        int opcion;
        limpiarPantalla();
        do {
            System.out.println();
            System.out.println("----- USUARIOS -----");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            opcion = leerEntero();
            limpiarPantalla();
            switch (opcion) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    crearUsuario();
                    break;
                case 3:
                    editarUsuario();
                    break;
                case 4:
                    eliminarUsuario();
                    break;
                default : 
                    System.out.println("Opcion invalida");
                    System.out.println();
            }
        } while (opcion != 0);
    }

    private static void listarUsuarios() {
        List<Usuario> lista = usuarioService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay usuarios.");
            return;
        }
        lista.forEach(u -> System.out.println(u.getId() + ". " + u.getNombre() + " " + u.getApellido()
                + " | " + u.getMail()
                + " | Cel: " + u.getCelular()
                + " | " + u.getRol()));
    }

    private static void crearUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Mail: ");
        String mail = scanner.nextLine().trim();
        System.out.print("Celular: ");
        String celular = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine().trim();
        System.out.println("Roles: 1. ADMIN  2. USUARIO");
        System.out.print("Seleccione rol: ");
        int r = leerEntero();
        Rol rol = (r == 1) ? Rol.ADMIN : Rol.USUARIO;
        try {
            Usuario u = usuarioService.crear(nombre, apellido, mail, celular, pass, rol);
            System.out.println("Usuario creado: " + u);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editarUsuario() {
        listarUsuarios();
        System.out.print("ID de usuario a editar: ");
        Long id = leerLong();
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Nuevo apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Nuevo mail: ");
        String mail = scanner.nextLine().trim();
        System.out.print("Nuevo celular: ");
        String celular = scanner.nextLine().trim();
        System.out.print("Nueva contraseña: ");
        String pass = scanner.nextLine().trim();
        System.out.println("Roles: 1. ADMIN  2. USUARIO");
        System.out.print("Seleccione rol: ");
        int r = leerEntero();
        Rol rol = (r == 1) ? Rol.ADMIN : Rol.USUARIO;
        try {
            usuarioService.editar(id, nombre, apellido, mail, celular, pass, rol);
            System.out.println("Usuario actualizado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarUsuario() {
        listarUsuarios();
        System.out.print("ID de usuario a eliminar: ");
        Long id = leerLong();
        try {
            usuarioService.eliminar(id);
            System.out.println("Usuario eliminado (logica).");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== PEDIDOS ====================

    private static void menuPedidos() {
        int opcion;
        limpiarPantalla();
        do {
            System.out.println();
            System.out.println("----- PEDIDOS -----");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Actualizar estado");
            System.out.println("4. Eliminar");
            System.out.println("5. Listar por usuario");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            opcion = leerEntero();
            limpiarPantalla();
            switch (opcion) {
                case 1:
                    listarPedidos();
                    break;
                case 2:
                    crearPedido();
                    break;
                case 3:
                    actualizarEstadoPedido();
                    break;
                case 4:
                    eliminarPedido();
                    break;
                case 5:
                    listarPedidosPorUsuario();
                    break;
                default : 
                    System.out.println("Opcion invalida");
                    System.out.println();
            }
        } while (opcion != 0);
    }

    private static void listarPedidos() {
        List<Pedido> lista = pedidoService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos.");
            return;
        }
        lista.forEach(p -> {
            System.out.println("Pedido #" + p.getId()
                    + " | " + (p.getUsuario() != null ? p.getUsuario().getNombre() + " " + p.getUsuario().getApellido() : "?")
                    + " | Fecha: " + p.getFecha()
                    + " | Estado: " + p.getEstado()
                    + " | Total: $" + p.getTotal()
                    + " | Pago: " + p.getFormaPago());
            p.getItems().forEach(d ->
                    System.out.println("   - " + d.getCantidad() + "x "
                            + d.getProducto().getNombre()
                            + " ($" + d.getSubtotal() + ")"));
        });
    }

    private static void crearPedido() {
        List<Usuario> usuarios = usuarioService.listar();
        if (usuarios.isEmpty()) {
            System.out.println("Debe crear al menos un usuario primero.");
            return;
        }
        List<Producto> productos = productoService.listar();
        if (productos.isEmpty()) {
            System.out.println("Debe crear al menos un producto primero.");
            return;
        }

        System.out.println("Usuarios disponibles:");
        usuarios.forEach(u -> System.out.println(u.getId() + ". " + u.getNombre() + " " + u.getApellido()));
        System.out.print("ID de usuario: ");
        Long userId = leerLong();

        List<DetallePedido> items = new ArrayList<>();
        boolean agregarMas;
        do {
            System.out.println("Productos disponibles:");
            productos.forEach(p -> System.out.println(p.getId() + ". " + p.getNombre()
                    + " | $" + p.getPrecio() + " | Stock: " + p.getStock()));
            System.out.print("ID de producto: ");
            Long prodId = leerLong();
            System.out.print("Cantidad: ");
            int cantidad = leerEntero();
            try {
                Producto prod = productoService.buscarPorId(prodId);
                items.add(new DetallePedido(prod, cantidad, prod.getPrecio()));
                System.out.println("Item agregado.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.print("Agregar otro producto? (s/n): ");
            agregarMas = scanner.nextLine().trim().equalsIgnoreCase("s");
        } while (agregarMas);

        if (items.isEmpty()) {
            System.out.println("Pedido cancelado: sin items.");
            return;
        }

        System.out.println("Formas de pago: 1. TARJETA  2. TRANSFERENCIA  3. EFECTIVO");
        System.out.print("Seleccione: ");
        int fp = leerEntero();
        FormaPago formaPago;
        switch (fp) {
            case 1:
                formaPago = FormaPago.TARJETA;
                break;
            case 2:
                formaPago = FormaPago.TRANSFERENCIA;
                break;
            default:
                formaPago = FormaPago.EFECTIVO;
        }

        try {
            Usuario user = usuarioService.buscarPorId(userId);
            Pedido pedido = pedidoService.crear(user, formaPago, items);
            System.out.println("Pedido creado: #" + pedido.getId() + " | Total: $" + pedido.getTotal());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void actualizarEstadoPedido() {
        listarPedidos();
        System.out.print("ID de pedido: ");
        Long id = leerLong();
        System.out.println("Estados: 1. PENDIENTE  2. CONFIRMADO  3. TERMINADO  4. CANCELADO");
        System.out.print("Seleccione nuevo estado: ");
        int e = leerEntero();
        Estado estado;
        switch (e) {
            case 1:
                estado = Estado.PENDIENTE;
                break;
            case 2:
                estado = Estado.CONFIRMADO;
                break;
            case 3:
                estado = Estado.TERMINADO;
                break;
            default:
                estado = Estado.CANCELADO;
        }
        try {
            pedidoService.actualizarEstado(id, estado);
            System.out.println("Estado actualizado.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void eliminarPedido() {
        listarPedidos();
        System.out.print("ID de pedido a eliminar: ");
        Long id = leerLong();
        try {
            pedidoService.eliminar(id);
            System.out.println("Pedido eliminado (logica).");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarPedidosPorUsuario() {
        listarUsuarios();
        System.out.print("ID de usuario: ");
        Long userId = leerLong();
        List<Pedido> lista = pedidoService.listarPorUsuario(userId);
        if (lista.isEmpty()) {
            System.out.println("El usuario no tiene pedidos.");
            return;
        }
        lista.forEach(p -> System.out.println("Pedido #" + p.getId()
                + " | Fecha: " + p.getFecha()
                + " | Estado: " + p.getEstado()
                + " | Total: $" + p.getTotal()));
    }

    private static int leerEntero() {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }

    private static long leerLong() {
        while (true) {
            try {
                long val = Long.parseLong(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }

    private static double leerDouble() {
        while (true) {
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }

    private static void limpiarPantalla() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
