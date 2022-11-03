package servicos;

import entidades.Loja;
import entidades.Produto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciamentoProdutosService {

    private List<Produto> produtos = new ArrayList<>();
    private String caminhoAbsoluto;
    private File caminho;

    Scanner sc = new Scanner(System.in);

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void atualizarListaProdutos() {

        // Lendo o arquivo .csv e atualizando a lista de produtos conforme arquivo
        //System.out.print("Para começar, entre com o caminho do arquivo dos produtos da loja: ");
        //caminhoAbsoluto = sc.nextLine();
        //caminho = new File(caminhoAbsoluto);

        caminhoAbsoluto = "C:\\Users\\lyene\\OneDrive\\Documentos\\South System\\desafio-qa-modulo2\\produtos.csv";
        caminho = new File(caminhoAbsoluto);

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while (linha != null) {
                String[] prod = linha.split(",");
                String nome = prod[0];
                double preco = Double.parseDouble(prod[1]);
                int qtdEstoque = Integer.parseInt(prod[2]);
                String categoria = prod[3];
                produtos.add(new Produto(nome, preco, qtdEstoque, categoria));
                linha = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void adicionarProduto(Produto produto) {

        int confirma = 2;
        while (confirma != 1 || confirma != 0) {
            System.out.println("Confirma a inclusão do produto '" + produto + "'? Digite 1 para confirmar ou 0 para cancelar.");
            confirma = sc.nextInt();
            if (confirma == 1) {
                // Adicionando o produto na lista de produtos da loja
                produtos.add(produto);
                // Adicionando o produto no .csv
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
                    for (Produto prod : produtos) {
                        bw.write(prod.getNome() + ","
                                + String.format("%.2f", prod.getPreco()) + ","
                                + prod.getQtdEstoque() + ","
                                + prod.getCategoria());
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
                // Finalizando a inclusao
                System.out.println("Produto adicionado com sucesso!");
                mostrarProdutos();
                break;
            } else if (confirma == 0) {
                System.out.println("Inclusão cancelada!");
                break;
            } else {
                System.out.println("Número inválido.");
            }
        }
    }

    public void editarProduto(int numProduto, Produto produto) {

        int confirma = 2;
        while (confirma != 1 || confirma != 0) {
            System.out.println("Confirma a edição do produto " + numProduto + "? Digite 1 para confirmar ou 0 para cancelar.");
            confirma = sc.nextInt();
            if (confirma == 1) {
                // Editando o produto na lista de produtos da loja
                produtos.get(numProduto - 1).setNome(produto.getNome());
                produtos.get(numProduto - 1).setPreco(produto.getPreco());
                produtos.get(numProduto - 1).setQtdEstoque(produto.getQtdEstoque());
                produtos.get(numProduto - 1).setCategoria(produto.getCategoria());
                // Editando o produto no .csv
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
                    for (Produto prod : produtos) {
                        bw.write(prod.getNome() + ","
                                + String.format("%.2f", prod.getPreco()) + ","
                                + prod.getQtdEstoque() + ","
                                + prod.getCategoria());
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
                // Finalizando a edicao
                System.out.println("Produto editado com sucesso!");
                mostrarProdutos();
                break;
            } else if (confirma == 0) {
                System.out.println("Edição cancelada!");
                break;
            } else {
                System.out.println("Número inválido.");
            }
        }
    }

    public void excluirProduto(Produto produto) {

    }

    public Produto dadosProduto() {

        sc.nextLine();
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Preço: ");
        double preco = sc.nextDouble();
        System.out.print("Quantidade em estoque: ");
        int qtdEstoque = sc.nextInt();
        System.out.print("Categoria: ");
        sc.nextLine();
        String categoria = sc.nextLine();

        Produto produto = new Produto(nome, preco, qtdEstoque, categoria);
        return produto;
    }

    public void mostrarProdutos() {

        // Imprimindo os produtos do arquivo
        System.out.println("\n--------PRODUTOS--------");
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            int contador = 1;
            while (linha != null) {
                System.out.println(contador + ". " + linha);
                linha = br.readLine();
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println("------------------------");
    }
}
