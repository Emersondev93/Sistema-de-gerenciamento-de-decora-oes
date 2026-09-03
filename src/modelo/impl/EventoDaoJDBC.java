package modelo.impl;

import db.Conexao;
import db.DbException;
import modelo.dao.EventoDao;
import modelo.entidades.Cliente;
import modelo.entidades.Endereco;
import modelo.entidades.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventoDaoJDBC implements EventoDao {
    @Override
    public void inserir(Evento evento) {
        String sql = """
                INSERT INTO evento
                (id_evento, data, horario, tema, valor, cliente_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, evento.getIdEvento());
            comando.setDate(2, java.sql.Date.valueOf(evento.getData())); //converte o LocalDate do Java para o tipo DATE que o MySQL entende.
            comando.setTime(3, java.sql.Time.valueOf(evento.getHorario())); //converte o LocalTime do Java para o tipo TIME que o MySQL entende.
            comando.setString(4, evento.getTema());
            comando.setDouble(5, evento.getValor());
            comando.setInt(6, evento.getCliente().getId());

            comando.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void atualizar(Evento evento) {

        String sql = """
                UPDATE evento
                SET data = ?, horario = ?, tema = ?, valor = ?, cliente_id = ?
                WHERE id_evento = ?
                """;

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setDate(1, java.sql.Date.valueOf(evento.getData()));
            comando.setTime(2, java.sql.Time.valueOf(evento.getHorario()));
            comando.setString(3, evento.getTema());
            comando.setDouble(4, evento.getValor());
            comando.setInt(5, evento.getCliente().getId());
            comando.setString(6, evento.getIdEvento());

            comando.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void excluirPorId(String idEvento) {

        String sql = "DELETE FROM evento WHERE id_evento = ?";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idEvento);

            comando.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Evento buscaPorId(String idEvento) {
        String sql = """
                SELECT evento.id_evento,
                evento.data,
                evento.horario,
                evento.tema,
                evento.valor,
                cliente.id AS cliente_id,
                cliente.nome,
                cliente.telefone,
                endereco.id AS endereco_id,
                endereco.rua,
                endereco.numero,
                endereco.bairro,
                endereco.cidade,
                endereco.cep
                FROM evento 
                    INNER JOIN cliente 
                    ON evento.cliente_id = cliente.id
                INNER JOIN  endereco
                ON cliente.endereco_id = endereco.id
                WHERE evento.id_evento = ?
                """;
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idEvento);

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
                            resultado.getInt("cliente_id"),
                            resultado.getString("nome"),
                            resultado.getString("telefone"),
                            endereco
                    );

                    return new Evento(
                            resultado.getString("id_evento"),
                            resultado.getDate("data").toLocalDate(),
                            resultado.getTime("horario").toLocalTime(),
                            resultado.getString("tema"),
                            resultado.getDouble("valor"),
                            cliente
                    );
                }
                return null;

            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public List<Evento> buscarTodos() {

        String sql = """
                SELECT evento.id_evento,
                evento.data,
                evento.horario,
                evento.tema,
                evento.valor,
                cliente.id AS cliente_id,
                cliente.nome,
                cliente.telefone,
                endereco.id AS endereco_id,
                endereco.rua,
                endereco.numero,
                endereco.bairro,
                endereco.cidade,
                endereco.cep
                FROM evento INNER JOIN cliente
                ON evento.cliente_id = cliente.id
                INNER JOIN endereco 
                ON cliente.endereco_id = endereco.id """;

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            List<Evento> eventos = new ArrayList<>();

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
                        resultado.getInt("cliente_id"),
                        resultado.getString("nome"),
                        resultado.getString("telefone"),
                        endereco
                );

                Evento evento = new Evento(
                        resultado.getString("id_evento"),
                        resultado.getDate("data").toLocalDate(),
                        resultado.getTime("horario").toLocalTime(),
                        resultado.getString("tema"),
                        resultado.getDouble("valor"),
                        cliente
                );

                eventos.add(evento);

            }

            return eventos;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public String gerarProximoId() {
        String sql = """
                SELECT MAX(CAST(SUBSTRING(id_evento, 3) AS UNSIGNED))
                FROM evento
                """;
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            if (resultado.next()) {
                int maiorId = resultado.getInt(1);

                if (resultado.wasNull()) {
                    maiorId = 0;
                }
                return String.format("EV%03d", maiorId + 1);

            }

            return "EV))!";

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public boolean existeEventoNaDataEHorario(LocalDate data, LocalTime horario) {
        String sql = """
                SELECT COUNT(*) FROM evento 
                WHERE data = ?
                AND horario = ?
                """;

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setDate(1, java.sql.Date.valueOf(data));
            comando.setTime(2, java.sql.Time.valueOf(horario));

            try (ResultSet resultado = comando.executeQuery()) {

                resultado.next();

                return resultado.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public boolean existeOutroEventoNaDataEHorario(String idEvento, LocalDate data, LocalTime horario) {
        String sql = """
                SELECT COUNT(*) FROM evento
                WHERE data = ?
                AND horario = ?
                AND id_evento <> ?
                """;

        try (Connection conexao = Conexao.getConnection();
            PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setDate(1, java.sql.Date.valueOf(data));
            comando.setTime(2, java.sql.Time.valueOf(horario));
            comando.setString(3, idEvento);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt(1) > 0;
                }
            }

            return false;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
