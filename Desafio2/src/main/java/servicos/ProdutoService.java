package servicos;

import com.opencsv.exceptions.CsvException;
import daos.ProdutoDAO;
import daos.ProdutoDAOInterface;
import entidades.Produto;
import exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    private ProdutoDAOInterface dao = new ProdutoDAO();

    private List<Produto> produtos = new ArrayList<>();

    public void atualizarListaProdutos() throws Exception {
        try {
            produtos = dao.lerProdutos();
        } catch (IOException e) {
            throw new AtualizarListaProdutosException();
        } catch (Exception e) {
            throw e;
        }
    }

    public void adicionarProduto(Produto produto) throws Exception {

        if (produto == null) {
            throw new AdicionarProdutoException("Produto inválido.");
        }

        try {
            // Adicionando o produto na lista de produtos da loja
            produtos.add(produto);
            // Adicionando o produto no .csv
            dao.salvar(produtos);
            // Finalizando a inclusao
            System.out.println("Produto adicionado com sucesso!");
            mostrarProdutos();
        } catch (IOException e) {
            throw new AdicionarProdutoException("Não foi possível adicionar o produto. Erro ao salvar no arquivo.");
        } catch (Exception e) {
            throw e;
        }
    }

    public void editarProduto(int numProduto, Produto produto) throws Exception {

        if (produto == null) {
            throw new EditarProdutoException("Produto inválido.");
        }

        if (numProduto <= 0 || numProduto > produtos.size()) {
            throw new EditarProdutoException("Produto não encontrado.");
        }

        try {
            // Editando o produto na lista de produtos da loja
            produtos.get(numProduto - 1).setNome(produto.getNome());
            produtos.get(numProduto - 1).setPreco(produto.getPreco());
            produtos.get(numProduto - 1).setQtdEstoque(produto.getQtdEstoque());
            produtos.get(numProduto - 1).setCategoria(produto.getCategoria());
            // Editando o produto no .csv
            dao.salvar(produtos);
            // Finalizando a edicao
            System.out.println("Produto editado com sucesso!");
            mostrarProdutos();
        } catch (IOException e) {
            throw new EditarProdutoException("Não foi possível editar o produto. Erro ao salvar no arquivo.");
        } catch (Exception e) {
            throw e;
        }
    }

    public void excluirProduto(int numProduto) throws Exception {

        if (numProduto <= 0 || numProduto > produtos.size()) {
            throw new ExcluirProdutoException("Produto não encontrado.");
        }

        try {
            // Excluindo o produto da lista de produtos da loja
            produtos.remove(produtos.get(numProduto - 1));
            // Excluindo o produto do .csv
            dao.salvar(produtos);
            // Finalizando a exclusão
            System.out.println("Produto excluído com sucesso!");
            mostrarProdutos();
        } catch (IOException e) {
            throw new ExcluirProdutoException("Não foi possível excluir o produto. Erro ao salvar no arquivo.");
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Produto> lerMostruario(String caminhoMostruario) throws Exception {
        try {
            return dao.lerMostruario(caminhoMostruario);
        } catch (IOException | CsvException e) {
            throw new LerMostruarioException();
        } catch (Exception e) {
            throw e;
        }
    }

    public void importarMostruario(List<Produto> produtosMostruario) throws Exception {

        try {
            // Incluindo os produtos do mostruario na lista de produtos da loja
            for (Produto prod : produtosMostruario) {
                produtos.add(prod);
            }
            // Incluindo os produtos do mostruario no arquivo de produtos da loja
            dao.salvar(produtos);
            // Finalizando a importacao
            System.out.println("Produtos importados com sucesso!");
            mostrarProdutos();
        } catch (IOException e) {
            throw new ImportarMostruarioException();
        } catch (Exception e) {
            throw e;
        }
    }

    public void mostrarProdutos() {
        mostrarProdutos(produtos);
    }

    public void mostrarProdutos(List<Produto> produtos) {

        // Imprimindo os produtos da lista
        System.out.println("\n--------PRODUTOS--------");
        int contador = 1;
        for (Produto prod : produtos) {
            System.out.println(contador++ + ". " + prod);
        }
        System.out.println("------------------------");
    }

    public int quantidadeProdutos() {
        return produtos.size();
    }
}
