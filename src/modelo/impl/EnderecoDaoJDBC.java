package modelo.impl;

import db.Conexao;
import db.DbException;
import modelo.dao.EnderecoDao;
import modelo.entidades.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
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
        String sql = """
                UPDATE endereco
                SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ?
                WHERE id = ?""";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, endereco.getRua());
            comando.setString(2, endereco.getNumero());
            comando.setString(3, endereco.getBairro());
            comando.setString(4, endereco.getCidade());
            comando.setString(5, endereco.getCep());
            comando.setInt(6, endereco.getId());

            comando.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void excluirPorId(Integer id) {
        String sql = "DELETE FROM endereco WHERE id = ?";

        try (Connection conexao = Conexao.getConnection(); PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            comando.executeUpdate();


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Endereco buscarPorId(Integer id) {

        String sql = "SELECT * FROM endereco WHERE id = ?";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {

                    return new Endereco(
                            resultado.getInt("id"),
                            resultado.getString("rua"),
                            resultado.getString("numero"),
                            resultado.getString("bairro"),
                            resultado.getString("cidade"),
                            resultado.getString("cep")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }


    }

    @Override
    public List<Endereco> buscarTodos() {


        String sql = "SELECT * FROM endereco";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            List<Endereco> enderecos = new ArrayList<>();

            while (resultado.next()) {
                Endereco endereco = new Endereco(

                        resultado.getInt("id"),
                        resultado.getString("rua"),
                        resultado.getString("numero"),
                        resultado.getString("bairro"),
                        resultado.getString("cidade"),
                        resultado.getString("cep")

                );

                enderecos.add(endereco);
            }

            return enderecos;

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        }

    }

}
