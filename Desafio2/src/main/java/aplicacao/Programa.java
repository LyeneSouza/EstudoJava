package aplicacao;

import entidades.Loja;

import java.util.Locale;

public class Programa {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Loja loja = new Loja();

        loja.menuLoja();
    }
}
