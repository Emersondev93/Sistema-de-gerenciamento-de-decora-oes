package aplicacao;

import db.ConfiguracaoJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.Cliente;

import java.lang.management.ManagementFactory;
import java.util.List;

public class TesteConsultaJPQL {
    public static void main(String[] args) {

        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("decoracoes", ConfiguracaoJPA.carregarPropriedades());
        EntityManager em = fabrica.createEntityManager();

        try {
            List<Cliente> clientes = em.createQuery(
                    "SELECT c FROM Cliente c JOIN FETCH c.endereco ",
                    Cliente.class
            ).getResultList();
            for (Cliente cliente : clientes) {
                System.out.println(cliente.getNome());
                System.out.println("Cidade: " + cliente.getEndereco().getCidade());
            }

        } finally {
            em.close();
            fabrica.close();
        }
    }
}
