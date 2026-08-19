package aplicacao;

import modelo.dao.ClienteDao;
import modelo.entidades.Cliente;
import modelo.impl.ClienteDaoJDBC;

import java.util.List;

public class TesteBuscarTodos {
    static void main(String[] args) {
        ClienteDao clienteDao = new ClienteDaoJDBC();

        List<Cliente> clientes = clienteDao.buscarTodos();
        for (Cliente cliente : clientes){
            System.out.println(cliente);
            System.out.println("-------------------------------");
        }
    }
}
