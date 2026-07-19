package entidades;

public class Cliente {
    private int idCliente;
    private String nome;
    private String telefone;
    private Endereco endereco;

    public Cliente(int idCliente, String nome, String telefone, Endereco endereco) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public int getId() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setId(int id) {
        this.idCliente = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "CLIENTE"
                + "\nID do cliente:" + idCliente
                + "\nNome: " + nome
                + "\nTelefone: " + telefone
                + "\nEndereco: " + endereco
                + "\n==========================================";
    }
}
