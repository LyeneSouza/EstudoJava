package servicos;

import entidades.Loja;
import entidades.Produto;
import exceptions.AdicionarProdutoException;
import org.junit.Test;

import static builders.ProdutoBuilder.umProduto;

public class ProdutoServiceTest {

    private ProdutoService service;
    private Loja loja;

    @Test
    public void deveAdicionarProdutoComSucesso() throws AdicionarProdutoException {

        // Cenario
        service = new ProdutoService();
        loja = new Loja();
        Produto produto = umProduto().agora();

        // Acao
        service.adicionarProduto(produto);

        // Verificacao
    }
}
