package exceptions;

public class ExcluirProdutoException extends Exception {

    private static final long serialVersionUID = 1;

    public ExcluirProdutoException(String message) {
        super(message);
    }
}
