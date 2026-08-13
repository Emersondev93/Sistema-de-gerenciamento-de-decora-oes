package modelo.servicos;

import modelo.entidades.Cliente;
import modelo.entidades.Evento;
import excecoes.DominioDeExcecao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoService {
    private List<Evento> eventos = new ArrayList<>();
    private int idEventoSequencial = 1;

    public Evento cadastrarEvento(LocalDate data, String tema, double valor, Cliente cliente) throws DominioDeExcecao {

        if (valor <= 0) {
            throw new DominioDeExcecao("O valor do evento deve ser maior que zero.");
        }

        String idEvento = "ev" + idEventoSequencial;
        idEventoSequencial++;
        Evento novoEvento = new Evento(idEvento, data, tema, valor, cliente);
        eventos.add(novoEvento);
        return novoEvento;
    }

    public List<Evento> listarEventos() {
        return new ArrayList<>(eventos);
    }

    public Evento buscarPorId(String id) {
        for (Evento e : eventos) {
            if (e.getIdEvento().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public Evento removerEvento(String id) {
        Evento encontrado = buscarPorId(id);
        if (encontrado != null) {
            eventos.remove(encontrado);
        }
        return encontrado;
    }


}
