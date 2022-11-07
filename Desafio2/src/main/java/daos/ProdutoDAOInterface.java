package daos;

import com.opencsv.exceptions.CsvException;
import entidades.Produto;

import java.io.IOException;
import java.util.List;

public interface ProdutoDAOInterface {

    public void salvar(List<Produto> produtos);

    public List<Produto> lerMostruario(String caminho) throws IOException, CsvException;

    public List<Produto> lerProdutos();
}
