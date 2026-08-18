package aplicacao;

import com.sun.tools.javac.Main;
import modelo.dao.ClienteDao;
import modelo.entidades.Cliente;
import modelo.impl.ClienteDaoJDBC;

public class TesteBuscaPorTelefone {
    static void main(String[] args) {
        ClienteDao clienteDao = new ClienteDaoJDBC();

        Cliente cliente = clienteDao.buscaPorTelefone("99999-1111");

        if (cliente != null) {
            System.out.println(cliente);
        } else {
            System.out.println("Cliente não encontrado.");
        }

    }
}
