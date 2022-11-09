package builders;

import entidades.Produto;

public class ProdutoBuilder {

    private Produto produto;

    private ProdutoBuilder() {
    }

    public static ProdutoBuilder umProduto() {
        ProdutoBuilder builder = new ProdutoBuilder();
        builder.produto = new Produto("Produto 1", 10.0, 5, "Categoria 1");
        return builder;
    }

    public static ProdutoBuilder umProdutoNulo() {
        ProdutoBuilder builder = new ProdutoBuilder();
        builder.produto = null;
        return builder;
    }

    public Produto agora() {
        return produto;
    }
}
