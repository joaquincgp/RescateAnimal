package Exceptions;

public class NoExisteException extends Exception{
    public NoExisteException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
