package entidades;

import java.time.LocalDate;

public class Evento {
    private String idEvento;
    private LocalDate data;
    private String tema;
    private double valor;
    private Cliente cliente;

    public Evento(String idEvento, LocalDate data, String tema, double valor, Cliente cliente) {
        this.idEvento = idEvento;
        this.data = data;
        this.tema = tema;
        this.valor = valor;
        this.cliente = cliente;
    }

    public String getIdEvento() {

        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;

    }

    public LocalDate getData() {

        return data;
    }

    public void setData(LocalDate data) {

        this.data = data;
    }

    public String getTema() {

        return tema;
    }

    public void setTema(String tema) {

        this.tema = tema;
    }

    public double getValor() {

        return valor;
    }

    public void setValor(double valor) {

        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "EVENTO "
                + "\nID do evento: " + idEvento
                + "\nData: " + data
                + "\nTema: " + tema
                + "\nValor: " + valor
                + "\n" + cliente
                + "=======================================";
    }
}

