package modelo.dao;

import modelo.entidades.Cliente;

import java.util.List;

public interface ClienteDao {
    void inserir(Cliente cliente);

    void atualizar(Cliente cliente);

    void excluirPorId(Integer id);

    Cliente buscaPorId(Integer id);

    List<Cliente> buscaPorNome(String nome);

    Cliente buscaPortelefone(String telefone);

    List<Cliente> buscarTodos();
}



