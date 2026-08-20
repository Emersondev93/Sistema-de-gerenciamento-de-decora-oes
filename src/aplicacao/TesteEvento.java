package aplicacao;

import modelo.entidades.Cliente;
import modelo.entidades.Endereco;
import modelo.entidades.Evento;
import modelo.impl.EventoDaoJDBC;
import modelo.servicos.ClienteService;

import java.time.LocalDate;

public class TesteEvento {
    public static void main(String[] args) {

        ClienteService clienteService = new ClienteService();

        Endereco endereco = new Endereco(
                null,
                "Rua das flores",
                "100",
                "Centro",
                "Para de minas",
                "35660-000"

        );

        Cliente cliente = clienteService.cadastrarCliente(
                "Joao da Silva",
                "99999-1111",
                endereco
        );

        Evento evento = new Evento(
                "EV001",
                LocalDate.of(2026, 9, 15),
                "Casamento",
                3.500,
                cliente
        );

        EventoDaoJDBC eventoDao = new EventoDaoJDBC();

        eventoDao.inserir(evento);

        System.out.println("Evento cadastrado com sucesso!");
        System.out.println("ID do evento: " + evento.getIdEvento());
        System.out.println("ID do cliente: " + cliente.getId());

    }
}
