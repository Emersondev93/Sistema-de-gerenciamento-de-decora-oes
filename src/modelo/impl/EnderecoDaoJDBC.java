package modelo.impl;

import db.Conexao;
import db.DbException;
import modelo.dao.EnderecoDao;
import modelo.entidades.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EnderecoDaoJDBC implements EnderecoDao {

    @Override
    public void inserir(Endereco endereco) {

        String sql = " INSERT INTO endereco (rua, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?) ";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, endereco.getRua());
            comando.setString(2, endereco.getNumero());
            comando.setString(3, endereco.getBairro());
            comando.setString(4, endereco.getCidade());
            comando.setString(5, endereco.getCep());

            comando.executeUpdate();
            try (ResultSet resultado = comando.getGeneratedKeys()){
                if (resultado.next()){
                    int id = resultado.getInt(1);
                    endereco.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public void atualizar(Endereco endereco) {

    }

    @Override
    public void excluirPorId(Integer id) {

    }

    @Override
    public void buscarPorId(Integer id) {

    }

    @Override
    public List<Endereco> buscarTodos() {
        return List.of();
    }
}
