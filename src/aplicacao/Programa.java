package aplicacao;

import excecoes.DominioDeExcecao;
import modelo.entidades.Cliente;
import modelo.entidades.Evento;
import modelo.servicos.Sistema;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) throws DominioDeExcecao {

        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);
        Sistema sistema = new Sistema();

        int opcao;

        System.out.println();
        System.out.println("======================================================");
        System.out.println("       SISTEMA DE GERENCIAMENTO PARA EVENTOS");
        System.out.println("======================================================");

        do {

            System.out.println();
            System.out.println("================ MENU PRINCIPAL ================");
            System.out.println("1 - Gerenciar clientes");
            System.out.println("2 - Gerenciar eventos");
            System.out.println("0 - Sair");
            System.out.println("=================================================");
            System.out.print("Escolha uma opção: ");


            opcao = sc.nextInt();
            sc.nextLine();
            try {
                switch (opcao) {

                    case 1:
                        menuClientes(sc, sistema);
                        break;

                    case 2:
                        menuEventos(sc, sistema);
                        break;

                    case 0:
                        System.out.println("\nEncerrando o sistema...");
                        break;

                    default:
                        System.out.println("\nOpção inválida!");

                }

            } catch (InputMismatchException erro) {

                System.out.println("\nErro: digite apenas números.");
                sc.nextLine();
                opcao = -1;
            } catch (DominioDeExcecao erro) {
                System.out.println("\nErro: " + erro.getMessage());
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void menuClientes(Scanner sc, Sistema sistema) throws DominioDeExcecao {

        int opcao;

        do {

            System.out.println();
            System.out.println("================ GERENCIAR CLIENTES ================");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Buscar cliente");
            System.out.println("4 - Alterar cliente");
            System.out.println("5 - Excluir cliente");
            System.out.println("0 - Voltar");
            System.out.println("====================================================");
            System.out.print("Escolha uma opção: ");

            try {

                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {

                    case 1:
                        sistema.cadastrarCliente();
                        break;

                    case 2:
                        List<Cliente> clientes = sistema.listarClientes();

                        if (clientes.isEmpty()) {
                            System.out.println("Nenhum cliente cadastrado.");
                        } else {
                            System.out.println("\n================ CLIENTES ================");

                            for (Cliente cliente : clientes) {
                                System.out.println(cliente);
                                System.out.println("------------------------------------------");
                            }
                        }
                        break;

                    case 3:
                        Cliente clienteEncontrado = sistema.buscaPorMenu();

                        if (clienteEncontrado != null){
                            System.out.println("\n==================== CLIENTE ENCONTRADO =====================");
                            System.out.println(clienteEncontrado);
                        }
                        break;

                    case 4:
                        sistema.alterarDadosCliente();
                        break;

                    case 5:
                        sistema.removerCliente();
                        break;

                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }

            } catch (InputMismatchException erro) {

                System.out.println("Erro: digite apenas números.");
                sc.nextLine();
                opcao = -1;

            }

        } while (opcao != 0);
    }

    private static void menuEventos(Scanner sc, Sistema sistema) throws DominioDeExcecao {

        int opcao;

        do {

            System.out.println();
            System.out.println("================ GERENCIAR EVENTOS ================");
            System.out.println("1 - Agendar decoração");
            System.out.println("2 - Listar decorações");
            System.out.println("3 - Buscar decoração");
            System.out.println("4 - Alterar decoração");
            System.out.println("5 - Cancelar decoração");
            System.out.println("0 - Voltar");
            System.out.println("===================================================");
            System.out.print("Escolha uma opção: ");

            try {

                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {

                    case 1:
                        sistema.cadastrarEvento();
                        break;

                    case 2:
                        sistema.listarEvento();
                        break;

                    case 3:
                        sistema.buscarEvento();
                        break;

                    case 4:
                        sistema.alterarEvento();
                        break;

                    case 5:
                        sistema.removerEvento();
                        break;

                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }

            } catch (InputMismatchException erro) {

                System.out.println("Erro: digite apenas números.");
                sc.nextLine();
                opcao = -1;

            }

        } while (opcao != 0);
    }
}
