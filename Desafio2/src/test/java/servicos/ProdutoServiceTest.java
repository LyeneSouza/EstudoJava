package servicos;

import daos.ProdutoDAOInterface;
import entidades.Produto;
import exceptions.AdicionarProdutoException;
import exceptions.AtualizarListaProdutosException;
import exceptions.EditarProdutoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static builders.ProdutoBuilder.umProduto;
import static builders.ProdutoBuilder.umProdutoNulo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;

    @Mock
    private List<Produto> produtos;
    @Mock
    private ProdutoDAOInterface dao;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deveAtualizarListaProdutosComSucesso() throws Exception {

        // Cenario
        List<Produto> produtos = Arrays.asList(umProduto().agora(), umProduto().agora());
        when(dao.lerProdutos()).thenReturn(produtos);

        // Acao
        service.atualizarListaProdutos();

        // Verificacao
        verify(dao).lerProdutos();
    }

    @Test(expected = AtualizarListaProdutosException.class)
    public void deveLancarExcecaoAoNaoConseguirAtualizarProdutos() throws Exception {

        // Cenario
        when(dao.lerProdutos()).thenThrow(new IOException());

        // Acao
        service.atualizarListaProdutos();

        // Verificacao
        // O teste passa se a excecao esperada for lancada
    }

    @Test
    public void deveAdicionarProdutoComSucesso() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();

        // Acao
        service.adicionarProduto(produto);

        // Verificacao
        ArgumentCaptor<List> argCapt = ArgumentCaptor.forClass(List.class);
        Mockito.verify(dao).salvar(argCapt.capture());
        List<Produto> produtoRetornado = argCapt.getValue();

        error.checkThat(produtoRetornado.get(0).getNome(), is("Produto 1"));
        error.checkThat(produtoRetornado.get(0).getPreco(), is(10.0));
        error.checkThat(produtoRetornado.get(0).getQtdEstoque(), is(5));
        error.checkThat(produtoRetornado.get(0).getCategoria(), is("Categoria 1"));
    }

    @Test
    public void deveLancarExcecaoAoAdicionarProdutoNulo() throws Exception {

        // Cenario
        Produto produto = umProdutoNulo().agora();
        when(dao.lerProdutos()).thenThrow(new IOException());

        // Acao
        try {
            service.adicionarProduto(produto);
            // Verificacao
            Assert.fail();
        } catch (AdicionarProdutoException e) {
            assertThat(e.getMessage(), is("Produto inválido."));
        }
    }

    @Test
    public void deveLancarExcecaoAoNaoConseguirAdicionarProduto() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();
        doThrow(new IOException()).when(dao).salvar(Mockito.any(List.class));

        // Acao
        try {
            service.adicionarProduto(produto);
            // Verificacao
            Assert.fail();
        } catch (AdicionarProdutoException e) {
            assertThat(e.getMessage(), is("Não foi possível adicionar o produto. Erro ao salvar no arquivo."));
        }
    }

    @Test ////?????
    public void deveEditarProdutoComSucesso() throws Exception {

        /*// Cenario
        Produto produto = umProduto().agora();

        // Acao
        service.adicionarProduto(produto);

        // Verificacao
        ArgumentCaptor<List> argCapt = ArgumentCaptor.forClass(List.class);
        Mockito.verify(dao).salvar(argCapt.capture());
        List<Produto> produtoRetornado = argCapt.getValue();

        error.checkThat(produtoRetornado.get(0).getNome(), is("Produto 1"));
        error.checkThat(produtoRetornado.get(0).getPreco(), is(10.0));
        error.checkThat(produtoRetornado.get(0).getQtdEstoque(), is(5));
        error.checkThat(produtoRetornado.get(0).getCategoria(), is("Categoria 1"));*/
    }

    @Test
    public void deveLancarExcecaoAoEditarProdutoNulo() throws Exception {

        // Cenario
        Produto produto = umProdutoNulo().agora();

        // Acao
        try {
            service.editarProduto(1, produto);
            // Verificacao
            Assert.fail();
        } catch (EditarProdutoException e) {
            assertThat(e.getMessage(), is("Produto inválido."));
        }
    }

    @Test
    public void deveLancarExcecaoAoEditarProdutoNaoEncontrado_zero() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();

        // Acao
        try {
            service.editarProduto(0, produto);
            // Verificacao
            Assert.fail();
        } catch (EditarProdutoException e) {
            assertThat(e.getMessage(), is("Produto não encontrado."));
        }
    }

    @Test
    public void deveLancarExcecaoAoEditarProdutoNaoEncontrado_maiorQueLista() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();
        when(produtos.size()).thenReturn(1);

        // Acao
        try {
            service.editarProduto(2, produto);
            // Verificacao
            Assert.fail();
        } catch (EditarProdutoException e) {
            assertThat(e.getMessage(), is("Produto não encontrado."));
        }
    }

    @Test ///????
    public void deveLancarExcecaoAoNaoConseguirEditarProduto() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();
        when(produtos.size()).thenReturn(1);
        when(produtos.get(0)).thenReturn(umProduto().agora());

        doThrow(new IOException()).when(dao).salvar(Mockito.any(List.class));

        // Acao
        try {
            service.editarProduto(1, produto);
            // Verificacao
            Assert.fail();
        } catch (EditarProdutoException e) {
            assertThat(e.getMessage(), is("Não foi possível editar o produto. Erro ao salvar no arquivo."));
        }
    }


}
