package servicos;

import entidades.Loja;
import entidades.Produto;
import org.junit.Test;

import static builders.ProdutoBuilder.umProduto;

public class GerenciamentoProdutosServiceTest {

    private GerenciamentoProdutosService service;
    private Loja loja;

    @Test
    public void deveAdicionarProdutoComSucesso() {

        // Cenario
        service = new GerenciamentoProdutosService();
        loja = new Loja();
        Produto produto = umProduto().agora();

        // Acao
        service.adicionarProduto(produto);

        // Verificacao
    }
}
