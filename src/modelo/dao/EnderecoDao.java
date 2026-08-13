package modelo.dao;

import modelo.entidades.Endereco;

import java.util.List;

public interface EnderecoDao {
    void inserir (Endereco endereco);

    void atualizar (Endereco endereco);

    void excluirPorId (Integer id);

    void buscarPorId (Integer id);

    List<Endereco> buscarTodos();
}
