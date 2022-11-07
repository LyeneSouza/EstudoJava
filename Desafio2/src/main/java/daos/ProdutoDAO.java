package daos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import entidades.Produto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements ProdutoDAOInterface {

    private String caminhoAbsoluto = "C:\\Users\\lyene\\OneDrive\\Documentos\\South System\\desafio-qa-modulo2\\produtos.csv"; // Pensar em como fazer para funcionar em outros computadores também
    private File caminho = new File(caminhoAbsoluto);

    @Override
    public void salvar(List<Produto> produtos) {

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
    }

    @Override
    public List<Produto> lerMostruario(String caminhoMostruario) throws IOException, CsvException {

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
        return produtosMostruario;
    }

    @Override
    public List<Produto> lerProdutos() throws IOException {

        List<Produto> produtosDAO = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while (linha != null && !linha.equals("")) {
                String[] prod = linha.split(",");
                String nome = prod[0];
                double preco = Double.parseDouble(prod[1]);
                int qtdEstoque = Integer.parseInt(prod[2]);
                String categoria = prod[3];
                produtosDAO.add(new Produto(nome, preco, qtdEstoque, categoria));
                linha = br.readLine();
            }
        }
        return produtosDAO;
    }
}
