package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    private static Properties loadProperties() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("db.properties"));
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static Connection getConnection() {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                String usuario = props.getProperty("user");
                String senha = props.getProperty("password");

                return DriverManager.getConnection(url, usuario, senha);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }

    }

}
