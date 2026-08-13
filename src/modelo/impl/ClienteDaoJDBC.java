package modelo.impl;

import db.Conexao;
import db.DbException;
import modelo.dao.ClienteDao;
import modelo.entidades.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClienteDaoJDBC implements ClienteDao {

    @Override
    public void inserir(Cliente cliente) {

        String sql = "INSERT INTO cliente (nome, telefone, endereco_id) values (?, ?, ?)";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)){

            comando.setString(1, cliente.getNome());
            comando.setString(2, cliente.getTelefone());
            comando.setInt(3, cliente.getEndereco().getId());

            comando.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void atualizar(Cliente cliente) {

    }

    @Override
    public void excluirPorId(Integer id) {

    }

    @Override
    public Cliente buscarPorId(Integer id) {
        return null;
    }

    @Override
    public List<Cliente> buscarTodos() {
        return List.of();
    }
}
