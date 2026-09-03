package modelo.dao;

import modelo.entidades.Cliente;

import java.sql.Connection;
import java.util.List;

public interface ClienteDao {
    void inserir(Cliente cliente, Connection conexao);

    void atualizar(Cliente cliente, Connection conexao);

    void excluirPorId(Integer id, Connection conexao);

    Cliente buscaPorId(Integer id);

    List<Cliente> buscaPorNome(String nome);

    Cliente buscaPorTelefone(String telefone);

    List<Cliente> buscarTodos();
}



