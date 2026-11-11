
package db;

import java.util.Properties;

public class ConfiguracaoJPA {

    public static Properties carregarPropriedades() {

        Properties propriedades = Conexao.loadProperties();

        Properties jpa = new Properties();

        jpa.setProperty(
                "jakarta.persistence.jdbc.driver",
                "com.mysql.cj.jdbc.Driver"
        );

        jpa.setProperty(
                "jakarta.persistence.jdbc.url",
                propriedades.getProperty("dburl")
        );

        jpa.setProperty(
                "jakarta.persistence.jdbc.user",
                propriedades.getProperty("user")
        );

        jpa.setProperty(
                "jakarta.persistence.jdbc.password",
                propriedades.getProperty("password")
        );

        jpa.setProperty("hibernate.show_sql", "true");
        jpa.setProperty("hibernate.format_sql", "true");

        return jpa;
    }
}
