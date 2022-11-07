package entidades;

import com.opencsv.exceptions.CsvException;
import exceptions.AdicionarProdutoException;
import exceptions.AtualizarListaProdutosException;
import exceptions.EditarProdutoException;
import servicos.ProdutoService;

import java.io.IOException;
import java.util.Scanner;

public class Loja {
    private ProdutoService service = new ProdutoService();

    public Loja() {
    }

    public static Produto receberDadosProduto() {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("Preço: ");
            double preco = Double.parseDouble(sc.nextLine());
            System.out.print("Quantidade em estoque: ");
            int qtdEstoque = Integer.parseInt(sc.nextLine());
            System.out.print("Categoria: ");
            String categoria = sc.nextLine();

            return new Produto(nome, preco, qtdEstoque, categoria);
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro na entrada dos dados.");
        }
        return null;
    }

    public void menuLoja() throws IOException, CsvException {
        Scanner sc = new Scanner(System.in);

        System.out.println("---------BEM-VINDO(A)---------");
        try {
            service.atualizarListaProdutos();

            int opcao = 0;

            do {
                try {
                    System.out.println("\n---------MENU---------");
                    System.out.println("Opções:\n" +
                            "1- Adicionar Novo Produto\n" +
                            "2- Editar Produto\n" +
                            "3- Excluir Produto\n" +
                            "4- Importar Mostruário de Fábrica\n" +
                            "5- Sair");
                    System.out.println("----------------------");
                    System.out.print("\nSelecione a opção que deseja: ");
                    opcao = Integer.parseInt(sc.nextLine());
                } catch (RuntimeException e) {
                    System.out.println("Ocorreu um erro na entrada dos dados.");
                }

                switch (opcao) {
                    case 1: // Adicionar produto
                        try {
                            System.out.println("ENTRE COM OS DADOS DO PRODUTO A SER ADICIONADO");
                            service.adicionarProduto(receberDadosProduto());
                            break;
                        } catch (AdicionarProdutoException e) {
                            System.out.println("Não foi possível adicionar o produto.");
                            break;
                        }
                    case 2: // Editar produto
                        try {
                            service.mostrarProdutos(service.getProdutos());
                            int numProduto = 0;
                            do {
                                System.out.print("QUAL O NÚMERO DO PRODUTO QUE DESEJA EDITAR? ");
                                numProduto = Integer.parseInt(sc.nextLine());
                                if (numProduto <= 0 || numProduto > service.getProdutos().size()) {
                                    System.out.println("Produto não encontrado.");
                                }
                            } while (numProduto <= 0 || numProduto > service.getProdutos().size());

                            System.out.println("ENTRE COM OS NOVOS DADOS PARA O PRODUTO " + numProduto);
                            service.editarProduto(numProduto, receberDadosProduto());
                            break;
                        } catch (EditarProdutoException e) {
                            System.out.println("Não foi possível editar o produto.");
                        }
                    case 3: // Excluir produto
                        service.mostrarProdutos(service.getProdutos());
                        if (service.getProdutos().size() == 1) { // Mudar para try-catch
                            System.out.println("Há apenas um produto na lista. Não é possível excluir!");
                        } else {
                            int numProduto = 0;
                            do {
                                System.out.print("QUAL O NÚMERO DO PRODUTO QUE DESEJA EXCLUIR? ");
                                numProduto = Integer.parseInt(sc.nextLine());
                                if (numProduto <= 0 || numProduto > service.getProdutos().size()) {
                                    System.out.println("Produto não encontrado.");
                                }
                            } while (numProduto <= 0 || numProduto > service.getProdutos().size());

                            service.excluirProduto(numProduto);
                        }
                        break;
                    case 4: // Importar mostruario de fabrica
                        service.importarMostruario();
                        break;
                    case 5: // Finalizar o aplicativo
                        System.out.println("Aplicativo encerrado.");
                        break;
                    default: // Caso a resposta seja diferente das opcoes oferecidas
                        System.out.println("Opção inválida.");
                }
            } while (opcao != 5);
        } catch (AtualizarListaProdutosException e) {
            System.out.println("Não foi possível atualizar a lista de produtos, portanto, não é possível continuar.");
        }

        sc.close();
    }
}
