package aplicacao;

import db.ConfiguracaoJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.Cliente;
import modelo.entidades.Endereco;

public class TestePersistenciaJPA {
    public static void main(String[] args) {
        EntityManagerFactory fabrica =
                Persistence.createEntityManagerFactory(
                        "decoracoes",
                        ConfiguracaoJPA.carregarPropriedades()
                );
        EntityManager em = fabrica.createEntityManager();

        try {
            em.getTransaction().begin();

            //Cadastrar um endereço

            Endereco endereco = new Endereco();
            endereco.setRua("Rua Alfredo Gaspar");
            endereco.setNumero("250");
            endereco.setBairro("Centro");
            endereco.setCidade("Pará de Minas");
            endereco.setCep("35660-100");

            em.persist(endereco);

            //Cadastrar um cliente

            Cliente cliente = new Cliente();
            cliente.setNome("Jorge Clouney");
            cliente.setTelefone("37991452125");
            cliente.setEndereco(endereco);

            em.persist(cliente);

            em.getTransaction().commit();

            System.out.println("Cadastro realizado com sucesso");

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;
        } finally {
            em.close();
            fabrica.close();
        }
    }
}
