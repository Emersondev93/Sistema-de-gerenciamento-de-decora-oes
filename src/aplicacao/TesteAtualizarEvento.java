package aplicacao;

import excecoes.DominioDeExcecao;
import modelo.entidades.Evento;
import modelo.servicos.EventoService;

public class TesteAtualizarEvento {

    public static void main(String[] args) {

        EventoService eventoService = new EventoService();
        try {
            Evento evento = eventoService.buscarPorId("EV001");

            if (evento != null) {

                System.out.println("Antes da alteração:");
                System.out.println(evento);

                evento.setTema("Casamento de João");
                evento.setValor(4000.00);

                eventoService.atualizarEvento(evento);

                System.out.println();
                System.out.println("Evento atualizado com sucesso!");

                Evento atualizado = eventoService.buscarPorId("EV001");

                System.out.println();
                System.out.println("Depois da alteração:");
                System.out.println(atualizado);

            } else {
                System.out.println("Evento EV001 não encontrado!");
            }
        } catch (DominioDeExcecao e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}