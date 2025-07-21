package com.fagr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;

public class DatabaseConnection {
    // Instancia única
    private static volatile DatabaseConnection instance;
    private Connection connection;

    // Credenciales de la base de datos (deberías usar un archivo de configuración
    // en producción)
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

    // Método para ejecutar una consulta de seleccion sin filtros.
    public String ejecutarSelectSQL(String table, String[] columns) {
        return ejecutarSelectSQL(table, columns, null, null, null);
    }

    // Método sobrecargado para ejecutar una consulta de selección con filtros
    // genéricos
    public String ejecutarSelectSQL(String table, String[] columns, String[] filterColumns, String[] filterValues,
            String[] operators) {
        StringBuilder sql = new StringBuilder();

        // Construir la parte SELECT
        if (columns == null || columns.length == 0) {
            sql.append("SELECT * FROM ").append(table);
        } else {
            sql.append("SELECT ");
            for (int i = 0; i < columns.length; i++) {
                sql.append(columns[i].trim());
                if (i < columns.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(" FROM ").append(table);
        }

        // Construir la parte WHERE si hay filtros
        if (filterColumns != null && filterValues != null && filterColumns.length > 0 && filterValues.length > 0) {
            sql.append(" WHERE ");

            int minLength = Math.min(filterColumns.length, filterValues.length);

            for (int i = 0; i < minLength; i++) {
                String operator = "=";
                if (operators != null && i < operators.length) {
                    operator = operators[i];
                }

                sql.append(filterColumns[i].trim()).append(" ").append(operator).append(" ");

                // Agregar comillas simples para valores de texto (esto es básico, en producción
                // necesitarías prepared statements)
                if (isNumeric(filterValues[i])) {
                    sql.append(filterValues[i]);
                } else {
                    sql.append("'").append(filterValues[i].replace("'", "''")).append("'");
                }

                if (i < minLength - 1) {
                    sql.append(" AND ");
                }
            }
        }

        System.out.println("Consulta SQL generada: " + sql.toString());
        return sql.toString();
    }

    // Método para crear filtros más complejos con OR
    public String ejecutarSelectSQLConOR(String table, String[] columns, String[] filterColumns, String[] filterValues,
            String[] operators) {
        StringBuilder sql = new StringBuilder();

        // Construir la parte SELECT
        if (columns == null || columns.length == 0) {
            sql.append("SELECT * FROM ").append(table);
        } else {
            sql.append("SELECT ");
            for (int i = 0; i < columns.length; i++) {
                sql.append(columns[i].trim());
                if (i < columns.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(" FROM ").append(table);
        }

        // Construir la parte WHERE con OR
        if (filterColumns != null && filterValues != null && filterColumns.length > 0) {
            sql.append(" WHERE ");

            int minLength = Math.min(filterColumns.length, filterValues.length);

            for (int i = 0; i < minLength; i++) {
                String operator = (operators != null && i < operators.length) ? operators[i] : "=";

                sql.append(filterColumns[i].trim()).append(" ").append(operator).append(" ");

                if (isNumeric(filterValues[i])) {
                    sql.append(filterValues[i]);
                } else {
                    sql.append("'").append(filterValues[i].replace("'", "''")).append("'");
                }

                if (i < minLength - 1) {
                    sql.append(" OR ");
                }
            }
        }

        System.out.println("Consulta SQL generada: " + sql.toString());
        return sql.toString();
    }

    // Método simplificado para filtros comunes
    public String buscarPorCampo(String table, String campo, String valor) {
        String[] columns = {};
        String[] filterColumns = { campo };
        String[] filterValues = { valor };
        return ejecutarSelectSQL(table, columns, filterColumns, filterValues, null);
    }

    // Método para búsqueda con LIKE
    public String buscarConLike(String table, String[] columns, String campo, String patron) {
        StringBuilder sql = new StringBuilder();

        if (columns == null || columns.length == 0) {
            sql.append("SELECT * FROM ").append(table);
        } else {
            sql.append("SELECT ");
            for (int i = 0; i < columns.length; i++) {
                sql.append(columns[i].trim());
                if (i < columns.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(" FROM ").append(table);
        }

        sql.append(" WHERE ").append(campo).append(" LIKE '%").append(patron.replace("'", "''")).append("%'");

        System.out.println("Consulta SQL generada: " + sql.toString());
        return sql.toString();
    }

    // Método auxiliar para ejecutar y mostrar resultados de cualquier consulta
    // SELECT
    public void ejecutarConsulta(String sql) {
        try (java.sql.Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(resultSet.getString(i));
                    if (i < columnCount)
                        row.append(" | ");
                }
                System.out.println("Resultado: " + row);
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar consulta: " + e.getMessage());
        }
    }

    // Método auxiliar para verificar si un valor es numérico
    private boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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