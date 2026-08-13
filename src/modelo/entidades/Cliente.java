package modelo.entidades;

public class Cliente {
    private Integer id;
    private String nome;
    private String telefone;
    private Endereco endereco;

    public Cliente(Integer id, String nome, String telefone, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Endereco getEndereco() {
        return endereco;
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
        return "ID do cliente: " + id
                + "\nNome: " + nome
                + "\nTelefone: " + telefone
                + "\nEndereco: " + endereco;
    }
}
