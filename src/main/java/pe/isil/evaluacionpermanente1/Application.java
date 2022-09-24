package pe.isil.evaluacionpermanente1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Crear Conexion
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dae_ep1", "root", "root");

        System.out.println("****************************************************");
        System.out.println("                 EJERCICIO 1                        ");
        System.out.println("****************************************************");

        System.out.println("------------- Statement para listar productos --------------");

        String queryStatement = "SELECT * FROM productos";
        Statement statementListarProductos = connection.createStatement();
        ResultSet resultSetListarProductos = statementListarProductos.executeQuery(queryStatement);
        System.out.println("LISTADO DE PRODUCTOS: \n");
        while (resultSetListarProductos.next()){
            System.out.println(
                    "ID : " + resultSetListarProductos.getString("id") + "\n"
                    + "CATEGORIA: " + resultSetListarProductos.getString("categoria") + "\n"
                    + "CODIGO: " + resultSetListarProductos.getString("codigo") + "\n"
                    + "NOMBRE: " + resultSetListarProductos.getString("nombre") + "\n"
                    + "PRECIO_VENTA: " + resultSetListarProductos.getString("precio_venta") + "\n"
                    + "STOCK: " + resultSetListarProductos.getString("stock") + "\n"
                    + "DESCRIPCION: " + resultSetListarProductos.getString("descripcion") + "\n"
                    + "CREATED_AT: " + resultSetListarProductos.getString("created_at") + "\n"
            );
        }

        System.out.println("****************************************************");
        System.out.println("                 EJERCICIO 2                       ");
        System.out.println("****************************************************");

        System.out.println("------------- PreparedStatement  para actualizar un producto --------------");
        System.out.println("Producto a actualizar");
        System.out.println(mostrarUnSoloProducto(3));
        String queryPreparedStatement = "UPDATE productos SET precio_venta = ? WHERE id = ?";
        PreparedStatement preparedStatementActualizarProducto = connection.prepareStatement(queryPreparedStatement);
        preparedStatementActualizarProducto.setDouble(1, 3.50);
        preparedStatementActualizarProducto.setInt(2, 3);
        int rsPreparedStatement = preparedStatementActualizarProducto.executeUpdate();
        if (rsPreparedStatement == 1){
            System.out.println("El producto se ha actualizado correctamente");
            System.out.println(mostrarUnSoloProducto(3));
        }else{
            System.out.println("No se realizó la actualización");
        }

        System.out.println("****************************************************");
        System.out.println("                 EJERCICIO 3                       ");
        System.out.println("****************************************************");

        System.out.println("------------- CallableStatement   para crear un producto --------------");
        CallableStatement callableStatementCrearProducto = connection.prepareCall("{call sp_registrarProducto(?, ?, ?, ?, ?, ?, ?, ?)}");
        callableStatementCrearProducto.setString(1, "Frutas");
        callableStatementCrearProducto.setString(2, "P006");
        callableStatementCrearProducto.setString(3, "Papayas");
        callableStatementCrearProducto.setDouble(4, 1.50);
        callableStatementCrearProducto.setInt(5,6);
        callableStatementCrearProducto.setString(6, "Esta es una descripcion del producto P006.");
        callableStatementCrearProducto.setString(7, "2018-02-23 18:18:00");
        callableStatementCrearProducto.registerOutParameter(8, Types.INTEGER);
        callableStatementCrearProducto.executeQuery();
        System.out.println("Listados de productos con el nuevo producto registrado");
        mostrarTodosLosProductos();


        System.out.println("****************************************************");
        System.out.println("                 EJERCICIO 4                     ");
        System.out.println("****************************************************");

        System.out.println("------------- PreparedStatement  para Eliminar un producto --------------");
        System.out.println("Producto a eliminar");
        System.out.println(mostrarUnSoloProducto(15));
        String queryPreparedStatementEliminar = "DELETE FROM productos WHERE id = ?";
        PreparedStatement preparedStatementEliminarProducto = connection.prepareStatement(queryPreparedStatementEliminar);
        preparedStatementEliminarProducto.setInt(1, 15);
        int rsPreparedStatementEliminarProducto = preparedStatementEliminarProducto.executeUpdate();
        if (rsPreparedStatementEliminarProducto == 1){
            System.out.println("El producto se ha eliminado correctamente");
            mostrarTodosLosProductos();
        }else{
            System.out.println("No se realizó la eliminación");
        }

    }

    public static String mostrarUnSoloProducto(int id) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Crear Conexion
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dae_ep1", "root", "root");
        CallableStatement callableStatement = connection.prepareCall("{call sp_ListarProductoId(?)}");
        callableStatement.setInt(1, id);
        ResultSet resultSet = callableStatement.executeQuery();
        String mostrarProductoString = "";
        while (resultSet.next()){
            mostrarProductoString = "ID: " + resultSet.getString("id") +", NOMBRE: " + resultSet.getString("nombre") + ", PRECIO: " + resultSet.getString("precio_venta") + "\n";
        }

        return mostrarProductoString;
    }

    public static void mostrarTodosLosProductos() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Crear Conexion
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dae_ep1", "root", "root");

        String queryStatement = "SELECT * FROM productos";
        Statement statementListarProductos = connection.createStatement();
        ResultSet resultSetListarProductos = statementListarProductos.executeQuery(queryStatement);
        while (resultSetListarProductos.next()){
            System.out.println(
                    "ID : " + resultSetListarProductos.getString("id") + ", CATEGORIA: " + resultSetListarProductos.getString("categoria") + ", CODIGO: " + resultSetListarProductos.getString("codigo") + ", NOMBRE: " + resultSetListarProductos.getString("nombre") + ", PRECIO_VENTA: " + resultSetListarProductos.getString("precio_venta") + ", STOCK: " + resultSetListarProductos.getString("stock") + ", DESCRIPCION: " + resultSetListarProductos.getString("descripcion") + ", CREATED_AT: " + resultSetListarProductos.getString("created_at") + "\n"
            );
        }
    }

}
