package servicos;

import entidades.Cliente;
import entidades.Endereco;

import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    private List<Cliente> clientes = new ArrayList<>();
    private int idCliente = 1;

    public Cliente cadastrarCliente(String nome, String telefone, Endereco endereco) {
        Cliente cliente = new Cliente(idCliente, nome, telefone, endereco);
        clientes.add(cliente);
        idCliente++;
        return cliente;
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    public Cliente buscarPorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Cliente buscarPorNome(String nome) {
        for (Cliente c : clientes) {
            if (nome.equals(c.getNome())) {
                return c;
            }
        }
        return null;
    }

    public Cliente buscarPorTelefone(String telefone) {
        for (Cliente c : clientes) {
            if (telefone.equals(c.getTelefone())) {
                return c;
            }
        }
        return null;
    }

    public Cliente removerCliente(int id) {
        Cliente encontrado = buscarPorId(id);
        if (encontrado != null) {
            clientes.remove(encontrado);
        }
        return encontrado;
    }

}
