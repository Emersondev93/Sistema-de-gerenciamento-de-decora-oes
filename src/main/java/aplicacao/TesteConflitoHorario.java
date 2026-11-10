package aplicacao;

import modelo.dao.EventoDao;
import modelo.impl.EventoDaoJDBC;

import java.time.LocalDate;
import java.time.LocalTime;

public class TesteConflitoHorario {
    public static void main(String[] args) {

        EventoDao eventoDao = new EventoDaoJDBC();

        LocalDate data = LocalDate.of(2026, 9, 20);
        LocalTime horario = LocalTime.of(15, 0);

        boolean existe = eventoDao.existeEventoNaDataEHorario(
                data,
                horario
        );

        if (existe) {
            System.out.println("Já existe um evento nesta data e horário.");
        } else {
            System.out.println("Horário disponível.");
        }
    }
}
