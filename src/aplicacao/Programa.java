package aplicacao;

import modelo.entidades.Cliente;
import modelo.entidades.Evento;
import excecoes.DominioDeExcecao;
import modelo.servicos.Sistema;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        try {

            Sistema sistema = new Sistema();

            int opcao;
            System.out.println(" ".repeat(50) + "============SISTEMA DE GERENCIAMENTO PARA EVENTOS==============");
            do {
                System.out.println("MENU PRINCIPAL" +
                        "\n1 - Cadastrar cliente" +
                        "\n2 - Excluir cadastro" +
                        "\n3 - Lista de clientes" +
                        "\n4 - Alterar informações de cliente" +
                        "\n5 - Agendar decoração" +
                        "\n6 - Decorações Agendadas" +
                        "\n7 - Cancelar agendamento" +
                        "\n8 - Sair");
                System.out.print("Digite o número da opção escolhida: ");
                opcao = sc.nextInt();
                sc.nextLine();
                System.out.println();
                switch (opcao) {
                    case 1:
                        sistema.cadastrarCliente();
                        break;
                    case 2:
                        sistema.removerCliente();
                        break;
                    case 3:
                        List<Cliente> listarClientes = sistema.listarClientes();
                        for (Cliente c : listarClientes) {
                            System.out.println(c);
                            System.out.println();
                        }
                        break;
                    case 4:
                        sistema.alterarDadosCliente();
                        break;
                    case 5:
                        sistema.cadastrarEvento();
                        break;
                    case 6:
                        List<Evento> listaEventos = sistema.listarEvento();
                        for (Evento e : listaEventos){
                            System.out.println(e);
                            System.out.println();
                        }
                        break;
                    case 7:
                        sistema.removerEvento();
                        break;
                    case 8:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
                System.out.println();
            } while (opcao != 8);
        } catch (InputMismatchException erro) {
            System.out.println("Erro. Tipo de caractere inválido! ");
        } catch (DominioDeExcecao erro) {
            System.out.println("Erro. " + erro.getMessage());
        }
        sc.close();
    }
}
