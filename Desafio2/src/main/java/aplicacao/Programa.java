package aplicacao;

import com.opencsv.exceptions.CsvException;
import entidades.Loja;

import java.io.IOException;
import java.util.Locale;

public class Programa {

    public static void main(String[] args) throws IOException, CsvException {

        Locale.setDefault(Locale.US);

        Loja loja = new Loja();

        loja.menuLoja();
    }
}
