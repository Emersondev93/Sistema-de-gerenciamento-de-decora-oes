package aplicacao;

import db.ConfiguracaoJPA;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TesteJPA {
    public static void main(String[] args) {

        EntityManagerFactory fabrica =
                Persistence.createEntityManagerFactory(
                        "decoracoes",
                        ConfiguracaoJPA.carregarPropriedades()
                );

        System.out.println("JPA inicializado com sucesso!");

        fabrica.close();
    }
}
