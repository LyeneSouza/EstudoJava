package entidades;

import exceptions.AtualizarListaProdutosException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import servicos.ProdutoService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class LojaTest {

    @InjectMocks
    private Loja loja;

    @Mock
    private ProdutoService service;

    @Mock
    private Scanner sc;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() throws IOException {
        outContent.close();
    }

    @Test
    public void deveReceberDadosProdutoComSucesso() {

        // Cenario
        when(sc.nextLine()).thenReturn("1");
        String expected = "Nome: " +
                "Preço: " +
                "Quantidade em estoque: " +
                "Categoria: ";

        // Acao
        Produto produto = loja.receberDadosProduto();

        // Verificacao
        error.checkThat(produto.getNome(), is("1"));
        error.checkThat(produto.getPreco(), is(1.0));
        error.checkThat(produto.getQtdEstoque(), is(1));
        error.checkThat(produto.getCategoria(), is("1"));
        error.checkThat(outContent.toString(), is(expected));
    }

    @Test
    public void deveRetornarProdutoNuloAoReceberDadosInvalidos() {

        // Cenario
        when(sc.nextLine()).thenReturn("A");
        String expected = "Nome: " +
                "Preço: " +
                "Ocorreu um erro na entrada dos dados.\r\n";

        // Acao
        Produto produto = loja.receberDadosProduto();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        assertNull(produto);
    }

    /*@Test // Fica em looping eterno porque a opção sempre vai ser 0... Não sei como testar o menu D:
    public void deveMostrarMenuPadraoComSucesso() throws Exception {

        // Cenario
        doNothing().when(service).atualizarListaProdutos();
        when(sc.nextLine()).thenReturn("0");
        String expected = "---------BEM-VINDO(A)---------\r\n" +
                "\n---------MENU---------\r\n" +
                "Opções:\n" +
                "1- Adicionar Novo Produto\n" +
                "2- Editar Produto\n" +
                "3- Excluir Produto\n" +
                "4- Importar Mostruário de Fábrica\n" +
                "5- Sair\r\n" +
                "----------------------\r\n" +
                "\nSelecione a opção que deseja:";

        // Acao
        loja.menuLoja();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
    }*/

    @Test
    public void deveAvisarQueNaoPodeContinuarPorqueListaProdutosNaoAtualizou() throws Exception {

        // Cenario
        doThrow(new AtualizarListaProdutosException()).when(service).atualizarListaProdutos();
        String expected = "---------BEM-VINDO(A)---------\r\n" +
                "Não foi possível atualizar a lista de produtos, portanto, não é possível continuar.\r\n";

        // Acao
        loja.menuLoja();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
    }

    @Test
    public void deveMostrarMensagemErroAoReceberException() throws Exception {

        // Cenario
        doThrow(new Exception()).when(service).atualizarListaProdutos();
        String expected = "---------BEM-VINDO(A)---------\r\n" +
                "Erro inesperado.\r\n";

        // Acao
        loja.menuLoja();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
    }
}
