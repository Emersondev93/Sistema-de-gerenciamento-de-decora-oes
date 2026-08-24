package modelo.servicos;

import modelo.entidades.Cliente;
import modelo.entidades.Endereco;
import modelo.entidades.Evento;
import excecoes.DominioDeExcecao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Sistema {
    private Scanner sc = new Scanner(System.in);
    private ClienteService clienteService = new ClienteService();
    private EventoService eventoService = new EventoService();
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String lerCampoObrigatorio(String mensagem) {
        String valor;

        do {
            System.out.print(mensagem);
            valor = sc.nextLine().trim();

            if (valor.isEmpty()) {
                System.out.println("Este campo não pode ser vazio.");
            }
        } while (valor.isEmpty());
        return valor;
    }

    private String lerTelefone() {

        String telefone;

        do {
            System.out.print("Telefone: ");
            telefone = sc.nextLine().trim();

            telefone = telefone.replaceAll("\\D", "");

            if (telefone.length() != 10 && telefone.length() != 11) {
                System.out.println("Telefone inválido. Digite um telefone com 10 ou 11 números.");
            }
        } while (telefone.length() != 10 && telefone.length() != 11);

        return telefone;
    }

    private String lerCep() {
        String cep;

        do {
            System.out.print("Cep: ");
            cep = sc.nextLine().trim();

            cep = cep.replaceAll("\\D", "");

            if (cep.length() != 8) {
                System.out.println("CEP inválido. Digite um CEP com 8 números.");
            }
        } while (cep.length() != 8);

        return cep;
    }

    private LocalTime lerHorario() {

        DateTimeFormatter formatoHorario = DateTimeFormatter.ofPattern("HH:mm");

        while (true) {
            System.out.print("Horario do evento (HH:mm): ");
            String horario = sc.nextLine().trim();

            try {
                return LocalTime.parse(horario, formatoHorario);

            } catch (DateTimeParseException erro) {
                System.out.println("Horário inválido. Use o formato HH:mm.");
            }
        }
    }

    private boolean confirmarAgendamento() {
        while (true) {
            System.out.println("Deseja agendar mesmo assim? (S/N): ");

            String entrada = sc.nextLine().trim();

            if ((entrada.isEmpty())) {
                System.out.println("Digite S para sim ou N para não.");
                continue;
            }

            char resposta = entrada.toUpperCase().charAt(0);

            if (resposta == 'S') {
                return true;
            }

            if (resposta == 'N') {
                return false;
            }
            System.out.println("Opção inválida. Digite S para sim ou N para não.");
        }
    }

    public void cadastrarCliente() throws DominioDeExcecao {
        System.out.println("================CADASTRAR CLIENTE================ ");
        String nome = lerCampoObrigatorio("Nome: ");
        String telefone = lerTelefone();
        String rua = lerCampoObrigatorio("Rua: ");
        String numero = lerCampoObrigatorio("Número: ");
        String bairro = lerCampoObrigatorio("Bairro: ");
        String cidade = lerCampoObrigatorio("Cidade: ");
        String cep = lerCep();
        Endereco endereco = new Endereco(null, rua, numero, bairro, cidade, cep);
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

                List<Cliente> clientesEncontrados = clienteService.buscarPorNome(nomeBusca);

                if (clientesEncontrados.isEmpty()) {
                    System.out.println("Nenhum cliente encontrado. ");
                } else if (clientesEncontrados.size() == 1) {
                    encontrado = clientesEncontrados.get(0);
                } else {
                    System.out.println("\nClientes encontrados: ");

                    for (Cliente cliente : clientesEncontrados) {
                        System.out.println("ID: " + cliente.getId()
                                + " - Nome: " + cliente.getNome()
                                + " - Telefone: " + cliente.getTelefone());
                    }
                    System.out.println("\nDigite o ID do cliente desejado: ");
                    int idEscolhido = sc.nextInt();
                    sc.nextLine();
                    encontrado = clienteService.buscarPorId(idEscolhido);
                }

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
            String data = sc.nextLine().trim();
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

        LocalTime horario = lerHorario();

        boolean existeConflito = eventoService.existeEventoNaDataEHorario(dataFormatada, horario);

        if (existeConflito){
            System.out.println();
            System.out.println("ATENÇÃO!");
            System.out.println("Já existe um evento para: ");
            System.out.println("Data: " + dataFormatada.format(fmt));
            System.out.println("Horário: " + horario);

            boolean continuar = confirmarAgendamento();

            if(!continuar){
                System.out.println("Agendamento cancelado.");
                return;
            }
        }

        sc.nextLine();

        System.out.print("Tema: ");
        String tema = sc.nextLine();

        System.out.print("Valor: ");
        double valor = sc.nextDouble();

        Evento novoEvento = eventoService.cadastrarEvento(dataFormatada, horario, tema, valor, cliente);
        System.out.println("Evento " + novoEvento.getIdEvento() + " cadastrado com sucesso! ");
    }

    public List<Evento> listarEvento() {

        List<Evento> eventos = eventoService.listarEventos();

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                  DECORAÇÕES AGENDADAS");
        System.out.println("============================================================");

        if (eventos.isEmpty()) {
            System.out.println("Nenhuma decoração agendada.");
            return eventos;
        }

        for (Evento evento : eventos) {

            System.out.println();
            System.out.println("ID: " + evento.getIdEvento());
            System.out.println("Data: " + evento.getData().format(fmt));
            System.out.println("Tema: " + evento.getTema());
            System.out.printf("Valor: R$ %.2f%n", evento.getValor());

            Cliente cliente = evento.getCliente();

            System.out.println("Cliente: " + cliente.getNome());
            System.out.println("Telefone: " + cliente.getTelefone());

            System.out.println("------------------------------------------------------------");
        }

        return eventos;
    }

    public void removerEvento() {

        System.out.println("================ CANCELAR AGENDAMENTO ================");
        System.out.print("Digite o ID do evento: ");
        String idRemover = sc.next();

        Evento evento = eventoService.buscarPorId(idRemover);

        if (evento == null) {
            System.out.println("Não há evento com este ID!");
            return;
        }

        System.out.println();
        System.out.println("Evento encontrado:");
        System.out.println("ID: " + evento.getIdEvento());
        System.out.println("Data: " + evento.getData().format(fmt));
        System.out.println("Tema: " + evento.getTema());
        System.out.printf("Valor: R$ %.2f%n", evento.getValor());
        System.out.println("Cliente: " + evento.getCliente().getNome());

        System.out.println();
        System.out.println("Deseja realmente cancelar este agendamento?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        System.out.print("Escolha uma opção: ");

        int opcao = sc.nextInt();
        sc.nextLine();

        if (opcao == 1) {

            Evento removido = eventoService.removerEvento(idRemover);

            if (removido != null) {
                System.out.println("Agendamento " + idRemover + " cancelado com sucesso!");
            }

        } else if (opcao == 2) {

            System.out.println("Cancelamento interrompido.");

        } else {

            System.out.println("Opção inválida. Cancelamento interrompido.");
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
            clienteService.atualizarCliente(c);
            System.out.println("Alterações feitas com sucesso!");
        }
    }

    public void buscarEvento() {
        System.out.println("=============== BUSCAR DECORAÇÃO ================");
        System.out.println("Digite o ID do evento: ");
        String id = sc.nextLine();

        Evento evento = eventoService.buscarPorId(id);

        if (evento == null) {
            System.out.println("Não foi encontrado evento com este ID.");
            return;
        }

        System.out.println("\nEvento encontrado!");
        System.out.println("ID: " + evento.getIdEvento());
        System.out.println("Data: " + evento.getData().format(fmt));
        System.out.println("Tema: " + evento.getTema());
        System.out.println("Valor: R$ " + evento.getValor());

        Cliente cliente = evento.getCliente();

        System.out.println("\nCliente: ");
        System.out.println("ID: " + cliente.getId());
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("Telefone: " + cliente.getTelefone());

        System.out.println("Endereço: " + cliente.getEndereco());

    }

    public void alterarEvento() throws DominioDeExcecao {

        System.out.println("=============== ALTERAR DECORAÇÃO ===============");
        System.out.print("Digite o ID do evento: ");
        String id = sc.nextLine();

        Evento evento = eventoService.buscarPorId(id);

        if (evento == null) {
            System.out.println("Não foi encontrado evento com este ID.");
            return;
        }

        System.out.println("\nEvento encontrado:");
        System.out.println("ID: " + evento.getIdEvento());
        System.out.println("Data: " + evento.getData().format(fmt));
        System.out.println("Tema: " + evento.getTema());
        System.out.println("Valor: R$ " + evento.getValor());

        System.out.println("\nO que deseja alterar?");
        System.out.println("1 - Data");
        System.out.println("2 - Tema");
        System.out.println("3 - Valor");
        System.out.println("4 - Voltar");
        System.out.print("Opção: ");

        int opcao = sc.nextInt();
        sc.nextLine();

        boolean alterado = false;

        switch (opcao) {

            case 1:
                while (true) {
                    System.out.print("Nova data (dd/MM/aaaa): ");
                    String data = sc.nextLine();

                    try {
                        LocalDate novaData = LocalDate.parse(data, fmt);

                        if (novaData.isBefore(LocalDate.now())) {
                            System.out.println(
                                    "A data do evento deve ser posterior à data de hoje."
                            );
                        } else {
                            evento.setData(novaData);
                            alterado = true;
                            break;
                        }

                    } catch (DateTimeParseException e) {
                        System.out.println(
                                "Data em formato inválido. Use dd/MM/aaaa."
                        );
                    }
                }
                break;

            case 2:
                System.out.print("Novo tema: ");
                String novoTema = sc.nextLine();
                evento.setTema(novoTema);
                alterado = true;
                break;

            case 3:
                System.out.print("Novo valor: ");
                double novoValor = sc.nextDouble();
                sc.nextLine();

                evento.setValor(novoValor);
                alterado = true;
                break;

            case 4:
                System.out.println("Alteração cancelada.");
                break;

            default:
                System.out.println("Opção inválida.");
        }

        if (alterado) {
            eventoService.atualizarEvento(evento);
            System.out.println("Evento atualizado com sucesso!");
        }
    }

    public void menuEventos() {
        int opcao;

        do {
            System.out.println("\n=============== GERENCIAMENTO DE DECORAÇÕES =================");
            System.out.println("1 - Agendar decoração");
            System.out.println("2 - Listar decorações");
            System.out.println("3 - Buscar decoração");
            System.out.println("4 - Alterar decoração");
            System.out.println("5 - Cancelar decoração");
            System.out.println("6 - Voltar");
            System.out.println("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    try {
                        cadastrarEvento();
                    } catch (DominioDeExcecao e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 2:
                    listarEvento();
                    break;

                case 3:
                    buscarEvento();
                    break;

                case 4:
                    try {
                        alterarEvento();
                    } catch (DominioDeExcecao e) {
                        System.out.println("Erro" + e.getMessage());
                    }
                    break;

                case 5:
                    removerEvento();
                    break;
                case 6:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opção inválida!");

            }


        } while (opcao != 6);
    }
}

