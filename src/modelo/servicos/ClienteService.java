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

        if (nome == null || nome.trim().isEmpty()) {
            throw new DominioDeExcecao("O nome do cliente não pode ser vazio.");
        }

        if (buscarPorTelefone(telefone) != null) {
            throw new DominioDeExcecao("Já existe um cliente cadastrado com este telefone.");
        }

        if (endereco == null) {
            throw new DominioDeExcecao("O endereço do cliente não pode ser vazio.");
        }

        if (endereco.getRua() == null || endereco.getRua().trim().isEmpty()) {
            throw new DominioDeExcecao("A rua não pode ser vazia.");
        }

        if (endereco.getNumero() == null || endereco.getNumero().trim().isEmpty()) {
            throw new DominioDeExcecao("O número não pode ser vazio.");
        }

        if (endereco.getBairro() == null || endereco.getBairro().trim().isEmpty()) {
            throw new DominioDeExcecao("O bairro não pode ser vazio.");
        }

        if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
            throw new DominioDeExcecao("A cidade não pode ser vazia.");
        }

        if (endereco.getCep() == null || endereco.getCep().trim().isEmpty()) {
            throw new DominioDeExcecao("O CEP não pode ser vazio.");
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

    public void atualizarCliente(Cliente cliente) {

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
