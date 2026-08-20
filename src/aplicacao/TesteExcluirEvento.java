package aplicacao;

import modelo.entidades.Evento;
import modelo.servicos.EventoService;

public class TesteExcluirEvento {
    public static void main(String[] args) {
        EventoService eventoService = new EventoService();

        Evento removido = eventoService.removerEvento("EV001");

        if (removido != null) {
            System.out.println("Evento excluído com sucesso!");
            System.out.println("ID: " + removido.getIdEvento());
        } else {
            System.out.println("Evento não encontrado!");
        }

    }
}
