package servicos;

import entidades.Cliente;
import entidades.Endereco;
import entidades.Evento;
import excecoes.DominioDeExcecao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Sistema {
    private Scanner sc = new Scanner(System.in);
    private Random random = new Random();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private int idCliente = 1;

    public void cadastrarCliente() {
        System.out.println("================CADASTRAR CLIENTE================ ");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Rua: ");
        String rua = sc.nextLine();
        System.out.print("Número: ");
        String numero = sc.nextLine();
        System.out.print("Bairro: ");
        String bairro = sc.nextLine();
        System.out.print("Cidade: ");
        String cidade = sc.nextLine();
        System.out.print("Cep: ");
        String cep = sc.nextLine();
        Endereco endereco = new Endereco(rua, numero, bairro, cidade, cep);
        Cliente cliente = new Cliente(idCliente, nome, telefone, endereco);
        clientes.add(cliente);
        System.out.println("Cliente cadastrado com sucesso! ID: " + idCliente);
        idCliente++;
    }

    public List<Cliente> listarClientes() {
        System.out.println("================LISTA DE CLIENTES================");
        return new ArrayList<>(clientes);
    }

    private void buscarEExibirCliente() {
        Cliente cliente = buscaPorMenu();
        if (cliente != null) {
            System.out.println(cliente);
        }
    }

    public Cliente buscaPorMenu() {
        System.out.println("Buscar cliente - Digite o número que corresponde ao tipo de busca: ");
        System.out.println("1 - Busca por ID.\n2 - Busca por nome. \n3 - Busca por telefone.");
        System.out.print("Opção de busca: ");
        int opcao = sc.nextInt();
        sc.nextLine();
        switch (opcao) {
            case 1:
                System.out.print("Digite o ID do cliente: ");
                int idBusca = sc.nextInt();
                sc.nextLine();
                for (Cliente cliente : clientes) {
                    if (idBusca == cliente.getId()) {
                        return cliente;
                    }
                }
                break;
            case 2:
                System.out.print("Digite o nome: ");
                String nomeBusca = sc.nextLine();
                for (Cliente cliente : clientes) {
                    if (nomeBusca.equals(cliente.getNome())) {
                        return cliente;
                    }
                }
                break;
            case 3:
                System.out.print("Digite o telefone: ");
                String telefoneBusca = sc.nextLine();
                for (Cliente cliente : clientes) {
                    if (telefoneBusca.equals(cliente.getTelefone())) {
                        return cliente;
                    }
                }
                break;
            default:
                System.out.println("Tipo de busca inválido.");

        }
        System.out.println("Cliente não encontrado!");
        return null;
    }

    public void removerCliente() {
        System.out.println("==========EXCLUIR CADASTRO==========");
        System.out.print("Digite o ID do cliente: ");
        int idRemover = sc.nextInt();

        Cliente clienteEncontrado = null;

        for (Cliente c : clientes) {
            if (idRemover == c.getId()) {
                clienteEncontrado = c;
                break;
            }
        }
        if (clienteEncontrado != null) {
            clientes.remove(clienteEncontrado);
            System.out.println(clienteEncontrado.getNome() + " excluido do cadastro.");
        } else {
            System.out.println("O id " + idRemover + " não foi encontrado.");
        }

    }

    public void cadastrarEvento() throws DominioDeExcecao {
        System.out.println("================AGENDAR DECORAÇÃO================");
        Cliente cliente = buscaPorMenu();
        if (cliente == null) {
            System.out.println("Cliente não encontrado no cadastro.");
            return;
        }
        int numAleatorio = random.nextInt(1000);
        String idEvento = "ev" + numAleatorio;
        LocalDate dataFormatada;
        boolean validacao = false;
        do {
            System.out.print("Data do evento (dd/MM/aaaa): ");
            String data = sc.next();
            dataFormatada = LocalDate.parse(data, fmt);
            LocalDate dataAtual = LocalDate.now();
            validacao = false;
            if (dataFormatada.isBefore(dataAtual)) {
                System.out.println("A data do evento deve ser posterior a data atual.");
            } else {
                validacao = true;
            }
        }
        while (!validacao);

        sc.nextLine();
        System.out.print("Tema: ");
        String tema = sc.nextLine();
        System.out.print("Valor: ");
        double valor = sc.nextDouble();
        Evento novoEvento = new Evento(idEvento, dataFormatada, tema, valor, cliente);
        eventos.add(novoEvento);
        System.out.println("Evento " + idEvento + " cadastrado com sucesso! ");
    }

    public List<Evento> listarEvento() {
        System.out.println("===========DECORAÇÕES AGENDADAS============");
        return new ArrayList<>(eventos);
    }

    public void removerEvento() {
        System.out.println("================CANCELAR AGENDAMENTO================");
        System.out.print("Digite o ID o evento: ");
        String idRemover = sc.next();
        eventos.removeIf(e -> e.getIdEvento().equals(idRemover));
    }

}
