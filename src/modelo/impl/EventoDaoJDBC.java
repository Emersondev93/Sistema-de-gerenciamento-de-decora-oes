package modelo.impl;

import db.Conexao;
import db.DbException;
import modelo.dao.EventoDao;
import modelo.entidades.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EventoDaoJDBC implements EventoDao {
    @Override
    public void inserir(Evento evento) {
        String sql = """
                INSERT INTO evento
                (id_evento, data, tema, valor, cliente_id)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, evento.getIdEvento());
            comando.setDate(2, java.sql.Date.valueOf(evento.getData())); //converte o LocalDate do Java para o tipo DATE que o MySQL entende.
            comando.setString(3, evento.getTema());
            comando.setDouble(4, evento.getValor());
            comando.setInt(5, evento.getCliente().getId());

            comando.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void atualizar(Evento evento) {

    }

    @Override
    public void excluirPorId(String edEvento) {

    }

    @Override
    public Evento buscaPorId(String idEvento) {
        return null;
    }

    @Override
    public List<Evento> buscartodos() {
        return List.of();
    }
}
