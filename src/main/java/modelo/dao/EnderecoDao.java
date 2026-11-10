package modelo.dao;

import modelo.entidades.Endereco;

import java.sql.Connection;
import java.util.List;

public interface EnderecoDao {
    void inserir (Endereco endereco, Connection conexao);

    void atualizar (Endereco endereco, Connection conexao);

    void excluirPorId (Integer id, Connection conexao);

    Endereco buscarPorId (Integer id);

    List<Endereco> buscarTodos();
}
