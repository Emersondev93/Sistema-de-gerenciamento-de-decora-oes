package aplicacao;

import modelo.entidades.Cliente;
import modelo.servicos.ClienteService;

public class TesteExcluirCliente {
    public static void main(String[] args) {
        ClienteService clienteService = new ClienteService();

        int id = 1;

        Cliente cliente = clienteService.buscarPorId(id);

        if(cliente!= null){

            System.out.println("Cliente encontrado: ");
            System.out.println(cliente);

            clienteService.removerCliente(id);

            System.out.println("--------------------------------");
            System.out.println("Cliente excluido com sucesso!!!");
        }else {
            System.out.println("Cliente não encontrado!!!");
        }

    }
}
