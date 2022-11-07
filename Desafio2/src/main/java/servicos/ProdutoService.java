package servicos;

import com.opencsv.exceptions.CsvException;
import daos.ProdutoDAO;
import entidades.Produto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProdutoService {

    private ProdutoDAO dao = new ProdutoDAO();

    private List<Produto> produtos = new ArrayList<>();
    private String caminhoAbsoluto;
    private File caminho;

    Scanner sc = new Scanner(System.in);

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void atualizarListaProdutos() {
        produtos = dao.lerProdutos();
    }

    public void adicionarProduto(Produto produto) {

        int confirma = 2;
        while (confirma != 1 || confirma != 0) {
            System.out.println("Confirma a inclusão do produto '" + produto + "'? Digite 1 para confirmar ou 0 para cancelar.");
            confirma = Integer.parseInt(sc.nextLine());
            if (confirma == 1) {
                // Adicionando o produto na lista de produtos da loja
                produtos.add(produto);
                // Adicionando o produto no .csv
                dao.salvar(produtos);
                // Finalizando a inclusao
                System.out.println("Produto adicionado com sucesso!");
                mostrarProdutos(produtos);
                break;
            } else if (confirma == 0) {
                System.out.println("Inclusão cancelada!");
                break;
            } else { // Trocar para try-catch
                System.out.println("Número inválido.");
            }
        }
    }

    public void editarProduto(int numProduto, Produto produto) { // Verificar se o numProduto eh valido

        int confirma = 2;
        while (confirma != 1 || confirma != 0) {
            System.out.println("Confirma a edição do produto " + numProduto + "? Digite 1 para confirmar ou 0 para cancelar.");
            confirma = Integer.parseInt(sc.nextLine());
            if (confirma == 1) {
                // Editando o produto na lista de produtos da loja
                produtos.get(numProduto - 1).setNome(produto.getNome());
                produtos.get(numProduto - 1).setPreco(produto.getPreco());
                produtos.get(numProduto - 1).setQtdEstoque(produto.getQtdEstoque());
                produtos.get(numProduto - 1).setCategoria(produto.getCategoria());
                // Editando o produto no .csv
                dao.salvar(produtos);
                // Finalizando a edicao
                System.out.println("Produto editado com sucesso!");
                mostrarProdutos(produtos);
                break;
            } else if (confirma == 0) {
                System.out.println("Edição cancelada!");
                break;
            } else { // Mudar para try-catch
                System.out.println("Número inválido.");
            }
        }
    }

    public void excluirProduto(int numProduto) { // Verificar se o numProduto eh valido

        int confirma = 2;
        while (confirma != 1 || confirma != 0) {
            System.out.println("Confirma a exclusão do produto " + numProduto + "? Digite 1 para confirmar ou 0 para cancelar.");
            confirma = Integer.parseInt(sc.nextLine());
            if (confirma == 1) {
                // Excluindo o produto da lista de produtos da loja
                produtos.remove(produtos.get(numProduto - 1));
                // Excluindo o produto do .csv
                dao.salvar(produtos);
                // Finalizando a exclusão
                System.out.println("Produto excluído com sucesso!");
                mostrarProdutos(produtos);
                break;
            } else if (confirma == 0) {
                System.out.println("Exclusão cancelada!");
                break;
            } else { // Mudar para try-catch
                System.out.println("Número inválido.");
            }
        }
    }

    public void importarMostruario() throws IOException, CsvException {

        // Solicitando o arquivo .csv ao usuario
        System.out.print("Para começar, entre com o caminho do arquivo (.csv) do mostruário da fábrica: ");
        String caminhoMostruario = sc.nextLine();

        List<Produto> produtosMostruario = dao.lerMostruario(caminhoMostruario);

        // Imprimindo os produtos do mostruario
        System.out.println("A seguir, confira a lista de produtos do mostrúário:");
        mostrarProdutos(produtosMostruario);

        int confirma = 2;
        while (confirma != 1 || confirma != 0) {
            System.out.println("Confirma a importação dos produtos do mostruário? Digite 1 para confirmar ou 0 para cancelar.");
            confirma = Integer.parseInt(sc.nextLine());
            if (confirma == 1) {
                // Incluindo os produtos do mostruario na lista de produtos da loja
                for (Produto prod : produtosMostruario) {
                    produtos.add(prod);
                }
                // Incluindo os produtos do mostruario no arquivo de produtos da loja
                dao.salvar(produtos);
                // Finalizando a importacao
                System.out.println("Produtos importados com sucesso!");
                mostrarProdutos(produtos);
                break;
            } else if (confirma == 0) {
                System.out.println("Importação cancelada!");
                break;
            } else { // Mudar para try-catch
                System.out.println("Número inválido.");
            }
        }
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
}
