package modelo.servicos;

import excecoes.DominioDeExcecao;
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

    public Cliente cadastrarCliente(String nome, String telefone, Endereco endereco) throws DominioDeExcecao {


        if (buscarPorTelefone(telefone) != null) {
            throw new DominioDeExcecao("Já existe um cliente cadastrado com este telefone.");
        }

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

    public void atualizarCliente(Cliente cliente)  throws DominioDeExcecao{
        Cliente clienteComMesmoTelefone = clienteDao.buscaPorTelefone(cliente.getTelefone());

        if(clienteComMesmoTelefone != null && !clienteComMesmoTelefone.getId().equals(cliente.getId())){
            throw new DominioDeExcecao(
                    "Já existe outro cliente cadastrado com este telefone."
            );
        }

        clienteDao.atualizar(cliente);
        enderecoDao.atualizar(cliente.getEndereco());

    }

    public Cliente removerCliente(int id) {
        Cliente encontrado = clienteDao.buscaPorId(id);

        if (encontrado != null) {
            Integer idEndereco = encontrado.getEndereco().getId();

            clienteDao.excluirPorId(id);

            enderecoDao.excluirPorId(idEndereco);
        }
        return encontrado;
    }

}
