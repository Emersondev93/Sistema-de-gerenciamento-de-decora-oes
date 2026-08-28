package modelo.servicos;

import modelo.dao.EventoDao;
import modelo.entidades.Cliente;
import excecoes.DominioDeExcecao;
import modelo.entidades.Evento;
import modelo.impl.EventoDaoJDBC;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventoService {

    private EventoDao eventoDao = new EventoDaoJDBC();

    public Evento cadastrarEvento(LocalDate data, LocalTime horario, String tema, double valor, Cliente cliente) throws DominioDeExcecao {

        String idEvento = eventoDao.gerarProximoId();

        Evento novoEvento = new Evento(idEvento, data, horario, tema, valor, cliente);

        eventoDao.inserir(novoEvento);

        return novoEvento;
    }

    public List<Evento> listarEventos() {
        return eventoDao.buscarTodos();
    }

    public void atualizarEvento(Evento evento) throws DominioDeExcecao {

        eventoDao.atualizar(evento);
    }

    public Evento buscarPorId(String idEvento) {
        return eventoDao.buscaPorId(idEvento);
    }

    public Evento removerEvento(String idEvento) {

        Evento encontrado = buscarPorId(idEvento);

        if (encontrado != null) {
            eventoDao.excluirPorId(idEvento);
        }

        return encontrado;
    }

    public boolean existeEventoNaDataEHorario(LocalDate data, LocalTime horario){
        return eventoDao.existeEventoNaDataEHorario(data, horario);
    }

    public boolean existeOutroEventoNaDataEHorario(String idEvento, LocalDate data, LocalTime horario){
        return eventoDao.existeOutroEventoNaDataEHorario(idEvento, data, horario);
    }


}
