package aplicacao;

import modelo.entidades.Endereco;
import modelo.impl.EnderecoDaoJDBC;
import org.w3c.dom.ls.LSOutput;

public class TesteBanco {
    public static void main(String[] args) {
        Endereco endereco = new Endereco(
                null,
                "Rua das flores",
                "100",
                "Centro",
                "Pará de Minas",
                "35660-000"
        );

        EnderecoDaoJDBC enderecoDao = new EnderecoDaoJDBC();

        enderecoDao.inserir(endereco);

        System.out.println("Endereço cadastrado!");
        System.out.println("ID gerado: " + endereco.getId());
    }

}
