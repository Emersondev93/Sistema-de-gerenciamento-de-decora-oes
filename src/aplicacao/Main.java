package aplicacao;

import servicos.Sistema;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Cadastrar cliente" +
                "\n2 - Listar clientes" +
                "\n3 - Cadastrar evento" +
                "\n4 - Listar eventos" +
                "\n5 - Sair");

        Sistema sistema = new Sistema();
        int opcao;
        do {
            System.out.print("Digite o número da opção desejada: ");
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    sistema.cadastrarCliente();
                    break;
                case 2:
                    System.out.println(sistema.listarClientes());
                    break;
                case 3:
                    sistema.cadastrarEvento();
                    break;
                case 4:
                    System.out.println(sistema.listarEvento());
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 5);
    }
}
