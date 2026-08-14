package aplicacao;

import jdk.swing.interop.SwingInterOpUtils;
import modelo.dao.ClienteDao;
import modelo.dao.EnderecoDao;
import modelo.entidades.Cliente;
import modelo.entidades.Endereco;
import modelo.impl.ClienteDaoJDBC;
import modelo.impl.EnderecoDaoJDBC;

public class TesteCliente {
    public static void main(String[] args) {
        Endereco endereco = new Endereco(
                null,
                "Rua das Flores",
                "100",
                "centro",
                "Pará de Minas",
                "35660-000"
        );

        EnderecoDao enderecoDao = new EnderecoDaoJDBC();

        enderecoDao.inserir(endereco);

        Cliente cliente = new Cliente(
                null,
                "Joao da Silva",
                "99999-1111",
                endereco
        );

        ClienteDao clienteDao = new ClienteDaoJDBC();

        clienteDao.inserir(cliente);

        System.out.println("Cliente cadastrado !");
        System.out.println("ID do cliente: " + cliente.getId());
        System.out.println("ID do endereço: " + endereco.getId());

        Cliente clienteEncontrado = clienteDao.buscaPorId(cliente.getId());
        System.out.println("\n=============CLIENTE ENCONTRADO==============");
        System.out.println(clienteEncontrado);
    }
}