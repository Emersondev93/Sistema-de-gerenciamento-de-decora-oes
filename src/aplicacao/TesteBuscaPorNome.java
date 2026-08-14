package aplicacao;

import modelo.dao.ClienteDao;
import modelo.entidades.Cliente;
import modelo.impl.ClienteDaoJDBC;

import java.util.List;

public class TesteBuscaPorNome {

    public static void main(String[] args) {

        ClienteDao clienteDao = new ClienteDaoJDBC();

        List<Cliente> clientes = clienteDao.buscaPorNome("Joao da Silva");

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
            System.out.println("--------------------");
        }
    }
}