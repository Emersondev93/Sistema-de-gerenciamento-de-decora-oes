package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
    private static Connection conexao = null;


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
        if (conexao == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                String usuario = props.getProperty("user");
                String senha = props.getProperty("password");

                conexao = DriverManager.getConnection(url, usuario, senha);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conexao;
    }

    public static void closeConnection() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }

        }
    }

}
