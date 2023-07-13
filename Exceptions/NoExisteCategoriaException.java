package Exceptions;

public class NoExisteCategoriaException extends Exception {
    public NoExisteCategoriaException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
