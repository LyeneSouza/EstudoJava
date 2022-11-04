package servicos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
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
            while (linha != null && !linha.equals("")) {
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
            } else { // Mudar para try-catch
                System.out.println("Número inválido.");
            }
        }
    }

    public void excluirProduto(int numProduto) {

        int confirma = 2;
        while (confirma != 1 || confirma != 0) {
            System.out.println("Confirma a exclusão do produto " + numProduto + "? Digite 1 para confirmar ou 0 para cancelar.");
            confirma = sc.nextInt();
            if (confirma == 1) {
                // Excluindo o produto da lista de produtos da loja
                produtos.remove(produtos.get(numProduto - 1));
                // Excluindo o produto do .csv
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
                // Finalizando a exclusão
                System.out.println("Produto excluído com sucesso!");
                mostrarProdutos();
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
        sc.nextLine(); // Esse nextLine so e necessario para absorver quebra pendente a partir da segunda execucao. Na primeira execucao ele fica sobrando
        System.out.print("Para começar, entre com o caminho do arquivo (.csv) do mostruário da fábrica: ");
        String caminhoMostruario = sc.nextLine();

        // Guardando cada linha do arquivo em uma posicao do vetor de Strings
        List<String[]> linhas;
        try (CSVReader reader = new CSVReader(new FileReader(caminhoMostruario))) {
            linhas = reader.readAll();
        }

        // Adicionando os produtos do arquivo na lista de produtos do mostruario
        List<Produto> produtosMostruario = new ArrayList<>();
        for (String[] arrays : linhas) {
            if (arrays != linhas.get(0)) { // Desconsiderar a primeira linha
                String nome = arrays[3];
                String valorBrutoString = arrays[6];
                String valorBrutoComPonto = valorBrutoString.replace(',', '.');
                double valorBruto = Double.parseDouble(valorBrutoComPonto);
                String impostoString = arrays[7];
                String impostoComPonto = impostoString.replace(",", ".");
                double imposto = Double.parseDouble(impostoComPonto) / 100;
                double lucro = 0.45;
                double preco = valorBruto + (valorBruto * imposto) + (valorBruto * lucro);
                int qtdEstoque = 0;
                String categoria = arrays[5];
                produtosMostruario.add(new Produto(nome, preco, qtdEstoque, categoria));
            }
        }

        // Imprimindo os produtos do mostruario
        System.out.println("\n--------PRODUTOS MOSTRUÁRIO--------");
        for (Produto prod : produtosMostruario) {
            System.out.println(prod);
        }
        System.out.println("----------------------------------");

        int confirma = 2;
        while (confirma != 1 || confirma != 0) {
            System.out.println("Confirma a importação dos produtos do mostruário? Digite 1 para confirmar ou 0 para cancelar.");
            confirma = sc.nextInt();
            if (confirma == 1) {
                // Incluindo os produtos do mostruario na lista de produtos da loja
                for (Produto prod : produtosMostruario) {
                    produtos.add(prod);
                }
                // Incluindo os produtos do mostruario no arquivo de produtos da loja
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
                // Finalizando a importacao
                System.out.println("Produtos importados com sucesso!");
                mostrarProdutos();
                break;
            } else if (confirma == 0) {
                System.out.println("Importação cancelada!");
                break;
            } else { // Mudar para try-catch
                System.out.println("Número inválido.");
            }
        }

    }

    public Produto dadosProduto() {

        sc.nextLine(); // Esse nextLine so e necessario para absorver quebra pendente a partir da segunda execucao. Na primeira execucao ele fica sobrando
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
        int contador = 1;
        for (Produto prod : produtos) {
            System.out.println(contador++ + ". " + prod);
        }
        System.out.println("------------------------");
    }
}
