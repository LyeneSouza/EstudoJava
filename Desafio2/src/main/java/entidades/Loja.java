package entidades;

import exceptions.*;
import servicos.ProdutoService;

import java.util.List;
import java.util.Scanner;

public class Loja {
    private ProdutoService service = new ProdutoService();

    private Scanner sc = new Scanner(System.in);

    public Loja() {
    }

    public Produto receberDadosProduto() {

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

    public void menuLoja() {

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
                    opcao = 0;
                    System.out.println("Ocorreu um erro na entrada dos dados.");
                }

                switch (opcao) {
                    case 1: // Adicionar produto
                        try {
                            System.out.println("ENTRE COM OS DADOS DO PRODUTO A SER ADICIONADO");
                            Produto produto = receberDadosProduto();

                            if (produto != null) {
                                String confirma = "";
                                while (!confirma.equals("S") || !confirma.equals("N")) {
                                    System.out.print("Confirma a inclusão do produto '" + produto + "' (s/n)? ");
                                    confirma = sc.nextLine().toUpperCase().trim();
                                    if (confirma.equals("S")) {
                                        service.adicionarProduto(produto);
                                        service.mostrarProdutos();
                                        break;
                                    } else if (confirma.equals("N")) {
                                        System.out.println("Inclusão cancelada!");
                                        break;
                                    } else {
                                        System.out.println("Resposta inválida.");
                                    }
                                }
                            }
                        } catch (AdicionarProdutoException e) {
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Erro inesperado.");
                        }
                        break;
                    case 2: // Editar produto
                        try {
                            service.mostrarProdutos();
                            int numProduto = 0;
                            do {
                                System.out.print("QUAL O NÚMERO DO PRODUTO QUE DESEJA EDITAR? ");
                                numProduto = Integer.parseInt(sc.nextLine());
                                if (numProduto <= 0 || numProduto > service.quantidadeProdutos()) {
                                    System.out.println("Produto não encontrado.");
                                }
                            } while (numProduto <= 0 || numProduto > service.quantidadeProdutos());

                            System.out.println("ENTRE COM OS NOVOS DADOS PARA O PRODUTO " + numProduto);
                            Produto produto = receberDadosProduto();

                            if (produto != null) {
                                String confirma = "";
                                while (!confirma.equals("S") || !confirma.equals("N")) {
                                    System.out.print("Confirma a edição do produto " + numProduto + " (s/n)? ");
                                    confirma = sc.nextLine().toUpperCase().trim();
                                    if (confirma.equals("S")) {
                                        service.editarProduto(numProduto, produto);
                                        service.mostrarProdutos();
                                        break;
                                    } else if (confirma.equals("N")) {
                                        System.out.println("Edição cancelada!");
                                        break;
                                    } else {
                                        System.out.println("Resposta inválida.");
                                    }
                                }
                            }
                        } catch (EditarProdutoException e) {
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Erro inesperado.");
                        }
                        break;
                    case 3: // Excluir produto
                        try {
                            service.mostrarProdutos();
                            if (service.quantidadeProdutos() == 1) {
                                System.out.println("Há apenas um produto na lista. Não é possível excluir.");
                            } else {
                                int numProduto = 0;
                                do {
                                    System.out.print("QUAL O NÚMERO DO PRODUTO QUE DESEJA EXCLUIR? ");
                                    numProduto = Integer.parseInt(sc.nextLine());
                                    if (numProduto <= 0 || numProduto > service.quantidadeProdutos()) {
                                        System.out.println("Produto não encontrado.");
                                    }
                                } while (numProduto <= 0 || numProduto > service.quantidadeProdutos());

                                String confirma = "";
                                while (!confirma.equals("S") || !confirma.equals("N")) {
                                    System.out.print("Confirma a exclusão do produto " + numProduto + " (s/n)? ");
                                    confirma = sc.nextLine().toUpperCase().trim();
                                    if (confirma.equals("S")) {
                                        service.excluirProduto(numProduto);
                                        service.mostrarProdutos();
                                        break;
                                    } else if (confirma.equals("N")) {
                                        System.out.println("Exclusão cancelada!");
                                        break;
                                    } else {
                                        System.out.println("Resposta inválida.");
                                    }
                                }
                            }
                        } catch (ExcluirProdutoException e) {
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Erro inesperado.");
                        }
                        break;
                    case 4: // Importar mostruario de fabrica
                        try {
                            // Solicitando o arquivo .csv ao usuario
                            System.out.print("Para começar, entre com o caminho do arquivo (.csv) do mostruário da fábrica: ");
                            String caminhoMostruario = sc.nextLine();

                            List<Produto> produtosMostruario = service.lerMostruario(caminhoMostruario);

                            // Imprimindo os produtos do mostruario
                            System.out.println("A seguir, confira a lista de produtos do mostruário:");
                            service.mostrarProdutos(produtosMostruario);

                            String confirma = "";
                            while (!confirma.equals("S") || !confirma.equals("N")) {
                                System.out.print("Confirma a importação dos produtos do mostruário (s/n)? ");
                                confirma = sc.nextLine().toUpperCase().trim();
                                if (confirma.equals("S")) {
                                    service.importarMostruario(produtosMostruario);
                                    service.mostrarProdutos();
                                    break;
                                } else if (confirma.equals("N")) {
                                    System.out.println("Importação cancelada!");
                                    break;
                                } else {
                                    System.out.println("Resposta inválida.");
                                }
                            }
                        } catch (ImportarMostruarioException e) {
                            System.out.println("Não foi possível importar o mostruário.");
                        } catch (LerMostruarioException e) {
                            System.out.println("Erro ao acessar o arquivo do mostruário.");
                        } catch (Exception e) {
                            System.out.println("Erro inesperado.");
                        }
                        break;
                    case 5: // Finalizar o aplicativo
                        String confirma = "";
                        while (!confirma.equals("S") || !confirma.equals("N")) {
                            System.out.print("Tem certeza que deseja encerrar o aplicativo (s/n)? ");
                            confirma = sc.nextLine().toUpperCase().trim();
                            if (confirma.equals("S")) {
                                System.out.println("Aplicativo encerrado.");
                                break;
                            } else if (confirma.equals("N")) {
                                opcao = 0;
                                break;
                            } else {
                                System.out.println("Resposta inválida.");
                            }
                        }
                    case 0:
                        break;
                    default: // Caso a resposta seja diferente das opcoes oferecidas
                        System.out.println("Opção inválida.");
                }
            } while (opcao != 5);
        } catch (AtualizarListaProdutosException e) {
            System.out.println("Não foi possível atualizar a lista de produtos, portanto, não é possível continuar.");
        } catch (Exception e) {
            System.out.println("Erro inesperado.");
        }

        sc.close();
    }
}
