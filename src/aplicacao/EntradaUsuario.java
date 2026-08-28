package aplicacao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class EntradaUsuario {
    private Scanner sc;

    public EntradaUsuario(Scanner sc) {
        this.sc = sc;
    }

    public String lerCampoObrigatorio(String mensagem) {
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

    public String lerTelefone() {

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

    public String lerCep() {
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

    public LocalTime lerHorario() {

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

    public double lerValor() {
        while (true) {
            System.out.print("Valor: ");

            String entrada = sc.nextLine().trim();

            try {
                double valor = Double.parseDouble(entrada);

                if (valor <= 0) {
                    System.out.println("O valor deve ser maior que zero.");
                    continue;
                }

                return valor;

            } catch (NumberFormatException erro) {
                System.out.println("Valor inválido. Digite apenas números.");
            }
        }
    }

    public boolean confirmarAgendamento() {
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

    public int lerInteiro(String mensagem) {

        while (true) {
            System.out.print(mensagem);

            String entrada = sc.nextLine().trim();

            try {

                return Integer.parseInt(entrada);

            } catch (NumberFormatException erro) {
                System.out.println("Valor inválido. Digite apenas números inteiros.");
            }
        }
    }

    public LocalDate lerData() {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.println("Data do evento (dd/MM/aaaa): ");
            String data = sc.nextLine().trim();

            try {
                LocalDate dataFormatada = LocalDate.parse(data, formatoData);

                if (dataFormatada.isBefore(LocalDate.now())) {
                    System.out.println("A data do evento deve ser posterior a data de hoje.");

                    continue;
                }
                return dataFormatada;

            } catch (DateTimeParseException erro) {
                System.out.println("Data em formato inválido. Use dd/MM/aaaa.");
            }
        }

    }

}
