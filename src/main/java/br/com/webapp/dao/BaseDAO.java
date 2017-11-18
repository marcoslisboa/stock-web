package br.com.webapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class BaseDAO {

    private static Connection connection = null;

    protected Connection getConnection() {

        String url = "jdbc:postgresql://localhost:5432/estoque";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");

        try {
            connection = DriverManager.getConnection(url, props);
            return connection;
        } catch (SQLException e) {
            log.log(Level.WARNING, e.getMessage(), e);
            log.warning("Falha ao conectar.");
        }

        return null;
    }

    protected void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.warning(e.getMessage());
            }
        }
    }
}
