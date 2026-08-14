package aplicacao;

import modelo.dao.ClienteDao;
import modelo.entidades.Cliente;
import modelo.impl.ClienteDaoJDBC;

public class TesteBuscaPorId {
    public static void main(String[] args) {
        ClienteDao clienteDao = new ClienteDaoJDBC();

        Cliente cliente = clienteDao.buscaPorId(1);

        if(cliente != null){
            System.out.println(cliente);
        }else {
            System.out.println("Cliente não encontrado.");
        }
    }
}
