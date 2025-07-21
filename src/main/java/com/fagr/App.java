package com.fagr;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class App {
    public static void main(String[] args) {
        // Obtener la instancia Singleton
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        // Obtener la conexión
        Connection connection = dbConnection.getConnection();

        // Llamar al método para mostrar usuarios
        ejecutarSelect(connection, "SELECT * FROM usuarios");

        // Cerrar la conexión
        dbConnection.closeConnection();
    }

    public static void ejecutarSelect(Connection connection, String query) {
        try (Statement statement = (Statement) connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    String value = resultSet.getString(i);
                    System.out.print(columnName + ": " + value + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ejemplo de uso en el main:
    // ejecutarSelect(connection, "SELECT * FROM usuarios");
}
