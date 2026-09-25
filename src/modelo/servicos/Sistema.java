package modelo.servicos;

import aplicacao.EntradaUsuario;
import modelo.entidades.Cliente;
import modelo.entidades.Endereco;
import modelo.entidades.Evento;
import excecoes.DominioDeExcecao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Sistema {
    private Scanner sc = new Scanner(System.in);
    private ClienteService clienteService = new ClienteService();
    private EventoService eventoService = new EventoService();
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private EntradaUsuario entrada = new EntradaUsuario(sc);

    public Sistema(EntradaUsuario entrada) {
        this.entrada = entrada;
    }

    public void cadastrarCliente() throws DominioDeExcecao {
        System.out.println("================CADASTRAR CLIENTE================ ");
        String nome = entrada.lerCampoObrigatorio("Nome: ");
        String telefone = entrada.lerTelefone();
        String rua = entrada.lerCampoObrigatorio("Rua: ");
        String numero = entrada.lerCampoObrigatorio("Número: ");
        String bairro = entrada.lerCampoObrigatorio("Bairro: ");
        String cidade = entrada.lerCampoObrigatorio("Cidade: ");
        String cep = entrada.lerCep();
        Endereco endereco = new Endereco(null, rua, numero, bairro, cidade, cep);
        Cliente cliente = clienteService.cadastrarCliente(nome, telefone, endereco);
        System.out.println("Cliente cadastrado com sucesso! ID: " + cliente.getId());
    }

    public List<Cliente> listarClientes() {
        System.out.println("================LISTA DE CLIENTES================");
        return clienteService.listarClientes();
    }

    public Cliente buscaPorMenu() {
        int opcao;
        System.out.println("Buscar cliente - Digite o número que corresponde ao tipo de busca: ");
        System.out.println("1 - Busca por ID.\n2 - Busca por nome. \n3 - Busca por telefone.");
        opcao = entrada.lerInteiro("Opção de busca: ");

        Cliente encontrado = null;

        switch (opcao) {
            case 1:
                int idBusca = entrada.lerInteiro("Digite o ID do cliente: ");
                encontrado = clienteService.buscarPorId(idBusca);
                break;
            case 2:
                String nomeBusca = entrada.lerCampoObrigatorio("Digite o nome do cliente: ");

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
                    int idEscolhido = entrada.lerInteiro("Digite o ID do cliente desejado: ");
                    encontrado = clienteService.buscarPorId(idEscolhido);
                }

                break;

            case 3:
                String telefoneBusca = entrada.lerTelefone();
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
        int idRemover = entrada.lerInteiro("Digite o ID do cliente: ");

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

        LocalDate dataFormatada = entrada.lerData();

        LocalTime horario = entrada.lerHorario();

        boolean existeConflito = eventoService.existeEventoNaDataEHorario(dataFormatada, horario);

        if (existeConflito) {
            System.out.println();
            System.out.println("ATENÇÃO!");
            System.out.println("Já existe um evento para: ");
            System.out.println("Data: " + dataFormatada.format(fmt));
            System.out.println("Horário: " + horario);

            boolean continuar = entrada.confirmarOperacao();

            if (!continuar) {
                System.out.println("Agendamento cancelado.");
                return;
            }
        }

        String tema = entrada.lerCampoObrigatorio("Tema: ");

        double valor = entrada.lerValor();

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

            exibirEvento(evento);
            Cliente cliente = evento.getCliente();

            System.out.println("Cliente: " + cliente.getNome());
            System.out.println("Telefone: " + cliente.getTelefone());

            System.out.println("------------------------------------------------------------");
        }

        return eventos;
    }

    public void removerEvento() {

        System.out.println("================ CANCELAR AGENDAMENTO ================");
        String idRemover = entrada.lerCampoObrigatorio("Digite o ID do evento: ");

        Evento evento = eventoService.buscarPorId(idRemover);

        if (evento == null) {
            System.out.println("Não há evento com este ID!");
            return;
        }

        System.out.println();
        System.out.println("Evento encontrado:");
        exibirEvento(evento);

        System.out.println();
        System.out.println("Deseja realmente cancelar este agendamento?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");


        int opcao = entrada.lerInteiro("Escolha uma opção: ");

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

    public void alterarDadosCliente() throws DominioDeExcecao {
        System.out.println("===================ALTERAR INFORMAÇÕES DO CLIENTE==================");
        int idcliente = entrada.lerInteiro("Digite o ID do cliente para alterar suas informações: ");

        Cliente cliente = clienteService.buscarPorId(idcliente);
        if (cliente == null) {
            System.out.println("Não foi encontrado cliente com este ID.");
            return;
        }

        System.out.println("Alterar informações de " + cliente.getNome() + " ID - " + cliente.getId());
        System.out.println("Escolha informação a ser alterada: " +
                "\n1 - Nome " +
                "\n2 - Telefone" +
                "\n3 - Rua" +
                "\n4 - Número" +
                "\n5 - Bairro" +
                "\n6 - Cidade" +
                "\n7 - Cep" +
                "\n8 - Salvar alterações" +
                "\n0 - Voltar");
        int opcao;
        boolean alterado = false;

        do {
            opcao = entrada.lerInteiro("Digite o número do campo que deseja alterar: ");


            switch (opcao) {
                case 1:
                    System.out.println("Nome atual: " + cliente.getNome());
                    cliente.setNome(entrada.lerCampoObrigatorio("Novo nome: "));
                    alterado = true;
                    break;
                case 2:
                    System.out.println("Telefone atual: " + cliente.getTelefone());
                    cliente.setTelefone(entrada.lerTelefone());
                    alterado = true;
                    break;
                case 3:
                    System.out.println("Rua atual: " + cliente.getEndereco().getRua());
                    cliente.getEndereco().setRua(entrada.lerCampoObrigatorio("Nova rua: "));
                    alterado = true;
                    break;
                case 4:
                    System.out.println("Número atual: " + cliente.getEndereco().getNumero());
                    cliente.getEndereco().setNumero(entrada.lerCampoObrigatorio("Novo número: "));
                    alterado = true;
                    break;
                case 5:
                    System.out.println("Bairro atual: " + cliente.getEndereco().getBairro());
                    cliente.getEndereco().setBairro(entrada.lerCampoObrigatorio("Novo bairro: "));
                    alterado = true;
                    break;
                case 6:
                    System.out.println("Cidade atual: " + cliente.getEndereco().getCidade());
                    cliente.getEndereco().setCidade(entrada.lerCampoObrigatorio("Nova cidade: "));
                    alterado = true;
                    break;
                case 7:
                    System.out.println("Cep atual: " + cliente.getEndereco().getCep());
                    cliente.getEndereco().setCep(entrada.lerCep());
                    alterado = true;
                    break;
                case 8:
                    if (alterado) {
                        clienteService.atualizarCliente(cliente);
                        System.out.println("Alterações salvas com sucesso!");
                    } else {
                        System.out.println("Nenhuma alteração foi realizada.");
                    }
                    break;
                case 0:
                    System.out.println("Alteração cancelada.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0 && opcao != 8);

    }

    public void buscarEvento() {
        System.out.println("=============== BUSCAR DECORAÇÃO ================");
        String id = entrada.lerCampoObrigatorio("Digite o ID do evento: ");

        Evento evento = eventoService.buscarPorId(id);

        if (evento == null) {
            System.out.println("Não foi encontrado evento com este ID.");
            return;
        }

        System.out.println("\nEvento encontrado!");
        exibirEvento(evento);

        System.out.println("\nCliente:");
        exibirCliente(evento.getCliente());

    }

    private boolean salvarAlteracoesEvento(Evento evento, LocalDate novaData, LocalTime novoHorario, String novoTema, double novoValor) throws DominioDeExcecao {
        boolean existeConflito =
                eventoService.existeOutroEventoNaDataEHorario(
                        evento.getIdEvento(),
                        novaData,
                        novoHorario
                );

        if (existeConflito) {
            System.out.println();
            System.out.println("ATENÇÃO!");
            System.out.println("Já existe outro evento nesta data e horário.");

            System.out.println("Data: " + novaData.format(fmt));

            System.out.println("Horário: " + novoHorario);

            boolean continuar = entrada.confirmarOperacao();

            if (!continuar) {
                return false;
            }
            evento.setData(novaData);
            evento.setHorario(novoHorario);
            evento.setTema(novoTema);
            evento.setValor(novoValor);

            eventoService.atualizarEvento(evento);

            return true;

        }
        return false;
    }

    public void alterarEvento() throws DominioDeExcecao {

        System.out.println();
        System.out.println("=============== ALTERAR DECORAÇÃO ===============");
        String id = entrada.lerCampoObrigatorio("Digite o ID do evento: ");

        Evento evento = eventoService.buscarPorId(id);

        if (evento == null) {
            System.out.println("Não foi encontrado evento com este ID.");
            return;
        }

        LocalDate novaData = evento.getData();
        LocalTime novoHorario = evento.getHorario();
        String novoTema = evento.getTema();
        double novoValor = evento.getValor();

        int opcao;
        boolean alteracoesSalvas = false;
        boolean possuiAlteracoes = false;

        do {
            System.out.println("\nEvento em edição:");
            System.out.println("ID: " + evento.getIdEvento());
            System.out.println("Data: " + novaData.format(fmt));
            System.out.println("Horário: " + novoHorario);
            System.out.println("Tema: " + novoTema);
            System.out.printf("Valor: R$ %.2f%n", novoValor);

            System.out.println("\nO que deseja alterar?");
            System.out.println("1 - Data");
            System.out.println("2 - Horário");
            System.out.println("3 - Tema");
            System.out.println("4 - Valor");
            System.out.println("5 - Salvar alterações");
            System.out.println("0 - Voltar");

            opcao = entrada.lerInteiro("Opção: ");

            switch (opcao) {

                case 1:
                    novaData = entrada.lerData();
                    possuiAlteracoes = true;

                    System.out.println("Data alterada.");
                    break;
                case 2:
                    novoHorario = entrada.lerHorario();
                    possuiAlteracoes = true;

                    System.out.println("Horário alterado. ");
                    break;

                case 3:
                    novoTema = entrada.lerCampoObrigatorio("Novo Tema: ");
                    possuiAlteracoes = true;

                    System.out.println("Tema alterado.");
                    break;

                case 4:
                    novoValor = entrada.lerValor();
                    possuiAlteracoes = true;

                    System.out.println("Valor alterado.");
                    break;

                case 5: {

                    boolean salvou = salvarAlteracoesEvento(evento, novaData, novoHorario, novoTema, novoValor);

                    if (!salvou) {
                        System.out.println("Alterações não foram salvas.");
                        break;
                    }

                    alteracoesSalvas = true;

                    System.out.println();
                    System.out.println("Alterações salvas com sucesso!");
                    break;
                }
                case 0: {
                    if (possuiAlteracoes) {
                        boolean salvar = entrada.confirmarSaidaComAlteracoes();

                        if (salvar) {
                            boolean salvou = salvarAlteracoesEvento(
                                    evento,
                                    novaData,
                                    novoHorario,
                                    novoTema,
                                    novoValor
                            );

                            if (salvou) {
                                System.out.println("Alterações salvas com sucesso!");
                            } else {
                                System.out.println("Alterações não foram salvas.");
                            }
                        } else {
                            System.out.println("Alterações descartadas.");
                        }
                    } else {
                        System.out.println("Alteração cancelada.");
                    }

                    break;
                }
                default:
                    System.out.println("Opção inválida.");

            }
        } while (!alteracoesSalvas && opcao != 0);
    }

    public void exibirCliente(Cliente cliente) {
        System.out.println("ID: " + cliente.getId());
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("Telefone: " + cliente.getTelefone());
        System.out.println("Endereço: " + cliente.getEndereco());
    }

    public void exibirEvento(Evento evento) {
        System.out.println("ID: " + evento.getIdEvento());
        System.out.println("Data: " + evento.getData());
        System.out.println("Horário: " + evento.getHorario());
        System.out.println("Tema: " + evento.getTema());
        System.out.printf("Valor: R$ %.2f%n", evento.getValor());
    }
}

