package aplicacao;

import db.ConfiguracaoJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.Cliente;

public class TesteBuscarClientePorId {
    public static void main(String[] args) {

        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory(
                "decoracoes", ConfiguracaoJPA.carregarPropriedades());

        EntityManager em = fabrica.createEntityManager();

        try{
            Cliente cliente = em.find(Cliente.class, 4 );

            if (cliente != null){
                System.out.println(cliente);
            }else{
                System.out.println("Cliente não encontrado.");
            }
        }finally{
            fabrica.close();
            em.close();
        }
    }
}
