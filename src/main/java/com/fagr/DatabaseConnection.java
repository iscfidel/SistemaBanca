package com.fagr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Instancia única
    private static volatile DatabaseConnection instance;
    private Connection connection;
    
    // Credenciales de la base de datos (deberías usar un archivo de configuración en producción)
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=PersonalActivity";
    private final String username = "iscfidel";
    private final String password = "metroxyz";

    // Constructor privado para evitar instanciación
    private DatabaseConnection() {
        try {
            // Registrar el driver (no necesario desde JDBC 4.0, pero buena práctica)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión establecida con SQL Server");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al establecer conexión: " + e.getMessage());
        }
    }

    // Método para obtener la instancia (thread-safe)
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Método para obtener la conexión
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                synchronized (DatabaseConnection.class) {
                    if (connection == null || connection.isClosed()) {
                        connection = DriverManager.getConnection(url, username, password);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar/conectar: " + e.getMessage());
        }
        return connection;
    }

    // Método para cerrar la conexión
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}