package aplicacao;

import modelo.entidades.Evento;
import modelo.impl.EventoDaoJDBC;

public class TesteBuscaEvento {
    public static void main(String[] args) {

        EventoDaoJDBC eventoDao = new EventoDaoJDBC();

        Evento evento = eventoDao.buscaPorId("EV001");

        if (evento != null) {
            System.out.println(evento);
        } else {
            System.out.println("Evento não encontrado!");
        }
    }
}
