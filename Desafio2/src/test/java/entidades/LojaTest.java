package entidades;

import exceptions.*;
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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static builders.ProdutoBuilder.umProduto;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

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

    // Nao sei como testar o menuLoja() - nao tenho como mockar os metodos da propria loja

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

    @Test
    public void menuAdicionarDeveAdicionarComSucesso() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();
        when(sc.nextLine()).thenReturn("S");
        doNothing().when(service).adicionarProduto(any(Produto.class));
        doNothing().when(service).mostrarProdutos(any(List.class));
        String expected = "Confirma a inclusão do produto '" + produto + "' (s/n)? ";

        // Acao
        loja.menuAdicionar(produto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).adicionarProduto(produto);
        verify(service, times(1)).mostrarProdutos();
    }

    @Test
    public void menuAdicionarDeveCancelarAdicao() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();
        when(sc.nextLine()).thenReturn("N");
        String expected = "Confirma a inclusão do produto '" + produto + "' (s/n)? " +
                "Inclusão cancelada!\r\n";

        // Acao
        loja.menuAdicionar(produto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, never()).adicionarProduto(produto);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuAdicionarDeveMostrarMsgErroAoReceberAdicionarProdutoException() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();
        when(sc.nextLine()).thenReturn("S");
        doThrow(new AdicionarProdutoException("Não foi possível adicionar o produto. Erro ao salvar no arquivo.")).when(service).adicionarProduto(any(Produto.class));
        String expected = "Confirma a inclusão do produto '" + produto + "' (s/n)? " +
                "Não foi possível adicionar o produto. Erro ao salvar no arquivo.\r\n";

        // Acao
        loja.menuAdicionar(produto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).adicionarProduto(produto);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuAdicionarDeveMostrarMsgErroInesperadoAoReceberException() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();
        when(sc.nextLine()).thenReturn("S");
        doThrow(new NullPointerException()).when(service).adicionarProduto(any(Produto.class));
        String expected = "Confirma a inclusão do produto '" + produto + "' (s/n)? " +
                "Erro inesperado.\r\n";

        // Acao
        loja.menuAdicionar(produto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).adicionarProduto(produto);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuQualProdutoEditarDeveRetornarNumProduto() {

        // Cenario
        doNothing().when(service).mostrarProdutos(any(List.class));
        when(service.quantidadeProdutos()).thenReturn(2);
        when(sc.nextLine()).thenReturn("1");
        String expected = "QUAL O NÚMERO DO PRODUTO QUE DESEJA EDITAR? ";

        // Acao
        int numRetornado = loja.menuQualProdutoEditar();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).mostrarProdutos();
        verify(service, atLeastOnce()).quantidadeProdutos();
        error.checkThat(numRetornado, is(1));
    }

    @Test
    public void menuEditardeveEditarComSucesso() throws Exception {

        // Cenario
        int numProduto = 1;
        Produto produto = umProduto().agora();
        when(sc.nextLine()).thenReturn("S");
        doNothing().when(service).editarProduto(any(Integer.class), any(Produto.class));
        doNothing().when(service).mostrarProdutos(any(List.class));
        String expected = "Confirma a edição do produto " + numProduto + " (s/n)? ";

        // Acao
        loja.menuEditar(numProduto, produto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).editarProduto(numProduto, produto);
        verify(service, times(1)).mostrarProdutos();
    }

    @Test
    public void menuEditarDeveCancelarEdicao() throws Exception {

        // Cenario
        int numProduto = 1;
        Produto produto = umProduto().agora();
        when(sc.nextLine()).thenReturn("N");
        String expected = "Confirma a edição do produto " + numProduto + " (s/n)? " +
                "Edição cancelada!\r\n";

        // Acao
        loja.menuEditar(numProduto, produto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, never()).editarProduto(numProduto, produto);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuEditarDeveMostrarMsgErroAoReceberEditarProdutoException() throws Exception {

        // Cenario
        int numProduto = 1;
        Produto produto = umProduto().agora();
        when(sc.nextLine()).thenReturn("S");
        doThrow(new EditarProdutoException("Não foi possível editar o produto. Erro ao salvar no arquivo.")).when(service).editarProduto(any(Integer.class), any(Produto.class));
        String expected = "Confirma a edição do produto " + numProduto + " (s/n)? " +
                "Não foi possível editar o produto. Erro ao salvar no arquivo.\r\n";

        // Acao
        loja.menuEditar(numProduto, produto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).editarProduto(numProduto, produto);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuEditarDeveMostrarMsgErroInesperadoAoReceberException() throws Exception {

        // Cenario
        int numProduto = 1;
        Produto produto = umProduto().agora();
        when(sc.nextLine()).thenReturn("S");
        doThrow(new NullPointerException()).when(service).editarProduto(any(Integer.class), any(Produto.class));
        String expected = "Confirma a edição do produto " + numProduto + " (s/n)? " +
                "Erro inesperado.\r\n";

        // Acao
        loja.menuEditar(numProduto, produto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).editarProduto(numProduto, produto);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuQualProdutoExcluirDeveRetornarNumProduto() {

        // Cenario
        doNothing().when(service).mostrarProdutos(any(List.class));
        when(service.quantidadeProdutos()).thenReturn(2);
        when(sc.nextLine()).thenReturn("1");
        String expected = "QUAL O NÚMERO DO PRODUTO QUE DESEJA EXCLUIR? ";

        // Acao
        int numRetornado = loja.menuQualProdutoExcluir();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).mostrarProdutos();
        verify(service, atLeastOnce()).quantidadeProdutos();
        error.checkThat(numRetornado, is(1));
    }

    @Test
    public void menuQualProdutoExcluirDeveMostrarMsgQueNaoPodeExcluir() {

        // Cenario
        doNothing().when(service).mostrarProdutos(any(List.class));
        when(service.quantidadeProdutos()).thenReturn(1);
        String expected = "Há apenas um produto na lista. Não é possível excluir.\r\n";

        // Acao
        int numRetornado = loja.menuQualProdutoExcluir();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).mostrarProdutos();
        verify(service, times(1)).quantidadeProdutos();
        error.checkThat(numRetornado, is(0));
    }

    @Test
    public void menuExcluirDeveExcluirComSucesso() throws Exception {

        // Cenario
        int numProduto = 1;
        when(sc.nextLine()).thenReturn("S");
        doNothing().when(service).excluirProduto(any(Integer.class));
        doNothing().when(service).mostrarProdutos(any(List.class));
        String expected = "Confirma a exclusão do produto " + numProduto + " (s/n)? ";

        // Acao
        loja.menuExcluir(numProduto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).excluirProduto(numProduto);
        verify(service, times(1)).mostrarProdutos();
    }

    @Test
    public void menuExcluirDeveCancelarExclusao() throws Exception {

        // Cenario
        int numProduto = 1;
        when(sc.nextLine()).thenReturn("N");
        String expected = "Confirma a exclusão do produto " + numProduto + " (s/n)? " +
                "Exclusão cancelada!\r\n";

        // Acao
        loja.menuExcluir(numProduto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, never()).excluirProduto(numProduto);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuExcluirDeveMostrarMsgErroAoReceberExcluirProdutoException() throws Exception {

        // Cenario
        int numProduto = 1;
        when(sc.nextLine()).thenReturn("S");
        doThrow(new ExcluirProdutoException("Não foi possível excluir o produto. Erro ao salvar no arquivo.")).when(service).excluirProduto(any(Integer.class));
        String expected = "Confirma a exclusão do produto " + numProduto + " (s/n)? " +
                "Não foi possível excluir o produto. Erro ao salvar no arquivo.\r\n";

        // Acao
        loja.menuExcluir(numProduto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).excluirProduto(numProduto);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuExcluirDeveMostrarMsgErroInesperadoAoReceberException() throws Exception {

        // Cenario
        int numProduto = 1;
        when(sc.nextLine()).thenReturn("S");
        doThrow(new NullPointerException()).when(service).excluirProduto(any(Integer.class));
        String expected = "Confirma a exclusão do produto " + numProduto + " (s/n)? " +
                "Erro inesperado.\r\n";

        // Acao
        loja.menuExcluir(numProduto);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).excluirProduto(numProduto);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuArquivoMostruarioDeveRetornarListaComSucesso() throws Exception {

        // Cenario
        when(sc.nextLine()).thenReturn("path");
        List<Produto> produtosMostruario = Arrays.asList(umProduto().agora(), umProduto().agora());
        when(service.lerMostruario(any(String.class))).thenReturn(produtosMostruario);
        doNothing().when(service).mostrarProdutos(any(List.class));
        String expected = "Para começar, entre com o caminho do arquivo (.csv) do mostruário da fábrica: " +
                "A seguir, confira a lista de produtos do mostruário:\r\n";

        // Acao
        List<Produto> produtosRetornados = loja.menuArquivoMostruario();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).lerMostruario(any(String.class));
        verify(service, times(1)).mostrarProdutos(produtosMostruario);
        error.checkThat(produtosRetornados.get(0), is(produtosMostruario.get(0)));
        error.checkThat(produtosRetornados.get(1), is(produtosMostruario.get(1)));
    }

    @Test
    public void menuArquivoMostruarioDeveMostrarMsgErroAoReceberLerMostruarioException() throws Exception {

        // Cenario
        when(sc.nextLine()).thenReturn("path");
        when(service.lerMostruario(any(String.class))).thenThrow(new LerMostruarioException());
        String expected = "Para começar, entre com o caminho do arquivo (.csv) do mostruário da fábrica: " +
                "Erro ao acessar o arquivo do mostruário.\r\n";

        // Acao
        List<Produto> produtosRetornados = loja.menuArquivoMostruario();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).lerMostruario(any(String.class));
        assertNull(produtosRetornados);
    }

    @Test
    public void menuArquivoMostruarioDeveMostrarMsgErroInesperadoAoReceberException() throws Exception {

        // Cenario
        when(sc.nextLine()).thenReturn("path");
        when(service.lerMostruario(any(String.class))).thenThrow(new NullPointerException());
        String expected = "Para começar, entre com o caminho do arquivo (.csv) do mostruário da fábrica: " +
                "Erro inesperado.\r\n";

        // Acao
        List<Produto> produtosRetornados = loja.menuArquivoMostruario();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).lerMostruario(any(String.class));
        assertNull(produtosRetornados);
    }

    @Test
    public void menuImportarMostruarioDeveImportarComSucesso() throws Exception {

        // Cenario
        List<Produto> produtosMostruario = Arrays.asList(umProduto().agora(), umProduto().agora());
        when(sc.nextLine()).thenReturn("S");
        String expected = "Confirma a importação dos produtos do mostruário (s/n)? ";
        doNothing().when(service).importarMostruario(any(List.class));
        doNothing().when(service).mostrarProdutos();

        // Acao
        loja.menuImportarMostruario(produtosMostruario);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).importarMostruario(produtosMostruario);
        verify(service, times(1)).mostrarProdutos();
    }

    @Test
    public void menuImportarMostruarioDeveCancelarImportacao() throws Exception {

        // Cenario
        List<Produto> produtosMostruario = Arrays.asList(umProduto().agora(), umProduto().agora());
        when(sc.nextLine()).thenReturn("N");
        String expected = "Confirma a importação dos produtos do mostruário (s/n)? " +
                "Importação cancelada!\r\n";

        // Acao
        loja.menuImportarMostruario(produtosMostruario);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, never()).importarMostruario(produtosMostruario);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuImportarMostrarioDeveMostrarMsgErroAoReceberImportarMostruarioException() throws Exception {

        // Cenario
        List<Produto> produtosMostruario = Arrays.asList(umProduto().agora(), umProduto().agora());
        when(sc.nextLine()).thenReturn("S");
        String expected = "Confirma a importação dos produtos do mostruário (s/n)? " +
                "Não foi possível importar o mostruário.\r\n";
        doThrow(new ImportarMostruarioException()).when(service).importarMostruario(any(List.class));

        // Acao
        loja.menuImportarMostruario(produtosMostruario);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).importarMostruario(produtosMostruario);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuImportarMostrarioDeveMostrarMsgErroInesperadoAoReceberException() throws Exception {

        // Cenario
        List<Produto> produtosMostruario = Arrays.asList(umProduto().agora(), umProduto().agora());
        when(sc.nextLine()).thenReturn("S");
        String expected = "Confirma a importação dos produtos do mostruário (s/n)? " +
                "Erro inesperado.\r\n";
        doThrow(new NullPointerException()).when(service).importarMostruario(any(List.class));

        // Acao
        loja.menuImportarMostruario(produtosMostruario);

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        verify(service, times(1)).importarMostruario(produtosMostruario);
        verify(service, never()).mostrarProdutos();
    }

    @Test
    public void menuFinalizarDeveEncerrarComSucesso() {

        // Cenario
        when(sc.nextLine()).thenReturn("S");
        String expected = "Tem certeza que deseja encerrar o aplicativo (s/n)? " +
                "Aplicativo encerrado.\r\n";

        // Acao
        Boolean aplicativoFinalizado = loja.menuFinalizar();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        error.checkThat(aplicativoFinalizado, is(true));
    }

    @Test
    public void menuFinalizarDeveCancelarFinalizacao() {

        // Cenario
        when(sc.nextLine()).thenReturn("N");
        String expected = "Tem certeza que deseja encerrar o aplicativo (s/n)? " +
                "Finalização cancelada.\r\n";

        // Acao
        Boolean aplicativoFinalizado = loja.menuFinalizar();

        // Verificacao
        error.checkThat(outContent.toString(), is(expected));
        error.checkThat(aplicativoFinalizado, is(false));
    }
}
