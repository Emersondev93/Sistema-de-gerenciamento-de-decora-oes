package modelo.dao;

import modelo.entidades.Evento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EventoDao {
    void inserir(Evento evento);

    void atualizar(Evento evento);

    void excluirPorId(String edEvento);

    Evento buscaPorId(String idEvento);

    List<Evento> buscarTodos();

    String gerarProximoId();

    boolean existeEventoNaDataEHorario(LocalDate data, LocalTime horario);

}
