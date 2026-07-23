package aplicacao;

import excecoes.DominioDeExcecao;
import servicos.Sistema;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        try {

            Sistema sistema = new Sistema();

            int opcao;
            System.out.println("============SISTEMA DE GERENCIAMENTO PARA EVENTOS==============");
            do {
                System.out.println("1 - Cadastrar cliente" +
                        "\n2 - Excluir cadastro" +
                        "\n3 - Lista de clientes" +
                        "\n4 - Agendar decoração" +
                        "\n5 - Decoraçoes Agendadas" +
                        "\n6 - Cancelar agendamento" +
                        "\n7 - Sair");
                System.out.print("Digite o número da opção escolhida: ");
                opcao = sc.nextInt();
                sc.nextLine();
                switch (opcao) {
                    case 1:
                        sistema.cadastrarCliente();
                        break;
                    case 2:
                        sistema.removerCliente();
                        break;
                    case 3:
                        System.out.println(sistema.listarClientes());
                        break;
                    case 4:
                        sistema.cadastrarEvento();
                        break;
                    case 5:
                        System.out.println(sistema.listarEvento());
                        break;
                    case 6:
                        sistema.removerEvento();
                        break;
                    case 7:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
                System.out.println();
            } while (opcao != 7);
        } catch (InputMismatchException erro) {
            System.out.println("Erro. Tipo de caractere inválido! ");
        }catch (DominioDeExcecao erro){
            System.out.println("Erro. " + erro.getMessage());
        }
        sc.close();
    }
}
