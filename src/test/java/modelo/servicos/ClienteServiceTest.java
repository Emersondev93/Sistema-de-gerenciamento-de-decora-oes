package modelo.servicos;

import modelo.dao.ClienteDao;
import modelo.dao.EnderecoDao;
import modelo.entidades.Cliente;


import modelo.servicos.ClienteService;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {
    @Test
    void deveBuscarClientePorNome(){

        //Criando os objetos simulados
        ClienteDao clienteDao = mock(ClienteDao.class);
        EnderecoDao enderecoDao = mock(EnderecoDao.class);

        //Criando o serviço com os objetos simulados
        ClienteService clienteService = new ClienteService(clienteDao, enderecoDao);

        //Preparando o resultado que o Mock deverá devolver
        List<Cliente> clientesEsperados = List.of();

        when(clienteDao.buscaPorNome("Joao")).thenReturn(clientesEsperados);

        //Executando o método que queremos testar
        List<Cliente> resultado = clienteService.buscarPorNome("Joao");

        //Verificando o resultado
        assertSame(clientesEsperados, resultado);

        //Verificando se o DAO foi chamado
        verify(clienteDao).buscaPorNome("Joao");

    }

}
