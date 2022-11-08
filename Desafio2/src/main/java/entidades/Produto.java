package entidades;

public class Produto {

    private String nome;
    private double preco;
    private int qtdEstoque;
    private String categoria;

    public Produto() {
    }

    public Produto(String nome, double preco, int qtdEstoque, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return nome
                + ", preço: R$"
                + String.format("%.2f", preco)
                + ", estoque: "
                + qtdEstoque
                + ", categoria: "
                + categoria;
    }
}
