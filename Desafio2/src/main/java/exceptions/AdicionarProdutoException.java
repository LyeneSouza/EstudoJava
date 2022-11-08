package exceptions;

public class AdicionarProdutoException extends Exception {

    private static final long serialVersionUID = 1;

    public AdicionarProdutoException(String message) {
        super(message);
    }
}
