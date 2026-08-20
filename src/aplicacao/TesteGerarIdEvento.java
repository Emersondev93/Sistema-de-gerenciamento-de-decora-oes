package aplicacao;

import excecoes.DominioDeExcecao;
import modelo.entidades.Cliente;
import modelo.entidades.Evento;
import modelo.servicos.ClienteService;
import modelo.servicos.EventoService;

import java.time.LocalDate;

public class TesteGerarIdEvento {

    public static void main(String[] args) throws DominioDeExcecao {

        ClienteService clienteService = new ClienteService();
        EventoService eventoService = new EventoService();

        Cliente cliente = clienteService.buscarPorId(3);

        Evento evento1 = eventoService.cadastrarEvento(
                LocalDate.of(2026, 9, 20),
                "Aniversário",
                1500.00,
                cliente
        );

        System.out.println("Evento cadastrado:");
        System.out.println("ID: " + evento1.getIdEvento());

        Evento evento2 = eventoService.cadastrarEvento(
                LocalDate.of(2026, 10, 5),
                "Casamento",
                4000.00,
                cliente
        );

        System.out.println();
        System.out.println("Evento cadastrado:");
        System.out.println("ID: " + evento2.getIdEvento());
    }
}
