package modelo.servicos;

import excecoes.DominioDeExcecao;
import modelo.dao.ClienteDao;
import modelo.dao.EventoDao;
import modelo.entidades.Cliente;
import modelo.entidades.Evento;
import modelo.impl.ClienteDaoJDBC;
import modelo.impl.EventoDaoJDBC;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventoService {

    private EventoDao eventoDao = new EventoDaoJDBC();
    private ClienteDao clienteDao = new ClienteDaoJDBC();

    public Evento cadastrarEvento(LocalDate data, LocalTime horario, String tema, double valor, Cliente cliente) throws DominioDeExcecao {

        if (valor <= 0) {
            throw new DominioDeExcecao("O valor do evento deve ser maior que zero.");
        }

        if (data == null) {
            throw new DominioDeExcecao("A data do evento é obrigatória.");
        }

        if (horario == null) {
            throw new DominioDeExcecao("O horário do evento é obrigatório.");
        }

        if (tema == null || tema.isBlank()) {
            throw new DominioDeExcecao("O tema do evento é obrigatório.");
        }

        if (cliente == null) {
            throw new DominioDeExcecao("O cliente do evento é obrigatório.");
        }

        if (clienteDao.buscaPorId(cliente.getId()) == null) {
            throw new DominioDeExcecao("O cliente informado não existe.");
        }

        String idEvento = eventoDao.gerarProximoId();

        Evento novoEvento = new Evento(idEvento, data, horario, tema, valor, cliente);

        eventoDao.inserir(novoEvento);

        return novoEvento;
    }

    public List<Evento> listarEventos() {
        return eventoDao.buscarTodos();
    }

    public void atualizarEvento(Evento evento) throws DominioDeExcecao {

        if (evento == null) {
            throw new DominioDeExcecao("O evento é obrigatório.");
        }
        if (evento.getIdEvento() == null || evento.getIdEvento().isBlank()){
            throw new DominioDeExcecao("O ID do evento é obrigatório.");
        }
        if (eventoDao.buscaPorId(evento.getIdEvento()) == null) {
            throw new DominioDeExcecao("O evento informado não existe.");
        }
        if (evento.getCliente() == null) {
            throw new DominioDeExcecao("O cliente do evento é obrigatório.");
        }
        if(evento.getCliente().getId() == null){
            throw new DominioDeExcecao("O ID do cliente é obrigatório.");
        }
        if (clienteDao.buscaPorId(evento.getCliente().getId()) == null) {
            throw new DominioDeExcecao("O cliente informado não existe.");
        }
        if (evento.getValor() <= 0) {
            throw new DominioDeExcecao("O valor do evento deve ser maior que zero.");
        }
        if (evento.getData() == null) {
            throw new DominioDeExcecao("A data do evento é obrigatória.");
        }
        if (evento.getHorario() == null) {
            throw new DominioDeExcecao("O horário do evento é obrigatório.");
        }
        if (evento.getTema() == null || evento.getTema().isBlank()) {
            throw new DominioDeExcecao("O tema do evento é obrigatório.");
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

    public boolean existeEventoNaDataEHorario(LocalDate data, LocalTime horario) {
        return eventoDao.existeEventoNaDataEHorario(data, horario);
    }

    public boolean existeOutroEventoNaDataEHorario(String idEvento, LocalDate data, LocalTime horario) {
        return eventoDao.existeOutroEventoNaDataEHorario(idEvento, data, horario);
    }


}
