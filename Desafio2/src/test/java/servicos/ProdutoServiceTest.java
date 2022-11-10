package servicos;

import com.opencsv.exceptions.CsvException;
import daos.ProdutoDAOInterface;
import entidades.Produto;
import exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static builders.ProdutoBuilder.umProduto;
import static builders.ProdutoBuilder.umProdutoNulo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;

    @Mock
    private List<Produto> produtos;
    @Mock
    private ProdutoDAOInterface dao;

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
    public void deveAdicionarProdutoComSucesso() throws IOException {

        // Cenario
        Produto produto = umProduto().agora();

        // Acao
        try {
            service.adicionarProduto(produto);
        } catch (Exception e) {
            // Verificacao
            Assert.fail();
        }

        verify(produtos, times(1)).add(produto);
        verify(dao, times(1)).salvar(produtos);
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

    @Test
    public void deveEditarProdutoComSucesso() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();
        when(produtos.size()).thenReturn(1);
        when(produtos.get(0)).thenReturn(umProduto().agora());

        // Acao
        try {
            service.editarProduto(1, produto);
        } catch (Exception e) {
            //Verificacao
            Assert.fail();
        }

        verify(produtos, times(4)).get(0);
        verify(dao, times(1)).salvar(produtos);
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
        int numProduto = 0;

        // Acao
        try {
            service.editarProduto(numProduto, produto);
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
        int numProduto = 2;

        // Acao
        try {
            service.editarProduto(numProduto, produto);
            // Verificacao
            Assert.fail();
        } catch (EditarProdutoException e) {
            assertThat(e.getMessage(), is("Produto não encontrado."));
        }
    }

    @Test
    public void deveLancarExcecaoAoNaoConseguirEditarProduto() throws Exception {

        // Cenario
        Produto produto = umProduto().agora();
        when(produtos.size()).thenReturn(1);
        when(produtos.get(0)).thenReturn(umProduto().agora());
        int numProduto = 1;

        doThrow(new IOException()).when(dao).salvar(Mockito.any(List.class));

        // Acao
        try {
            service.editarProduto(numProduto, produto);
            // Verificacao
            Assert.fail();
        } catch (EditarProdutoException e) {
            assertThat(e.getMessage(), is("Não foi possível editar o produto. Erro ao salvar no arquivo."));
        }
    }

    @Test
    public void deveExcluirProdutoComSucesso() throws IOException {

        // Cenario
        when(produtos.size()).thenReturn(2);
        when(produtos.get(0)).thenReturn(umProduto().agora());
        int numProduto = 1;

        // Acao
        try {
            service.excluirProduto(numProduto);
        } catch (Exception e) {
            // Verificacao
            Assert.fail();
        }

        verify(produtos, times(1)).remove(produtos.get(0));
        verify(dao, times(1)).salvar(produtos);
    }

    @Test
    public void deveLancarExcecaoAoExcluirUltimoProduto_naoPodeDeixarListaVazia() throws Exception {

        // Cenario
        when(produtos.size()).thenReturn(1);
        int numProduto = 1;

        // Acao
        try {
            service.excluirProduto(numProduto);
            // Verificacao
            Assert.fail();
        } catch (ExcluirProdutoException e) {
            assertThat(e.getMessage(), is("Há apenas um produto na lista. Não é possível excluir."));
        }
    }

    @Test
    public void deveLancarExcecaoAoExcluirProdutoNaoEncontrado_zero() throws Exception {

        // Cenario
        when(produtos.size()).thenReturn(2);
        int numProduto = 0;

        // Acao
        try {
            service.excluirProduto(numProduto);
            // Verificacao
            Assert.fail();
        } catch (ExcluirProdutoException e) {
            assertThat(e.getMessage(), is("Produto não encontrado."));
        }
    }

    @Test
    public void deveLancarExcecaoAoExcluirProdutoNaoEncontrado_maiorQueLista() throws Exception {

        // Cenario
        when(produtos.size()).thenReturn(2);
        int numProduto = 3;

        // Acao
        try {
            service.excluirProduto(numProduto);
            // Verificacao
            Assert.fail();
        } catch (ExcluirProdutoException e) {
            assertThat(e.getMessage(), is("Produto não encontrado."));
        }
    }

    @Test
    public void deveLancarExcecaoAoNaoConseguirExcluirProduto() throws Exception {

        // Cenario
        when(produtos.size()).thenReturn(2);
        int numProduto = 1;

        doThrow(new IOException()).when(dao).salvar(Mockito.any(List.class));

        // Acao
        try {
            service.excluirProduto(numProduto);
            // Verificacao
            Assert.fail();
        } catch (ExcluirProdutoException e) {
            assertThat(e.getMessage(), is("Não foi possível excluir o produto. Erro ao salvar no arquivo."));
        }
    }

    @Test
    public void deveLerMostruarioComSucesso() throws Exception {

        // Cenario
        String caminho = "caminho";
        List<Produto> produtos = Arrays.asList(umProduto().agora(), umProduto().agora());
        when(dao.lerMostruario(caminho)).thenReturn(produtos);

        // Acao
        List<Produto> produtosMostruario = service.lerMostruario(caminho);

        // Verificacao
        verify(dao).lerMostruario(caminho);
        assertThat(produtosMostruario, is(produtos));
    }

    @Test(expected = LerMostruarioException.class)
    public void deveLancarExcecaoIOAoNaoConseguirLerMostruario() throws Exception {

        // Cenario
        String caminho = "caminho";
        when(dao.lerMostruario(caminho)).thenThrow(new IOException());

        // Acao
        service.lerMostruario(caminho);

        // Verificacao
        // O teste passa se a excecao esperada for lancada
    }

    @Test(expected = LerMostruarioException.class)
    public void deveLancarExcecaoCsvAoNaoConseguirLerMostruario() throws Exception {

        // Cenario
        String caminho = "caminho";
        when(dao.lerMostruario(caminho)).thenThrow(new CsvException());

        // Acao
        service.lerMostruario(caminho);

        // Verificacao
        // O teste passa se a excecao esperada for lancada
    }

    @Test
    public void deveImportarMostruarioComSucesso() throws IOException {

        // Cenario
        List<Produto> produtosMostruario = Arrays.asList(umProduto().agora(), umProduto().agora());

        // Acao
        try {
            service.importarMostruario(produtosMostruario);
        } catch (Exception e) {
            // Verificacao
            Assert.fail();
        }

        verify(produtos, times(1)).add(produtosMostruario.get(0));
        verify(produtos, times(1)).add(produtosMostruario.get(1));
        verify(dao, times(1)).salvar(produtos);
    }

    @Test(expected = ImportarMostruarioException.class)
    public void deveLancarExcecaoAoNaoConseguirImportarMostruario() throws Exception {

        // Cenario
        List<Produto> produtosMostruario = Arrays.asList(umProduto().agora(), umProduto().agora());
        doThrow(new IOException()).when(dao).salvar(Mockito.any(List.class));

        // Acao
        service.importarMostruario(produtosMostruario);

        // Verificacao
        // O teste passa se a excecao esperada for lancada
    }

    /*@Test
    public void deveMostrarProdutosComSucesso() {
        // Como testar a impressão na tela???
        // Precisa testar isso??
    }*/

    @Test
    public void deveRetornarQuantidadeProdutosComSucesso() {

        // Cenario
        when(produtos.size()).thenReturn(2);

        // Acao
        int quantidadeProdutos = service.quantidadeProdutos();

        // Verificacao
        assertEquals(2, quantidadeProdutos);
    }
}
