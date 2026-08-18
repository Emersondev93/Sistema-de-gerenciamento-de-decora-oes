package modelo.servicos;

import modelo.dao.ClienteDao;
import modelo.dao.EnderecoDao;
import modelo.entidades.Cliente;
import modelo.entidades.Endereco;
import modelo.impl.ClienteDaoJDBC;
import modelo.impl.EnderecoDaoJDBC;
import java.util.List;

public class ClienteService {
    private ClienteDao clienteDao = new ClienteDaoJDBC();
    private EnderecoDao enderecoDao = new EnderecoDaoJDBC();

    public Cliente cadastrarCliente(String nome, String telefone, Endereco endereco) {
        enderecoDao.inserir(endereco);

        Cliente cliente = new Cliente(null, nome, telefone, endereco);

        clienteDao.inserir(cliente);

        return cliente;
    }

    public List<Cliente> listarClientes() {
        return clienteDao.buscarTodos();
    }

    public Cliente buscarPorId(int id) {
        return clienteDao.buscaPorId(id);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteDao.buscaPorNome(nome);
    }

    public Cliente buscarPorTelefone(String telefone) {
        return clienteDao.buscaPorTelefone(telefone);
    }

    public Cliente removerCliente(int id) {
        Cliente encontrado = clienteDao.buscaPorId(id);
        if (encontrado != null) {
            clienteDao.excluirPorId(id);
        }
        return encontrado;
    }

}
