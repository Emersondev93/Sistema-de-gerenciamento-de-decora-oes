package modelo.servicos;

import modelo.dao.EventoDao;
import modelo.entidades.Cliente;
import modelo.entidades.Evento;
import excecoes.DominioDeExcecao;
import modelo.impl.EventoDaoJDBC;

import java.time.LocalDate;
import java.util.List;

public class EventoService {

    private EventoDao eventoDao = new EventoDaoJDBC();

    public Evento cadastrarEvento(LocalDate data, String tema, double valor, Cliente cliente) throws DominioDeExcecao {

        if (valor <= 0) {
            throw new DominioDeExcecao("O valor do evento deve ser maior que zero.");
        }

        String idEvento = eventoDao.gerarProximoId();

        Evento novoEvento = new Evento(idEvento, data, tema, valor, cliente);

        eventoDao.inserir(novoEvento);

        return novoEvento;
    }

    public List<Evento> listarEventos() {
        return eventoDao.buscarTodos();
    }

    public void atualizarEvento(Evento evento) throws DominioDeExcecao {

        if (evento.getValor() <= 0) {
            throw new DominioDeExcecao(
                    "O valor do evento deve ser maior que zero."
            );
        }

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
}
