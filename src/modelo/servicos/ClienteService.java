package modelo.servicos;

import db.Conexao;
import db.DbException;
import excecoes.DominioDeExcecao;
import modelo.dao.ClienteDao;
import modelo.dao.EnderecoDao;
import modelo.entidades.Cliente;
import modelo.entidades.Endereco;
import modelo.impl.ClienteDaoJDBC;
import modelo.impl.EnderecoDaoJDBC;

import java.sql.Connection;
import java.util.List;

public class ClienteService {
    private ClienteDao clienteDao;
    private EnderecoDao enderecoDao;

    public ClienteService(){
        clienteDao = new ClienteDaoJDBC();
        enderecoDao = new EnderecoDaoJDBC();
    }

    public Cliente cadastrarCliente(String nome, String telefone, Endereco endereco) throws DominioDeExcecao {


        if (buscarPorTelefone(telefone) != null) {
            throw new DominioDeExcecao("Já existe um cliente cadastrado com este telefone.");
        }

        Cliente cliente = new Cliente(null, nome, telefone, endereco);

        try (Connection conexao = Conexao.getConnection()){

            conexao.setAutoCommit(false);

            try{
                enderecoDao.inserir(endereco, conexao);
                clienteDao.inserir(cliente, conexao);

                conexao.commit();
            } catch (Exception e){
                conexao.rollback();
                throw e;
            }
        }catch (Exception e){
            throw new DbException((e.getMessage()));
        }
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

        try (Connection conexao = Conexao.getConnection()){

            conexao.setAutoCommit(false);

            try{
                clienteDao.atualizar(cliente, conexao);
                enderecoDao.atualizar(cliente.getEndereco(), conexao);

                conexao.commit();

            } catch (Exception e){
                conexao.rollback();
                throw e;
            }

        } catch (Exception e){
            throw  new DbException(e.getMessage());
        }

    }

    public Cliente removerCliente(int id) {

        Cliente encontrado = clienteDao.buscaPorId(id);

        if (encontrado != null) {

            Integer idEndereco = encontrado.getEndereco().getId();

            try (Connection conexao = Conexao.getConnection()){

                conexao.setAutoCommit(false);

                try {

                    clienteDao.excluirPorId(id, conexao);
                    enderecoDao.excluirPorId(idEndereco, conexao);

                    conexao.commit();

                }catch (Exception e){
                    conexao.rollback();
                    throw e;
                }

            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
        return encontrado;
    }

}
