package servicos;

import entidades.Cliente;
import entidades.Endereco;
import entidades.Evento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Sistema {
    Scanner sc = new Scanner(System.in);
    Random random = new Random();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void cadastrarCliente() {
        System.out.println("CADASTRO ");
        int idCliente = 1;
        sc.nextLine();
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Telefone:");
        String telefone = sc.nextLine();
        System.out.print("Rua: ");
        String rua = sc.nextLine();
        System.out.print("Número: ");
        String numero = sc.nextLine();
        System.out.println("Bairro: ");
        String bairro = sc.nextLine();
        System.out.print("Cidade: ");
        String cidade = sc.nextLine();
        System.out.print("Cep: ");
        String cep = sc.nextLine();
        Endereco endereco = new Endereco(rua, numero, bairro, cidade, cep);
        Cliente cliente = new Cliente(idCliente, nome, telefone, endereco);
        clientes.add(cliente);
        idCliente++;

    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    public void buscarCliente() {
        System.out.println("Digite o número correspondente ao tipo de busca: ");
        System.out.println("1 - Busca por ID.\n2 - Busca por nome. \n3 - Busca por telefone.");
        System.out.print("Opção: ");
        int opcao = sc.nextInt();
        sc.nextLine();
        switch (opcao) {
            case 1:
                System.out.print("Digite o ID: ");
                int idBusca = sc.nextInt();
                for (Cliente cliente : clientes) {
                    if (idBusca == cliente.getId()) {
                        System.out.println(cliente);
                    }
                    break;
                }
            case 2:
                System.out.print("Digite o nome: ");
                String nomeBusca = sc.nextLine();
                for (Cliente cliente : clientes) {
                    if (nomeBusca.equals(cliente.getNome())) {
                        System.out.println(cliente);
                    }
                    break;
                }
            case 3:
                System.out.print("Digite o telefone");
                String telefoneBusca = sc.nextLine();
                for (Cliente cliente : clientes) {
                    if (telefoneBusca.equals(cliente.getTelefone())) {
                        System.out.println(cliente);
                    }
                    break;
                }
            default:
                System.out.println("Tipo de busca inválido.");

        }

    }

    public void removerCliente(Cliente cliente) {
        System.out.print("Digite o ID do cliente: ");
        int idRemover = sc.nextInt();
        clientes.removeIf(c -> c.getId() == idRemover);
    }

    public void cadastrarEvento(){
        String idEvento = "ev" + 001 + new Random(999);
        System.out.print("Data: ");
        String data = sc.next();
        LocalDate dataFormatada = LocalDate.parse(data, fmt);


    }

}
