package com.fagr;

import java.sql.Connection;

import com.fagr.entity.User;
import com.fagr.utils.DatabaseConnection;

public class App {
    public static void main(String[] args) {
        User user = new User();
        // Obtener la instancia Singleton
        DatabaseConnection db = DatabaseConnection.getInstance();
        // Obtener la conexión
        Connection connection = db.getConnection();

        String[] columnas = user.getColumns();
        String[] filtrarColumnas = user.getFiltrarColumnas();
        String[] operadores = user.getOperadores();
        String[] filtrarValores = user.getFiltrarValores();

        String sql = db.ejecutarSelectSQL("users",  columnas, filtrarColumnas, filtrarValores, operadores);
        db.ejecutarConsulta(sql);

        // Cerrar la conexión
        db.closeConnection();
    }
}
