package servicos;

import entidades.Cliente;
import entidades.Endereco;
import entidades.Evento;
import excecoes.DominioDeExcecao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Sistema {
    private Scanner sc = new Scanner(System.in);
    private ClienteService clienteService = new ClienteService();
    private EventoService eventoService = new EventoService();
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        Cliente cliente = clienteService.cadastrarCliente(nome, telefone, endereco);
        System.out.println("Cliente cadastrado com sucesso! ID: " + cliente.getId());
    }

    public List<Cliente> listarClientes() {
        System.out.println("================LISTA DE CLIENTES================");
        return clienteService.listarClientes();
    }

    public Cliente buscaPorMenu() {
        System.out.println("Buscar cliente - Digite o número que corresponde ao tipo de busca: ");
        System.out.println("1 - Busca por ID.\n2 - Busca por nome. \n3 - Busca por telefone.");
        System.out.print("Opção de busca: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        Cliente encontrado = null;

        switch (opcao) {
            case 1:
                System.out.print("Digite o ID do cliente: ");
                int idBusca = sc.nextInt();
                sc.nextLine();
                encontrado = clienteService.buscarPorId(idBusca);
                break;
            case 2:
                System.out.print("Digite o nome: ");
                String nomeBusca = sc.nextLine();
                encontrado = clienteService.buscarPorNome(nomeBusca);
                break;
            case 3:
                System.out.print("Digite o telefone: ");
                String telefoneBusca = sc.nextLine();
                encontrado = clienteService.buscarPorTelefone(telefoneBusca);
                break;
            default:
                System.out.println("Tipo de busca inválido.");
        }

        if (encontrado == null) {
            System.out.println("Cliente não encontrado! ");
        }
        return encontrado;
    }

    public void removerCliente() {
        System.out.println("==========EXCLUIR CADASTRO==========");
        System.out.print("Digite o ID do cliente: ");
        int idRemover = sc.nextInt();
        sc.nextLine();

        Cliente removido = clienteService.removerCliente(idRemover);

        if (removido != null) {
            System.out.println(removido.getNome() + " excluido do cadastro.");
        } else {
            System.out.println("O id " + idRemover + " não foi encontrado.");
        }
    }

    public void cadastrarEvento() throws DominioDeExcecao {
        System.out.println("================AGENDAR DECORAÇÃO================");
        Cliente cliente = buscaPorMenu();
        if (cliente == null) {
            return;
        }

        LocalDate dataFormatada = null;
        boolean validacao = false;
        do {
            System.out.print("Data do evento (dd/MM/aaaa): ");
            String data = sc.next();
            try {
                dataFormatada = LocalDate.parse(data, fmt);
            } catch (DateTimeParseException erro) {
                System.out.println("Data em formato inválido. Use dd/MM/aaaa.");
                continue;
            }
            if (dataFormatada.isBefore(LocalDate.now())) {
                System.out.println("A data do evento deve ser posterior a data de hoje.");
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

        Evento novoEvento = eventoService.cadastrarEvento(dataFormatada, tema, valor, cliente);
        System.out.println("Evento " + novoEvento.getIdEvento() + " cadastrado com sucesso! ");
    }

    public List<Evento> listarEvento() {
        System.out.println("===========DECORAÇÕES AGENDADAS============");
        return eventoService.listarEventos();
    }

    public void removerEvento() {
        System.out.println("================CANCELAR AGENDAMENTO================");
        System.out.print("Digite o ID o evento: ");
        String idRemover = sc.next();

        Evento removido = eventoService.removerEvento(idRemover);
        if (removido != null) {
            System.out.println("Agendamento " + idRemover + " cancelado!");
        } else {
            System.out.println("Não há evento com este ID!");
        }
    }

    public void alterarDadosCliente() {
        System.out.println("===================ALTERAR INFORMAÇÕES DO CLIENTE==================");
        System.out.print("Digite o ID do cliente para alterar suas informações: ");
        int alterarDados = sc.nextInt();
        sc.nextLine();

        Cliente c = clienteService.buscarPorId(alterarDados);
        if (c == null) {
            System.out.println("Não foi encontrado cliente com este ID.");
            return;
        }

        System.out.println("Alterar informações de " + c.getNome() + " ID - " + c.getId());
        System.out.println("Escolha informação a ser alterada: " +
                "\n1 - Nome " +
                "\n2 - Telefone" +
                "\n3 - Rua" +
                "\n4 - Número" +
                "\n5 - Bairro" +
                "\n6 - Cidade" +
                "\n7 - Cep" +
                "\n8 - Voltar");
        System.out.print("Digite o número do campo que deseja alterar: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        boolean alterado = false;
        switch (opcao) {
            case 1:
                System.out.println("Nome atual: " + c.getNome());
                System.out.print("Novo nome: ");
                c.setNome(sc.nextLine());
                alterado = true;
                break;
            case 2:
                System.out.println("Telefone atual: " + c.getTelefone());
                System.out.print("Novo telefone: ");
                c.setTelefone(sc.nextLine());
                alterado = true;
                break;
            case 3:
                System.out.println("Rua atual: " + c.getEndereco().getRua());
                System.out.print("Nova rua: ");
                c.getEndereco().setRua(sc.nextLine());
                alterado = true;
                break;
            case 4:
                System.out.println("Número atual: " + c.getEndereco().getNumero());
                System.out.print("Novo número: ");
                c.getEndereco().setNumero(sc.nextLine());
                alterado = true;
                break;
            case 5:
                System.out.println("Bairro atual: " + c.getEndereco().getBairro());
                System.out.print("Novo bairro: ");
                c.getEndereco().setBairro(sc.nextLine());
                alterado = true;
                break;
            case 6:
                System.out.println("Cidade atual: " + c.getEndereco().getCidade());
                System.out.print("Nova cidade: ");
                c.getEndereco().setCidade(sc.nextLine());
                alterado = true;
                break;
            case 7:
                System.out.println("Cep atual: " + c.getEndereco().getCep());
                System.out.print("Novo cep: ");
                c.getEndereco().setCep(sc.nextLine());
                alterado = true;
                break;
            case 8:
                break;
            default:
                System.out.println("Opção inválida.");
        }
        if (alterado) {
            System.out.println("Alterações feitas com sucesso!");
        }
    }
}

