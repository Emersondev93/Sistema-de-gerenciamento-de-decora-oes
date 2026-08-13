package modelo.dao;

import modelo.entidades.Cliente;

import java.util.List;

public interface ClienteDao {
    void inserir(Cliente cliente);

    void atualizar(Cliente cliente);

    void excluirPorId(Integer id);

    Cliente buscarPorId(Integer id);

    List<Cliente> buscarTodos();
}



