package modelo.impl;

import db.Conexao;
import db.DbException;
import modelo.dao.ClienteDao;
import modelo.entidades.Cliente;
import modelo.entidades.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoJDBC implements ClienteDao {

    @Override
    public void inserir(Cliente cliente) {

        String sql = "INSERT INTO cliente (nome, telefone, endereco_id) values (?, ?, ?)";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, cliente.getNome());
            comando.setString(2, cliente.getTelefone());
            comando.setInt(3, cliente.getEndereco().getId());

            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    int id = resultado.getInt(1);
                    cliente.setId(id);
                }

            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        String sql = """
                UPDATE cliente 
                SET nome = ?, telefone = ?
                WHERE id = ?""";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cliente.getNome());
            comando.setString(2, cliente.getTelefone());
            comando.setInt(3, cliente.getId());

            comando.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void excluirPorId(Integer id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection conexao = Conexao.getConnection(); PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            comando.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public Cliente buscaPorId(Integer id) {
        String sql = """
                  SELECT cliente.id, cliente.nome, cliente.telefone,
                  endereco.id AS endereco_id, 
                endereco.rua,
                  endereco.numero,
                  endereco.bairro,
                  endereco.cidade,
                  endereco.cep FROM cliente INNER JOIN endereco ON cliente.endereco_id = endereco.id
                  WHERE cliente.id = ?""";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {

                    Endereco endereco = new Endereco(
                            resultado.getInt("endereco_id"),
                            resultado.getString("rua"),
                            resultado.getString("numero"),
                            resultado.getString("bairro"),
                            resultado.getString("cidade"),
                            resultado.getString("cep")
                    );

                    Cliente cliente = new Cliente(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            resultado.getString("telefone"),
                            endereco
                    );

                    return cliente;
                }

            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return null;

    }

    @Override
    public List<Cliente> buscaPorNome(String nome) {
        String sql = """
                SELECT cliente.id, cliente.nome, cliente.telefone,
                 endereco.id AS endereco_id,
                 endereco.rua,
                 endereco.numero,
                 endereco.bairro,
                 endereco.cidade,
                 endereco.cep FROM cliente INNER JOIN endereco
                 ON cliente.endereco_id = endereco.id 
                 WHERE cliente.nome = ?""";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nome);

            try (ResultSet resultado = comando.executeQuery()) {
                List<Cliente> clientes = new ArrayList<>();
                while (resultado.next()) {
                    Endereco endereco = new Endereco(
                            resultado.getInt("endereco_id"),
                            resultado.getString("rua"),
                            resultado.getString("numero"),
                            resultado.getString("bairro"),
                            resultado.getString("cidade"),
                            resultado.getString("cep")
                    );

                    Cliente cliente = new Cliente(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            resultado.getString("telefone"),
                            endereco
                    );
                    clientes.add(cliente);
                }

                return clientes;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public Cliente buscaPorTelefone(String telefone) {
        String sql = """ 
                SELECT cliente.id, cliente.nome, cliente.telefone,
                endereco.id AS endereco_id,
                endereco.rua,
                endereco.numero,
                endereco.bairro,
                endereco.cidade,
                endereco.cep
                FROM cliente INNER JOIN endereco
                ON cliente.endereco_id = endereco.id
                WHERE cliente.telefone = ? """;

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, telefone);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    Endereco endereco = new Endereco(
                            resultado.getInt("endereco_id"),
                            resultado.getString("rua"),
                            resultado.getString("numero"),
                            resultado.getString("bairro"),
                            resultado.getString("cidade"),
                            resultado.getString("cep"));

                    return new Cliente(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            resultado.getString("telefone"),
                            endereco
                    );
                }
                return null;

            }


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public List<Cliente> buscarTodos() {
        String sql = """
                SELECT cliente.id, cliente.nome, cliente.telefone,
                endereco.id AS endereco_id,
                endereco.rua,
                endereco.numero,
                endereco.bairro,
                endereco.cidade,
                endereco.cep 
                FROM cliente INNER JOIN endereco ON cliente.endereco_id = endereco.id""";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            List<Cliente> clientes = new ArrayList<>();
            while (resultado.next()) {
                Endereco endereco = new Endereco(
                        resultado.getInt("endereco_id"),
                        resultado.getString("rua"),
                        resultado.getString("numero"),
                        resultado.getString("bairro"),
                        resultado.getString("cidade"),
                        resultado.getString("cep")
                );

                Cliente cliente = new Cliente(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("telefone"),
                        endereco
                );

                clientes.add(cliente);
            }

            return clientes;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
