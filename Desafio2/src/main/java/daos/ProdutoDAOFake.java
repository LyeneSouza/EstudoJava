package daos;

import entidades.Produto;

import java.util.List;

public class ProdutoDAOFake implements ProdutoDAOInterface {

    // Vai ser usado para os testes

    @Override
    public void salvar(List<Produto> produtos) {

    }

    @Override
    public List<Produto> lerMostruario(String caminho) {
        return null;
    }

    @Override
    public List<Produto> lerProdutos() {
        return null;
    }
}
